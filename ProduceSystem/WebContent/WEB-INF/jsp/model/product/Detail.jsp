<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<table class="info" cellpadding="0" cellspacing="0">
		<tr>
			<td class="text">要素名称：</td>
			<td class="value">${ elementDetail[0].elementName}</td>
		</tr>
		<tr>
			<td class="text">要素类型：</td>
			<td class="value">${ elementDetail[0].elementType}</td>
		</tr>
		<tr>
			<td class="text">气象指标：</td>
			<td class="value">${ elementDetail[0].remark}</td>
		</tr>
		<tr>
			<td class="text">要素描述：</td>
			<td class="value">${ elementDetail[0].elementDecription}</td>
		</tr>
		<tr>
			<td class="text">添加时间：</td>
			<td class="value">${ elementDetail[0].addTime}</td>
		</tr>
		<tr>
			<td class="text">&nbsp;</td>
			<td class="value">&nbsp; &nbsp;<a id="btnBack" href="javascript:history.go(-1);"
				class="btn_blue">返回</a>
			</td>
		</tr>
	</table>
</body>
</html>