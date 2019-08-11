package member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;

@WebServlet("/idCheck.me")
public class IdCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IdCheckServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String userId = request.getParameter("inputId");
		String userId = request.getParameter("userId");
		int result = new MemberService().idCheck(userId);

		/*
		 * request.setAttribute("result", result); request.setAttribute("checkedId",
		 * userId); RequestDispatcher view =
		 * request.getRequestDispatcher("views/member/idCheckForm.jsp");
		 * view.forward(request, response);
		 */
		
		PrintWriter out = response.getWriter();
		if(result>0) {
			//실패시에!
			out.append("fail");
		}else {
			out.append("success");
		}
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
