package kr.co.sukbinggo.jsp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.sukbinggo.jsp.domain.Attach;
import kr.co.sukbinggo.jsp.util.ParamSolver;

@WebServlet("/download")
public class FileDownloader extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Attach attach = ParamSolver.getParams(req, Attach.class);
		System.out.println(attach);
		
		// File
		File file = attach.getFile();
		
		// Attach 클래스 단에서 코드를 줄일려고 getFile 메서드를 만들어서 코드의 재사용성을 활용함
//		new File(ParamSolver.UPLOAD_PATH, attach.getPath());
		String origin = attach.getOrigin();
		// 파일명 중 마지막 .의 위치
//		int dotIdx = origin.lastIndexOf(".");
//		
//		// 확장자를 담을 변수
//		String ext = "";
//		
//		// 확장자 구하기
//		if(dotIdx > -1) {
//			ext = origin.substring(dotIdx);
//		}
//		file = new File(file, attach.getUuid() + ext);
//		System.out.println(file);
//		System.out.println(file.exists());
		
		// 응답 제작
		resp.addHeader("Content-Disposition","attachment; filename=" + new String(attach.getOrigin().getBytes("utf-8"), "iso-8859-1"));
		
		// input
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		// byte 일차원 배열 형태로 한번에 가져오게 된다.
//		byte[] bytes = bis.readAllBytes();
		byte[] bytes = new byte[(int) file.length()];
		bis.read(bytes);
		// output
		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
		bos.write(bytes);
		
//		 bufferedStream은 자체적으로 close를 해주지만 신경 쓰이면 해도 된다.
		bis.close();
		bos.close();
		
	}
	
}
