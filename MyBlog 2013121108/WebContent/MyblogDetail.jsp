<%@page import="com.sanjin.dao.ArticleTypeDao"%>
<%@page import="java.util.List"%>
<%@page import="com.sanjin.dao.ArticleDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date,java.text.*"%>
<%@page import="com.sanjin.form.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>我的博文</title>
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
					<li class="selected"><a href="MyblogDetail.jsp">我的博文</a></li>
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
						alt="" title="" /> </span>我的博文
				</div>
			
				<div class="feat_prod_box">
					<div class="prod_img"></div>
					
				<%
					ArticleForm articleForm=new ArticleForm();
					MasterForm masterForm2=new MasterForm();
					ArticleDao articleDao2=new ArticleDao();
					masterForm2=(MasterForm)session.getAttribute("Master");
					if(masterForm2!=null)
					{
					String Master_loginname2=masterForm2.getMaster_loginname();
					List<ArticleForm> list=articleDao2.SelectMyArticle(Master_loginname2);
					
					System.out.println("list的大小 在我的博客详情里面"+list.size()+"Master_loginname2"+Master_loginname2);
					for(int i=0;i<list.size();i++)
					{
						articleForm=list.get(i);
						request.setAttribute("article_title", articleForm.getArticle_title());
						request.setAttribute("article_create", articleForm.getArticle_create());
						request.setAttribute("article_content", articleForm.getArticle_content());
						request.setAttribute("article_typeID", articleForm.getArticle_typeID());
						request.setAttribute("article_info", articleForm.getArticle_info());
						
				%>
						<div class="prod_det_box">
						<div class="box_top"></div>
						<div class="box_center">
							<div class="prod_title"><c:out value="${article_title}">博文标题</c:out></div>
							<p class="details"><c:out value="${article_info}">博文描述</c:out></p>
							<a href="DeleteArticle?id=<%=request.getAttribute("article_typeID") %>" class="more">- 删除 -</a>
							<a href="detail.jsp?id=<%=request.getAttribute("article_typeID") %>" class="more">- 阅读更多 -</a>
							<div class="clear"></div>
						</div>
						<div class="box_bottom"></div>
						
					
					
					
					
					</div>
					
					<%}
					}%>
					<!-- 循环结束 -->
				</div>
				
			</div>
			<% MasterForm masterForm=new MasterForm();
			masterForm=(MasterForm)session.getAttribute("user");
			if(masterForm!=null){
			String Master_loginname=masterForm.getMaster_loginname();
			System.out.println(masterForm.getMaster_loginname());
			request.setAttribute("loginname",Master_loginname);
			}
			else if(masterForm==null)
			{
				response.sendRedirect("login.jsp");
			}
			%>
			<div class="right_content">
				<!--right_center  -->
				<div class="languages_box">
					<span class="red">欢迎您：<c:out value="${loginname}"
							default="游客" /></span>

				</div>
				<div class="cart">
					<%Date date=new Date();
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String time=simpleDateFormat.format(date);
			%>
					<span class="red">现在时间：<%=time %></span>
				</div>
				<div class="languages_box">
					<span class="red">
					<c:out value="${check}"></c:out>
					</span>
				</div>
				<div class="title">
					<span class="title_icon"><img src="images/bullet1.gif"
						alt="" title="" /> </span>写博文
				</div>
				
				<div class="clear"></div>
				<div class="feat_prod_box_details">
					<div class="contact_form2">
						<form name="addArticle" action="AddArticle" method="post">
						<div>
						<label class="contact" ><strong>文章类别：</strong></label>
							<select name="articleType_id">
							<%
								ArticleTypeDao articleTypeDao=new ArticleTypeDao();
								ArticleTypeForm atArticleTypeForm=new ArticleTypeForm();
								List<ArticleTypeForm> list=articleTypeDao.selectAllArticleType();
								for(int i=0;i<list.size();i++)
								{
									atArticleTypeForm=list.get(i);
									request.setAttribute("articleType_id", 
											atArticleTypeForm.getArticleType_id());
									request.setAttribute("articleType_name",
											atArticleTypeForm.getArticleType_name());
									
									
								
							%>
								<option value="${ articleType_id}">
									<c:out value="${ articleType_name}"> </c:out>
								</option>
								<!-- <option value="1" selected="selected">娱乐</option>
								<option value="2">人文</option>
								<option value="3">地理</option>
								<option value="4">社会</option>
								<option value="5">科学</option>
								<option value="6">互联网</option>
								<option value="7">生活感悟</option>
								<option value="8">技术讨论</option> -->
								
								
								<% }%>
								
							</select>
						</div>
						<div class="form_row">
							<div>
								<label class="contact" >
									<strong>文章名称：</strong>
								</label> 
								<input type="text" class="contact_input" name="article_title" />
							</div>
							<div>
							<label class="contact" ><strong>文章描述：</strong></label>
								<textarea  class="contact_textarea"  name="article_info"></textarea>
							</div>
							<div>
								<label class="contact" ><strong>文章内容：</strong></label>
								<textarea  class="inputArticle"  name="article_content"></textarea>
							</div>
							<div>
								
							</div>
						</div>
						
						<div class="form_row">

									<input type="submit" class="register" value="提交" /> 
									<input type="reset" class="register" value="重置" />
								</div>
						</form>
					</div>
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