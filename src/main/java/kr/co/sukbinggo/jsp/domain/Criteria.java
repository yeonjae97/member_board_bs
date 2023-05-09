package kr.co.sukbinggo.jsp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 페이징 처리

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {
	// 페이지 번호와 총합의 기본값
	private int pageNum = 1;
	private int amount = 10;
	private int category = 1;
	
	// 이 배열의 길이가 0인 경우 페이지를 넘길 때에는 type이 null인 값이 뜨지 않지만
	// new String[0]로 초기화하지 않거나 배열의 길이가 1이 이상일 경우에는 type이 null일 가능성을 포함하고 있다.
	
	private String[] type = new String[0];
	
	// 빈문자열을 검색하면 null이기 때문에 그것을 방지할려고 초기화시킴
	private String keyword = "";
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String getTypeStr() {
		String str = "";

		if(type != null) {
			for(String s : type) {
				str += "&type=" + s;
			}
			str += "&keyword=" + keyword;
		}
//		str += getQueryString();
		return str;
	}
	
	//
	public String getQueryString() {
		String str = "";
		str += "amount=" + amount + "&category=" + category;
		str += getTypeStr();
		return str;
	}
	
	public String getFullQueryString() {
		String str = "";
		str += "pageNum=" + pageNum + "&";
		str += getQueryString();
//		if(type != null) {
//			for(String s : type) {
//				str += "&type=" + s;
//			}
//			str += "&keyword=" + keyword;
//		}
		return str;
	}
}
