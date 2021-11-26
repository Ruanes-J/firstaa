<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<ul> 

[&nbsp;&nbsp;
<a id="hypertext" href="/board/view?bno=${viewPrev}&num=${view.num}" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">이전글</a> &nbsp;&nbsp;| &nbsp;&nbsp;

<a id="hypertext" href="/board/modify?bno=${view.bno}&num=${view.num}" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">글 수정</a> &nbsp;&nbsp;| &nbsp;&nbsp;

<a id="hypertext" href="javascript:confirmCheck()" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">글 삭제</a> &nbsp;&nbsp;| &nbsp;&nbsp;

<a id="hypertext" href="/board/view?bno=${viewNext}&num=${view.num}" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">다음글</a> 

&nbsp;&nbsp;]
 
</ul>

