package kr.board.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.board.dao.boardDAO;
import kr.board.domain.boardVO;



@Service
public class boardServiceImpl implements boardService {

 @Inject
 private boardDAO dao;


//게시물 작성
@Override
public void write(boardVO vo) throws Exception {
	dao.write(vo);
}

//게시물 목록 보기
@Override
public List<boardVO> listPage(int displayPost, int postNum, String searchType, String keyword) throws Exception {
	return dao.listPage(displayPost, postNum, searchType, keyword);
}

//게시물 조회
@Override
public boardVO view(int bno) throws Exception {
	return dao.view(bno);
}

//게시물 수정
@Override
public void modify(boardVO vo) throws Exception {
 dao.modify(vo);
}

//게시물 삭제
@Override
public void delete(int bno) throws Exception {
 dao.delete(bno);
}

//게시물 총 갯수
@Override
public int count(String searchType,String keyword) throws Exception {
	return dao.count(searchType,keyword);
}

//게시물 이전 페이지
@Override
public int viewPrev(int bno,String searchType,String keyword) throws Exception {
	return dao.viewPrev(bno,searchType,keyword);
}

//게시물 다음 페이지
@Override
public int viewNext(int bno,String searchType,String keyword) throws Exception {
	return dao.viewNext(bno,searchType,keyword);
}

//게시물 처음글 확인
@Override
public int checkMIN(String searchType,String keyword) throws Exception {
	return dao.checkMIN(searchType,keyword);
}

//게시물 다음글 확인
@Override
public int checkMAX(String searchType,String keyword) throws Exception {
	return dao.checkMAX(searchType,keyword);
}

//조회수 증가
@Override
public void hitno(boardVO vo) throws Exception {
	dao.hitno(vo);
}

//첨부 파일 업로드(신규) 정보 입력
@Override
public void insertFile(Map<String, Object> map) throws Exception {
	dao.insertFile(map);
}

//첨부 파일 업로드(수정) 정보 입력
@Override
public void updateFile(Map<String, Object> map) throws Exception {
	dao.updateFile(map);
}

//첨부 파일 업로드 정보 조회
@Override
public List<Map<String, Object>> fileList(int bno) throws Exception {
	return dao.fileList(bno);
}

//첨부 파일 다운로드 정보 조회
@Override
public boardVO fileInfo(int file_no) throws Exception {
	return dao.fileInfo(file_no);
}

//첨부 파일 삭제(게시물 수정시 업로드 파일 삭제)
@Override
public void fileDelete(int file_no) throws Exception {
	dao.fileDelete(file_no);
}

//첨부 파일 삭제(게시물 삭제 tbl_file 내 fileCheck을 N으로 변경)
@Override
public void fileCheckChange(int bno) throws Exception {
	dao.fileCheckChange(bno);
}

//좋아요/싫어요 목록 보기
@Override
public boardVO likeInfoList(Map<String,Object> map) throws Exception {
	return dao.likeInfoList(map);
}

//좋아요/싫어요 등록
@Override
public void likeInsert(Map<String, Object> map) throws Exception {
	dao.likeInsert(map);
}

//좋아요/싫어요 수정
@Override
public void likeUpdate(Map<String, Object> map) throws Exception {
	dao.likeUpdate(map);
}

//좋아요/싫어요 삭제
@Override
public void likeDelete(String userid) throws Exception {
	dao.likeDelete(userid);
}

//게시판 테이블에서 싫어요/좋아요 값만 조회
@Override
public boardVO listLikeCnt(int bno) throws Exception {
	return dao.listLikeCnt(bno);
}

//게시판 테이블에서 싫어요/좋아요 값만 수정
@Override
public void listLikeUpdate(Map<String,Object> map) throws Exception {
	dao.listLikeUpdate(map);	
}

//댓글 등록
@Override
public void replyInsert(boardVO vo) throws Exception {
	dao.replyInsert(vo);
}

//댓글 보기
@Override
public List<boardVO> replyList(int bno) throws Exception {
	return dao.replyList(bno);	
}

//댓글 삭제(게시물 조회 화면에서 개별 댓글 삭제)
@Override
public void replyDelete(int replyBno) throws Exception {
	dao.replyDelete(replyBno);	
}

//댓글 전체 삭제(게시물 삭제 전체 댓글 삭제)
@Override
public void replyDeleteList(int bno) throws Exception {
	dao.replyDeleteList(bno);
}

//댓글 수정
@Override
public void replyUpdate(boardVO vo) throws Exception {
	dao.replyUpdate(vo);	
}

//로그인
@Override
public boardVO loginCheck(boardVO vo) throws Exception {
	return dao.loginCheck(vo);
}

//아이디 중복 체크
@Override
public int idCheck(boardVO vo) throws Exception {
	return dao.idCheck(vo);
}

//사용자 등록
@Override
public void mbrInfoRegister(boardVO vo) throws Exception {
	dao.mbrInfoRegister(vo);
}

//사용자 사진 등록
@Override
public void mbrPhotoRegister(Map<String, Object> map) throws Exception {
	dao.mbrPhotoRegister(map);	
}

//사용자 사진 수정
@Override
public void mbrPhotoModify(Map<String, Object> map) throws Exception {
	dao.mbrPhotoModify(map);	
}

//사용자 로그인 정보 등록
@Override
public void mbrLoginInfo(Map<String, Object> map) throws Exception {
	dao.mbrLoginInfo(map);	
}

//관리자용 사용자 목록 보기
@Override
public List<boardVO> mbrListInfo() throws Exception {
	return dao.mbrInfoList();
}

//사용자 정보 보기
@Override
public boardVO mbrInfoView(String userid) throws Exception {
	return dao.mbrInfoView(userid);
}

//사용자 정보 수정
@Override
public void mbrInfoModify(boardVO vo) throws Exception {
	dao.mbrInfoModify(vo);
}

//사용자 암호 수정
@Override
public void passwdModify(boardVO vo) throws Exception {
	dao.passwdModify(vo);
}

//사용자 암호 수정일 등록
@Override
public void passwdModiDate(Map<String, Object> map) throws Exception {
	dao.passwdModiDate(map);
}

//회원 탈퇴(사용자 정보 삭제)
@Override
public void mbrInfoDelete(String userid) throws Exception {
	dao.mbrInfoDelete(userid);
}

//회원 탈퇴 시 사용자가 등록한 게시물 전체 삭제
@Override
public void mbrDeleteReplyList(String userid) throws Exception {
	dao.mbrDeleteReplyList(userid);
}

//회원탈퇴 시 tbl_file 내에 사용자가 등록한 파일 정보를 수정 : fileCheck를 N으로 수정
@Override
public void mbrFileCheck(String userid) throws Exception {
	dao.mbrFileCheck(userid);
}

//아이디 찾기
@Override
public int idFind(boardVO vo) throws Exception {
	return dao.idFind(vo);
}

} //End of class BoardServiceImpl 
