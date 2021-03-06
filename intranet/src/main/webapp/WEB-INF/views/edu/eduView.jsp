<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>

<style>
	body { font-family: "나눔고딕", "맑은고딕" }
	h1 { font-family: "HY견고딕" }

	a:link { color: black; }
	a:visited { color: black; }

	a { text-decoration : none; cursor: hand; }

    .menubar {
	width: 1100px;
    height:50px;
    position: fixed;
    top: 0;
    left:0;
    right:0;
	text-align: center; 
    }
    
    .menubar > a {
	display:block; 
	text-decoration : none;
	}

	.menubar > a:link, .navi_top > a:visited, .menubar > a:active { color: white; }

	.menubar .main_menu > li {
    	float:left;
    	width:19%;
    	height: 50px;
    	line-height: 50px;
    	list-style: none;
    	font-weight: bold;
    	position: relative;
    	margin: 0px,0px,0px,0px;
    	background-color: #14D3FF; 
        
	}

	.menubar .main_menu > li:hover {
    	background-color: #1E90FF;
	}

	.menubar .main_menu .sub_menu { 
    	list-style: none; 
    	display: none;
    	position: absolute;
    	padding-left: 0;
    	padding-right: 0;

		background-color: #3CAEFF;
		width: 100%;
		box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
	}

	.menubar .main_menu>li:hover .sub_menu { display:block; }
	.sub_menu a {
			color: white;
			padding: 1px;
			text-decoration: none;
			display: block;
	}
	.sub_menu a:hover { background-color: #3CFBFF; }
    
    .mainView {
        width: 1400px;
        height: auto;
        top: 0;
        left:0;
        right:0;
        margin-top: 60px;
        padding: 0,0,0,0;
        display: inline-block;
        z-index: -1;
    }
    .videoView {
    	z-index: -1;
        width:69%;
        height: 520px;
        display: inline-block;
        vertical-align: middle;
     }

     .chattingView {
        width:29%;
        height: 520px;
        border: solid gray 2px;
        display: inline-block;
        vertical-align: top;
    }   
    .chattingTitle {
        width: 100%;
        height:5%;
        color: white;
        background-color: gray;
        text-align: center;
     }
     .ChattingArea {
        width: 100%;
        height: 75%;
        overflow:auto;
     }
     .chattingInput {
        width: 100%;
        height: 10%;
        border-top: solid 2px gray;
        vertical-align: bottom;
     }
    .replyView {
        margin-top: 5px;
        top: 0;
        left:0;
        right:0;
        width:1100px%;
        height:auto;
        border-top: solid gray 2px;
    }
</style>

<body>

   <div class="menubar">
 	<ul class="main_menu">
            <li><a href="#">사용자 정보</a>
                <ul class="sub_menu">
                    <li><a href="/intranet/mbrInfoView"">정보 조회</a></li>
                    <li><a href="/intranet/logout">로그아웃</a></li>
                    <li><a href="javascript:mbrDelete()">회원탈퇴</a></li>
                </ul>    
            </li>
            <li><a href="#">게시판</a>
                <ul class="sub_menu">
                    <li><a href="/intranet/listPage?num=1">목록 보기</a></li>
                    <li><a href="/intranet/write?num=1">게시글 등록</a></li>
                </ul>
            </li>
            <li><a href="#">일정 관리</a>
               <ul class="sub_menu">
                    <li><a href="/intranet/calendarView">일정보기</a></li>
               </ul>
            </li>            
            <li><a href="/intranet/eduView">강의</a>
            </li>
            <li><a href="#">뉴스&공지</a>
            </li>
	</ul>  
   </div>
    <div class="mainView">
        <div class="videoView">
            <video width="940" src="${pageContext.request.contextPath}/video/2021년_개발_트렌드는_과연_어떻게_변화했나.mp4" 
                controls autoplay controlsList="nodownload">브라우저가 후져서 지원이 안되네요.</video>
        </div>
        <div class="chattingView">
            <div class="chattingTitle">실시간 채팅</div>
            <div class="ChattingArea"></div>
            <div class="chattingInput">
                <textarea id="chattingInput" cols="39" rows="4"></textarea><br>
                <button>등록</button><button>취소</button>

            </div>


        </div>
    </div>
    <div class="replyView">
    
    <p id="replyNotice">댓글을 작성해 주세요</p>
	<div id='formDiv'>
		<form id="formReply" name="formReply" method="POST"> 
		작성자 : ${username}<br>
			<input type='hidden' name='bno' value=''>
			<input type='hidden' id='writer' name='writer' value='${username}'>
			<textarea id='replyContent' name='replyContent' cols='85' rows='5' maxlength='150' placeholder='글자수:150자 이내'></textarea><br>
			<input type='button' id='btn_replyRegister' value='등록' onclick='replyRegister()'>
			<input type='button' id='btn_replyCancel' value='취소' onclick='replyCancel()'>
		</form>
	</div>
    
    
    </div>

</body>
</html>