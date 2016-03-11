<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>模型管理-气象要素-列表</title>
	<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/js/validate.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/js/public.js"></script>
</head>
<body>
	<form id="form1" method="post" action="" enctype="multipart/form-data" onsubmit="return doSubmit()">
		<table class="info" cellpadding="0" cellspacing="0">
			<tr>
				<td class="text">商品名称：</td>
				<td class="value"><input id="txtName" name="txtName"
					class="width60per" value="${ productDetail[0].productName}" />
					<input id="txtProduct" name="txtProduct" type="hidden" value="${ productDetail[0].productNumber}" /></td>
			</tr>
			<tr>
				<td class="text">商品码：</td>
				<td class="value"><input id="txtCode" name="txtCode"
					class="width60per" value="${ productDetail[0].productCode}" /></td>
			</tr>
		<tr>
				<td class="text"><span class="required">*</span>产品类别：</td>
						<td class="content_left"><select id="selType"
							name="selType" style="width: 120px">
								<c:forEach items="${dicType}" var="dicItem">
									<option value="${dicItem.cuitMoonDictionaryCode}">${ dicItem.cuitMoonDictionaryName}</option>									
								</c:forEach>
						</select>
						</td>
			</tr>
			<tr>
				<td class="text">产品描述：</td>
				<td class="value">
					<textarea id="txtDecription" name="txtDecription" rows="8" cols="25">${ productDetail[0].productDescription}</textarea>
				</td>
			</tr>
			<tr>
				<td class="text">&nbsp;</td>
				<td class="value"><input type="submit" class="btn_blue"
					value="提交" />&nbsp; &nbsp;<a id="btnBack"
					href="javascript:history.go(-1);" class="btn_blue">返回</a>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
	function validate(){
	    return $("#form1").validate({
	        ignore: "",
	        rules: {
	            txtName: { required: true, maxlength: 20 },
	            txtCode: { required: true, Code: 20 },
	            txtDecription: { maxlength: 200 }
	        },
	        messages: {
	            txtName: { required: "请输入产品名称", maxlength: "产品名称不能超过20个字符" },
	            txtCode: { required: "请输入商品码", Code: "商品码应为6-20位的字母或数字" },
	            txtDecription: { maxlength:"产品描述不能超过200个字符"}
	        }
	    }).form();
	}
	function doSubmit(){
		if(validate()){
			$.post("<%=basePath%>model/product/edit", $("#form1").serialize(), function(data){
				if(data.state=="fail"){
					alert(data.result);
				}
				else{
					alert(data.result);
					location.href="<%=basePath%>model/product/list";
				}
			});
		}
		return false;
	}
	</script>
</body>
</html>