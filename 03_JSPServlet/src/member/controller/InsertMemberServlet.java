package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/*@WebServlet("/insert.me")
*/
@WebServlet(name="InsertMemberServlet",urlPatterns="/insert.me")

public class InsertMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 인코딩 처리 - 한국어로 넘어가는것 多
//		request.setCharacterEncoding("UTF-8");

		// 2. 전송 값 꺼내서 변수 및 객체에 저장
		String userId = request.getParameter("joinUserId");
		String userPwd = request.getParameter("joinUserPwd");
		String userName = request.getParameter("userName");
		String nickName = request.getParameter("nickName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String irr[] = request.getParameterValues("interest");
		String interest = "";
		if (irr != null) {
			for (int i = 0; i < irr.length; i++) {
				if (i == irr.length - 1) {
					interest += irr[i];
				} else {
					interest += irr[i] + ",";
				}
			}
		}
		Member member = new Member(userId, userPwd, userName, nickName, phone, email, address, interest);
		int result = new MemberService().insertMember(member);

		String page = "";
		if (result > 0) {
			page = "index.jsp";
			request.setAttribute("msg", "회원가입에 성공하였습니다.");
		} else {
			page="view/common/errorPage.jsp";
			request.setAttribute("msg", "회원가입에 실패하였습니다.");
		}
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
