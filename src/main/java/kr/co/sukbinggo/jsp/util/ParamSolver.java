package kr.co.sukbinggo.jsp.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import kr.co.sukbinggo.jsp.domain.Attach;
import kr.co.sukbinggo.jsp.domain.Board;
import kr.co.sukbinggo.jsp.domain.Member;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;

public class ParamSolver {
	// 전역 변수
	public static final String UPLOAD_PATH = "c:/upload";
	
	// jsp내에 있는 param을 받아오기 위해서 첫번째 인자에 HttpServletRequest를 선언하고,
	// 두 번째는 Class를 선언하여 인자로 받게 하여 class내에 들어있는 필드 및 메서드를 호출한다.
	public static <T> T getParams(HttpServletRequest req, Class<T> clazz) { 
		
		
		// 인스턴스 생성
		T t = null;
		try {
			t = clazz.getDeclaredConstructor().newInstance(null);
			// 선언 필드에 대한 타입 및 이름 체크
			
			// method
			Field[] fields = clazz.getDeclaredFields();

			// 해당 클래스의 정보를 향상된 for문을 이용해 모두 출력시킬려 하는데
			for (Field f : fields) {		
				// 그 중에 int형인 클래스 정보를 추출할려고 할 것이다.
				String fieldName = f.getName();
				String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Method[] methods = clazz.getDeclaredMethods();
				for (Method m : methods) {
					if (setterName.equals(m.getName())) {
						if(req.getParameter(fieldName) == null) {
							continue;
						}
						if (f.getType() ==  Integer.class || f.getType() == int.class) {
							m.invoke(t, Integer.parseInt(req.getParameter(fieldName)));
						}
						if (f.getType() == String.class) {
							m.invoke(t, req.getParameter(fieldName));
						}

						if (f.getType() == String[].class) {
							m.invoke(t, (Object)req.getParameterValues(fieldName));
						}
						
						if(f.getType() == Long.class || f.getType() == long.class) {
							m.invoke(t, Long.valueOf(req.getParameter(fieldName)));
						}

					}
				}
			}
			if(req.getContentType() == null || !req.getContentType().startsWith("multipart")) {
				return t;
			}
			
			Collection<Part> parts = req.getParts();
			
			List<Attach> attachs = new ArrayList<Attach>();
			for(Part p : parts) {
				if(p.getContentType() == null) {
					continue;
				}
				// 파일의 원본이름
				String origin = p.getSubmittedFileName();
				
				// 파일명 중 마지막 .의 위치
				int dotIdx = origin.lastIndexOf(".");
				
				// 확장자를 담을 변수
				String ext = "";
				
				// 확장자 구하기
				if(dotIdx > -1) {
					ext = origin.substring(dotIdx);
				}
				
				// UUID 문자열 생성
				String uuid = UUID.randomUUID().toString();
				// 경로 문자열 반환
				String path = getTodayStr();
				
				// 경로 문자열에 대한 폴더 생성
				File targetPath = new File(ParamSolver.UPLOAD_PATH , path);
				if(!targetPath.exists()) {
					targetPath.mkdirs();
				}
				
				// 원본에 대한 저장
				File fs = new File(targetPath, uuid + ext);
				p.write(fs.getPath());
				
				// 이미지여부 확인
				// mime
				// image/x-icon, image/webp
				
				// 이미지 영역에 거치지 않게 하기 위함
//				List<String> exceptImgMimes = Arrays.asList("image/x-icon", "image/webp"); 
				boolean image = p.getContentType().startsWith("image"); //&& !exceptImgMimes.contains(p.getContentType());
				
				if(image) {
					// 섬네일 생성
					try {
						File out = new File(targetPath, uuid + "_t" + ext);
						Thumbnailator.createThumbnail(fs, out, 400, 400);
					}catch(UnsupportedFormatException e) {
						image = false;
					}
				}
			
				attachs.add(new Attach(uuid, origin, image, path));
				// uuid, origin, image, path		
			}
			
			// 명확하게 체크할려고
			if(clazz == Board.class) {
				((Board)t).setAttachs(attachs);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	private static String getTodayStr() {
		return new SimpleDateFormat("yyyy/MM/dd").format(System.currentTimeMillis());
	}
	public static boolean isLogin(HttpServletRequest req) {
		return req.getSession().getAttribute("member") != null;
	}
	public static boolean isMine(HttpServletRequest req, String writer) {
		return isLogin(req) && ((Member)req.getSession().getAttribute("member")).getId().equals(writer);
	}
}
