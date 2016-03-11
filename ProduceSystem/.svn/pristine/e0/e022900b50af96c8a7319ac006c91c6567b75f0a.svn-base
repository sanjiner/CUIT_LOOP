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
<%-- <link href="<%=basePath%>res/css/common/commonStyle.css" rel="stylesheet"
	type="text/css" /> --%>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />

<title>新闻</title>
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

<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">企业个性化管理>>新闻管理>>新闻列表</label>
	</div>
	<form id="form1" action="<%=basePath%>enterprise/news/list" method="post">
		<div class="container">
			<div class="" style="margin: 0 auto">
				<span><label style="font-size: medium;">请输入要查找的新闻标题:</label><input
					type="text" id="key" value="${key}" name="key"></span> <span><input
					type="submit" onclick="return validate()" class="btn_blue"
					value="搜索"></span> <span>
					<shiro:hasPermission
								name="NewsAdd">
					<a
					href="<%=basePath%>enterprise/news/add" class="btn_blue"
					target="mainFrame">新增</a>
					</shiro:hasPermission></span>
			</div>
		</div>
	</form>
	<div style="margin-top: 10px;">
		<table class="gvtable" border="0">
			<thead>
				<tr>
					<th width="40%">新闻标题</th>
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
						<td>${item.newstitle}</td>
						<td><fmt:formatDate value="${item.cretime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${item.qiyenum}</td>
						<td>
						<shiro:hasPermission
								name="NewsDetails"><a
							href="<%=basePath%>enterprise/news/details?newsId=${item.newsId}">查看</a>
							</shiro:hasPermission>
							<shiro:hasPermission
								name="NewsEdit">
							<a href="<%=basePath%>enterprise/news/edit?newsId=${item.newsId}">编辑</a>
							</shiro:hasPermission>
							<shiro:hasPermission
								name="NewsDelete">
							<a style="width: 60px"
							onclick="if(confirm('确认删除?') == false)return false;"
							href="<%=basePath%>enterprise/news/delete?newsId=${item.newsId}&requsetPageNo=${list.currentPage}">删除</a>
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
				href="<%=basePath%>enterprise/news/list?requsetPageNo=${list.prePage}">上一页</a></span>
			<span><a style="width: auto;"
				href="<%=basePath%>enterprise/news/list?requsetPageNo=${list.nextPage}">下一页</a></span>
			<span>转到<input style="width: 20px" type="text" name="pageNo">页
				<input type="button" value="跳转" class="btn_blue"></span>
		</span>
	</div>
</body>

</html>
