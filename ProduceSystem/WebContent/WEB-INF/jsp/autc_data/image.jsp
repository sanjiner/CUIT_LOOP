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
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>res/js/My97DatePicker/calendar.js"></script>
<script type="text/javascript" src="<%=basePath%>res/js/public.js"></script>
<script type="text/javascript"
	src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
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
	$(document).ready(function() {
		$("#textId").val(getQueryString("id"));
		$("#textNum").val(getQueryString("num"));
	});
	function add(){
		if($("#select_stage option:selected").val() == "0"){
			alert("请选择生长周期");
			return;
		}
		window.location.href = "<%=basePath%>/autc_data/image_add?stage="+$("#select_stage option:selected").text()+"&code=${code}&num=${num}";
	}
</script>
</head>

<body>
	<div>
	<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">应用中心>>数据管理>图片列表</label>
	</div>
		<form id="form1" action="<%=basePath%>autc_data/image">
			<label><b>日期:</b></label> <input id="textStart" value="开始日期"
				name="start"
				onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})"> ~
			<input id="textEnd" value="结束日期" name="end"
				onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})"> <input
				type="button" value="查询" class="btn_blue" onclick="query()">
			<select id="select_stage">
				<option value="0">请选择</option>
				<c:choose>
					<c:when test="${bear.size() > 0 }">
						<c:forEach items="${bear}" var="item">
							<option value="bearingInfoId">${item.cropGrowthPeriod}</option>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<option value="0">无生育信息</option>
					</c:otherwise>
				</c:choose>
			</select> <input type="button" value="添加" onclick="add()" class="btn_blue">
			<a href="<%=basePath %>/autc_data/list" class="btn_blue">返回</a>
			<input type="hidden" id="textId" name="id"> <input
				type="hidden" id="textNum" name="num">
		</form>
	</div>
	<div>
		<div>
			<table class="gvtable" border="0" style="margin-top: 10px">
				<thead>
					<tr height="30px">
						<th width="20%">图片</th>
						<th width="20%">数据来源</th>
						<th width="10%">生育期</th>
						<th width="10%">采集时间</th>
						<th width="30%">描述</th>
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
							<td><img width="30px" src='<%=path %>${item.proctureUrl}' height="30px" /></td>
							<td>${item.dataOrigin}</td>
							<td>${item.cropGrowthPeriod}</td>
							<td><fmt:formatDate value="${item.collectionTime}"
									pattern="yyyy-MM-dd" /></td>
							<td>${item.remark}</td>
							<td><a
								href="<%=basePath%>autc_data/image_edit?id=${item.pictureCode}&code=${code}&num=${num}">编辑</a>
								<a onclick="if(!confirm('确认删除')){return false}"
								href="<%=basePath%>autc_data/image_delete?id=${item.pictureCode}&num=${num}&code=${code}">删除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>