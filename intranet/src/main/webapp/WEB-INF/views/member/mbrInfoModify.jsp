<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>사용자 정보 수정</title>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

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

.useridForm, .usernameForm, .userpasswdForm, .userpasswd1Form, .authoritycodeForm, .telnoForm, .emailForm {
  border-bottom: 2px solid #adadad;
  margin: 30px;
  padding: 10px 10px;
}

.userid, .username, .userpasswd, .userpasswd1, .authority_code, .telno, .email {
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

#ImageRegistration {
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

</style>

<script>

$(document).ready(function(){
    var objDragAndDrop = $("#ImageRegistration");
    
    $(document).on("dragenter","#ImageRegistration",function(e){
        e.stopPropagation();
        e.preventDefault();
        $(this).css('border', '2px solid #0B85A1');
    });
   
    $(document).on("dragover","#ImageRegistration",function(e){
        e.stopPropagation();
        e.preventDefault();
    });

    $(document).on("drop","#ImageRegistration",function(e){
        
        $(this).css('border', '2px dotted #0B85A1');
        e.preventDefault();
        var files = e.originalEvent.dataTransfer.files;
    
        imageView(files, objDragAndDrop);
    });
    
    $(document).on('dragenter', function (e){
        e.stopPropagation();
        e.preventDefault();
    });

    $(document).on('dragover', function (e){
      e.stopPropagation();
      e.preventDefault();
      objDragAndDrop.css('border', '2px dotted #0B85A1');
    });

    $(document).on('drop', function (e){
        e.stopPropagation();
        e.preventDefault();
        imageView(files, objDragAndDrop);
    });

    //drag 영역 클릭시 파일 선택창
    objDragAndDrop.on('click',function (e){
        $("#fileUpload").trigger('click');
    });

    $("#fileUpload").on('change', function(e) {
       var files = e.originalEvent.target.files;
       imageView(files, objDragAndDrop);
    });

});

var imgcheck = "N";
var imgFile = null;
function imageView(f,obj){

	obj.html("");
	imgFile = f[0];

	if(imgFile.size > 1024*1024) { alert("1MB 이하 파일만 올려주세요."); return false; }
	if(imgFile.type.indexOf("image") < 0) { alert("이미지 파일만 올려주세요"); return false; }

	const reader = new FileReader();
	reader.onload = function(event){
		obj.html("<img src=" + event.target.result + " id='uploadImg' style='width:350px; height:auto;'>");
	};
	reader.readAsDataURL(imgFile);
	imgcheck = "Y";
}

function modify(){
	
	if($("#username").val() == '') { alert("이름을 입력하세요."); $("#username").focus(); return false; }
		
 	if($("#telno").val() == '') { alert("전화번호를 입력하세요."); $("#telno").focus(); return false; }
 	//전화번호 문자열 정리
	var beforeTelno = $("#telno").val();
 	var afterTelno = beforeTelno.replace(/\-/gi,"").replace(/\ /gi,"").trim();
	
	$("#telno").val(afterTelno);
	
	if($("#email").val() == '') { alert("이메일주소를 입력하세요."); $("#email").focus(); return false; }
	
	sendImageToServer();
	$("#registerForm").attr("action" , "/member/mbrInfoModify").submit();
		
}

function sendImageToServer(){
	var file=document.querySelector('#fileUpload');
	
	var fileList=file.files;
	var reader=new FileReader();
	
	if(fileList && fileList[0]){
	reader.readAsDataURL(fileList[0]);
	  reader.onload = function (e) {
        	var formData = new FormData();
        	formData.append("imgUpload",fileList[0]);
	
        	$.ajax( {
                url: "/member/imgUpload?userid=" + $("#userid").val() + "&kinds=M",
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
				dataType: 'json',
                enctype: 'multipart/form-data',
                success: function(data){
                	if(JSON.parse(data)['result'] == "OK"){	} 
                		else if(JSON.parse(data)['result'] == "GOOD") { }
        					else alert("조금 늦네요.잠시만 기다려 주세요.");
                }, //End of Success
                error: function (xhr, status, error) {
               	    	alert("서버오류 문제로 이미지 업로드가 안됩니다. 잠시 후 다시 시도해주시기 바랍니다.");
               	     return false;
               	} //End of Error  
             }); //End of ajax
	
      }; //End of reader.onload
	} //End of if(fileList && fileList[0])
}


</script>

</head>

<body>
<c:if test="${userid == null}">
	<script>
		document.location.href='/';
	</script>
</c:if>

<c:if test="${userid != null}">
	<div class="panel-body" align="center">
<br><br>

<div class="registerForm">
	<h1>사용자 정보 수정</h1>
	<span style="color:red;">※ 사진 편집을 원하시면 이미지를 클릭해 주세요</span>
	<input type="file" name="fileUpload" id="fileUpload" style="display:none;" />
	<center>
	<div id="ImageRegistration">
	<img src="${pageContext.request.contextPath}/resources/images/profile/${view.img_stored_file}" 
         	style='width:300px; height:auto;'></div>
    </center>
	<form id="registerForm" name="registerForm" method="post" >
         
         <div class="useridForm"><input type="text" class="userid" value="아이디 : ${userid}" disabled>
         	<input type="hidden" name="userid" id="userid" value="${userid}">
         	<input type="hidden" name="authority_code" id="authority_code" value="${view.authority_code}">
         </div>
		 <div class="usernameForm"><input type="text" name="username" id="username" class="username" value="이름 : ${view.username}" disabled></div>
         <div class="telnoForm"><input type="text" name="telno" id="telno" class="telno" value="${view.telno}"></div>
         <div class="emailForm"><input type="text" name="email" id="email" class="email" value="${view.email}"></div>
         <div>
         	<input type="button" class="modify_btn" value="수정 완료" onClick="modify()">
         	<input type="button" class="cancel_btn" value="취소" onclick="history.back()">
	     </div>
	</form>
<br><br>
</div>



</c:if>
</body>
</html>