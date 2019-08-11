<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String category = request.getParameter("category");
	String[] bList = new String[7];
	
	switch(category){
	case "공통":
		bList[0] = "selected";
		break;
	case "운동":
		bList[1] = "selected";
		break;
	case "등산":
		bList[2] = "selected";
		break;
	case "게임":
		bList[3] = "selected";
		break;
	case "낚시":
		bList[4] = "selected";
		break;
	case "요리":
		bList[5] = "selected";
		break;
	case "기타":
		bList[6] = "selected";
		break;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.outer{
		width: 800px; height: 500px; background: rgba(255, 255, 255, 0.4); border: 5px solid white;
		margin-left: auto; margin-right: auto; margin-top: 50px;
	}
	.tableArea {width:500px; height:350px; margin-left:auto; margin-right:auto;}
	#updateBtn, #cancelBtn{background: #B2CCFF; border-radius: 10px; color: white; width: 80px; height: 25px; display: inline-block;}
	#updateBtn:hover, #cancelBtn:hover{cursor: pointer;}
	#cancelBtn{background: #D1B2FF;}
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
		
	<div class="outer">
		<br>
		<h2 align="center">게시판 수정</h2>
		<div class="tableArea">
			<form action="<%= request.getContextPath() %>/update.bo" method="post">
				<table>
					<tr>
						<th>분야</th>
						<td>
							<select name="category">
								<option>--------</option>
								<option value="10" <%=bList[0] %>>공통</option>
								<option value="20" <%=bList[1] %>>운동</option>
								<option value="30" <%=bList[2] %>>등산</option>
								<option value="40" <%=bList[3] %>>게임</option>
								<option value="50" <%=bList[4] %>>낚시</option>
								<option value="60" <%=bList[5] %>>요리</option>
								<option value="70" <%=bList[6] %>>기타</option>
							</select>
							<input type="hidden" name="bid" value="<%=Integer.parseInt(request.getParameter("bid")) %>">
						</td>	
					</tr>
					<tr>
						<th>제목</th>
						<td colspan="3"><input type="text" size="58" name="title" value="<%= request.getParameter("title")%>"></td>
					</tr>
					<tr>
						<th>내용</th>
						<td colspan="3">
							<textarea rows="15" cols="60" name="content" style="resize:none;"><%= request.getParameter("content")%></textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<button type="submit" id="updateBtn">수정</button>
					<div id="cancelBtn" onclick="location.href='<%= request.getContextPath() %>/list.bo'">취소</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>