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
<title>商家信息</title>
<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">

	
</script>
</head>
<body bgcolor="#cccccc" style="margin-left:1%; margin-right: 1%">
 	<div>
 		<img src="<%=basePath%>res/images/labelscan/logo2.jpg" style="height: 20%;width: 100%">
 	</div>
 	<div style="${divStyle};text-align: right;">
 		<input type="button" value="back" onclick="javascript:history.go(-1);"/>
 	</div>
 	<br/>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >商家信息</label></div>
 	<div style="background-color: #FFFFFF"> 
 		<table>
 			<tr><td>企业名称：${campanyName}</td></tr>
 			<tr><td>企业类型：${campanyType}</td></tr>
 			<tr><td>联系人：${cantactPerson}</td></tr>
 			<tr><td>联系电话：${tel}</td></tr>
 			<tr><td>邮箱：${email}</td></tr>
 			<tr><td>地址：${address}</td></tr>
 		</table>
 	</div>
 	<br/>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >新闻信息</label></div>
 	<div style="background-color: #FFFFFF"> 
 		<table>
 			<c:forEach items="${newslist}" var="nlist" varStatus="nvs">
 				<tr>
 				<td><a href="<%=basePath%>labelscan/BusinessNews?id=${nlist.newsId}">${nlist.newstitle}</a></td>
 				<td>
 					&emsp;${nlist.cretime.toString().substring(0,7)}
 				</td>
 				</tr>	
 			</c:forEach>
 		</table>
 	</div>
 	<br/>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >产品信息</label></div>
 	<div style="background-color: #FFFFFF"> 
 		<table>
 			<c:forEach items="${productlist}" var="plist" varStatus="pvs">
 				<tr>
 				<td><a href="<%=basePath%>labelscan/BusinessProduct?id=${plist.productId}">${plist.productname}</a>
 				</td>
 				<td>
 				&emsp;${plist.cretime.toString().substring(0,7) }
 				</td>
 				</tr>	
 			</c:forEach>
 		</table>
 	</div>
 	<br/>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >已认证的产品</label></div>
 	<div style="background-color: #FFFFFF"> 
 		<table>
 			<c:forEach items="${alist1}" var="alist1" varStatus="avs">
 				<tr>
 				<td>${alist1.productName}</td>
 				<td>
 					&emsp;${alist1.applyTime.toString().substring(0,7)}
 				</td>
 				</tr>	
 			</c:forEach>
 			<c:forEach items="${plist1}" var="plist1" varStatus="pvs">
 				<tr>
 				<td>${plist1.originName}</td>
 				<td>
 					&emsp;${plist1.applyTime.toString().substring(0,7)}
 				</td>
 				</tr>	
 			</c:forEach>
 		</table>
 	</div>
 	<br/>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >认证中的产品</label></div>
 	<div style="background-color: #FFFFFF"> 
 		<table>
 			<c:forEach items="${alist2}" var="alist2" varStatus="avs">
 				<tr>
 				<td>${alist2.productName}</td>
 				<td>
 					&emsp;${alist2.applyTime.toString().substring(0,7)}
 				</td>
 				</tr>	
 			</c:forEach>
 			<c:forEach items="${plist2}" var="plist2" varStatus="pvs">
 				<tr>
 				<td>${plist2.originName}</td>
 				<td>
 					&emsp;${plist2.applyTime.toString().substring(0,7)}
 				</td>
 				</tr>	
 			</c:forEach>
 		</table>
 	</div>
 	<br/>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >相关资质证书</label></div>
 	<div style="background-color: #FFFFFF"> 
 		<table style="margin:auto;">
 			<c:forEach items="${certiImages}" var="certilist" varStatus="certivs">
 				<tr><td><img alt="" style="width: 70%" src="<%=basePath2%>${certilist.picUrl}"></td></tr>	
 			</c:forEach>
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