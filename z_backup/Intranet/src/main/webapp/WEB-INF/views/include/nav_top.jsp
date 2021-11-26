<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<ul> 

<span style="color:blue; font-weight:bold;">${username}님(${authority_code})</span>이 로그인 하셨습니다. 

[<c:if test="${authority_code == '01'}">
&nbsp;&nbsp; <a id="hypertext" href="/Intranet/mbrInfoList" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">사용자정보</a>&nbsp;&nbsp; | &nbsp;&nbsp;
</c:if>
<c:if test="${authority_code != '01'}">
&nbsp;&nbsp; <a id="hypertext" href="/Intranet/mbrInfoView" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">사용자정보</a>&nbsp;&nbsp; | &nbsp;&nbsp;
</c:if>
<a id="hypertext" href="/Intranet/listPage?num=1" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">게시판</a> &nbsp;&nbsp; | &nbsp;&nbsp;

<a id="hypertext" href="/Intranet/eduView" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">강의</a> &nbsp;&nbsp; | &nbsp;&nbsp;

<a id="hypertext" href="/Intranet/sourceView" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">소스 보기</a> &nbsp;&nbsp; | &nbsp;&nbsp;

<a id="hypertext" href="/Intranet/logout" onMouseover="this.style.background='#96FFFF'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">로그아웃</a>&nbsp;&nbsp; ]
 
 
</ul>