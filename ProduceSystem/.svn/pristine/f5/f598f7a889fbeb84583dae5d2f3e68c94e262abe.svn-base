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
<title>认证查询</title>
</head>
<body>
<div class="" style="height: 100%; width:100%">
	<ul style="height: 100%; width:100%"> 
		<c:forEach var="item" items="${ApplyQuryList}">
			<li style = "border-bottom-width:1px;border-bottom-color: gray;border-bottom-style:dashed; list-style-type:none;height: 100%; width:100%">${item.unityName}</li> 
		</c:forEach>
	</ul>
</div>
</body>
</html>