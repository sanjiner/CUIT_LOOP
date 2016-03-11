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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标签申请-填写申请</title>
<style type="text/css">
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
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>res/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=basePath%>res/js/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>res/js/jquery.uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>res/js/jquery.uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
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
				fileSizeLimit : '512KB',
				removeTimeout : 1,
				multi : false, //是否支持多文件上传
				onUploadSuccess : function(file, data, response) {
					//alert(data);
					if (data.toString() == 'error') {
						alert('文件上传失败，请重新上传');
					} else {
						//alert(data.toString());
						var json = eval("(" + data + ")");
						$("#divDel").css("display","block");
						$("#divwrapper").attr("title",json["fileName"]);
						$("#imgProduce").attr("src",json["filePath"]);
						var imgName = $("#divwrapper").attr("title");
						document.getElementById('imgName').value = imgName;
					}
				}
			});
		});	
		
function deleImg(btnDel){
	//向服务器发送删除图片的请求
	$.get("<%=basePath%>autc_data/image_del?fileName="
			+ $(btnDel).parent().attr("title"), function(data) {
		if (data.success == "true") {
			alert("删除成功");
			$("#divDel").css("display", "none");
			$("#imgProduce").attr("src", "");
		} else {
			alert("删除失败,请重试");
		}
	});
}

function validate() {
    return $("#form1").validate({
        rules: {
        	applyNum: { digits:true, min:1, required: true },
        	pici: {required: true},
        	format: {required: true}
        },
        messages: {
        	applyNum: { required: "请输入申请数量", min:"最小是1", digits: "只能输入合法的数字" },
        	pici: {required: "请输入申请批次"},
        	format:{required: "请输入标签规格"}
        }
    }).form();
}

function btnOK() {
	if(validate()){
		document.getElementById('form1').submit();
	}
}
function back() {
	window.location.href = "<%=basePath%>label/apply/applylist";
}
</script>
</head>
<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">认证标签管理>>标签申请>>填写申请</label>
</div>
<br/>
<form id="form1" action="<%=basePath%>label/apply/add" method="post">	
	<div>
		<input id="imgName" name="imgName" type="hidden" value=""/>
		<input id="applyBh" name="applyBh" type="hidden" value="${applyBh}"/>
		<input id="labeltype" name="labeltype" type="hidden" value="${labeltype}"/>
		<input id="businessName" name="businessName" type="hidden" value="${businessName}"/>
		<table>	
			<tr>
				<td>产品名称</td>
				<td>${productName}</td>
			</tr>
			<tr>
				<td>产品品牌</td>
				<td>${productBrand}</td>
			</tr>
			<tr>
				<td>产地</td>
				<td>${productBase}</td>
			</tr>
			<tr>
				<td>认证申请时间</td>
				<td>${applyTime}</td>
			</tr>
			<tr>
				<td>认证类型</td>
				<td>${labeltype}</td>
			</tr>
			<tr>
				<td>申请商家</td>
				<td>${businessName}</td>
			</tr>
			<tr>
				<td>申请数量</td>
				<td><input type="text" id="applyNum" name = "applyNum"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>申请批次</td>
				<td><input type="text" id="pici" name = "pici"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>标签规格</td>
				<td><input type="text" id="format" name = "format"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>示例图片</td>
				<td>
					<div id="imgContainer" style="height: auto; min-height: 190px">
						<div id="divwrapper" class="wrapper" style="cursor: pointer;">
							<img id="imgProduce"
								style="width: 120px; height: 180px; margin: 5px; background: #c2c2c2; cursor: pointer;" />
							<div onclick="deleImg(this)" class="divdelete" id="divDel"
								style="display: none;"></div>
						</div>
					</div>
					<div>
						<div id="fileQueue"></div>
						<div style="margin-left:5px; float: left; width: 125px;">
							<input type="file" name="file" id="uploadImage" />
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>申请理由</td>
				<td><textarea id="applyReason" name="applyReason" rows="5" cols="50" ></textarea></td>
			</tr>			
		</table>
	</div>
	<br/>
	<div>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="提交" onclick="btnOK()" class="btn_blue" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="返回" onclick="back()" class="btn_blue" />
	</div>
</form>
</body>
</html>