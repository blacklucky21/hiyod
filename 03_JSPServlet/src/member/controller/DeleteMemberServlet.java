package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;

@WebServlet("/delete.me")
public class DeleteMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteMemberServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		// hidden에 있는 친구를 데려오는것
		int result = new MemberService().deleteMember(userId);
		String page = null;
		if (result > 0) {
			HttpSession session = request.getSession(false);
			// HttpSession이 존재하면 현재 있는 HttpSession반환
			// 존재하지 않는다면 새로 생성하지 않고 null반환
			if (session != null) {
				// 세션이 존재한다면 꼭 invalidate 줘야함!

				// 세션 무효화 시키는 방법
				// 1. HttpSession의 invalidate()
				session.invalidate();
				// 2. HttpSession의 setMaxInactiveInterval(s)
				// 시간을 0초로 두기
				// 3. web.xml 이용
				/*
				 * <session-config> 
				 * 	<session-timeout>분</session-timeout> 
				 * </session-config>
				 */
			}
			page = "index.jsp";
			request.setAttribute("msg", "성공적으로 회원탈퇴를 했습니다.");
		} else {
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "회원탈퇴에 실패하였습니다.");
		}
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
