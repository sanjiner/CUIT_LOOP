<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模型管理-气象要素-列表</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<div>
		&nbsp;&nbsp;<a href="<%=basePath%>model/grade/add?productApproModelId=<%=request.getParameter("productApproModelId") %>" class="addButton"></a>
		&nbsp; &nbsp;<a id="btnBack" href="javascript:history.go(-1);" class="btn_blue">返回</a>
	</div>
	<br />
	<div style="margin: 0 auto; margin-top: 20px">
		<table class="gvtable">
			<thead>
				<tr>
					<th>等级名称</th>
					<th>所属模型</th>
					<th>等级描述</th>
					<th>操作</th>
					<th>删除</th>
				</tr>
			</thead>
			<c:forEach items="${gradelList}" var="gradelList">
				<tr>
					<td>${gradelList.approveLevelName }</td>
					<td>${gradelList.remark }</td>
					<td style="max-width: 600px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">${gradelList.approveLevelDescription}</td>
					<td><a href="<%=basePath%>model/grade/edit?approveLevelId=${gradelList.approveLevelId }&productApproModelId=${gradelList.productApproModelId}">编辑</a></td>
					<td><a href="javascript:doDelete('<%=basePath%>model/grade/delete?approveLevelId=${gradelList.approveLevelId}&productApproModelId=${gradelList.productApproModelId}')">删除</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function doDelete(url) {
			if (confirm("确定删除吗？")) {
				self.location.href = url;
			}
		}
	</script>
</body>
</html>