<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
<script type="text/javascript">

$(function() {
	
	var maxAppend = 0;
	var d1=	0;
	
	$('#add').click(function() {
		d1++;
		if(maxAppend < 5) {
			
			$('#addFile').append('<input type="file" class="form-control" id="" name="f'+ d1 +'"><span class="deL">X</span>');
			/* div 에 아이디를 주고 span title에도 동일한 값을 주면 부모 자식 관계 */
		maxAppend++;
		} else {
			
			alert('파일은 5개 미만');
			
		}
	});
	
			$('#addFile').on('click', '.deL', function() {
				$(this).prev().remove();
				$(this).remove();
				maxAppend--;
			});
	
});


</script>
<style type="text/css">

.deL {

	color : red;
	cursor: pointer;


}

</style>
</head>
<body>
<c:import url="../temp/header.jsp"/>

<div class="container">
	
	<h1>Qna Write Page</h1>

 <form action="./qnaWrite" method="post" enctype="multipart/form-data">
<!--  데이터가 크기 때문에 메소드는 포스트로, 파일 업로드시  enctype=multipart/form-data 필수 -->
 
    <div class="form-group">
      <label for="title">title :</label>
      <input type="text" class="form-control" id="title" name="title">
    </div>
    
    <div class="form-group">
      <label for="name">작성자 :</label>
      <input type="text" class="form-control" id="name" name="name">
    </div>
    
    <div class="form-group">
      <label for="contents">Contents :</label>
      <textarea class="form-control" rows="10" id="contents" name="contents"></textarea>
    </div>
    
    <div class="form-group" id="addFile">
      <label for="file">File :</label>
      
    </div>
    
     <div class="form-group">
    <input type="button" value="Add" class="btn btn-primary" id="add">
    </div>
    
    <button class="btn btn-danger">Write</button>
  </form>

</div>

</body>
</html>