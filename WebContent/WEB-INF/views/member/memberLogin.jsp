<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
<style type="text/css">
.main {
	
	max-width: 400px;
	line-height: 15px;
	margin-top: 70px;
	margin-bottom: 500px;
}

.main>h2 {

text-align: center;
margin-bottom: 20px;
}

</style>
</head>
<body>
<c:import url="../temp/header.jsp"/>

<div class="container main">

<h2>Login</h2>
  <form action="./memberLogin" name="Login" id="formBox" method="post">
  
   <div class="form-group">
      <label for="id">id:</label>
      <input type="text" class="form-control" id="id" name="id" value="${cookie.id.value}">
      
     
    </div>
    
     <div class="form-group">
      <label for="pw">Password:</label>
      <input type="password" class="form-control" id="pw" name="pw">
    </div>
    
    <div class="checkbox">
    <label><input type="checkbox" name="check" id="check" value="check">Remember me</label>
    												<!-- value = 체크박스가 체크인지 아닌지 확인 -->
 	 </div>
 	 
 	
    
    <button class="btn btn-primary" id="btnLogin">Login</button>
    
    </form>

</div>
<c:import url="../temp/footer.jsp"/>
</body>
</html>