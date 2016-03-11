<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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

<title>图像列表</title>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/calendar.js"></script>

<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function query() {
		if ($("#textStart").val() == "开始日期") {
			alert("请选择开始日期");
			return;
		}
		if ($("#textEnd").val() == "开始日期") {
			alert("请选择开始日期");
			return;
		}
		$("#form1").submit();
	}
</script>
</head>

<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">应用中心>>数据管理>>气象数据</label>
	</div>
	<div>
		<form id="form1" action="<%=basePath%>autc_data/weather">
			<label><b>日期:</b></label> <input id="textStart" value="开始日期"
				name="start"
				onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})"> ~
			<input id="textEnd" value="结束日期" name="end"
				onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})"> <input
				type="button" value="查询" onclick="query()" class="btn_blue"><a
				class="btn_blue"
				href="<%=basePath%>autc_data/weather_add?code=${code}">添加</a>
				<input type="hidden" name="id" value="${code }">
		</form>
	</div>
	<div>
		<div>
			<table class="gvtable" border="0" style="margin-top: 10px">
				<thead>
					<tr height="30px">
						<th width="25%">采集时间</th>
						<th width="15%">数据来源</th>
						<th width="30%">气象灾害数据描述</th>
						<th width="20%">备注</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody id="list">
					<c:if test="${list.size() <= 0}">
						<tr>
							<td colspan="6" style="margin: 5px">暂无数据</td>
						</tr>
					</c:if>
					<c:forEach items="${list}" var="item">
						<tr>
							<td><fmt:formatDate value="${item.addTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${item.dataOrigin}</td>
							<td>${item.disastoursDescription}</td>
							<td>${item.remark}</td>
							<td><a
								href="<%=basePath%>autc_data/weather_edit?id=${item.dataCode}&code=${code}">编辑</a>
								<a
								href="<%=basePath%>autc_data/weather_delete?id=${item.dataCode}&code=${code}"
								onclick="if(!confirm('确认删除该记录?')){return false;}">删除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>