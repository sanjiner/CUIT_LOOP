<%@page import="com.sanjin.dao.ReviewArticleDao"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK" autoFlush="true"%>
<%@page import="java.util.Date,java.text.*"%>
<%@page import="java.util.List"%>
<%@page import="com.sanjin.form.*"%>
<%@page import="com.sanjin.dao.ArticleDao"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>我的相册</title>
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
					<li ><a href="MyblogDetail.jsp">我的博文</a></li>
					<li><a href="Myaccount.jsp">个人信息</a></li>
					<li class="selected"><a href="MyPhoto.jsp" >我的相册</a></li>
					<li><a href="register.jsp">用户注册</a></li>
					<li><a href="Logout.html">注销登陆</a></li>
				</ul>
			</div>


		</div>
		<!-- header -->
		<div class="center_content">
		<div class="left_content">
			<div class="crumb_nav">
					<a href="MyPhoto.jsp">我的相册</a> &gt;&gt; 查看相册
			</div>
				
				<div class="clear"></div>
			<div id="demo" class="demolayout">
				<div class="clear"></div>
			<div class="tabs-container">
		<%
			PhotoForm photoForm=new PhotoForm();
			List<PhotoForm> list=(List<PhotoForm>)request.getAttribute("list");
			if(list!=null)
			{
				for(int i=0;i<list.size();i++)
				{
					photoForm=list.get(i);
					request.setAttribute("photo_desc", photoForm.getPhoto_desc());
					request.setAttribute("photo_addr",photoForm.getPhoto_addr());
					request.setAttribute("PhotoType_id",photoForm.getPhotoType_id());
					System.out.println("地址"+request.getAttribute("photo_addr"));
			%>
		
				
					<div class="new_prod_box">
                        <a ><c:out value="${photo_desc}">图片描述</c:out></a>
                        <div class="clear"></div>
                        <div class="new_prod_bg">
                        <a><img src="http://localhost:8080/MyBlog_2013121108${photo_addr}" alt="" title="" class="thumb" border="0" height="150" width="250"/></a>
                        </div>           
                    </div>
					<div class="clear"></div>
					<br><br><br><br>
			
			<%}
				}%>
				
			
	</div>
		</div>
			</div>
			
			
			<div class="right_content">
				
				<div class="title">
					<span class="title_icon"><img src="images/bullet1.gif"
						alt="" title="" /> </span>上传图片
						
						<div class="clear"></div>
				
				<div class="feat_prod_box_details">
					<div class="contact_form2">
						<form action="Smartupload?id=<%=request.getAttribute("idm")%>" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
							<div>
								<label class="contact" ><strong>图片描述：</strong></label>
								<input type="text" class="contact_input" name="name" placeholder="请输入类别名"/>
							</div>
							<div>
								<label class="contact" ><strong>请选择照片</strong></label>
								<input type="file" class="contact_input" name="upfile" placeholder="请输入类别描述"/>
							</div>
							<div class="form_row">
								<input type="submit" class="register" value="提交" />
								<input type="reset" class="register" value="重置" />
							</div>
						</form>
					</div>
				</div>
				
				</div>
			
			</div>
			
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