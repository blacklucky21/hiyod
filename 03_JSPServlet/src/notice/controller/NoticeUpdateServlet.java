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

@WebServlet("/update.no")
public class NoticeUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NoticeUpdateServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("no"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String date = request.getParameter("date");
		Date sqlDate = null;

	      if (date != "") {
	         String[] dateArr = date.split("-");
	         int year = Integer.parseInt(dateArr[0]);
	         int month = Integer.parseInt(dateArr[1])-1;
	         int day = Integer.parseInt(dateArr[2]);

	         sqlDate = new Date(new GregorianCalendar(year, month, day).getTimeInMillis());
	      } else {
	         sqlDate = new Date(new GregorianCalendar().getTimeInMillis());
	      }
	      Notice notice = new Notice();
	      notice.setnNo(no);
	      notice.setnTitle(title);
	      notice.setnContent(content);
	      notice.setnDate(sqlDate);
	      
	     int result = new NoticeService().updateNotice(notice);
	     String page=null;
	     if(result >0) {
	    	 page="/detail.no?no="+no;
	     }else {
	    	 page = "views/common/errorPage.jsp";
	    	 request.setAttribute("msg", "공지사항 수정에 실패하였습니다.");
	     }
	     RequestDispatcher view = request.getRequestDispatcher(page);
	     view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
