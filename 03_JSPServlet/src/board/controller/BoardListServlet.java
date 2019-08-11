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
import board.model.vo.PageInfo;

@WebServlet("/list.bo")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardListServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BoardService service = new BoardService();

		int listCount = service.getListCount();// 총게시글 개수를 가지고옴!

		/******************** 페이징 처리 **********************/
		int currentPage; // 현재페이지
		int limit; // 한 페이지에 표시 될 페이징 수
		int maxPage; // 전체 페이지 중 가장 마지막 페이지
		int startPage; // 페이징이 된 페이지 중 시작 페이지
		int endPage; // 페이징이 된 페이지 중 마지막 페이지

		currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			// 뭔가 페이지를 눌렀다는거!!
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		limit = 10;
		// 페이지 수를 10개를 하겠다!

		maxPage = (int) ((double) listCount / limit + 0.9);
		// 전체 리스트갯수 / 한페이지 리스트 나눠서 내림한거(int)형변환
		startPage =  (((int)((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		// 시작 페이지 = 전체 리스트/ 한페이지 리스트 나눠서 -1 하고 (곱하기!)한페이지 들어갈 숫자!
		// 내림해서 한페이지(숫자) 곱하고 +1
		endPage = startPage + limit - 1;
		// 마지막 페이지 = 시작페이지 + 한개 페이지 숫자 - 1;
		if (maxPage < endPage) {
			endPage = maxPage;
		}
		PageInfo pi = new PageInfo(currentPage, listCount, limit, maxPage, startPage, endPage);

		ArrayList<Board> list = service.selectList(currentPage);

		String page = null;
		if (list != null) {
			page = "views/board/boardListView.jsp";
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
		} else {
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "게시판 조회가 실패하였습니다.");
		}
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
