package kr.co.sukbinggo.jsp.service;

import java.util.List;

import kr.co.sukbinggo.jsp.domain.Member;

public interface MemberService {

	// 회원가입
	void register(Member member);
	
	// 로그인
	int login(String id, String pw);
	
	// 회원 단일 조회
	Member get(String id);
	
	// 회원목록 조회
	List<Member> list();
	// 회원 정보 수정
	void modify(Member member);
	void modifyPw(Member member);
	
	// 탈퇴
	void remove(Member member);
}
