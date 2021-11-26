package kr.schedule.dao;

import java.util.List;
import java.util.Map;

import kr.schedule.domain.ScheduleVO;



public interface ScheduleDAO {
	//게시물 작성
		public void write(ScheduleVO vo) throws Exception;

		//첨부파일 업로드(게시물 신규 등록시 파일 업로드)
		public void insertFile(Map<String, Object> map) throws Exception;

		//첨부파일 업로드(게시물 수정 시 파일 업로드)
		public void updateFile(Map<String, Object> map) throws Exception;

		//첨부파일 업로드 정보 조회
		public List<Map<String, Object>> fileList(int bno) throws Exception;

		//첨부파일 다운로드 정보 조회
		public ScheduleVO fileInfo(int file_no) throws Exception;

		//첨부파일 삭제(게시물 수정 시 등록된 파일 삭제)
		public void fileDelete(int file_no) throws Exception;

		//첨부파일 정보 삭제(게시물 삭제 시 업로드된 파일 정보 내 fileCheck를 N으로 변경)
		public void fileCheckChange(int bno) throws Exception;

		//회원정보 삭제 시 tbl_file 내에 사용자가 등록한 파일 정보를 수정 : fileCheck를 N으로 수정
		public void mbrFileCheck(String userid) throws Exception;

		//게시물 조회
		public ScheduleVO view(int bno) throws Exception;

		//게시물 이전 페이지
		public int viewPrev(int bno, String searchType, String keyword) throws Exception;

		//게시물 다음 페이지
		public int viewNext(int bno, String searchType, String keyword) throws Exception;

		//게시물 처음글 확인
		public int checkMIN(String searchType,String keyword) throws Exception;

		//게시물 마지막글 확인
		public int checkMAX(String searchType,String keyword) throws Exception;

		//게시물 수정
		public void modify(ScheduleVO vo) throws Exception;

		//조회수 증가
		public void hitno(ScheduleVO vo) throws Exception;

		//게시물 삭제
		public void delete(int bno) throws Exception;

		//게시물 총 갯수
		public int count(String searchType,String keyword) throws Exception;

		//게시물 목록 보기
		public List<ScheduleVO> listPage(int displayPost, int postNum, String searchType, String keyword) throws Exception;

		//댓글 등록
		public void replyInsert(ScheduleVO vo) throws Exception;

		//댓글 보기
		public List<ScheduleVO> replyList(int bno) throws Exception;

		//댓글 삭제(게시물 조회 화면에서 개별 댓글 삭제)
		public void replyDelete(int replyBno) throws Exception;

		//댓글 전체 삭제(게시물 삭제 시 전체 댓글 삭제)
		public void replyDeleteList(int bno) throws Exception;

		//회원 탈퇴 시 사용자가 등록한 게시물 전체 삭제
		public void mbrDeleteReplyList(String userid) throws Exception;

		//댓글 수정
		public void replyUpdate(ScheduleVO vo) throws Exception;

		//게시판 테이블에서 좋아요/싫어요 값만 가져 오기
		public ScheduleVO listLikeCnt(int bno) throws Exception;

		//게시판 테이블 내의 좋아요/싫어요 값만 수정
		public void listLikeUpdate(Map<String, Object> map) throws Exception;

		//좋아요/싫어요 목록 보기
		public ScheduleVO likeInfoList(Map<String,Object> map) throws Exception;

		//좋아요/싫어요 등록
		public void likeInsert(Map<String,Object> map) throws Exception;

		//좋아요/싫어요 수정
		public void likeUpdate(Map<String,Object> map) throws Exception;

		//좋아요/싫어요 삭제
		public void likeDelete(String userid) throws Exception;

}
