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
<title>模型管理-模型等级-列表</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" /> 
</head>
<body>
	<div>
	&nbsp;&nbsp;模型名称： <input type="text" id="txtModelName"/>
	<input type="submit" class="checkButton" value="" />&nbsp;&nbsp;
	<input type="submit" class="addButton" value=""/>&nbsp;&nbsp;
	</div>
	<br/>
	<table class="gvtable">
		<thead>
		<tr>
			<th>模型名称</th>
			<th>等级名称</th>
			<th>等级描述</th>
			<th>备注</th>
			<th>查看详情</th>
			<th>操作</th>
			<th>删除</th>
		</tr>
		</thead> 
	<c:forEach items="${mgradelist}" var="mglist" varStatus="vs">	
		<tr>
			<td></td><%--模型名称在“模型制定”表里取 --%>
			<td>${mglist.approveLevelName}</td>
			<td>${mglist.approveLevelDescription}</td><%--模型具体详情在“模型制定”表里取 --%>
			<td>${mglist.remark}</td>
			<td><a href="">查看详情</a></td>
			<td><a href="">编辑</a></td>
			<td><a href="">删除</a></td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>