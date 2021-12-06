<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../include/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function modiCheck() {
		var bnum = frmModi.bnum.value;
		var email = frmModi.email.value;
		var subject = frmModi.subject.value;
		var content = frmModi.content.value;
		
		
		
		if(bnum==''){
			alert('번호입력 해주세요');
			frmModi.bnum.focus();
		}else if(email==''){
			alert('이메일 입력해주세요');
			frmModi.email.focus();
		}else if(subject==''){
			alert('제목을 입력해주세요');
			frmModi.subject.focus();
		}else if(content==''){
			alert('내용을 입력해주세요');
			frmModi.content.focus();
		}else{
			frmModi.submit();
		}
		
	}
</script>
</head>
<body>
	<h2>댓글수정</h2>
	${board} <hr>
	<form name="frmModi" action="${path}/reply/modify">
		<input type="hidden" name="ref" value="${board.ref}"> 
		번호  <input type="text" name="bnum" value="${board.bnum}" readonly> <br>
		이메일  <input type="email" name="email" value="${board.email}"readonly> <br>
		제목 <input type="text" name="subject" value="${board.subject}"> <br>
		내용  <textarea rows="4" cols="20" name="content"></textarea> <br>
		<button type="button" onclick="modiCheck()">수정하기</button>
	</form>

</body>
</html>