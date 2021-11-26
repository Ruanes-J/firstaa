package kr.edu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.edu.domain.EduVO;



@Repository
public class EduDAOImpl implements EduDAO {
	
	@Inject
	private SqlSession sql;
	private static String namespace = "kr.edu.mappers.edu";
	// 게시물 작성
		@Override
		public void write(EduVO vo) throws Exception {
			sql.insert(namespace + ".write", vo);
		}

		//게시물 목록 보기
		@Override
		public List<EduVO> listPage(int displayPost, int postNum, String searchType, String keyword) throws Exception {
			
			HashMap data = new HashMap();
			
			data.put("displayPost", displayPost);
			data.put("postNum", postNum);
			data.put("searchType", searchType);
			data.put("keyword", keyword);
			
			return sql.selectList(namespace + ".listPage", data);
		}

		//게시물 조회
		@Override
		public EduVO view(int bno) throws Exception {
			return sql.selectOne(namespace + ".view", bno);
		}

		//게시물 수정
		@Override
		public void modify(EduVO vo) throws Exception {
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
		public void hitno(EduVO vo) throws Exception {
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
		public EduVO fileInfo(int file_no) throws Exception {
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
		public void replyInsert(EduVO vo) throws Exception {
			sql.insert(namespace + ".replyInsert", vo);	
		}

		//댓글 내용 보기
		@Override
		public List<EduVO> replyList(int bno) throws Exception {
			return sql.selectList(namespace + ".replyList", bno);
		}

		//댓글 수정
		@Override
		public void replyUpdate(EduVO vo) throws Exception {
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
		public EduVO likeInfoList(Map<String,Object> map) throws Exception {
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
		public EduVO listLikeCnt(int bno) throws Exception {
			return sql.selectOne(namespace + ".listLikeCnt", bno);
		}

		//게시판 테이블에서 좋아요/싫어요 값만 수정
		@Override
		public void listLikeUpdate(Map<String,Object> map) throws Exception {
			sql.update(namespace + ".listLikeUpdate", map);	
		}
		
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


	

}
