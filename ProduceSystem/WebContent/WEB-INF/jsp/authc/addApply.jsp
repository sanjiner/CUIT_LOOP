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
	display: none;
	width: 32px;
	height: 32px;
	background-image: url(<%=basePath%>/res/images/common/Close_Box_Red.gif);
	-moz-opacity: .5;
	filter: alpha(opacity = 100);
}
</style>
<script type="text/javascript">
	function validate1() {
		//输入验证
		var vali = $("#form1").validate({
			rules : {
				edit_company_name : {
					required : true,
					maxlength : 50
				},
				edit_contact_address : {
					required : true
				},
				edit_repr_name : {
					required : true
				},
				edit_company_property : {
					required : true
				},
				edit_contact_person : {
					required : true
				},
				edit_contact_phone : {
					required : true
				},
				edit_cell_phone : {
					required : true
				},
				edit_contact_email : {
					required : true
				},
				chb_type : {
					required : true
				}
			},
			messages : {
				edit_company_name : {
					required : "请输入单位名称",
					maxlength : "单位名称不能超过50个字符"
				},
				edit_contact_address : {
					required : "请输入通讯地址"
				},
				edit_repr_name : {
					required : "请输入法人代表姓名"
				},
				edit_company_property : {
					required : "请输入单位性质"
				},
				edit_contact_person : {
					required : "请输入业务联系人"
				},
				edit_contact_phone : {
					required : "请输入联系电话"
				},
				edit_cell_phone : {
					required : "请输入移动电话"
				},
				edit_contact_email : {
					required : "请输入联系邮箱"
				},
				chb_type : {
					required : "您必须至少选择一项"
				}
			}
		}).form();
		if (vali) {
			step1to2();
			return true;
		} else {
			return false;
		}
	}
	
	
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
					buttonText : '上传照片',
					fileTypeDesc : '支持的格式：',
					fileTypeExts : '*.jpg;*.bmp;*.gif;*.png',
					fileSizeLimit : '2048kb',
					removeTimeout : 1,
					multi : false, //是否支持多文件上传,
					onUploadSuccess : function(file, data, response) {
						alert(data);
						if (data.toString() == 'error') {
							alert('文件上传失败，请重新上传');
						} else {
							//alert(data.toString());
							var json = eval("(" + data + ")");
							//var html = "<div id=\""+json["fileName"]+"\" class=\"wrapper\"  style=\"cursor: pointer;\"><img style=\"width: 120px; height: 120px;margin:5px; background: #c2c2c2; cursor: pointer;\" src=\""+json["filePath"]+"\"/><div onclick=\"deleImg(this)\" class=\"divdelete\" style=\"display: block;\"></div></div>";
							$("#imgTmp").attr("src",json["filePath"]);
							$("#imgTmp").attr("alt",json["fileName"]);
							//$("#imgContainer").append(html);
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
	
	function validate2() {
		var vali = $("#form2").validate({
			rules : {
				select_country : {
					required : true,
					maxlength : 50
				},
				edit_product_area : {
					required : true,
					maxlength : 50
				},
				edit_product_address : {
					required : true
				},
				edit_product_resp : {
					required : true
				},
				edit_product_phone : {
					required : true
				},
				edit_product_cell_phone : {
					required : true
				},
				edit_product_fox_mail : {
					required : true
				},
				getLable : {
					required : true
				},
				edit_product_e_mail : {
					required : true
				}
			},
			messages : {
				select_country : {
					required : "请输入生成基地区域",
					maxlength : "单位名称不能超过50个字符"
				},
				edit_product_area : {
					required : "请输入生成基地区域",
					maxlength : "单位名称不能超过50个字符"
				},
				edit_product_address : {
					required : "请输入生产基地地址"
				},
				edit_product_resp : {
					required : "请输入基地负责人姓名"
				},
				edit_product_phone : {
					required : "请输入基地联系电话"
				},
				edit_product_cell_phone : {
					required : "请输入基地联系人移动电话"
				},
				/* edit_product_fox_mail : {
					required : "请输入传真号码"
				}, */
				getLable : {
					required : "是否申请标签"
				},
				edit_product_e_mail : {
					required : "请输入基地联系邮箱"
				}
			}
		}).form();
		if (vali) {
			step2to3();
			return true;
		} else {
			return false;
		}
	}
	function validate4() {
		var vali = $("#form4").validate({
			rules : {
				select_type : {
					required : true
				},
				edit_produce_name : {
					required : true
				},
				edit_produce_brand : {
					required : true
				},
				edit_produce_quality : {
					required : true
				},
				edit_produce_production : {
					required : true
				},
				edit_produce_output : {
					required : true
				},
				edit_produce_scope : {
					required : true
				},
				edit_produce_spec : {
					required : true
				},
				edit_produce_label_count : {
					required : true,
					digits : true
				},
				labelType : {
					required : true
				},
				edit_produce_cost : {
					required : true
				},
				edit_weather_control_mesure : {
					required : true
				},
				edit_pack_spec : {
					required : true
				},
				edit_place_sitiation : {
					required : true
				} 
			},
			messages : {
				select_type : {
					required :"请输入产品类别" 
				},
				edit_produce_name : {
					required : "请输入产品名称"
				} ,
				edit_produce_brand : {
					required : "请输入农品品牌"
				},
				edit_produce_quality : {
					required : "请输入保质期(天、月、年)"
				},
				edit_produce_production : {
					required : "请输入产量"
				},
				edit_produce_output : {
					required : "请输入产值"
				},
				edit_produce_scope : {
					required : "请输入规模"
				},
				edit_produce_spec : {
					required : "请输入包装规格"
				},
				edit_produce_label_count : {
					required : "请输入标签数量",
					digits : "必须为数字"
				},
				labelType : {
					required : "请选择认证类别"
				},
				edit_produce_cost : {
					required : "请输入费用"
				},
				edit_weather_control_mesure : {
					required : "请输入气候控制措施"
				},
				edit_pack_spec : {
					required : "请输入包装规格"
				},
				edit_place_sitiation : {
					required : "请输入产地概况"
				} 
			}
		}).form();
		if (vali) {
			submit();
			return true;
		} else {
			return false;
		}
	}
	function step1to2() {
		$("#div_first").hide();
		$("#div_second").show();
		$("#node_1").addClass("node_done");
		$("#line_1").addClass("line_done");
	}

	function step2to3() {
		$("#div_second").hide();
		$("#div_third").show();
		$("#node_2").addClass("node_done");
		$("#line_2").addClass("line_done");
	}

	function step3to4() {
		$("#div_third").hide();
		$("#div_forth").show();
		$("#node_3").addClass("node_done");
		$("#line_3").addClass("line_done");
	}

	function step2to1() {
		$("#div_second").hide();
		$("#div_first").show();
		$("#node_1").removeClass("node_done");
		$("#line_1").removeClass("line_done");
	}

	function step3to2() {
		$("#div_third").hide();
		$("#div_second").show();
		$("#node_2").removeClass("node_done");
		$("#line_2").removeClass("line_done");
	}
	function step4to3() {
		$("#div_forth").hide();
		$("#div_third").show();
		$("#node_3").removeClass("node_done");
		$("#line_3").removeClass("line_done");
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
        $("[name='chb_type']:checked").each(function(){
        	type+=$(this).val()+",";
        });
		 $.ajax({
             type: "post",
             url: "<%=basePath%>/authc/addApply",
             dataType: "html",
             data: { edit_company_name : $("#edit_company_name").val(),
            	 edit_contact_address  : $("#edit_contact_address").val(),
            	 edit_repr_name  : $("#edit_repr_name").val(),
            	 edit_company_property  : $("#edit_company_property").val(),
            	 edit_contact_person  : $("#edit_contact_person").val(),
            	 edit_contact_phone  : $("#edit_contact_phone").val(),
            	 edit_cell_phone  : $("#edit_cell_phone").val(),
            	 edit_contact_email  : $("#edit_contact_email").val(),
            	 chb_type  : type,
            	 edit_product_area  : $("#select_pro option:selected").val() + "|" + $("#select_city option:selected").val()+"|" + $("#select_country option:selected").val(),
            	 edit_product_address  : $("#edit_product_address").val(),
            	 edit_product_resp  : $("#edit_product_resp").val(),
            	 edit_product_phone  : $("#edit_product_phone").val(),
            	 edit_product_cell_phone  : $("#edit_product_cell_phone").val(),
            	 edit_product_fox_mail  : $("#edit_product_fox_mail").val(),
            	 getLable  : $("input[name='getLable']:checked").val(),
            	 edit_product_e_mail  : $("#edit_product_e_mail").val(),
            	 
            	 select_type  : $("#select_type option:selected").val(),
            	 edit_produce_name  : $("#edit_produce_name").val(),
            	 edit_produce_brand  : $("#edit_produce_brand").val(),
            	 edit_produce_quality  : $("#edit_produce_quality").val(),
            	 edit_produce_production  : $("#edit_produce_production").val(),
            	 edit_produce_output  : $("#edit_produce_output").val(),
            	 edit_produce_scope  : $("#edit_produce_scope").val(),
            	 edit_produce_spec  : $("#edit_produce_spec").val(),
            	 edit_produce_label_count  : $("#edit_produce_label_count").val(),
            	 labelType  : $("input[name='labelType']:checked").val(),
            	 edit_produce_cost  : $("#edit_produce_cost").val(),
            	 edit_weather_control_mesure   : $("#edit_weather_control_mesure").val(),
            	 edit_product_descipt   : $("#edit_product_descipt").val(),
            	 edit_place_sitiation   : $("#edit_place_sitiation").val(),
            	 cerIds : $("#cerIds").val(),
            	 imags : imags
            	 },
             success: function (data) {
            	 var obj = eval('(' + data + ')');
            	 if(obj.success == "true"){
            		 alert("添加成功");
            		 window.location.href="<%=basePath%>/authc/showApply";
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
		window.location.href = "<%=basePath%>authc/showApply";
	}

	function deleteCer(elemId) {
		$("#" + elemId).remove();
		var cerIds = $("#cerIds").val();
		var idsStr = "";
		var strs = new Array(); //定义一数组 
		strs = cerIds.split(","); //字符分割 
		for (i = 0; i < strs.length; i++) {
			if ((elemId != strs[i]) && (strs[i].length > 0)) {
				idsStr = idsStr + "," + strs[i];
			}
		}

		$("#cerIds").val(idsStr);
	}
</script>
</head>
<body style="background: #edf1fc">
	<table id="tb_indicator" align="center" width="80%" height="30px">
		<tbody>
			<tr>
				<td width="30dp"><div class="node_tbd" id="node_1">
						<span
							style="height: 30px; line-height: 30px; display: block; color: #fff; text-align: center"><font
							size="5px">1</font></span>
					</div></td>
				<td><div class="line_tbd" id="line_1"></div></td>
				<td width="30dp"><div class="node_tbd" id="node_2">
						<span
							style="height: 30px; line-height: 30px; display: block; color: #fff; text-align: center"><font
							size="5px">2</font></span>
					</div></td>
				<td><div class="line_tbd" id="line_2"></div></td>
				<td width="30dp"><div class="node_tbd" id="node_3">
						<span
							style="height: 30px; line-height: 30px; display: block; color: #fff; text-align: center"><font
							size="5px">3</font></span>
					</div></td>
				<td><div class="line_tbd" id="line_3"></div></td>
				<td width="30dp"><div class="node_tbd" id="node_4">
						<span
							style="height: 30px; line-height: 30px; display: block; color: #fff; text-align: center"><font
							size="5px">4</font></span>
					</div></td>
			</tr>
		</tbody>
	</table>
	<form id="form1">
		<div id="div_first">
			<label style="margin-left: 10px"><font size="+1.5px">企业信息</font></label>
			<table width="90%">
				<tbody>
					<tr>
						<td class="text_right"><span class="required">*</span>单位名称</td>
						<td class="content_left"><input id="edit_company_name"
							name="edit_company_name" type="text" value=${bsm.campanyName }></td>
						<td class="text_right"><span class="required">*</span>通讯地址</td>
						<td class="content_left"><input id="edit_contact_address"
							name="edit_contact_address" type="text" value=${bsm.address }></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>法人代表</td>
						<td class="content_left"><input type="text"
							id="edit_repr_name" name="edit_repr_name"
							value=${bsm.legalPerson }></td>
						<td class="text_right"><span class="required">*</span>单位性质</td>
						<td class="content_left"><input type="text"
							id="edit_company_property" name="edit_company_property"
							value=${bsm.campanyType }></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>业务联系人</td>
						<td class="content_left"><input type="text"
							id="edit_contact_person" name="edit_contact_person"
							value=${bsm.cantactPerson }></td>
						<td class="text_right"><span class="required">*</span>业务电话</td>
						<td class="content_left"><input type="text"
							id="edit_contact_phone" name="edit_contact_phone"
							value=${bsm.tel }></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>手机</td>
						<td class="content_left"><input type="text"
							id="edit_cell_phone" name="edit_cell_phone"
							value=${bsm.mobilePhone }></td>
						<td class="text_right"><span class="required">*</span>商家邮箱</td>
						<td class="content_left"><input type="text"
							id="edit_contact_email" name="edit_contact_email"
							value=${bsm.mailBox }></td>
					</tr>
					<tr>
						<td class="text_right" style="width: auto;"><span
							class="required">*</span>申请人类型</td>
						<td class="content_left" style="text-align: left;"><input
							type="checkbox" id="chb_self_done" name="chb_type"
							value="1000171" style="width: auto; margin-left: 5px">自产自销型
							<input type="checkbox" id="chb_company_farmer" name="chb_type"
							value="1000172" style="width: auto; margin-left: 5px">公司加农户型
							<input type="checkbox" id="chb_united_farmer" name="chb_type"
							value="1000173" style="width: auto; margin-left: 5px">农业合作组织
							<input type="checkbox" id="chb_other" name="chb_type"
							value="1000174" style="width: auto; margin-left: 5px">其他</td>
					</tr>
				</tbody>
			</table>
			<div class="text_right" style="width: 80%; margin-right: 5px">
				<input type="button" style="width: 100px; height: 30px"
					class="btn_blue" value="取消" onClick="back()" /> <input
					type="button" style="width: 100px; height: 30px" class="btn_blue"
					onClick="validate1();" value="下一步" />
			</div>
		</div>
	</form>
	<form id="form2">
		<div id="div_second" style="display: none;">
			<label style="margin-left: 10px"><font size="+1.5px">产地信息</font></label>
			<table width="90%">
				<tbody>
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
						</select> <select id="select_country" name="select_country">
								<!-- country -->
								<c:forEach items="${country}" var="item">
									<option value="${item.cuitMoonAreaCode}">${item.cuitMoonAreaName}</option>
								</c:forEach>
								<!-- country-end -->
						</select>
						<td class="text_right"><span class="required">*</span>地址</td>
						<td class="content_left"><input type="text"
							id="edit_product_address" name="edit_product_address" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>产地负责人</td>
						<td class="content_left"><input type="text"
							id="edit_product_resp" name="edit_product_resp" /></td>
						<td class="text_right"><span class="required">*</span>生产基地联系电话</td>
						<td class="content_left"><input type="text"
							id="edit_product_phone" name="edit_product_phone" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>手机</td>
						<td class="content_left"><input type="text"
							id="edit_product_cell_phone" name="edit_product_cell_phone" /></td>
						<!-- <td class="text_right"><span class="required">*</span>传真</td>
						<td class="content_left"><input type="text"
							id="edit_product_fox_mail" name="edit_product_fox_mail" /></td> -->
							<td class="text_right"><span class="required">*</span>E_MAIL</td>
						<td class="content_left"><input type="text"
							id="edit_product_e_mail" name="edit_product_e_mail" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>是否申请标签</td>
						<td class="content_left"><input type="radio" value="100011"
							name="getLable" style="float: left; width: auto;">是 <input
							type="radio" value="100012" name="getLable" style="width: auto;">否</td>
						<!-- <td class="text_right"><span class="required">*</span>E_MAIL</td>
						<td class="content_left"><input type="text"
							id="edit_product_e_mail" name="edit_product_e_mail" /></td> -->
					</tr>
				</tbody>
			</table>
			<div class="text_right" style="width: 80%; margin-right: 5px">
				<input type="button" style="width: 100px; height: 30px"
					class="btn_blue" value="上一步" onclick="step2to1()"> <input
					type="button" class="btn_blue" style="width: 100px; height: 30px"
					value="下一步" onclick="return validate2()">
			</div>
		</div>
	</form>
	<form id="form3">
		<div id="div_third" style="display: none;">
						<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
			<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">已获得资质</font></label>
		</div>

			<table class="gvtable" style="margin: 10dp">
				<thead style="text-align: center;">
					<tr>
						<th>证书图片</th>
						<th>证书名称</th>
						<th>颁发机构</th>
						<th>获得时间</th>
						<th>操作</th>
					</tr>
				<tbody>
					<c:if test="${bsqList.size() <= 0}">
						<tr>
							<td colspan="4" style="margin: 5px">暂无数据</td>
						</tr>
					</c:if>
					<c:set var="cerIdsValue" value="" />
					<c:forEach items="${bsq}" var="item">
						<tr id="${item.businessId}">
							<td><img height="50px" width="50px"
								src="<%=path %>${item.picUrl }"></td>
							<td>${item.name }</td>
							<td>${item.publishUnit }</td>
							<td>${item.awardTime }</td>
							<td><a href="javascript:void(0)"
								onclick="deleteCer('${item.businessId}')">删除</a></td>
						</tr>
						<c:set var="cerIdsValue"
							value="${cerIdsValue}${','}${item.businessId }" />
					</c:forEach>
					<input id="cerIds" name="cerIds" type="hidden"
						value="${cerIdsValue }" />
				</tbody>
			</table>
			<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
			<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">新增资质</font></label>
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
				<div class="text_right" style="width: 80%; margin-right: 5px">
					<input class="btn_blue" style="width: 100px; height: 30px"
						type="button" value="上一步" onclick="step3to2()" /> <input
						class="btn_blue" type="button" style="width: 100px; height: 30px"
						value="下一步" onclick="step3to4()" />
				</div>
			</div>
		</div>
	</form>
	<form id="form4">
		<div id="div_forth" style="display: none">
			<label style="margin-left: 10px"><font size="+1.5px">申报产品情况</font></label>
			<table width="90%">
				<tbody>
					<tr>
						<td class="text_right"><span class="required">*</span>产品类别</td>
						<td class="content_left"><select id="select_type"
							name="select_type" style="width: 120px">
								<c:forEach items="${dicType}" var="dicItem">
									<option value="${dicItem.cuitMoonDictionaryCode}">≡${ dicItem.cuitMoonDictionaryName}</option>
									<c:forEach items="${pfType}" var="pfItem">
										<c:if
											test="${pfItem.productType.trim().equals(dicItem.cuitMoonDictionaryCode.toString())}">
											<option value="${ pfItem.productNumber}">└ ${ pfItem.productName}</option>
										</c:if>
									</c:forEach>
								</c:forEach>
						</select></td>
						<td class="text_right"><span class="required">*</span>产品名称</td>
						<td class="content_left"><input type="text"
							id="edit_produce_name" name="edit_produce_name" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>农产品品牌</td>
						<td class="content_left"><input type="text"
							id="edit_produce_brand" name="edit_produce_brand" /></td>
						<td class="text_right"><span class="required">*</span>保质期</td>
						<td class="content_left"><input type="text"
							id="edit_produce_quality" name="edit_produce_quality" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>产量</td>
						<td class="content_left"><input type="text"
							id="edit_produce_production" name="edit_produce_production" /></td>
						<td class="text_right"><span class="required">*</span>产值</td>
						<td class="content_left"><input type="text"
							id="edit_produce_output" name="edit_produce_output" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>规模</td>
						<td class="content_left"><input type="text"
							id="edit_produce_scope" name="edit_produce_scope" /></td>
						<td class="text_right"><span class="required">*</span>包装规格</td>
						<td class="content_left"><input type="text"
							id="edit_produce_spec" name="edit_produce_spec" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>标签数量</td>
						<td class="content_left"><input type="text"
							id="edit_produce_label_count" name="edit_produce_label_count" /></td>
					</tr>
					<tr>
						<td class="text_right"><span class="required">*</span>申请类别</td>
						<td class="content_left"><input type="radio" value="1000161"
							name="labelType" style="float: left; width: auto;">气候品质认证
							<!-- <input type="radio" value="1000162" name="labelType"
							style="width: auto;">纯气候品质认证 --></td>
						<td class="text_right">费用</td>
						<td class="content_left"><input type="text"
							readonly="readonly" value="暂不收费" id="edit_produce_cost"
							name="edit_produce_cost" /></td>
					</tr>
					<tr height="100px">
						<td class="text_right"><span class="required">*</span>气候控制措施</td>
						<td height="100px" class="content_left"><textarea
								style="height: 100px; width: 80%;"
								id="edit_weather_control_mesure"
								name="edit_weather_control_mesure"></textarea></td>
						<td class="text_right"><span class="required">*</span>产品特征、特性描述</td>
						<td height="100px" class="content_left"><textarea
								style="height: 100px; width: 80%;" id="edit_product_descipt"
								name="edit_product_descipt"></textarea></td>
					</tr>
					<tr height="100px">
						<td class="text_right"><span class="required">*</span>产地概况</td>
						<td height="100px" class="content_left"><textarea
								style="height: 100px; width: 80%;" id="edit_place_sitiation"
								name="edit_place_sitiation"></textarea></td>
					</tr>
				</tbody>
			</table>
			<div class="text_right"
				style="width: 80%; margin-right: 5px; margin-top: 10px">
				<input class="btn_blue" style="width: 100px; height: 30px"
					type="button" value="上一步" onclick="step4to3()" /> <input
					class="btn_blue" style="width: 100px; height: 30px" type="button"
					onclick="validate4()" value="完成" />
			</div>
		</div>
	</form>
</body>
</html>