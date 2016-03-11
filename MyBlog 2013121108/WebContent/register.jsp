<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date,java.text.*"%>
<%@page import="com.sanjin.form.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>新用户注册</title>
<link rel="stylesheet" type="text/css" href="sty.css" />
</head>
<body style="background-image: url(images/528.jpg)">
	<div id="wrap">
		<!--header  -->
		<div class="header">
			<div class="logo">
				<a href="index.jsp"><img src="images/6ek.png" alt="" title=""
					border="0" /></a>
			</div>
			<div id="menu">
				<ul>
					<li><a href="index.jsp">首页</a></li>

					<li><a href="articleType.jsp">博文分类</a></li>
					<li><a href="MyblogDetail.jsp">我的博文</a></li>
					<li><a href="Myaccount.jsp">个人信息</a></li>
					<li><a href="MyPhoto.jsp">我的相册</a></li>
					<li class="selected"><a href="register.jsp">用户注册</a></li>
					<li><a href="Logout.html">注销登陆</a></li>
				</ul>
			</div>


		</div>


		<div class="center_content">
			<!-- center began -->
			<div class="left_content">
				<!--left_center   -->
				<div class="title">
					<span class="title_icon"><img src="images/bullet1.gif"
						alt="" title="" /> </span>用户注册
				</div>
				<div class="feat_prod_box_details">
					<p class="details">
						欢迎您注册为本网站的会员，请在下面表单填写注册信息，要求每项必填，信息务必真实，方便我们联系您，谢谢！</p>
					<div class="contact_form">
						<div class="form_subtitle">创建新账户</div>
						<form name="register" action="register?id=1" method="post" id="formmer" onsubmit="return checkisnull();">
							<div class="form_row">
								<label class="contact"><strong>用户名:</strong></label> 
								<input
									type="text" class="contact_input" name="master_loginName" id="master_loginName"  placeholder="请输入用户名"/>
									<span id="checkmaster_loginName"></span>
							</div>


							<div class="form_row">
								<label class="contact"><strong>密码:</strong></label> <input
									type="password" class="contact_input" name="master_password" id="master_password" placeholder="请输入密码"/>
							</div>

							<div class="form_row">
								<label class="contact"><strong>密码确认:</strong></label> <input
									type="password" class="contact_input" name="master_password2" id="master_password2" onkeyup="validate()" placeholder="确认密码"/>
									<span id="tishi"></span>
							</div>

							<div class="form_row">
								<label class="contact"><strong>昵称:</strong></label> <input
									type="text" class="contact_input" name="master_name" placeholder="请输入昵称"/>
							</div>

							<div class="form_row">
								<label class="contact"><strong>真实姓名:</strong></label> <input
									type="text" class="contact_input" name="master_realname" placeholder="请输入真实姓名"/>
							</div>

							

							<div class="form_row">
								<label class="contact"><strong>QQ号:</strong></label> <input
									type="text" class="contact_input" name="master_oicq" id="qq" onkeyup="checkqq()" placeholder="请输入QQ号"/>
									<span id="show"></span>
							</div>

							<div class="form_row">
								 <div class="clear"></div>
								<label class="contact"><strong>男:</strong><input type="radio"  name="master_sex" value="男" checked/></label>
								<label class="contact"><strong>女:</strong><input type="radio"  name="master_sex" value="女"/></label>
							</div>

							<div class="form_row">
								<div class="terms">
									<input type="checkbox" name="terms" /> 我同意 <a href="#">相关协议和政策</a>
								</div>
							</div>


							<div class="form_row">
								<input type="submit" class="register" value="注册" id="submit"  />
							</div>
							
							

							
							
						</form>
					</div>
				</div>

			</div>
		</div>
		<% MasterForm masterForm=new MasterForm();
		
			masterForm=(MasterForm)session.getAttribute("user");
			if(masterForm!=null)
			{
			String Master_loginname=masterForm.getMaster_loginname();
			System.out.println(masterForm.getMaster_loginname());
			request.setAttribute("loginname",Master_loginname);
			}
			else{
				
			}
			%>
		<div class="right_content">
			<!--right_center  -->
			<div class="languages_box">
				<span class="red">欢迎您：<c:out value="${loginname}"
						default="游客" />
				</span>

			</div>
			<div class="cart">
				<%Date date=new Date();
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String time=simpleDateFormat.format(date);
			%>
				<span class="red">现在时间：<%=time %></span>
			</div>

			<div class="right_box">
				<%
			String isadd="";
			System.out.println(request.getAttribute("judge"));
			if(request.getAttribute("judge")!=null){
			if(request.getAttribute("judge").equals("no"))
			{
				isadd="注册失败！";
			}
			else if(request.getAttribute("judge").equals("yes"))
			{
				isadd="注册成功！";
			}
			
			}
			else
			{
				
			}%>
				<span class="red"><%=isadd %></span>
			</div>
			<div class="right_box"></div>

		</div>
		<!--center end  -->
		<div class="clear"></div>



		<div class="footer">
			<!--footer  -->
			<jsp:include page="footer.jsp" />
		</div>
	</div>
	
	 <script>
          function validate() {
              var pw1 = document.getElementById("master_password").value;
              var pw2 = document.getElementById("master_password2").value;
              if(pw1 == pw2) {
                  document.getElementById("tishi").innerHTML="<font color='green'>(两次密码相同)</font>";
                  document.getElementById("submit").disabled = false;
              }
              else {
                  document.getElementById("tishi").innerHTML="<font color='red'>(两次密码不相同)</font>";
                document.getElementById("submit").disabled = true;
              }
          }
      </script>
	<script type="text/javascript">
		function checkqq()
		{
			
			var reg = new RegExp("^[0-9]*$");
		       var obj = document.getElementById("qq");
		    if(!reg.test(obj.value)){
		    	document.getElementById("show").innerHTML="<font color='red'>(请输入纯数字)</font>";
                document.getElementById("submit").disabled = true;
		    }
		    else if(!/^[0-9]*$/.test(obj.value)){
			   document.getElementById("show").innerHTML="<font color='red'>(请输入纯数字)</font>";
               document.getElementById("submit").disabled = true;
		    } 
		   else 
			   {
			   document.getElementById("show").innerHTML="<font color='green'>(合法)</font>";
               document.getElementById("submit").disabled = false;
			   }

		}
	</script>
	<script type="text/javascript">
	function checkmaster_loginName()
	{
		var  obj = document.getElementById("master_loginName");
		var obj2="请输入用户名";
		if(obj!=obj2)
			{
			document.getElementById("checkmaster_loginName").innerHTML="<font color='red'>(合法)</font>";
            document.getElementById("submit").disabled = false;
			}
		else if(obj==obj2)
			{
			alert("用户名不能为空！");
			document.getElementById("checkmaster_loginName").innerHTML="<font color='red'>(不能为空)</font>";
            document.getElementById("submit").disabled = true;
			
			}
	}
	</script>
	<script>
	function checkisnull()
	{
		if(document.getElementByName("master_loginName")=="请输入用户名")
			{
			 alert("不能为空");
			   return false;
			}
		else
			{
			 
			return true;
			}
	}
	</script>
	
</body>
</html>