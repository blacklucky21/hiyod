<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NickNamecheckForm</title>
<script type="text/javascript">
	function nickNameValue() {
		if(opener.document.getElementById("updateForm")){
			if('<%=request.getAttribute("checkedNickName")%>'=='null'){
				console.log(123);
				<%--servlet에 왓다갓다 하지 않았을 경우 --%>
				document.getElementById("inputNickName").value = opener.document.updateForm.nickName.value;
				<%--나를 부른 상위 창을 입력--%>
			}else{
				console.log(321);
				document.getElementById("inputNickName").value ='<%=(String) request.getAttribute("checkedNickName")%>';
			}
		}else if(open.document.getElementById("joinForm")){
			if('<%=request.getAttribute("checkedNickName")%>' == "null") {
				if('<%=request.getAttribute("checkedNickName")%>' == "null" ){
					document.getElementById("inputNickName").value = opener.document.joinForm.nickName.value;
				}else{
					document.getElementById("inputNickName").value ='<%=(String) request.getAttribute("checkedNickName")%>';
				}
			}
		}
	}

	function useNickName() {
		if (opener.document.getElementById("updateForm")) {
			opener.document.updateForm.nickName.value = document.getElementById("inputNickName").value;
			self.close();
		} else if (open.document.getElementById("joinForm")) {
			opener.document.joinForm.nickName.value = document
					.getElementById("inputNickName").value;
			self.close();
		}
	}
</script>
</head>
<body onload="nickNameValue();">
	<b>닉네임 중복 체크</b>
	<br>
	<form action="<%=request.getContextPath()%>/nickNameCheck.me"
		id="nickNameCheckForm">
		<input type="text" id="inputNickName" name="inputNickName"> <input
			type="submit" value="중복확인">
	</form>
	<br>
	<%
		if (request.getAttribute("result") != null) {
			int result = (int) request.getAttribute("result");
			if (result > 0) {
	%>
	이미 사용 중인 닉네임 입니다.
	<%
		} else {
	%>
	사용 가능한 닉네임 입니다.
	<%
		}
		}
	%>
	<br>
	<br>

	<input type="button" id="cancle" value="취소" onclick="window.close();">
	<input type="button" id="useNickName" value="확인" onclcick="useNickName();">
</body>
</html>