package kr.schedule.domain;

import java.util.Date;

/*
 
 create table tbl_authority ( # 사용자 권한 코드 테이블 
	authority_code char(2) not null,
	authority_name varchar(200) not null,
	constraint PK_tbl_authority(authority_code)
);

create table tbl_member ( # 사용자 정보 테이블 
	userid varchar(20) not null,
	username varchar(200),
	userpasswd varchar(200),
	authority_code char(2),
	telno varchar(200),
	email varchar(200),
	regiDate timestamp default current_timestamp,
	lastLoginDate date,
	pwModiDate date,
	img_org_file varchar(200),
	img_stored_file varchar(200),
	img_size varchar(200),
	constraint PK_tbl_member primary key(userid),
	#constraint FK_tbl_member_authority_code foreign key(authority_code) references tbl_authority(authority_code)
);

create table tbl_board ( # 게시판 테이블
	bno int not null auto_increment, 
	userid varchar(20) not null,
	writer varchar(200) not null, 
	title varchar(200) not null,
	content text not null,
	regDate timestamp not null default current_timestamp,
	viewCnt int not null default '0',
	likeCnt int,
	dislikeCnt int,
	constraint PK_tbl_board primary key(bno),
	#constraint FK_tbl_board_userid foreign key(userid) references tbl_member(userid)
);

create table tbl_reply ( # 댓글 테이블
	replyBno int not null auto_increment,
	bno int not null,
	userid varchar(20) not null,
	writer varchar(200) not null,
	replyContent text not null, 
	regDate timestamp not null default current_timestamp, 
	modiDate date,
	constraint PK_tbl_reply(replyBno),
	constraint FK_tbl_reply_bno foreign key(bno) references tbl_board(bno) on delete cascade,
	#constraint FK_tbl_reply_userid foreign key(userid) references tbl_member(userid)
);

create table tbl_file ( # 업로드 파일 정보 테이블
	file_no int not null auto_increment,
	bno int,
	org_file_name varchar(260) not null,
	stored_file_name varchar(260) not null, 
	file_size int not null default '0',
	regDate timestamp not null default current_timestamp,
	fileCheck char(1) not null default 'Y',
	constraint PK_tbl_file primary key(file_no)
);

create table tbl_like ( #좋아요_싫어요 정보 테이블
	bno int not null,
	userid varchar(20) not null,
	myLikeCnt char(1),
	myDislikeCnt char(1),
	likeDate date,
	dislikeDate date,
	constraint PK_tbl_like primary key(bno,userid),
	#constraint FK_tbl_like_bno_userid foreign key(bno) references tbl_board(bno),
	#constraint FK_tbl_like_userid foreign key(userid) references tbl_member(userid)
);
  
 */

public class MemberVO {

	//게시판 테이블(tbl_board) 항목
	private int seq;
	private int bno;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private int viewCnt;
	private String bdkinds;
	private int likeCnt;
	private int dislikeCnt;
	
	//댓글 테이블 항목 관리(tbl_reply)
	private int replyBno;
	private String replyContent;
	private String modiDate;
	
	//업로드 파일 테이블(tbl_file) 항목
	private int file_no;
	private String org_file_name;
	private String stored_file_name;
	private int file_size;
	private String fileCheck;
	
	//좋아요_싫어요 테이블 항목
	private String myLikeCheck;
	private String myDislikeCheck;
	private String likeDate;
	private String dislikeDate;
	private int checkCnt;
	
	//조회에 필요한 데이터 입출력 관리 항목
	private int num;
	private int maxbno;
	private int minbno;

	//게시판 테이블(tbl_board) Getter, Setter
	public int getSeq() {
		return seq;
	}	
	public void setSeq(int seq) {
		this.seq = seq;		
	}	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	public String getBdkinds() {
		return bdkinds;
	}
	public void setbdkinds(String bdkinds) {
		this.bdkinds = bdkinds;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	public int getDislikeCnt() {
		return dislikeCnt;
	}
	public void setDislikeCnt(int dislikeCnt) {
		this.dislikeCnt = dislikeCnt;
	}
	
	//댓글 테이블 항목 관리(tbl_reply) Getter, Setter
	public int getReplyBno() {
		return replyBno;
	}
	public void setReplyBno(int replyBno) {
		this.replyBno = replyBno;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}	
	public String getFileCheck() {
		return fileCheck;
	}
	public void setFileCheck(String fileCheck) {
		this.fileCheck = fileCheck;
	}
	public String getReplyModiDate() {
		return modiDate;
	}
	public void setreplyModiDate(String modiDate) {
		this.modiDate = modiDate;
	}
			
	//업로드 파일 테이블(tbl_file) Getter, Setter
	public int getFileNo() {
		return file_no;
	}
	public void setFileNo(int file_no) {
		this.file_no = file_no;
	}	
	public String GetOrg_file_name() {
		return org_file_name;
	}
	public void setOrg_file_name(String org_file_name) {
		this.org_file_name = org_file_name;
	}
	public String GetStored_file_name() {
		return stored_file_name;
	}
	public void setStored_file_name(String stored_file_name) {
		this.stored_file_name = stored_file_name;
	}
	public int GetFile_size() {
		return file_size;
	}
	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}
	
	//조회에 필요한 데이터 입출력 관리 항목 Getter, Setter
	public int getNum( ) {
		return num;
	}
	public void setNum(int num) {
	    this.num = num;	
	}
	public int getMaxbno( ) {
		return maxbno;
	}
	public void setMaxbno(int maxbno) {
	    this.maxbno = maxbno;	
	}
	public int getMinbno( ) {
		return minbno;
	}
	public void setMinbno(int minbno) {
	    this.minbno = minbno;	
	}
	
	//좋아요_싫어요 테이블(tbl_like) 관리 항목 Getter, Setter 
	public String getMyLikeCheck( ) {
		return myLikeCheck;
	}
	public void setMyLikeCheck(String myLikeCheck) {
		this.myLikeCheck = myLikeCheck;
	}
	public String getMyDislikeCheck( ) {
		return myDislikeCheck;
	}
	public void setMyDislikeCheck(String myDislikeCheck) {
		this.myDislikeCheck = myDislikeCheck;
	}
	public String getLikeDate( ) {
		return likeDate;
	}
	public void setlikeDate(String likeDate) {
		this.likeDate = likeDate;
	}
	public String getDislikeDate( ) {
		return dislikeDate;
	}
	public void setDislikeDate(String dislikeDate) {
		this.dislikeDate = dislikeDate;
	}
	public int getCheckCnt( ) {
		return checkCnt;
	}
	public void setCheckCnt(int checkCnt) {
		this.checkCnt = checkCnt;
	}
	
	// 사용자 테이블(tbl_member) 항목
	private String userid;
	private String username;
	private String userpasswd;
	private String authority_code;
	private String telno;
	private String email;
	private String regiDate;
	private String lastLoginDate;
	private String pwModiDate;
	private String img_org_file;
	private String img_stored_file;
	private String img_size;
	
	//사용자 테이블(tbl_member) Getter, Setter
	public String getUserid( ) {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
    public String getUsername() {
    	return username;
    }
    public void setUsername(String username) {
    	this.username = username;    	
    }
    public String getUserpasswd() {
        return userpasswd;
    }
    public void setUserpasswd(String userpasswd) {
    	this.userpasswd = userpasswd;
    }
    public String getAuthority_code() {
        return authority_code;
    }
    public void setAuthority(String authority_code) {
    	this.authority_code = authority_code;
    }
    public String gettelno() {
        return telno;                
    }
    public void settelno(String telno) {
    	this.telno = telno;
    }    
    public String getemail() {
        return email;
    }
    public void setemail(String email) {
    	this.email = email;
    }
    public String getRegiDate() {
        return regiDate;                
    }
    public void setRegiDate(String regiDate) {
    	this.regiDate = regiDate;
    }
    public String getlastLoginDate() {
        return lastLoginDate;                
    }
    public void setlastLoginDate(String lastLoginDate) {
    	this.lastLoginDate = lastLoginDate;
    }
    public String getpwModiDate() {
        return pwModiDate;                
    }
    public void setpwModiDate(String pwModiDate) {
    	this.pwModiDate = pwModiDate;
    }
    public String getImg_org_file() {
        return img_org_file;                
    }
    public void setImg_org_file(String img_org_file) {
    	this.img_org_file = img_org_file;
    }
    public String getImg_stored_file() {
        return img_stored_file;                
    }
    public void setImg_stored_file(String img_stored_file) {
    	this.img_stored_file = img_stored_file;
    }    
    public String getImg_size() {
        return img_size;                
    }
    public void setImg_size(String img_size) {
    	this.img_size = img_org_file;
    } 
}
