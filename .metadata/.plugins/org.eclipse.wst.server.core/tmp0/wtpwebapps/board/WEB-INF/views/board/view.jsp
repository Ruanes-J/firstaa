<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<head>
<script src="http://code.jquery.com/jquery-1.11.3.js"></script>
<title>게시물 보기</title>
<style>
body { font-family: "나눔고딕", "맑은고딕" }
h1 { font-family: "HY견고딕" }

a:link { color: black; }
a:visited { color: black; }

a { text-decoration : none; cursor: hand; }

div.left { 
	width: 55%; 
	height: 100%; 
	margin-top: 30px;
	margin-left:0px;
	padding-right:0px;	
	display:inline-block;
	/* border:3px solid gray; */
}
div.right { 
	width: 35%; 
	height: 100%; 
	vertical-align: top;
	margin-top: 70px;	
	display:inline-block;
	/* border:3px solid gray; */
}

.navi_top {	
	width: 100%;
    height:auto;
    position: fixed;
    top: 0;
    left:0;
    right:0;
	text-align: center; 
	background-color: white;
}

.navi_top .loginInfo {
	width: 100%;
	height : 10%
}
.navi_top .main_menu {
	width: 100%;
	height : 90%
}

.navi_top > a {
	display:block; 
	text-decoration : none;
}

.navi_top > a:link, .navi_top > a:visited, .navi_top > a:active { color: white; }

.navi_top .main_menu > li {
    float:left;
    width:19%;
    height: auto;
    line-height: 50px;
    list-style: none;
    font-weight: bold;
    position: relative;
    margin: 0px,0px,0px,0px;
    background-color: #14D3FF; 
        
}

.navi_top .main_menu > li:hover {
    background-color: #1E90FF;
}

.navi_top .main_menu .sub_menu { 
    list-style: none; 
    display: none;
    position: absolute;
    padding-left: 0;
    padding-right: 0;

	background-color: #3CAEFF;
	width: 100%;
	box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
}

.navi_top .main_menu>li:hover .sub_menu {
    display:block;
}

.sub_menu a {
			color: white;
			padding: 1px;
			text-decoration: none;
			display: block;
}
.sub_menu a:hover { background-color: #3CFBFF; }

.navi_bottom { text-align: center; margin-top: 20px; }

.navi_bottom > a:link, .navi_bottom > a:visited {
			background-color: #FFA500;
			color: maroon;
			padding: 15px 25px;
			text-align: center;	
			display: inline-block;
}
.navi_bottom > a:hover, .navi_bottom > a:active { 
	background-color: #1E90FF;
	text-decoration : none; 
}

.registerForm {
  /* position:absolute; */
  
  margin-top: 50px;
  margin-left: 50px;
  margin-right: 50px;
  padding-right: 30px;
  width:80%;
  height:820px;
  /* padding: 20px, 20px; */
  background-color:#FFFFFF;
  text-align:center;

  border-right:3px solid gray; 
}

.nameForm, .titleForm, .fileListForm {
  border-bottom: 2px solid #adadad;
  margin: 20px;
  padding: 5px 5px;
}

.writer, .title, .fileList {
  width: 100%;
  border:none;
  outline:none;
  color: #636e72;
  font-size:16px;
  height:25px;
  background: none;
}

.textareaForm {
  width: 100%;
  height: 350px;
  overflow: auto;
  margin: 22px;
  padding: 10px;
  box-sizing: border-box;
  border: solid #adadad;
  text-align: left;
  font-size: 16px;
  resize: both;
}

#replyNotice {
	width: 90%;
	border: 1px solid skyblue; 
	background-color: #0AC9FF; 
	color: white; 
	padding: 5px;
	font-size:100%;
	font-weight: bold;
	display:inline-block;
	text-align: center;
}

.likeClick, .dislikeClick {
    padding: 10px 10px;
	text-align: center;	
	text-decoration: none;
    border: solid 1px #a0a0a0;
    display: inline-block;
    background-color: #d2d2d2;
    border-radius: 20px
}

}
</style>
<script>

function confirmCheck(){
	if(confirm("정말 삭제 하시겠습니까?")==true) location.href='/board/delete?bno=${bno}&num=${num}'
}

function fileDownload(file_no){
	
	location.href='/board/fileDownload?file_no=' + file_no;
}

function replyRegister() { //form 데이터 전송 --> 반드시 serialize()를 해야 한다.
	
	if($("#replyContent").val() == "") {alert("댓글을 입력하세요."); $("#replyContent").focus();return false;}
	
	var queryString = $("form[name=formReply]").serialize();
	$.ajax({
		url : "/board/replyInsert?option=I",
		type : "post",
		dataType : "json",
		data : queryString,
		success : replyList,
		error : function(data) {
						alert("서버오류 문제로 댓글 등록이 실패 했습니다. 잠시 후 다시 시도해주시기 바랍니다.");
              	    	return false;
				}
	}); //End od ajax
	$("#replyContent").val("");	
}

function pageStart() {
	
	var queryString = { "bno": ${bno} };
	$.ajax({
		url : "/board/replyInsert?option=L",
		type : "post",
		dataType : "json",
		data : queryString,
		success : replyList,
		error : function(data) {
						alert("서버오류 문제로 댓글 등록이 실패 했습니다. 잠시 후 다시 시도해주시기 바랍니다.");
              	    	return false;
				}
	}); //End od ajax
	
}

function replyList(list){
	
	var result = "";
	$.each(list, function(index,item){		
		var username = '${username}';
		var authority_code = '${authority_code}';
		
		<!-- 날짜, 시간 포맷 계산 시작-->
		date = new Date(item["regDate"]);
		let month = date.getMonth() + 1;
        let day = date.getDate();
        let hour = date.getHours();
        let minute = date.getMinutes();
        let second = date.getSeconds();

        month = month >= 10 ? month : '0' + month;
        day = day >= 10 ? day : '0' + day;
        hour = hour >= 10 ? hour : '0' + hour;
        minute = minute >= 10 ? minute : '0' + minute;
        second = second >= 10 ? second : '0' + second;
        
        let regDate = date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
        <!-- 날짜, 시간 포맷 계산 종료-->
        
		result += "<div id='replyListView'>";
		<!-- result += "<div id='replyListView" + item["replyBno"]  + "'>"; -->
			result += "<div id = '" + item["replyBno"] + "' style='font-size: 0.8em'>";
				result += "작성자 : " + item["writer"];
								
				if(item["writer"] == username || authority_code == '01') {
					result += "[<a href=" + "'javascript:replyModify(" + item["replyBno"] + ")' style='cursor:pointer;'>수정</a> | " ;
					result += "<a href=" + "'javascript:replyDelete(" + item["replyBno"] + ")' style='cursor:pointer;'>삭제</a>]" ;
				}
				
				result += "&nbsp;&nbsp;" + regDate
				result += "<div style='width:90%; height: auto; border-top: 1px solid gray; overflow: auto;'>";
				result += "<pre class='" + item["replyBno"] + "'>" + item["replyContent"] + "</pre></div>";
			result += "</div>";
		result += "</div><br>";
	})
	$("#replyListView").remove();
	$("#replyList").html(result);
}

function replyDelete(replyBno) { //JSON --> MAP 타입일 경우 contentType를 반드시 입력...
	if(confirm("정말로 삭제하시겠습니까?") == true) {
		var queryString = { "replyBno": replyBno, "bno": ${bno} };
		$.ajax({
			url : "/board/replyDelete",
			type : "post",
			dataType : "json",
			contentType: 'application/json; charset=UTF-8',
			data : JSON.stringify(queryString),
			success : replyList,
			error : function(data) {
							alert("서버오류 문제로 댓글 등록이 실패 했습니다. 잠시 후 다시 시도해주시기 바랍니다.");
	              	    	return false;
					}
		}); //End od ajax
	}
}

function replyModify(replyBno) {

	var replyContent = $("." + replyBno).html();
	
	var strReplyList = "<form id='formModify' name='formModify' method='POST'>"
					+ "작성자 : ${username}&nbsp;"
					+ "<input type='button' id='btn_replyModify' value='수정'>"
					+ "<input type='button' id='btn_replyModifyCancel' value='취소'>"
					+ "<input type='hidden' name='replyBno' value='" + replyBno + "'>"
					+ "<input type='hidden' name='bno' value='${bno}'>"
					+ "<input type='hidden' id='writer' name='writer' value='${username}'>"
					+ "<input type='hidden' id='uerid' name='userid' value='${userid}'>"
					+ "<textarea id='replyContent' name='replyContent' cols='80' rows='5' maxlength='150' placeholder='글자수:150자 이내'>" + replyContent + "</textarea><br>"
					+ "</form>";
	$('#' + replyBno).after(strReplyList);				
	$('#' + replyBno).remove();

	$("#btn_replyModify").on("click", function(){
		var queryString = $("form[name=formModify]").serialize();
		$.ajax({
			url : "/board/replyInsert?option=U",
			type : "post",
			dataType : "json",
			data : queryString,
			success : replyList,
			error : function(data) {
							alert("서버오류 문제로 댓글 등록이 실패 했습니다. 잠시 후 다시 시도해주시기 바랍니다.");
	              	    	return false;
			}
		}); //End of $.ajax
	 }); //End of $("#btn_replyModify")
	
	 $("#btn_replyModifyCancel").on("click", function(){
		 if(confirm("정말로 취소하시겠습니까?") == true  ) { $("#replyListView").remove(); pageStart(); }
	 });	 
	
}
	
function replyCancel() { 
		if(confirm("정말로 취소 하시겠습니까?") == true) { $("#replyContent").val(""); $("#replyContent").focus(); }
}

function mbrDelete() {
	
	if(confirm("사용자 탈퇴를 하시면 작성하셨던 모든 게시물도 함께 삭제됩니다. \n정말로 사용자 탈퇴 하시겠습니까?") == true)
	 	{ alert("사용자 정보기 삭제 되었습니다."); document.location.href='/board/mbrInfoDelete';  } 	
}

$(document).ready(function() {
    var likeCnt = ${view.likeCnt}; 
    var dislikeCnt = ${view.dislikeCnt}; 
    var myLikeCheck = '${myLikeCheck}'; 
    var myDislikeCheck = '${myDislikeCheck}'; 
    var username = '${username}';
    $("#like").html(likeCnt);
    $("#dislike").html(dislikeCnt);

    if(myLikeCheck == "Y") $(".likeClick").css("background-color", "#00B9FF"); 
        else if(myDislikeCheck == "Y") $(".dislikeClick").css("background-color", "#00B9FF"); 

    if(myLikeCheck == "Y") $("#myChoice").html(username + "님의 선택은 좋아요입니다."); 
            else if(myDislikeCheck == "Y") $("#myChoice").html(username + "님의 선택은 싫어요입니다."); 
            else if(myLikeCheck == "N" && myDislikeCheck == "N") $("#myChoice").html(username + "님은 아직 선택을 안 했네요"); 
    
    $("#likeClick").on("click", function() { 
        
        if(myLikeCheck == "Y" && myDislikeCheck =="N") {
            alert("좋아요를 취소합니다."); 
            var checkCnt = 1;  //likeCnt --;
            myLikeCheck = "N";
            sendDataToServer(myLikeCheck,myDislikeCheck,checkCnt); 
            $(".likeClick").css("background-color", "#d2d2d2"); 
        }else if(myLikeCheck == "N" && myDislikeCheck =="Y") {
            alert("싫어요가 취소되고 좋아요가 등록됩니다.");
            var checkCnt = 2; // likeCnt ++ , dislikeCnt --
            myLikeCheck = "Y";
            myDislikeCheck = "N";
            sendDataToServer(myLikeCheck,myDislikeCheck,checkCnt);  
            $(".likeClick").css("background-color", "#00B9FF"); 
            $(".dislikeClick").css("background-color", "#d2d2d2");
        } else if(myLikeCheck == "N" && myDislikeCheck =="N") {
            alert("좋아요를 선택 했습니다.")
        	var checkCnt = 3; //likeCnt ++
            myLikeCheck = "Y";
            sendDataToServer(myLikeCheck,myDislikeCheck,checkCnt);
            $(".likeClick").css("background-color", "#00B9FF"); 
        }
        if(myLikeCheck == "Y") $("#myChoice").html(username + "님의 선택은 좋아요입니다."); 
            else if(myDislikeCheck == "Y") $("#myChoice").html(username + "님의 선택은 싫어요입니다."); 
            else if(myLikeCheck == "N" && myDislikeCheck == "N") $("#myChoice").html(username + "님은 아직 선택을 안 했네요"); 
    })
    $("#disLikeClick").on("click", function() {
        
        if(myDislikeCheck == "Y" && myLikeCheck =="N") {
            alert("싫어요를 취소합니다."); 
            var checkCnt = 4; // dislikeCnt --
            myDislikeCheck = "N";
            sendDataToServer(myLikeCheck,myDislikeCheck,checkCnt);
            $(".dislikeClick").css("background-color", "#d2d2d2"); 
        } else if(myDislikeCheck = "N" && myLikeCheck =="Y") {
            alert("좋아요가 취소되고 싫어요가 등록됩니다.");
            var checkCnt = 5; //likeCnt -- , dislikeCnt ++            
            myLikeCheck = "N";            
            myDislikeCheck = "Y";
            sendDataToServer(myLikeCheck,myDislikeCheck,checkCnt);
            $(".likeClick").css("background-color", "#d2d2d2"); 
            $(".dislikeClick").css("background-color", "#00B9FF"); 
        } else if(myDislikeCheck = "N" && myLikeCheck =="N") {
            alert("싫어요를 선택했습니다.");
        	var checkCnt = 6; //dislikeCnt ++
            myDislikeCheck = "Y";
            sendDataToServer(myLikeCheck,myDislikeCheck,checkCnt);
            $(".dislikeClick").css("background-color", "#00B9FF"); 
        }
        if(myLikeCheck == "Y") $("#myChoice").html(username + "님의 선택은 좋아요입니다."); 
            else if(myDislikeCheck == "Y") $("#myChoice").html(username + "님의 선택은 싫어요입니다."); 
            else if(myLikeCheck == "N" && myDislikeCheck == "N") $("#myChoice").html(username + "님은 아직 선택을 안 했네요"); 
    })

})

function sendDataToServer(myLike, myDislike, checkCount) {

    var myLikeCheck = myLike;
    var myDislikeCheck = myDislike;
    var checkCnt = checkCount;
    var bno = 45;

    var queryString = { "bno":${view.bno}, "userid": "${userid}", "myLikeCheck": myLikeCheck, "myDislikeCheck": myDislikeCheck, "checkCnt": checkCnt };
    $.ajax({ //JSON --> MAP 타입일 경우 contentType를 반드시 입력...
        url: '/board/likeCheck',
        method: 'POST',
        data: JSON.stringify(queryString),
        contentType: 'application/json; charset=UTF-8',
        dataType : 'json',
        success: function(map) {
            $("#like").html(map["likeCnt"]);
            $("#dislike").html(map["dislikeCnt"]);
        },
        error: function(map) {
			alert("서버오류 문제로 좋아요/싫어요 등록이 실패 했습니다. 잠시 후 다시 시도해주시기 바랍니다.");
  	    	return false;
	}
    }); //End of ajax

}

</script>

</head>
<body onload="pageStart()"> 

<c:if test="${userid == null}">
<script>
	document.location.href='/';
</script>
</c:if>

<c:if test="${userid != null}">

<div class="navi_top">
 <div class="loginInfo" style="text-align: right; vertival-align: middle">${username}님(${authority_code})이 로그인 하셨습니다.</div>
 <ul class="main_menu">
 	       <li><a href="#">사용자 정보</a>
                <ul class="sub_menu">
                    <li><a href="/board/mbrInfoView"">정보 조회</a></li>
                    <li><a href="/board/logout">로그아웃</a></li>
                    <li><a href="javascript:mbrDelete()">회원탈퇴</a></li>
                </ul>    
            </li>
            <li><a href="#">게시판</a>
                <ul class="sub_menu">
                    <li><a href="/board/listPage?num=1">목록 보기</a></li>
                    <li><a href="/board/write?num=1">게시글 등록</a></li>
                </ul>
            </li>
            <li><a href="#">일정 관리</a>
               <ul class="sub_menu">
                    <li><a href="/board/calendarView">일정보기</a></li>
               </ul>
            </li>            
            <li><a href="/board/eduView">강의</a>
            </li>
            <li><a href="/board/sourceView">소스 보기</a>
            </li>
</ul>
</div>

<div class="left">
<form name="registerForm" method="post" class="registerForm">

         <h1>제목 : ${view.title}</h1>
         <div class="nameForm">
         	<input type="hidden" name="userid" value="${userid}">
         	<input type="hidden" name="searchType" value="${searchType}">
         	<input type="hidden" name="keyword" value="${keyword}">
         	<p style="text-align:left;">작성자 : ${view.writer}님</p>
         </div>
         <div class="titleForm"><p style="text-align:left;">작성일자 : <fmt:formatDate value="${view.regDate}" type="both" /></p></div>
         <div class="textareaForm" ><pre>${view.content}</pre></div>
         <div class="likeForm">
         	<span id='like'></span>&nbsp;<a href='#' id="likeClick" class="likeClick">좋아요</a>
        	<a href="#" id="disLikeClick" class="dislikeClick">싫어요</a>&nbsp;<span id="dislike"></span><br>
  			<span id='myChoice' style='color:red'></span>
         </div>
         <c:if test="${file == true}">
         	<div class="fileListForm"><p style="text-align:left;">파일 목록</p></div>
         	<div class="fileList">	
         		<p style="text-align:left;">
        	 		<c:forEach items="${fileList}" var="file" >
         				&nbsp&nbsp&nbsp&nbsp■ <a href="javascript:fileDownload(${file.file_no})">${file.org_file_name}</a>&nbsp(${file.file_size} kb)<br>
	         		</c:forEach>
         		</p>
         	</div>       
         </c:if>
</form>
</div> <!-- left End  -->	

<div class="right">

	<p id="replyNotice">댓글을 작성해 주세요</p>
	<div id='formDiv' style="width:100%; height: auto; overflow:auto; font-size: 0.8em">
		<form id="formReply" name="formReply" method="POST"> 
		작성자 : ${username}&nbsp;<input type='button' id='btn_replyRegister' value='등록' onclick='replyRegister()'>
			<input type='button' id='btn_replyCancel' value='취소' onclick='replyCancel()'>
			<input type='hidden' name='bno' value='${bno}'>
			<input type='hidden' name='userid' value='${userid}'>
			<input type='hidden' id='writer' name='writer' value='${username}'>
			<textarea id='replyContent' name='replyContent' cols='80' rows='5' maxlength='150' placeholder='글자수:150자 이내'></textarea><br>
		</form>
	</div>
	<hr>
	
	<div id="replyList" style="width:100%; height:600px; overflow:auto;">
		<div id="replyListView"></div> 
	</div><!-- replyList End  -->		
</div>
	
<div class="navi_bottom">
				<c:if test="${viewPrev != 0 }">
					&nbsp;&nbsp;<a href="/board/view?bno=${viewPrev}&num=${num}&searchType=${searchType}&keyword=${keyword}" onMouseover="this.style.textDecoration='underline';" 
						onmouseout="this.style.textDecoration='none';">이전글 ▼</a> &nbsp;&nbsp;
				</c:if>
					
				<a href="/board/listPage?num=${num}&searchType=${searchType}&keyword=${keyword}" onMouseover="this.style.textDecoration='underline';" 
						onmouseout="this.style.textDecoration='none';">목록보기</a> &nbsp;&nbsp;

				<c:if test = "${viewNext != 0 }">
				<a href="/board/view?bno=${viewNext}&num=${num}&searchType=${searchType}&keyword=${keyword}" onMouseover="this.style.textDecoration='underline';" 
						onmouseout="this.style.textDecoration='none';">다음글 ▲</a> &nbsp;&nbsp;
				</c:if>

				<a href="/board/write?num=${num}" onMouseover="this.style.textDecoration='underline';" 
						onmouseout="this.style.textDecoration='none';">글 작성</a> &nbsp;&nbsp;

				<c:if test="${msg == true}">
				<a href="/board/modify?bno=${view.bno}&num=${num}&searchType=${searchType}&keyword=${keyword}" onMouseover="this.style.textDecoration='underline';" 
						onmouseout="this.style.textDecoration='none';">글 수정</a> &nbsp;&nbsp;
					
				<a href="javascript:confirmCheck()" onMouseover="this.style.textDecoration='underline';" 
						onmouseout="this.style.textDecoration='none';">글 삭제</a> &nbsp;&nbsp;
				</c:if>

</div>

</c:if>
</body>
</html>