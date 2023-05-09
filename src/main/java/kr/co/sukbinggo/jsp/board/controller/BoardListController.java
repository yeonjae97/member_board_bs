package kr.co.sukbinggo.jsp.board.controller;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.sukbinggo.jsp.domain.Criteria;
import kr.co.sukbinggo.jsp.domain.PageDto;
import kr.co.sukbinggo.jsp.service.BoardService;
import kr.co.sukbinggo.jsp.service.BoardServiceImpl;
import kr.co.sukbinggo.jsp.util.ParamSolver;

@WebServlet("/board/list")
public class BoardListController extends HttpServlet{
	private BoardService boardService = new BoardServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Criteria criteria = ParamSolver.getParams(req, Criteria.class);
		List<String> categories = new ArrayList<>(Arrays.asList("공지사항","자유게시판","자료실","갤러리"));		
		req.setAttribute("categories", categories);
		req.setAttribute("boards", boardService.list(criteria));
		req.setAttribute("page", new PageDto(boardService.listCount(criteria), criteria));
		
		if(criteria.getCategory() != 4){
			
			req.getRequestDispatcher("/WEB-INF/jsp/board/list.jsp").forward(req, resp);
		}else{
			
			req.getRequestDispatcher("/WEB-INF/jsp/board/gallery.jsp").forward(req, resp);
		}
		
	}
	
}
