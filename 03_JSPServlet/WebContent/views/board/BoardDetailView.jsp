<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="board.model.vo.*, java.util.*"%>
<%
	Board board = (Board) request.getAttribute("board");
	Member member = (Member) session.getAttribute("loginUser");
	//ajax 
	ArrayList<Reply> list = (ArrayList<Reply>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.outer {
	width: 800px;
	min-height: 500px;
	background: rgba(255, 255, 255, 0.4);
	border: 5px solid white;
	margin-left: auto;
	margin-right: auto;
	margin-top: 50px;
}

.tableArea {
	width: 450px;
	height: 350px;
	margin-left: auto;
	margin-right: auto;
	align: center;
}

table {
	align: center;
	margin: auto;
}
/* ajax  table과 #addReply , #addReply:hover 추가함*/
#updateBtn, #menuBtn, #deleteBtn, #addReply {
	background: #B2CCFF;
	color: white;
	border-radius: 15px;
	width: 80px;
	heigth: 25px;
	text-align: center;
	display: inline-block;
}

#menuBtn {
	background: #D1B2FF;
}

#deleteBtn {
	background: #D5D5D5;
}

#updateBtn:hover, #menuBtn:hover, #deleteBtn:hover, #addReply:hover {
	cursor: pointer;
}
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp"%>
	<%-- <% System.out.println(msg); %> --%>

	<div class="outer">
		<br>
		<h2 align="center">게시판 상세보기</h2>
		<div class="tableArea">
			<form
				action="<%=request.getContextPath()%>/views/board/BoardUpdateForm.jsp"
				id="detailForm" method="post">
				<table>
					<tr>
						<th>분야</th>
						<td><%=board.getCategory()%> <input type="hidden" name="bid"
							value="<%=board.getbId()%>"> <input type="hidden"
							name="category" value="<%=board.getCategory()%>"></td>
						<th>제목</th>
						<td colspan="3"><input type="hidden" name="title"
							value="<%=board.getbTitle()%>"><%=board.getbTitle()%></td>
					</tr>
					<tr>
						<th>작성자</th>
						<td><input type="hidden" name="writer"
							value="<%=board.getbWriter()%>"><%=board.getbWriter()%></td>
						<th>조회수</th>
						<td><input type="hidden" name="writer"
							value="<%=board.getbWriter()%>"><%=board.getbCount()%></td>
						<th>작성일</th>
						<td><input type="hidden" name="createDate"
							value="<%=board.getCreateDate()%>"><%=board.getCreateDate()%></td>
					</tr>
					<tr>
						<th>내용</th>
					</tr>
					<tr>
						<td colspan="6"><textarea cols="60" rows="15"
								style="resize: none;" name="content" readonly><%=board.getbContent()%></textarea>
						</td>
					</tr>
				</table>

				<div align="center">
					<%
						if (member.getNickName().equals(board.getbWriter()) || member.getUserId().equals("admin")) {
					%>
					<input type="submit" id="updateBtn" value="수정"> <input
						type="button" onclick="deleteBoard();" id="deleteBtn" value="삭제">
					<%
						}
					%>
					<div
						onclick="location.href='<%=request.getContextPath()%>/list.bo'"
						id="menuBtn">메뉴로</div>
				</div>
			</form>
		</div>

		<!-- ajax시작 -->
		<div class="replyArea">
			<div class="replyWriterArea">
				<table>
					<tr>
						<td>댓글 작성</td>
						<td><textarea rows="3" cols="80" id="replyContent" style="resize: none;"></textarea></td>
						<td>
							<button id="addReply">댓글등록</button>
						</td>
					</tr>
				</table>
			</div>
			<div class="replySelectArea">
				<table id="replySelectTable">
					<%
						if (list.isEmpty()) {
					%>
					<tr>
						<td colspan="3">댓글이 없습니다.</td>
					</tr>
					<%
						} else {
					%>
					<%
						for (int i = 0; i < list.size(); i++) {
					%>
							<tr>
								<td width="100px"><%=list.get(i).getrWriter() %></td>
								<td width="400px"><%=list.get(i).getrContent() %></td>
								<td width="200px"><%=list.get(i).getCreateDate() %></td>
							</tr>
					<%
						}
					%>
					<%
						}
					%>
				</table>
			</div>
		</div>
		<script type="text/javascript">
			$("#addReply").click(function(){
				var writer = '<%= loginUser.getUserId()%>';
				var bid = '<%=board.getbId()%>';
				var content = $("#replyContent").val();
				
				$.ajax({
					url: "insertReply.bo",
					//ReplyInsertServlet
					type:"post",
					data : {writer:writer, content:content, bid:bid},
					success:function(data){
						$replyTable = $("replySelectTable");
						$replyTable.html("");
						
						for(var key in data){
							var $tr = $("<tr>");
							//메소드체인
							var $writerTd = $("<td>").text(data[key].rWriter).css("width","100px");
							var $contentTd = $("<td>").text(data[key].rContent);
							var $dataTd = $("<td>").text(data[key].createDate);
							
							$tr.append($writerTd);
							$tr.append($contentTd);
							$tr.append($dataTd);
							$replyTable.append($tr);
						}
						$("#replyContent").val("");
					}
				});
			});
		</script>
		<!-- ajax 끝~~~~~  -->
		
		<script>
			function deleteBoard(){
				if(confirm('정말로 삭제하시겠습니까?')){
					location.href="<%=request.getContextPath()%>/delete.bo?bid="+<%=board.getbId()%>;
				}
			}
		</script>
	</div>
</body>
</html>