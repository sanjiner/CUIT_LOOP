<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible"
content="IE=9; IE=8; IE=7; IE=EDGE">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>res/css/mainPage/mainPageStyle.css">
<title>农产品认证与溯源</title>

<style type="text/css">
</style>
</head>

<body>
	<div style="position: relative; margin-right: -20px; color: red; min-width:1200px;">
		<img src="<%=basePath%>res/images/top/top_bg.png">
		<img style="width:820px; height: 140px; margin-top:-140px;z-index: 200" src="<%=basePath%>res/images/top/top_logoandname.png" >
		<div style="position: absolute; z-index: 200; right: 40px; top: 115px">
			<span>欢迎您，<shiro:principal /></span>
			<span><a
				target="_top" id="logout" href="<%=basePath%>sys/logout">注销</a>
			</span>
			<span><a href="<%=basePath%>sys/changePassword" target="mainFrame">修改密码</a></span>
		</div>
	</div>

	<div class="container">

		<div id="title">
			<img src="<%=basePath%>res/images/left/title_bg.png">
		</div>

		<div class="items">
			
			<c:forEach items="${moduleList}" var="parentModule">
				<div class="item">
					<img src="<%=basePath%>res/images/left/label_bg.png">
					<div class="optlabel">
						 <span>${parentModule.cuitMoonModuleName}</span>
					</div>
					<div>
						<ul>
							<c:forEach items="${parentModule.childModulesList}" var="childModule">
							
								<li><a href="${ctx}${childModule.cuitMoonModuleUrl}"
								target="mainFrame"><img
									src="<%=basePath%>res/images/left/item_ic_unselected.png">${childModule.cuitMoonModuleName}</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:forEach>
			 
			
		</div>

	</div> 

	<!-- iframe -->

	<div class="right_content">
		<iframe src="<%=basePath %>sys/welcome" id="mainFrame" 
		name="mainFrame" frameBorder="0" scrolling="auto" height="100%" onload="reinitIframe()" width="100%" ></iframe>
	</div>

	<script type="application/javascript"
		src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
	<script type="application/javascript"
		src="<%=basePath%>res/js/mainPage/mainPageMenu.js"></script>
	<script type="application/javascript">
		if (top.location != self.location){       
	        top.location = "<%=basePath%>sys/mainPage";       
	    }   
	
	
		function reinitIframe() {
		    var iframeid = document.getElementById("mainFrame"); 
		    if (document.getElementById) {
		        if (iframeid && !window.opera) {
		            if (iframeid.contentDocument && iframeid.contentDocument.body.offsetHeight) {
		                iframeid.height = iframeid.contentDocument.body.offsetHeight+30;
		            } else if (iframeid.Document && iframeid.Document.body.scrollHeight) {
		                iframeid.height = iframeid.Document.body.scrollHeight+30;
		            }
		        }
		    }
		}

	</script>

</body>
</html>
