<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>模型管理-气象模型-列表</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
</head>
<body>
<div
		style="background: #4c9ee7; width: 100%; height: 30px; margin-bottom: 5px">
		<label
			style="width: auto; height: 30px; text-align: center; color: white; line-height: 30px; margin-left: 5px">模型等级>>添加</label>
	</div>
	<form id="form1" method="post" action="" enctype="multipart/form-data"
		onsubmit="return doSubmit()">
		<table class="info" cellpadding="0" cellspacing="0">
			<tr>
				<td class="text"><span class="required">*</span>模型名称：</td>
				<td class="value">
					<input id="txtModel" name="txtModel" type="hidden" value="${modeldetail[0].productApproModelId}" />${modeldetail[0].modelName}
				</td>
			</tr>
			<tr>
				<td class="text"><span class="required">*</span>模型等级：</td>
				<td class="value"><select id="selLevel" name="selLevel"
					class="width60per">
						<c:forEach items="${dictionaryList}" var="diclist">
							<option value="${diclist.cuitMoonDictionaryCode }">${diclist.cuitMoonDictionaryName }</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="text">等级描述：</td>
				<td class="value"><textarea id="txtDecription"
						name="txtDecription" rows="8" cols="25" class="width60per"
						style="height: 120px"></textarea></td>
			</tr>
			<tr>
				<td class="text">&nbsp;</td>
				<td class="value"><input type="submit" class="btn_blue"
					value="提交" />&nbsp; &nbsp;<a id="btnBack"
					href="javascript:history.go(-1);" class="btn_blue">返回</a></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
	function doSubmit(){
			$.post("<%=basePath%>model/grade/add", $("#form1").serialize(), function(data){
				if(data.state=="fail"){
					alert(data.result);
				}
				else{
					alert(data.result);
					location.href="<%=basePath%> model/grade/list?productApproModelId=<%=request.getParameter("productApproModelId")%>";
						}
					});
			return false;
		}
	</script>
</body>
</html>