<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<title>进度详情</title>
<style type="text/css">
img {
	max-width: 111px;
	max-height: 30px;
	scale: expression(( this.offsetWidth > this.offsetHeight)?(this.style.width=
		
		 this.offsetWidth>= 111? "111px": "auto"):(this.style.height= this.offsetHeight>=
		
		 111? "111px": "auto"));
	display: inline !important;
}
</style>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<div
		style="background: #4c9ee7; width: 100%; height: 30px; margin-bottom: 5px">
		<label
			style="width: auto; height: 30px; text-align: center; color: white; line-height: 30px; margin-left: 5px">应用中心>>进度查询>>进度详情</label>
	</div>
	<div
		style="float: left; text-align: left; width: 100%; display: inline;">
		<c:forEach items="${status}" var="item">
			<c:choose>
				<c:when test="${item.state eq 'true' }">
					<div title="${item.time }"
						style="border: 1px solid #0099ff; width: 8%; height: 40px; background: #82b205; text-align: center; line-height: 40px; float: left">
						<label style="color: white;"><font style="font-size: -1">${item.name }</font></label>
					</div>
					<div class="arrow-righ"
						style="float: left; width: 8.7%; height: 40px; text-align: center; line-height: 40px">
						<img alt="img" style="line-height: 40px; margin-top: 10px"
							src="<%=basePath%>res/images/common/arrow_green.png">
					</div>
				</c:when>
				<c:otherwise>
					<div title="${item.time }"
						style="border: 1px solid #0099ff; width: 8%; height: 40px; background: #c0c0c0; text-align: center; line-height: 40px; float: left">
						<label style="color: white;">${item.name }</label>
					</div>
				</c:otherwise>
			</c:choose>


		</c:forEach>
	</div>
<body>
</html>
