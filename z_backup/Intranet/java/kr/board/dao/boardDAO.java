package kr.board.dao;

import java.util.List;
import java.util.Map;

import kr.board.domain.boardVO;

public interface boardDAO {

//게시물 작성
	public void write(boardVO vo) throws Exception;

//첨부파일 업로드(게시물 신규 등록시 파일 업로드)
	public void insertFile(Map<String, Object> map) throws Exception;

//첨부파일 업로드(게시물 수정 시 파일 업로드)
	public void updateFile(Map<String, Object> map) throws Exception;

//첨부파일 업로드 정보 조회
	public List<Map<String, Object>> fileList(int bno) throws Exception;

//첨부파일 다운로드 정보 조회
	public boardVO fileInfo(int file_no) throws Exception;

//첨부파일 삭제(게시물 수정 시 등록된 파일 삭제)
	public void fileDelete(int file_no) throws Exception;

//첨부파일 정보 삭제(게시물 삭제 시 업로드된 파일 정보 내 fileCheck를 N으로 변경)
	public void fileCheckChange(int bno) throws Exception;

//회원정보 삭제 시 tbl_file 내에 사용자가 등록한 파일 정보를 수정 : fileCheck를 N으로 수정
	public void mbrFileCheck(String userid) throws Exception;

//게시물 조회
	public boardVO view(int bno) throws Exception;

//게시물 이전 페이지
	public int viewPrev(int bno, String searchType, String keyword) throws Exception;

//게시물 다음 페이지
	public int viewNext(int bno, String searchType, String keyword) throws Exception;

//게시물 처음글 확인
	public int checkMIN(String searchType, String keyword) throws Exception;

//게시물 마지막글 확인
	public int checkMAX(String searchType, String keyword) throws Exception;

//게시물 수정
	public void modify(boardVO vo) throws Exception;

//조회수 증가
	public void hitno(boardVO vo) throws Exception;

//게시물 삭제
	public void delete(int bno) throws Exception;

//게시물 총 갯수
	public int count(String searchType, String keyword) throws Exception;

//게시물 목록 보기
	public List<boardVO> listPage(int displayPost, int postNum, String searchType, String keyword) throws Exception;

//댓글 등록
	public void replyInsert(boardVO vo) throws Exception;

//댓글 보기
	public List<boardVO> replyList(int bno) throws Exception;

//댓글 삭제(게시물 조회 화면에서 개별 댓글 삭제)
	public void replyDelete(int replyBno) throws Exception;

//댓글 전체 삭제(게시물 삭제 시 전체 댓글 삭제)
	public void replyDeleteList(int bno) throws Exception;

//회원 탈퇴 시 사용자가 등록한 게시물 전체 삭제
	public void mbrDeleteReplyList(String userid) throws Exception;

//댓글 수정
	public void replyUpdate(boardVO vo) throws Exception;

//게시판 테이블에서 좋아요/싫어요 값만 가져 오기
	public boardVO listLikeCnt(int bno) throws Exception;

//게시판 테이블 내의 좋아요/싫어요 값만 수정
	public void listLikeUpdate(Map<String, Object> map) throws Exception;

//좋아요/싫어요 목록 보기
	public boardVO likeInfoList(Map<String, Object> map) throws Exception;

//좋아요/싫어요 등록
	public void likeInsert(Map<String, Object> map) throws Exception;

//좋아요/싫어요 수정
	public void likeUpdate(Map<String, Object> map) throws Exception;

//좋아요/싫어요 삭제
	public void likeDelete(String userid) throws Exception;

//사용자 등록
	public void mbrInfoRegister(boardVO vo) throws Exception;

//사용자 사진 등록
	public void mbrPhotoRegister(Map<String, Object> map) throws Exception;

//사용자 사진 수정
	public void mbrPhotoModify(Map<String, Object> map) throws Exception;

//사용자 로그인 기록 등록
	public void mbrLoginInfo(Map<String, Object> map) throws Exception;

//사용자 목록 보기
	public List<boardVO> mbrInfoList() throws Exception;

//사용자정보 보기
	public boardVO mbrInfoView(String userid) throws Exception;

//사용자정보 수정
	public void mbrInfoModify(boardVO vo) throws Exception;

//사용자 암호 수정
	public void passwdModify(boardVO vo) throws Exception;

//사용자 암호 수정일 등록
	public void passwdModiDate(Map<String, Object> map) throws Exception;

//사용자 암호 수정일 보기 추가 jehun
	public boardVO passwdModiView(String userid) throws Exception;
	
//사용자정보 삭제
	public void mbrInfoDelete(String userid) throws Exception;

//로그인 
	public boardVO loginCheck(boardVO vo) throws Exception;

//아이디 중복 체크
	public int idCheck(boardVO vo) throws Exception;
// 아이디 찾기
	public int idFind(boardVO vo) throws Exception;

}
