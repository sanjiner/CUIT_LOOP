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

<title>产品</title>
<style type="text/css">
body {
	font-family: "黑体";
}

.container {
	width: 100%;
	margin: 0 auto;
}

a {
	display: inline-block;
	width: 50px;
	float: inherit;
}
</style>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	function validate() {
		if ($("#edit_search_key").val() == "") {
			alert("请输入要查找的新闻标题");
			return false;
		}
		return true;
	}
</script>
</head>

<body  style="background: #edf1fc;">
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">企业个性化管理>>产品管理>>产品列表</label>
	</div>
	<form id="form1" action="<%=basePath%>enterprise/produce/list" method="post">
		<div class="container">
			<div class="" style="margin: 0 auto">
				<span><label style="font-size: medium;">请输入要查找的产品名称:</label><input
					type="text" id="key" value="${key}" name="key"></span> <span><input
					type="submit" onclick="return validate()" class="btn_blue"
					value="搜索"></span>
					<shiro:hasPermission
								name="ProduceAdd"> 
					<span><a
					href="<%=basePath%>enterprise/produce/add" class="btn_blue"
					target="mainFrame">新增</a></span>
					</shiro:hasPermission>
			</div>
		</div>
	</form>
	<div style="margin-top: 10px;">
		<table class="gvtable" border="0">
			<thead>
				<tr>
					<th width="40%">产品名称</th>
					<th width="20%">发布时间</th>
					<th width="20%">添加人</th>
					<th width="20%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${list.pageList.size() <= 0}">
					<tr>
						<td colspan="4" style="margin: 5px">暂无数据</td>
					</tr>
				</c:if>
				<c:forEach items="${list.pageList}" var="item">
					<tr>
						<td>${item.productname}</td>
						<td><fmt:formatDate value="${item.cretime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${item.qiyenum}</td>
						<td>
						<shiro:hasPermission
								name="ProduceDetail"> 
						<a
							href="<%=basePath%>enterprise/produce/details?productId=${item.productId}">查看</a>
							</shiro:hasPermission>
							<shiro:hasPermission
								name="ProduceEdit">
							<a href="<%=basePath%>enterprise/produce/edit?productId=${item.productId}">编辑</a>
							</shiro:hasPermission>
							<shiro:hasPermission
								name="ProduceDelte">
							<a style="width: 60px"
							onclick="if(confirm('确认删除?') == false)return false;"
							href="<%=basePath%>enterprise/produce/delete?productId=${item.productId}&requsetPageNo=${list.currentPage}">删除</a>
							</shiro:hasPermission>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- 分页 -->
	<div>
		<span>第<font color="red">${list.currentPage}</font>页/共${list.pageCount}页&nbsp;&nbsp;每页显示<font
			color="red">${list.pageSize}</font>条&nbsp;&nbsp;共${list.allSize}条记录
		</span> <span style="float: right;"> <span>[总共${list.currentPageSize}条数据]</span>
			<span><a style="width: auto;"
				href="<%=basePath%>enterprise/produce/list?requsetPageNo=${list.prePage}">上一页</a></span>
			<span><a style="width: auto;"
				href="<%=basePath%>enterprise/produce/list?requsetPageNo=${list.nextPage}">下一页</a></span>
			<span>转到<input style="width: 20px" type="text" name="pageNo">页
				<input type="button" value="跳转" class="btn_blue"></span>
		</span>
	</div>
</body>

</html>
