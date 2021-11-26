<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<head>
<script src="http://code.jquery.com/jquery-1.11.3.js"></script>

<title>게시물 목록</title>

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

<script>

function search(){
	
	var searchType = $("#searchType").val();
	var keyword =  $("#keyword").val();
	//console.log("SearchType = " + searchType);
	//console.log("keyword = " + keyword);
	
	location.href = '/Intranet/listPage?num=${num}&searchType=' + searchType + '&keyword=' + keyword;
	
}

</script>

</head>

<body>
<!-- 세션이 없으면 초기 화면으로 이동 -->
<c:if test="${userid == null}">
	<script>
		document.location.href='/';
	</script>
</c:if>

<c:if test="${userid != null }">
<!-- 세션이 존재 할 경우 -->

<div class="navi_top">
<%@ include file="../include/nav_top.jsp" %>
</div>


<div class="middle_page">

<table class="InfoTable">
  <tr>
   <th>번호</th>
   <th>제목</th>
   <th>작성자</th>
   <th>작성일</th>
   <th>조회수</th>
  </tr>

 <tbody>
<c:forEach items="${list}" var="list">
 <tr onMouseover="this.style.background='#46D2D2';" 
 	onmouseout="this.style.background='white';">
  <td id="td_bno">${list.seq}</td>
  <td style="text-align:left;"><a id="hypertext" href="/Intranet/view?bno=${list.bno}&num=${num}&searchType=${searchType}&keyword=${keyword}" onMouseover='this.style.textDecoration="underline"'  
  	onmouseout="this.style.textDecoration='none';">${list.title}</a></td>
  <td>${list.writer}</td>
  <td><fmt:formatDate value="${list.regDate}" pattern="yyyy-MM-dd(EE요일) hh:mm" /></td> 
  <td id="viewCount_td">${list.viewCnt}</td>
 </tr>
</c:forEach>
</tbody>

</table>
<br>
<div>
  <select id="searchType" name="searchType">
      <option value="title">제목</option>
      <option value="content">내용</option>
      <option value="title_content">제목+내용</option>
      <option value="writer">작성자</option>
  </select>
  
  <input type="text" id="keyword" name="keyword" />
  <button type="button" onclick="search()">검색</button>
 </div>


<br>

<div class="navi_bottom">
	[<a id="hypertext" href="/Intranet/write?num=${num}" onMouseover="this.style.background='#96FFFF'; this.style.textDecoration='underline';" 
						onmouseout="this.style.background='white'; this.style.textDecoration='none';">글 작성</a>]
	[현재 ${num}/${page.endPageNum} 페이지] [
	<c:if test="${page.prev}">
 		<span><a href="/Intranet/listPage?num=${page.startPageNum - 1}">◀</a></span>
	</c:if>

	<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}" var="cnt">

  		<c:if test="${cnt != num}">
  			<span><a href="/Intranet/listPage?num=${cnt}">${cnt}</a></span>
  		</c:if>
  		<c:if test="${cnt == num}">
  			<span>${num}</span>
  		</c:if>
  	</c:forEach>

	<c:if test="${page.next}">
 		<span><a href="/Intranet/listPage?num=${page.endPageNum + 1}">▶</a></span>
	</c:if>
	]<br>
</div>

</div>
<!-- 세션 존재할 경우 보여질 화면 종료 -->
</c:if>

</body>
</html>