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
<title>标签管理-编辑标签</title>
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
        	scanNum: { digits:true, min:0, required: true },
        	abnormalType: { maxlength:10 },
        },
        messages: {
        	scanNum: { required: "请输入领用数量", min:"最小是1", digits: "只能输入合法的数字" },
        	abnormalType: { maxlength:"长度不能超过10" }
        }
    }).form();
}

function btnOK() {
	if(validate()){
		document.getElementById('form1').submit();
	}
}
function back() {
	var apId = document.getElementById('applyId').value;
	var proId = document.getElementById('productId').value;
	window.location.href = "<%=basePath%>label/apply/label/list?key="+apId+"&productId="+proId;
}
</script>
</head>
<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">认证标签管理>>标签管理>>编辑标签</label>
</div>
<br/>
<form id="form1" action="<%=basePath%>label/apply/label/edit" method="post">	
	<div>
		<input id="labelId" name="labelId" type="hidden" value="${labelId}"/>
		<input id="applyId" name="applyId" type="hidden" value="${applyId}"/>
		<input id="productId" name="productId" type="hidden" value="${productId}"/>
		<table>	
			<tr>
				<td>扫描次数</td>
				<td><input type="text" id="scanNum" name="scanNum" value="${scanNum}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>标签状态</td>
				<td>
					<select id="labelStatus" name="labelStatus" style="width: 175px">
						<option value="有效">有效</option>
						<option value="无效">无效</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>是否异常</td>
				<td>
					<select id="abnormal" name="abnormal" style="width: 175px">
						<option value="否">否</option>
						<option value="是">是</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>异常类型</td>
				<td><input type="text" id="abnormalType" name="abnormalType" value="${abnormalType}"/><span style="color: #FF0000"></span></td>
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