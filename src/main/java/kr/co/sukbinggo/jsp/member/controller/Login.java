package kr.co.sukbinggo.jsp.member.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.sukbinggo.jsp.service.MemberService;
import kr.co.sukbinggo.jsp.service.MemberServiceImpl;

@WebServlet("/member/login")
public class Login extends HttpServlet {
	private MemberService memberService = new MemberServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// forwarding
		req.getRequestDispatcher("/WEB-INF/jsp/member/login.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		String msg = "";
		String redirectStr = req.getContextPath() + "/";
		String href = req.getParameter("href");
		switch(memberService.login(id, pw)) {
		case 1: 
			req.getSession().setAttribute("member", memberService.get(id));
			if(href != null) {
				redirectStr = href;
			}
			break;
		case 2: 
			msg = "해당 아이디가 없습니다.";
			msg = URLEncoder.encode(msg, "utf-8");
			redirectStr += "member/login?msg="+msg;
			if(href != null) {
				redirectStr += "&href=" + URLEncoder.encode(href, "utf-8");
			}
			break;
		case 3: 
			msg = "비밀번호가 일치하지 않습니다.";
			msg = URLEncoder.encode(msg, "utf-8");
			redirectStr += "member/login?msg="+msg;
			if(href != null) {
				redirectStr += "&href=" + URLEncoder.encode(href, "utf-8");
			}
		}
		resp.sendRedirect(redirectStr);
	}
}
