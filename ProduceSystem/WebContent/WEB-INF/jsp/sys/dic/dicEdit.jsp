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
<link href="<%=basePath%>res/css/sys/tableStyle.css" rel="stylesheet"
	type="text/css" />
<script type="application/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery.validate.min.js"></script>
<title>修改字典</title>
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
			<form:form action="${ctx}sys/dic/edit" modelAttribute="dic"
				method="post">
				<table>
					<tr>
						<td>
							<p>字典名称</p>
							<input type="hidden" name="dicId" value="${dic.cuitMoonDictionaryId }">
							<form:hidden path="cuitMoonDictionaryId"/>
						</td>
						<td><form:input path="cuitMoonDictionaryName" /><form:errors cssClass="error" path="cuitMoonDictionaryName"/></td>
					</tr>
					<tr>
						<td>
							<p>编号</p>
						</td>
						<td><form:input path="cuitMoonDictionaryCode" /><form:errors cssClass="error" path="cuitMoonDictionaryCode"/><span class="error">${codeExist }</span></td>
					</tr>
					<tr>
						<td>
							<p>上级字典</p>
						</td>
						<td><select name="cuitMoonParentDictionaryCode">
								<option value="0">字典</option>
								<c:forEach items="${dicList }" var="pardic">
									<option <c:if test="${dic.cuitMoonParentDictionaryCode eq pardic.cuitMoonDictionaryCode }">selected="selected"</c:if> value="${pardic.cuitMoonDictionaryCode}">${pardic.cuitMoonDictionaryName}</option>
								</c:forEach>
							</select></td>
					</tr>
					<tr>
						<td>
							<p>备注信息</p>
						</td>
						<td><form:textarea path="cuitMoonDictionaryRemarks" cols="50"></form:textarea></td>
					</tr>
					<tr>
						<td></td>
						<td><input id="submitBtn" class="btn_blue" type="submit"
							value="提交" style="width: 89px;"><a href="javascript:history.go(-1)" target="dicFrame"
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
				cuitMoonDictionaryName : {
					required : true
				},
				cuitMoonDictionaryCode : {
					required : true,
					digits : true
				}
			},
			messages : {
				cuitMoonDictionaryName : {
					required : "请输入名称"
				},
				cuitMoonDictionaryCode : {
					required : "请输入编码",
					digits:"必须是数字"
				}
			}
		});
	});
	
	</script>

</body>
</html>
