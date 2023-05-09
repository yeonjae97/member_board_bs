package kr.co.sukbinggo.jsp.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Board {
	private Long bno;
	@NonNull
	private String title;
	@NonNull
	private String content;
	@NonNull
	private String writer;
	private Date regdate;
	private String updatedate;
	private int hitcount;
	private Integer category;
}
