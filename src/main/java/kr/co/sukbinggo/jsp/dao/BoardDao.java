package kr.co.sukbinggo.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kr.co.sukbinggo.jsp.domain.Criteria;
import kr.co.sukbinggo.jsp.util.DBConn;
import kr.co.sukbinggo.jsp.domain.Board;


public class BoardDao {
		private Connection conn;
		private PreparedStatement pstmt;
		private ResultSet rs;

		public Long insert(Board board) {
			conn = DBConn.getConnection();
			// 처리 갯수
			long result = 0;
			// 처리할 sql 구문
			String sql = "insert into tbl_board (title, content, writer, category) values (?, ?, ?, ?)";
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getContent());
				pstmt.setString(3, board.getWriter());
				pstmt.setInt(4, board.getCategory());
				
				// 문장 처리
				pstmt.executeUpdate();
				close();
			
				
				// 트랜젝션 문제가 아니다. 글 작성을 했으면 한번에 처리해야한다.
				
				
				// 쿼리 재실행
				// 작성한 게시글의 글번호 조회
				// 1행 1열 가져옴
				sql = "select max(bno) from tbl_board";
				conn = DBConn.getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rs.next(); // 커서 이동
				result = rs.getLong(1); // 1번컬럼 long 반환
				close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;

		}

		public Board selectOne(Long bno) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			Board board = null;
			// 처리할 sql 구문
			String sql = "select tb.*, (select count(*) from tbl_reply tr where tr.bno = tb.bno) replyCnt "
					+ "from tbl_board tb where bno = ?";
			try {
				// 문장생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, bno);
				// 결과집합
				rs = pstmt.executeQuery();

				// 결과집합 >> 자바객체
				if (rs.next()) {
					int idx = 1;
					// 객체 생성 후 값 바인딩
					board = new Board(
							rs.getLong(idx++),
							rs.getString(idx++),
							rs.getString(idx++),
							rs.getString(idx++),
							rs.getDate(idx++),
							rs.getString(idx++),
							rs.getInt(idx++), rs.getInt(idx++), null, rs.getInt(idx++));
				}
	            close();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         			close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return board;
		}

		// cri의 문장에 의해서 sql문장이 생성됨
		public List<Board> selectList(Criteria cri) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			List<Board> boards = new ArrayList<Board>();
			// 처리할 sql 구문
			String sql = "";  
			sql += "select tb.*, (select count(*) from tbl_reply tr where tr.bno = tb.bno) replyCnt ";
			sql += "from tbl_board tb where category = ?";
			
			// 검색
			sql += getSearchSqlBy(cri);
			sql += " order by bno desc limit ? offset ?";
			try {
				// 문장생성
				pstmt = conn.prepareStatement(sql);
				int idx = 1;
				pstmt.setLong(idx++, cri.getCategory());
				pstmt.setLong(idx++, cri.getAmount());
				pstmt.setLong(idx++, (cri.getPageNum() - 1) * cri.getAmount());
				// 결과집합
				rs = pstmt.executeQuery();
				// 결과집합 >> 자바객체
				while(rs.next()) {
					idx = 1;
					// 객체 생성 후 값 바인딩
					Board board = new Board(
							rs.getLong(idx++),
							rs.getString(idx++),
							rs.getString(idx++),
							rs.getString(idx++),
							rs.getDate(idx++),
							rs.getString(idx++),
							rs.getInt(idx++), rs.getInt(idx++), null, rs.getInt(idx++));					
					boards.add(board);
				}
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             			close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return boards;
		}
		// cri의 문장에 의해서 sql문장이 생성됨
		public List<Map<String, String>> selectListGallery(Criteria cri) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			List<Map<String,String>> boards = new ArrayList<>();
			
			
			// 처리할 sql 구문 ( inner join시에 서브쿼리를 활용하는 것도 방법
			String sql = "";  
			sql += "select bno,	title, writer, "
					+ "(select concat(path,'/', uuid,'.', substring_index(origin, '.', -1)) from tbl_attach ta where ta.bno = tb.bno and image = 1 limit 1) fullpath "		
					+ "from tbl_board tb where category = ?";			
			// 검색
			sql += getSearchSqlBy(cri);
			sql += " order by bno desc limit ? offset ?";
			try {
				// 문장생성
				pstmt = conn.prepareStatement(sql);
				int idx = 1;
				pstmt.setLong(idx++, cri.getCategory());
				pstmt.setLong(idx++, cri.getAmount());
				pstmt.setLong(idx++, (cri.getPageNum() - 1) * cri.getAmount());
				// 결과집합
				rs = pstmt.executeQuery();
				// 결과집합 >> 자바객체
				ResultSetMetaData rsmd = rs.getMetaData();
				
				while(rs.next()) {
					idx = 1;
					// 객체 생성 후 값 바인딩
					Map<String, String> map = new HashMap<String, String>();
					for(int i = 0; i < rsmd.getColumnCount(); i++){
						
						map.put(rsmd.getColumnName(idx), rs.getString(idx++));
					}
					boards.add(map);
				}
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return boards;
		}

		public int selectListCount(Criteria cri) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			int count = 0;
			// 처리할 sql 구문
			String sql = "select count(*) from tbl_board where category = ?";
			sql += getSearchSqlBy(cri);
			try {
				// 문장생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, cri.getCategory());
				// 결과집합
				rs = pstmt.executeQuery();

				// 결과집합 >> 자바객체
				while(rs.next()) {
					int idx = 1;
					// 객체 생성 후 값 바인딩
					count = rs.getInt(1);
					
				}
				close();
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             			close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
		}
		public void update(Board board) {
			conn = DBConn.getConnection();
			// 처리할 sql 구문
			String sql = "update tbl_board set\r\n"
					+ "	title = ?,\r\n"
					+ "	content = ?,\r\n"
					+ "	updatedate = now()\r\n"
					+ "where bno = ?";
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getContent());
				pstmt.setLong(3, board.getBno());
				
				// 문장 처리
				pstmt.executeUpdate();
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		public void updateWriterToNull(String id) {
			conn = DBConn.getConnection();
			// 처리할 sql 구문
			String sql = "update tbl_board set\r\n"
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

		public void increaseHitCount(Long bno) {
			conn = DBConn.getConnection();
			// 처리할 sql 구문
			String sql = "update tbl_board set\r\n"
					+ "	hitcount = hitcount + 1\r\n"
					+ "where bno = ?";
			try {
				// 문장 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, bno);
				
				// 문장 처리
				pstmt.executeUpdate();
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		public void delete(Long bno) {
			conn = DBConn.getConnection();
			// 반환 예정 객체
			// 처리할 sql 구문
			String sql = "delete from tbl_board where bno = ?";
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
		private String getSearchSqlBy(Criteria cri) {
			String sql = "";
			if(cri.getType() != null && cri.getKeyword() != null && String.join("", cri.getType()).length() > 0) {		
				sql += " and ( ";
	
							
				// T C W
				List<String> list = Arrays.asList(cri.getType());
				List<String> typeList = list.stream().map(s->" " + s + " like '%" + cri.getKeyword() + "%' ").collect(Collectors.toList());
			
				sql += String.join(" or ", typeList);
				sql += ")";
			}
			return sql;
		}
		
		public static void main(String[] args) {
//			new BoardDao().updateWriterToNull("id3");
			Criteria cri = new Criteria(1, 12);
			cri.setCategory(4);
			new BoardDao().selectListGallery(cri).forEach(System.out::println);
		}


}
