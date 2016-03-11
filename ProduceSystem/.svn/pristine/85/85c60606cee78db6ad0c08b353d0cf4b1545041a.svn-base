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


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<script type="application/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery.validate.min.js"></script>
<title>添加角色</title>
<style type="text/css">
table.tablecontainer {
	margin: 0 auto;
	text-align: left;
	border-collapse: collapse;
	border-spacing: 0;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
	border-left: 1px solid #CDCDCD;
	border-top: 1px solid #CDCDCD;
}

th, td {
	border-right: 1px solid #CDCDCD;
	border-bottom: 1px solid #CDCDCD;
	padding: 5px 5px;
}

th {
	font-weight: bold;
	background: #ccc;
}
</style>

</head>

<body>

	<div class="container">
		<div>
			<form:form action="" modelAttribute="role" method="post">
				<table class="tablecontainer">
					<tr>
						<td>
							<div>角色名称</div>
						</td>
						<td><form:input path="cuitMoonRoleName" /></td>
					</tr>


					<tr>
						<td><input type="checkbox" id="" name="" value="" class="chkAll">角色权限</td>
						<td>
							<table>
								<c:forEach items="${moduleList}" var="parentModule">

									<tr>
										<td><input type="checkbox" id="" name="moduleIds" value="${parentModule.cuitMoonModuleId }" class="chkModule">${parentModule.cuitMoonModuleName}</td>
										<td>
											<table style="width: 100%">
												<c:forEach items="${parentModule.childModulesList}"
													var="childModule">
													<tr>
														<td style="width: 140px;"><input type="checkbox" id="" name="moduleIds"  value="${childModule.cuitMoonModuleId }" class="chkModule">${childModule.cuitMoonModuleName}</td>
														<td><c:forEach
																items="${childModule.tbcuitmoonJurisdictions}"
																var="jurisdictions" varStatus="status">
																<span><input type="checkbox" class="chkjur"
																	name="jurisdictionIds"
																	value="${jurisdictions.cuitMoonJurisdictionId}">${jurisdictions.cuitMoonJurisdictionName }</span>
																<c:if test="${status.count % 4 == 0}"><br></c:if>
															</c:forEach></td>
													</tr>
												</c:forEach>
											</table>
										</td>
									</tr>

								</c:forEach>
							</table>
						</td>
					</tr>

					<tr>
						<td>父级角色</td>
						<td><input type="hidden" name="cuitMoonParentRoleId"
							value="${role.cuitMoonParentRoleId }">${role.cuitMoonParentRoleName }</td>
					</tr>

					<tr>
						<td>角色状态</td>
						<td><form:select path="cuitMoonRoleStatus">
								<form:option value="1">启用</form:option>
								<form:option value="0">禁用</form:option>
							</form:select></td>
					</tr>

					<tr>
						<td>角色说明</td>
						<td><form:textarea path="cuitMoonRoleDescription" rows="3"
								cols="50"></form:textarea></td>
					</tr>

					<tr>
						<td>备注信息</td>
						<td><form:textarea path="cuitMoonRoleRemarks" rows="3"
								cols="50"></form:textarea></td>
					</tr>
					<tr>
						<td></td>
						<td><input id="submitBtn" class="btn_blue" type="submit"
							value="提交" style="width: 89px; line-height: 0px;"><a
							href="<%=basePath%>sys/role/roleList?parentRoleId=${role.cuitMoonParentRoleId}"
							target="roleFrame" class="btn_blue"
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
				cuitMoonRoleName : {
					required : true,
				}
			},
			messages : {
				cuitMoonRoleName : {
					required : "请输入名称",
				}
			
			}
		});
		
		 $("input[type=checkbox].chkAll").click(function () {
             if (this.checked) {
                 $("input[type=checkbox]").each(function () { this.checked = true; });
             }
             else {
                 $("input[type=checkbox]").each(function () { this.checked = false; });
             }
         });

         $("input[type=checkbox].chkModule").click(function () {
             if (this.checked) {
                 $(this).parent().parent().find("input[type=checkbox]").each(function () { this.checked = true; });
             }
             else {
                 $(this).parent().parent().find("input[type=checkbox]").each(function () { this.checked = false; });
             }
         });
         $("input[type=checkbox].chkjur").click(function () {
             if (this.checked) {
                 $(this).parent().parent().parent().find("input[type=checkbox].chkModule").each(function () { this.checked = true; });
                 $(this).parent().parent().parent().parent().parent().parent().parent().parent().parent().find("input[type=checkbox]").first().each(function () { this.checked = true; });
             }
         });
         $("input[type=checkbox].chkModule").change(function () {
             if (this.checked) {
                 $(this).parent().parent().parent().parent().parent().parent().find("input[type=checkbox]").first().each(function () { this.checked = true; });
             }
         });
	});
	</script>

</body>
</html>
