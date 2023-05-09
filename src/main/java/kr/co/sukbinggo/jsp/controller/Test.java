package kr.co.sukbinggo.jsp.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import kr.co.sukbinggo.jsp.domain.Board;

public class Test {
	public static void main(String[] args) {
		
		List<String> set = new ArrayList<>();
		set.add("공지사항");
		set.add("자유게시판");
		set.add("자료실");
		set.add("갤러리");
		
		List<Board> list = new ArrayList<Board>();
		
		LinkedHashMap<String, List<Board>> map = new LinkedHashMap<>();
		for(String s : set){
			map.put(s, list);
		}
		System.out.println(map);
	}
}
