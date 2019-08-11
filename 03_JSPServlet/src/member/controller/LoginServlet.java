package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
/* @WebServlet("/login.me") */
@WebServlet(name="LoginServlet",urlPatterns="/login.me")

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 인코딩 처리
		// 지금은 필요하지 않음 ==> id 와 pw는 영어로 되어있기 때문에

		// 2. 전송 값 꺼내서 변수 또는 객체에 기록
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		System.out.println(userPwd);
		Member member = new Member(userId, userPwd);

		// 3. 비즈니스 로직 처리하기 위해 서비스 클래스의 해당 메소드 실행, 그 처리 결과 받음
		Member loginUser = new MemberService().loginMember(member);

		// 4. 보낼 값에 한글이 있을 경우 인코딩 처리
		response.setContentType("text/html charset=UTF-8");
		// 지금은 필요 없음, 왜냐하면 Servlet 안에서 자체적으로 한글이 들어갔을 경우 인코딩 처리

		// 5. 서비스 요청에 해당하는 결과를 가지고 성공/실패에 대한 뷰 페이지 내보내기
		if (loginUser != null) {
			// 해당 클라이언트에 대한 세션 객체 생성
			HttpSession session = request.getSession();
//			HttpSession session = request.getSession(true);
//			괄호에 true 있으나 없으나 상관 x

			session.setMaxInactiveInterval(600);// 10분 뒤에 자동 로그아웃
			session.setAttribute("loginUser", loginUser);
			//세션에 담아놨기 때문에 저장되는 영역이 달라서 그냥 페이지 이동을 해도 문제x. 
			//session의 영역은 브라우져 까지임!
			
			response.sendRedirect("index.jsp");
			//sendRedirect를 보냈을때 새로운 responese 를 만들어냄. 그래서 정보를 전달할수 없음
			//session영역과 requeset영역은 다르기때문에
			
//			request setAttribute 은 값을 담아보냄
//			sendRedirect 는 값을 안담아보냄 단순한 페이지 전환!!!!

		} else {
			request.setAttribute("msg", "로그인 실패");
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request,response);
			// RequestDispatcher와  forward는 세트임!!!
;		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
