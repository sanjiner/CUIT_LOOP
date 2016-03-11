<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>认证列表</title>
<style>
.titleDiv{
	width:100%; 
	background-color:#429A50; 
	height:80px; 
	text-align:center;
	line-height:80px;
	background-image:url('<%=basePath%>res/images/bussiness/center_top.jpg');
	background-repeat:repeat-y;
	background-size:cover;
}
.titleDiv p{
	color:white;
 	font-size:30px;
 	font-weight: 800
}
.introduceDiv{
	width:100%; 
	height:25%; 
}
.introduceZone1{
	 width: 20%;
	 float:left;
	 height:100%;
}
.introduceZone1 img{
	 height:60%;
	 float:top;
	 margin-left: 40%;
}
.introduceZone1 p{
	 height:40%;
	 float:top;
	 margin-left: 45%;
	 font-weight: bolder;
	 font-size:25px;
	 color:#4E4E4E;
}
.introduceZone2{
	width:79%;
	height:100%; 
	float:left;
	vertical-align:middle;
}
.introduceZone2 p{
	color:#4E4E4E;
	 font-weight: bolder;
	 font-size:25px;
	 width:90%;
	 margin-left:5%;
	 height:100%;
}
.details{
	height:35.5rem;
	z-index:1;
	width:50%;
	margin-left:25%;
	background-color:white;
	filter:progid:DXImageTransform.Microsoft.Shadow(color=#909090,direction=120,strength=3);/*ie*/
	-moz-box-shadow: 2px 2px 10px #909090;/*firefox*/
	-webkit-box-shadow: 2px 2px 10px #909090;/*safari或chrome*/
	box-shadow:2px 2px 10px #909090;/*opera或ie9*/
	border-radius: 20px; 
	filter:alpha(opacity=80); /*IE滤镜，透明度50%*/
	-moz-opacity:0.8; /*Firefox私有，透明度50%*/
	opacity:0.8;/*其他，透明度50%*/
}
.details_title{
 	height:4rem;
 	width:100%;
 	float:top;
 	z-index:1;
 	line-height:7rem;
}
.details_title p{
	color:#4A9E50;
	font-size:20px;
	margin-top:5px;
	margin-left:20px;
 	font-weight: bolder;
 	height:100%;
 	width:100%;
}
.line{
	border:1px solid #4A9E50;
	width:95%;
 	float:top;
 	z-index:1;
}
.details_time{
	font-size:15px;
	text-align:center;
	z-index:1;
	height:0.5rem;
	color:#4E4E4E;
}
.details_content{
	font-size:20px;
	width:100%;
	z-index:1;
	height:30rem;
	overflow:auto;
	color:#4E4E4E;
}
li{
	margin-top:5px;
}
li a{
	color: #282828;
	text-decoration: none;
	width:75%;
	float:left;
	display:block;
	white-space:nowrap; 
	overflow:hidden;
	 text-overflow:ellipsis;
}
li span{
	font-size:15px;
	color: #545454;
	width:25%;
	float:left;
	display:block;
	white-space:nowrap; 
	overflow:hidden;
	text-overflow:ellipsis;
}
</style>
</head>
<body>
<div style="background-size:cover; height:900px">
<div class="titleDiv">
<p>${BusinessModel.getCampanyName()}</p>
</div>

<!-- 商家相关信息介绍区域-->
<div class="introduceDiv">
	<div class="introduceZone1">
		<c:if test = "${IsLogo.equals('NO')}">
			<img src="<%=basePath%>res/images/bussiness/center_default.png" />
		</c:if>
		<c:if test = "${IsLogo.equals('YES')}">
			<img src="${BusinessModel.getLogo()}" />
		</c:if>
		<p>认证产品</p>
	</div>
	<img style="width:2px; float:left; height:100%" src="<%=basePath%>res/images/bussiness/center_line.jpg" />
	<div class="introduceZone2"><p>${BusinessModel.getRemark()}</p></div>
</div>

<img style="width:100%;height: 75%;z-index:-1; position: absolute; margin-bottom:0px; margin-left:0px" src="<%=basePath%>res/images/bussiness/bottom_bg.png" />
<hr style="border:1px dashed #4A9E50"/>
<!-- 新闻列表-->
<div class="details">
<div class = "details_title"><p>认证产品</p></div>
<hr class="line" />
<div class = "details_content">
	<ul>
		<c:forEach var="item" items="${AuthedList}">
			<li><a href="<%=basePath%>bussiness_center/apply?id=${BusinessModel.getCampanyNo()}&applyID=${item.applyBh}">${item.productName }</a><span>[${item.applyTime}]</span></li>
		</c:forEach>
	</ul>
</div>
</div>
</div>
</body>
</html>