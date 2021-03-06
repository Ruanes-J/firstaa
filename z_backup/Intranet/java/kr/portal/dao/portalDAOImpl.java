package kr.portal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.portal.domain.portalVO;

@Repository
public class portalDAOImpl implements portalDAO {

 @Inject
 private SqlSession sql;
 
 private static String namespace = "kr.portal.mappers.Intranet";

 
// 게시물 작성
@Override
public void write(portalVO vo) throws Exception {
	sql.insert(namespace + ".write", vo);
}

//게시물 목록 보기
@Override
public List<portalVO> listPage(int displayPost, int postNum, String searchType, String keyword) throws Exception {
	
	HashMap data = new HashMap();
	
	data.put("displayPost", displayPost);
	data.put("postNum", postNum);
	data.put("searchType", searchType);
	data.put("keyword", keyword);
	
	return sql.selectList(namespace + ".listPage", data);
}

//게시물 조회
@Override
public portalVO view(int bno) throws Exception {
	return sql.selectOne(namespace + ".view", bno);
}

//게시물 수정
@Override
public void modify(portalVO vo) throws Exception {
sql.update(namespace + ".modify", vo);
}

//게시물 삭제
@Override
public void delete(int bno) throws Exception {
	sql.delete(namespace + ".delete", bno);
}

//게시물 총 갯수
@Override
public int count(String searchType,String keyword) throws Exception {
	
	HashMap<String,String> data = new HashMap<String, String>();
	data.put("searchType", searchType);
	data.put("keyword", keyword);
	
	return sql.selectOne(namespace + ".count",data); 
}

//게시물 이전 페이지
@Override
public int viewPrev(int bno, String searchType, String keyword) throws Exception {
	HashMap data = new HashMap();
	data.put("bno", bno);
	data.put("searchType", searchType);
	data.put("keyword", keyword);
	return sql.selectOne(namespace + ".viewPrev", data);
}

//게시물 다음 페이지
@Override
public int viewNext(int bno, String searchType, String keyword) throws Exception {
	HashMap data = new HashMap();
	data.put("bno", bno);
	data.put("searchType", searchType);
	data.put("keyword", keyword);	
	return sql.selectOne(namespace + ".viewNext", data);
}

//게시물 처음글 확인
@Override
public int checkMIN(String searchType,String keyword) throws Exception {
	HashMap<String, String> data = new HashMap<String, String>();
	data.put("searchType", searchType);
	data.put("keyword", keyword);	
	return sql.selectOne(namespace + ".checkMIN", data);
}

//게시물 마지막글 확인
@Override
public int checkMAX(String searchType,String keyword) throws Exception {
	HashMap<String, String> data = new HashMap<String, String>();
	data.put("searchType", searchType);
	data.put("keyword", keyword);
	return sql.selectOne(namespace + ".checkMAX", data);
}

//조회수 증가
@Override
public void hitno(portalVO vo) throws Exception {
	sql.update(namespace + ".hitno", vo);
}

//첨부 파일 업로드(신규)
@Override
public void insertFile(Map<String, Object> map) throws Exception {
	sql.insert(namespace + ".insertFile", map);
}

//첨부 파일 업로드(수정)
@Override
public void updateFile(Map<String, Object> map) throws Exception {
	sql.insert(namespace + ".updateFile", map);
}

//첨부파일 업로드 정보 조회
@Override
public List<Map<String, Object>> fileList(int bno) throws Exception {
	return sql.selectList(namespace + ".fileList" , bno);
}

//첨부 파일 다운로드 정보 조회
@Override
public portalVO fileInfo(int file_no) throws Exception {
	return sql.selectOne(namespace + ".fileInfo", file_no);
}

//첨부 파일 삭제(게시물 수정 시 첨부 파일 삭제)
@Override
public void fileDelete(int file_no) throws Exception {
	sql.delete(namespace + ".fileDelete", file_no);
}

//첨부파일 정보 삭제(게시물 삭제 시 업로드된 파일 정보 내 fileCheck를 N으로 변경)
@Override
public void fileCheckChange(int bno) throws Exception {
	sql.update(namespace + ".fileCheckChange", bno);
}

//댓글 입력
@Override
public void replyInsert(portalVO vo) throws Exception {
	sql.insert(namespace + ".replyInsert", vo);	
}

//댓글 내용 보기
@Override
public List<portalVO> replyList(int bno) throws Exception {
	return sql.selectList(namespace + ".replyList", bno);
}

//댓글 수정
@Override
public void replyUpdate(portalVO vo) throws Exception {
	sql.update(namespace + ".replyUpdate", vo);
}

//댓글 삭제(게시물 조회 화면에서 개별 댓글 삭제)
@Override
public void replyDelete(int replyBno) throws Exception {
	sql.delete(namespace + ".replyDelete", replyBno);
}

//댓글 전체 삭제(게시물 삭제 시 전체 댓글 삭제)
@Override
public void replyDeleteList(int bno) throws Exception {
	sql.update(namespace + ".replyDeleteList", bno);
}

//좋아요/싫어요 목록 보기
@Override
public portalVO likeInfoList(Map<String,Object> map) throws Exception {
	return sql.selectOne(namespace + ".likeInfoList", map);
}

//좋아요/싫어요 등록
@Override
public void likeInsert(Map<String, Object> map) throws Exception {
	sql.insert(namespace + ".likeInsert", map);	
}

//좋아요/싫어요 수정
@Override
public void likeUpdate(Map<String, Object> map) throws Exception {
	sql.update(namespace + ".likeUpdate", map);	
}

//좋아요/싫어요 삭제
@Override
public void likeDelete(String userid) throws Exception {
	sql.delete(namespace + ".likeDelete", userid);	
}

//게시판 테이블에서 좋아요/싫어요 값만 조회
@Override
public portalVO listLikeCnt(int bno) throws Exception {
	return sql.selectOne(namespace + ".listLikeCnt", bno);
}

//게시판 테이블에서 좋아요/싫어요 값만 수정
@Override
public void listLikeUpdate(Map<String,Object> map) throws Exception {
	sql.update(namespace + ".listLikeUpdate", map);	
}

//로그인 확인
@Override
public portalVO loginCheck(portalVO vo) throws Exception {
	return sql.selectOne(namespace + ".loginCheck", vo);
}

//로그인 이력 정보 등록
@Override
public void mbrLoginInfo(Map<String, Object> map) throws Exception {
	sql.update(namespace + ".mbrLoginInfo", map);	
}

//아이디 중복 체크
@Override
public int idCheck(portalVO vo) throws Exception {
	return sql.selectOne(namespace + ".idCheck", vo);
}

//사용자 정보 등록
@Override
public void mbrInfoRegister(portalVO vo) throws Exception {
	sql.insert(namespace + ".mbrInfoRegister", vo);
}

//사용자 사진 등록
@Override
public void mbrPhotoRegister(Map<String, Object> map) throws Exception {
	sql.update(namespace + ".mbrPhotoRegister", map);
}

//사용자 사진 수정
@Override
public void mbrPhotoModify(Map<String, Object> map) throws Exception {
	sql.update(namespace + ".mbrPhotoModify", map);
}

//관리자용 사용자 목록 보기
@Override
public List<portalVO> mbrInfoList() throws Exception {
	
	return sql.selectList(namespace + ".mbrInfoList");
}

//사용자 정보 보기
@Override
public portalVO mbrInfoView(String userid) throws Exception {
	return sql.selectOne(namespace + ".mbrInfoView", userid); 
}

//사용자 정보 수정
@Override
public void mbrInfoModify(portalVO vo) throws Exception {
	sql.update(namespace + ".mbrInfoModify", vo);
}

//사용자 정보 삭제
@Override
public void mbrInfoDelete(String userid) throws Exception {
	sql.delete(namespace + ".mbrInfoDelete", userid);
}

//사용자 암호 수정
@Override
public void passwdModify(portalVO vo) throws Exception {
	sql.update(namespace + ".passwdModify", vo);
}

//사용자 암호 수정 날짜 변경
@Override
public void passwdModiDate(Map<String, Object> map) throws Exception {
	sql.update(namespace + ".passwdModiDate", map);
}
/*
 * //사용자 암호 수정날짜 보기 및 대조
 * 
 * @Override public portalVO passwdModiView(String userid) throws Exception {
 * return sql.selectOne(namespace + ".passwdModiView", userid); }
 */

//회원 탈퇴 시 사용자가 등록한 게시물 전체 삭제
@Override
public void mbrDeleteReplyList(String userid) throws Exception {
	sql.delete(namespace + ".mbrDeleteReplyList", userid);
}

//회원정보 삭제 시 tbl_file 내에 사용자가 등록한 파일 정보를 수정 : fileCheck를 N으로 수정
@Override
public void mbrFileCheck(String userid) throws Exception {
	sql.update(namespace + ".mbrFileCheck", userid);
}

/*
 * //아이디 찾기
 * 
 * @Override public int idFind(portalVO vo) throws Exception { return
 * sql.selectOne(namespace + ".idFind", vo); }
 */

}