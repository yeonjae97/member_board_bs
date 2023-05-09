package kr.co.sukbinggo.jsp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/*
 	< Filter >
 	HttpServlet을 쓰지 않음
 	
 	
 	
 */

@WebFilter("/*") // 모든 필터에 해당
public class EncodingFilter implements Filter{

	// doFilter 메서드가 강제된다.
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		
		// 로깅에 대한 필터도 많이 한다.
		chain.doFilter(request, response);
	}

}
