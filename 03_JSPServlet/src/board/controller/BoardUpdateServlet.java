package board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.service.BoardService;
import board.model.vo.Board;
import member.model.vo.Member;

@WebServlet("/update.bo")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardUpdateServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
		String category =request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int bid = Integer.parseInt(request.getParameter("bid"));
		
		HttpSession session = request.getSession();
		Member loginUser = (Member)session.getAttribute("loginUser");
		String writer = loginUser.getUserId();
		
		Board b = new Board();
		b.setCategory(category);
		b.setbTitle(title);
		b.setbContent(content);
		b.setbWriter(writer);
		b.setbId(bid);
		
		int result = new BoardService().updateBoard(b);
		
		if(result >0) {
			response.sendRedirect("list.bo?currentPage=1");
		}else {
			RequestDispatcher view =request.getRequestDispatcher("views/common/errorPage.jsp");
			request.setAttribute("msg", "게시판 수정이 실패하였습니다.");
			view.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
