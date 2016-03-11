<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商家详情</title>
</head>
<body>
	<table border="1" bordercolor="black" width="100%" frame="void"
		cellspacing="0">
		<!-- 用户名称这行 -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td width="200">用户名称</td>
			<td>${ bsm.userName}</td>
		</tr>
		<!-- 用户名称这行 -->

		<!--单位名称这行  -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>单位名称</td>
			<td>${bsm.campanyName }</td>
		</tr>
		<!--单位名称这行  -->
		<!--单位性质这行  -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>单位性质</td>
			<td>${ bsm.campanyType}</td>
		</tr>
		<!--单位性质这行  -->
		<!-- 企业类型 这行-->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>企业类型</td>
			<td>${ bsm.companyType}</td>
		</tr>
		<!-- 企业类型 这行-->
		<!-- 商家类型这行 -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>商家类型</td>
			<td>${bsm.businessType }</td>
		</tr>
		<!-- 商家类型这行 -->
		<!-- 商家所在地区这行 -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>商家所在地区</td>
			<td>${ bsm.businessArea}</td>
		</tr>
		<!-- 商家所在地区这行 -->
		<!--法人代表这行  -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>法人代表</td>
			<td>${ bsm.legalPerson}</td>
		</tr>
		<!--法人代表这行  -->
		<!-- 法人代表码这行 -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>法人代表码</td>
			<td>${ bsm.legalPresentCode}</td>
		</tr>
		<!-- 法人代表码这行 -->
		<!--注册时间这行  -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>注册时间</td>
			<td>${ bsm.addTime}</td>
		</tr>
		<!--注册时间这行  -->
		<!--通讯地址这行  -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>通讯地址</td>
			<td>${ bsm.address}</td>
		</tr>
		<!--通讯地址这行  -->
		<!--业务联系人这行  -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>业务联系人</td>
			<td>${ bsm.cantactPerson}</td>
		</tr>
		<!--业务联系人这行  -->
		<!--联系电话这行  -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>联系电话</td>
			<td>${ bsm.tel}</td>
		</tr>
		<!--联系电话这行  -->
		<!-- 传真这行 -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>传真</td>
			<td>${ bsm.fax}</td>
		</tr>
		<!-- 传真这行 -->
		<!-- 企业邮箱这行 -->
		<tr bgcolor="white" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<td>企业邮箱</td>
			<td>${ bsm.mailBox}</td>
		</tr>
		<!-- 企业邮箱这行 -->
		
	</table>
	<button onclick="">返回</button>
</body>
</html>