<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
</head>
<body>
<c:import url="../temp/header.jsp"/>

<h1>My Page</h1>
<h1>ID : ${select.id}</h1>
<h1>Name : ${select.name}</h1>
<h1>Phone : ${select.phone}</h1>
<h1>Email : ${select.email}</h1>
<h1>Age : ${select.age}</h1>

<a href="./memberUpdate.jsp?id=">update</a>
<a href="./memberDelete.jsp?id=">delete</a>

<c:import url="../temp/footer.jsp"/>
</body>
</html>