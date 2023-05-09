package kr.co.sukbinggo.jsp.service;

import java.util.List;

import kr.co.sukbinggo.jsp.domain.Criteria;
import kr.co.sukbinggo.jsp.domain.Reply;

public interface ReplyService {
	
	// CRUD
	Long register(Reply reply);

	
	List<Reply> list(Long bno);

	Reply get(Long rno);
	
	int remove(Long rno);
}
