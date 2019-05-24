<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${pageContext.servletContext.contextPath}/index.do">WebSiteName</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="${pageContext.request.contextPath}/notice/noticeList">Notice</a></li>
      <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Page 1 <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Page 1-1</a></li>
          <li><a href="#">Page 1-2</a></li>
          <li><a href="#">Page 1-3</a></li>
        </ul>
      </li>
      <li><a href="#">Page 2</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">

	<c:choose> 
	
		<c:when test="${sessionScope.session.id == null}">
		
      <li><a href="<%=application.getContextPath()%>/member/memberCheck"><span class="glyphicon glyphicon-user"></span>Sign Up</a></li> 
      <li><a href="<%=application.getContextPath()%>/member/memberLogin"><span class="glyphicon glyphicon-log-in"></span>Login</a></li>
		
		
		</c:when> 
		<c:otherwise>
		
		<li><a href="<%=application.getContextPath()%>/member/memberPage"><span class="glyphicon glyphicon-user"></span>My Page</a></li> 
      <li><a href="<%=application.getContextPath()%>/member/memberLogout"><span class="glyphicon glyphicon-log-in"></span>Logout</a></li>
		
		</c:otherwise> 
	
	   
	
</c:choose> 
    </ul>
  </div>
</nav>

