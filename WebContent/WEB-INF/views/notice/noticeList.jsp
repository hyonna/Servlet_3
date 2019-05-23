<%@page import="com.iu.notice.NoticeDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- prefix(접두어=태그명) -->
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<c:import url="../temp/bootstrap.jsp"/> <%-- <jsp:include page="../temp/bootstrap.jsp"></jsp:include>와 동일한 코드 --%>

</head>
<body>
<c:import url="../temp/header.jsp"/>

<div class="container">

<h1>NoticeList Page</h1>

<table class="table table-hover">

	<tr>
	
		<td>NUM</td>
		<td>TITLE</td>
		<td>NAME</td>
		<td>DATE</td>
		<td>HIT</td>
		
	</tr>
	
	<c:forEach items="${list}" var="noticeDTO">
		<tr>
			<td>${noticeDTO.num}</td>
			<td><a href="./noticeSelect?num=${noticeDTO.num}">${noticeDTO.title}</a></td>
			<td>${noticeDTO.name}</td>
			<td>${noticeDTO.reg_date}</td>
			<td>${noticeDTO.hit}</td>
	
		</tr>
	</c:forEach>



</table>
<a href="./noticeWrite" class="btn btn-danger">Go Write</a>

</div>

<div class="container">
  <ul class="pager">
   <!-- [이전] : 현재블럭이 1보다 클때 -->
   <c:if test="${pager.curBlock gt 1}">
   <li><a href="./noticeList?curPage=${pager.startNum-1}&kind=${pager.search.kind}&search=${pager.search.search}">Previous</a></li>
    </c:if>
    
    <li>
    
	    <ul class="pagination">
	    <c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i">
	    <li><a href="./noticeList?curPage=${i}&kind=${pager.search.kind}&search=${pager.search.search}">${i}</a></li>
	    </c:forEach>
  </ul>
    
    </li>
    <!-- [다음] : 현재블럭이 totalBlock보다 작을떄 -->
    <c:if test="${pager.curBlock lt pager.totalBlock}">
   <li><a href="./noticeList?curPage=${pager.lastNum+1}&kind=${pager.search.kind}&search=${pager.search.search}">Next</a></li>
    </c:if>
  </ul>
</div>




</body>
</html>