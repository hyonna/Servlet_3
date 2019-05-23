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
	margin: 70px auto;
}

</style>
</head>
<body>
<c:import url="../temp/header.jsp"/>

<div class="container main">

		  <h2>Join form</h2>
  <form action="./memberJoin" name="join" id="formBox" method="post" enctype="multipart/form-data">
  
    <div class="form-group">
      <label for="id">Id:</label>
      <input type="text"  class="form-control" id="id" placeholder="10자 미만" name="id">
      <input type="hidden" id="idc" value="0">
      <input type="button" class="btn btn-primary" value="중복확인" id="idOk" style="margin-top: 5px;"><!--  중복확인 누르면 팝업창 뜨면서 아이디 입력하고 확인 버튼 누르면 창 꺼짐 / value값에 입력한 아이디 입력되어짐 / 중복확인 했다 라는 표시를 어딘가에는 해놔야함 -->
    </div>
    
    <div id="idChk">
    
    </div>
    
	<!-- 비밀번호 입력란 start -->
    
    
    <div class="form-group">
      <label for="pw">Password:</label>
      <input type="password" class="form-control" id="pw" placeholder="6자 이상" name="pw">
    </div>
    
     <div class="form-group">
      <label for="pw2">Password:</label>
      <input type="password" class="form-control" id="pw2" placeholder="둘다 일치" name="pw2">
    </div>
    
   <div id="pwdChk">
   
   </div>
    
   
    
    
    <!-- 비밀번호 입력란 end -->
    
    <div class="form-group">
      <label for="name">Name:</label>
      <input type="text" class="form-control" id="name" placeholder="Enter name" name="name">
    </div>
    
     <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" class="form-control" id="email" placeholder="Enter email" name="email">
    </div>
    
     <div class="form-group">
      <label for="phone">Phone:</label>
      <input type="text" class="form-control" id="phone" placeholder="Enter phone" name="phone">
    </div>
    
    <div class="form-group">
      <label for="age">Age:</label>
      <input type="text" class="form-control" id="age" placeholder="Enter age" name="age">
    </div>
    
     <div class="form-group">
      <label for="profile">Profile Pic :</label>
      <input type="file" class="form-control" id="profile" name="profile">
    </div>
    
    <button class="btn btn-primary" id="btnJoin">Join</button> <!-- 모두 비어있으면 버튼 X -->
  </form>
		


</div>
<c:import url="../temp/footer.jsp"/>
</body>
</html>