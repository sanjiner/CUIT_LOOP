<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模型管理-气象要素-列表</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<link href="<%=basePath%>res/js/jquery.uploadify/uploadify.css"
	rel="stylesheet" type="text/css" />
<script
	src="<%=basePath%>res/js/jquery.uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
</head>
<body>
	<form id="form1" method="post" action="" enctype="multipart/form-data"
		onsubmit="return doSet()">
		<table class="info" cellpadding="0" cellspacing="0">
			<tr>
				<td class="text">图片类型：</td>
				<td class="value"><select id="selType" name="selType"
					class="width60per" onchange="showImg()">
						<option value="qihou">气候品质认证logo</option>
						<option value="nongwang">农网认证logo</option>
						<option value="teyou">认证等级特优</option>
						<option value="you">认证等级优</option>
						<option value="liang">认证等级良</option>
				</select></td>
			</tr>
			<tr>
				<td class="text"><span class="required">*</span>图片：</td>
				<td class="value"><img id="logo" alt=""
					src="<%=basePath%>res/images/logo/qihou.jpg"
					style="width: auto; height: 220px" onclick="selectImg()"><br />
					<br />
					<div>
						<input id="flImage" name="flImage" type="file"
							accept="image/gif, image/jpeg,image/jpg, image/png" />
					</div> <input type="hidden" name="txtImgUrl" id="txtImgUrl" value="" />
				</td>
			</tr>
			<tr>
				<td class="text">&nbsp;</td>
				<td class="value"><input type="submit" class="btn_blue"
					value="设置" />&nbsp; &nbsp;<a id="btnBack"
					href="javascript:history.go(-1);" class="btn_blue">返回</a></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		function showImg(){
			$("#logo").attr("src","<%=basePath%>res/images/logo/" + $("#selType").val() + ".jpg");
		}
		//////商标上传
        $("#flImage").uploadify({
            debug: false,
            method: 'post',
            height: 30,
            swf: '<%=basePath%>res/js/jquery.uploadify/uploadify.swf',
            uploader: '<%=basePath%>label/file',
			//文件选择后的容器ID  
			queueID : 'fileQueue',
			width : 120,
			auto : true,
			buttonText : '上传图片',
			fileTypeDesc : '支持的格式：',
			fileTypeExts : '*.jpg;*.bmp;*.gif;*.png',
			fileSizeLimit : '512KB',
			removeTimeout : 1,
			multi : false, //是否支持多文件上传
			onUploadSuccess : function(file, data, response) {
				data=data.replace(/\\/g,"/");
				if (data.toString() == 'error') {
					alert('文件上传失败，请重新上传');
				} else {
					var json = eval("(" + data + ")");
					$('#logo').attr('src', json["filePath"]);
					$("#txtImgUrl").val(json["filePath"]);
				}
			}
		});
		function doSet(){
			$.post("<%=basePath%>label/set", $("#form1").serialize(), function(data){
				if(data.state=="fail"){
					alert(data.result);
				}
				else{
					alert(data.result);
				}
				location.href="<%=basePath%>label/setting";
			});
		}
	</script>
</body>
</html>