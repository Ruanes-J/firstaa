package kr.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
import javax.servlet.ServletRequest;
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

import kr.board.domain.BoardVO;
import kr.board.domain.BoardPage;
import kr.board.service.BoardService;

//@Controller를 이용하여 com.intranet.controller.intranetController 클래스를 Bean으로 등록
//servlet-context.xml에 <context:component-scan base-package="com.intranet.aop" /> 등록
@RequestMapping("/board/*")
@Controller
public class BoardController {
	@Inject
	private BoardService service; // 의존성 주입으로 객체 생성

	@Inject
	private BCryptPasswordEncoder pwdEncoder; // 의존성 주입으로 객체 생성
//게시글 작성(GET 방식)

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWrite(@RequestParam int num, Model model, HttpServletRequest session) throws Exception {
		
		String SessionUserid = (String) session.getAttribute("userid");
		model.addAttribute("userid", SessionUserid);
		model.addAttribute("num", num);
	}

	// 게시글 작성(POST 방식)
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWirte(BoardVO vo) throws Exception {
		System.out.println("write method Test 4");   
		System.out.println("userid = " + vo.getUserid()); 
		System.out.println("title = " + vo.getTitle());
		System.out.println("content = " + vo.getContent());
		System.out.println("writer = " + vo.getWriter());
		service.write(vo);
		return "redirect:/board/listPage?num=1";
	}

//게시물 목록 보기
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void getListPage(Model model, @RequestParam(name = "num", required = false) int num,
			@RequestParam(name = "searchType", defaultValue = "title", required = false) String searchType,
			@RequestParam(name = "keyword", defaultValue = "", required = false) String keyword) throws Exception {
		BoardPage page = new BoardPage();

		page.setNum(num);
		page.setCount(service.count(searchType, keyword));

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
	public void getView(BoardVO vo, @RequestParam(name = "bno", required = false) int bno,
			@RequestParam(name = "num", required = false) int num,
			@RequestParam(name = "searchType", defaultValue = "title", required = false) String searchType,
			@RequestParam(name = "keyword", defaultValue = "", required = false) String keyword, Model model,
			HttpSession session) throws Exception {
		String SessionUserid = (String) session.getAttribute("userid"); // 세션 정보에서 userid를 가져 온다.

		BoardVO BoardData = service.view(bno); // 게시물 정보를 가져 온다
		int minbno = service.checkMIN(searchType, keyword); // 처음 게시물 번호(최초 등록)를 가져온다
		int maxbno = service.checkMAX(searchType, keyword); // 마지막 게시물 번호(가장 최근에 등록)를 가져 온다.

		int viewPrev = 0;
		int viewNext = 0;

		if (bno != minbno)
			viewPrev = service.viewPrev(bno, searchType, keyword); // 이전 게시물 번호를 가져 온다.
		if (bno != maxbno)
			viewNext = service.viewNext(bno, searchType, keyword); // 다음 게시물 정보를 가져 온다.

		List<Map<String, Object>> fileListView = service.fileList(bno); // 업로드 파일 리스트
		List<BoardVO> replyListView = service.replyList(bno); // 댓글 리스트
		// String userid = BoardData.getUserid(); //tbl_intranet에서 bno에 해당하는 userid를 가져
		// 온다.

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", SessionUserid);
		map.put("bno", bno);
		BoardVO likeInfoListView = service.likeInfoList(map); // 좋아요/싫어요 리스트

		BoardData.setNum(num);
		model.addAttribute("view", BoardData);
		model.addAttribute("bno", bno);
		model.addAttribute("num", num);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		model.addAttribute("viewPrev", viewPrev);
		model.addAttribute("viewNext", viewNext);
		model.addAttribute("replyListView", replyListView);

		// 좋아요/싫어요 정보가 등록되어 있지 않으면 MyLikeCheck,MyDislikeChecheck에 "N"을 입력
		if (likeInfoListView == null) {
			model.addAttribute("myLikeCheck", "N");
			model.addAttribute("myDislikeCheck", "N");
		} else if (likeInfoListView != null) {
			model.addAttribute("myLikeCheck", likeInfoListView.getMyLikeCheck());
			model.addAttribute("myDislikeCheck", likeInfoListView.getMyDislikeCheck());
		}

		// 파일 목록 보여 주기
		if (!fileListView.isEmpty()) {
			model.addAttribute("fileList", fileListView);
			model.addAttribute("file", "true");
		}

		// 시용자 권한을 가진 사람은 타인이 쓴 글을 수정/삭제 할수 없음.
		if (!SessionUserid.equals(BoardData.getUserid()) && !session.getAttribute("authority_code").equals("01"))
			session.setAttribute("msg", "false");
		else
			session.setAttribute("msg", "true");

		// 본인이 쓴 게시물은 조회수 증가가 안됨.
		if (!SessionUserid.equals(BoardData.getUserid())) {
			int viewCnt = BoardData.getViewCnt() + 1;
			BoardData.setViewCnt(viewCnt);
			service.hitno(BoardData);
		}

	}

//게시물 수정(GET)
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String getModify(@RequestParam("bno") int bno, @RequestParam("num") int num,
			@RequestParam(name = "searchType", defaultValue = "title", required = false) String searchType,
			@RequestParam(name = "keyword", defaultValue = "", required = false) String keyword, Model model,
			HttpSession session) throws Exception {
		BoardVO BoardData = service.view(bno);
		String SessionUserid = (String) session.getAttribute("userid");
		if (!SessionUserid.equals(BoardData.getUserid()) && !session.getAttribute("authority_code").equals("01"))
			session.setAttribute("msg", "illegal");

		List<Map<String, Object>> fileListView = service.fileList(bno);
		if (!fileListView.isEmpty()) {
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
	public String postModify(BoardVO postData, HttpSession session, @RequestParam("num") int num,
			@RequestParam("bno") int bno,
			@RequestParam(name = "searchType", defaultValue = "title", required = false) String searchType,
			@RequestParam(name = "keyword", defaultValue = "", required = false) String keyword,
			@RequestParam(name = "deleteFileList", required = false) String[] deleteFileNo, Model model)
			throws Exception {

		String filePath = "d:\\Repository\\file\\";
		BoardVO BoardData = service.view(bno);

		if (!StringUtils.isEmpty(deleteFileNo)) {
			for (int i = 0; i < deleteFileNo.length; i++) {
				BoardVO fileInfo = service.fileInfo(Integer.parseInt(deleteFileNo[i]));
				String deleteFile = fileInfo.GetStored_file_name();
				File file = new File(filePath + deleteFile);
				file.delete();
				service.fileDelete(Integer.parseInt(deleteFileNo[i]));
			}
		}

		String SessionUserid = (String) session.getAttribute("userid");
		if (SessionUserid.equals(BoardData.getUserid()) || session.getAttribute("authority_code").equals("01"))
			service.modify(postData);

		return "redirect:/board/view?num=" + Integer.toString(num) + "&bno=" + Integer.toString(bno) + "&searchType="
				+ searchType + "&keyword=" + keyword;

	}

//게시물 삭제
	@Transactional
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String getDelete(HttpSession session, @RequestParam("bno") int bno, @RequestParam("num") int num)
			throws Exception {
		String SessionUserid = (String) session.getAttribute("userid");
		BoardVO BoardData = service.view(bno); // 게시물 정보 가져 오기
		List<Map<String, Object>> FileList = service.fileList(bno); // 게시물에 업로드된 파일 리스트 정보 가져 오기
		if (SessionUserid.equals(BoardData.getUserid()) || session.getAttribute("authority_code").equals("01")) {

			// 첨부 파일 삭제 --> 업로드된 파일이 있으면 tbl_check내 fileCheck을 N으로 변경
			if (!FileList.isEmpty())
				service.fileCheckChange(bno);
			service.replyDeleteList(bno); // 게시물에 속한 댓글 전체 삭제
			service.delete(bno); // 게시물 본문 삭제
		}
		return "redirect:/board/listPage?num=" + Integer.toString(num);
	}

//파일업로드(Ajax)
	@ResponseBody
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST, headers = ("content-type=multipart/*"))
	public String upload(@RequestParam("SendToFileList") List<MultipartFile> multipartFile,
			@RequestParam("kinds") String kinds, @RequestParam(name = "bno", required = false) String bno,
			HttpSession session) {

		String strResult = "{ \"result\":\"FAIL\" }";
		String filePath = "d:\\Repository\\file\\"; // 업로드된 파일 저장하는 디렉토리

		String userid = (String) session.getAttribute("userid"); // 세션정보에서 userid를 가져 온다.

		File targetFile = new File(filePath);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> listMap = null;

		int i = 0;

		try { // 업로드된 파일 있을 경우

			if (!multipartFile.isEmpty()) {

				for (MultipartFile mpr : multipartFile) {

					String originalFileName = mpr.getOriginalFilename();
					String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
					String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;

					try {
						targetFile = new File(filePath + storedFileName);
						mpr.transferTo(targetFile);

						listMap = new HashMap<String, Object>();
						listMap.put("org_file_name", originalFileName);
						listMap.put("stored_file_name", storedFileName);
						listMap.put("file_size", mpr.getSize());
						listMap.put("userid", userid);

						if (kinds.equals("M")) { // 게시물 등록 시 파일 업로드
							listMap.put("bno", bno);
							list.add(listMap);
							service.updateFile(list.get(i));
						} else if (kinds.equals("R")) { // 게시물 수정 시 파일 업로드
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
			} else // 업로드된 파일이 없을 경우
				strResult = "{ \"result\":\"Good\" }";
		} catch (Exception e) {
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

		byte fileByte[] = org.apache.commons.io.FileUtils
				.readFileToByteArray(new File("d:\\Repository\\file\\" + storedFileName));

		rs.setContentType("application/octet-stream");
		rs.setContentLength(fileByte.length);
		rs.setHeader("Content-Disposition",
				"attachment; fileName=\"" + URLEncoder.encode(orgFileName, "UTF-8") + "\";");
		rs.getOutputStream().write(fileByte);
		rs.getOutputStream().flush();
		rs.getOutputStream().close();

	}

//좋아요/싫어요 관리
	@ResponseBody
	@RequestMapping(value = "/likeCheck", method = RequestMethod.POST)
	public Map<String, Object> postLikeCheck(@RequestBody Map<String, Object> ajaxData) throws Exception {

		BoardVO likeInfoListView = service.likeInfoList(ajaxData);
		// select myLikeCheck, myDislikeCheck from tbl_like where userid = #{userid} and
		// bno = #{bno}

		String likeDate = "";
		String dislikeDate = "";

		// tbl_like 입력/수정
		// 현재 날짜, 시간 구해서 좋아요/싫어요 한 날짜/시간 입력 및 수정
		LocalDateTime now = LocalDateTime.now();
		if (ajaxData.get("myLikeCheck").equals("Y")) {
			likeDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			dislikeDate = "";
		} else if (ajaxData.get("myDislikeCheck").equals("Y")) {
			dislikeDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			likeDate = "";
		}

		ajaxData.put("likeDate", likeDate);
		ajaxData.put("dislikeDate", dislikeDate);

		if (likeInfoListView == null)
			service.likeInsert(ajaxData);
		else if (likeInfoListView != null)
			service.likeUpdate(ajaxData);
		// tbl_like 입력/수정 종료

		int bno = (Integer) ajaxData.get("bno");
		int checkCnt = (Integer) ajaxData.get("checkCnt");

		int likeCnt = 0;
		int dislikeCnt = 0;
		BoardVO vo = service.listLikeCnt(bno);

		if (vo != null) {
			likeCnt = vo.getLikeCnt();
			dislikeCnt = vo.getDislikeCnt();
		}

		switch (checkCnt) {
		case 1:
			likeCnt--;
			break;
		case 2:
			likeCnt++;
			dislikeCnt--;
			break;
		case 3:
			likeCnt++;
			break;
		case 4:
			dislikeCnt--;
			break;
		case 5:
			likeCnt--;
			dislikeCnt++;
			break;
		case 6:
			dislikeCnt++;
			break;
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

		if (option.equals("I"))
			service.replyInsert(vo);
		else if (option.equals("U")) {
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

		service.replyDelete((Integer) map.get("replyBno"));
		List<BoardVO> replyDataList = service.replyList((Integer) map.get("bno"));

		return replyDataList;
	}
	
//연습용 게시판 값입력
	@RequestMapping(value = "/writeForm", method = RequestMethod.GET)
	public void getWriteForm(BoardVO vo) throws Exception {
	
		
		//return "redirect:/board/readForm";
		
	}	
	
	
	@RequestMapping(value = "/writeForm", method = RequestMethod.POST)
	public String postWriteForm(BoardVO vo) throws Exception {
		service.writeForm(vo);
		
		return "redirect:/board/readForm";
		
	}	
	@RequestMapping(value = "/readForm", method = RequestMethod.GET)
	public void readForm(BoardVO vo, Model model) throws Exception {
		
	List<BoardVO> list = null;
	list = service.readForm(vo);
	model.addAttribute("list", list);
	System.out.println(vo.getUname());
	
	
	//return "redirect:/board/readForm";
		
	}	
	
	@RequestMapping(value = "/modifyForm", method = RequestMethod.GET)
	public void GetmodifyForm(BoardVO vo, Model model) throws Exception {
		
	List<BoardVO> list = null;
	list = service.readForm(vo);
	model.addAttribute("list", list);
		
	//return "redirect:/board/modifyForm";
		
	}		
	
	
	@RequestMapping(value = "/modifyForm", method = RequestMethod.POST)
	public void PostmodifyForm(BoardVO vo, Model model) throws Exception {
		
	List<BoardVO> list = null;
	list = service.readForm(vo);
	model.addAttribute("list", list);
		
	//return "redirect:/board/modifyForm";
		
	}		

}