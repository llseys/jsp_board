<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./include/include.jsp" %>
<%@ include file="./include/msg.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	img#home{
		width : 70%;
		display: block;
		margin: auto;
	}
</style>
</head>
<body>
	<%@ include file="header.jsp" %>
	<div>
		<img id="home" alt="" src="${path}/views/images/hamster.jpg">
	</div>
</body>
</html>