<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>res/css/sys/sysStyle.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>res/css/right/bread.css">
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>res/css/right/sysIndexStyle.css">
<script type="application/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>

<title>角色管理</title>

<style type="text/css">
 	.tbwidth{
 		width: 200px;
 	}
</style>

</head>

<body>
	<div class="bread">
		<ul>
			<li>系统管理>></li>
			<li>日志管理</li>
		</ul>
	</div>
	<div class="container">
		<div style="margin: 0 auto; margin-top: 10px">
			<table class="gvtable" border="0">
				<thead>
					<tr>
						<th>IPv4地址</th>
						<th>操作名称</th>
						<th class="tbwidth">页面地址</th>
						<th>备注</th>
						<th>操作用户</th>
						<th>操作时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.pageList}" var="log">
						<tr>
							<td>${log.cuitMoonLogOperationIpv4}</td>
							<td>${log.cuitMoonLogOperationName}</td>
							<td class="tbwidth">${log.cuitMoonLogOperationUrl}</td>
							<td>${log.cuitMoonLogRemarks}</td>
							<td>${log.cuitMoonLogOperationUserName}</td>
							<td><fmt:formatDate value="${log.cuitMoonLogOperationTime}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
							<td>
							<shiro:hasPermission name="DeleteLog"><a
								href="javascript:void(0)" onclick="deleteItem('<%=basePath%>sys/log/delete?cuitMoonLogId=${log.cuitMoonLogId}')">删除</a></shiro:hasPermission></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- 分页 -->
		<div style="margin-top: 30px; font-size: 14px">
			<span>第<font color="red">${page.currentPage}</font>页/共${page.pageCount}页&nbsp;&nbsp;每页显示<font
				color="red">${page.pageSize}</font>条&nbsp;&nbsp;共${page.allSize}条记录
			</span> <span style="float: right;"> <span>[总共${page.currentPageSize}条数据]</span>
				<span><a
					href="<%=basePath%>sys/log/logList?requsetPageNo=${page.prePage}">上一页</a></span>
				<span><a
					href="<%=basePath%>sys/log/logList?requsetPageNo=${page.nextPage}">下一页</a></span>
				<span style="float: right;">
					<form action="<%=basePath%>sys/log/logList">
						转到<input style="width: 20px" type="text" name="requsetPageNo"
							<c:if test="${page.currentPageSize lt 1}">readonly</c:if>>页
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
