<%@page import="com.sanjin.dao.ReplyDao"%>
<%@page import="com.sanjin.dao.ReviewArticleDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" autoFlush="true"%>
<%@page import="java.util.Date,java.text.*"%>
<%@page import="java.util.List"%>
<%@page import="com.sanjin.form.*"%>
<%@page import="com.sanjin.dao.ArticleDao"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>博客</title>
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

		<!-- header -->
		<div class="header">
			<div class="logo">
				<a href="index.html"><img src="images/6ek.png" alt="" title=""
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
		<!-- header -->
		<div class="center_content">
			<div class="left_content">
				<div class="crumb_nav">
					<a href="index.jsp">首页</a> &gt;&gt; 我的博文
				</div>
				<%String id=request.getParameter("id");
				
				ArticleDao articleDao=new ArticleDao();
				ArticleForm aritcleForm=new ArticleForm();
				aritcleForm=articleDao.selectArticleByarticle_typeID(id);
					request.setAttribute("article_title", aritcleForm.getArticle_title());
					request.setAttribute("article_create",aritcleForm.getArticle_create());
					request.setAttribute("article_content", aritcleForm.getArticle_content());
					request.setAttribute("article_typeID", aritcleForm.getArticle_typeID());
					request.setAttribute("article_info", aritcleForm.getArticle_info());
					request.setAttribute("aritcle", aritcleForm);
				%>
				
				
				
				
				<div class="title">
					<span class="title_icon"><img src="images/bullet1.gif"
						alt="" title="" /></span>
						<c:out value="${article_title}">文章标题</c:out>
				</div>
				<div class="feat_prod_box_details">
					<div class="prod_det_box">
						<div class="box_top"></div>
						<div class="box_center">
							<div class="prod_title"><c:out value="作者：${article_create}">作者</c:out></div>
							<p class="details">
								<c:out value="${article_content}">暂无文章内容</c:out>
							</p>

							<div class="clear"></div>
						</div>

						<div class="box_bottom"></div>
					</div>
					<div class="clear"></div>
				</div>





				<div id="demo" class="demolayout">

					<ul id="demo-nav" class="demolayout">
						<li><a class="active" href="#tab1">我要评论</a></li>
						<li><a class="" href="#tab2">评论列表</a></li>
					</ul>

					<div class="tabs-container">
						<!-- 评论框 -->
						<div style="display: block;" class="tab" id="tab1">
							<form name="review" action="AddArticleReview?id=<%=request.getAttribute("article_typeID") %>" method="post">
								<div class="form_row">
									<label class="contact"><strong>评论该博文</strong></label>
									<textarea class="con_input" name="review_content"></textarea>
								</div>
								<div class="form_row">

									<input type="submit" class="register" value="提交" /> 
									<input type="reset" class="register" value="重置" />
								</div>
							</form>
							<div class="clear"></div>
						</div>
						<!-- 评论框结束 -->

						<!-- 历史评论列表 -->
						<div style="display: none;" class="tab" id="tab2">
							<div class="new_prod_box">
								<div class="form_row">
									<label class="contact"><strong>评论列表:</strong></label>
								</div>
								<%
									ReviewArticleDao reviewArticleDao=new ReviewArticleDao();
									ReviewFrom reviewFrom=new ReviewFrom();
									ReplyForm replyForm=new ReplyForm();
									ReplyDao replyDao=new ReplyDao();
									List<ReviewFrom> list=reviewArticleDao.SelectAllReviewByArticleId(id);
									for(int i=0;i<list.size();i++)
									{
										reviewFrom=list.get(i);
										request.setAttribute("review_content", reviewFrom.getReview_content());
										request.setAttribute("reviewID", reviewFrom.getReviewID());
										replyForm=replyDao.SelectReplyByreviewID(reviewFrom.getReviewID());
										request.setAttribute("Reply_Content", replyForm.getReply_Content());
								%>
								
								
								<!--循环开始  -->
								<div class="new_prod_box">
								<div class="new_prod_bg">
								<div class="new_prod_bg">
									<div class="clear"></div>
									<textarea class="review2" style="overflow-y: hidden" disabled="disabled"><c:out value="${review_content}"></c:out></textarea>
									<div class="clear"></div>
									<textarea class="review2" style="overflow-y: hidden" disabled="disabled"><c:out value="${Reply_Content}">暂无回复</c:out></textarea>
									<div class="clear"></div>
									<div class="clear"></div>
									<form action="AddNewReply?id=${reviewID}" method="post">
									<input type="text" class="contact_input2" placeholder="回复该评论" name="Reply_Content" id="Reply_Content_id"></input>
									<input type="submit" value="-提交-">
									</input>
									<%-- <a href="AddNewReply?id=${reviewID}" class="more">-回复-</a> --%>
									</form>
									<div class="clear"></div>
								</div>
								</div>
								</div>
								<div class="clear"></div>
								<br>
								<br>
								<!--循环结束  -->
								<%}%>
								
							</div>

							<div class="clear"></div>
						</div>

						<!-- 历史评论列表 -->

					</div>


				</div>



				<div class="clear"></div>
			</div>
			<!--end of left content-->

			<!--start of right content-->
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
					<span class="red">当前时间：<%=time%>
					</span>
				</div>
				<div class="cart"></div>

				<div class="languages_box">
					<span class="red">
					<c:out value="${back}" > </c:out></span>

				</div>



				<div class="right_box"></div>

				<div class="right_box"></div>


			</div>

			<!--end of right content-->




			<div class="clear"></div>
		</div>
		<!--end of center content-->


		<!-- footer -->
		<div class="footer">

			<div class="right_footer">
				<a href="#">首页</a> <a href="#">关于我们</a> <a href="#">服务</a> <a
					href="#">隐私政策</a> <a href="#">联系我们</a>

			</div>


		</div>
		<!--end footer -->


	</div>

</body>
<script type="text/javascript">

var tabber1 = new Yetii({
id: 'demo'
});

</script>
</html>