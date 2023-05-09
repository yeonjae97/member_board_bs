package kr.co.sukbinggo.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.bcrypt.BCrypt;

import kr.co.sukbinggo.jsp.util.DBConn;
import kr.co.sukbinggo.jsp.domain.Member;

public class MemberDao {
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;

	public int insert(Member vo) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql 구문
		String sql = "insert into tbl_member(id, pw, name, email, addr, addrDetail) values (?, ?, ?, ?, ?, ?)";
		try {
			// 문장 생성
			
			int idx = 1;
			stmt = conn.prepareStatement(sql);
			stmt.setString(idx++, vo.getId());
			stmt.setString(idx++, vo.getPw());
			stmt.setString(idx++, vo.getName());
			stmt.setString(idx++, vo.getEmail());
			stmt.setString(idx++, vo.getAddr());
			stmt.setString(idx++, vo.getAddrDetail());
			
			// 문장 처리
			result = stmt.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public Member selectOne(String id) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		Member vo = null;
		// 처리할 sql 구문
		String sql = "select * from tbl_member where id = ?";
		try {
			// 문장생성
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			// 결과집합
			rs = stmt.executeQuery();

			// 결과집합 >> 자바객체
			if (rs.next()) {
				int idx = 1;
				// 객체 생성 후 값 바인딩
				vo = new Member(
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getDate(idx++),
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++) 
				);
			}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}
	public List<Member> selectList() {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		List<Member> vo = new ArrayList<Member>();
		// 처리할 sql 구문
		String sql = "select * from tbl_member";
		try {
			// 문장생성
			stmt = conn.prepareStatement(sql);
			// 결과집합
			rs = stmt.executeQuery();
			
			// 결과집합 >> 자바객체
			while(rs.next()) {
				int idx = 1;
				// 객체 생성 후 값 바인딩
				Member member = new Member(
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getDate(idx++),
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++) 
				);
				
				vo.add(member);
			}
			
            close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}

	public int updatePw(Member vo){
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql 구문
		String sql = "update tbl_member set pw = ? where id = ?";
		try {
			// 문장 생성
			
			stmt = conn.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++, vo.getPw());
			stmt.setString(idx++, vo.getId());
			
			// 문장 처리
			result = stmt.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}
	
	// 비밀번호 변경시
	public int update(Member vo){
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql 구문
		String sql = "update tbl_member set name = ?, email = ?, addr = ?, addrDetail = ? where id = ?";
		try {
			// 문장 생성
			
			stmt = conn.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++, vo.getName());
			stmt.setString(idx++, vo.getEmail());
			stmt.setString(idx++, vo.getAddr());
			stmt.setString(idx++, vo.getAddrDetail());
			stmt.setString(idx++, vo.getId());
			
			// 문장 처리
			result = stmt.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int delete(Member vo){
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql 구문
		String sql = "delete tbl_member where id = ?";
		try {
			// 문장 생성
			
			stmt = conn.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++, vo.getId());
			
			// 문장 처리
			result = stmt.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	
	// 자원 반환
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {}
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {}
		}

	}
	
	
	public static void main(String[] args) {
		MemberDao dao = new MemberDao();
		dao.selectList().forEach(member ->{
			// 패스워드는 1234로 고정
			member.setPw(BCrypt.hashpw("1234", BCrypt.gensalt()));
			
			// 전체 회원에 대해서 updatePw를 수행하게 된다.
			dao.updatePw(member);
		});
	}
}
