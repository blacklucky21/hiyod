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

@WebServlet("/myPage.me")
public class myPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public myPageServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member sessionMember = (Member) session.getAttribute("loginUser");
		String loginUserId = sessionMember.getUserId();

		Member member = new MemberService().selectMember(loginUserId);

		String page = null;
		if (member != null) {
			page = "views/member/memberView.jsp";
			request.setAttribute("member", member);
		} else {
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "회원조회에 실패했습니다.");
			// 삭제할 때는 removeAttribute("이름");사용 "이름은" =>"member"이거 말하는것
		}
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
