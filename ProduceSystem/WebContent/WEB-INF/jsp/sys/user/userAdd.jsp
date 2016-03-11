<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	
<link href="<%=basePath%>res/js/My97DatePicker/skin/WdatePicker.css" rel="stylesheet"
	type="text/css" />
<script type="application/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery.validate.min.js"></script>
	
<script language="javascript" type="text/javascript" src="<%=basePath%>/res/js/My97DatePicker/WdatePicker.js"></script>
<title>编辑用户信息</title>
<style type="text/css">
input[type="text"]{
	width: 400px;
}
input[type="password"]{
	width: 400px;
}
</style>

</head>

<body>

	<div class="container">
		<div style="margin-top:20px;">
			<form:form id="userForm" action="${ctx}sys/user/add" method="post"
				modelAttribute="user">
				<table class="tablecontainer">
					<tr>
						<td>
							<p>用户名</p>
						</td>
						<td>
							<form:input path="cuitMoonUserName" />${errorMsg}
						</td>
					</tr>
					<tr>
						<td>
							<p>密码</p>
						</td>
						<td><input type="password" id="newPassword" name="newPassword" /></td>
					</tr>
					<tr>
						<td>
							<p>确认密码</p>
						</td>
						<td><form:password path="cuitMoonUserPassWord" /></td>
					</tr>
					<tr>
						<td>
							<p>角色</p>
						</td>
						<td>
							<table>
								<c:forEach items="${roles}" var="role" varStatus="status">
									<c:set var="flag" value="0" />
									<c:if test="${status.index % 4 == 0 }">
										<tr>
									</c:if>

									<c:forEach items="${user.tbcuitmoonRoles }" var="myrole">
										<c:if test="${myrole.cuitMoonRoleId eq role.cuitMoonRoleId}">
											<c:set var="flag" value="1" />
										</c:if>
									</c:forEach>
									<td><input name="roleIds" value="${role.cuitMoonRoleId}"
										type="checkbox"
										<c:if test="${flag eq 1}">checked="checked"</c:if>>${role.cuitMoonRoleName}</td>
									<c:if test="${status.index % 4 == 3 }">
										</tr>
									</c:if>
								</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<p>姓名</p>
						</td>
						<td><form:input path="cuitMoonUserRealName" /></td>
					</tr>
					<tr>
						<td>
							<p>生日</p>
						</td>
						<td><input type="text" name="userBirthday" class="Wdate" onClick="WdatePicker({lang:'zh-cn',readOnly:true})" value='<fmt:formatDate value="${user.cuitMoonUserBirthday}" pattern="yyyy-MM-dd" />'></td>
					</tr>
					<tr>
						<td>
							<p>手机</p>
						</td>
						<td><form:input path="cuitMoonUserCellphone" /></td>
					</tr>
					<tr>
						<td>
							<p>QQ</p>
						</td>
						<td><form:input path="cuitMoonUserQq" /></td>
					</tr>
					<tr>
						<td>
							<p>MSN</p>
						</td>
						<td><form:input path="cuitMoonUserMsn" /></td>
					</tr>
					<tr>
						<td>
							<p>Email</p>
						</td>
						<td><form:input path="cuitMoonUserEmail" /></td>
					</tr>
					<tr>
						<td>
							<p>地址</p>
						</td>
						<td><form:input path="cuitMoonUserAddress" /></td>
					</tr>
					<tr>
						<td>
							<p>邮编</p>
						</td>
						<td><form:input path="cuitMoonUserZipCode" /></td>
					</tr>

					<tr>
						<td>
							<p>地区</p>
						</td>
						<td><form:select id="cuitMoonAreaId" name="cuitMoonAreaId" path="cuitMoonAreaId">
								<c:forEach items="${areaList}" var="areaPro">
									<option value="${areaPro.cuitMoonAreaCode}">${areaPro.cuitMoonAreaName}</option>
									<c:forEach items="${areaPro.childAreas}" var="areaCity">
										<option value="${areaCity.cuitMoonAreaCode}">&nbsp;&nbsp;${areaCity.cuitMoonAreaName}</option>
										<c:forEach items="${areaCity.childAreas}" var="areaCountry">
											<option value="${areaCountry.cuitMoonAreaCode}">&nbsp;&nbsp;&nbsp;&nbsp;${areaCountry.cuitMoonAreaName}</option>
										</c:forEach>
									</c:forEach>
								</c:forEach>
							</form:select>
						</td>
					</tr>
					<tr>
						<td>
							<p>状态</p>
						</td>
						<td><form:select path="cuitMoonUserStatus">
								<form:option value="1">启用</form:option>
								<form:option value="0">禁用</form:option>
							</form:select></td>
					</tr>

					<tr>
						<td>
							<p>备注信息</p>
						</td>
						<td><form:textarea rows="3" cols="50"
								path="cuitMoonUserRemarks"></form:textarea></td>
					</tr>
					<tr>
						<td></td>
						<td><input id="submitBtn" class="btn_blue" type="submit"
							value="提交" style="width: 89px;"><a
							href="<%=basePath%>sys/user/userList?areaCode=${user.cuitMoonAreaId}"
							target="userFrame" class="btn_blue"
							style="margin-left: 20px; width: 89px;">返回</a></td>
					</tr>
				</table>
			</form:form>
		</div>

	</div>


	<script type="application/javascript">
		
		<%-- function getCity(code){
			if(code == "0"){
				
			}else{
				$.get("<%=basePath%>sys/area/getAreaJson?parentCode="+code,function(data){
					
					$("#city").empty();
					$("#cuitMoonAreaId").empty();
					for(var i= 0; i<data.length; i++){
						 $("#city").append("<option value=" + data[i].cuitMoonAreaCode + ">" + data[i].cuitMoonAreaName + "</option>");
					}
				});
			}
			
		}
		
		 $("#province").change(function(){
			var code = $("#province").val();
			getCity(code);
		 });
		
		 function getCounty(code){
			 if(code == "0"){
				 
			 }else{
				 $.get("<%=basePath%>sys/area/getAreaJson?parentCode="+code,function(data){			
						$("#cuitMoonAreaId").empty();
							for(var i= 0; i<data.length; i++){
								 $("#cuitMoonAreaId").append("<option value=" + data[i].cuitMoonAreaCode + ">" + data[i].cuitMoonAreaName + "</option>");
							}
						}); 
			 }
			 
		 }
		$("#city").change(function(){
				var code = $("#city").val();
				getCounty(code);
		}); --%>
		
		$(document).ready(function() {
			
			$("form").validate({
				rules:{
					cuitMoonUserRealName:{
						required: true
					},
					cuitMoonAreaId:{
						required: true
					},
					cuitMoonUserName:{
						required: true,
						remote: {
			                    type: "get",
			                    url: "<%=basePath%>sys/user/validUsername",
			                    data: {
			                        username: function() {
			                            return $("#cuitMoonUserName").val();
			                        }
			                    },
			                    dataType: "html",
			                    dataFilter: function(data, type) {
			                        if (data == "true")
			                            return false;
			                        else
			                            return true;
			                    }
			                }
					},
					newPassword:{
						required: true
					},
					cuitMoonUserPassWord:{
						required: true,
					    equalTo: "#newPassword"
					}
				},
				messages:{
					cuitMoonUserRealName:{
						required: "不能为空"
					},
					cuitMoonAreaId:{
						required: "不能为空"
					},
					cuitMoonUserName:{
						required: "不能为空",
						remote: "用户名已经存在"
					},
					newPassword:{
						required: "不能为空"
					},
					cuitMoonUserPassWord:{
						 required: "不能为空",
					     equalTo: "两次输入密码不一致不一致"
					}
				}
			});
		});
	
	</script>

</body>
</html>
