<%@page import="com.sanjin.dao.ArticleDao"%>
<%@page import="com.sanjin.form.ArticleForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sanjin.dao.FriendDao"%>
<%@page import="com.sanjin.form.FriendForm"%>
<%@page import="java.util.List"%>
<%@page import="com.sanjin.form.MasterForm"%>
<%@page import="java.util.Date,java.text.*"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>博客首页</title>
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
					<li class="selected"><a href="index.jsp">首页</a></li>
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
			<!-- center began -->
			<div class="left_content">
				<!--left_center   -->
				<div class="title">
					<span class="title_icon"><img src="images/bullet1.gif"
						alt="" title="" /> </span>精彩博文推荐
				</div>
				<!-- 循环开始 -->
				<div class="feat_prod_box">
					<div class="prod_img"></div>
					
					<%
					
					ArticleDao articleDao=new ArticleDao();
					ArticleForm articleForm=new ArticleForm();
					List<ArticleForm> Articlelist=articleDao.selectArticleForms();
					System.out.println("文章大小"+Articlelist.size());
					for(int i=0;i<Articlelist.size();i++)
					{
						
						articleForm=Articlelist.get(i);
						request.setAttribute("article_title", articleForm.getArticle_title());
						request.setAttribute("article_info", articleForm.getArticle_info());
						request.setAttribute("article_typeID", articleForm.getArticle_typeID());
						System.out.println(request.getAttribute("article_title")+"得到的文章的标题");
					%>
					
					<div class="prod_det_box">
						<div class="box_top"></div>
						<div class="box_center">
							<div class="prod_title"><c:out value="${article_title}"  default="博文题目" /></div>
							<p class="details"><c:out value="${article_info}" default="博文概要" /></p>
							<a href="readmore.jsp?id=<%=request.getAttribute("article_typeID") %>" class="more">- 详细阅读 -</a>
							<div class="clear"></div>
						</div>

						<div class="box_bottom"></div>
					</div>
					
					<%} %>
					
					<!-- 循环结束 -->
				</div>
			</div>
			<% MasterForm masterForm=new MasterForm();
			masterForm=(MasterForm)session.getAttribute("user");
			if(masterForm!=null)
			{
			String Master_loginname=masterForm.getMaster_loginname();
			System.out.println(masterForm.getMaster_loginname());
			request.setAttribute("loginname",Master_loginname);
			request.setAttribute("master_oicq", masterForm.getMaster_oicq());
			session.setAttribute("Master",masterForm );
			}else{
				
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
				<div class="right_box"></div>
				<div class="right_box"></div>
				<div class="title">
					<span class="title_icon"><img src="images/1310.png" alt=""
						title="" /> </span>我的伙伴
				</div>

				<ul class="list">
					<li>
						<a>好友名称</a>	
						<a>性别</a> 
						<a>好友QQ</a>
						
					</li>
					<!-- 开始循环 -->
					<%
					String username=null;
				MasterForm masterForm2=new MasterForm();
				masterForm2=(MasterForm)session.getAttribute("Master");
				System.out.println(masterForm2);
				if(masterForm!=null)
				{
				username=masterForm.getMaster_loginname();
				}
				System.out.println("好友时的用户名"+username );
				FriendDao friendDao=new FriendDao();
				FriendForm friendForm1=new FriendForm();
				List<FriendForm> list=friendDao.ListAllFriend(username);
				System.out.println("list 的长度"+list.size());
				for( int i=0;i<list.size();i++)
				{
					friendForm1=list.get(i);
					request.setAttribute("Friend_name", friendForm1.getFriend_name());
					request.setAttribute("friend_sex",friendForm1.getFriend_sex());
					request.setAttribute("friend_oicq", friendForm1.getFriend_oicq());
				%>
					
					<li>
						
						<a><c:out value="${Friend_name}" default="Friend_name" /></a>	
						<a><c:out value="${friend_sex}" default="friend_sex" /></a> 
						<a><c:out value="${friend_oicq}" default="friend_oicq" /></a>
						
					</li>
			<%} %>
				
					<!-- 结束循环 --> 
				</ul>
				
				
				<br /> <br /> <a class="more">- 添加好友 -</a>
				<div class="addfriend">
					<div class="form_subtitle">添加好友</div>
					<form name="addfriend" action="addfriend" method="post">
						<div class="form_row">
							<label class="contact"><strong>好友账号</strong></label> 
							<input
								type="text" class="contact_input" name="master_loginName"
								value="请输入好友账号名" onfocus="this.value = '';"
								onblur="if (value=='') {value='请输入账号名'}" />
						</div>
						<div class="form_row">
							<input type="submit" class="add" value="查找" />
						</div>
						<ul class="list">
							<li>
								<a><c:out value="${message}" default="" /></a>
							</li>
						</ul>
					</form>
				</div>
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