<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="./include/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	header{
		display: flex;
		justify-content: space-around;
	}
</style>
</head>
<body>
	<header>
		<div>
			<img alt="" src="${path}/views/images/newItem.jpg" width="50">
		</div>
		<div>이젠컴퓨터</div>
		<div>
			<span>  <!-- 내정보 보기  -->
				<a href="${path}/member/myInfo">${sessionScope.email}</a> 
			</span> 
			<c:if test="${empty sessionScope.email}">  <!-- 비 로그인시 -->
				<a href="${path}/views/member/login.jsp">Login</a> 
				<a href="${path}/views/member/add.jsp">Join</a>
			</c:if>
			<c:if test="${not empty sessionScope.email}"> <!-- 로그인시 -->
				<a href="${path}/member/logout">Logout</a>  <!-- 컨트롤러에서 로그아웃할수있게 호출 -->
			</c:if>
			

		</div>
	</header>
	<hr>
	<nav>
		<a href="${path}/views/home.jsp">Home</a>
		<a href="${path}/board/list">리스트</a>
		<a href="${path}/views/board/add.jsp">등록하기</a>
	</nav>
</body>
</html>