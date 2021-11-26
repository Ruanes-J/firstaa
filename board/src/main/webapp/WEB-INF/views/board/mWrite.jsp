<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>게시물 등록</title>
<script src="http://code.jquery.com/jquery-1.11.3.js">
$(document).ready(funcion(){
	$("btn_write").on("click", function(){
		if($("#mwriter").val()==="") {alter("이름을 입력하세요")); $("#mwriter").focus(); return false; }
		if($("#mtitle").val()==="") {alter("제목을 입력하세요")); $("#mtitle").focus(); return false; }
		if($("#mcontent").val()==="") {alter("내용을 입력하세요")); $("#mcontent").focus(); return false; }
	})
})
</script>
</head>
<body>

<form name="WriteForm" method="POST">
이름: <input type="text" id="mwriter" name="mwriter" value="" placeholder="여기에 이름을 입력하세요"><br>
제목: <input type="text" id="mtitle" name="mtitle" value="" placeholder="여기에 제목을 입력하세요"><br>
내용 <br>
<textarea id="mcontent" cols="100" row="500" placeholder="여기에 내용을 입력하세요"></textarea>
</form>

<button id="btn_write">클릭</button>

</body>
</html>