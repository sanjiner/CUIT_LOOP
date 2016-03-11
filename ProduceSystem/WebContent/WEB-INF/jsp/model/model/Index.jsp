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
<title>模型制定</title>

<style type="text/css">

</style>
</head>

<body>	
		<div class="bread">
			<ul>
				<li>认证模型管理>></li>
				<li>模型制定</li>
			</ul>
		</div>

		<div class="left_nav">
			<div>
				<ul id="tree" class="filetree">	
				 <li>
				 	<span class="folder">
							<a href="<%=basePath%>model/model/list?parentId=0" target="modelFrame">模型类别</a>
					</span>	
					<ul>			
					<c:forEach items="${modelTypelist}" var="mtlist">
						<li>
							<span class="file">
								<a href="<%=basePath%>model/model/list?parentId=${mtlist.cuitMoonDictionaryCode}" target="modelFrame">${mtlist.cuitMoonDictionaryName}</a>
							</span>
						</li>
					</c:forEach>
					</ul>
				</li>
				</ul>
			</div>
		</div>


	<!-- iframe -->

	<div class="right_content">
		<iframe name="modelFrame"
			src="<%=basePath%>model/model/list?parentId=0"></iframe>
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
