<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>게시물 작성</title>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="http://code.jquery.com/jquery-1.11.3.js"></script>
<script>
$(document).ready(function(){
	
	$("btn_write").on("click",function(){
			console.log("ok");
		if($("#mwriter")).val()=="") { alert("이름을 입력하세요 1!!"); $("#mwriter").focus(); returnfalse;'}
		if($("#mtitle")).val()=="") { alert("제목을 입력하세요 1!!"); $("#mtitle").focus(); returnfalse;'}
		if($("#mcontent")).val()=="") { alert("이름을 입력하세요 1!!"); $("#mcontent").focus(); returnfalse;'}
	}
	});
	</script>
</head>
<body>

<table>
<tr>
<%-- <td>번호</td><td>이름</td><td>제목</td><td>게시 날짜</td>
<c:forEach items="${list}" var="list">

<td>번호</td><td>이름</td><td>제목</td><td>게시 날짜</td>
</c:foreach>
 --%>

</table>

 

</body>
</html>