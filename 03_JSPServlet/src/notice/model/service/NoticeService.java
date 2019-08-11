package notice.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import notice.model.dao.NoticeDAO;
import notice.model.vo.Notice;

public class NoticeService {

	public ArrayList<Notice> selectList() {
		Connection conn = getConnection();
		ArrayList<Notice> list = new NoticeDAO().selectList(conn);
		close(conn);

		return list;
	}

	public int insertNotice(Notice n) {
		Connection conn = getConnection();
		int result = new NoticeDAO().insertNotice(conn, n);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public Notice selectNotice(int nno) {
		Connection conn = getConnection();
		Notice n = new NoticeDAO().selectNotice(conn,nno);
		close(conn);
		return n;
	}

	public int updateNotice(Notice notice) {
		Connection conn = getConnection();
		int result = new NoticeDAO().updateNotice(conn,notice);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int delteNotice(int nno) {
		Connection conn = getConnection();
		int result = new NoticeDAO().delteNotice(conn,nno);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public void updateCount(int nno) {
		Connection conn = getConnection();
		int result = new NoticeDAO().updateCount(conn,nno);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
	}

}
