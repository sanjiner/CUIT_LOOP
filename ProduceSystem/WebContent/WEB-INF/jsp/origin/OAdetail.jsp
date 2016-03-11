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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=basePath%>res/css/common/applay.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/js/jquery.uploadify/uploadify.css"
	rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery.validate.min.js">
	
</script>
<script
	src="<%=basePath%>res/js/jquery.uploadify/jquery.uploadify.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/calendar.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/WdatePicker.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>申请详情</title>
<style type="text/css">
div.wrapper {
	position: relative;
	width: 125px;
	height: 125px;
	border: 0;
	float: left;
	background-color: transparent;
	text-align: center;
}

.divdelete {
	position: absolute;
	top: 0;
	right: 0;
	z-index: 1;
	display: block;
	width: 32px;
	height: 32px;
	background-image: url(<%=basePath%>/res/images/common/Close_Box_Red.gif);
	-moz-opacity: .5;
	filter: alpha(opacity = 100);
}
</style>
<script type="text/javascript">
function back(){ window.location.href="<%=basePath%>origin/OAlist";
	}
	
function presee()
	{
		
	}
</script>
</head>
<body style="background: #edf1fc">
	<div
		style="background: #4c9ee7; width: 100%; height: 30px; margin-bottom: 5px">
		<label
			style="width: auto; height: 30px; text-align: center; color: white; line-height: 30px; margin-left: 5px">农网认证>>认证详情</label>
	</div>
	<form id="form1">
		<div id="div_second" style="display: block;">
			<div
				style="width: 100%; height: 30px; line-height: 30px; background: #0090d7; padding-left: 10px; margin-top: 10dp; margin-right: 10dp">
				<label><font color="#fff" size="+1.5"><b>产品信息</b></font></label>
			</div>
			<table width="90%">
				<tbody>
					<tr>
					<tr>
						<td class="text_right">单位名称:</td>
						<td class="content_left">${pamodel.applyCompany }</td>
						<td class="text_right">申请人:</td>
						<td class="content_left">${pamodel.applyPerson }</td>
					</tr>
					<tr>
						<td class="text_right">产品名称:</td>
						<td class="content_left">${pamodel.originName }</td>
						<td class="text_right">产品品牌:</td>
						<td class="content_left">${pamodel.productBrand }</td>
					</tr>
					<tr>
						<td class="text_right">生产基地:</td>
						<td class="content_left">${address} </td>
						<td class="text_right">地址:</td>
						<td class="content_left">${pamodel.personAdress }</td>
					</tr>
					<tr>
						<td class="text_right">产品产值:</td>
						<td class="content_left">${pamodel.productValue }</td>
						<td class="text_right">产品产量:</td>
						<td class="content_left">${pamodel.productNum }</td>
					</tr>
					<tr>
						<td class="text_right">手机:</td>
						<td class="content_left">${pamodel.constract }</td>
						<td class="text_right">申请标签数量:</td>
						<td class="content_left">${pamodel.labelNum }</td>
					</tr>
					<tr>
						<td class="text_right">产品描述:</td>
						<td class="content_left">${pamodel.originDescription }</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<div id="div_third" style="display: block; margin: 10px">
		<div
			style="width: 100%; height: 30px; line-height: 30px; background: #0090d7; padding-left: 10px; margin-top: 10dp; margin-right: 10dp">
			<label><font color="#fff" size="+1.5"><b>已获得的资质、认证、荣誉、注册商标</b></font></label>
		</div>
		<table class="gvtable" style="margin: 10dp">
			<thead style="text-align: center;">
				<tr>
					<th>证书图片</th>
					<th>证书名称</th>
					<th>颁发机构</th>
					<th>获得时间</th>
				</tr>
			<tbody>
				<c:if test="${bsqList.size() <= 0}">
					<tr>
						<td colspan="4" style="margin: 5px">暂无数据</td>
					</tr>
				</c:if>
				<c:forEach items="${certs}" var="item">
					<tr>
						<td><img height="50px" width="50px" src="<%=path %>${item.pictureUrl }"></td>
						<td>${item.certificateName }</td>
						<td>${item.awardDepart }</td>
						<td>${item.getTime }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<center>
		<input class="btn_blue" style="width: 100px; height: 30px"
			type="button" value="返回" onclick="back()" />
			
			</center>
</body>
</html>