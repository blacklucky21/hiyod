<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>idCheckForm</title>
<script type="text/javascript">
	function idValue() {
		if('<%=request.getAttribute("checkedId")%>' == "null") {
			  // 서블릿에 왓다갓다 하지않았으면 처음에 가져온 값으로 해줌
	         document.getElementById("inputId").value = opener.document.joinForm.joinUserId.value;
		// joinForm은 회원가입하는데 form이름임!
		}else{
			 document.getElementById("inputId").value = '<%=(String) request.getAttribute("checkedId")%>';
		}
	}
	function usedId() {
		opener.document.joinForm.joinUserId.value = document
				.getElementById("inputId").value;
		self.close();
	}
</script>
</head>
<body onload="idValue();">
	<b>아이디 중복 체크</b>
	<br>
	<form action="<%=request.getContextPath()%>/idCheck.me"
		id="idCheckForm">
		<input type="text" id="inputId" name="inputId"> <input
			type="submit" value="중복확인">
	</form>
	<br>
	<%
		if (request.getAttribute("result") != null) {
			int result = (int) request.getAttribute("result");
			if (result > 0) {
	%>
	이미 사용 중인 아이디 입니다.
	<%
		} else {
	%>
	사용 가능한 아이디 입니다.
	<%
		}
		}
	%>
	<br>
	<br>
	<input type="button" id="cancel" value="취소" onclick="window.close();">
	<input type="button" id="usedId" value="확인" onclick="usedId();">
</body>
</html>