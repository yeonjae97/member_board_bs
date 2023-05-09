package test;

import org.mindrot.bcrypt.BCrypt;

public class BcryptTest {
	public static void main(String[] args) {
		String pw = "0000";
		BCrypt bCrypt= new BCrypt();
		
		// "0000" + 0
		// gensalt는 외부에서의 침입을 방어하기 위한 대비 기능으로 인자만큼 추가한다.
		String result = BCrypt.hashpw(pw, bCrypt.gensalt(8));
		String result2 = BCrypt.hashpw(pw, bCrypt.gensalt(8));
		String result3 = BCrypt.hashpw(pw, bCrypt.gensalt(8));
		System.out.println(result);
		System.out.println(result2);
		System.out.println(result3);
		
		
		System.out.println(BCrypt.checkpw("1111", result));
		System.out.println(BCrypt.checkpw(pw, result2));
		System.out.println(BCrypt.checkpw(pw, result3));
		
		
		
		String resultCopy = "$2a$08$fdbFZ31F531Aize.p1RQmuR7lmNtorrsCjaAlRxf2kq8vXo7dpWJe";
		System.out.println(BCrypt.checkpw("0000", resultCopy));
		System.out.println(BCrypt.checkpw("1234", "$2a$10$N6QXbypLMGxTbjLNUPeWces8drFBvobI7IRSB9nLE6fMS86PapieK"));
		
	}
}
