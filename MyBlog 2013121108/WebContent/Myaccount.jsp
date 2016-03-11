<%@page import="com.sanjin.form.MasterForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>个人信息</title>
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
					<li class="selected"><a href="Myaccount.jsp">个人信息</a></li>
					<li><a href="MyPhoto.jsp">我的相册</a></li>
					<li><a href="register.jsp">用户注册</a></li>
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
						alt="" title="" /> </span>用户基本信息
				</div>
				<div class="feat_prod_box_details">
					<p class="details">欢迎您， 本网站的会员，您可对自己基本信息进行修改。</p>


					<div class="contact_form">
						<div class="form_subtitle">账户信息</div>
						<form name="changeAccount" action="Myaccount" method="post">
							<% MasterForm masterForm=new MasterForm();
            String master_loginname=null;
            String master_oicq=null;
            String master_sex=null;
            String master_password=null;
            String master_realname=null;
            String master_name=null;
            String master_blogID=null;
			masterForm=(MasterForm)session.getAttribute("Master");
			
			if(masterForm!=null)
			{
				/* request.setAttribute("master_loginname", masterForm.getMaster_loginname());
				request.setAttribute("Master_oicq",masterForm.getMaster_oicq()); 
				request.setAttribute("Master_name",masterForm.getMaster_name());
				request.setAttribute("Master_password",masterForm.getMaster_password() );
				request.setAttribute("Master_realname",masterForm.getMaster_realname() );
				request.setAttribute("Master_sex", masterForm.getMaster_sex()); */
				request.setAttribute("userdetail", masterForm);
				
			}
			else if(masterForm==null)
			{
				response.sendRedirect("login.jsp");
			}
			%>

							<div class="form_row">

								<label class="contact"><strong>用户名:</strong></label> <input
									type="text" class="contact_input" name="master_loginName"
									value="${userdetail.getMaster_loginname()}" disabled />
							</div>
							<div class="form_row">
								<label class="contact"><strong>密码:</strong></label> <input
									type="password" class="contact_input" name="master_password"
									value="${userdetail.getMaster_password()}" />
							</div>
							<div class="form_row">
								<label class="contact"><strong>昵称:</strong></label> <input
									type="text" class="contact_input" name="master_name"
									value="${userdetail.getMaster_name()}" />
							</div>

							<div class="form_row">
								<label class="contact"><strong>真实姓名:</strong></label> <input
									type="text" class="contact_input" name="master_realname"
									value="${userdetail.getMaster_realname()}" />
							</div>

							<div class="form_row">
								<label class="contact"><strong>性别:</strong></label> <input
									type="text" class="contact_input" name="master_sex"
									value="${userdetail.getMaster_sex()}" />
							</div>

							<div class="form_row">
								<label class="contact"><strong>QQ号:</strong></label> <input
									type="text" class="contact_input" name="master_oicq"
									value="${userdetail.getMaster_oicq()}" />
							</div>
							<div class="form_row">
								<input type="submit" class="register" value="确认修改" />
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
		<div class="right_content">
			<!--right_center  -->
			<div class="languages_box"></div>
			<div class="cart"></div>
			<div class="right_box">
				<%
			String isadd="";
			System.out.println(request.getAttribute("judge"));
			if(request.getAttribute("judge")!=null){
			if(request.getAttribute("judge").equals("no"))
			{
				isadd="修改失败！";
			}
			else if(request.getAttribute("judge").equals("yes"))
			{
				isadd="修改成功！";
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
</body>
</html>