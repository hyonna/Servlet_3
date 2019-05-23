<%@page import="com.iu.notice.NoticeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%--  
    <%
    
    NoticeDTO noticeDTO = (NoticeDTO)request.getAttribute("select");
    
    %> --%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../temp/bootstrap.jsp"></jsp:include>
<title>Insert title here</title>
<jsp:include page="../temp/header.jsp"></jsp:include>
</head>
<body>


<div class="container">

<h1>noticeSelect Page</h1>

<table class="table table-hover">

	<tr>
	
		<td>NUM</td>
		<td>TITLE</td>
		<td>CONTENTS</td>
		<td>NAME</td>
		<td>DATE</td>
		<td>HIT</td>
		<td>PARAM</td>
		<td>name비교연산자</td>
		
	</tr>
	<tr>
		<td>${select.num}</td> <!-- 내가 꺼내고자 하는 영역 (영역이름.가져온속성이름.getNum 인 경우 get은 생략 가능-->
		<td>${select.title}</td>
		<td>${select.contents}</td>
		<td>${select.name}</td>
		<td>${select.reg_date}</td>
		<td>${select.hit}</td>
		<td>${param.num*select.num}</td> <%-- [산술연산자] ${a+b} : +, -, *, /, % --%>
		<td>${select.name == 'admin'}</td> <%-- [비교연산자] ${a>b} : ==, !=, >, <, >=, <= --%>
	
	
	
	</tr>

</table>

<a href="./noticeUpdate?num=${select.num}" class="btn btn-danger">Update</a>
<a href="./noticeDelete?num=${select.num}" class="btn btn-danger">Delete</a>

</div>


</body>
</html>