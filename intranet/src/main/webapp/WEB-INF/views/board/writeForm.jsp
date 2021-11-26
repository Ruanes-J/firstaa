<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>

function sendData(){
	if($("#uid").val() == '') { alert("아이디를 입력하세요."); $("#uid").focus();  return false; }
	if($("#uname").val() == '') { alert("이름을 입력하세요."); $("#uname").focus();  return false; }
	if($("#uage").val() == '') { alert("나이를 입력하세요."); $("#uage").focus();  return false; }
	
	console.log("uid = " + $("#uid").val());
	console.log("uid = " + $("#uname").val());
	console.log("uid = " + $("#uage").val());
	
	$("#registerForm").attr("action","/board/writeForm").submit();
	
}
</script>

</head>
<body>

<form name="registerForm" id="registerForm" method="post" >     
아이디 : <input type="text" name="uid" id="uid"><br>
이름 : <input type="text" name="uname"  id="uname"><br>
나이 : <input type="text" name="uage"  id="uage"><br>
<input type="button" value="Click!!!" onclick="sendData()">
</form>

</body>
</html>