package kr.edu.service;

import java.util.List;
import java.util.Map;

import kr.edu.domain.EduVO;


public interface EduService {
	
	//게시물 작성
	 public void write(EduVO vo) throws Exception;
	 
	//게시물 목록 보기
	 public List<EduVO> listPage(int displayPost, int postNum, String searchType, String keyword) throws Exception;
	 
	 // 게시물 조회
	 public EduVO view(int bno) throws Exception;
	 
	 // 게시물 삭제
	 public void delete(int bno) throws Exception;
	 
	// 게시물 수정
	 public void modify(EduVO vo) throws Exception;
	 

	// 게시물 총 갯수
	 public int count(String searchType,String keyword) throws Exception;

	// 게시물 이전 페이지
	 public int viewPrev(int bno,String searchType,String keyword) throws Exception;
		 
	// 게시물 다음 페이지
	 public int viewNext(int bno,String searchType,String keyword) throws Exception;
		 
	// 게시물 처음글 확인
	 public int checkMIN(String searchType,String keyword) throws Exception; 
		 
	// 게시물 마지막글 확인
    	public int checkMAX(String searchType,String keyword) throws Exception;  

	// 조회수 증가
	 public void hitno(EduVO vo) throws Exception;	 
		 
	//첨부파일 업로드(신규) 정보 입력
	 public void insertFile(Map<String, Object> map) throws Exception;

	//첨부파일 업로드(수정) 정보 입력
	 public void updateFile(Map<String, Object> map) throws Exception;
	 
	//첨부 파일 업로드 정보 조회
	 public List<Map<String, Object>> fileList(int bno) throws Exception;
	 
	//첨부 파일 다운로드 정보 조회
	 public EduVO fileInfo(int file_no) throws Exception;
	 
	 //첨부 파일 삭제(게시물 수정 시 업로드 파일 삭제)
	 public void fileDelete(int file_no) throws Exception;
	 
	 //첨부 파일 삭제(게시물 삭제 시 tbl_file 내의 fileCheck를 N으로 변경)
	 public void fileCheckChange(int bno) throws Exception;
	 
	//댓글 등록
	 public void replyInsert(EduVO vo) throws Exception;
	 
	//댓글 보기
	 public List<EduVO> replyList(int bno) throws Exception;
	 
	 //댓글 삭제(게시물 조회 화면에서 개별 댓글 삭제)
	 public void replyDelete(int replyBno) throws Exception;
	 
	 //댓글 전체 삭제(게시물 삭제 시 댓글 전체 삭제)
	 public void replyDeleteList(int bno) throws Exception;
	 
	 //댓글 수정
	 public void replyUpdate(EduVO vo) throws Exception;
	 
	 //게시판 테이블에서 싫어요/좋아요 값만 조회
	 public EduVO listLikeCnt(int bno) throws Exception;

	//게시판 테이블 내의 좋아요/싫어요 값만 수정
	 public void listLikeUpdate(Map<String,Object> map) throws Exception;
	 
	//좋아요/싫어요 목록 보기
	 public EduVO likeInfoList(Map<String,Object> map) throws Exception;
	 
   	//좋아요/싫어요 등록
	 public void likeInsert(Map<String,Object> map) throws Exception;

	//좋아요/싫어요 수정
	 public void likeUpdate(Map<String,Object> map) throws Exception;
	 
	//좋아요/싫어요 삭제
	 public void likeDelete(String userid) throws Exception;

}
