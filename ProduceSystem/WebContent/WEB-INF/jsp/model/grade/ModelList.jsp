<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>模型管理-气象要素-列表</title>
	<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<div>
		&nbsp;&nbsp;要素名称： <input type="text" id="txtName" class="height20" />
		<input type="button" class="checkButton" value="" onclick="doSearch()" />&nbsp;&nbsp; 
	</div>
	<br />
	<div style="margin: 0 auto; margin-top: 20px">
		<table class="gvtable">
			<thead>
				<tr>
					<th>模型名称</th>
					<th>模型类别</th>
					<th>模型描述</th>
					<th>模型等级</th>
				</tr>
			</thead>
			<c:forEach items="${modelList.pageList}" var="modlist">
				<tr>
					<td><a href="<%=basePath%>model/model/detail?productApproModelId=${modlist.productApproModelId }">${modlist.modelName }</a></td>
					<td>${modlist.modelType}</td>
					<td style="max-width: 600px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">${modlist.modelName}</td>
					<td><a href="<%=basePath%>model/grade/list?productApproModelId=${modlist.productApproModelId }">模型等级</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<!-- 分页 -->
	<div style="margin-top: 30px; font-size: 14px">
		<span>第<font color="red">${modelList.currentPage}</font>页/共${modelList.pageCount}页&nbsp;&nbsp;每页显示<font
			color="red">${modelList.pageSize}</font>条&nbsp;&nbsp;共${modelList.allSize}条记录
		</span> <span style="float: right;"> <span>[总共${modelList.currentPageSize}条数据]</span>
			<span><a
				href="<%=basePath%>model/grade/modellist?requsetPageNo=${modelList.prePage}">上一页</a></span>
			<span><a
				href="<%=basePath%>model/grade/modellist?requsetPageNo=${modelList.nextPage}">下一页</a></span>
			<span>转到<input style="width: 20px" type="text" name="pageNo"
				<c:if test="${modelList.currentPageSize lt 1}">readonly</c:if>>页
				<button>GO</button></span>
		</span>
	</div>
	<script type="text/javascript">
		function doDelete(url){
			if(confirm("确定删除吗？")){
				self.location.href=url;
			}
		}
		function doSearch(){
			self.location.href='<%=basePath%>model/grade/search?keyword='+$("#txtName").val();
		}
	</script>
</body>
</html>