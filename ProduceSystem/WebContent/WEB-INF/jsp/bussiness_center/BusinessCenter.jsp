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
<title>商家中心</title>
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
.infoDiv1{
	width:100%;
	background-color:#F3F3F3;
	height: 25%
}
.infoDiv2{
	width:100%;
	height:25%;
}
.itemDiv{
	width:49%; 
	height:100%;
	float:left
}
.pTitle
{
	 background-color: #429A50;
	 margin-left:25%;
	 height:45px;
	 width:240px;
	 text-align:center;
	 line-height:45px;
	 -moz-border-radius: 5px;
	 -webkit-border-radius: 5px;
	  border-radius:5px;
	  color:white;
	  font-weight: 900;
	  font-size:25px;
}
.pTitle a
{
	 text-decoration:none;
	 color:white;
}
ul{
	margin-left:25%;
	height:65%;
	width:70%;
	list-style-type:square;
}
li a{
	font-size:20px;
	font-weight: bolder;
	color: #545454;
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
<!-- 商家名称-->
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

<!-- 商家相关信息列表区域 -->
<div class="infoDiv1">
	<div class="itemDiv">
		<p class="pTitle"><a href="<%=basePath%>bussiness_center/newsList?id=${BusinessModel.getCampanyNo()}">商家新闻 >></a></p>
		<ul>
		<c:forEach var="item" items="${NewsList}">
			<li><a href="<%=basePath%>bussiness_center/news?id=${BusinessModel.getCampanyNo()}&newsID=${item.newsId}">${item.newstitle}</a><span>[${item.cretime}]</span></li>
		</c:forEach>
		</ul>
	</div>
	<img style="width:2px; float:left; height:100%" src="<%=basePath%>res/images/bussiness/center_line.jpg" />
	<div style = "width:49%;height:100%; float:left">
		<p class="pTitle"><a href="<%=basePath%>bussiness_center/productList?id=${BusinessModel.getCampanyNo()}">商家产品 >></a></p>
		<ul>
			<c:forEach var="item" items="${ProductList}">
				<li><a href="<%=basePath%>bussiness_center/product?id=${BusinessModel.getCampanyNo()}&productID=${item.productId}">${item.productname}</a><span>[${item.cretime}]</span></li>
			</c:forEach>
		</ul>
	</div>
</div>

<!-- 商家相关信息列表区域 -->
<div class="infoDiv2">
	<img style="width:100%;height:40%;z-index:-1;position: absolute;" src="<%=basePath%>res/images/bussiness/bottom_bg.png" />
	<div style ="width:49%; height:100%;float:left">
		<p class="pTitle"><a href="<%=basePath%>bussiness_center/applyList?id=${BusinessModel.getCampanyNo()}&code=AUTHED">已通过的产品 >></a></p>
		<ul>
			<c:forEach var="item" items="${AuthedList}">
				<li><a href="<%=basePath%>bussiness_center/apply?id=${BusinessModel.getCampanyNo()}&applyID=${item.applyBh}">${item.productName }</a><span>[${item.applyTime}]</span></li>
			</c:forEach>
		</ul>
	</div>
	<img style="width:2px; float:left; height:100%" src="<%=basePath%>res/images/bussiness/center_line.jpg" />
	<div style = "width:49%;height:100%; float:left">
		<p class="pTitle"><a href="<%=basePath%>bussiness_center/applyList?id=${BusinessModel.getCampanyNo()}&code=AUTHING">正在认证的产品 >></a></p>
		<ul>
			<c:forEach var="item" items="${AuthingList}">
				<li><a href="<%=basePath%>bussiness_center/apply?id=${BusinessModel.getCampanyNo()}&applyID=${item.applyBh}">${item.productName }</a><span>[${item.applyTime}]</span></li>
			</c:forEach>
		</ul>
	</div>
</div>
</div>
</body>
</html>