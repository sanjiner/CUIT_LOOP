<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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

<title>角色管理</title>

<style type="text/css">
	input{
	line-height:0px;
	}
</style>

</head>

<body>
	<div class="container">
		<div class="" style="margin: 0 auto">
			<table>
			<tr>
			<td>
				<form action="<%=basePath%>sys/role/roleList">
					角色名称：<input type="text" name="condition" value="${condition}">
					<input type="hidden" name="parentRoleId" value="${parentRole.cuitMoonRoleId}">
					<input type="submit" class="btn_blue" value="搜索">
				</form>
			<td>
			<td>
				<shiro:hasPermission name="AddRole"><a href="<%=basePath%>sys/role/add?parentRoleId=${parentRole.cuitMoonRoleId}" class="btn_blue">添加</a></shiro:hasPermission>
			</td>
			</tr>
			</table>
		</div>
		<div style="margin: 0 auto;margin-top: 20px">
			<table class="gvtable" border="0">
				<thead>
					<tr>
						<th>角色名称</th>
						<th>上级角色</th>
						<th>描述</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.pageList}" var="role">
						<tr>
							<td>${role.cuitMoonRoleName}</td>
							<td>${parentRole.cuitMoonRoleName}</td>
							<td>${role.cuitMoonRoleDescription}</td>
							<td>
								<c:choose>
									<c:when test="${role.cuitMoonRoleStatus eq 1}">启用</c:when>
									<c:otherwise>禁止</c:otherwise>
								</c:choose>
							</td>
							
							<td>
							<shiro:hasPermission name="EditRole"><a href="<%=basePath%>sys/role/edit?roleId=${role.cuitMoonRoleId}">编辑</a></shiro:hasPermission> 
							<shiro:hasPermission name="DeleteRole"><a href="javascript:void(0)" onclick="deleteItem('<%=basePath%>sys/role/delete?roleId=${role.cuitMoonRoleId}')">删除</a></shiro:hasPermission></td>
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
			<span><a <c:if test="${page.prePage > 0 }">href="<%=basePath%>sys/role/roleList?requsetPageNo=${page.prePage}&condition=${condition}&parentRoleId=${parentRole.cuitMoonRoleId}"</c:if>>上一页</a></span>
			<span><a <c:if test="${page.nextPage > 0 }">href="<%=basePath%>sys/role/roleList?requsetPageNo=${page.nextPage}&condition=${condition}&parentRoleId=${parentRole.cuitMoonRoleId}"</c:if>>下一页</a></span>
			
			<span style="float: right;">
				<form action="<%=basePath%>sys/role/roleList">
					<input type="hidden" name="parentRoleId" value="${parentRole.cuitMoonRoleId}">
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
