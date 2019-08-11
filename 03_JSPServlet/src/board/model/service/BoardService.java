package board.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import board.model.dao.BoardDAO;
import board.model.vo.Attachment;
import board.model.vo.Board;
import board.model.vo.Reply;

public class BoardService {

	public int getListCount() {
		Connection conn = getConnection();
		int result = new BoardDAO().getListCount(conn);
		close(conn);
		return result;
	}

	public ArrayList<Board> selectList(int currentPage) {
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDAO().selectList(conn, currentPage);

		return list;
	}

	public int insertBoard(Board b) {
		Connection conn = getConnection();
		int result = new BoardDAO().insertBoard(conn, b);
		close(conn);

		return result;
	}

	public Board selectBoard(int bid) {

		Connection conn = getConnection();
		Board board = new BoardDAO().selectBoard(conn, bid);
		close(conn);

		return board;
	}

	public void updateCount(int bid) {
		Connection conn = getConnection();
		int result = new BoardDAO().updateCount(conn, bid);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
	}

	public int deleteBoard(int bId) {
		Connection conn = getConnection();
		int result = new BoardDAO().deleteBoard(conn, bId);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return result;
	}

	public int updateBoard(Board b) {
		Connection conn = getConnection();
		int result = new BoardDAO().updateBoard(conn, b);
		System.out.println("서비스 : " +result );
		close(conn);

		return result;
	}

	public ArrayList selectTList(int i) {
		// 제네릭은 오버로딩 조건에 걸리지 않음!
		Connection conn = getConnection();
		ArrayList list = null;
		BoardDAO dao = new BoardDAO();

		if (i == 1) {
			list = dao.selectBList(conn);
		} else {
			list = dao.selectFList(conn);
		}
		close(conn);

		return list;
	}

	public int insertThumbnail(Board b, ArrayList<Attachment> fileList) {
		Connection conn = getConnection();
		BoardDAO dao = new BoardDAO();

		int result1 = dao.insertThBoard(conn, b);
		int result2 = dao.insertAttachment(conn, fileList);

		if (result1 > 0 && result2 > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result1;
	}

	public ArrayList<Attachment> selectThumbnail(int bid) {
		Connection conn = getConnection();
		ArrayList<Attachment> list = new BoardDAO().selectThumnail(conn, bid);
		close(conn);
		return list;
	}

	public Attachment selectAttachment(int fid) {
		Connection conn = getConnection();
		BoardDAO dao = new BoardDAO();

		int result = dao.updateDownloadCount(conn, fid);
		Attachment at = null;
		if (result > 0) {
			commit(conn);
			at = dao.selectAttachment(conn, fid);
		} else {
			rollback(conn);
		}
		close(conn);
		return at;

	}

	public ArrayList<Reply> selectReplyList(int bid) {
		Connection conn = getConnection();
		ArrayList<Reply> list = new BoardDAO().selectReplyList(conn,bid);
		close(conn);
		
		return list;
	}

	public ArrayList<Reply> insertReply(Reply r) {
		Connection conn = getConnection();
		BoardDAO dao = new BoardDAO();
		
		int result = dao.insertReply(conn,r);
		
		ArrayList<Reply> rlist = null;
		
		if(result > 0) {
			commit(conn);
			rlist = dao.selectReplyList(conn, r.getRefBid());
		}else {
			rollback(conn);
		}
		close(conn);
		return rlist;
	}



}
