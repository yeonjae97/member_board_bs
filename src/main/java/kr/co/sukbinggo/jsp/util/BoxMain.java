package kr.co.sukbinggo.jsp.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.ToString;

public class BoxMain {
	public static void main(String[] args) {
		Box<String> box = new Box<>(); // 인스턴스의 꺽쇠는 자동으로 해당 타입을 추가해준다.

		
		
		box.unshift("1");
		box.unshift("2");
		box.unshift("3");


	}
	
}


@ToString
class Box<T> { // 제네릭 타입변수는 보통 T를 많이 쓴다. => 타입으로 인정하겠다는 뜻 => 클래스의 타입을 T로 치환하게 되면 
	// Object 대신 T로 쓰여진다.
//	private T item;
//	
//	public void setItem(T item) {
//		this.item = item;
//	}
//	
//	public T getItem() {
//		return item;
//	}
	private List<T> items = new ArrayList<>();
	
	public void push(T item) {
		items.add(item);
	}
	
	public T pop(){
		return items.remove(items.size() - 1);
	}
	
	// unshift 0번째 추가
	public void unshift(T item) {
		items.add(0, item);
	}
	
	
	// shift 0번째 빼기
	public T shift() {
		return items.remove(0);
	}
}


abstract class Fruit{}


class Apple extends Fruit {}


class Grape extends Fruit {}
