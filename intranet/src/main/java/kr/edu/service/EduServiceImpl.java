package kr.edu.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.edu.dao.EduDAO;
import kr.edu.domain.EduVO;

@Service
public class EduServiceImpl implements EduService{
	
	 @Inject
	 private EduDAO dao;
	
	//게시물 작성
		 @Override
		 public void write(EduVO vo) throws Exception {
		 	dao.write(vo);
		 }

		 //게시물 목록 보기
		 @Override
		 public List<EduVO> listPage(int displayPost, int postNum, String searchType, String keyword) throws Exception {
		 	return dao.listPage(displayPost, postNum, searchType, keyword);
		 }

		 //게시물 조회
		 @Override
		 public EduVO view(int bno) throws Exception {
		 	return dao.view(bno);
		 }

		 //게시물 수정
		 @Override
		 public void modify(EduVO vo) throws Exception {
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
		 public void hitno(EduVO vo) throws Exception {
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
		 public EduVO fileInfo(int file_no) throws Exception {
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
		 public EduVO likeInfoList(Map<String,Object> map) throws Exception {
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
		 public EduVO listLikeCnt(int bno) throws Exception {
		 	return dao.listLikeCnt(bno);
		 }

		 //게시판 테이블에서 싫어요/좋아요 값만 수정
		 @Override
		 public void listLikeUpdate(Map<String,Object> map) throws Exception {
		 	dao.listLikeUpdate(map);	
		 }

		 //댓글 등록
		 @Override
		 public void replyInsert(EduVO vo) throws Exception {
		 	dao.replyInsert(vo);
		 }

		 //댓글 보기
		 @Override
		 public List<EduVO> replyList(int bno) throws Exception {
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
		 public void replyUpdate(EduVO vo) throws Exception {
		 	dao.replyUpdate(vo);	
		 }

		
}
