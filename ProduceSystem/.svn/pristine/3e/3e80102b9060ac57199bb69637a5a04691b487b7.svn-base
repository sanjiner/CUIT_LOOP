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
<title>产品详情</title>
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
}
.details_title{
	color:#4E4E4E;
	font-size:35px;
 	font-weight: bolder;
 	text-align:center;
 	height:1rem;
 	width:100%;
 	float:top;
 	z-index:1;
}
.line{
	border:1px dashed #CDCDCD;
	width:50%;
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
	width:50%;
	margin-left:25%;
	z-index:1;
	height:28rem;
	overflow:auto;
	color:#4E4E4E;
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
		<p>商家介绍</p>
	</div>
	<img style="width:2px; float:left; height:100%" src="<%=basePath%>res/images/bussiness/center_line.jpg" />
	<div class="introduceZone2"><p>${BusinessModel.getRemark()}</p></div>
</div>
<hr style="border:1px dashed #CDCDCD"/>
<!-- 新闻详情-->
<div class="details">
<p class="details_title">${ApplyModel.getProductName()}</p>
<p class = "details_time">申请时间：${ApplyModel.getApplyTime()}</p>
<hr class="line" />
<img style="width:100%;height: 50%;z-index:-1; position: absolute; margin-bottom:0px; margin-left:0px" src="<%=basePath%>res/images/bussiness/bottom_bg.png" />
<div class = "details_content">
${ApplyModel.getProductDescription()}
</div>
</div>
</div>

</body>
</html>