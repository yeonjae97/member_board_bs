package kr.co.sukbinggo.jsp.service;

import java.util.List;

import kr.co.sukbinggo.jsp.domain.Criteria;
import kr.co.sukbinggo.jsp.domain.Board;

public interface BoardService {
	
	// CRUD
	Long register(Board board);
	
	Board get(Long bno);
	
	List<Board> list(Criteria cri);
	int listCount(Criteria cri);
	
	void modify(Board board);
	
	void remove(Long bno);
}
