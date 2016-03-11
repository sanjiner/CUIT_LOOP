<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>res/css/right/sysIndexStyle.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>res/css/sys/sysStyle.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>res/css/right/bread.css">
<link rel="stylesheet"
	href="<%=basePath%>res/js/jquery.treeview/jquery.treeview.css" />
<script src="<%=basePath%>res/js/jquery.treeview/lib/jquery.js"
	type="text/javascript"></script>
<script src="<%=basePath%>res/js/jquery.treeview/lib/jquery.cookie.js"
	type="text/javascript"></script>
<script src="<%=basePath%>res/js/jquery.treeview/jquery.treeview.js"
	type="text/javascript"></script>
<title>角色管理</title>

<style type="text/css">

</style>
</head>

<body>	
		<div class="bread">
			<ul>
				<li>系统管理>></li>
				<li>角色管理</li>
			</ul>
		</div>

		<div class="left_nav">
			<div>
				<ul id="tree" class="filetree">
					
					<c:forEach items="${roleList}" var="role">
						<li>
							<c:choose>
								<c:when test="${role.childRoles.size() == 0 }">
									<span class="file">
										<a href="<%=basePath%>sys/role/roleList?parentRoleId=${role.cuitMoonRoleId}" target="roleFrame">${role.cuitMoonRoleName }</a></span>
								</c:when>
								<c:otherwise>
									<span class="folder">
										<a href="<%=basePath%>sys/role/roleList?parentRoleId=${role.cuitMoonRoleId}" target="roleFrame">${role.cuitMoonRoleName }</a></span>
										<ul>
											<c:forEach items="${role.childRoles}" var="chileRole">
												<li>
													<span class="file">
														<a href="javascript:void(0)" target="roleFrame">${chileRole.cuitMoonRoleName }</a></span>
												</li>
											</c:forEach>
										</ul>
								</c:otherwise>
							</c:choose>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>


	<!-- iframe -->

	<div class="right_content">
		<iframe name="roleFrame"
			src="<%=basePath%>sys/role/roleList?parentRoleId=0"></iframe>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#tree").treeview({
				collapsed : true,
				unique: true
			});
		});
	</script>

</body>
</html>
