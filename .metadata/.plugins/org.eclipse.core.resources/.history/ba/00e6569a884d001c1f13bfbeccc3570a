<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>사용자 정보 보기</title>

<style>
body { font-family: "나눔고딕", "맑은고딕" }
h1 { font-family: "HY견고딕" }
a:link { color: black; }
a:visited { color: black; }
a:hover { color: red; }
a:active { color: red; }
#hypertext { text-decoration-line: none; cursor: hand; }

.registerForm {
  /* position:absolute; */
  width:900px;
  height:auto;
  padding: 20px, 20px;
  background-color:#FFFFFF;
  text-align:center;
  /* top:50%; 
  left:50%;
  transform: translate(-50%,-50%);
  */
  border:8px solid gray;
  border-radius: 30px;
}

.useridForm, .usernameForm, .userpasswdForm, .userpasswd1Form, 
	.authoritycodeForm, .telnoForm, .emailForm, .regiDateForm, .lastLoginDateForm, .pwModiDateForm {
  border-bottom: 2px solid #adadad;
  margin: 30px;
  padding: 10px 10px;
}

.userid, .username, .userpasswd, .userpasswd1, .authority_code, .telno, .email, 
	.regiDate, .lastLoginDate, .pwModiDate {
  width: 100%;
  border:none;
  outline:none;
  color: #636e72;
  font-size:16px;
  height:25px;
  background: none;
}

#ImageView {
                border: 2px solid #92AAB0;
                width: 450px;
                height: 200px;
                color: #92AAB0;
                text-align: center;
                vertical-align: middle;
                margin: 30px;
  				padding: 10px 10px;
                font-size:200%;
                display: table-cell;
                
}

.navi_top {	width: 1350px; text-align: right; }
</style>

<script>

function mbrDelete() {
	
	if(confirm("사용자 탈퇴를 하시면 작성하셨던 모든 게시물도 함께 삭제됩니다. \n정말로 사용자 탈퇴 하시겠습니까?") == true)
	 	{ alert("사용자 정보기 삭제 되었습니다."); document.location.href='/Intranet/mbrInfoDelete';  } 	
}

</script>

</head>

<body>
<c:if test="${userid == null}">
<script>
	document.location.href='/';
</script>
</c:if>
<div class="navi_top">
<%@ include file="../include/nav_top.jsp" %>
</div>

<c:if test="${userid != null}">
<div class="panel-body" align="center">
<br><br>
<form name="registerForm" method="post" class="registerForm" onsubmit="register()">

         <h1>사용자 정보</h1>
         <center><div id="ImageView"><img src="${pageContext.request.contextPath}/profile/${view.img_stored_file}" style='width:300px; height:auto;'></div></center>
         <div class="useridForm"><input type="text" name="userid" class="userid" value="아이디 : ${userid}" disabled></div>
		 <div class="usernameForm"><input type="text" name="username" class="username" value="이름 : ${view.username}" disabled></div>
         <div class="telnoForm"><input type="text" name="telno" class="telno" value="전화번호 : ${view.telno}" disabled></div>
         <div class="emailForm"><input type="text" name="email" class="email" value="이메일주소 : ${view.email}" disabled></div>
         <div class="regiDateForm"><input type="text" name="regiDate" class="regiDate" value="서비스 가입일 : ${view.regiDate}" disabled></div>
         <div class="lastLoginDateForm"><input type="text" name="lastLoginDate" class="lastLoginDate" value="마지막으로 로그인 한 날은??? &nbsp&nbsp ${view.lastLoginDate}" disabled></div>
         <div class="pwModiDateForm"><input type="text" name="pwModiDate" class="pwModiDate" value="마지막으로 패스워드 변경한 한 날은??? &nbsp&nbsp ${view.pwModiDate}" disabled></div>
         <div class="authoritycodeForm"><input type="text" class="authority_code" value="권한 : ${a_code}" disabled /></div>
         
</form>
<br><br>
<div class="mbr_bottom">
<a id="hypertext" href="javascript:history.back()" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">전 화면 이동</a>&nbsp&nbsp |  &nbsp&nbsp
<a id="hypertext" href="/Intranet/mbrInfoModify" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">사용자 정보 수정</a>&nbsp&nbsp | &nbsp&nbsp
<a id="hypertext" href="/Intranet/passwdModify" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">암호 수정</a>&nbsp&nbsp | &nbsp&nbsp
<a id="hypertext" href="javascript:mbrDelete()" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
onmouseout="this.style.background='white'; this.style.textDecoration='none';">회원탈퇴</a>
</div>
<br><br>
</div>

</c:if>
</body>
</html>