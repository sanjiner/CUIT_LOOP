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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标签申请-下载标签</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
function down() {
	var id = document.getElementById('labelApplicationId').value;
	window.location.href = "<%=basePath%>label/manage/application/downExcel?key="+id;
}
function back() {
	var pid = document.getElementById('applyBh').value;
	window.location.href = "<%=basePath%>label/manage/application/list?key="+pid;
}
</script>
</head>
<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">认证标签管理>>标签管理>>下载标签</label>
</div>
<br/>
<form id="form1" action="<%=basePath%>label/manage/application/edit" method="post">	
	<div>
		<input id="labelApplicationId" name="labelApplicationId" type="hidden" value="${labelApplicationId}"/>
		<input id="applyBh" name="applyBh" type="hidden" value="${applyBh}"/>
		<table>	
			<tr>
				<td>申请商家</td>
				<td>${applyPerson}</td>
			</tr>
			<tr>
				<td>标签类型</td>
				<td>${applyType}</td>
			</tr>
			<tr>
				<td>申请数量</td>
				<td>${applyNum}</td>
			</tr>
			<tr>
				<td>申请批次</td>
				<td>${pici}</td>
			</tr>
			<tr>
				<td>申请时间</td>
				<td>${applyTime}</td>
			</tr>
			<tr>
				<td>制作状态</td>
				<td>${makeStatus}</td>
			</tr>
			<tr>
				<td>申请理由</td>
				<td>${applyReason}</td>
			</tr>
		</table>
	</div>
	<br/>
	<div>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="下载标签" onclick="down()" class="btn_blue" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="返回" onclick="back()" class="btn_blue" />
	</div>
</form>
</body>
</html>