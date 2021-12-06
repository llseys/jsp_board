<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../include/include.jsp" %>
    <%@ include file="../include/msg.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function loginCheck() {
		var email = frmLogin.email.value;
		var userpw = frmLogin.userpw.value;
		if(email==''){
			alert('이메일을 입력해주세요');
			frmLogin.email.focus();
		}else if(userpw==''){
			alert('비밀번호를 입력해주세요');
			frmLogin.userpw.focus();
		}else{
			frmLogin.submit();
		}
	}

</script>
</head>
<body>
	<h2>로그인</h2>
	<%@ include file="../header.jsp" %> l
	<form name="frmLogin" action="${path}/member/login" method="post">
		<table border="1">
			<tr>
				<th>이메일</th>
				<td> <input type="text" name="email" value="${cookie.email.value}"></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td> <input type="password" name="userpw" value=""></td>
			</tr>
			<tr>
				<td colspan="2" align="center"> 이메일저장<input type="checkbox" name="emailsave" checked></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="button" onclick="loginCheck()">로그인</button>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>