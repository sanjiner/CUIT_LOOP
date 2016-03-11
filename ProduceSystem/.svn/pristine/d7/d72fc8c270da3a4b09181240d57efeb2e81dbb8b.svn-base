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
<link href="<%=basePath%>res/css/common/applay.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/js/jquery.uploadify/uploadify.css"
	rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery.validate.min.js">
</script>
<script
	src="<%=basePath%>res/js/jquery.uploadify/jquery.uploadify.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/calendar.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/WdatePicker.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新建认证申请</title>
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
	display: block;
	width: 32px;
	height: 32px;
	background-image: url(<%=basePath%>/res/images/common/Close_Box_Red.gif);
	-moz-opacity: .5;
	filter: alpha(opacity = 100);
}
</style>
<script type="text/javascript">
	function validate() {
		//输入验证
		var vali = $("#form1").validate({
			rules : {
				text_company_name : {
					required : true,
					maxlength : 50
				},
				text_product_name : {
					required : true
				},
				text_product_brand : {
					required : true
				},
				text_product_address : {
					required : true
				},
				text_output_value : {
					required : true
				},
				text_output_num : {
					required : true
				},
				text_contact_phone : {
					required : true
				},
				text_label_num:{
					required : true,
					digits : true
				} 
			},
			messages : {
				text_company_name : {
					required : "请输入公司名称"
				},
				text_product_name : {
					required : "请输入产品名称"
				},
				text_product_brand : {
					required : "请输入产品品牌"
				},
				text_product_address : {
					required : "请输入联系地址"
				},
				text_output_value : {
					required : "请输入产值"
				},
				text_output_num : {
					required : "请输入产量"
				},
				text_contact_phone : {
					required : "请输入联系电话"
				},
				text_label_num:{
					required : "请输入申请的标签数量",
					digits : "必须为数字"
				} 
			}
		}).form();
		if($("#select_pro option:selected").val() == "请选择省份"){
			alert("请选择产地");
			vali = false;
		}
		if (vali) {
			submit();
			return true;
		} else {
			return false;
		}
	}
	
	function back(){
		window.location.href="<%=basePath%>origin/OAlist";
	}
	
	$(document).ready(function(){
		$("#uploadImage").uploadify({
		    debug: false,
		    method: 'post',
		    height: 30,
		    swf: '<%=basePath%>res/js/jquery.uploadify/uploadify.swf',
		    uploader: '<%=basePath%>auth_center_info/expert_info/file;',
					//文件选择后的容器ID  
					queueID : 'fileQueue',
					width : 120,
					auto : true,
					buttonText : '上传证书',
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
							//var html = "<div id=\""+json["fileName"]+"\" class=\"wrapper\" style=\"cursor: pointer; width: 250px;\">"
							//+"<img id=\"image\" style=\"width: 120px; height: 150px; margin: 5px; background: #c2c2c2; cursor: pointer; float: left\" src=\""+json["filePath"]+"\" />"
							//+"<div onclick=\"deleImg(this)\" class=\"divdelete\" style=\"display: block; top: 0; left: 93px; width: 32px;\"></div>"
							//+"<div style=\"float: left; display: block; float: left\"><p id=\"cert_name\">"+$("#input_name").val()+"</p><p id=\"text_publish_name\">"+$("#input_publisher").val()+"</p><p id=\"text_publish_time\">"+$("#input_publish_time").val()+"</p></div></div>";
							//var html = "<div id=\""+json["fileName"]+"\" class=\"wrapper\"  style=\"cursor: pointer;\"><img style=\"width: 120px; height: 120px;margin:5px; background: #c2c2c2; cursor: pointer;\" src=\""+json["filePath"]+"\"/><div onclick=\"deleImg(this)\" class=\"divdelete\" style=\"display: block;\"></div></div>";
							$("#imgTmp").attr("src",json["filePath"]);
							$("#imgTmp").attr("alt",json["fileName"]);
						//	$("#imgContainer").append(html);
						}
					}
				});
			});	
	
	function deleImg(btnDel){
		if(!confirm('确认删除此图片?')){
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
	 
	
	function saveCert(){
		if($("#imgTmp").attr("alt") == "" || $("#imgTmp").attr("alt") == undefined){
			alert("请先上传证书");
			return;
		}
		if($("#input_name").val() == ""){
			alert("请输入证书名称");
			return;
		}
		if($("#input_publisher").val() == ""){
			alert("请输入证书颁发机构");
			return;
		}
		if($("#input_publish_time").val() == ""){
			alert("请输入证书颁发时间");
			return;
		}
		var name = $("#imgTmp").attr("alt");
		var url = $("#imgTmp").attr("src");
		var html = "<div id=\""+name+"\" class=\"wrapper\" style=\"cursor: pointer; width: 250px;\">"
		+"<img id=\"image\" style=\"width: 120px; height: 150px; margin: 5px; background: #c2c2c2; cursor: pointer; float: left\" src=\""+url+"\" />"
		+"<div onclick=\"deleImg(this)\" class=\"divdelete\" style=\"display: block; top: 0; left: 93px; width: 32px;\"></div>"
		+"<div style=\"float: left; display: block; float: left\"><p id=\"cert_name\">"+$("#input_name").val()+"</p><p id=\"text_publish_name\">"+$("#input_publisher").val()+"</p><p id=\"text_publish_time\">"+$("#input_publish_time").val()+"</p></div></div>";
		$("#imgContainer").append(html);
		$("#imgTmp").attr("alt","");
		$("#imgTmp").attr("src","");
		$("#input_name").val("");
		$("#input_publisher").val("");
		$("#input_publish_time").val("");
	}

	function pro_change() {
		var index = "<!-- city -->";
		var end = "<!-- city-end -->";
		var parentCode = $("#select_pro option:selected").val();
		$.get("<%=basePath%>authc/updateCity?parentCode=" + parentCode,
				function(data, status) {
					var str = data.toString().substring(data.indexOf(index)+60);
					var html = str.substring(str.indexOf(index),str.indexOf(end)+end.length);
					$("#select_city").html(html);
				});
		$("#select_country").html("");
	}
	
	function city_change() {
		var index = "<!-- country -->";
		var end = "<!-- country-end -->";
		var parentCode = $("#select_city option:selected").val();
		$.get("<%=basePath%>authc/updateCountry?parentCode=" + parentCode,
				function(data, status) {
					var str = data.toString().substring(
							data.indexOf(index) + 60);
					var html = str.substring(str.indexOf(index), str
							.indexOf(end)
							+ end.length);
					$("#select_country").html(html);
				});
	}

	function submit() {
		var type =  "";
		var imags = "[";
		$("#imgContainer").children().each(function(){
			imags +="{";
			imags +="\"name\":\""+$(this).find("#cert_name").text()+"\",";
			imags +="\"publisher\":\""+$(this).find("#text_publish_name").text()+"\",";
			imags +="\"url\":\""+$(this).attr("id")+"\",";
			imags +="\"time\":\""+$(this).find("#text_publish_time").text()+"\"";
			imags+="},"
			//imags += $(this).attr("id")+","+ $(this).find("img").attr("src") + "|";
		});
		imags = imags.substring(0,imags.length -1);
		imags+="]";
		 $.ajax({
             type: "post",
             url: "<%=basePath%>origin/add",
             dataType: "html",
             /*
             text_company_name :  
				text_product_name :  
				text_product_brand :  
				text_product_address : 
				text_output_value :  
				text_output_num :  
				text_contact_phone : 
				text_label_num: 
				chb_type : 
             */
             data: { text_company_name : $("#text_company_name").val(),
            	 text_product_name  : $("#text_product_name").val(),
            	 text_product_brand  : $("#text_product_brand").val(),
            	 text_product_address  : $("#text_product_address").val(),
            	 text_output_value  : $("#text_output_value").val(),
            	 text_output_num  : $("#text_output_num").val(),
            	 text_contact_phone  : $("#text_contact_phone").val(),
            	 text_label_num  : $("#text_label_num").val(),
            	 text_prodcut_base  : $("#select_pro option:selected").val() + "|" + $("#select_city option:selected").val()+"|" + $("#select_country option:selected").val(),
            	 imags : imags,
            	 text_product_desc:$("#text_product_desc").val()
            	 },
             success: function (data) {
            	 var obj = eval('(' + data + ')');
            	 if(obj.success == "true"){
            		 alert("添加成功");
            		 window.location.href="<%=basePath%>origin/OAlist";
            	 }else{
            		 alert("添加失败");
            	 }
             },
             cache: false,
             error: function (msg) {
                 alert(msg.responseText);
             }
         });
	}
	function back() {
		window.location.href="<%=basePath%>/origin/OAlist";
	}
</script>
</head>
<body style="background: #edf1fc">
	<div
		style="background: #4c9ee7; width: 100%; height: 30px; margin-bottom: 5px">
		<label
			style="width: auto; height: 30px; text-align: center; color: white; line-height: 30px; margin-left: 5px">农网认证>>认证申请</label>
	</div>
	<form id="form1">
		<div id="div_second" style="display: block;">
			<div
				style="width: 100%; height: 30px; line-height: 30px; background: #0090d7; padding-left: 10px; margin-top: 10dp; margin-right: 10dp">
				<label><font color="#fff" size="+1.5"><b>产品信息</b></font></label>
			</div>
			<table width="90%">
				<tbody>
					<tr>
					<tr>
						<td class="text_right"><span class="required">*</span>单位名称</td>
						<td class="content_left"><input type="text" value="${bsm.campanyName}"
							id="text_company_name" name="text_company_name" /></td>
						<td class="text_right"><span class="required">*</span>申请人</td>
						<td class="content_left"><input type="text" value="${UserRealName}"
							id="text_company_person" name="text_company_person" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>产品名称</td>
						<td class="content_left"><input type="text"
							id="text_product_name" name="text_product_name" /></td>
						<td class="text_right"><span class="required">*</span>产品品牌</td>
						<td class="content_left"><input type="text"
							id="text_product_brand" name="text_product_brand" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>生产基地</td>
						<td class="content_left"><select id="select_pro"
							onchange="pro_change()">
								<option selected="selected">请选择省份</option>
								<c:forEach items="${pro}" var="item">
									<c:if test="${ item.cuitMoonParentAreaCode == 0}">
										<option value="${item.cuitMoonAreaCode}">${item.cuitMoonAreaName}</option>
									</c:if>
								</c:forEach>
						</select> <select id="select_city" onchange="city_change()">
								<!-- city -->
								<c:forEach items="${city}" var="item">
									<option value="${item.cuitMoonAreaCode}">${item.cuitMoonAreaName}</option>
								</c:forEach>
								<!-- city-end -->
						</select> <select id="select_country">
								<!-- country -->
								<c:forEach items="${country}" var="item">
									<option value="${item.cuitMoonAreaCode}">${item.cuitMoonAreaName}</option>
								</c:forEach>
								<!-- country-end -->
						</select>
						<td class="text_right"><span class="required">*</span>地址</td>
						<td class="content_left"><input type="text" value="${bsm.address }"
							id="text_product_address" name="edit_product_address" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>产品产值</td>
						<td class="content_left"><input type="text"
							id="text_output_value" name="text_output_value" /></td>
						<td class="text_right"><span class="required">*</span>产品产量</td>
						<td class="content_left"><input type="text"
							id="text_output_num" name="text_output_num" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>手机</td>
						<td class="content_left"><input type="text" value="${bsm.mobilePhone }"
							id="text_contact_phone" name="text_contact_phone" /></td>
						<td class="text_right"><span class="required">*</span>申请标签数量</td>
						<td class="content_left"><input type="text"
							id="text_label_num" name="text_label_num" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>产品描述</td>
						<td class="content_left"><textarea
								style="width: 80%; height: 100px" id="text_product_desc"
								name="text_product_desc"></textarea></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<div id="div_third" style="display: block; margin: 10px">
		<div
			style="width: 100%; height: 30px; line-height: 30px; background: #0090d7; padding-left: 10px; margin-top: 10dp; margin-right: 10dp">
			<label><font color="#fff" size="+1.5"><b>已获得的资质、认证、荣誉、注册商标</b></font></label>
		</div>
		<table class="gvtable" style="margin: 10dp">
			<thead style="text-align: center;">
				<tr>
					<th>证书图片</th>
					<th>证书名称</th>
					<th>颁发机构</th>
					<th>获得时间</th>
				</tr>
			<tbody>
				<c:if test="${bsqList.size() <= 0}">
					<tr>
						<td colspan="4" style="margin: 5px">暂无数据</td>
					</tr>
				</c:if>
				<c:forEach items="${bsqList}" var="item">
					<tr>
						<td><img height="50px" width="50px"
							src="<%=path %>${item.picUrl }"></td>
						<td>${item.name }</td>
						<td>${item.publishUnit }</td>
						<td>${item.awardTime }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div
			style="width: 100%; height: 30px; line-height: 30px; background: #0090d7; padding-left: 10px; margin-top: 10dp; margin-right: 10dp">
			<label><font color="#fff" size="+1.5"><b>添加资质、认证</b></font></label>
		</div>
		<div style="float: block; height: auto">
			<div id="imgContainer" style="height: 150px; width: auto"></div>
			<table width="300px">
			<tr>
						<td><span class="required">*</span>上传证书</td>
						<td><img width="120px" height="150px" id="imgTmp"
							style="margin-top: 20px"></td>
					</tr>
				<tr>
					<td><span class="required">*</span>证书名称</td>
					<td><input type="text" id="input_name" /></td>
				</tr>
				<tr>
					<td><span class="required">*</span>颁发部门</td>
					<td><input type="text" id="input_publisher" /></td>
				</tr>
				<tr>
					<td><span class="required">*</span>颁发时间</td>
					<td><input
						onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})"
						type="text" id="input_publish_time" /></td>
				</tr>
				<tr style="text-align: center;height: 35px">
						<td style="padding-top: 3px"><input type="file" name="file" id="uploadImage" /></td>
						<td valign="top"><input type="button"  value="保存证书" onclick="saveCert()" class="btn_blue" style="height: 33px;vertical-align: top;" /></td>
				</tr>
			</table>
			<div id="fileQueue"></div>
		</div>
		<div class="text_right" style="width: 80%; margin-right: 5px">
			<input class="btn_blue" type="button"
				style="width: 100px; height: 30px" value="提交" onclick="validate()" />
			<input class="btn_blue" style="width: 100px; height: 30px"
				type="button" value="取消" onclick="back()" />
		</div>
	</div>
</body>
</html>