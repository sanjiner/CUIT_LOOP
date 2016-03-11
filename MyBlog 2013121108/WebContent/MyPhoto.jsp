<%@page import="com.sanjin.dao.PhotoTypeDao"%>
<%@page import="com.sanjin.dao.ArticleTypeDao"%>
<%@page import="com.sanjin.form.MasterForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date,java.text.*"%>
<%@page import="com.sanjin.form.*"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>相册管理</title>
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
					<li ><a href="articleType.jsp">博文分类</a></li>
					<li><a href="MyblogDetail.jsp">我的博文</a></li>
					<li><a href="Myaccount.jsp">个人信息</a></li>
					<li class="selected"><a href="MyPhoto.jsp" >我的相册</a></li>
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
						alt="" title="" /> </span>相册分类查看
						
						
				</div>
				
				<!-- 循环开始 -->
				<div class="feat_prod_box">
					<div class="prod_img"></div>
					

<!-- 循环 -->
<% PhotoTypeDao photoTypeDao=new PhotoTypeDao();
	PhotoTypeForm photoTypeForm=new PhotoTypeForm();
	List<PhotoTypeForm> list=photoTypeDao.selectAllPhotoType();
	for(int i=0;i<list.size();i++)
	{
		photoTypeForm=list.get(i);
		request.setAttribute("PhotoType_name", photoTypeForm.getPhotoType_name());
		request.setAttribute("PhotoType_info", photoTypeForm.getPhotoType_info());
		request.setAttribute("PhotoType_id", photoTypeForm.getPhotoType_id());
		
		
	
%>
					
					<div class="prod_det_box">
						<div class="box_top"></div>
						<div class="box_center">
							<div class="prod_title"><c:out value="${ PhotoType_name}"  default="相册类别" /></div>
							<p class="details"><c:out value="${PhotoType_info}"  default="相册类别描述" /></p>
							<a href="DeletePhotoType?id=<%=request.getAttribute("PhotoType_id") %>" class="more">- 删除 -</a>
							<a href="ShowPhotoSevlet?id=<%=request.getAttribute("PhotoType_id") %>&PhotoType_name=<%=request.getAttribute("PhotoType_name") %>" class="more">- 查看相册 -</a>
							
							<div class="clear"></div>
						</div>

						<div class="box_bottom"></div>
					</div>
					
					<!-- 循环结束 -->
			<%} %>		
					
					
					
				</div>
			</div>
			<%MasterForm masterForm=new MasterForm();
		masterForm=(MasterForm)session.getAttribute("Master");
		if(masterForm!=null)
		{
		request.setAttribute("masterForm", masterForm);
		System.out.println("wenzhangliebie"+masterForm.getMaster_loginname());
		}
		else if(masterForm==null)
		{
			
		}
		%>
			<div class="right_content">
				<!--right_center  -->
				<div class="languages_box">
					<span class="red">欢迎您：<c:out
							value="${masterForm.getMaster_loginname()}" default="游客" />
					</span>

				</div>
				<div class="cart">
					<%Date date=new Date();
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String time=simpleDateFormat.format(date);
			%>
					<span class="red">现在时间：<%=time %></span>
				</div>
				<div class="right_box"></div>
				<div class="right_box"></div>
				<div class="clear"></div>
				<div class="languages_box">
					<span class="red">
					<c:out value="${check}"></c:out>
					</span>
				</div>
				<div class="clear"></div>
				<div class="title">
					<span class="title_icon"><img src="images/bullet1.gif"
						alt="" title="" /> </span>添加相册
				</div>
				<div class="clear"></div>
				
				<div class="feat_prod_box_details">
					<div class="contact_form2">
						<form name="AddPhotoType" action="AddPhotoType" method="post">
							<div>
								<label class="contact" ><strong>相册类别：</strong></label>
								<input type="text" class="contact_input" name="PhotoType_name" placeholder="请输入相册名"/>
							</div>
							<div>
								<label class="contact" ><strong>类别描述：</strong></label>
								<input type="text" class="contact_input" name="PhotoType_info" placeholder="请输入类别描述"/>
							</div>
							<div class="form_row">
								<input type="submit" class="register" value="提交" />
								<input type="reset" class="register" value="重置" />
							</div>
						</form>
					</div>
				</div>
				<ul class="list">
					<li></li>
				</ul>
			</div>
			<!--center end  -->
			<div class="clear"></div>

		</div>

		<div class="footer">
			<!--footer  -->
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>