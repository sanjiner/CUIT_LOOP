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
			url : "<%=basePath%>enterprise/news/edit",
			data : {
				"newsId" :"${news.newsId}",
				"newstitle" : $("#newstitle").val(),
				"newscontent" : UE.getEditor('editor').getContent()
			},
			success : function(data) {
				if (data.success == "true") {
					alert("编辑成功");
					location.href="<%=basePath%>enterprise/news/list";
				} else {
					alert("编辑失败,请稍后重试");
				}
			}
		});
	}
</script>
</head>

<body style="background: #edf1fc;">
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">企业个性化管理>>新闻管理>>编辑新闻</label>
	</div>
	<form id="form1" action="<%=basePath%>enterprise/news/add"
		method="post">
		<table>
			<tr>
				<td>标题：</td>
				<td><input style="width: 500px" id="newstitle"
					value="${news.newstitle }"></td>
			</tr>
			<tr>
				<td>内容：</td>
				<td><script id="editor" type="text/plain"
						style="width:1024px;height:500px;">${news.newscontent}</script></td>
			</tr>
		</table>
		<div style="text-align: center; width: 550px; margin-top: 10px">
			<input type="button" onclick="submitData()" class="btn_blue"
				value="提交"> <a style="width: 80px" class="btn_blue"
					href="<%=basePath%>enterprise/news/list">返回</a>
		</div>
	</form>
</body>
<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
</script>
</html>
