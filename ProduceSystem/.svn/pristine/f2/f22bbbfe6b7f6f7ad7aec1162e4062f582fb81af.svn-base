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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目归档编辑</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js" type="text/javascript" language="javascript"></script>
<script src="<%=basePath%>res/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=basePath%>res/js/validate.js" type="text/javascript"></script>
<script type="text/javascript">
function validate() {
    return $("#form1").validate({
        rules: {
        	proName: { maxlength: 50, required: true },
        	proNo: { maxlength: 50, required: true },
        	Person: { maxlength: 10, required: true },
        	protime: { maxlength: 10, required: true }
        },
        messages: {
        	proName: { required: "请输入项目名称", maxlength: " 项目名称不能超过50字符" },
        	proNo: { required: "请输入档案编号", maxlength: " 档案编号不能超过50字符" },
        	Person: { required: "请输入归档人", maxlength: " 归档人不能超过10字符" },
        	protime: { required: "请输入归档时间", maxlength: " 归档时间不能超过10字符" }
        }
    }).form();
}

function btnOK() {
	if(validate()){
		document.getElementById('form1').submit();
	}
}
function back() {
	window.location.href = "<%=basePath%>file_recode/list";
}
</script>
</head>
<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">应用中心>>项目归档>>编辑归档</label>
</div>
<br/>
<form id="form1" action="<%=basePath%>file_recode/edit" method="post">	
	<div>
		<input id="primaryKey" name="primaryKey" type="hidden" value="${primaryKey}"/>
		<table>	
			<tr>
				<td>项目名称</td>
				<td><input type="text" id="proName" name = "proName" value="${proName}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>档案编号</td>
				<td><input type="text" id="proNo" name = "proNo" value="${proNo}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>归档人</td>
				<td><input type="text" id="Person" name = "Person" value="${person}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>归档时间</td>
				<td><input type="text" id="protime" name = "protime" value="${protime}" onfocus="WdatePicker()"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>备注</td>
				<td><textarea id="remark" name="remark" rows="5" cols="50" >${remark}</textarea></td>
			</tr>	
		</table>
	</div>
	<br/>
	<div>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="提交" onclick="btnOK()" class="btn_blue" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="返回" onclick="back()" class="btn_blue" />
	</div>
</form>
</body>
</html>