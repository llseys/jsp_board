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
			alert('순번 입력해주세요.')
			frmModi.bnum.focus();
		}else if(email==''){
			alert('이메일 입력해주세요.')
			frmModi.email.focus();
		}else if(subject==''){
			alert('제목을 입력해주세요.')
			frmModi.subject.focus();
		}else if(content==''){
			alert('내용을 입력해주세요.')
			frmModi.content.focus();
		}else{
			frmModi.submit();
		}
	}
</script>
</head>
<body>
	<h2>수정</h2>
	<form name="frmModi" action="${path}/board/modify" method="post" enctype="multipart/form-data">
		<table border="1">
		<tr>
			<th>순번</th>
			<td> <input type="text" name="bnum"  value="${map.board.bnum}"readonly> </td>
		</tr>
		<tr>
			<th>이메일</th>
			<td><input type="email" name="email" value="${map.board.email}"readonly></td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" name="subject" value="${map.board.subject}"></td>
		</tr>
		<tr>
			<th>내용</th>
			<td> <textarea rows="8" cols="30" name="content"></textarea> </td>
		</tr>
		<tr>
			<th>파일</th>
			<td>
				<c:forEach var="boardfile" items="${map.bflist}">
					${boardfile.filename} 삭제<input type="checkbox" name="filedel" value="${boardfile.fnum}""> <br> 
				</c:forEach>
				<input type="file" name="file1"> <br> 
				<input type="file" name="file2"> <br> 
				<input type="file" name="file3"> <br> 
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button type="button" onclick="modiCheck()">수정하기</button>
			 </td>
		</tr>
	</table>
	</form>

</body>
</html>