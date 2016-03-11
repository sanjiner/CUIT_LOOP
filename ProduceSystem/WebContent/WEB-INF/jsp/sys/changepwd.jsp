<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<script type="application/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="application/javascript"
	src="<%=basePath%>res/js/jQuery.md5.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery.validate.min.js"></script>
<title>修改密码</title>



<style type="text/css">
body {
	font-family: "黑体";
}

.container {
	background-color: #EDF1FC;
	width: 100%;
	height: 300px;
	margin: 0 auto;
	vertical-align: middle;
}

td {
	height: 40px;
	vertical-align: middle;
}

input {
	width: 200px;
	height: 20px;
}
.error{
	color:red;
}
</style>

</head>

<body>
	<div class="container">
		<form action="" method="post" onsubmit="return false">
			<div style="padding-top: 30px;">
				<table style="margin: 0 auto;">
					<tr>
						<td style="text-align: right; padding-right: 20px;"><font
							size="+1"><strong>原密码</strong></font></td>
						<td><input id="oldPassword" type="password" name="oldPassword"></td>
						<td class="error" id="passwordError" hidden="hidden">密码不正确</td>

					</tr>
					<tr>
						<td style="text-align: right; padding-right: 20px;"><font
							size="+1"><strong>新密码</strong></font></td>
						<td><input id="newPassword" type="password" name="newPassword"></td>

					</tr>
					<tr>
						<td style="text-align: right; padding-right: 20px;"><font
							size="+1"><strong>确认新密码</strong></font></td>
						<td><input id="reNewPassword" type="password" name="reNewPassword"></td>
					</tr>

					<tr>
						<td colspan="2"><input type="submit" class="btn_blue" style="width:90px; float:right;"
							value="确认"></td>
					</tr>
				</table>

			</div>
		</form>

	</div>
	<script type="application/javascript">
		$(document).ready(function() {
			$("form").validate({
				rules : {	
					oldPassword : {
						required : true
					},
					newPassword : {
						required : true,
						minlength: 5
					},
					reNewPassword : {
						required : true,
						equalTo: "#newPassword"
					}
				},
				messages : {
					oldPassword : {
						required : "请输入原密码"
					},
					newPassword : {
						required : "请输入新密码",
						minlength: "至少5个字符"
					},
					reNewPassword : {
						required : "请确认新密码",
						equalTo: "两次输入密码不相同"
					}
				},
				submitHandler: function(form) {  //通过之后回调
				     var param = $("form").serialize();
				     $.ajax({
						url : "<%=basePath%>sys/changePassword",
						type : "post",
						dataType : "json",
						data: param,
						success : function(data) {
							
							if(data.msg=='success') {
								alert("密码修改成功！");
							} else {
								$("#passwordError").removeAttr("hidden");
							}
						}
				     });
				 }
			});
		});
	
		
	</script>

</body>
</html>
