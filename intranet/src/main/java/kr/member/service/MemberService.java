package kr.member.service;

import java.util.List;
import java.util.Map;

import kr.member.domain.MemberVO;

public interface MemberService {
	
	
		 
		//로그인 
		public MemberVO loginCheck(MemberVO vo) throws Exception;

		//아이디 중복 체크
		public int idCheck(MemberVO vo) throws Exception;
			 
		//사용자 목록 보기
		public List<MemberVO> mbrListInfo() throws Exception;
		 
		//사용자 정보 보기
		public MemberVO mbrInfoView(String userid) throws Exception; 
		 
		//사용자 등록
		public void mbrInfoRegister(MemberVO vo) throws Exception;
		
		//사용자 사진 등록
		public void mbrPhotoRegister(Map<String, Object> map) throws Exception;
		
		//사용자 사진 수정
		public void mbrPhotoModify(Map<String, Object> map) throws Exception;
		
		//사용자 로그인 이력 정보 등록
		public void mbrLoginInfo(Map<String, Object> map) throws Exception;
		
		//사용자 정보 수정
		public void mbrInfoModify(MemberVO vo) throws Exception;
		
		//사용자 암호 수정
		public void passwdModify(MemberVO vo) throws Exception;
		
		//사용자 암호 수정일 등록
		public void passwdModiDate(Map<String, Object> map) throws Exception;
		
		//사용자 정보 삭제(회원탈퇴)
		public void mbrInfoDelete(String userid) throws Exception;
		
		//회원 탈퇴 시 사용자가 등록한 게시물 전체 삭제
		 public void mbrDeleteReplyList(String userid) throws Exception;
		 
		//회원정보 삭제 시 tbl_file 내에 사용자가 등록한 파일 정보를 수정 : fileCheck를 N으로 수정
		 public void mbrFileCheck(String userid) throws Exception;
		 
		//좋아요/싫어요 삭제
		 public void likeDelete(String userid) throws Exception;


}
