package member.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;

import member.model.dao.MemberDAO;
import member.model.vo.Member;

public class MemberService {
	public Member loginMember(Member member) {
		Connection conn = getConnection();
		Member loginUser = new MemberDAO().loginMember(conn, member);
		close(conn);
		return loginUser;
	}

	public int insertMember(Member member) {
		Connection conn = getConnection();
		MemberDAO mDAO = new MemberDAO();

		int result = mDAO.insertMember(conn, member);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public int idCheck(String userId) {
		Connection conn = getConnection();

		int result = new MemberDAO().idCheck(conn, userId);
		close(conn);
		return result;
	}

	public Member selectMember(String loginUserId) {
		Connection conn = getConnection();

		Member member = new MemberDAO().selectMember(conn, loginUserId);

		close(conn);

		return member;
	}

	public int updateMember(Member member) {
		Connection conn = getConnection();
		int result = new MemberDAO().updateMember(conn, member);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);
		return result;
	}

	public int deleteMember(String userId) {
		Connection conn = getConnection();
		int result = new MemberDAO().deleteMember(conn, userId);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int nickNameCheck(String userNickName) {
		Connection conn = getConnection();
		int result = new MemberDAO().nickNameCheck(conn, userNickName);
		close(conn);
		return result;
	}

	public int updatePwd(String userId, String newPwd) {
		Connection conn = getConnection();
		int result = new MemberDAO().updatePwd(conn, userId,newPwd);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

}
