<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>사용자 정보 보기</title>
<style>
body { font-family: "나눔고딕", "맑은고딕" }
h1 { font-family: "HY견고딕" }
a:link { color: black; }
a:visited { color: black; }
a:hover { color: red; }
a:active { color: red; }
#hypertext { text-decoration-line: none; cursor: hand; }

.middle_page {
	text-align: center;
}

.InfoTable {
      border-collapse: collapse;
      border-top: 3px solid #168;
      width: 800px;  
      margin-left: auto; margin-right: auto;
    }  
    .InfoTable th {
      color: #168;
      background: #f0f6f9;
      text-align: center;
    }
    .InfoTable th, .InfoTable td {
      padding: 10px;
      border: 1px solid #ddd;
    }
    .InfoTable th:first-child, .InfoTable td:first-child {
      border-left: 0;
    }
    .InfoTable th:last-child, .InfoTable td:last-child {
      border-right: 0;
    }
    .InfoTable tr td:first-child{
      text-align: center;
    }
    .InfoTable caption{caption-side: top; }

.navi_top {	width: 1350px; text-align: right; }
.navi_bottom { text-align: center; }

</style>

</head>
<body>

<div class="navi_top">
<%@ include file="../include/nav_top.jsp" %>
</div>

<div class="middle_page">

<table class="InfoTable">
  <tr>
   <th>번호</th>
   <th>아이디</th>
   <th>이름</th>
   <th>등록일자</th>
   <th>마지막 로그일</th>
  </tr>

 <tbody>
<c:forEach items="${list}" var="list">
 <tr onMouseover="this.style.background='#46D2D2';" 
 	onmouseout="this.style.background='white';">
  <td>${list.seq}</td>
  <td style="text-align:left;"><a id="hypertext" href="/Intranet/mbrInfoView?userid=${list.userid}" onMouseover='this.style.textDecoration="underline"'  
  	onmouseout="this.style.textDecoration='none';">${list.userid}</a></td>
  <td>${list.username}</td>
  <td>${list.regiDate}</td> 
  <td>${list.lastLoginDate}</td>
 </tr>
</c:forEach>
</tbody>

</table>
<br>

</body>
</html>