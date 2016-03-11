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

<title>档案列表</title>
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
			alert("请输入要查找的产品名称");
			return false;
		}
		return true;
	}
</script>
</head>

<bodystyle="background: #edf1fc">
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">应用中心>>品质档案>>档案列表</label>
	</div>
	<form id="form1" action="<%=basePath%>quality_archive/list"
		method="post">
		<div class="container">
			<div class="" style="margin: 0 auto">
				<span><label style="font-size: medium;">请输入要查找的产品名称:</label><input
					type="text" id="key" value="${key}" name="key"></span> <span><input
					type="submit" onclick="return validate()" class="btn_blue"
					value="搜索"></span>
			</div>
		</div>
	</form>
	<div style="margin-top: 10px;">
		<table class="gvtable" border="0">
			<thead>
				<tr>
					<th width="20%">公司名称</th>
					<th width="20%">产品名称</th>
					<th width="20%">产品品牌</th>
					<th width="20%">产地地址</th>
					<th width="10%">受理机构</th>
					<th width="10%">数据操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${applyPage.pageList.size() <= 0}">
					<tr>
						<td colspan="6" style="margin: 5px">暂无数据</td>
					</tr>
				</c:if>
				<c:forEach items="${applyPage.pageList}" var="apply">
					<tr>
						<td>${apply.unityName}</td>
						<td>${apply.productName}</td>
						<td>${apply.productBrand}</td>
						<td>${apply.address}</td>
						<td>${apply.handleDescription}</td>
						<td><c:choose>
								<c:when test="${apply.declareStatus eq '100029' }">
									<a>待归档</a>
								</c:when>
								<c:when test="${apply.declareStatus eq '100030' }">
									<a>已归档</a>
								</c:when>
								<c:otherwise>
									<shiro:hasPermission name="AddCase">
										<a
											href="<%=basePath%>quality_archive/add?applyBh=${apply.applyBh}">建档</a>
									</shiro:hasPermission>
								</c:otherwise>
							</c:choose> <shiro:hasPermission name="DetailCase">
								<a
									href="<%=basePath%>quality_archive/details?applyBh=${apply.applyBh}">详情</a>
							</shiro:hasPermission></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- 分页 -->
	<div>
		<span>第<font color="red">${applyPage.currentPage}</font>页/共${applyPage.pageCount}页&nbsp;&nbsp;每页显示<font
			color="red">${applyPage.pageSize}</font>条&nbsp;&nbsp;共${applyPage.allSize}条记录
		</span> <span style="float: right;"> <span>[总共${applyPage.currentPageSize}条数据]</span>
			<span><a style="width: auto;"
				href="<%=basePath%>authc/showApply?requsetPageNo=${applyPage.prePage}">上一页</a></span>
			<span><a style="width: auto;"
				href="<%=basePath%>authc/showApply?requsetPageNo=${applyPage.nextPage}">下一页</a></span>
			<span>转到<input style="width: 20px" type="text" name="pageNo">页
				<input type="button" value="跳转" class="btn_blue"></span>
		</span>
	</div>
	</div>
</body>

</html>
