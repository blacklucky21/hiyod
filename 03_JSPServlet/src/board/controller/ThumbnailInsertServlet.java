package board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.MyFileRenamePolicy;
import member.model.vo.Member;

@WebServlet("/insert.th")
public class ThumbnailInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThumbnailInsertServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
//		String title = request.getParameter("title");
//		System.out.println(title);

		// cos.jar ==> 파일을 받고 다른 값 들도 받아오는 역할
		// cos == com.orelilly.servlet
		// servlets.com/cos/

		// encType 이 multipart/form-data로 전송되었는지 확인
		if (ServletFileUpload.isMultipartContent(request)) {
			int maxSize = 1024 * 1024 * 10;// 10Mbyte
			String root = request.getSession().getServletContext().getRealPath("/");
			String savePath = root + "thumbnail_uploadFiles/";
			System.out.println(savePath);
			// C:\dev\5_Servlet_JSP_workspace\03_JSPServlet\WebContent\thumbnail_uploadFiles/

			/*
			 * cos.jar에서 파일명 변환하는 클래스를 제공하고 있음 == DefaultFileRenamePolicy : 같은 파일 명이 존재하는지
			 * 검사하고 있을 경우에는 파일 명 뒤에 숫자를 붙여움 ex) aaa.jpg, aaa1.jpg, aaa2.jpg MultipartRequest
			 * mr = new MultipartRequest(request, savePath, maxSize, encoding new
			 * DefaultFileRenamePloicy());
			 * 
			 */
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8",
					new MyFileRenamePolicy());

			// 바뀐 파일의 이름을 저장할 ArrayList
			ArrayList<String> saveFiles = new ArrayList<String>();
			// 원본 파일의 이름을 저장할 ArrayList
			ArrayList<String> originFiles = new ArrayList<String>();

			Enumeration<String> files = multiRequest.getFileNames();
			// getFileNames() : (insert)폼에서 전송된 파일 리스트들의 name 반환
			while (files.hasMoreElements()) {
				String name = files.nextElement();

				if (multiRequest.getFilesystemName(name) != null) {
					// getFilesystemName() :MyFileReamePolicy의 rename메소드에서 작성한 rename 된 파일 명
					saveFiles.add(multiRequest.getFilesystemName(name));
					originFiles.add(multiRequest.getOriginalFileName(name));

				}
			}
			System.out.println(originFiles.size());
			// 이터레이터

			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			String bWriter = ((Member) request.getSession().getAttribute("loginUser")).getUserId();

			Board b = new Board();
			b.setbTitle(title);
			b.setbContent(content);
			b.setbWriter(bWriter);

			System.out.println(originFiles);

			ArrayList<Attachment> fileList = new ArrayList<Attachment>();
			for (int i = originFiles.size() - 1; i >= 0; i--) {
				Attachment at = new Attachment();
				at.setFilePAth(savePath);
				at.setOriginName(originFiles.get(i));
				at.setChangeName(saveFiles.get(i));

				if (i == originFiles.size() - 1) {
					at.setFileLevel(0);
				} else {
					at.setFileLevel(1);
				}
				fileList.add(at);
			}

			int result = new BoardService().insertThumbnail(b, fileList);

			if (result > 0) {
				response.sendRedirect("list.th");
			} else {
				for (int i = 0; i < saveFiles.size(); i++) {
					File failedFile = new File(savePath + saveFiles.get(i));
					failedFile.delete();
				}
				request.setAttribute("msg", "사진 게시판 등록에 실패했습니다.");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
