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
        	applyPerson: {required: true, maxlength:50},
        	labelType: {required: true, maxlength:32},
        	applyNum: { digits:true, min:1, required: true },
        	pici: {required: true},
        	format: {required: true}
        },
        messages: {
        	applyPerson: {required: "请输入申请商家", maxlength:"长度不能超过50"},
        	labelType: {required: "请输入标签类型", maxlength:"长度不能超过32"},
        	applyNum: { required: "请输入申请数量", min:"最小是1", digits: "只能输入合法的数字" },
        	pici: {required: "请输入申请批次"},
        	format: {required: "请输入标签规格"}
        }
    }).form();
}

function btnOK() {
	if(validate()){
		document.getElementById('form1').submit();
	}
}
function back() {
	var pid = document.getElementById('applyBh').value;
	window.location.href = "<%=basePath%>label/manage/application/list?key="+pid;
}
</script>
</head>
<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">认证标签管理>>标签管理>>编辑申请</label>
</div>
<br/>
<form id="form1" action="<%=basePath%>label/manage/application/edit" method="post">	
	<div>
		<input id="labelApplicationId" name="labelApplicationId" type="hidden" value="${labelApplicationId}"/>
		<input id="applyBh" name="applyBh" type="hidden" value="${applyBh}"/>
		<table>	
			<tr>
				<td>申请商家</td>
				<td><input type="text" id="applyPerson" name="applyPerson" value="${applyPerson}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>标签类型</td>
				<td><input type="text" id="labelType" name="labelType" value="${applyType}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>申请数量</td>
				<td><input type="text" id="applyNum" name="applyNum" value="${applyNum}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>申请批次</td>
				<td><input type="text" id="pici" name="pici" value="${pici}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>标签规格</td>
				<td><input type="text" id="format" name="format" value="${format}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>申请时间</td>
				<td><input type="text" id="applyTime" name="applyTime" value="${applyTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>制作状态</td>
				<td>
					<select id="makeStatus" name="makeStatus" style="width: 175px">
						<option value="1000190">${makeStatus}</option>
						<option value="1000191">${makeStatus2}</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>申请理由</td>
				<td><textarea id="applyReason" name="applyReason" rows="5" cols="50" >${applyReason}</textarea></td>
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