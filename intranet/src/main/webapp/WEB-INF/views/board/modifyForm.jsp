<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>

function sendData(){

	$("modifyForm").attr("action","/board/modifyForm").submit();
	
}
</script>

</head>
<body>

<form name="modifyForm" id="modifyForm" method="post" >     

<table border=1>
<tr>
<td>아이디</td><td>이름</td><td>나이</td>
</tr>

<c:forEach items="${list}" var="list">
<tr>
<td><input type="text" value="${list.uid}"></td>
<td><input type="text" value="${list.uname}"></td>
<td><input type="text" value="${list.uage}"></td>
</tr>

</c:forEach>

<input type="button" value="Click!!!" onclick="sendData()">
</form>

</body>
</html>