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
<title>商家详情</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"/>
<script type="text/javascript" language="javascript">
function back(){
		window.location.href = "<%=basePath%>auth_center_info/business_info/list";
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
			<td class="text">单位名称</td>
			<td class="value"><span>${companyName}</span></td>
		</tr>
		<tr>
			<td class="text">单位性质</td>
			<td class="value"><span>${companyType}</span></td>
		</tr>
		<tr>
			<td class="text">企业类型</td>
			<td class="value"><span>${enterpriseType}</span></td>
		</tr>
		<tr>
			<td class="text">商家类型</td>
			<td class="value"><span>${shopType}</span></td>
		</tr>
		<tr>
			<td class="text">商家所在地区</td>
			<td class="value"><span>${province}${city}${county}</span></td>
		</tr>
		<tr>
			<td class="text">法人代表</td>
			<td class="value"><span>${legalPerson}</span></td>
		</tr>
		<tr>
			<td class="text">法人代表码</td>
			<td class="value"><span>${legalPresentCode}</span></td>
		</tr>
		<tr>
			<td class="text">通讯地址</td>
			<td class="value"><span>${address}</span></td>
		</tr>
		<tr>
			<td class="text">业务联系人</td>
			<td class="value"><span>${cantactPerson}</span></td>
		</tr>
		<tr>
			<td class="text">联系电话</td>
			<td class="value"><span>${tel}</span></td>
		</tr>
		<tr>
			<td class="text">手机</td>
			<td class="value"><span>${mobilePhone}</span></td>
		</tr>
		<tr>
			<td class="text">传真</td>
			<td  class="value"><span>${fax}</span></td>
		</tr>
		<tr>
			<td class="text">企业邮箱</td>
			<td class="value"><span>${mailBox}</span></td>
		</tr>
		<tr>
			<td class="text">注册商标</td>
			<td class="value">
				<img id="logo"
					style="width: 120px; height: 120px; background: #c2c2c2; cursor: pointer;"
					src="${logo}" />
			</td>
		</tr>
		<tr>
				<td class="text">商家简介</td>
				<td class="value">
				<span>${remarks}</span></td>
		</tr>
		
		<tr>
				<td class="text">已上传的证书</td>
				<td class="value">
					<div id = "certifyDiv" style = "width:100%; height:120px;; overflow:auto;cursor: pointer;">
						<c:forEach var="item" items="${certificateList}">
						<div class="wrapper" style = "width:33%; height:100%;float:left">
							<img style = "width:50%; height:100%; float:left;" src ="<%=basePath%>${item.picUrl}" />
							<span style = "width:50%; height:30%;">证书名称：${item.name}</span><br/>
							<span style = "width:50%; height:30%;">颁发机构：${item.publishUnit}</span><br/>
							<span style = "width:50%; height:10%;">颁发时间：</span><br/>
							<span style = "width:50%; height:30%;">${item.awardTime}</span>
							<div id="${item.businessId}" onclick="deleImg(this)" class="divdelete" style="display: block;"></div>
						</div>
						</c:forEach>
					</div>
				</td>
			</tr>
		
		<tr>
				<td class="text">&nbsp;</td>
				<td class="value">
				<input type="button" value="返回" class="btn_blue" onclick="back()"></td>
			</tr>
	</table>
</body>
</html>