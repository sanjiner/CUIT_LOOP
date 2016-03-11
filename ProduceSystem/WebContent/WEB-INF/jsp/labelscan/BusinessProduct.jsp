<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String basePath2 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商家产品信息</title>
<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">

</script>
</head>
<body bgcolor="#cccccc" style="margin-left:1%; margin-right: 1%">
 	<div>
 		<img src="<%=basePath%>res/images/labelscan/logo2.jpg" style="height: 20%;width: 100%">
 		<div style="${divStyle};text-align: right;">
 		<input type="button" value="back" onclick="javascript:history.go(-1);"/>
 	</div>
 	</div>
 	<br/>
 	<div style="background-color: #FFFFFF">
 		<table style="width: 100%">
 			<tr style="text-align: center;"><td><h2>${productName}</h2></td></tr>
 			<tr><td><small>发布时间：${creTime}</small></td></tr>
 			<tr style="text-align: center;"><td><img alt="" style="width: 70%" src="<%=basePath2%>${productImage}"></td></tr>
 			<tr><td><p style="TEXT-INDENT: 2em">${productContent}</p></td></tr>
 		</table>
 	</div>
 	<br/>
 	<div style="text-align: center; width: 100%">
 		<table style="text-align: center; width: 100%">
 			<tr><td>蜀ICP备11018099号-1</td></tr>
 			<tr><td>版权所有：四川农村经济综合信息中心</td></tr>
 			<tr><td>业务咨询电话：028-87360982 028-87345251</td></tr>
 			<tr><td>邮箱：scnjw@sina.com scnjw@foxmail.com</td></tr>
 			<tr><td>地址：四川省成都市青羊区光华村街20号（四川省气象局塔楼）</td></tr>
 		</table>
 	</div>
</body>
</html>