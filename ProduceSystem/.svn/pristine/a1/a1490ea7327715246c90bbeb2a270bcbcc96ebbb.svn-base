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
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"
	type="text/css" />

<script type="application/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery.validate.min.js"></script>
<title>添加地区</title>
<style type="text/css">
.btn {
	border: 0;
	width: 100px;
	height: 35px;
	font-size: 18px;
	background-image: url("<%=basePath%>res/images/sys/button_yellow.png");
}

table {
	margin: 0 auto;
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
			<form:form action="${ctx}sys/area/add" modelAttribute="area"
				method="post">
				<table>
					<tr>
						<td>
							<p>地区名称</p>
						</td>
						<td><form:input path="cuitMoonAreaName" /><span class="error">${nameIsNull }</span></td>
					</tr>
					<tr>
						<td>
							<p>地区编号</p>
						</td>
						<td><input id="cuitMoonAreaCode" name="cuitMoonAreaCode" ><span class="error">${codeExist }</span></td>
					</tr>
					<tr>
						<td>
							<p>上级地区</p>
						</td>
						<td><form:select path="cuitMoonParentAreaCode">
								<form:option value="0">---选择上级地区---</form:option>
								<c:forEach items="${topAreaList }" var="topArea">
									<form:option value="${topArea.cuitMoonAreaCode}">${topArea.cuitMoonAreaName}</form:option>
									<c:forEach items="${topArea.childAreas}" var="childArea">
										<form:option value="${childArea.cuitMoonAreaCode}">&nbsp;&nbsp;&nbsp;&nbsp;${childArea.cuitMoonAreaName}</form:option>
									</c:forEach>
								</c:forEach>
							</form:select></td>
					</tr>
					<tr>
						<td>
							<p>备注信息</p>
						</td>
						<td><form:textarea path="cuitMoonAreaRemarks" cols="50"></form:textarea></td>
					</tr>
					<tr>
						<td></td>
						<td><input id="submitBtn" class="btn_blue" type="submit"
							value="提交" style="width: 89px;"><a href="javascript:history.go(-1)" target="jurFrame"
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
				cuitMoonAreaName : {
					required : true
				},
				cuitMoonAreaCode : {
					required : true,
					digits : true
				}
			},
			messages : {
				cuitMoonAreaName : {
					required : "请输入名称"
				},
				cuitMoonAreaCode : {
					required : "请输入名称",
					digits:"必须是数字"
				}
			}
		});
	});
	
	</script>

</body>
</html>
