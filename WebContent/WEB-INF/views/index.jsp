<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="./temp/bootstrap.jsp" %>
</head>
<body>
<jsp:include page="./temp/header.jsp"></jsp:include> <!-- response가 나오기 전 -->

<h1>Index Page</h1>
<img src="./images/123.png" width="300px" height="300px">
<!-- 이미지는 WEB-INF로 지정 X -->

</body>
</html>