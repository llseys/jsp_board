<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	if('${sessionScope.email}'==''){
		alert('로그인 후 이용해주세요');
		location.href='${path}/views/member/login.jsp';
	} 
</script>
</head>
<body>

</body>
</html>