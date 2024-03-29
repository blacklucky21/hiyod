package notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.service.NoticeService;

@WebServlet("/delete.no")
public class NoticeDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NoticeDeleteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int nno = Integer.parseInt(request.getParameter("no"));
		int result = new NoticeService().delteNotice(nno);

		if (result > 0) {
			response.sendRedirect("/list.no");
		} else {
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "공지사항 삭제를 실패하였습니다.");
			view.forward(request, response);
		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
