<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>사용자 암호 수정</title>
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
  height:600px;
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

.useridForm, .usernameForm, .userpasswdForm, .userpasswdForm1 {
  border-bottom: 2px solid #adadad;
  margin: 30px;
  padding: 10px 10px;
}

.userid, .username, .userpasswd, .userpasswd1 {
  width: 100%;
  border:none;
  outline:none;
  color: #636e72;
  font-size:16px;
  height:25px;
  background: none;
}

.modify_btn  {
  position:relative;
  left:20%;
  transform: translateX(-50%);
  margin-top: 20px;
  margin-bottom: 10px;
  width:40%;
  height:40px;
  background: red;
  background-position: left;
  background-size: 200%;
  color:white;
  font-weight: bold;
  border:none;
  cursor:pointer;
  transition: 0.4s;
  display:inline;
}

.cancel_btn  {
  position:relative;
  left:20%;
  transform: translateX(-50%);
  margin-top: 20px;
  margin-bottom: 10px;
  width:40%;
  height:40px;
  background: pink;
  background-position: left;
  background-size: 200%;
  color:white;
  font-weight: bold;
  border:none;
  cursor:pointer;
  transition: 0.4s;
  display:inline;
}

</style>

<script>

function modify(){
	
	var oldPass = document.getElementById('olduserpasswd').value;
	var newPass = document.getElementById('userpasswd').value;
	var newPass1 = document.getElementById('userpasswd1').value;
	if(oldPass == '') { alert("현재 암호를 입력하세요."); document.registerForm.olduserpasswd.focus(); return false; }
	if(newPass == '') { alert("새 암호를 입력하세요."); document.registerForm.userpasswd.focus(); return false; }
	if(newPass1 == '') { alert("새 암호를 입력하세요."); document.registerForm.userpasswd1.focus(); return false; }
	if(newPass != newPass1) 
		{ alert("입력된 새 암호를 확인하세요"); document.registerForm.userpasswd1.focus(); return false; }
	
	//암호 유효성 검사
	var num = newPass.search(/[0-9]/g);
 	var eng = newPass.search(/[a-z]/ig);
 	var spe = newPass.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);	
	if(newPass.length < 8 || newPass.length > 20) { alert("암호는 8자리 ~ 20자리 이내로 입력해주세요."); return false; }
	else if(newPass.search(/\s/) != -1){ alert("암호는 공백 없이 입력해주세요."); return false; }
	else if(num < 0 || eng < 0 || spe < 0 ){ alert("암호는 영문,숫자,특수문자를 혼합하여 입력해주세요."); return false; }
	
	
	document.registerForm.action = '/member/passwdModify';
	document.registerForm.submit();
	
}

</script>

</head>

<c:if test="${userid  == null}">
<script>
	alert("세션이 만료되었습니다. 로그인 페이지로 이동합니다.");
	document.location.href='/';
</script>
</c:if>

<c:if test="${userid != null}">
<body>

<div class="panel-body" align="center">
<br><br>
<form name="registerForm" method="post" class="registerForm" onsubmit="modify()">

         <h1>사용자 암호 수정</h1>
         <div class="useridForm"><input type="text" class="userid" value="아이디 : ${userid}" disabled>
         </div>
		 <div class="usernameForm"><input type="text" name="username" class="username" value="이름 : ${username}" disabled></div>
		 <div class="userpasswdForm"><input type="password" id="olduserpasswd" name="olduserpasswd" class="userpasswd" placeholder="현재 암호를 입력하세요."></div>
         <c:if test="${msg == false}">
         	<p style="color:red;">현재 사용중인 암호를 잘못 입력했습니다. 현재 암호를 다시 입력 하세요.</p>    
         </c:if>
         <div class="userpasswdForm"><input type="password" id="userpasswd" name="userpasswd" class="userpasswd" placeholder="새 암호를 입력하세요."></div>
         <div class="userpasswdForm1"><input type="password" id="userpasswd1" name="userpasswd1" class="userpasswd1" placeholder="새 암호를 확인하세요."></div>
         <div><p style="color:red;text-align:center;">※ 8~20이내의 영문자, 숫자, 특수문자 조합으로 암호를 만들어 주세요.</p></div>
         <div>
         <input type="button" class="modify_btn" value="수정 완료" onClick="modify()">
         <input type="button" class="cancel_btn" value="취소" onclick="history.back()">
         
         </div>
</form>
<br><br>

</div>

</div>

</body>
</c:if>
</html>