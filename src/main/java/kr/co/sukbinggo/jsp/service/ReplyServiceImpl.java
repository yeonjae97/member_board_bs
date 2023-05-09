package kr.co.sukbinggo.jsp.service;

import java.util.List;

import kr.co.sukbinggo.jsp.dao.ReplyDao;
import kr.co.sukbinggo.jsp.domain.Reply;

public class ReplyServiceImpl implements ReplyService{
	private ReplyDao dao = new ReplyDao();
	
	@Override
	public Long register(Reply reply) {
		return (long)dao.insert(reply);
	}

	@Override
	public List<Reply> list(Long bno) {
		return dao.selectList(bno);
	}


	@Override
	public Reply get(Long rno) {
		return dao.selectOne(rno);
	}

	@Override
	public int remove(Long rno) {
		return dao.delete(rno);
	}

	
}
