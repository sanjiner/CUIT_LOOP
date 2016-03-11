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

<title>字典管理</title>

<style type="text/css">
</style>

</head>

<body>
	<div class="container">
		<div class="" style="margin: 0 auto">
			<form action="<%=basePath%>sys/dic/dicList">
					地区名称：<input type="text" name="condition" value="${condition}">
					<input type="hidden" name="parentCode" value="${parentCode}">
					<button type="submit" class="btn_blue">搜索</button>
					<shiro:hasPermission name="AddDictionary"><a href="<%=basePath%>sys/dic/add" class="btn_blue">添加</a></shiro:hasPermission>
			</form>
		</div>
		<div style="margin: 0 auto;margin-top: 20px">
			<table class="gvtable" border="0">
				<thead>
					<tr>
						<th>字典名称</th>
						<th>字典编码</th>
						<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${fn:length(page.pageList) == 0 }">
						<tr>
							<td colspan="8">没有数据</td>
						</tr>
					</c:if>
					<c:forEach items="${page.pageList}" var="dic">
						<tr>
							<td>${dic.cuitMoonDictionaryName}</td>
							<td>${dic.cuitMoonDictionaryCode}</td>
							<td>${dic.cuitMoonDictionaryRemarks}</td>
							
							<td>
							<shiro:hasPermission name="EditDictionary"><a href="<%=basePath%>sys/dic/edit?dicId=${dic.cuitMoonDictionaryId}">编辑</a></shiro:hasPermission>
							<shiro:hasPermission name="DeleteDictionary"><a href="javascript:void(0)" onclick="deleteDic('<%=basePath%>sys/dic/delete?dicId=${dic.cuitMoonDictionaryId}&parentCode=${parentCode}')">删除</a></shiro:hasPermission></td>
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
			<span><a <c:if test="${page.prePage > 0 }">href="<%=basePath%>sys/dic/dicList?parentCode=${parentCode}&requsetPageNo=${page.prePage}&condition=${condition}"</c:if>>上一页</a></span>
			<span><a <c:if test="${page.nextPage > 0 }">href="<%=basePath%>sys/dic/dicList?parentCode=${parentCode}&requsetPageNo=${page.nextPage}&condition=${condition}"</c:if>>下一页</a></span>
			
			<span style="float: right;">
				<form action="<%=basePath%>sys/dic/dicList">
					<input type="hidden" name="parentCode" value="${parentCode}">
					<input type="hidden" name="condition" value="${condition}">
					转到<input style="width:20px" type="text" name="requsetPageNo" <c:if test="${page.pageCount == 1}">readonly</c:if>>页  
					<button type="submit">GO</button>
				</form>
			</span>
			</span>
		</div>
	</div>
	<script type="application/javascript">
		
		function deleteDic(url){
			if(confirm("确定删除该？")){ 
				location.href=url;
			} 
		}
		
	</script>

</body>
</html>
