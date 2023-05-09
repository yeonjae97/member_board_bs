package kr.co.sukbinggo.jsp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private String updatedate;
	private int hitcount;
	private Integer category;
	
	// tbl_board에는 정보가 없지만 앞으로는 활용이 될 것이다.
	private List<Attach> attachs = new ArrayList<Attach>();
	
	// 어떤 bno의 댓글 count
	private int replyCnt;
}
