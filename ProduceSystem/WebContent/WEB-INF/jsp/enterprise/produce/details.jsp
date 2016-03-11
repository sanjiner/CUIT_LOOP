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
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<link href="<%=basePath%>res/js/jquery.uploadify/uploadify.css"
	rel="stylesheet" type="text/css" />
<script
	src="<%=basePath%>res/js/jquery.uploadify/jquery.uploadify.min.js"
	type="text/javascript"></script>
	
<title>新闻</title>
<style type="text/css">
body {
	font-family: "黑体";
}

.container {
	width: 100%;
	margin: 0 auto;
}

a {
	display: inline-block;
	width: 50px;
	float: inherit;
}

div.wrapper {
	position: relative;
	width: 125px;
	height: 125px;
	border: 0;
	float:left;
	background-color: transparent;
	text-align: center;
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	
	var imags = "${produce.productimg}";
	var strs = imags.split("|");
	for(i = 0; i < strs.length;i++){
		if(strs[i].length <=0 )
			continue;
		if(strs[i].length >= 0){
			var html = "<div id=\""+strs[i].substring(strs[i].lastIndexOf('/',strs[i].length))+"\" class=\"wrapper\"  style=\"cursor: pointer;\"><img style=\"width: 120px; height: 120px;margin:5px; background: #c2c2c2; cursor: pointer;\" src=\""+'<%=path%>'+strs[i]+"\"/><div onclick=\"deleImg(this)\" class=\"divdelete\" style=\"display: block;\"></div></div>";
			$("#imgContainer").append(html);
		}
	}
});	
</script>

</head>

<body  style="background: #edf1fc;">
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">企业个性化管理>>产品管理>>产品详情</label>
	</div>
	<form id="form1" action="<%=basePath%>enterprise/news/add"
		method="post">
		<center>
			<table style="margin-top: 20px">
				<tr>
					<td>标题：</td>
					<td style="width: 500px; margin: 5px">${produce.productname }</td>
				</tr>
				<tr>
					<td>图片：</td>
					<td><%-- <img src="<%=path %>${produce.productimg}" width="120px"
						height="180px"> --%>
						<div id="imgContainer" style="height: auto;min-height: 150px"></div>
					</td>
				</tr>
				<tr>
					<td>内容：</td>
					<td style="width: 500px; margin: 5px">${produce.productcontent}</td>
				</tr>
				<tr>
					<td>添加时间：</td>
					<td style="width: 500px; margin: 5px">${produce.cretime}</td>
				</tr>
				<tr>
					<td>添加人：</td>
					<td style="width: 500px; margin: 5px">${produce.qiyenum}</td>
				</tr>
			</table>
			<div style="text-align: center; width: 550px; margin-top: 10px">
				<a style="width: 100px" class="btn_blue"
					href="<%=basePath%>enterprise/produce/list">返回</a>
			</div>
	</form>
	</center>
</body>

</html>
