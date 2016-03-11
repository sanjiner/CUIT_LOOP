<%@page import="com.sanjin.dao.ArticleDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" autoFlush="true"%>
<%@page import="java.util.Date,java.text.*"%>
<%@page import="com.sanjin.form.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>博文</title>
<link rel="stylesheet" type="text/css" href="sty.css" />
<link rel="stylesheet" href="lightbox.css" type="text/css"
	media="screen" />

<script src="js/prototype.js" type="text/javascript"></script>
<script src="js/scriptaculous.js?load=effects" type="text/javascript"></script>
<script src="js/lightbox.js" type="text/javascript"></script>
<script type="text/javascript" src="js/java.js"></script>
</head>
<body style="background-image: url(images/528.jpg)">
	<div id="wrap">
	
		<div class="header">
			<div class="logo">
				<a href="index.html"><img src="images/6ek.png" alt="" title=""
					border="0" /></a>
			</div>
			<div id="menu">
				<ul>
					<li><a href="index.jsp">首页</a></li>
					<li><a href="articleType.jsp">博文分类</a></li>
					<li><a href="MyblogDetail.jsp">我的博文</a></li>
					<li><a href="Myaccount.jsp">个人信息</a></li>
					<li><a href="MyPhoto.jsp">我的相册</a></li>
					<li><a href="register.jsp">用户注册</a></li>
					<li><a href="Logout.html">注销登陆</a></li>
				</ul>
			</div>


		</div>
		
	
		<div class="center_content">
			<div class="left_content">
				<div class="left_content">
					<div class="crumb_nav">
						<a href="index.jsp">首页</a> &gt;&gt; 我的博文
					</div>
					<%String id=request.getParameter("id");
						ArticleDao articleDao=new ArticleDao();
						ArticleForm articleForm=new ArticleForm();
						articleForm=articleDao.selectArticleByarticle_typeID(id);
						request.setAttribute("article_title", articleForm.getArticle_title());
						request.setAttribute("article_create", articleForm.getArticle_create());
						request.setAttribute("article_content", articleForm.getArticle_content());
						
					%>
					
					
					
					<div class="title">
					<span class="title_icon"><img src="images/bullet1.gif"
						alt="" title="" />
					</span>
						<c:out value="${article_title}" default="文章标题" />
					</div>
					<div class="feat_prod_box_details">
						<div class="prod_det_box">
							<div class="box_top"></div>
							<div class="box_center">
								<div class="prod_title"><c:out value="作者：${article_create}" default="作者" /></div>
								
								<p class="details">
								<c:out value="${article_content}" default="文章内容" />
								</p>
								
								<div class="clear"></div>
							</div>
							<div class="box_bottom"></div>
						</div>
						<div class="clear"></div>
					</div>
					
					<div class="clear"></div>
				</div>
				
				</div>
				<div class="right_content">
				<%MasterForm masterForm=new MasterForm();
		masterForm=(MasterForm)session.getAttribute("Master");
		request.setAttribute("masterForm", masterForm);%>
				<div class="languages_box">
					<span class="red">欢迎您：<c:out
							value="${masterForm.getMaster_loginname()}" default="游客" /></span>

				</div>
				<%Date date=new Date();
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String time=simpleDateFormat.format(date);
			%>
				<div class="currency">
					<span class="red">当前时间：<%=time %>
					</span>
				</div>
				<div class="cart"></div>





				<div class="right_box"></div>

				<div class="right_box"></div>


			</div>
			
			
		
		<div class="clear"></div>
	</div>
	
	
	<div class="footer">

			<div class="right_footer">
				<a href="#">首页</a> <a href="#">关于我们</a> <a href="#">服务</a> <a
					href="#">隐私政策</a> <a href="#">联系我们</a>

			</div>


		</div>
		
	</div>
</body>
</html>