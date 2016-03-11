<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<title>方案列表</title>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
</head>

<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5dp">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px;">应用中心>>实施方案>>方案列表</label>
	</div>
	<form id="form1" action="<%=basePath%>impl_plan/search_plan"
		method="post">
		<div>
			<div class="" style="margin: 0 auto">
				<span><label style="font-size: medium;">请输入要查找的申请的名称:</label><input
					type="text" id="key" value="${key}" name="key"></span> <span><input
					type="submit" class="btn_blue" value="搜索"></span>
			</div>
		</div>
	</form>
	<div>
		<div>
			<table class="gvtable" border="0">
				<thead>
					<tr height="30px">
						<th width="25%">产品名称</th>
						<th width="25%">申请时间</th>
						<th width="10%">是否建立方案</th>
						<th width="10%">审核状态</th>
						<th width="10%">审核方案</th>
						<th width="15%" style="text-align: center;">数据操作</th>
					</tr>
				</thead>
				<tbody id="list">
					<c:if test="${((fn:length(applyList) <= 0) || applyList == null)}">
						<tr>
							<td colspan="6" style="margin: 5px; width: 100%">暂无数据</td>
						</tr>
					</c:if>
					<c:forEach items="${applyList}" var="item">
						<tr>
							<td>${item.productName }</td>
							<td>${item.applyTime}</td>
							<td>${item.establishedPlan}</td>
							<td>${item.handleDescription}</td>
							<td><c:choose>
									<c:when test="${!(item.establishedPlan eq '是') }">
										<a>待建</a>
									</c:when>
									<c:when test="${(item.declareStatus eq '100028') }">
										<a>通过</a>
									</c:when>
									<c:otherwise>
										<shiro:hasPermission name="AuditControl">
											<a
												href="<%=basePath%>impl_plan/audit?applyBh=${item.applyBh}&num=${item.num}">审核</a>
										</shiro:hasPermission>
									</c:otherwise>
								</c:choose></td>
							<td style="text-align: center;"><c:choose>
									<c:when test="${!(item.establishedPlan eq '是') }">
										<shiro:hasPermission name="AddControl">
											<a
												href="<%=basePath %>impl_plan/addPlan?applyBh=${item.applyBh}">新建</a>
										</shiro:hasPermission>
									</c:when>
									<c:when test="${(item.declareStatus eq '100028') }">
										<a>通过</a>
									</c:when>
									<c:otherwise>
										<a>待审</a>
									</c:otherwise>
								</c:choose> <shiro:hasPermission name="DetailControl">
									<a
										href="<%=basePath%>impl_plan/details?applyBh=${item.applyBh}&num=${item.num}">查看</a>
								</shiro:hasPermission> <!--  <a href="#">编辑</a> --></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>

</html>
