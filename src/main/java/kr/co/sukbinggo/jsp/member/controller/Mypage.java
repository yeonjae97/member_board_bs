package kr.co.sukbinggo.jsp.member.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import kr.co.sukbinggo.jsp.domain.Member;
import kr.co.sukbinggo.jsp.service.MemberService;
import kr.co.sukbinggo.jsp.service.MemberServiceImpl;
import kr.co.sukbinggo.jsp.util.ParamSolver;

@WebServlet("/member/mypage")
public class Mypage extends HttpServlet {
	private MemberService memberService = new MemberServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object member = req.getSession().getAttribute("member");
		if(req.getSession().getAttribute("member") == null){
			resp.sendRedirect(req.getContextPath() + "/member/login");
			return;
		}
		// forwarding
		req.setAttribute("member", memberService.get(((Member)member).getId()));
		req.getRequestDispatcher("/WEB-INF/jsp/member/mypage.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("member") == null){
			resp.sendRedirect(req.getContextPath() + "/member/login");
			return;
		}
		Member member = ParamSolver.getParams(req, Member.class);
		System.out.println(member);
		
		memberService.modify(member);
		// 회원 수정이 완료되면 해야할 일이 있음 ( 세션 유지!!! )
		req.getSession().setAttribute("member", member);
		resp.sendRedirect(req.getContextPath() + "/");;
	}
}
