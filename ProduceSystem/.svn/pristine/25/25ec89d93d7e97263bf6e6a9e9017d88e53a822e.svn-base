<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>认证须知</title>
<style>
body {
	display: block;
	font: "黑体";
	margin: 0px;
}

.container {
	text-align: center;
}

.notice {
	position: relative;
	width: 600px;
	height: 500px;
	margin: 0 auto;
	background-color: #F3F3F3;
	margin-top: 10px;
	border: #333 dashed 1px;
	margin-bottom: 20px;
}

.noticetile {
	
}

.hasread {
	margin-top: 20px;
}

.hasread span {
	font-size: 14px;
	color: #666;
}

.hasread .next_step {
	margin-top: 10px;
}
</style>
<script type="text/javascript">
	function validate() {
		//alert($("#checkBox").prop("checked"));
		if ($("#checkBox").is(':checked')) {
			return true;
		} else {
			return false;
		}
	}
</script>
</head>

<body>
	<div class="container">

		<div class="notice">
			<div class="noticetile">
				<h2>
					用户须知
					<h2>
			</div>
			<div class="noticecontent">
				<p>用户须知内容</p>
			</div>

		</div>

		<div class="hasread">
			<div class="">
				<input id="checkBox" type="checkbox" checked="checked"><span>已阅读</span>
			</div>
			<div class="next_step">
				<a href="<%=basePath%>authc/addApply" onclick="return validate()"
					target="mainFrame">下一步</a>
			</div>

		</div>

	</div>
</body>
</html>
