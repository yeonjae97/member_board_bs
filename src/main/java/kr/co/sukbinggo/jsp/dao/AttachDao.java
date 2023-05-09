package kr.co.sukbinggo.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sukbinggo.jsp.util.DBConn;
import kr.co.sukbinggo.jsp.domain.Attach;


public class AttachDao {
		private Connection conn;
		private PreparedStatement pstmt;
		private ResultSet rs;

		public void insert(Attach attach) {
			conn = DBConn.getConnection();
			// 처리할 sql 구문
			String sql = "insert into tbl_attach values (?, ?, ?, ?, ?)";
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				int idx = 1;
				pstmt.setString(idx++, attach.getUuid());
				pstmt.setString(idx++, attach.getOrigin());
				pstmt.setBoolean(idx++, attach.isImage());
				pstmt.setString(idx++, attach.getPath());
				pstmt.setLong(idx++, attach.getBno());
				
				// 문장 처리
				pstmt.executeUpdate();
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	

		}

		public Attach selectOne(String uuid) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			Attach attach = null;
			// 처리할 sql 구문
			String sql = "select * from tbl_attach where uuid = ?";
			try {
				// 문장생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, uuid);
				// 결과집합
				rs = pstmt.executeQuery();

				// 결과집합 >> 자바객체
				if (rs.next()) {
					int idx = 1;
					// 객체 생성 후 값 바인딩
					attach = new Attach(
							rs.getString(idx++),
							rs.getString(idx++),
							rs.getBoolean(idx++),
							rs.getString(idx++),
							rs.getLong(idx++));
				}
	            close();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         			close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return attach;
		}

		// cri의 문장에 의해서 sql문장이 생성됨
		public List<Attach> selectList(Long bno) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			List<Attach> attachs = new ArrayList<Attach>();
			// 처리할 sql 구문
			String sql = "select * from tbl_attach where bno = ?";  
			

			try {
				// 문장생성
				pstmt = conn.prepareStatement(sql);
				int idx = 1;
				pstmt.setLong(idx++, bno);
				// 결과집합
				rs = pstmt.executeQuery();
				// 결과집합 >> 자바객체
				while(rs.next()) {
					idx = 1;
					// 객체 생성 후 값 바인딩
					Attach attach =  new Attach(
							rs.getString(idx++),
							rs.getString(idx++),
							rs.getBoolean(idx++),
							rs.getString(idx++),
							rs.getLong(idx++));					
					attachs.add(attach);
				}
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             			close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(attachs);
			return attachs;
		}


		public void delete(Long bno) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			// 처리할 sql 구문
			String sql = "delete from tbl_attach where bno = ?";
			try {
				// 문장생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, bno);
				// 결과집합
				pstmt.executeUpdate();	
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
		
		// 자원 반환
		public void close() {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}

		}
		

}
