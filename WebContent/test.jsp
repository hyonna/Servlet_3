<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- prefix(접두어=태그명) -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%

 	request.setAttribute("s", 5);
	request.setAttribute("e", 10);
	String[] ar = {"a", "b", "c"};
	request.setAttribute("ar", ar);
	ArrayList<Integer> list = new ArrayList<Integer>();
	list.add(100);
	list.add(200);
	list.add(300);
	request.setAttribute("list", list);

%>

<c:forEach begin="${s}" end="${e}" step="1" var="i"> 
<!-- begin=시작 , end=끝(작거나같을때까지), step=증가할 수, - 또는 0은 안됨, var=변수명 -->
<!-- for(int i = 1; i < 5; i++) 과 같음 -->
<!-- 페이지영역 -->
<%-- ${pageScope.i} --%>
${i}

</c:forEach>

<c:forEach items="${ar}" var="a"> <!-- 컬렉션이나 배열을 가져올때는 items , 그래서 var 변수에 담아줌, 변수명은 알아서-->

${a} <!-- 출력은 변수명으로 -->

</c:forEach>

<c:forEach items="${list}" var="b" varStatus="t"> <!-- varStatus도 변수, 배열 인덱스에 대한 변수 -->


${b}<br>
<h3>count : ${t.count}</h3> <!-- count는 증가수 (1, 2, 3) -->
<h3>index : ${t.index}</h3> <!-- index는 인덱스 번호 (0, 1, 2) -->
<h3>first : ${t.first}</h3> <!-- 첫번째 숫자인지 물어보는 것 -->
<h3>last : ${t.last}</h3> <!-- 마지막 숫자인지 물어보는 것 -->

</c:forEach>

</body>
</html>