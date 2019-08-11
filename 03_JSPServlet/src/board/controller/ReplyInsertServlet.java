package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import board.model.service.BoardService;
import board.model.vo.Reply;

@WebServlet("/insertReply.bo")
public class ReplyInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyInsertServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//필터때무에 굳이 인코딩 안해줘도됨!
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		int bid = Integer.parseInt(request.getParameter("bid"));
		
		Reply r = new Reply();
		r.setrWriter(writer);
		r.setrContent(content);
		r.setRefBid(bid);
		
		ArrayList<Reply> list = new BoardService().insertReply(r);
		//삽입 하자마자 등록된걸 봐야하기 때문에 서비스에서 조회하는것까지 같이하기 위해서 조회해서 가져오는것까지 같이할것임 그래서 ArrayList 로받아오는것!

		//gson lib에 넣기
		response.setContentType("application/json,charset = utf-8");
		//charset =utf-8도 필터에 잇어서 여기서 생략 가능
//		new Gson().toJson(list,response.getWriter());
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		gson.toJson(list,response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
