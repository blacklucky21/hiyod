package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import board.model.vo.Reply;

@WebServlet("/detail.bo")
public class BoardDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardDetailServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bid = Integer.parseInt(request.getParameter("bid"));
		
		BoardService service = new BoardService();
		new BoardService().updateCount(bid);
		Board board = service.selectBoard(bid);
		
		/**************************ajax 추가시**********************/
		ArrayList<Reply> list = new BoardService().selectReplyList(bid);
		/*********************************************************/
		
		String page = null;
		if (board != null) {
			page = "views/board/BoardDetailView.jsp";
			request.setAttribute("board", board);
			/**************************ajax 추가시**********************/
			request.setAttribute("list", list);
			//이다음 보드 디테일뷰
			/*********************************************************/
		} else {
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "게시판 조회에 실패했습니다.");
		}
		
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
