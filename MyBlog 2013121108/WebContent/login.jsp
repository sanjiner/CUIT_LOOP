<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>博客，分享生活</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<meta name="keywords"
	content="Ribbon Login Form Responsive Templates, Iphone Compatible Templates, Smartphone Compatible Templates, Ipad Compatible Templates, Flat Responsive Templates" />
<link href="css/login.css" rel='stylesheet' type='text/css' />

<link
	href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900'
	rel='stylesheet' type='text/css'>

</head>
<body>

	<h1>
		Welcome <span>请登录...</span>
	</h1>
	<div class="login-box">
		<form action="dologin.html" method="post">
			<input type="text" name="master_loginName" class="text"
				value="Username" onfocus="this.value = '';"
				onblur="if (this.value == '') {this.value = '';}"> <input
				type="password" name="master_password" onfocus="this.value = '';"
				onblur="if (this.value == '') {this.value = '';}">

			<div class="clear"></div>
			<div class="btn">
				<input type="submit" value="LOG IN"> <input type="reset"
					value="RESET">
			</div>
		</form>
		<div class="clear"></div>
		<div class="btn">
			<h4>
				加入我们...<a href="register.jsp">用户注册</a>
			</h4>
		</div>

		<div class="clear"></div>
	</div>
	<div class="copy-right">
		<p>
			Copyright ©2015 CUIT web综合设计 <a href="http://www.cuit.edu.cn">唐鑫</a>
		</p>
	</div>
</body>
</html>