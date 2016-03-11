<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}
</style>
<title></title>
<script src="js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script src="js/menu.js" type="text/javascript" charset="utf-8"></script>
<script src="js/global.js" type="text/javascript" charset="utf-8"></script>
<script src="js/modal.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="css/style.css" type="text/css"
	media="screen" charset="utf-8" />
</head>
<body style="background-image: url(images/login.jpeg)">
	<div>
		<div id="wrapper_login">
			<div id="menu">
				<div id="left"></div>
				<div id="right"></div>
				<h2>用户登录</h2>
				<div class="clear"></div>
			</div>
			<div id="desc">
				<div class="body">
					<div class="col w10 last bottomlast">
						<form action="index.html">
							<p>
								<label for="username">用户名:</label> <input type="text"
									name="username" id="username" value="" size="40" class="text" />
								<br />
							</p>
							<p>
								<label for="password">密码:</label> <input type="password"
									name="password" id="password" value="" size="40" class="text" />
								<br />
							</p>
							<p class="last">
								<input type="submit" value="登陆" id="submit_demo3" /> <input
									type="reset" value="重置" id="submit_demo4" /> <a href="##"
									value="register" id="submit_demo4">注册</a> <br />
							</p>
							<div class="clear"></div>
						</form>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div id="body_footer">
					<div id="bottom_left">
						<div id="bottom_right"></div>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>