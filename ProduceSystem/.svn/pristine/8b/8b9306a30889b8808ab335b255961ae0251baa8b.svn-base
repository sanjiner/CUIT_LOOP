<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />

<title>新闻</title>
<style type="text/css">
body {
	font-family: "黑体";
}

.container {
	width: 100%;
	margin: 0 auto;
}

a {
	display: inline-block;
	width: 50px;
	float: inherit;
}
</style>
<script>window.PROJECT_CONTEXT = "<%=basePath%>";</script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
	<script src="<%=basePath%>ueditor/ueditor.config.js"
	type="text/javascript"></script>
<script src="<%=basePath%>ueditor/ueditor.all.min.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=basePath%>ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>ueditor/ueditor.parse.min.js"> </script> 
	
<script type="text/javascript">
	function validate() {
		if ($("#newstitle").val() == "") {
			alert("请输入新闻标题");
			return false;
		}
		if ($("#newscontent").val() == "") {
			alert("请输入新闻内容");
			return false;
		}
		return true;
	}

	function submitData() {
		if (!validate())
			return false;
		$.ajax({
			type:"POST",
			url : "<%=basePath%>enterprise/news/add",
			data : {
				"newstitle" : $("#newstitle").val(),
				"newscontent" : UE.getEditor('editor').getContent()
			},
			success : function(data) {
				if (data.success == "true") {
					alert("添加成功");
					location.href="<%=basePath%>enterprise/news/list";
				} else {
					alert("添加失败,请稍后重试");
				}
			}
		});
	}
</script>
</head>

<body style="background: #edf1fc;">
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">企业个性化管理>>新闻管理>>发布新闻</label>
	</div>
	<form id="form1" action="<%=basePath%>enterprise/news/add"
		method="post">
		<table>
			<tr>
				<td>标题：</td>
				<td><input style="width: 500px" id="newstitle"></td>
			</tr>
			<tr>
				<td>内容：</td>
				<td><script id="editor" type="text/plain"
						style="width:1024px;height:500px;"></script></td>
			</tr>
		</table>
		<div style="text-align: center; width: 550px; margin-top: 10px">
			<input type="button" onclick="submitData()" class="btn_blue"
				value="提交"> <a href="<%=basePath%>enterprise/news/list"
				class="btn_blue">返回</a>
		</div>
	</form>
</body>
<script type="text/javascript">
    var ue = UE.getEditor('editor');
</script>
</html>
