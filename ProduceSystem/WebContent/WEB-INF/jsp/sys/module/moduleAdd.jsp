<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}/" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/sys/tableStyle.css" rel="stylesheet"
	type="text/css" />
<script type="application/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery.validate.min.js"></script>
<title>模块详情</title>
<style type="text/css">
.btn {
	border: 0;
	width: 100px;
	height: 35px;
	font-size: 18px;
	background-image: url("<%=basePath%>res/images/sys/button_yellow.png");
}

table {
	margin: 20px auto;
}

input {
	width: 400px;
}

.error {
	color: red;
}
</style>

</head>

<body>

	<div class="container">
		<div>
			<form:form action="${ctx}sys/module/add" modelAttribute="module"
				method="post">
				<table>
					<tr>
						<td>
							模块名称
						</td>
						<td><form:hidden path="cuitMoonModuleId" /> 
							<form:input
								path="cuitMoonModuleName" /> <form:errors cssClass="error"
								path="cuitMoonModuleName" /></td>
					</tr>
					<tr>
						<td>
							链接地址
						</td>
						<td><form:input path="cuitMoonModuleUrl" /></td>
					</tr>
					<tr>
						<td>
							上级模块
						</td>
						<td><form:hidden path="cuitMoonParentModuleId" />${module.cuitMoonParentModuleName }</td>
					</tr>
					<tr>
						<td>
							模块状态
						</td>
						<td><form:select path="cuitMoonModuleStatus">
								<form:option value="1">启用</form:option>
								<form:option value="0">禁用</form:option>
							</form:select></td>
					</tr>

					<tr>
						<td>
							描述信息
						</td>
						<td><form:textarea rows="3" cols="50"
								path="cuitMoonModuleDescription"></form:textarea></td>
					</tr>
					<tr>
						<td>
							备注信息
						</td>
						<td><form:textarea rows="3" cols="50"
								path="cuitMoonModuleRemarks"></form:textarea></td>
					</tr>
					<tr>
						<td></td>
						<td><input id="submitBtn" class="btn_blue" type="submit"
							value="提交" style="width: 89px;"><a href="javascript:history.go(-1)" target="moduleFrame"
							class="btn_blue"
							style="margin-left: 20px; width: 89px;">返回</a></td>
					</tr>
				</table>
			</form:form>
		</div>

	</div>


	<script type="application/javascript">
		
		
		
	$(document).ready(function() {
		$("form").validate({
			rules : {	
				cuitMoonModuleName : {
					required : true,
				}
			},
			messages : {
				cuitMoonModuleName : {
					required : "请输入模块名称",
				}
			
			}
		});
	});
	
	
	
	</script>

</body>
</html>
