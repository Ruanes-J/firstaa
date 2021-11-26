package kr.member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.member.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Inject
	private SqlSession sql;
	private static String namespace = "kr.member.mappers.member";


	

	//로그인 확인
	@Override
	public MemberVO loginCheck(MemberVO vo) throws Exception {
		return sql.selectOne(namespace + ".loginCheck", vo);
	}

	//로그인 이력 정보 등록
	@Override
	public void mbrLoginInfo(Map<String, Object> map) throws Exception {
		sql.update(namespace + ".mbrLoginInfo", map);	
	}

	//아이디 중복 체크
	@Override
	public int idCheck(MemberVO vo) throws Exception {
		return sql.selectOne(namespace + ".idCheck", vo);
	}

	//사용자 정보 등록
	@Override
	public void mbrInfoRegister(MemberVO vo) throws Exception {
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
	public List<MemberVO> mbrInfoList() throws Exception {
		
		return sql.selectList(namespace + ".mbrInfoList");
	}

	//사용자 정보 보기
	@Override
	public MemberVO mbrInfoView(String userid) throws Exception {
		return sql.selectOne(namespace + ".mbrInfoView", userid); 
	}

	//사용자 정보 수정
	@Override
	public void mbrInfoModify(MemberVO vo) throws Exception {
		sql.update(namespace + ".mbrInfoModify", vo);
	}

	//사용자 정보 삭제
	@Override
	public void mbrInfoDelete(String userid) throws Exception {
		sql.delete(namespace + ".mbrInfoDelete", userid);
	}

	//사용자 암호 수정
	@Override
	public void passwdModify(MemberVO vo) throws Exception {
		sql.update(namespace + ".passwdModify", vo);
	}

	//사용자 암호 수정 날짜 변경
	@Override
	public void passwdModiDate(Map<String, Object> map) throws Exception {
		sql.update(namespace + ".passwdModiDate", map);
	}
	
	//회원정보 삭제 시 tbl_file 내에 사용자가 등록한 파일 정보를 수정 : fileCheck를 N으로 수정
	@Override
	public void mbrFileCheck(String userid) throws Exception {
		sql.update(namespace + ".mbrFileCheck", userid);
	}
	
	//회원 탈퇴 시 사용자가 등록한 게시물 전체 삭제
	@Override
	public void mbrDeleteReplyList(String userid) throws Exception {
		sql.delete(namespace + ".mbrDeleteReplyList", userid);
	}
	
	//좋아요/싫어요 삭제
	@Override
	public void likeDelete(String userid) throws Exception {
		sql.delete(namespace + ".likeDelete", userid);	
	}





}
