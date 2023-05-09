package kr.co.sukbinggo.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sukbinggo.jsp.domain.Reply;
import kr.co.sukbinggo.jsp.util.DBConn;



public class ReplyDao {
		private Connection conn;
		private PreparedStatement pstmt;
		private ResultSet rs;

		
		// 댓글 
		public int insert(Reply reply) {
			conn = DBConn.getConnection();
			int result = 0;
			// 처리할 sql 구문
			String sql = "insert into tbl_reply (content, writer, bno) values (?, ?, ?)";
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, reply.getContent());
				pstmt.setString(2, reply.getWriter());
				pstmt.setLong(3, reply.getBno());
				
				// 문장 처리
				result = pstmt.executeUpdate();
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;

		}

	
		// cri의 문장에 의해서 sql문장이 생성됨
		public List<Reply> selectList(Long bno) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			List<Reply> replies = new ArrayList<Reply>();
			// 처리할 sql 구문
			String sql = "";  
			sql += "select * from tbl_reply where bno = ?";


			try {
				// 문장생성
				pstmt = conn.prepareStatement(sql);
				int idx = 1;
				
				pstmt.setLong(idx, bno);
			
				// 결과집합
				rs = pstmt.executeQuery();
				// 결과집합 >> 자바객체
				while(rs.next()) {
					idx = 1;
					// 객체 생성 후 값 바인딩
					Reply reply = new Reply(
							rs.getLong(idx++),
							rs.getString(idx++),
							rs.getDate(idx++),
							rs.getString(idx++),
							rs.getLong(idx++));					
					replies.add(reply);
				}
	           close();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        			close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return replies;
		}

		// cri의 문장에 의해서 sql문장이 생성됨
		public Reply selectOne(Long rno) {
			conn = DBConn.getConnection();
			// 처리할 sql 구문
			Reply reply = null;
			String sql = "";  
			sql += "select * from tbl_reply where rno = ?";

			try {
				// 문장생성
				pstmt = conn.prepareStatement(sql);
				int idx = 1;
				
				pstmt.setLong(idx, rno);
			
				// 결과집합
				rs = pstmt.executeQuery();
				// 결과집합 >> 자바객체
				if(rs.next()) {
					idx = 1;
					// 객체 생성 후 값 바인딩
					reply = new Reply(
							rs.getLong(idx++),
							rs.getString(idx++),
							rs.getDate(idx++),
							rs.getString(idx++),
							rs.getLong(idx++));					
				}
	           close();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        			close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return reply;
		}
		
		public void updateWriterToNull(String id) {
			conn = DBConn.getConnection();
			// 처리할 sql 구문
			String sql = "update tbl_reply set\r\n"
					+ "	writer = NULL \r\n"
					+ "where writer = ?";
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				
				
				// 문장 처리
				pstmt.executeUpdate();
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		public int delete(Long rno) {
			int ret = 0;
			conn = DBConn.getConnection();
			// 반환 예정 객체
			// 처리할 sql 구문
			String sql = "delete from tbl_reply where rno = ?";
			try {
				// 문장생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, rno);
				// 결과집합
//				rs = pstmt.executeQuery();
				ret = pstmt.executeUpdate();	
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ret;
		}
		public void deleteByBno(Long bno) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			// 처리할 sql 구문
			String sql = "delete from tbl_reply where bno = ?";
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
