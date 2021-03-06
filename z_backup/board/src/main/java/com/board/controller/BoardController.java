package com.board.controller;
import java.io.File;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.board.domain.BoardVO;
import com.board.domain.Page;
import com.board.service.BoardService;

//@Controller를 이용하여 com.board.controller.BoardController 클래스를 Bean으로 등록
//servlet-context.xml에 <context:component-scan base-package="com.board.aop" /> 등록
@Controller 
@RequestMapping("/board/*") 
public class BoardController {
 @Inject
 private BoardService service; //의존성 주입으로 객체 생성
 
 @Inject
 private BCryptPasswordEncoder pwdEncoder; //의존성 주입으로 객체 생성
//게시글 작성(GET 방식)
 @RequestMapping(value = "/write", method = RequestMethod.GET)
 public void getWrite(@RequestParam int num, Model model) throws Exception {
	 model.addAttribute("num", num);
 }
 
 //게시글 작성(POST 방식)
 @RequestMapping(value = "/write", method = RequestMethod.POST)
 public String postWirte(BoardVO vo) throws Exception {
	 service.write(vo);
	 return "redirect:/board/listPage?num=1";
 }
//게시물 목록 보기
@RequestMapping(value = "/listPage", method = RequestMethod.GET)
public void getListPage(Model model, @RequestParam(name="num",required=false) int num, 
		@RequestParam(name="searchType", defaultValue="title", required=false) String searchType, 
		@RequestParam(name="keyword", defaultValue="", required=false) String keyword ) throws Exception {
	Page page = new Page();
	
	page.setNum(num);
	page.setCount(service.count(searchType,keyword));
	
	List<BoardVO> list = null;	
	list = service.listPage(page.getDisplayPost(), page.getPostNum(), searchType, keyword);
	model.addAttribute("list", list);
	model.addAttribute("page", page);
	model.addAttribute("num", num);
	model.addAttribute("searchType", searchType);
	model.addAttribute("keyword", keyword);
}
 
//게시물 조회
@RequestMapping(value = "/view", method = RequestMethod.GET)
public void getView(BoardVO vo, @RequestParam(name="bno", required=false) int bno, @RequestParam(name="num", required=false) int num, 
		@RequestParam(name="searchType", defaultValue="title", required=false) String searchType,
		@RequestParam(name="keyword", defaultValue="", required=false) String keyword,
		Model model,HttpSession session) throws Exception {
	String SessionUserid = (String)session.getAttribute("userid"); //세션 정보에서 userid를 가져 온다.
	
	BoardVO BoardData = service.view(bno); //게시물 정보를 가져 온다
	int minbno = service.checkMIN(searchType, keyword); //처음 게시물 번호(최초 등록)를 가져온다 
	int maxbno = service.checkMAX(searchType, keyword); //마지막 게시물 번호(가장 최근에 등록)를 가져 온다.
	
	int viewPrev = 0; 
	int viewNext = 0;	
		
	if(bno != minbno)  viewPrev = service.viewPrev(bno,searchType,keyword); //이전 게시물 번호를 가져 온다.
	if(bno != maxbno)  viewNext = service.viewNext(bno,searchType,keyword); //다음 게시물 정보를 가져 온다.
	
	List<Map<String, Object>> fileListView = service.fileList(bno); //업로드 파일 리스트
	List<BoardVO> replyListView = service.replyList(bno); //댓글 리스트
	//String userid = BoardData.getUserid(); //tbl_board에서 bno에 해당하는 userid를 가져 온다.
	
	Map<String, Object> map = new HashMap<String, Object>(); 
	map.put("userid", SessionUserid); 
	map.put("bno", bno);
	BoardVO likeInfoListView = service.likeInfoList(map); //좋아요/싫어요 리스트
	
	BoardData.setNum(num);
	model.addAttribute("view", BoardData);
	model.addAttribute("bno",bno);
	model.addAttribute("num",num);
	model.addAttribute("searchType",searchType);
	model.addAttribute("keyword",keyword);
	model.addAttribute("viewPrev",viewPrev);
	model.addAttribute("viewNext",viewNext);
	model.addAttribute("replyListView",replyListView); 
	
	//좋아요/싫어요 정보가 등록되어 있지 않으면 MyLikeCheck,MyDislikeChecheck에 "N"을 입력
	if(likeInfoListView == null) {
		model.addAttribute("myLikeCheck", "N");
		model.addAttribute("myDislikeCheck", "N");
	} else if(likeInfoListView != null) {
		model.addAttribute("myLikeCheck", likeInfoListView.getMyLikeCheck());
		model.addAttribute("myDislikeCheck", likeInfoListView.getMyDislikeCheck());
	}
	
	//파일 목록 보여 주기
	if(!fileListView.isEmpty()) {
			model.addAttribute("fileList", fileListView);
			model.addAttribute("file", "true");
	}
	
	//시용자 권한을 가진 사람은 타인이 쓴 글을 수정/삭제 할수 없음.
	if(!SessionUserid.equals(BoardData.getUserid()) && !session.getAttribute("authority_code").equals("01") ) 
	 session.setAttribute("msg", "false"); else session.setAttribute("msg", "true");
	
	// 본인이 쓴 게시물은 조회수 증가가 안됨. 
	if(!SessionUserid.equals(BoardData.getUserid())) { 	
		int viewCnt = BoardData.getViewCnt() + 1;
		BoardData.setViewCnt(viewCnt);
		service.hitno(BoardData);
	}
	
}
 
 
//게시물 수정(GET)
@RequestMapping(value = "/modify", method = RequestMethod.GET)
public String getModify(@RequestParam("bno") int bno, @RequestParam("num") int num, 
		@RequestParam(name="searchType", defaultValue="title", required=false) String searchType, 
		@RequestParam(name="keyword", defaultValue="", required=false) String keyword,
		Model model, HttpSession session) throws Exception {
		BoardVO BoardData = service.view(bno);
		String SessionUserid = (String)session.getAttribute("userid");
		if(!SessionUserid.equals(BoardData.getUserid()) && !session.getAttribute("authority_code").equals("01") ) 
			session.setAttribute("msg", "illegal"); 
		
		List<Map<String, Object>> fileListView = service.fileList(bno);
		if(!fileListView.isEmpty()) {
			model.addAttribute("fileList", fileListView);
			model.addAttribute("file", "true");
		}
				
		model.addAttribute("view", BoardData);
		model.addAttribute("num", num);
		model.addAttribute("bno", bno);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);		
				
		return null;
}
//게시물 수정(POST)
@RequestMapping(value = "/modify", method = RequestMethod.POST)
public String postModify(BoardVO postData, HttpSession session, @RequestParam("num") int num, @RequestParam("bno") int bno, 
		@RequestParam(name="searchType", defaultValue="title", required=false) String searchType, 
		@RequestParam(name="keyword", defaultValue="", required=false) String keyword,		
		@RequestParam(name="deleteFileList", required=false) String[] deleteFileNo,  Model model) throws Exception {
	
	String filePath = "d:\\Repository\\file\\";
	BoardVO BoardData = service.view(bno);
	
	if(!StringUtils.isEmpty(deleteFileNo)) {
		for(int i=0;i<deleteFileNo.length; i++) {
			BoardVO fileInfo = service.fileInfo(Integer.parseInt(deleteFileNo[i]));
			String deleteFile = fileInfo.GetStored_file_name();
			File file = new File(filePath + deleteFile);
			file.delete();
			service.fileDelete(Integer.parseInt(deleteFileNo[i]));
		}
	}
	
	String SessionUserid = (String)session.getAttribute("userid");
		if(SessionUserid.equals(BoardData.getUserid()) || session.getAttribute("authority_code").equals("01")) 
			service.modify(postData); 
	
	return "redirect:/board/view?num=" + Integer.toString(num) + "&bno=" + Integer.toString(bno)
				+ "&searchType=" + searchType + "&keyword=" + keyword ;
	 
}
//게시물 삭제
@Transactional
@RequestMapping(value = "/delete", method = RequestMethod.GET)
public String getDelete(HttpSession session, @RequestParam("bno") int bno, @RequestParam("num") int num) throws Exception {
	String SessionUserid = (String)session.getAttribute("userid");
	BoardVO BoardData = service.view(bno); //게시물 정보 가져 오기
	List<Map<String, Object>>FileList = service.fileList(bno); //게시물에 업로드된 파일 리스트 정보 가져 오기
	if(SessionUserid.equals(BoardData.getUserid()) || session.getAttribute("authority_code").equals("01")) {
		
		//첨부 파일 삭제 --> 업로드된 파일이 있으면 tbl_check내 fileCheck을 N으로 변경
		if(!FileList.isEmpty()) service.fileCheckChange(bno); 
		service.replyDeleteList(bno); //게시물에 속한 댓글 전체 삭제
		service.delete(bno); //게시물 본문 삭제
	}
   return "redirect:/board/listPage?num=" + Integer.toString(num);
}
 
 
//파일업로드(Ajax)
@ResponseBody
@RequestMapping(value = "/fileUpload", method= RequestMethod.POST, headers = ("content-type=multipart/*"))
public String upload(@RequestParam("SendToFileList") List<MultipartFile> multipartFile, 
		@RequestParam("kinds") String kinds,@RequestParam(name="bno", required=false) String bno,
		HttpSession session) { 
   
	String strResult = "{ \"result\":\"FAIL\" }";
	String filePath = "d:\\Repository\\file\\"; //업로드된 파일 저장하는 디렉토리
	
	String userid = (String)session.getAttribute("userid"); //세션정보에서 userid를 가져 온다.
				
	File targetFile = new File(filePath); 
	
	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
	Map<String, Object> listMap = null;
	
	int i=0;
	
	try { //업로드된 파일 있을 경우 
		
		if(!multipartFile.isEmpty()) {
			
			for(MultipartFile mpr:multipartFile) {
												
				String originalFileName = mpr.getOriginalFilename();	
				String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));	
				String storedFileName =  UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;	
								
				try {
					targetFile = new File(filePath + storedFileName);
					mpr.transferTo(targetFile);
					
					listMap = new HashMap<String, Object>();
					listMap.put("org_file_name", originalFileName);
					listMap.put("stored_file_name", storedFileName);
					listMap.put("file_size", mpr.getSize());
					listMap.put("userid", userid);
					
					if(kinds.equals("M")) { //게시물 등록 시 파일 업로드
						listMap.put("bno", bno);
						list.add(listMap);
						service.updateFile(list.get(i));
					} else if(kinds.equals("R")) { //게시물 수정 시 파일 업로드
						list.add(listMap);	
						service.insertFile(list.get(i));
					}										
					i++;
				} catch (Exception e) {
					
					e.printStackTrace();
					break;
				}
			}
			
			strResult = "{ \"result\":\"OK\" }";
		}
		else //업로드된 파일이 없을 경우
			strResult = "{ \"result\":\"Good\" }";
	}catch(Exception e){
		e.printStackTrace();
	}
	return strResult;
}
//파일 다운로드
@RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
public void fileDownload(@RequestParam("file_no") int file_no, HttpServletResponse rs) throws Exception {
	
	BoardVO fileInfo = service.fileInfo(file_no);
	String storedFileName = fileInfo.GetStored_file_name();
	String orgFileName = fileInfo.GetOrg_file_name();
	
	byte fileByte[] = org.apache.commons.io.FileUtils.readFileToByteArray(new File("d:\\Repository\\file\\"+storedFileName));
	
	rs.setContentType("application/octet-stream");
	rs.setContentLength(fileByte.length);
	rs.setHeader("Content-Disposition",  "attachment; fileName=\""+URLEncoder.encode(orgFileName, "UTF-8")+"\";");
	rs.getOutputStream().write(fileByte);
	rs.getOutputStream().flush();
	rs.getOutputStream().close();
	
}
//좋아요/싫어요 관리
@ResponseBody
@RequestMapping(value = "/likeCheck", method = RequestMethod.POST)
public Map<String, Object> postLikeCheck(@RequestBody Map<String, Object> ajaxData) throws Exception {
	
	BoardVO likeInfoListView = service.likeInfoList(ajaxData);
	// select myLikeCheck, myDislikeCheck from tbl_like where userid = #{userid} and bno = #{bno}
	
	String likeDate = "";
	String dislikeDate = "";
	
	//tbl_like 입력/수정	
	//현재 날짜, 시간 구해서 좋아요/싫어요 한 날짜/시간 입력 및 수정
	LocalDateTime now = LocalDateTime.now();
	if(ajaxData.get("myLikeCheck").equals("Y")) { 
		likeDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		dislikeDate = ""; 
		} else if(ajaxData.get("myDislikeCheck").equals("Y")) {
			dislikeDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			likeDate = "";
		}
	
	ajaxData.put("likeDate", likeDate); 
	ajaxData.put("dislikeDate", dislikeDate);
	
	if(likeInfoListView == null) service.likeInsert(ajaxData);
		else if(likeInfoListView != null) service.likeUpdate(ajaxData);
	//tbl_like 입력/수정 종료
	
	int bno = (int)ajaxData.get("bno");
	int checkCnt = (int)ajaxData.get("checkCnt");
	
	int likeCnt = 0;
	int dislikeCnt = 0;
	BoardVO vo = service.listLikeCnt(bno);
	
	if(vo != null) {  
		likeCnt = vo.getLikeCnt();
		dislikeCnt = vo.getDislikeCnt();
	}
		
	switch(checkCnt){
    	case 1 : likeCnt --; break;
    	case 2 : likeCnt ++; dislikeCnt --; break;
    	case 3 : likeCnt ++; break;
    	case 4 : dislikeCnt --; break;
    	case 5 : likeCnt --; dislikeCnt ++; break;
    	case 6 : dislikeCnt ++; break;
	}
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("bno", bno);
	map.put("likeCnt", likeCnt);
	map.put("dislikeCnt", dislikeCnt);
	
	service.listLikeUpdate(map);
		
	return map;
}
//댓글 등록(POST)
@ResponseBody
@RequestMapping(value = "/replyInsert", method = RequestMethod.POST)
public List<BoardVO> PostReplyInsert(BoardVO vo, @RequestParam("option") String option) throws Exception {
	
	if(option.equals("I")) service.replyInsert(vo);
		else if(option.equals("U")) { 
			LocalDateTime now = LocalDateTime.now();
			String modiDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			vo.setreplyModiDate(modiDate);
			service.replyUpdate(vo);
		}
	int bno = vo.getBno();
	
	List<BoardVO> replyDataList = service.replyList(bno);
		
	return replyDataList; 
}
//댓글 삭제
//JSON --> MAP 방식으로 데이터 전송시 반드시 @RequestBody를 선언해 줘야 한다.
@ResponseBody
@RequestMapping(value = "/replyDelete", method = RequestMethod.POST)
public List<BoardVO> PostReplyDelete(@RequestBody Map<String, Object> map) throws Exception {
	
		service.replyDelete((int)map.get("replyBno"));
		List<BoardVO> replyDataList = service.replyList((int)map.get("bno"));
			
		return replyDataList; 
}
//로그인(GET)
@RequestMapping(value = "/login", method = RequestMethod.GET)
public String login() throws Exception {
   
	return "redirect:/";
	}
//로그인(POST)
@RequestMapping(value = "/login", method = RequestMethod.POST)
public String login(BoardVO loginData, Model model,HttpSession session, RedirectAttributes rttr) throws Exception {
			
	try {
		
		int sessionInterval = 60*60*24; //세션 유지 기간 : 1일
		session.setMaxInactiveInterval(sessionInterval);
		session.getAttribute("member");
		
		int result = service.idCheck(loginData);
		
		if(result == 0) { //존재하지 않는 아이디가 입력될때 
			session.setAttribute("member",null);
			rttr.addFlashAttribute("message", false);
			return "redirect:/";			
			} 		
		BoardVO memberData = service.loginCheck(loginData);	
		boolean pwdMatch = pwdEncoder.matches(loginData.getUserpasswd(),memberData.getUserpasswd());
		
		if(memberData != null && pwdMatch) {
			String userid = memberData.getUserid();
			session.setAttribute("userid",userid);
			session.setAttribute("username",memberData.getUsername());
			session.setAttribute("authority_code",memberData.getAuthority_code());
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> listMap = new HashMap<String, Object>();
			
			LocalDateTime currentTime = LocalDateTime.now();
			String FormattedCurrentTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			listMap.put("userid", userid);
			listMap.put("lastLoginDate",FormattedCurrentTime);
			list.add(listMap);	
			service.mbrLoginInfo(list.get(0));
				
			return "redirect:/board/listPage?num=1";
		
		} else {
			session.setAttribute("member",null);
			rttr.addFlashAttribute("message", false);
			return "redirect:/";
		}
	} catch(Exception e) {throw new RuntimeException(); }
	
	
}
//로그 아웃
@RequestMapping(value = "/logout", method = RequestMethod.GET)
public String logout(HttpSession session) throws Exception{
	
	session.invalidate();
	
	return "redirect:/";
}
//아이디 중복 체크
@ResponseBody
@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
public int idCheck(BoardVO vo) throws Exception {
	int result = service.idCheck(vo);
	return result;
}
//사용자 등록(GET 방식)
@RequestMapping(value = "/mbrInfoRegister", method = RequestMethod.GET)
public void getMbrRegister(BoardVO vo) throws Exception {
	 
}
//사용자 등록(POST 방식)
@RequestMapping(value = "/mbrInfoRegister", method = RequestMethod.POST)
public String postMbrRegister(BoardVO vo, HttpServletRequest req) throws Exception {
    
	int result = service.idCheck(vo);//아이디 중복 확인
	
	//중복된 아이디가 있으면 초기 화면으로 리다이렉트
	if(result == 1) return "redirect:/board/mbrInfoRegister";
		else if(result == 0) { // 회원 가입 절차 진행
		    	
		String inputPass = vo.getUserpasswd(); //로그인 창에서 입력한 암호 저장
		String pwd = pwdEncoder.encode(inputPass); //입력한 암호를 비대칭 암호화
	
		LocalDateTime currentTime = LocalDateTime.now();  
		String FormattedCurrentTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		vo.setUserpasswd(pwd); 
		vo.setRegiDate(FormattedCurrentTime);
		
		service.mbrInfoRegister(vo); //사용자 정보 등록
		
		HttpSession session = req.getSession();
		session.setAttribute("userid",vo.getUserid());
		session.setAttribute("username",vo.getUsername());
		session.setAttribute("authority_code","02");
		
	}
	return "redirect:/board/listPage?num=1";	
}
//사용자 프로파일 사진업로드(Ajax)
@ResponseBody
@RequestMapping(value = "/imgUpload") 
public String imgUpload(BoardVO vo, @RequestParam("imgUpload") MultipartFile multipartFile, 
		@RequestParam("userid") String userid, @RequestParam("kinds") String kinds ) 
{ 
 
	String strResult = "{ \"result\":\"FAIL\" }";
	String filePath = "D:\\Repository\\profile\\";
				
	File targetFile = new File(filePath);
	
	//List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	Map<String, Object> listMap = null;
	
	try { //업로드된 파일 있을 경우 
		
		if(!multipartFile.isEmpty()) {
			
				String originalFileName = multipartFile.getOriginalFilename();	
				String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));	
				String storedFileName =  UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;	
								
				try {
					targetFile = new File(filePath + storedFileName);
					
					multipartFile.transferTo(targetFile);
					
					listMap = new HashMap<String, Object>();
					listMap.put("org_file_name", originalFileName);
					listMap.put("stored_file_name", storedFileName);
					listMap.put("file_size", multipartFile.getSize());
					listMap.put("userid", userid);
					
					if(kinds.equals("R")) service.mbrPhotoRegister(listMap); //이미지 신규 등록
						else if(kinds.equals("M")) { //이미지 수정 : 기존 파일 삭제하고 신규 파일 등록 
							vo = service.mbrInfoView(userid);
							
							String old_img_stored_file = vo.getImg_stored_file();
							File file = new File(filePath + old_img_stored_file);
							file.delete();
							
							service.mbrPhotoModify(listMap);							
					}
															
				} catch (Exception e) {	e.printStackTrace(); }
			
			strResult = "{ \"result\":\"OK\" }";
		} else //업로드된 파일이 없을 경우
			strResult = "{ \"result\":\"Good\" }";
	}catch(Exception e){
		e.printStackTrace();
	}
	return strResult;
}
//사용자 정보 보기(GET 방식)
@RequestMapping(value = "/mbrInfoView", method = RequestMethod.GET)
public void GetMbrView(Model model, HttpSession session, @RequestParam(name="userid", required=false) String GetUserid) throws Exception {
  
	String userid = "";
	String SessionUserid = (String)session.getAttribute("userid");
	String ac = (String)session.getAttribute("authority_code");
	
	if(GetUserid != null && ac.equals("01")) userid = GetUserid;
			else userid = SessionUserid;
	
	BoardVO mbrData = service.mbrInfoView(userid);
	model.addAttribute("view", mbrData);
	String a_code = mbrData.getAuthority_code();
	
	if(a_code.equals("01")) model.addAttribute("a_code", "Master 관리자"); 
	else if(a_code.equals("02")) model.addAttribute("a_code", "일반 사용자"); 
	else if(a_code.equals("03")) model.addAttribute("a_code", "강의 관리자"); 
		
}
//사용자 정보 수정(GET)
@RequestMapping(value = "/mbrInfoModify", method = RequestMethod.GET)
public void GetMbrModify(HttpSession session, Model model) throws Exception {
	String userid = (String)session.getAttribute("userid");
	BoardVO vo = service.mbrInfoView(userid); 
	model.addAttribute("view", vo);
	
}
//사용자 정보 수정(POST)
@RequestMapping(value = "/mbrInfoModify", method = RequestMethod.POST)
public String PostMbrModify(BoardVO vo, HttpSession session) throws Exception {
	  service.mbrInfoModify(vo);
	  
	  return "redirect:/board/mbrInfoView";
}
//사용자 암호 수정(GET)
@RequestMapping(value = "/passwdModify", method = RequestMethod.GET)
public void GetPasswdModify(HttpSession session, Model model, BoardVO postData) throws Exception {
		
}
//사용자 암호 수정(POST)
@RequestMapping(value = "/passwdModify", method = RequestMethod.POST)
public String PostPasswdModify(BoardVO postData, HttpSession session, @RequestParam("olduserpasswd") String olduserpasswd, RedirectAttributes rttr) throws Exception {
	  	String userid = (String)session.getAttribute("userid");
	  	BoardVO mbrData = service.mbrInfoView(userid);
	  		  	
	  	if(!pwdEncoder.matches(olduserpasswd,mbrData.getUserpasswd())) {
	  		
	  		rttr.addFlashAttribute("msg", false);
	  		return "redirect:/board/passwdModify";
	  		
	  	} else {
	  		
	  		postData.setUserid(userid);
	  		postData.setUserpasswd(pwdEncoder.encode(postData.getUserpasswd()));  
	  		service.passwdModify(postData);
	  		
	  		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> listMap = new HashMap<String, Object>();
	  		LocalDateTime currentTime = LocalDateTime.now();
	  		String FormattedCurrentTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	  		
	  		listMap.put("userid", userid);
			listMap.put("pwModiDate",FormattedCurrentTime);
			list.add(listMap);	
			service.passwdModiDate(list.get(0));
	  		return "redirect:/board/logout";
	  	}
}
//회원 탈퇴
@Transactional
@RequestMapping(value = "/mbrInfoDelete", method = RequestMethod.GET)
public String GetMbrInfoDelete(BoardVO vo, HttpSession session) throws Exception {
	  	String userid = (String)session.getAttribute("userid");
	  	vo = service.mbrInfoView(userid); //사용자 정보를 읽어 들인다.
	  	String img_stored_file = vo.getImg_stored_file();
	  	if(!img_stored_file.equals("")) { //사용자가 등록한 사진을 삭제
	  		String filePath = "D:\\Repository\\profile\\";
	  		File file = new File(filePath+img_stored_file);
	  		file.delete();	  	
	  	}
	  	
	  	service.mbrFileCheck(userid); //회원이 등록한 파일 업로드 정보인 fileCheck를 N으로 변경
	  	service.mbrDeleteReplyList(userid); //회원이 등록한 댓글 전체 삭제
	  	service.likeDelete(userid); //회원이 등록한 좋아요/싫어요 전체 삭제
	  	
	    //사용자 정보 + 사용자가 등록한 게시물 + 게시물의 댓글 전체 삭제
	  	//delete a,b from tbl_board a join tbl_member b on a.userid = b.userid where b.userid = #{userid}
	  	//tbl_reply 테이블은 CONSTRAINT FK_tbl_reply_bno FREIGN KEY(BNO) REFERENCES TBL_BOARD(BNO) 
	  	// ON DELETE CASCADE 제약 조건으로 인하여 tbl_borad의 특정 bno 레코드가 삭제되면 tbl_reply 테이블의 레코드까지 삭제된다.  
	  	service.mbrInfoDelete(userid);	
	  	
	  	return "redirect:/board/logout";
}
//관리자용 사용자 리스트 목록 보기
@RequestMapping(value = "/mbrInfoList", method = RequestMethod.GET)
public void GetMbrInfoList(Model model, HttpSession session) throws Exception {
	List<BoardVO> list = service.mbrListInfo();
	model.addAttribute("list", list);
}
//일정관리
@RequestMapping(value = "/calendarView", method = RequestMethod.GET)
public void caledarView(BoardVO vo) throws Exception {
	
	
}
//동영상 강의 보기
@RequestMapping(value = "/eduView", method = RequestMethod.GET)
public void eduView(HttpSession session, Model model) throws Exception {
	String userid = (String)session.getAttribute("userid");
	String username = (String)session.getAttribute("username");
	
	model.addAttribute("userid", userid);
	model.addAttribute("username", username);
}
//소스 보기
@RequestMapping(value = "/sourceView", method = RequestMethod.GET)
public void sourceView(HttpSession session, Model model) throws Exception {
	String userid = (String)session.getAttribute("userid");
	String username = (String)session.getAttribute("username");
	
	model.addAttribute("userid", userid);
	model.addAttribute("username", username);
}

//게시물 등록(시험)
@RequestMapping(value = "/mWrite", method = RequestMethod.GET)
public void getWrite(HttpSession session, Model model) throws Exception {
	
}

//게시물 등록(시험)
@RequestMapping(value = "/mWrite", method = RequestMethod.POST)
public void postWrite(HttpSession session, Model model) throws Exception {
	
}
} //End of BoardController
