package kr.co.sukbinggo.jsp.service;

import java.util.List;

import org.mindrot.bcrypt.BCrypt;

import kr.co.sukbinggo.jsp.dao.BoardDao;
import kr.co.sukbinggo.jsp.dao.MemberDao;
import kr.co.sukbinggo.jsp.dao.ReplyDao;
import kr.co.sukbinggo.jsp.domain.Member;

public class MemberServiceImpl implements MemberService{
	private MemberDao memberDao = new MemberDao();
	private BoardDao boardDao = new BoardDao();
	private ReplyDao replyDao = new ReplyDao();
	@Override
	public void register(Member member) {
		member.setPw(BCrypt.hashpw(member.getPw(),BCrypt.gensalt()));
		memberDao.insert(member);
	}

	@Override
	public int login(String id, String pw) {
		Member member = memberDao.selectOne(id);
		if(member == null) {
			return 2;
		}
		else if(!BCrypt.checkpw(pw, member.getPw())) {
			return 3;
		}
		else {
			return 1;
		}
	}

	@Override
	public Member get(String id) {
		// TODO Auto-generated method stub
		return memberDao.selectOne(id);
	}
	
	
	// 회원 탈퇴는 3개가 필요함
	public void remove(String id) {
		// 작성한 게시글에 대한 아이디 변경
		// 작성한 댓글의 아이디 변경
		// 회원 테이블 내에서 삭제
	}

	@Override
	public List<Member> list() {
		// TODO Auto-generated method stub
		return memberDao.selectList();
	}

	@Override
	public void modify(Member member) {
		memberDao.update(member);
		
	}

	@Override
	public void modifyPw(Member member) {
		// 암호화 처리
		member.setPw(BCrypt.hashpw(member.getPw(),BCrypt.gensalt()));
		// DB반영
		memberDao.updatePw(member);
		
	}

	@Override
	public void remove(Member member) {
		// 게시글 처리
		boardDao.updateWriterToNull(member.getId());
		// 댓글 처리
		replyDao.updateWriterToNull(member.getId());
		
		// 회원 탈퇴
		memberDao.delete(member);
	}
	
}
