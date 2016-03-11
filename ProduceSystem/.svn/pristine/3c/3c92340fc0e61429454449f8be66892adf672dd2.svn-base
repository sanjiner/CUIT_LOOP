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

<title>评分详情</title>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/highcharts.js"></script>
</head>
<body>
	<table class="gvtable">
		<thead>
			<tr>
				<th width="10%">生育期</th>
				<th width="30%">时间范围</th>
				<th width="10%">气象因子</th>
				<th width="10%">最适范围</th>
				<th width="20%">实测气象条件</th>
				<th width="10%">专家评级</th>
				<th width="10%">具体分值</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="item">
				<tr title="bearInfo" id="${item.id }">
					<td rowspan="${item.grades.size() +1}">${item.name }</td>
					<td rowspan="${item.grades.size() +1}"><fmt:formatDate
							value="${item.start}" pattern="yyyy-MM-dd" />~<fmt:formatDate
							value="${item.end}" pattern="yyyy-MM-dd" /></td>
				</tr>
				<c:forEach items="${item.grades}" var="grade">
					<tr>
						<td>${grade.element}</td>
						<td>${grade.optimalRange }</td>
						<td>${grade.realCondition }</td>
						<td>${grade.gradeLevel }</td>
						<td>${grade.gradeValue }</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
	<center>
		<a style="margin-top: 10px;" class="btn_blue" href = "<%=basePath %>expert_grade/list">返回</a>
	</center>
	<div id="chart_line"></div>
</body>
</html>

