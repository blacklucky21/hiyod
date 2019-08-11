package notice.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

@WebServlet("/insert.no")
public class NoticeInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NoticeInsertServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");

		String title = request.getParameter("title");
		String nickName = "운영자";
		String date = request.getParameter("date");
		String content = request.getParameter("content");

		Date sqlDate = null;
		if (date != "") {
			String[] dateArr = date.split("-");
			int year = Integer.parseInt(dateArr[0]);
			int month = Integer.parseInt(dateArr[1]) - 1;
			int day = Integer.parseInt(dateArr[2]);

			sqlDate = new Date(new GregorianCalendar(year, month, day).getTimeInMillis());
		} else {
			sqlDate = new Date(new GregorianCalendar().getTimeInMillis());
		}

		Notice n = new Notice(title, content, nickName, sqlDate);

		int result = new NoticeService().insertNotice(n);
		if (result > 0) {
			/*
			 * RequstDispatcher Vs sendRedirect
			 * 보낼값이 있을 때			보낼 값이 없을 때
			 * 						request/response객체를 새로 만들어주기 때문에
			 * */
			response.sendRedirect("list.no");
		} else {
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "공지사항 등록에 실패했습니다.");
			view.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
