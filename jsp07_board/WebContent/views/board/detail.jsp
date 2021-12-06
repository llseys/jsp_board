<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../include/include.jsp" %>
    <%@ include file="../include/msg.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.reply{
		display: flex;
		color: green;
	}
	i{
		color: red;
		transform:rotate(180deg);
	}


</style>

<script type="text/javascript">
	//수정폼으로 이동(권한체크)
	function modiformCheck() {
		if('${sessionScope.email}'=='${map.board.email}'){
			location.href='${path}/board/modiForm?bnum=${map.board.bnum}';
		}else{
			alert('수정권한이 없습니다');
		}
	}


	function removeCheck() {
		//글 삭제 권한체크
		if('${sessionScope.email}'!='${map.board.email}'){
			alert('삭제권한이 없습니다');
			return;
		}
		var result	= confirm('삭제하겠습니까?');
		if(result){
			location.href="${path}/board/remove?bnum=${map.board.bnum}";
		}
	}
	
	// 댓글수정 권한
	function replymodiCheck(remail, rnum) {
		alert('접속');
		console.log(remail);
		if('${sessionScope.email}'==remail){
			location.href='${path}/reply/modiform?bnum='+rnum;
		}else{
			alert('댓글수정권한이 없습니다.');
		} 
	}
	
	// 댓글 삭제 권한
	/* rnum 댓글삭제키 
	   bnum 원본글키(컨트롤러에서 디테일올때 원본bnum 필요) */
	function replyremoveCheck(remail, rnum) {

		if('${sessionScope.email}'!= remail){
			alert('댓글삭제권한이 없습니다.');
			return;
		}
		
		var result = confirm('댓글을 삭제하겠습니까?');
		if(result){
			location.href='${path}/reply/remove?rnum='+rnum+'&bnum=${map.board.bnum}'
		}
		
	}

	

</script>
</head>
<body>
	<h2>상세조회</h2>
	<%@ include file="../header.jsp" %>

		<table border="1">
			<tr>
				<th>순번</th>
				<td>${map.board.bnum}</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>${map.board.email}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${map.board.subject}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><pre>${map.board.content}</pre></td>
			</tr>

			<tr>
				<th>파일</th>
				<td>
					<c:forEach var="boardfile" items="${map.bflist}">
						${boardfile.filename} 
						<button onclick="location.href='${path}/file/filedown?filename=${boardfile.filename}'">다운</button>  <br>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${map.board.readcnt}</td>
			</tr>
			<tr>
				<th>등록일자</th>
				<td> <fmt:formatDate value="${map.board.regidate}" pattern="yyyy.MM.dd-HH.mm.ss"/> </td>
			</tr>
			<tr>
				<th>조회수</th>
				<td><fmt:formatDate value="${map.board.modidate}" pattern="yyyy.MM.dd-HH.mm.ss"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button onclick="modiformCheck()">수정하기</button>
					<button onclick="removeCheck()">삭제하기</button>
					<button onclick="location.href='${path}/views/board/replyAdd.jsp?ref=${map.board.ref}&restep=${map.board.restep}&relevel=${map.board.relevel}'">댓글</button>
				</td>
			</tr>
		</table>
		
		<hr>
		
		<h2>댓글창</h2>
		<c:forEach var="reply" items="${map.relist}">
		<div class="reply">
			<div> 
				<c:forEach var="i" begin="1" end="${reply.relevel}">
					<i class="fas fa-reply"></i>
				</c:forEach>
			 </div>
			<div>
				bnum : ${reply.bnum} <br>
 				작성자 : ${reply.email} <br>
				제목 : ${reply.subject} <br>
				내용 : ${reply.content} <br>
				등록일자 : <fmt:formatDate value="${reply.regidate}" pattern="yyyy.MM.dd-HH.mm.ss"/> <br>
				수정일자 : <fmt:formatDate value="${reply.modidate}" pattern="yyyy.MM.dd-HH.mm.ss"/><br>
				<button onclick="location.href='${path}/views/board/replyAdd.jsp?ref=${reply.ref}&restep=${reply.restep}&relevel=${reply.relevel}'">댓글</button> 
				<button onclick="replymodiCheck('${reply.email}',${reply.bnum})">수정</button> 
				<button onclick="replyremoveCheck('${reply.email}',${reply.bnum})">삭제</button> 
			</div>
		</div>
		<hr>
		</c:forEach>
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

</body>
</html>