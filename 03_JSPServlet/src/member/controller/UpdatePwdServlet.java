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
import member.model.vo.Member;

/*@WebServlet("/updatePwd.me")
*/
@WebServlet(name="UpdatePwdServlet",urlPatterns="/updatePwd.me")

public class UpdatePwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdatePwdServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userPwd = (String)request.getParameter("userPwd");
		String newPwd = (String)request.getParameter("newPwd");
		HttpSession session = request.getSession(true);
		Member sessionMember = (Member) session.getAttribute("loginUser");
		String usedPwd = sessionMember.getUserPwd();

		int result = 0;
		
		String page = null;
		if (!userPwd.equals(usedPwd)) {
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "비밀번호가 일치하지 않습니다.");
		} else {
			
			result = new MemberService().updatePwd(sessionMember.getUserId(), newPwd);
			
			if (result > 0) {
				page = "/myPage.me";
				request.setAttribute("msg", "비밀번호가 성공적으로 변경되었습니다.");
			} else {
				page = "views/common/errorPage.jsp";
				request.setAttribute("msg", "비밀번호변경에 실패하였습니다.");
			}
		}
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
