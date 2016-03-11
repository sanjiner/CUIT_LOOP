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
<title>添加专家</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"/>
<script src="<%=basePath%>res/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>res/js/jquery.validate.min.js" type="text/javascript"></script>
<link href="<%=basePath%>res/js/jquery.uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>res/js/jquery.uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
<script src="<%=basePath%>res/js/validate.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	var photo = ""; //专家头像
	
	//输入验证
    function validateInfo() {
        return $("#form1").validate({
            rules: {
                Username: { maxlength: 50, required: true },
                Password: { maxlength: 50, required: true },
                ConfirmPassword: { equalTo: "#password", required: true},
                ExpertName: { maxlength: 50, required: true },
                ID: { maxlength: 18, Identity: true },
                WorkUnits: { maxlength: 50 },
                School: { maxlength: 100 },
                Education: { maxlength: 32 },
                TEL: { maxlength: 20, Mobile: true },
                QQ: { maxlength: 20, QQ: true },
                Email: { email: true, maxlength: 20 },
                PostCode: { maxlength: 6, Zipcode: true },
                Address: { maxlength: 100 },
                ExpertIntroduction: { maxlength: 200 },
                Remark: { maxlength: 200 }
            },
            messages: {
                Username: { required: "请输入用户名称", maxlength: "用户名称不能超过50个字符" },
                Password: { required: "请输入密码", maxlength: "密码不能超过50个字符" },
                ConfirmPassword: { equalTo: "密码不一致", required: "请再次输入密码"},
                ExpertName: { required: "请输入专家姓名名称", maxlength: "专家姓名不能超过50个字符" },
                ID: { maxlength: "身份证号码不能超过18个字符", Identity: "请输入正确的身份证号" },
                WorkUnits: { maxlength: "工作单位不能超过50个字符" },
                School: { maxlength: "毕业学校不能超过100个字符" },
                Education: { maxlength: "学历不能超过32个字符" },
                TEL: { maxlength: "电话号码不能超过20个字符", Mobile: "请输入一个正确的手机号码" },
                QQ: { maxlength: "QQ号码不能超过20个字符", QQ: "请输入正确的QQ号" },
                Email: { email: "请输入有效的邮箱地址", maxlength: "Email邮箱不能超过20个字符" },
                PostCode: { maxlength: "邮政编码不能超过6个字符", Zipcode: "请输入一个正确的邮编" },
                Address: { maxlength: "通讯地址不能超过100个字符" },
                ExpertIntroduction: { maxlength: "专家介绍不能超过200个字符" },
                Remark: { maxlength: "备注信息不能超过200个字符" }
            }
        }).form();
    }
	
	////////添加数据
	function submitToServer(){
		if(validateInfo()){
			//String userName, String password, String expertname, String expertLevel, 
			//String expertCategory,  String belongToMeteorology
			// String idnumber, String workUnits, String schools, String tel, 
			// String educationalBackground
			// String mailBox, String address, String postCode, String qq, 
			//String expertIntroduction,  String remark
			if($("#password").val() == $("#check_password").val()){
				var expertCategory = ""; //专家类别
				$("input[name='ecChechbox']").each(function(){
		  			if($(this).is(':checked')){
		  				expertCategory += $(this).val() + "|";
		  			}
		 		 }); 
				if(expertCategory.length > 0){
					var url = "<%=basePath%>auth_center_info/expert_info/add";
					$.ajax({
						type : "post",
						url : url,
						dataType : "html",
						data : { 'userName': $("#userName").val(),'password': $("#password").val(),'expertname': $("#expertname").val(),'expertLevel': $("#expertLevel").text(),'expertCategory': expertCategory,'belongToMeteorology': $("#belongToMeteorologyCode").val(),'idnumber': $("#idnumber").val(),'workUnits':$("#workUnits").val(),'schools':$("#schools").val(),'tel':$("#tel").val(),'educationalBackground':$("#educationalBackground").val(),'mailBox':$("#mailBox").val(),'address':$("#address").val(),'postCode':$("#postCode").val(),'qq':$("#qq").val(),'expertIntroduction':$("#expertIntroduction").val(),'remark':$("#remark").val(),'photo':photo},
						success : function(data) {
							if(data == "T"){
								window.location.href = "<%=basePath%>auth_center_info/expert_info/list";
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
					alert("请选择专家类别");
				}
			}
			else{
				alert("两次密码输入不一致，请重新输入密码");
			}
		}
	}
	
	function back(){
		window.location.href = "<%=basePath%>auth_center_info/expert_info/list";
	}
</script>
<style type="text/css">
.error {
	color: red;
}
</style>
</head>
<body>
	<form name="form1" id = "form1" method="post">
		<table class="info" cellpadding="0" cellspacing="0">
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>用户名称</td>
				<td class="value"><input type="text" id="userName" name="Username" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>登录密码</td>
				<td class="value"><input type="password" id="password" name="Password" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>确认密码</td>
				<td class="value"><input type="password" id="check_password" name="ConfirmPassword" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>专家名称</td>
				<td class="value"><input type="text" id="expertname" name="ExpertName" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">专家层次</td>
				<td><span id="expertLevel" >${expertLevel }</span></td>
			</tr>
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>专家类别</td>
				<td class="value"><c:forEach var="expertCategoryVar"
						items="${expertCategoryList}">
						<p>
							<input  type="checkbox" name="ecChechbox"
								value="${expertCategoryVar.getCuitMoonDictionaryCode()}">${expertCategoryVar.getCuitMoonDictionaryName()}</p>
					</c:forEach></td>
			</tr>
			<tr>
				<td class="text">专家所属气象局</td>
				<td class="value">
				<input type="hidden" id="belongToMeteorologyCode" value = "${belongToMeteorologyCode }" />
				<span id="belongToMeteorology" class="width60per" >${belongToMeteorology }</span></td>
			</tr>
			<tr>
				<td class="text">身份证号码</td>
				<td class="value"><input type="text" id="idnumber" name="ID" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">工作单位</td>
				<td class="value"><input type="text" id="workUnits" name="WorkUnits" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">毕业学校</td>
				<td class="value"><input type="text" id="schools" name="School" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">手机号码</td>
				<td class="value"><input type="text" id="tel" name="TEL" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">学历</td>
				<td class="value"><input type="text" id="educationalBackground" name="Education" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">Email</td>
				<td class="value"><input type="text" id="mailBox" name="Email" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">联系地址</td>
				<td class="value"><input type="text" id="address" name="Address" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">邮编</td>
				<td class="value"><input type="text" id="postCode" name="PostCode" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">QQ</td>
				<td class="value"><input type="text" id="qq" name="QQ" class="width60per" /></td>
			</tr>
			<tr>
				<td class="text">照片</td>
				<td class="value"><img id="photo"
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
				<td class="text">专家简介</td>
				<td class="value"><input type="text" id="expertIntroduction" class="width60per" name="ExpertIntroduction" /></td>
			</tr>
			<tr>
				<td class="text">备注信息</td>
				<td class="value"><input type="text" id="remark" class="width60per" name="Remark" /></td>
			</tr>
			<tr>
				<td class="text"></td>
				<td class="value"><input type="button" value="提交" class="btn_blue" onclick="submitToServer()">&nbsp;&nbsp;&nbsp;
					<input type="button" value="返回" class="btn_blue" onclick="back()"></td>
			</tr>
		</table>
	</form>

	<script type="text/javascript">
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
				
				if (data.toString() == 'error') {
					alert('文件上传失败，请重新上传');
				} else {
					//alert(data.toString());
					var json = eval("(" + data + ")");
					photo = json["fileName"];
					$('#photo').attr('src', json["filePath"]);
				}
			}
		});
	</script>
</body>
</html>