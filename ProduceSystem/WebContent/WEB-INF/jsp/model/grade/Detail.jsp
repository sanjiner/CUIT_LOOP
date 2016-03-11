<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	<title>模型管理-气象要素-列表</title>
	<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/js/jquery.validate.min.js"></script>
</head>
<body>
	<form id="form1" method="post" action="" enctype="multipart/form-data"
		onsubmit="return doSubmit()">
		<table class="info" cellpadding="0" cellspacing="0">
			<tr>
				<td class="text">模型名称：</td>
				<td class="value">${ modelDetail[0].modelName}</td>
			</tr>
			<tr>
				<td class="text">模型类别：</td>
				<td class="value">${ modelDetail[0].modelType}</td>
			</tr>
			<tr>
				<td class="text">所属产品：</td>
				<td class="value">${ modelDetail[0].productNumber}</td>
			</tr>
			<tr>
				<td class="text">气象要素：</td>
				<td class="value"></td>
			</tr>
			<tr>
				<td class="text">最优生长环境描述：</td>
				<td class="value">${modelDetail[0].modelDescription}</td>
			</tr>
			<tr>
				<td class="text">&nbsp;</td>
				<td class="value"><a id="btnBack"
					href="javascript:history.go(-1);" class="btn_blue">返回</a></td>
			</tr>
		</table>
	</form>
</body>
</html>