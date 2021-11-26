package kr.member.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.board.domain.BoardVO;
import kr.board.domain.BoardPage;
import kr.member.domain.MemberVO;
import kr.member.service.MemberService;


@RequestMapping("/member/*")
@Controller
public class MemberController {
	@Inject
	private MemberService service; //의존성 주입으로 객체 생성
	@Inject
	private BCryptPasswordEncoder pwdEncoder; //의존성 주입으로 객체 생성
	
	//로그인(GET)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() throws Exception {
	   
		return "redirect:/";
		}
	//로그인(POST)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(MemberVO loginData, Model model,HttpSession session, RedirectAttributes rttr) throws Exception {
				
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
			MemberVO memberData = service.loginCheck(loginData);	
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
//				return "redirect:/intranet/view";
				
			
			
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
	public int idCheck(MemberVO vo) throws Exception {
		int result = service.idCheck(vo);
		return result;
	}
	
	//사용자 등록(GET 방식)
	@RequestMapping(value = "/mbrInfoRegister", method = RequestMethod.GET)
	public void getMbrRegister(MemberVO vo) throws Exception {
		 
	}
	//사용자 등록(POST 방식)
	@RequestMapping(value = "/mbrInfoRegister", method = RequestMethod.POST)
	public String postMbrRegister(MemberVO vo, HttpServletRequest req) throws Exception {
	    
		int result = service.idCheck(vo);//아이디 중복 확인
		
		//중복된 아이디가 있으면 초기 화면으로 리다이렉트
		if(result == 1) return "redirect:/member/mbrInfoRegister";
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
		//return "redirect:/Member/listPage?num=1";
		return "redirect:/member/mbrInfoView";
	}
	//사용자 프로파일 사진업로드(Ajax)
	@ResponseBody
	@RequestMapping(value = "/imgUpload") 
	public String imgUpload(MemberVO vo, @RequestParam("imgUpload") MultipartFile multipartFile, 
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
		
		MemberVO mbrData = service.mbrInfoView(userid);
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
		MemberVO vo = service.mbrInfoView(userid); 
		model.addAttribute("view", vo);
		
	}
	//사용자 정보 수정(POST)
	@RequestMapping(value = "/mbrInfoModify", method = RequestMethod.POST)
	public String PostMbrModify(MemberVO vo, HttpSession session) throws Exception {
		  service.mbrInfoModify(vo);
		  
		  return "redirect:/board/mbrInfoView";
	}
	//사용자 암호 수정(GET)
	@RequestMapping(value = "/passwdModify", method = RequestMethod.GET)
	public void GetPasswdModify(HttpSession session, Model model, MemberVO postData) throws Exception {
			
	}
	//사용자 암호 수정(POST)
	@RequestMapping(value = "/passwdModify", method = RequestMethod.POST)
	public String PostPasswdModify(MemberVO postData, HttpSession session, @RequestParam("olduserpasswd") String olduserpasswd, RedirectAttributes rttr) throws Exception {
		  	String userid = (String)session.getAttribute("userid");
		  	MemberVO mbrData = service.mbrInfoView(userid);
		  		  	
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
	public String GetMbrInfoDelete(MemberVO vo, HttpSession session) throws Exception {
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
		List<MemberVO> list = service.mbrListInfo();
		model.addAttribute("list", list);
	}
	//일정관리
	@RequestMapping(value = "/calendarView", method = RequestMethod.GET)
	public void caledarView(MemberVO vo) throws Exception {
		
		
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
	
	
	
	
}
