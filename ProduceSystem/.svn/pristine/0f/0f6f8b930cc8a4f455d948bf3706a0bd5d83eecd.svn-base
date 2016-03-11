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
	<form id="form1" method="post" action="" enctype="multipart/form-data"
		onsubmit="return doSubmit()">
		<table class="info" cellpadding="0" cellspacing="0">
			<tr>
				<td class="text">模型名称：</td>
				<td class="value"><input id="txtGrade" name="txtGrade"  type="hidden"
					value="${gradeDetail[0].approveLevelId}" />${gradeDetail[0].remark}</td>
			</tr>
			<tr>
				<td class="text">模型等级：</td>
				<td class="value">${gradeDetail[0].approveLevelName}</td>
			</tr>
			<tr>
				<td class="text">等级描述：</td>
				<td class="value"><textarea id="txtDecription"
						name="txtDecription" rows="8" cols="25" class="width60per"
						style="height: 120px">${gradeDetail[0].approveLevelDescription}</textarea></td>
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
			$.post("<%=basePath%>model/grade/edit", $("#form1").serialize(), function(data){
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