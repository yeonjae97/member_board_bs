package kr.co.sukbinggo.jsp.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
	private Long rno;
	private String content;
	private Date regDate;
	private String writer;
	private Long bno;
}
