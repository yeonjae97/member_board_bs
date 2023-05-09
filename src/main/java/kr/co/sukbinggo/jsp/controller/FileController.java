package kr.co.sukbinggo.jsp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/file")
public class FileController extends HttpServlet{

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("file.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
//		String id = req.getParameter("id");
//		String file = req.getParameter("file");
//		
		// MultipartRequest 형식
		MultipartRequest multipartRequest = new MultipartRequest(req, "C:\\upload", 2 * 1024 * 1024, "utf-8", new DefaultFileRenamePolicy());
		String id = multipartRequest.getParameter("id");
		
		// input의 file 타입이었을 때 getParameter로 처리를 못함
//		String file = multipartRequest.getParameter("file");
		System.out.println(id);
//		System.out.println(file);
		String origin = multipartRequest.getOriginalFileName("file");
		String realName = multipartRequest.getFilesystemName("file");
//		multipartRequest.getFile("file").
		System.out.println(origin);
		System.out.println(realName);
		
//		File file = new File("C:\\upload\reply1.txt");
	}
	
}
