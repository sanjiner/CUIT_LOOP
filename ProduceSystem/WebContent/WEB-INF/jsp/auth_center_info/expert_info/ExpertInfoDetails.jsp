<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>专家详情</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"/>
<script type="text/javascript" language="javascript">
function back(){
	window.location.href = "<%=basePath%>auth_center_info/expert_info/list";
}
</script>
</head>
<body>
	<table class="info" cellpadding="0" cellspacing="0">
		<tr>
			<td class="text">用户名称</td>
			<td class="value"><span>${userName}</span></td>
		</tr>
		<tr>
			<td class="text">专家名称</td>
			<td class="value"><span>${expertname}</span></td>
		</tr>
		<tr>
			<td class="text">专家层次</td>
			<td class="value"><span>${expertLevel}</span></td>
		</tr>
		<tr>
			<td class="text">专家类别</td>
			<td class="value"><span>${expertCategory}</span></td>
		</tr>
		<tr>
			<td class="text">专家所属气象局</td>
			<td class="value"><span>${belongToMeteorology}</span></td>
		</tr>
		<tr>
			<td class="text">身份证号码</td>
			<td class="value"><span>${idnumber}</span></td>
		</tr>
		<tr>
			<td class="text">工作单位</td>
			<td class="value"><span>${workUnits}</span></td>
		</tr>
		<tr>
			<td class="text">毕业学校</td>
			<td class="value"><span>${schools}</span></td>
		</tr>
		<tr>
			<td class="text">手机号码</td>
			<td class="value"><span>${tel}</span></td>
		</tr>
		<tr>
			<td class="text">学历</td>
			<td class="value"><span>${educationalBackground}</span></td>
		</tr>
		<tr>
			<td class="text">Email</td>
			<td class="value"><span>${mailBox}</span></td>
		</tr>
		<tr>
			<td class="text">联系地址</td>
			<td class="value"><span>${address}</span></td>
		</tr>
		<tr>
			<td class="text">邮编</td>
			<td class="value"><span>${postCode}</span></td>
		</tr>
		<tr>
			<td class="text">QQ</td>
			<td class="value"><span>${qq}</span></td>
		</tr>
		<tr>
			<td class="text">专家简介</td>
			<td class="value"><span>${expertIntroduction}</span></td>
		</tr>
		<tr>
			<td class="text">备注信息</td>
			<td class="value"><span>${remark}</span></td>
		</tr>
		<tr>
				<td class="text"></td>
				<td class="value">
					<input type="button" value="返回" class="btn_blue" onclick="back()"></td>
		</tr>
	</table>
</body>
</html>