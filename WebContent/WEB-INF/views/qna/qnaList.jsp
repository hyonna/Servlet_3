<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
</head>
<body>
<c:import url="../temp/header.jsp"/>

<div class="container">

<h1>QnaList Page</h1>

<table class="table table-hover">

	<tr>
	
		<td>NUM</td>
		<td>TITLE</td>
		<td>WRITER</td>
		<td>DATE</td>
		<td>HIT</td>
		
		
	</tr>
	
	<c:forEach items="${list}" var="qnaDTO">
		<tr>
			<td>${qnaDTO.num}</td>
			<td>
			<c:forEach begin="1" end="${qnaDTO.depth}" varStatus="i">
			&nbsp;&nbsp;
			<c:if test="${i.last}">└</c:if>
			</c:forEach>
			<a href="./qnaSelect?num=${qnaDTO.num}">${qnaDTO.title}</a></td>
			<td>${qnaDTO.writer}</td>
			<td>${qnaDTO.reg_date}</td>
			<td>${qnaDTO.hit}</td>
			
		</tr>
	</c:forEach>

</table>

<a href="./qnaWrite" class="btn btn-danger">Go Write</a>

</div>

<div class="container">
  <ul class="pager">
   <!-- [이전] : 현재블럭이 1보다 클때 -->
   <c:if test="${pager.curBlock gt 1}">
   <li><a href="./qnaList?curPage=${pager.startNum-1}&kind=${pager.search.kind}&search=${pager.search.search}">Previous</a></li>
    </c:if>
    
    <li>
    
	    <ul class="pagination">
	    <c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i">
	    <li><a href="./qnaList?curPage=${i}&kind=${pager.search.kind}&search=${pager.search.search}">${i}</a></li>
	    </c:forEach>
  </ul>
    
    </li>
    <!-- [다음] : 현재블럭이 totalBlock보다 작을떄 -->
    <c:if test="${pager.curBlock lt pager.totalBlock}">
   <li><a href="./qnaList?curPage=${pager.lastNum+1}&kind=${pager.search.kind}&search=${pager.search.search}">Next</a></li>
    </c:if>
  </ul>
</div>




</body>
</html>