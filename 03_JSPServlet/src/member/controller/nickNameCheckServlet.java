package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;

@WebServlet("/nickNameCheck.me")
public class nickNameCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public nickNameCheckServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		String userNickName = request.getParameter("inputNickName");
		int result = new MemberService().nickNameCheck(userNickName);
		
		request.setAttribute("result", result);
		request.setAttribute("checkedNickName", userNickName);
		RequestDispatcher view = request.getRequestDispatcher("views/member/NickNamecheckForm.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
