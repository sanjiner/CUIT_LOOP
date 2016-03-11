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
<title>审核列表</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript">
 function add() {
	window.location.href = "<%=basePath%>origin/OAadd";
}
 function go() {
	 var pageIndex = document.getElementById("page").value;
	 window.location.href = "<%=basePath%>
	origin/OAlist?requsetPageNo="
				+ pageIndex;
	}
</script>
</head>
<body>
	<div
		style="background: #4c9ee7; width: 100%; height: 30px; margin-bottom: 5px">
		<label
			style="width: auto; height: 30px; text-align: center; color: white; line-height: 30px; margin-left: 5px">应用中心>>农网认证>>审核列表</label>
	</div>
	<div>
		&nbsp;&nbsp;产品名称： <input type="text" id="txtProductName"> <input
			type="submit" class="btn_blue" value="查询">
	</div>
	<table class="gvtable">
		<thead>
			<tr>
				<th width="20">产品名称</th>
				<th width="25%">产品品牌</th>
				<th width="20%">申请商家</th>
				<th width="10%">申请人</th>
				<th width="10%">申请时间</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${productapplyPage.pageList}" var="plist"
				varStatus="vs">
				<tr>
					<td>${plist.originName }</td>
					<td>${plist.productBrand }</td>
					<td>${plist.applyCompany }</td>
					<td>${plist.applyPerson }</td>
					<td><fmt:formatDate value="${plist.applyTime }" type="date"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><c:choose>
							<c:when test="${plist.orignStatus.trim() eq '100084' }">
								<a>通过</a>
							</c:when>
							<c:when test="${plist.orignStatus.trim() eq '100083' }">
								<a>失败</a>
							</c:when>
							<c:when test="${plist.orignStatus.trim() eq '100082' }">
								<a>退回</a>
							</c:when>
							<c:when test="${plist.orignStatus.trim() eq '100023'}">
								<a href="<%=basePath%>org_exam/audit?id=${plist.originId }">审核</a>
							</c:when>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- 分页 -->
	<div>
		<span>第<font color="red">${productapplyPage.currentPage}</font>页/共${productapplyPage.pageCount}页&nbsp;&nbsp;每页显示<font
			color="red">${productapplyPage.pageSize}</font>条&nbsp;&nbsp;共${productapplyPage.allSize}条记录
		</span> <span style="float: right;"> <span>[总共${productapplyPage.currentPageSize}条数据]</span>
			<span><a
				href="<%=basePath%>org_exam/list?requsetPageNo=${productapplyPage.prePage}">上一页</a></span>
			<span><a
				href="<%=basePath%>org_exam/list?requsetPageNo=${productapplyPage.nextPage}">下一页</a></span>
			<span>转到<input style="width: 10px" type="text" id="page"
				name="pageNo" onKeyUp="value=value.replace(/\D/g,'')">页
				<button onclick="go()">GO</button></span>
		</span>
	</div>
</body>
</html>