package kr.co.sukbinggo.jsp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.co.sukbinggo.jsp.domain.Reply;
import kr.co.sukbinggo.jsp.service.ReplyService;
import kr.co.sukbinggo.jsp.service.ReplyServiceImpl;
import kr.co.sukbinggo.jsp.util.ParamSolver;
import static kr.co.sukbinggo.jsp.util.ParamSolver.*;

@WebServlet("/reply")
public class ReplyController  extends HttpServlet{
	private ReplyService service = new ReplyServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long bno = Long.parseLong(req.getParameter("bno"));
		List<Reply> replies = service.list(bno);
		Gson gson = new Gson();
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gson.toJson(replies);
//		req.getRequestDispatcher("/")
//		JSON.stringify를 뜻함
		resp.setContentType("application/json; charset=utf8");
		resp.getWriter().append(json);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인?
//		isLogin(req);
	
		// 그래서 그게 내꺼냐?
		Long rno = Long.valueOf(req.getParameter("rno"));
		PrintWriter out = resp.getWriter();
		Reply reply = service.get(rno);
//		System.out.println(reply);
//		System.out.println(((Member)req.getSession().getAttribute("member")).getId());
//		System.out.println(isMine(req, service.get(rno).getWriter()));
		// writer와 내 정보랑 공유할 수 있다.
		if(reply != null && isMine(req, service.get(rno).getWriter())) {
			// remove의 반환값이 1이 나오면 삭제 성공! 
			out.print(service.remove(rno));
		}else {
			// 아니면 실패...
			out.print(0);
		}
	}
	
	// doDelete에서 작성했던 (구)기록들
//			service.remove(rno);
//			service.remove(Long.valueOf(req.getParameter("rno")));
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reply reply = ParamSolver.getParams(req, Reply.class);
		service.register(reply);
	}
	// Http Method
	// GET, POST, PUT(PATCH), DELETE
	
}
