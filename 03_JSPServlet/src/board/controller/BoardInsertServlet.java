package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.service.BoardService;
import board.model.vo.Board;
import member.model.vo.Member;

@WebServlet("/insert.bo")
public class BoardInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardInsertServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
		String category =request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		HttpSession session = request.getSession();
		Member loginUser = (Member)session.getAttribute("loginUser");
		String writer = loginUser.getUserId();
		
		Board b = new Board();
		b.setCategory(category);
		b.setbTitle(title);
		b.setbContent(content);
		b.setbWriter(writer);
		
		int result = new BoardService().insertBoard(b);
		
		if(result >0) {
			response.sendRedirect("list.bo?currentPage=1");
		}else {
			request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "게시판 조회가 실패하였습니다.");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
