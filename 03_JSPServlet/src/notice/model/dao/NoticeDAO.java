package notice.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import notice.model.vo.Notice;

public class NoticeDAO {
	private Properties prop = new Properties();

	public NoticeDAO() {
		String fileName = NoticeDAO.class.getResource("/sql/notice/notice-query.properties").getPath();
		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Notice> selectList(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Notice> list = null;
		String query = prop.getProperty("selectList");

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			list = new ArrayList<Notice>();
			while (rset.next()) {
				Notice no = new Notice(rset.getInt("nno"), rset.getString("ntitle"), rset.getString("nContent"),
						rset.getString("nwriter"), rset.getInt("ncount"), rset.getDate("ndate"));
				list.add(no);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}

		return list;
	}

	public int insertNotice(Connection conn, Notice n) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("insertNotice");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, n.getnTitle());
			pstmt.setString(2, n.getnContent());
			pstmt.setString(3, n.getnWriter());
			pstmt.setDate(4, n.getnDate());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Notice selectNotice(Connection conn, int nno) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Notice n = null;
		String query = prop.getProperty("selectNotice");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, nno);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				n = new Notice(rset.getInt("nno"), rset.getString("ntitle"), rset.getString("nContent"),
						rset.getString("nwriter"), rset.getInt("ncount"), rset.getDate("nDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);

		}
		return n;
	}

	public int updateNotice(Connection conn, Notice notice) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("updateNotice");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, notice.getnTitle());
			pstmt.setString(2, notice.getnContent());
			pstmt.setDate(3, notice.getnDate());
			pstmt.setInt(4, notice.getnNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int delteNotice(Connection conn, int nno) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("delteNotice");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, nno);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int updateCount(Connection conn, int nno) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("updateCount");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, nno);
			pstmt.setInt(2, nno);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}



}
