<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Member member = (Member) request.getAttribute("member");
	/*메뉴바에서 가져오기때문에  import안해줘도됨???  */
	String userId = member.getUserId();
	String userName = member.getUserName();
	String nickName = member.getNickName();
	String phone = member.getPhone() != null ? member.getPhone() : "-";
	String email = member.getEmail() != null ? member.getEmail() : "-";
	String address = member.getAddress() != null ? member.getAddress() : "-";
	String interest = member.getInterest() != null ? member.getInterest() : "-";

	String updateMsg = (String) request.getAttribute("msg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.outer {
	width: 600px;
	height: 500px;
	background-color: rgba(255, 255, 255, 0.4);
	border: 5px solid white;
	color: black;
	margin-left: auto;
	margin-right: auto;
	margin-top: 50px;
}

.outer label, .outer td {
	color: black;
}

input {
	margin-top: 2px;
}

#deleteBtn, #goMain, #updateBtn, #pwdUpdateBtn {
	background: #B2CCFF;
	color: white;
	border-radius: 5px;
	width: 110px;
	heigth: 25px;
	text-align: center;
	display: inline-block;
}

#deleteBtn:hover, #updateBtn:hover, #goMain:hover, #pwdUpdateBtn:hover {
	cursor: pointer;
}

#myInfoForm td {
	text-align: right;
}

#goMain {
	background: #FFD8D8;
}

#updateBtn {
	background: #D1B2FF;
}

#deleteBtn {
	background: #D5D5D5;
}
</style>
<title>내 정보 보기</title>
</head>
<body>
	<%@ include file="../common/menubar.jsp"%>

	<div class="outer">
		<br>
		<h2 align="center">회원 수정</h2>

		<form action="views/member/memberUpdateForm.jsp" method="post"
			id="myInfoForm" name="myInfoForm">
			<table>
				<tr>
					<td width="200px">아이디</td>
					<td width="250px"><input type="hidden" name="userId"
						value="<%=userId%>"><%=userId%></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="hidden" name="userName" value="<%=userName%>"><%=userName%></td>
				</tr>
				<tr>
					<td>닉네임</td>
					<td><input type="hidden" name="nickName" value="<%=nickName%>"><%=nickName%></td>
				</tr>
				<tr>
					<td>연락처</td>
					<td><input type="hidden" name="phone" value="<%=phone%>"><%=phone%></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="hidden" name="email" value="<%=email%>"><%=email%></td>
				</tr>
				<tr>
					<td>주소</td>
					<td><input type="hidden" name="address" value="<%=address%>"><%=address%></td>
				</tr>
				<tr>
					<td>관심분야</td>
					<td><input type="hidden" name="interest" value="<%=interest%>"><%=interest%></td>
				</tr>
			</table>

			<br>

			<div class="btns" align="center">
				<div id="pwdUpdateBtn" onclick="location.href='views/member/pwdUpdateForm.jsp'">비밀번호 변경</div>
				<input id="updateBtn" type="submit" value="내 정보 수정">

				<hr width="70%">
				<div id="goMain"
					onclick="location.href='<%=request.getContextPath()%>/index.jsp'">메인으로</div>
				<div id="deleteBtn" onclick="delteMember();">탈퇴하기</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
         $(function() {
            if("<%=updateMsg%>" != "null"){
               alert("<%=updateMsg%>");
			}
		});

		function delteMember() {
			var bool = confirm("정말로 탈퇴하시겠습니까?");
			
			if(bool){
			$("#myInfoForm").attr("action","<%=request.getContextPath()%>/delete.me");
			$("#myInfoForm").submit();
			}
		}
	</script>
</body>
</html>