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
<meta http-equiv="X-UA-Compatible"
content="IE=9; IE=8; IE=7; IE=EDGE">
<title>登录</title>

<style type="text/css">
body {
	text-align:center;
	height: 100%;
	width:100%;
	background-image: url(<%=basePath%>res/images/sys/login/login_bg.png);
	background-repeat: no-repeat;
	background-color: #83C9E2;
	background-attachment: fixed;
	background-position: center center;
	background-attachment: fixed;
	background-size: cover;
}

.container {
}

form {
	margin: auto; 
	margin-top:10%;
	color: #045AA7;
	height:350px;
	width:600px;
	background-image: url(<%=basePath%>res/images/sys/login/login_panel.png);
	background-repeat: no-repeat;
}
form table{
	position: relative;
	left: 40px;
	top: 130px;
}
form input {
	width: 200px;
	height: 25px;
	font-size: large;;
	margin:0px;
	padding:0px;
}

form td {
	height: 40px;
}

.loginBtn {
	width: 65px;
	height: 30px;
	color: white;
	font-weight: bold;
	background-repeat: no-repeat;
	background-image: url(<%=basePath%>res/images/sys/login/login_btn.png);
}

form label{
	font-size: 20px; 
	font-weight: bold
}
#thisBody{
	position: relative;
	margin:0 auto;
	z-index: 100;
	left: 200px;
  	top: 200px;
}
#thisBody .spinner{
	position: relative;
	
}

.btn_blue{
	position:relative; 
	height:28px;
	cursor:pointer;
	display:inline-block;
	width:60px;
	vertical-align:middle;
	font-size:14px;
	font-weight:bold;
	line-height:0px;
	text-align:center;
	text-decoration:none;
	border-radius:5px;
	color:#F3F7FC;
	background-color:#009eea;
	border:1px solid #ccc;
	box-sizing:border-box;
	-moz-box-sizing:border-box; /* Firefox */
	-webkit-box-sizing:border-box; /* Safari */
}
.btn_blue:hover{
	background-color:#20b7ff;
	background:-webkit-linear-gradient(top, #009eea, #20b7ff);
	background:-moz-linear-gradient(top, #009eea, #20b7ff);
	background:linear-gradient(top, #009eea, #20b7ff);
}
</style>

</head>

<body>
	<div class="container">

		<form id="loginform" action="" method="post" onsubmit="return login()">
			<div id="thisBody"></div>
			<table>
				<tr>
					<td style="text-align: right;"><label>用户名:</label></td>
					<td colspan="2"><input id="username" type="text"
						class="required" name="username"></td>
					<td id="usernametip"></td>
				</tr>
				<tr>
					<td style="text-align: right"><label>密码:</label></td>
					<td colspan="2"><input id="password" type="password"
						name="password"></td>
					<td id="passwordtip"></td>
				</tr>
				<tr>
					<td style="text-align: right"><label>验证码:</label></td>
					<td style="padding:0px;text-align: left"><input id="validateCode"
						type="text" name="validateCode"
						style="width: 100px;"></td>
					<td><img id="validateCodeImage"
						src="<%=basePath%>/sys/login/validatecode"></td>
					<td><input id="loginBtn" class="btn_blue" type="submit"
						value="登&nbsp;录"></td>
				</tr>
			</table>
		</form>

	</div>
	<script type="text/javascript"
		src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
	<script type="application/javascript" src="<%=basePath%>res/js/spin.js"></script>
	<script type="text/javascript">
	if (top.location != self.location){       
        top.location = "<%=basePath%>login";       
    }   
	
	$(document).ready(function(){
		  $("#validateCodeImage").click(function(){
		    $(this).attr("src", "<%=basePath%>/sys/login/validatecode?"+ new Date().getTime());
		  });
	});
	
	function validate(){
		var username = $.trim($("#username").val());
		var password = $.trim($("#password").val());
		var validateCode = $.trim($("#validateCode").val());
		if(username==null || username==""){
			alert("用户名不能为空");
			return false;
		}
		if(password==null || password==""){
			alert("密码不能为空");
			return false;
		}
		if(validateCode==null || validateCode==""){
			alert("验证码不能为空");
			return false;
		}
		return true;
	}
	
	 //opts 可从网站在线制作
    var opts = {            
        lines: 10, // 花瓣数目
        length: 20, // 花瓣长度
        width: 10, // 花瓣宽度
        radius: 30, // 花瓣距中心半径
        corners: 1, // 花瓣圆滑度 (0-1)
        rotate: 0, // 花瓣旋转角度
        direction: 1, // 花瓣旋转方向 1: 顺时针, -1: 逆时针
        color: '#000', // 花瓣颜色
        speed: 1, // 花瓣旋转速度
        trail: 60, // 花瓣旋转时的拖影(百分比)
        shadow: false, // 花瓣是否显示阴影
        hwaccel: false, //spinner 是否启用硬件加速及高速旋转            
        className: 'spinner', // spinner css 样式名称
        zIndex: 2e9, // spinner的z轴 (默认是2000000000)
        top: 'auto', // spinner 相对父容器Top定位 单位 px
        left: 'auto'// spinner 相对父容器Left定位 单位 px
    };

    var spinner = new Spinner(opts);
	
	function login(){
		if(validate()){
			var target = document.getElementById('thisBody');
            spinner.spin(target);  
			$.post("<%=basePath%>login", $("#loginform").serialize(), function(data){
				if(data.state=="false"){
					spinner.spin();
					alert(data.result);
				}else{
					location.href="<%=basePath%>sys/mainPage";
				}
			});
		}
		return false;

	}
	</script>
</body>
</html>