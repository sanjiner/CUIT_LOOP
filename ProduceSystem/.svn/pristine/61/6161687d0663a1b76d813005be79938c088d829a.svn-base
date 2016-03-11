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
<script type="application/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="application/javascript"
	src="<%=basePath%>res/js/jQuery.md5.js"></script>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"
	type="text/css" />

<title>模块管理</title>
<style type="text/css">
</style>

</head>

<body>
	<div class="container">
		<div class="" style="margin: 0 auto;">
			<table>
			<tr>
			<td>
				<form action="<%=basePath%>sys/module/modulesList">
					模块名称：<input type="text" name="condition" value="${condition}">
					<input type="hidden" name="parentModuelId" value="${parentModule.cuitMoonModuleId}<c:if test="${parentModule eq null}">0</c:if>">
					<button type="submit" class="btn_blue">搜索</button>
				</form>
			<td>
			<td>
				<shiro:hasPermission name="AddModule"><a href="<%=basePath%>sys/module/add?parentModuelId=${parentModule.cuitMoonModuleId}<c:if test="${parentModule eq null}">0</c:if>" class="btn_blue">添加</a></shiro:hasPermission>
			</td>
			</tr>
			</table>
		</div>
		<div style="margin: 0 auto;margin-top: 20px">
			<table class="gvtable" border="0">
				<thead>
					<tr>
						<th>模块名称</th>
						<th>链接地址</th>
						<th>父级模块</th>
						<th>描述</th>
						<th>状态</th>
						<th>排序</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${fn:length(modulePage.pageList) == 0 }">
						<tr>
							<td colspan="6">没有数据</td>
						</tr>
					</c:if>
					<c:forEach items="${modulePage.pageList}" var="module">
						<tr>
							<td>${module.cuitMoonModuleName}</td>
							<td>${module.cuitMoonModuleUrl}</td>
							<td>${parentModule.cuitMoonModuleName}</td>
							<td>${module.cuitMoonModuleDescription}</td>
							<td>
								<c:choose>
									<c:when test="${module.cuitMoonModuleStatus eq 1}">启用</c:when>
									<c:otherwise>禁止</c:otherwise>
								</c:choose>
							</td>
							<td><a href="<%=basePath%>sys/module/sort?type=asc&moduleId=${module.cuitMoonModuleId}"><img src="<%=basePath%>res/images/right/sys/ico_up.gif"></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>sys/module/sort?type=desc&moduleId=${module.cuitMoonModuleId}"><img src="<%=basePath%>res/images/right/sys/ico_down.gif"></a></td>
							<td>
								<shiro:hasPermission name="EditModule"><a href="<%=basePath%>sys/module/show?cuitMoonModuleId=${module.cuitMoonModuleId}">修改</a></shiro:hasPermission>
								<shiro:hasPermission name="DeleteModule"><a href="javascript:void(0)" onclick="deleteItem('<%=basePath%>sys/module/delete?cuitMoonModuleId=${module.cuitMoonModuleId}')">删除</a></shiro:hasPermission>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- 分页 -->
		<div style="margin-top: 30px; font-size: 15px">
			<span>第<font color="red">${modulePage.currentPage}</font>页/共${modulePage.pageCount}页&nbsp;&nbsp;每页显示<font
				color="red">${modulePage.pageSize}</font>条&nbsp;&nbsp;共${modulePage.allSize}条记录
			</span> 
			<span style="float: right;">
			<span>[总共${modulePage.currentPageSize}条数据]</span>
			<span><a <c:if test="${modulePage.prePage > 0 }">href="<%=basePath%>sys/module/modulesList?parentModuelId=${parentModule.cuitMoonModuleId}&requsetPageNo=${modulePage.prePage}&condition=${condition}"</c:if>>上一页</a></span>
			<span><a <c:if test="${modulePage.nextPage > 0 }">href="<%=basePath%>sys/module/modulesList?parentModuelId=${parentModule.cuitMoonModuleId}&requsetPageNo=${modulePage.nextPage}&condition=${condition}"</c:if>>下一页</a></span>
			
			<span style="float: right;">
				<form action="<%=basePath%>sys/module/modulesList">
					<input type="hidden" name="parentModuelId" value="${parentModule.cuitMoonModuleId}">
					<input type="hidden" name="condition" value="${condition}">
					转到<input style="width:20px" type="text" name="requsetPageNo" <c:if test="${modulePage.pageCount == 1}">readonly</c:if>>页  
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
