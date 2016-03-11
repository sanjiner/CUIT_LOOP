<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=basePath%>res/css/common/applay.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>申请审核</title>
<script type="text/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						//通过
						$("#Submit")
								.click(
										function() {
											$
													.ajax({
														type : "POST",
														url : "<%=basePath%>org_exam/audit?originId=${apply.originId}",
														dataType : "html",
														data : {
															'IsPass' : "Yes",
															'Person' : $("#textPerson").val(),
															'Advice' : $("#textAdvice").val()
														},
														success : function(data) {
															 var obj = eval('(' + data + ')');
											            	 if(obj.success == "true"){
											            		 alert("审核成功");
											            		 window.location.href = "<%=basePath%>org_exam/list";
											            	 }else
											            		 alert("审核失败");
														},
														cache : false,
														error : function(msg) {
															alert(msg.responseText);
														}
													});
										});

						//不通过
						$("#No")
								.click(
										function() {
											$
													.ajax({
														type : "POST",
														url : "<%=basePath%>org_exam/audit?originId=${apply.originId}",
														dataType : "html",
														data : {
															'IsPass' : "No",
															'Person' : $("#textPerson").val(),
															'Advice' : $("#textAdvice").val()
														},
														success : function(data) {
															 var obj = eval('(' + data + ')');
											            	 if(obj.success == "true"){
											            		 alert("审核成功");
											            		 window.location.href = "<%=basePath%>org_exam/list";
											            	 }else
											            		 alert("审核失败");
														},
														cache : false,
														error : function(msg) {
															alert(msg.responseText);
														}
													});
										});

						//退回
						$("#CallBack")
								.click(
										function() {
											$
													.ajax({
														type : "POST",
														url : "<%=basePath%>org_exam/audit?originId=${apply.originId}",
														dataType : "html",
														data : {
															'IsPass' : "Back",
															'Person' : $("#textPerson").val(),
															'Advice' : $("#textAdvice").val()
														},
														success : function(data) {
															 var obj = eval('(' + data + ')');
											            	 if(obj.success == "true"){
											            		 alert("审核成功");
											            		 window.location.href = "<%=basePath%>org_exam/list";
					} else
						alert("审核失败");
				},
				cache : false,
				error : function(msg) {
					alert(msg.responseText);
				}
			});
		});
	});
</script>
<style type="text/css">
.table td {
	width: 25%;
}

.father {
	padding: 5px;
	margin: 10px;
	border: 1px solid #dcdcdc;
	overflow: hidden;
}

.child {
	
}

a:link {
	color: #000000;
	text-decoration: underline;
}

a:visited {
	color: #000000;
	text-decoration: none;
}
</style>
</head>
<body>
		<div
		style="background: #4c9ee7; width: 100%; height: 30px; margin-bottom: 5px">
		<label
			style="width: auto; height: 30px; text-align: center; color: white; line-height: 30px; margin-left: 5px">农网认证>>认证审核>>审核产品</label>
	</div>
	<form id="form1">
		<div id="div_second" style="display: block;">
			<div
				style="width: 100%; height: 30px; line-height: 30px; background: #0090d7; padding-left: 10px; margin-top: 10dp; margin-right: 10dp">
				<label><font color="#fff" size="+1.5"><b>产品信息</b></font></label>
			</div>
			<table width="90%">
				<tbody>
					<tr>
					<tr>
						<td class="text_right">单位名称:</td>
						<td class="content_left">${apply.applyCompany }</td>
						<td class="text_right">申请人:</td>
						<td class="content_left">${apply.applyPerson }</td>
					</tr>
					<tr>
						<td class="text_right">产品名称:</td>
						<td class="content_left">${apply.originName }</td>
						<td class="text_right">产品品牌:</td>
						<td class="content_left">${apply.productBrand }</td>
					</tr>
					<tr>
						<td class="text_right">生产基地:</td>
						<td class="content_left">${address} </td>
						<td class="text_right">地址:</td>
						<td class="content_left">${apply.personAdress }</td>
					</tr>
					<tr>
						<td class="text_right">产品产值:</td>
						<td class="content_left">${apply.productValue }</td>
						<td class="text_right">产品产量:</td>
						<td class="content_left">${apply.productNum }</td>
					</tr>
					<tr>
						<td class="text_right">手机:</td>
						<td class="content_left">${apply.constract }</td>
						<td class="text_right">申请标签数量:</td>
						<td class="content_left">${apply.labelNum }</td>
					</tr>
					<tr>
						<td class="text_right">产品描述:</td>
						<td class="content_left">${apply.originDescription }</td>
					</tr>
				</tbody>
			</table>
		</div>
	<div id="div_third" style="display: block; margin: 10px">
		<div
			style="width: 100%; height: 30px; line-height: 30px; background: #0090d7; padding-left: 10px; margin-top: 10dp; margin-right: 10dp">
			<label><font color="#fff" size="+1.5"><b>已获得的资质、认证、荣誉、注册商标</b></font></label>
		</div>
		<table class="gvtable" style="margin: 10dp">
			<thead style="text-align: center;">
				<tr>
					<th>证书图片</th>
					<th>证书名称</th>
					<th>颁发机构</th>
					<th>获得时间</th>
				</tr>
			<tbody>
				<c:if test="${bsqList.size() <= 0}">
					<tr>
						<td colspan="4" style="margin: 5px">暂无数据</td>
					</tr>
				</c:if>
				<c:forEach items="${certs}" var="item">
					<tr>
						<td><img height="50px" width="50px" src="<%=path %>${item.pictureUrl }"></td>
						<td>${item.certificateName }</td>
						<td>${item.awardDepart }</td>
						<td>${item.getTime }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	<div id="City">
			<div
			style="width: 100%; height: 30px; line-height: 30px; background: #0090d7; padding-left: 10px; margin-top: 10dp; margin-right: 10dp">
			<label><font color="#fff" size="+1.5"><b>审核意见</b></font></label>
		</div>
		<div class="child">
			<table class="table">
				<tr id="cityPre" class="item">
					<td class="text" style="width: 30%;">审核人签名</td>
					<td class="value" style="width: 20%;"><input
						value="${username }" id="textPerson" width="2000px" type="text"></td>
					<td class="text" style="width: 10%;">审核意见</td>
					<td class="value" style="width: 40%;"><input id="textAdvice"
						width="2000px" type="text"></td>
				</tr>
			</table>
		</div>
	</div>
	</form>
	<center>
		<table class="table">
			<tbody>
				<tr>
					<td><input type="button" class="btn_blue" id="Submit"
						value="通过"></td>
					<td><input type="button" class="btn_blue" id="CallBack"
						value="退回"></td>
					<td><input type="button" class="btn_blue" id="No" value="不通过"></td>
					<td><a type="button" class="btn_blue"
						style="display: inline-block; vertical-align: middle; text-decoration: none; line-height: 30px; color: white;"
						href="<%=basePath%>org_exam/list">返回</a></td>

				</tr>
			</tbody>
		</table>
	</center>
</body>
</html>