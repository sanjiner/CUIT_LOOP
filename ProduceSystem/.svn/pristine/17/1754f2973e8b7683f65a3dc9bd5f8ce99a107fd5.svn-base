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
<title>标签申请-编辑申请</title>
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
        	receivePerson: {required: true, maxlength:50},
        	receiveNum: { digits:true, min:1, required: true }
        },
        messages: {
        	receivePerson: {required: "请输入领用人", maxlength:"长度不能超过50"},
        	receiveNum: { required: "请输入领用数量", min:"最小是1", digits: "只能输入合法的数字" }
        }
    }).form();
}

function btnOK() {
	if(validate()){
		document.getElementById('form1').submit();
	}
}
function back() {
	var pid = document.getElementById('originId').value;
	window.location.href = "<%=basePath%>label/manage/receive/list?key="+pid;
}
</script>
</head>
<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">认证标签管理>>标签管理>>编辑领用</label>
</div>
<br/>
<form id="form1" action="<%=basePath%>label/manage/receive/edit" method="post">	
	<div>
		<input id="receiveId" name="receiveId" type="hidden" value="${receiveId}"/>
		<input id="originId" name="originId" type="hidden" value="${originId}"/>
		<table>	
			<tr>
				<td>领用人</td>
				<td><input type="text" id="receivePerson" name="receivePerson" value="${receivePerson}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>领用数量</td>
				<td><input type="text" id="receiveNum" name="receiveNum" value="${receiveNum}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>领用时间</td>
				<td><input type="text" id="receiveTime" name="receiveTime" value="${receiveTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/><span style="color: #FF0000">*</span></td>
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