package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;

@WebServlet("/list.th")
public class ThumbnailListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThumbnailListServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BoardService service = new BoardService();
		// service 를 여러번 불러와야 할 때 service 객체를 만듬

		// 사진 게시판 리스트 정보 불러오기
		ArrayList<Board> bList = service.selectTList(1);
		// 사진 리스트 불러오기
		ArrayList<Attachment> fList = service.selectTList(2);

		String page = null;
		if (bList != null && fList != null) {
			request.setAttribute("bList", bList);
			request.setAttribute("fList", fList);
			page = "views/thumbnail/ThumbnailListView.jsp";
		} else {
			request.setAttribute("msg", "사진 게시판 조회에 실패하였습니다.");
			page = "views/common/errorPage.jsp";
		}
		request.getRequestDispatcher(page).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
