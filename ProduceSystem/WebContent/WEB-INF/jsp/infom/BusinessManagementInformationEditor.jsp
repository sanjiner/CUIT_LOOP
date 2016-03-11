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
<title>编辑商家</title>
</head>

<!-- <script type="text/javascript">
function start(a,b )
{
    document.getElementById(a).value;
    
    document.getElementById(b).value=num1;
}

</script> -->
<body>

	
	<form action="<%=basePath%>infom/BusinessManagementInformationEditor"
		name="form2" method="post" >
		
		<!-- 用户名称表 -->
		<table border="1" bordercolor="black" width="100%" frame="void" cellspacing="0">
		<tr >
		<td  align="right" style="width:120px; height: 21px;" valign="middle">用户名称</td>
		<td ><input type="text" name="userName" style="width:600px;" value=""></td>
		</tr>
		</table>
		<!-- 用户名称表 -->
		<!-- 单位名称表 -->
		<table border="1" bordercolor="black" width="100%" frame="void" cellspacing="0">
		<tr >
		<td  align="right" style="width:120px; height: 21px;" valign="middle">单位名称</td>
		<td ><input type="text" name="campanyName" style="width:600px;" value=""></td>
		</tr>
		</table>
		<!--单位名称表  -->
		<!-- 单位性质表 -->
		<table border="1" bordercolor="black" width="100%" frame="void" cellspacing="0">
		<tr>
		<td align="right" style="width:120px; height: 21px;" valign="middle">单位性质</td>
		<td ><select name="campanyType" size="1"  style="width:600px;">
		<c:forEach var="danweixinzhilist" items="${danweixinzhilist}">
		 <option>${danweixinzhilist}</option>
		</c:forEach>
		</select></td>
		
		</tr>
		</table>
		<!-- 企业类型表 -->
		
		<table border="1" bordercolor="black" width="100%" frame="void"  cellspacing="0">
		<tr>
		<td align="right" style="width:120px; height: 21px;" valign="middle">企业类型</td>
		<td ><select name="companyType" size="1"  style="width:600px;">
		<c:forEach var="qiyeleixinglist" items="${qiyeleixinglist}">
		 <option>${qiyeleixinglist}</option>
		</c:forEach>
		</select></td>
		</tr>
		</table>
		<!-- 商家类型表 -->
		
		<table border="1" bordercolor="black" width="100%"  frame="void"   cellspacing="0">
		<tr>
		<td align="right" style="width:120px; height: 21px;" valign="middle">商家类型</td>
		<td ><select name="businessType" size="1"   style="width:600px;">
		<c:forEach var="shangjialeixinglist" items="${shangjialeixinglist}">
		 <option>${shangjialeixinglist}</option>
		</c:forEach>
		</select></td>
		</tr>
		</table>
		<!-- 商家所在地区表 -->
		
		<table border="1" bordercolor="black" width="100%"   frame="void"   cellspacing="0">
		<tr>
		<td align="right" style="width:120px; height: 21px;" valign="middle">商家所在地区</td>
		<td >省级<select name="businessArea_shengji" size="1">
		<option>四川</option>
		</select>
		市级<select name="businessArea_shiji" size="1" >
		<option>德阳市</option>
		<option>眉山市</option>
		<option>遂宁市</option>
		<option>南充市</option>
		<option>攀枝花市</option>
		<option>阿坝藏族羌族自治州</option>
		<option>达州市</option>
		<option>绵阳市</option>
		<option>广元市</option>
		<option>凉山彝族自治州</option>
		<option>内江市</option>
		<option>巴中市</option>
		<option>成都市</option>
		<option>雅安市</option>
		<option>自贡市</option>
		<option>泸州市</option>
		<option>宜宾市</option>
		<option>乐山市</option>
		<option>甘孜藏族自治州</option>
		<option>资阳市</option>
		<option>广安市</option>
		</select><br>县级<select name="businessArea_xianji" size="1">
		<option>四川</option>
		
		
		</td>
		</tr>
		</table>
		<!-- 法人代表表-->
		<table border="1" bordercolor="black" width="100%" frame="void" cellspacing="0">
		<tr >
		<td  align="right" style="width:120px; height: 21px;" valign="middle">法人代表</td>
		<td ><input type="text" name="legalPerson" style="width:600px;"></td>
		</tr>
		</table>
		
		<!-- 法人代表码表 -->
		<table border="1" bordercolor="black" width="100%" frame="void" cellspacing="0">
		<tr >
		<td  align="right" style="width:120px; height: 21px;" valign="middle">法人代表码</td>
		<td ><input type="text" name="legalPresentCode" style="width:600px;"></td>
		</tr>
		</table>
		<!--通讯地址表  -->
		<table border="1" bordercolor="black" width="100%" frame="void" cellspacing="0">
		<tr >
		<td  align="right" style="width:120px; height: 21px;" valign="middle">通讯地址</td>
		<td ><input type="text" name="address" style="width:600px;"></td>
		</tr>
		</table>
		<!-- 业务联系人表 -->
		<table border="1" bordercolor="black" width="100%" frame="void" cellspacing="0">
		<tr >
		<td  align="right" style="width:120px; height: 21px;" valign="middle">业务联系人</td>
		<td ><input type="text" name="cantactPerson" style="width:600px;"></td>
		</tr>
		</table>
		<!--联系电话表  -->
		<table border="1" bordercolor="black" width="100%" frame="void" cellspacing="0">
		<tr >
		<td  align="right" style="width:120px; height: 21px;" valign="middle">联系电话</td>
		<td ><input type="text" name="tel" style="width:600px;"></td>
		</tr>
		</table>
		<!-- 手机表 -->
		<table border="1" bordercolor="black" width="100%" frame="void" cellspacing="0">
		<tr >
		<td  align="right" style="width:120px; height: 21px;" valign="middle">手机</td>
		<td ><input type="text" name="mobilePhone" style="width:600px;"></td>
		</tr>
		</table>
		<!-- 传真表 -->
		<table border="1" bordercolor="black" width="100%" frame="void" cellspacing="0">
		<tr >
		<td  align="right" style="width:120px; height: 21px;" valign="middle">传真</td>
		<td ><input type="text" name="fax" style="width:600px;"></td>
		</tr>
		</table>
		<!-- 企业邮箱表 -->
		<table border="1" bordercolor="black" width="100%" frame="void" cellspacing="0">
		<tr >
		<td  align="right" style="width:120px; height: 21px;" valign="middle">企业邮箱</td>
		<td ><input type="text" name="mailBox" style="width:600px;"></td>
		</tr>
		</table>
		<!--注册时间表   -->
		<!--注册商标表  -->
		<!--备注表  -->
		<input type="submit" value="submit">
	</form>
	
	

	
</body>
</html>