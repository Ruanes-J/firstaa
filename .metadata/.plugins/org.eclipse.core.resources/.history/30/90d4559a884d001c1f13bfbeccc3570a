<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>아이디 찾기 검색결과</title>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style>
body { font-family: "나눔고딕", "맑은고딕" }
h1 { font-family: "HY견고딕" }

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

.idFindForm {
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

.idFind_btn  {
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

/* $(document).ready(function(){
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

}); */
/* 
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
 */

function home(){
	

	
	$("#idFindForm").attr("action","/Intranet/home.jsp").submit();
    
			
}
/* 
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
                url: "/Intranet/imgUpload?userid=" + $("#userid").val() + "&kinds=R",
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
} */


function nameCheck(){
	$.ajax({
		url : "/Intranet/nameCheck",
		type : "post",
		dataType : "json",
		data : {"username" : $("#username").val()},
		success : function(data){
			if(data == 1){
				/* $("#checkID").html("입력한 아이디가 존재하지 않습니다.");
				$("#userid").val("").focus(); */
							
			}else if(data == 0){
				$("#checkName").html("입력한 이름이 존재하지 않습니다.");
				$("#username").val("").focus();
			}
		}
	})
}
function emailCheck(){
	$.ajax({
		url : "/Intranet/emailCheck",
		type : "post",
		dataType : "json",
		data : {"email" : $("#email").val()},
		success : function(data){
			if(data == 1){
				$("#checkEmail").html("동일한 아이디가 이미 존재합니다. 새로운 아이디를 입력하세요.");
				$("#email").val("").focus();
							
			}else if(data == 0){
				$("#checkEmail").html("입력한 이메일이 존재하지 않습니다");
			}
		}
	})
}

</script>

</head>
<body>

<div class="panel-body" align="center">
<br><br>

<div class="idFindForm">
	      
    <h1>아이디 찾기 검색결과</h1>
    <input type="file" name="fileUpload" id="fileUpload" style="display:none;" />
        <form name="idFindForm" id="idFindForm" method="post" enctype="multipart/form-data">           
		 <div class="usernameForm"><input type="text" id="username" name="username" class="username" placeholder="이름을 입력하세요."></div>
		 <div><p id="checkName" style="color:red;text-align:center;"></p></div>
         <div class="emailForm"><input type="text" id="email" name="email" class="email" placeholder="이메일주소를 입력하세요."></div>
         </div>
         <div><input type="button" class="idFind_btn" value="로그인 하러가기" onclick="home()">
         <input type="button" class="cancel_btn" value="취소" onclick="history.back()"></div>
	</form>
</div>

</div>

</body>
</html>