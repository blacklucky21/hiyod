<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="board.model.vo.*, java.util.*"%>
<%
	Board board = (Board) request.getAttribute("board");
	ArrayList<Attachment> fileList = (ArrayList<Attachment>) request.getAttribute("fileList");
	Attachment titleImg = fileList.get(0);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>thumbnailDetailView</title>
<style>
.outer {
	width: 1000px;
	height: 735px;
	background: rgba(255, 255, 255, 0.4);
	border: 5px solid white;
	margin-left: auto;
	margin-right: auto;
	margin-top: 50px;
}

.detail {
	text-align: center;
}

.detail th, .detail td {
	width: 1000px;
	padding: 10px;
	background: rgba(255, 255, 255, 0.4);
}

.detail th {
	background: white;
}

#titleImgArea {
	width: 500px;
	height: 300px;
	margin-left: auto;
	margin-right: auto;
}

#contentArea {
	height: 30px;
}

.detailImgArea {
	width: 250px;
	height: 210px;
	margin-left: auto;
	margin-right: auto;
}

#titleImg {
	width: 500px;
	height: 300px;
}

.detailImg {
	width: 250px;
	height: 180px;
}

.downBtn {
	width: 80px;
	height: 25px;
	color: white;
	border-radius: 5px;
	background: #D1B2FF;
}

#thumbTable {
	margin: auto;
}
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp"%>
	<div class="outer">
		<table class="detail" id="thumbTable">
			<tr>
				<th width="50px">제목</th>
				<td colspan="5"><%=board.getbTitle()%></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%=board.getbWriter()%>></td>
				<th>조회수</th>
				<td><%=board.getbCount()%></td>
				<th>작성일</th>
				<td><%=board.getCreateDate()%></td>
			</tr>
			<tr>
				<th>대표<br>사진
				</th>
				<td colspan="4">
					<div id="titldImgArea" align="center">
						<img id="titleImg"
							src="<%=request.getContextPath()%>/thumbnail_uploadFiles/<%=titleImg.getChangeName()%>">
					</div>
				</td>
				<td>
					<button
						onclick="location.href='<%=request.getContextPath()%>/download.th?fid=<%=titleImg.getfId()%>'"
						class="downBtn">다운로드</button>
				</td>
			</tr>
			<tr>
				<th>사진<br>메모
				</th>
				<td colspan="6">
					<p id="contentArea">
						<%
							String memo = board.getbContent();
						%>
						<%
							if (memo == null) {
						%>
						(메모없음)
						<%
							} else {
						%>
						<%=board.getbContent()%>
						<%
							}
						%>
					</p>
				</td>
			</tr>
		</table>

		<table class="detail">
			<tr>
			<%for(int i = 1; i <fileList.size();i++){ %>
				<td>
					<div class="detailImgArea">
						<img id="detailImg" class="detailImg" src="<%=request.getContextPath()%>/thumbnail_uploadFiles/<%=fileList.get(i).getChangeName()%>">
						<button onclick="location.href='<%=request.getContextPath()%>/download.th?fid=<%=fileList.get(i).getfId()%>'"class="downBtn">다운로드</button>
					</div>
				</td>
			<%} %>
			</tr>
		</table>
	</div>
</body>
</html>