<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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

<title>编辑生育过程图片</title>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/calendar.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/public.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/WdatePicker.js"></script>

<link href="<%=basePath%>res/js/jquery.uploadify/uploadify.css"
	rel="stylesheet" type="text/css" />
<script
	src="<%=basePath%>res/js/jquery.uploadify/jquery.uploadify.min.js"
	type="text/javascript"></script>
<style type="text/css">
 div.wrapper {
	position: relative;
	width: 125px;
	height: 125px;
	border: 0;
	float:left;
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
	
	var imags = "${entity.proctureUrl}";
	var strs = imags.split("|");
	for(i = 0; i < strs.length;i++){
		if(strs[i].length <=0 )
			continue;
		if(strs[i].length >= 0){
		var html = "<div id=\""+strs[i].substring(strs[i].lastIndexOf('/',strs[i].length))+"\" class=\"wrapper\"  style=\"cursor: pointer;\"><img style=\"width: 120px; height: 120px;margin:5px; background: #c2c2c2; cursor: pointer;\" src=\""+'<%=path%>'+strs[i]+"\"/><div onclick=\"deleImg(this)\" class=\"divdelete\" style=\"display: block;\"></div></div>";
		$("#imgContainer").append(html);
		}
	}
	
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
				buttonText : '上传照片',
				fileTypeDesc : '支持的格式：',
				fileTypeExts : '*.jpg;*.bmp;*.gif;*.png',
				fileSizeLimit : '512KB',
				removeTimeout : 1,
				multi : false, //是否支持多文件上传
				onUploadSuccess : function(file, data, response) {
					alert(data);
					if (data.toString() == 'error') {
						alert('文件上传失败，请重新上传');
					} else {
						//alert(data.toString());
						var json = eval("(" + data + ")");
						var html = "<div id=\""+json["fileName"]+"\" class=\"wrapper\"  style=\"cursor: pointer;\"><img style=\"width: 120px; height: 120px;margin:5px; background: #c2c2c2; cursor: pointer;\" src=\""+json["filePath"]+"\"/><div onclick=\"deleImg(this)\" class=\"divdelete\" style=\"display: block;\"></div></div>";
						$("#imgContainer").append(html);
					}
				}
			});
		});	
		
	function deleImg(btnDel){
		if(!confirm('是否删除该图片')){
			return false;
		}
		//向服务器发送删除图片的请求
		$.get("<%=basePath%>autc_data/image_del?fileName="+$(btnDel).parent().attr("id"),function(data){
			if(data.success == "true"){
				alert("删除成功");
				$(btnDel).parent().remove();
			}else{
				alert("删除失败,请重试");
			}
		});
		
	}
	function edit_image(){
		var imags = "";
		if($("#select_station option:selected").val() == "0"){
			alert("必须选择气象站才能添加数据");
			return;
		}
		if($("#textDate").val() == ""){
			alert("必须选择时间");
			return;
		}
		$("#imgContainer").children().each(function(){
			imags += '/Upload/PicturePath/'+$(this).attr("id")+"|";
		});
		$.ajax({
			 type: "POST",
			 url:"<%=basePath%>autc_data/image_edit",
			data : {
				"id":"${entity.pictureCode}",
				"stage" : getQueryString("stage"),
				"station" : $("#select_station option:selected").text(),
				"img": imags,
				"date" : $("#textDate").val(),
				"memo" : $("#textMemo").val(),
				"code":getQueryString("code")
			},
			dataType : "html",
			success : function(data) {
				var data = eval("("+data+")");
				if(data.success == "true"){
					alert("编辑成功");
					 window.location.href = "<%=basePath%>autc_data/image?id="+getQueryString("code")+"&num=${num}";
				}else{
					alert("修改失败");
				}
			}
		});
	}
	function back() {
		window.location.href = "<%=basePath%>autc_data/image?id="+getQueryString("code")+"&num="+getQueryString("num");
	}
</script>
</head>
<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">应用中心>>数据管理>>编辑图片</label>
	</div>
	<table class="table" cellpadding="3px" cellspacing="1px">
		<tbody>
			<tr>
				<td >数据来源</td>
				<td ><select id="select_station">
						<c:choose>
							<c:when test="${stations.size() > 0 }">
								<c:forEach items="${stations}" var="item">
									<option value="item.weatherStationInfoId">${item.name }</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="0">无气象站信息</option>
							</c:otherwise>
						</c:choose>
				</select>
			</tr>
			<tr>
				<td class="text">生育期</td>
				<td class="value">${entity.cropGrowthPeriod }</td>
			</tr>
			<tr>
				<td class="text">采集时间</td>
				<td class="value"><input style="width: 300px" type="text" id="textDate"
				 value="<fmt:formatDate value="${entity.collectionTime}"
									pattern="yyyy-MM-dd" />" onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})"></td>
			</tr>
			<tr>
				<td>图片</td>
				<td>
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
				<td class="text">备注</td>
				<td class="value"><textarea style="width: 300px; height: 100px" id="textMemo"
						id="txtContent">${entity.remark }</textarea></td>
			</tr>
			<tr>
				<td class="text">&nbsp;</td>
				<td class="value submit"><input id="btnSubmit" type="button"
					class="btn_blue" value="提交" onclick="edit_image()"/> <input id="btnSubmit" type="button"
					class="btn_blue" value="返回" onclick="back()" /></td>
			</tr>
		</tbody>
	</table>
</body>
</html>