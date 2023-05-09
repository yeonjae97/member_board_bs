package kr.co.sukbinggo.jsp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.sukbinggo.jsp.domain.Criteria;
import kr.co.sukbinggo.jsp.service.BoardService;
import kr.co.sukbinggo.jsp.service.BoardServiceImpl;
import kr.co.sukbinggo.jsp.util.ParamSolver;
import kr.co.sukbinggo.jsp.domain.Board;

import static kr.co.sukbinggo.jsp.util.ParamSolver.*;

@WebServlet("/board/modify")
public class BoardModifyController extends HttpServlet{
	private BoardService boardService = new BoardServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getSession().getAttribute("member") == null) {
			resp.sendRedirect(req.getContextPath() + "/member/login");
			return;
		}
		req.setAttribute("cri", ParamSolver.getParams(req, Criteria.class));
		req.setAttribute("board", boardService.get(Long.valueOf(req.getParameter("bno"))));
		req.getRequestDispatcher("/WEB-INF/jsp/board/modify.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 이미 util에 ParamSolver클래스를 자체적으로 만들어 놨기 때문에 필요없어짐
//		Board board = new Board(req.getParameter("title"), req.getParameter("content"),req.getParameter("writer"));
//		board.setBno(Long.valueOf(req.getParameter("bno")));
		Criteria cri = ParamSolver.getParams(req, Criteria.class);
		Board board = ParamSolver.getParams(req, Board.class);
		boardService.modify(board);
		// 글번호를 요청해야지만 해당 번호의 view를 볼 수 있다.
		resp.sendRedirect("view?bno=" + board.getBno() + "&" + cri.getFullQueryString());
	}
	
	
	
}
