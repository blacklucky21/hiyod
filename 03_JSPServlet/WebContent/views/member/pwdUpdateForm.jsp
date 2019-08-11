<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/menubar.jsp" %>
<%-- <%
	String msg = (String) request.getAttribute("msg");
--%>
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

input {
	margin-top: 2px;
}

#updatePwdBtn, #cancelBtn {
	background: #B2CCFF;
	color: white;
	border-radius: 5px;
	width: 110px;
	heigth: 25px;
	text-align: center;
	display: inline-block;
}

#updatePwdBtn:hover, #cancelBtn:hover {
	cursor: pointer;
}

#updatePwdForm td {
	text-align: right;
	height: 50px;
}

#cancelBtn {
	background: #D1B2FF;
}

table {
	margin: auto;
}
</style>
<!-- 비밀번호가 같은지 다른지 확인하는 유효성검사도 넣기 -->
<title>비밀번호 변경</title>
<script type="text/javascript">
	function send() {
		if ($('#userPwd').val().trim().length == 0) {
			alert("비밀번호를 입력해주세요!");
			return false;
		}
		if ($('#newPwd').val().trim().length == 0
				|| $('#newPwd2').val().trim().length == 0) {
			alert("새로운 비밀번호와 비밀번호 확인을 모두 입력해주세요!");
			return false;
		}
		if (document.getElementById("newPwd").value != document
				.getElementById("newPwd2").value) {
			alert("비밀번호 확인이  일치하지 않습니다! 변경할 비밀번호를 다시 입력해주세요.");
			$('#newPwd').focus();
			return false;
		}
		return true;
	}
	
	var msg="<%=msg%>";
	
	$(function(){
		if(msg!="null")
		alert(msg);
	});
</script>
</head>
<body>


	<div class="outer">
		<br>
		<h2 align="center">비밀번호 수정하기</h2>

		<form action="<%=request.getContextPath()%>/updatePwd.me"
			method="post" id="updatePwdForm" name="updatePwdForm"
			onsubmit="return send();">
			<table>
				<tr>
					<td><label>현재 비밀번호</label></td>
					<td><input type="password" name="userPwd" id="userPwd"></td>
				</tr>
				<tr>
					<td><label>변경 비밀번호</label></td>
					<td><input type="password" name="newPwd" id="newPwd"></td>
				</tr>
				<tr>
					<td><label>변경 비밀번호 확인</label></td>
					<td><input type="password" name="newPwd2" id="newPwd2"></td>
				</tr>
			</table>

			<br> <br>

			<div class="btns" align="center">
				<input id="updatePwdBtn" type="submit" value="변경하기">
				<div id="cancelBtn"
					onclick="location.href='javascript:history.back()'">취소하기</div>
			</div>
		</form>
	</div>
</body>
</html>