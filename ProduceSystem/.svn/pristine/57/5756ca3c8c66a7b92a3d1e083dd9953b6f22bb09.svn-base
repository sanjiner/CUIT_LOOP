<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<title>数据列表</title>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
</head>

<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">应用中心>>数据管理>>管理列表</label>
	</div>
	<form id="form1" action="<%=basePath%>expert_assign/searchexpert"
		method="post">
		<div>
			<div class="" style="margin: 0 auto">
				<span><label style="font-size: medium;">请输入要查找的申请的名称:</label><input
					type="text" id="key" value="${key}" name="key"></span> <span><input
					type="submit" onclick="return validate()" class="btn_blue"
					value="搜索"></span>
			</div>
		</div>
	</form>
	<div>
		<div>
			<table class="gvtable" border="0"  style="margin-top: 10px">
				<thead>
					<tr height="30px">
						<th width="25%">产品名称</th>
						<th width="25%">产品品牌</th>
						<th width="20%">申请时间</th>
						<th width="10%">要素过程</th>
						<th width="10%">图片采集</th>
						<th width="10%">气象数据</th>
					</tr>
				</thead>
				<tbody id="list">
				<c:if
					test="${((fn:length(applyList) <= 0) || applyList == null)}">
						<tr>
							<td colspan="6" style="margin: 5px; width: 100%">暂无数据</td>
						</tr>
				</c:if>
					<c:forEach items="${applyList}" var="item">
						<tr>
							<td>${item.productName }</td>
							<td>${item.productBrand }</td>
							<td><fmt:formatDate value="${item.applyTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td> <shiro:hasPermission name="ProcessData"><a href="<%=basePath%>autc_data/factor_process?id=${item.approveCode}">查询</a></shiro:hasPermission></td>
							<td><shiro:hasPermission name="ImageCollection"><a href="<%=basePath%>autc_data/image?id=${item.approveCode}&num=${item.num}">管理</a></shiro:hasPermission></td>
							<td><shiro:hasPermission name="WeatherData"><a href="<%=basePath%>autc_data/weather?id=${item.approveCode}">管理</a></shiro:hasPermission></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>

</html>
