package kr.co.sukbinggo.jsp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kr.co.sukbinggo.jsp.dao.AttachDao;
import kr.co.sukbinggo.jsp.dao.BoardDao;
import kr.co.sukbinggo.jsp.dao.ReplyDao;
import kr.co.sukbinggo.jsp.domain.Criteria;
import kr.co.sukbinggo.jsp.domain.Attach;
import kr.co.sukbinggo.jsp.domain.Board;
public class BoardServiceImpl implements BoardService{
	private BoardDao dao = new BoardDao();
	private AttachDao attachDao = new AttachDao();
	private ReplyDao replyDao = new ReplyDao();
	@Override
	public Long register(Board board) {
		// 첨부파일에 대한 insert를 하기 위해서 bno가 필요하다.
//		System.out.println(board);
		// 글 작성 후 글번호 지정
		Long bno = (long)dao.insert(board);
		
		// 어디에서 실행되고 있는지 명시적으로 적어줘야 한다.
		System.out.println("boardService.register() ::" + bno);
		// 
		for(Attach attach : board.getAttachs()) {
			
			// attach 부착물 입장에서 글번호를 알아야하므로 bno를 setter 해야한다.
			attach.setBno(bno);
			attachDao.insert(attach);
//			System.out.println(attach);
		}
		return bno;
	}

	@Override
	public Board get(Long bno) {
		dao.increaseHitCount(bno);
		Board board = dao.selectOne(bno);
		board.setAttachs(attachDao.selectList(bno));
//		System.out.println("boardService get : " + board);
		return board;
	}

	@Override
	public List<Board> list(Criteria cri) {
		List<Board> list = dao.selectList(cri);
		if(cri.getCategory() == 4){
//			List<Map<String, String>> gallerys = dao.selectListGallery(cri);
			
			// map(Map<String,String> a -> Board b)
			list = dao.selectListGallery(cri).stream().map(a ->{
				Board board = new Board();
				board.setBno(Long.valueOf(a.get("bno")));
				board.setTitle(a.get("title"));
				board.setWriter(a.get("writer"));
				
//				String uuid = a.get("uuid");
				String fullpath = a.get("fullpath");
//				attach.setPath(a.get(key));
				if(fullpath != null){
//					Attach attach = new Attach();
//					attach.setPath(path);
//					attach.setUuid(uuid);
//					board.setAttachs(Arrays.asList(attach));
					board.setContent(fullpath);
				}
				return board;
			}).collect(Collectors.toList());
		}
		return list;
//		return dao.selectList(cri).stream().map(board -> {
//			board.setAttachs(attachDao.selectList(board.getBno()));
//			return board;
//		}).collect(Collectors.toList());
	}

	@Override
	public void modify(Board board) {
		dao.update(board);
	}

	@Override
	public void remove(Long bno) {
		// 파일시스템에 존재하는 파일 삭제 ( 람다식을 활용해서 2줄을 1줄로 줄인다. ) - 1
		attachDao.selectList(bno).forEach(attach -> {
			attach.getFile().delete();
			if(attach.isImage()) {
				attach.getFile(true).delete();
			}
		});
		// 첨부 목록 삭제 ( 댓글 삭제도 이런 방식으로 해도 된다. ) - 2
		attachDao.delete(bno);
		
		// 댓글 삭제
		replyDao.deleteByBno(bno);
		
		// tbl_board 삭제 - 3
		dao.delete(bno);
	}

	@Override
	public int listCount(Criteria cri) {
		return dao.selectListCount(cri);
	}

	
}
