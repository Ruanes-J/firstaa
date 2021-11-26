<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<table border=1>
<tr>
<td>아이디</td><td>이름</td><td>나이</td>
</tr>
<c:forEach items="${list}" var="list">
<tr>
<td>${list.uid}</td>
<td>${list.uname}</td>
<td>${list.uage}</td>
</tr>

</c:forEach>


</table>


</body>
</html>