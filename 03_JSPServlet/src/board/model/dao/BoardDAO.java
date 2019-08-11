package board.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import board.model.vo.Attachment;
import board.model.vo.Board;
import board.model.vo.Reply;

public class BoardDAO {
	private Properties prop = new Properties();

	public BoardDAO() {
		String fileName = BoardDAO.class.getResource("/sql/board/board-query.properties").getPath();

		try {
			prop.load(new FileReader(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getListCount(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		int result = 0;
		String query = prop.getProperty("getListCount");

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			if (rset.next()) {
				result = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}

		return result;
	}

	public ArrayList<Board> selectList(Connection conn, int currentPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Board> list = null;

		int posts = 10;

		int startRow = (currentPage - 1) * posts + 1;
		int endRow = startRow + posts - 1;

		String query = prop.getProperty("selectList");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			pstmt.setInt(3, 1);

			rset = pstmt.executeQuery();

			list = new ArrayList<Board>();
			while (rset.next()) {
				Board b = new Board(rset.getInt("bId"), rset.getInt("btype"), rset.getString("cname"),
						rset.getString("btitle"), rset.getString("bcontent"), rset.getString("nickname"),
						rset.getInt("bcount"), rset.getDate("create_date"), rset.getDate("modify_date"),
						rset.getString("status"));
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int insertBoard(Connection conn, Board b) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("insertBoard");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(b.getCategory()));
			pstmt.setString(2, b.getbTitle());
			pstmt.setString(3, b.getbContent());
			pstmt.setString(4, b.getbWriter());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public Board selectBoard(Connection conn, int bid) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Board board = null;
		String query = prop.getProperty("selectBoard");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bid);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				board = new Board(rset.getInt("bId"), rset.getInt("btype"), rset.getString("cname"),
						rset.getString("btitle"), rset.getString("bcontent"), rset.getString("nickname"),
						rset.getInt("bcount"), rset.getDate("create_date"), rset.getDate("modify_date"),
						rset.getString("status"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return board;
	}

	public int updateCount(Connection conn, int bid) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("updateCount");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bid);
			pstmt.setInt(2, bid);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int deleteBoard(Connection conn, int bId) {

		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteBoard");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bId);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateBoard(Connection conn, Board board) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = prop.getProperty("updateBoard");
		System.out.println(query);
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getbTitle());
			pstmt.setString(2, board.getbWriter());
			pstmt.setString(3, board.getbContent());
			pstmt.setInt(4, Integer.parseInt(board.getCategory()));
			pstmt.setInt(5, board.getbId());

			result = pstmt.executeUpdate();
			System.out.println("DAO : " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public ArrayList selectBList(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Board> list = null;

		String query = prop.getProperty("selectBList");

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			list = new ArrayList<Board>();
			while (rs.next()) {
				list.add(new Board(rs.getInt("bid"), rs.getInt("btype"), rs.getString("cname"), rs.getString("btitle"),
						rs.getString("bcontent"), rs.getString("nickname"), rs.getInt("bcount"),
						rs.getDate("create_date"), rs.getDate("modify_date"), rs.getString("status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}

		return list;
	}

	public ArrayList selectFList(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Attachment> list = null;

		String query = prop.getProperty("selectFList");

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			list = new ArrayList<Attachment>();
			while (rs.next()) {
				list.add(new Attachment(rs.getInt("bid"), rs.getString("change_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return list;
	}

	public int insertThBoard(Connection conn, Board b) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = prop.getProperty("insertThBoard");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, b.getbTitle());
			pstmt.setString(2, b.getbContent());
			pstmt.setString(3, b.getbWriter());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int insertAttachment(Connection conn, ArrayList<Attachment> fileList) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = prop.getProperty("insertAttachment");

		try {
			for (int i = 0; i < fileList.size(); i++) {
				Attachment at = fileList.get(i);
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, at.getOriginName());
				pstmt.setString(2, at.getChangeName());
				pstmt.setString(3, at.getFilePAth());
				pstmt.setInt(4, at.getFileLevel());

				result += pstmt.executeUpdate(); // 계속 더해줄것

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public ArrayList<Attachment> selectThumnail(Connection conn, int bid) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Attachment> list = null;

		String query = prop.getProperty("selectThumbnail");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bid);

			rs = pstmt.executeQuery();
			list = new ArrayList<Attachment>();

			while (rs.next()) {
				Attachment at = new Attachment();
				at.setfId(rs.getInt("fid"));
				at.setOriginName(rs.getString("origin_name"));
				at.setChangeName(rs.getString("change_name"));
				at.setFilePAth(rs.getString("file_path"));
				at.setUploadDate(rs.getDate("upload_date"));

				list.add(at);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}

	public int updateDownloadCount(Connection conn, int fid) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = prop.getProperty("updateDownloadCount");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fid);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public Attachment selectAttachment(Connection conn, int fid) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Attachment at = null;

		String query = prop.getProperty("selectAttachment");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fid);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				at = new Attachment();
				at.setOriginName(rs.getString("origin_name"));
				at.setChangeName(rs.getString("change_name"));
				at.setFilePAth(rs.getString("file_path"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return at;
	}

	public ArrayList<Reply> selectReplyList(Connection conn, int bid) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<Reply> rlist = null;

		String query = prop.getProperty("selectReplyList");
		// DB켜서 view 생성 해야함!

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bid);
			rs = pstmt.executeQuery();

			rlist = new ArrayList<Reply>();

			while (rs.next()) {
				rlist.add(new Reply(rs.getInt("rid"), rs.getString("rcontent"), rs.getInt("ref_bid"),
						rs.getString("nickname"), rs.getDate("create_date"), rs.getDate("modify_date"),
						rs.getString("status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rlist;
	}

	public int insertReply(Connection conn, Reply r) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = prop.getProperty("insertReply");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, r.getrContent());
			pstmt.setInt(2, r.getRefBid());
			pstmt.setString(3, r.getrWriter());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

}
