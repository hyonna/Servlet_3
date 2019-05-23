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

<div class="container">
	
	<h1>NoticeUpdate Page</h1>

 <form action="./noticeUpdate?num=${select.num}" method="post" enctype="multipart/form-data">
 
 	<input type="hidden" id="num" name="num" value="${select.num}">
 	
 	
    <div class="form-group">
      <label for="title">title :</label>
      <input type="text" class="form-control" id="title" name="title" value="${select.title}">
    </div>
    
    <div class="form-group">
      <label for="name">작성자 :</label>
      <input type="text" class="form-control" id="name" name="name" value="${select.name}" readonly>
    </div>
    
    <div class="form-group">
      <label for="contents">Contents :</label>
      <textarea class="form-control" rows="10" id="contents" name="contents">${select.contents}</textarea>
    </div>
    
    <div class="form-group">
      <label for="file">File :</label>
      <input type="file" class="form-control" id="f1" name="f1">
    </div>
    
    <button class="btn btn-danger">Update</button>
  </form>

</div>

</body>
</html>