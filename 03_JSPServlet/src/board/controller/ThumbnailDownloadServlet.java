package board.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Attachment;

@WebServlet("/download.th")
public class ThumbnailDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThumbnailDownloadServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int fid = Integer.parseInt(request.getParameter("fid"));
		Attachment at = new BoardService().selectAttachment(fid);
		ServletOutputStream downOut = response.getOutputStream();
		// 클라이언트로 내보낼 출력 스트림 생성

		File downFile = new File(at.getFilePAth() + at.getChangeName());

		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + new String(at.getOriginName().getBytes("UTF-8"), "ISO-8859-1") + "\"");
		response.setContentLength((int) downFile.length());
		FileInputStream fin = new FileInputStream(downFile);
		BufferedInputStream buf = new BufferedInputStream(fin);

		int readBytes = 0;
		while ((readBytes = buf.read()) != -1) {
			downOut.write(readBytes);
		}
		
		downOut.close();
		buf.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
