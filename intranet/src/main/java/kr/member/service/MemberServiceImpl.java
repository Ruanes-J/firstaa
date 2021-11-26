package kr.member.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.member.dao.MemberDAO;
import kr.member.domain.MemberVO;


@Service
public class MemberServiceImpl implements MemberService {
	
	 @Inject
	 private MemberDAO dao;

	

	 //로그인
	 @Override
	 public MemberVO loginCheck(MemberVO vo) throws Exception {
	 	return dao.loginCheck(vo);
	 }

	 //아이디 중복 체크
	 @Override
	 public int idCheck(MemberVO vo) throws Exception {
	 	return dao.idCheck(vo);
	 }

	 //사용자 등록
	 @Override
	 public void mbrInfoRegister(MemberVO vo) throws Exception {
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
	 public List<MemberVO> mbrListInfo() throws Exception {
	 	return dao.mbrInfoList();
	 }

	 //사용자 정보 보기
	 @Override
	 public MemberVO mbrInfoView(String userid) throws Exception {
	 	return dao.mbrInfoView(userid);
	 }

	 //사용자 정보 수정
	 @Override
	 public void mbrInfoModify(MemberVO vo) throws Exception {
	 	dao.mbrInfoModify(vo);
	 }

	 //사용자 암호 수정
	 @Override
	 public void passwdModify(MemberVO vo) throws Exception {
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
	 
	//좋아요/싫어요 삭제
	 @Override
	 public void likeDelete(String userid) throws Exception {
	 	dao.likeDelete(userid);
	 }




}
