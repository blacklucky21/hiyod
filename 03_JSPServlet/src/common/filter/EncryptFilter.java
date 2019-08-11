package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import common.wrapper.EncryptWrapper;

/**
 * Servlet Filter implementation class EncryptFilter
 */
@WebFilter(filterName = "encrypt", urlPatterns = { "/encrypt" }, servletNames = { "InsertMemberServlet", "LoginServlet",
		"UpdatePwdServlet" })
//꼭꼭 서블릿에 이름 처리해줘야함!
//얘도 web.xml에서 만들 수 있음!
public class EncryptFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public EncryptFilter() {
		// TODO Auto-generated constructor stub
		System.out.println("EncryptFilter 작동");
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest hsr = (HttpServletRequest) request;
		EncryptWrapper ew = new EncryptWrapper(hsr);
		// 암호화 래퍼 객체 생성

		// pass the request along the filter chain
		chain.doFilter(ew, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
