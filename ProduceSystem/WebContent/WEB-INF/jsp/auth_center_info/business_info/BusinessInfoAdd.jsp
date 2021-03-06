<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>添加商家</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"/>
<link href="<%=basePath%>res/js/jquery.uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>res/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>res/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=basePath%>res/js/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>res/js/jquery.uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
<script src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" language="javascript">
	var logo = ""; //注册商标
	var certifyImage = "";
	var certifyImagePath = "";
	var certifyDataArray = new Array();
	$(document).ready(function(){
		//////商标上传
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
			fileSizeLimit : '10240KB',
			removeTimeout : 1,
			multi : false, //是否支持多文件上传
			onUploadSuccess : function(file, data, response) {
				if (data.toString() == 'error') {
					alert('文件上传失败，请重新上传');
				} else {
					//alert(data.toString());
					var json = eval("(" + data + ")");
					logo = json["fileName"];
					$('#logo').attr('src', json["filePath"]);
				}
			}
		});
		
  		//////资质上传
        $("#uploadCertify").uploadify({
            debug: false,
            method: 'post',
            height: 30,
            swf: '<%=basePath%>res/js/jquery.uploadify/uploadify.swf',
            uploader: '<%=basePath%>auth_center_info/expert_info/file',
			//文件选择后的容器ID  
			queueID : 'fileQueueCertify',
			width : 120,
			auto : true,
			buttonText : '上传照片',
			fileTypeDesc : '支持的格式：',
			fileTypeExts : '*.jpg;*.bmp;*.gif;*.png',
			fileSizeLimit : '10240KB',
			removeTimeout : 1,
			multi : false, //是否支持多文件上传
			onUploadSuccess : function(file, data, response) {
				if (data.toString() == 'error') {
					alert('文件上传失败，请重新上传');
				} else {
					//alert(data.toString());
					var json = eval("(" + data + ")");
					//var htmlString = "<div style = \"width:33%; height:100%;float:left;background-color:blue\"><img style = \"width:50%; height:100%; float:left; background-color:gray\" src =\""+json["filePath"]+"\" /><span style = \"width:50%; height:30%;\">证书名称："+$("#certificateName").val()+"</span><span style = \"width:50%; height:30%;\">颁发机构："+$("#awardDepart").val()+"</span><span style = \"width:50%; height:30%;\">颁发时间："+$("#getTime").val()+"</span></div>";
					certifyImage = json["fileName"];
					certifyImagePath = json["filePath"];
					$('#businessCertifyImage').attr('src', json["filePath"]);
				}
			}
		});
	});	
	
	Array.prototype.remove=function(dx) 
	{ 
	    if(isNaN(dx)||dx>this.length){return false;} 
	    for(var i=0,n=0;i<this.length;i++) 
	    { 
	        if(this[i]!=this[dx]) 
	        { 
	            this[n++]=this[i] 
	        } 
	    } 
	    this.length-=1 
	} 
	
	function saveCertifyToServer(){
		if(certifyImage.length == 0){
			alert("请上传证书文件");
			return false;
		}
		if($("#certificateName").val().length == 0){
			alert("请输入证书名称");
			return false;
		}
		if($("#awardDepart").val().length == 0){
			alert("请输入证书的颁发机构");
			return false;
		}
		if($("#getTime").val().length == 0){
			alert("请选择证书颁发时间");
			return false;
		}
		var jsonStr = "{\"certificateName\":\""+$("#certificateName").val()+"\",\"getTime\":\""+$("#getTime").val()+"\",\"awardDepart\":\""+$("#awardDepart").val()+"\",\"certifyImage\":\""+certifyImage+"\"}";
		//var htmlString = "<div class=\"wrapper\" style = \"width:33%; height:100%;float:left\"><img style = \"width:50%; height:100%; float:left;\" src =\""+certifyImagePath+"\" /><span style = \"width:50%; height:30%;\">证书名称："+$("#certificateName").val()+"</span><br/><span style = \"width:50%; height:30%;\">颁发机构："+$("#awardDepart").val()+"</span><br/><span style = \"width:50%; height:10%;\">颁发时间：</span><br/><span style = \"width:50%; height:30%;\">"+$("#getTime").val()+"</span><div id=\""+certifyDataArray.length+"\" onclick=\"deleLocationImg(this)\" class=\"divdelete\" style=\"display: block;\"></div></div>";
		var htmlString = "<div class=\"wrapper\" style = \"width:33%; height:100%;float:left\"><img style = \"width:100%; height:100%; float:left;\" src =\""+certifyImagePath+"\" /><div id=\""+certifyDataArray.length+"\" onclick=\"deleLocationImg(this)\" class=\"divdelete\" style=\"display: block;\"></div></div>";
		certifyDataArray.push(jsonStr);
		certifyImage = "";
		$('#certifyDiv').append(htmlString);
		$("#certificateName").val("");
		$("#awardDepart").val("");
		$("#getTime").val("");
		$('#businessCertifyImage').attr('src', "");
	}
	
	function deleLocationImg(btnDel){
		certifyDataArray.remove($(btnDel).attr("id"));		
		$(btnDel).parent().remove();
	}
	
	function validateInfo() {
		//输入验证
        return $("#form1").validate({
            rules: {
            	Username: { required: true, maxlength: 50 },
            	Password: { maxlength: 50, required: true },
                ConfirmPassword: { equalTo: "#password", required: true},
            	campanyName: { maxlength: 100, required: true },
            	legalPerson: { maxlength: 50, required: true },
            	legalPresentCode: { maxlength: 20, required: true },
            	address: { maxlength: 100 },
            	cantactPerson: { maxlength: 50 },
            	tel: { Telphone: true },
            	mobilePhone: { Mobile: true},
                fax: { maxlength: 20, Fax: true },
                mailBox: { maxlength: 20, Email: true }
            },
            messages: {
            	Username: { required: "请输入用户名", maxlength: "用户名不能超过50个字" },
            	Password: { required: "请输入密码", maxlength: "密码不能超过50个字符" },
                ConfirmPassword: { equalTo: "密码不一致", required: "请再次输入密码"},
            	campanyName: { required: "请输入单位名称", maxlength: " 单位名称不能超过100字符" },
            	legalPerson: { required: "请输入法人代表", maxlength: " 法人代表不能超过50字符" },
            	legalPresentCode: { required: "请输入法人代表码", maxlength: " 法人代表不能超过20字符" },
            	address: { maxlength: " 通讯地址不能超过100字符" },
            	cantactPerson: { maxlength: " 业务联系人不能超过50字符" },
            	tel: { Telphone: "请输入一个正确的电话号码" },
            	mobilePhone: { Mobile: "请输入一个正确的手机号码" },
            	fax: { maxlength: "传真不能超过20个字符", Fax: "请输入一个标准的传真" },
            	mailBox: { maxlength: "邮箱不能超过20个字符", Email: "请输入正确的邮箱" }
            }
        }).form();
    }

	//////选择省市区
	function changeSelect(value, nextObjc) {
		if(value != 0){
			var url = "<%=basePath%>auth_center_info/business_info/dictionary?code="+ value;
			$.ajax({
				type : "get",
				url : url,
				dataType : "html",
				data : "",
				success : function(data) {
					var json = eval("(" + data +")");
					var list = json["list"];
					var optionHtml = "<option value=\"0\">--请选择--</option>";
					for(var i = 0; i < list.length; i++){
						optionHtml += "<option value="+list[i].cuitMoonAreaCode+">"+list[i].cuitMoonAreaName+"</option>";
					}
					nextObjc.html(optionHtml);
				},
				cache : false,
				error : function(msg) {
					alert(msg.responseText);
				}
			});
		}
	}
	
	////////提交编辑之后的数据
	function submitToServer(){
		//if(validateInfo()){
			//String id, String userName, String companyType, String area, String companyName, String legalPerson, String address
			//	, String cantactPerson, String enterpriseType, String shopType
			// String certificateName, String awardDepart, String certifyImage, String getTime
           	if($("#userName").val().length == 0){
           		alert("请输入用户名");
           		return false;
           	}
           	if($("#password").val().length == 0){
           		alert("请输入密码");
           		return false;
           	}
           	if($("#password").val() != $("#check_password").val()){
           		alert("两次输入的密码不一致");
           		return false;
           	}
           	if($("#campanyName").val().length == 0){
           		alert("请输入单位名称");
           		return false;
           	}
           	if($("#legalPerson").val().length == 0){
           		alert("请输入法人代表");
           		return false;
           	}
           	if($("#legalPresentCode").val().length == 0){
           		alert("请输入法人代表码");
           		return false;
           	}
           	if($("#tel").val().length > 0 && !(/^\d{3,4}-\d{6,9}(\(\d{6,9}\))*$/.test($("#tel").val()) || /^1\d{10}$/.test($("#tel").val()))){
           		alert("请输入标准的电话号码");
           		return false;
           	}
           	if($("#mobilePhone").val().length > 0 && !( /^1\d{10}$/.test($("#mobilePhone").val()))){
           		alert("请输入一个正确的手机号码");
           		return false;
           	}
           	if($("#fax").val().length > 0 && !(/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/.test($("#fax").val()))){
           		alert("请输入一个标准的传真");
           		return false;
           	}
           	if($("#mailBox").val().length > 0 && !( /^([a-zA-Z0-9_-]){1,}@([a-zA-Z0-9_-]){1,}((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#mailBox").val()))){
           		alert("请输入正确的邮箱");
           		return false;
           	}
           	
			var area = "";
			if($("#provinceSelect").find("option:selected").val() != 0 && $("#citySelect").find("option:selected").val() != 0 && $("#countySelect").find("option:selected").val() != 0){
				area = $("#provinceSelect").find("option:selected").val() +"|" + $("#citySelect").find("option:selected").val() +"|" + $("#countySelect").find("option:selected").val();
				var jsonStr = "{\"DataArray\":["+certifyDataArray.join(',')+"]}";
				//alert(jsonStr);
				var url = "<%=basePath%>auth_center_info/business_info/add";
				$.ajax({
					type : "post",
					url : url,
					dataType : "html",
					data : {'userName': $("#userName").val(),'password': $("#password").val(),'companyName': $("#campanyName").val(),'companyType': $("#companyType").find("option:selected").val(),'enterpriseType': $("#enterpriseType").find("option:selected").val(),'shopType': $("#shopType").find("option:selected").val(),'area': area,'legalPerson':$("#legalPerson").val(),'legalPresentCode':$("#legalPresentCode").val(),'cantactPerson':$("#cantactPerson").val(),'tel':$("#tel").val(),'mobilePhone':$("#mobilePhone").val(),'fax':$("#fax").val(),'mailBox':$("#mailBox").val(),'address':$("#address").val(),'logo':logo,'certificateName':$("#certificateName").val(),'awardDepart':$("#awardDepart").val(),'certifyImage': certifyImage,'getTime':$("#getTime").val(),'json':jsonStr,'remarks':$("#remarks").text()},
					success : function(data) {
						if(data == "T"){
							window.location.href = "<%=basePath%>auth_center_info/business_info/list";
						}
						else if(data == "E"){
							alert("用户名“"+$("#userName").val()+"”已存在，请重新输入。");
						}
						else{
							alert("系统错误");
						}
					},
					cache : false,
					error : function(msg) {
						alert(msg.responseText);
					}
				});
			}
			else{
				alert("请选择商家所在地区");
			}
		//}
	}
	
	function back(){
		window.location.href = "<%=basePath%>auth_center_info/business_info/list";
	}
</script>
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
	left: 0;
	z-index: 1;
	display: none;
	width: 32px;
	height: 32px;
	background-image: url("<%=basePath%>/res/images/common/Close_Box_Red.gif");
	-moz-opacity: .5;
	filter: alpha(opacity = 100);
}
</style>
</head>
<body>
	<form name="form1" id = "form1" method="post">
		<table class="info" cellpadding="0" cellspacing="0">
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>用户名称</td>
				<td class="value"><input type="text" id="userName" name = "Username"  class="width60per" /> </td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>登录密码</td>
				<td class="value"><input type="password" id="password" name="Password"  class="width60per"/></td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>确认密码</td>
				<td class="value"><input type="password" id="check_password" name="ConfirmPassword"  class="width60per"/></td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>单位名称</td>
				<td class="value"><input type="text" id="campanyName" name="campanyName"  class="width60per" /></td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>单位性质</td>
				<td class="value"><select id="companyType"  class="width60per">
						<c:forEach var="companyTypeVar" items="${companyTypeList}">
							<option value="${companyTypeVar.getCuitMoonDictionaryCode()}">${companyTypeVar.getCuitMoonDictionaryName()}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>企业类型</td>
				<td class="value"><select id="enterpriseType"  class="width60per">
						<c:forEach var="enterpriseTypeVar" items="${enterpriseTypeList}">
							<option value="${enterpriseTypeVar.getCuitMoonDictionaryCode()}">${enterpriseTypeVar.getCuitMoonDictionaryName()}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>商家类型</td>
				<td class="value"><select id="shopType"  class="width60per">
						<c:forEach var="shopTypeVar" items="${shopTypeList}">
							<option value="${shopTypeVar.getCuitMoonDictionaryCode()}">${shopTypeVar.getCuitMoonDictionaryName()}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>商家所在地区</td>
				<td class="value"> <select id="provinceSelect"  class="width25per"
					onchange="changeSelect(this.value,$('#citySelect'));">
						<option value="0">请先选择</option>
						<c:forEach var="area" items="${provinceList}">
							<option value="${area.getCuitMoonAreaCode()}">${area.getCuitMoonAreaName()}</option>
						</c:forEach>
				</select>  <select id="citySelect"  class="width25per" name="city"
					onchange="changeSelect(this.value,$('#countySelect'));">
						<option value="0">请先选择省级</option>
				</select>  <select id="countySelect"  class="width25per" name="county">
						<option value="0">请先选择县级</option>
				</select>
				</td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>法人代表</td>
				<td class="value"><input type="text" id="legalPerson" name = "legalPerson"  class="width60per" /></td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>法人代表码</td>
				<td class="value"><input type="text" id="legalPresentCode" name="legalPresentCode"  class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">通讯地址</td>
				<td class="value"><input type="text" id="address" name="address"  class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">业务联系人</td>
				<td class="value"><input type="text" id="cantactPerson" name="cantactPerson"  class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">联系电话</td>
				<td class="value"><input type="text" id="tel" name="tel"  class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">手机</td>
				<td class="value"><input type="text" id="mobilePhone" name="mobilePhone"  class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">传真</td>
				<td class="value"><input type="text" id="fax" name="fax"  class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">企业邮箱</td>
				<td class="value"><input type="text" id="mailBox" name="mailBox"  class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">注册商标</td>
				<td class="value"><img id="logo"
					style="width: 120px; height: 120px; background: #c2c2c2; cursor: pointer;"
					src="" />
					<div>
						<div style="float: left; width: 125px;">
							<input type="file" name="file" id="uploadImage" />
						</div>
					</div>
					<div id="fileQueue"></div></td>
			</tr>
			<tr>
				<td class="text">商家简介</td>
				<td class="value">
					<textarea id="remarks" name="remarks" rows="8" cols="25"></textarea>
				</td>
			</tr>
			<tr>
				<td class="text"><hr /></td>
				<td class="value"><hr /></td>
			</tr>
			<tr>
				<td class="text">已上传的证书</td>
				<td class="value">
					<div id = "certifyDiv" style = "width:100%; height:120px; overflow:auto">
						
					</div>
				</td>
			</tr>
			<tr>
				<td class="text">上传证书</td>
				<td class="value">
					<img id="businessCertifyImage" style="width: 120px; height: 120px; background: #c2c2c2; cursor: pointer;" src="" />
					<div>
						<div style="float: left; width: 125px;">
							<input type="file" name="file" id="uploadCertify" />
						</div>
					</div>
					<div id="fileQueueCertify"></div>
				</td>
			</tr>
			<tr>
				<td class="text">证书名称</td>
				<td class="value"><input type="text" id="certificateName"  class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">颁发部门</td>
				<td class="value"><input type="text" id="awardDepart"  class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">颁发时间</td>
				<td class="value"><input  class="width60per" type="text" id="getTime" name="getTime" onfocus="WdatePicker()"></td>
			</tr>
			<tr>
				<td class="text">&nbsp;</td>
				<td class="value"><input type="button" value="保存证书" class="btn_blue" onclick="saveCertifyToServer()"></td>
			</tr>
			<tr>
				<td class="text"><hr /></td>
				<td class="value"><hr /></td>
			</tr>
			<tr>
				<td class="text">&nbsp;</td>
				<td class="value"><input type="button" value="提交" class="btn_blue" onclick="submitToServer()">&nbsp;&nbsp;&nbsp;
				<input type="button" value="返回" class="btn_blue" onclick="back()"></td>
			</tr>
		</table>
	</form>
</body>
</html>