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
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/js/jquery.uploadify/uploadify.css"
	rel="stylesheet" type="text/css" />
<script
	src="<%=basePath%>res/js/jquery.uploadify/jquery.uploadify.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>ueditor/ueditor.config.js"
	type="text/javascript"></script>
<script src="<%=basePath%>ueditor/ueditor.all.min.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=basePath%>ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>ueditor/ueditor.parse.min.js"> </script> 
<title>发布产品</title>
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

div.wrapper {
	position: relative;
	width: 125px;
	height: 125px;
	border: 0;
	float: left;
	background-color: transparent;
	text-align: center;
}

.divdelete {
	position: absolute;
	top: 0;
	right: 0;
	z-index: 1;
	display: none;
	width: 32px;
	height: 32px;
	background-image: url(<%=basePath%>/res/images/common/Close_Box_Red.gif);
	-moz-opacity: .5;
	filter: alpha(opacity = 100);
}
</style>
<script type="text/javascript">

$(document).ready(function(){
	$("#uploadImage").uploadify({
	    debug: false,
	    method: 'post',
	    height: 30,
	    swf: '<%=basePath%>res/js/jquery.uploadify/uploadify.swf',
	    uploader: '<%=basePath%>auth_center_info/expert_info/file',
				//文件选择后的容器ID  
				queueID : 'fileQueue',
				width : 120,
				auto : true,
				buttonText : '上传图片',
				fileTypeDesc : '支持的格式：',
				fileTypeExts : '*.jpg;*.bmp;*.gif;*.png',
				fileSizeLimit : '10240KB',
				removeTimeout : 1,
				multi : false, //是否支持多文件上传
				onUploadSuccess : function(file, data, response) {
					alert(data);
					if (data.toString() == 'error') {
						alert('文件上传失败，请重新上传');
					} else {
						//alert(data.toString());
						var json = eval("(" + data + ")");
						/* $("#divDel").css("display","block");
						$("#divwrapper").attr("title",json["fileName"]);
						$("#imgProduce").attr("src",json["filePath"]); */
						var html = "<div id=\""+json["fileName"]+"\" class=\"wrapper\"  style=\"cursor: pointer;\"><img style=\"width: 120px; height: 120px;margin:10px; background: #c2c2c2; cursor: pointer;\" src=\""+json["filePath"]+"\"/><div onclick=\"deleImg(this)\" class=\"divdelete\" style=\"display: block;\"></div></div>";
						$("#imgContainer").append(html); 
					}
				}
			});
		});

	function validate() {
		if ($("#newstitle").val() == "") {
			alert("请输入商品名称");
			return false;
		}
		if ($("#newscontent").val() == "") {
			alert("请输入商品内容");
			return false;
		}
		return true;
	}

	function submitData() {
		if (!validate())
			return false;
		var imags = "";
		$("#imgContainer").children().each(function(){
			imags += '/Upload/PicturePath/'+$(this).attr("id")+"|";
		});
		$.ajax({
			type:"POST",
			url : "<%=basePath%>enterprise/produce/add",
			data : {
				"productname" : $("#newstitle").val(),
				"productcontent" : UE.getEditor('editor').getContent(),
				/* "productimg":$("#divwrapper").attr("title") */
				"productimg":imags
				
			},
			success : function(data) {
				if (data.success == "true") {
					alert("添加成功");
					location.href="<%=basePath%>enterprise/produce/list";
				} else {
					alert("添加失败,请稍后重试");
				}
			}
		});
	}
	
	function deleImg(btnDel){
		//向服务器发送删除图片的请求
		$.get("<%=basePath%>autc_data/image_del?fileName="
				+ $(btnDel).parent().attr("id"), function(data) {
			if (data.success == "true") {
				alert("删除成功");
				$(btnDel).parent().remove();
				/* $("#divDel").css("display", "none");
				$("#imgProduce").attr("src", ""); */
			} else {
				alert("删除失败,请重试");
			}
		});

	}
</script>
</head>

<body style="background: #edf1fc;">
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">企业个性化管理>>产品管理>>发布产品</label>
	</div>
	<form id="form1" action="<%=basePath%>enterprise/news/add"
		method="post">
		<table>
			<tr>
				<td>名称：</td>
				<td><input style="width: 500px" id="newstitle"></td>
			</tr>
			<tr>
				<td>图片：</td>
				<td>
					<!-- <div id="imgContainer" style="height: auto; min-height: 190px">
						<div id="divwrapper" class="wrapper" style="cursor: pointer;">
							<img id="imgProduce"
								style="width: 120px; height: 180px; margin: 5px; background: #c2c2c2; cursor: pointer;" />
							<div onclick="deleImg(this)" class="divdelete" id="divDel"
								style="display: none;"></div>
						</div>
					</div> -->
					<div id="imgContainer" style="height: auto;min-height: 150px">
					</div>
					<div>
						<div id="fileQueue"></div>
						<div style="float: left; width: 125px;">
							<input type="file" name="file" id="uploadImage" />
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>内容：</td>
				<td><script id="editor" type="text/plain"
						style="width:1024px;height:500px;"></script></td>
			</tr>
		</table>
		<div style="text-align: center; width: 550px; margin-top: 10px">
			<input type="button" onclick="submitData()" class="btn_blue"
				value="提交"> <a href="<%=basePath%>enterprise/produce/list"
				class="btn_blue">返回</a>
		</div>
	</form>
</body>
<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
</script>

</html>
