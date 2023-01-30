package ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FlowerDao {
	// 싱글톤
	private static FlowerDao instance;

	private FlowerDao() {
	}

	public static FlowerDao getInstance() {
		if (instance == null) {
			instance = new FlowerDao();
		}
		return instance;
	}

	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String ID = "project01";
	private final String PW = "1234";

	private Connection getConnection() {
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, ID, PW);
			System.out.println("conn: " + conn);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 정보입력
	public boolean addPurchase(FlowerVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "insert into f_purchase(id,item,quantity,seq_id)"
					+ "		values (?,?,?, seq_purchase.nextval)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getItem());
			pstmt.setInt(3, vo.getQuantity());
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}
		return false;
	} // addPurchase

	// 특정 회원의 구매정보 조회(pk : id)
	public List<FlowerVo> selectPuchaseById(String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FlowerVo> list = new ArrayList<>();
		try {
			conn = getConnection();
			String sql = "select * from f_purchase" 
						+ "	  where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("id");
				String item = rs.getString("item");
				String quantity = rs.getString("quantity");
				Date purchaseDate = rs.getDate("purchaseDate");
				String seq_id = rs.getString("seq_id");
				FlowerVo vo = new FlowerVo(id, item, Integer.parseInt(quantity), purchaseDate, seq_id);
				list.add(vo);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	}

	// 장바구니 수정
	public void updateCart(FlowerVo flowerVo, String updateItem, int updateQuan) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "update f_purchase set" 
						+ "			item = ?," 
						+ "			quantity = ?"
						+ "   where seq_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, updateItem);
			pstmt.setInt(2, updateQuan);
			pstmt.setString(3, flowerVo.getSeq_id());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}
	} // updateCart()

	// 장바구니 삭제
	public void deleteCart(FlowerVo fvo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "delete from f_purchase" 
						+ "		where seq_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fvo.getSeq_id());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}
	} // deleteMember()
}
