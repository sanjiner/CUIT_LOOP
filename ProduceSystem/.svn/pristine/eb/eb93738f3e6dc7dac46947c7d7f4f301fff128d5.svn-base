<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"
	type="text/css" />
<script type="application/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="application/javascript"
	src="<%=basePath%>res/js/jQuery.md5.js"></script>


<title>权限列表</title>
<style type="text/css">
</style>

</head>

<body>
	<div class="container">
		<div class="" style="margin: 0 auto;">
			<table>
			<tr>
			<td>
				<form action="<%=basePath%>sys/jurisdiction/jurisdictionList">
					<input type="text" name="condition" value="${condition}">
					<input type="hidden" name="moduleId" value="${module.cuitMoonModuleId}">
					<button type="submit" class="btn_blue">搜索</button>
				</form>
			<td>
			<td>
				<shiro:hasPermission name="AddJurisdiction"><a href="<%=basePath%>sys/jurisdiction/add?moduleId=${module.cuitMoonModuleId}" class="btn_blue">添加</a></shiro:hasPermission>
			</td>
			</tr>
			</table>
		</div>
		<div style="margin: 0 auto;margin-top: 20px">
			<table class="gvtable" border="0">
				<thead>
					<tr>
						<th>权限代码</th>
						<th>权限名称</th>
						<th>所属模块</th>
						<th>描述</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${fn:length(page.pageList) == 0 }">
						<tr>
							<td colspan="6">没有数据</td>
						</tr>
					</c:if>
					<c:forEach items="${page.pageList}" var="jur">
						<tr>
							<td>${jur.cuitMoonJurisdictionCode}</td>
							<td>${jur.cuitMoonJurisdictionName}</td>
							<td>${module.cuitMoonModuleName}</td>
							<td>${jur.cuitMoonJurisdictionDescription}</td>
							<td>
								<c:choose>
									<c:when test="${jur.cuitMoonJurisdictionStatus eq 1}">启用</c:when>
									<c:otherwise>禁止</c:otherwise>
								</c:choose>
							</td>
							
							<td>
								<shiro:hasPermission name="EditJurisdiction"><a href="<%=basePath%>sys/jurisdiction/edit?jurisdictionId=${jur.cuitMoonJurisdictionId}">修改</a></shiro:hasPermission>
								<shiro:hasPermission name="DeleteJurisdiction"><a href="javascript:void(0)" onclick="deleteItem('<%=basePath%>sys/jurisdiction/delete?jurisdictionId=${jur.cuitMoonJurisdictionId}')">删除</a></shiro:hasPermission>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- 分页 -->
		<div style="margin-top: 30px; font-size: 15px">
			<span>第<font color="red">${page.currentPage}</font>页/共${page.pageCount}页&nbsp;&nbsp;每页显示<font
				color="red">${page.pageSize}</font>条&nbsp;&nbsp;共${page.allSize}条记录
			</span> 
			<span style="float: right;">
			<span>[总共${page.currentPageSize}条数据]</span>
			<span><a <c:if test="${page.prePage > 0 }">href="<%=basePath%>sys/jurisdiction/jurisdictionList?moduleId=${module.cuitMoonModuleId}&requsetPageNo=${page.prePage}&condition=${condition}"</c:if>>上一页</a></span>
			<span><a <c:if test="${page.nextPage > 0 }">href="<%=basePath%>sys/jurisdiction/jurisdictionList?moduleId=${module.cuitMoonModuleId}&requsetPageNo=${page.nextPage}&condition=${condition}"</c:if>>下一页</a></span>
			
			<span style="float: right;">
				<form action="<%=basePath%>sys/module/modulesList">
					<input type="hidden" name="moduleId" value="${module.cuitMoonModuleId}">
					<input type="hidden" name="condition" value="${condition}">
					转到<input style="width:20px" type="text" name="requsetPageNo" <c:if test="${page.pageCount == 1}">readonly</c:if>>页  
					<button type="submit">GO</button>
				</form>
			</span>
			</span>
		</div>
	</div>
	<script type="application/javascript">
	function deleteItem(url){
		if(confirm("确定删除该？")){ 
			location.href=url;
		} 
	}
	</script>

</body>
</html>
