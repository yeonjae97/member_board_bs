package kr.co.sukbinggo.jsp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.sukbinggo.jsp.domain.Board;
import kr.co.sukbinggo.jsp.domain.Criteria;
import kr.co.sukbinggo.jsp.service.BoardService;
import kr.co.sukbinggo.jsp.service.BoardServiceImpl;
import kr.co.sukbinggo.jsp.util.ParamSolver;

@WebServlet("/board/view")
public class BoardViewController extends HttpServlet{
	private BoardService boardService = new BoardServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Criteria cri = ParamSolver.getParams(req, Criteria.class);
		req.setAttribute("cri", cri);
		Board board = boardService.get(Long.valueOf(req.getParameter("bno")));
//		System.out.println("boardController check : " + board);
		req.setAttribute("board",board);
		
		req.getRequestDispatcher("/WEB-INF/jsp/board/view.jsp").forward(req, resp);
	}
	
	
	
}
