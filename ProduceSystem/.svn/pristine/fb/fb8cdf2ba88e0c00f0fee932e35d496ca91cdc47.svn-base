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
<title>添加专家组</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"/>
<script src="<%=basePath%>res/js/jquery-1.11.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>res/js/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>res/js/validate.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">

	//输入验证
	function validateInfo() {
	    return $("#form1").validate({
	        rules: {
	        	expertsName: { maxlength: 50, required: true }
	        },
	        messages: {
	        	expertsName: { required: "请输入专家组名称", maxlength: "名称不能超过50个字符" }
	        }
	    }).form();
	}

	////////添加数据
	function submitToServer(){
		if(validateInfo()){
			//String expertsLevel, String expertsName, String expertsPerson, String region
			var expertLevel = $("#expertLevel").text();
			var expertsPerson = '';
			$("input[name='expertsCheckBox']").each(function(){
	  			if($(this).is(':checked')){
	  				expertsPerson += $(this).val() + "|";
	  			}
	 		 });
			///选中的区域
			var area;
			if(expertLevel == "基层专家"){
				area = $("#areacode").val();
			}
			else{
				if($("#provinceSelect").find("option:selected").val() != 0){
					area = $("#provinceSelect").find("option:selected").val();
				}
				else{
					alert("请选择所在地区");
					return false;
				}
			}
			if(expertsPerson.length > 0){
				var url = "<%=basePath%>auth_center_info/expertGroup_info/add";
				$.ajax({
					type : "post",
					url : url,
					dataType : "html",
					data : { 'expertsName': $("#expertsName").val(),'expertsLevel': expertLevel,'expertsPerson': expertsPerson,'region': area},
					success : function(data) {
						if(data == "T"){
							window.location.href = "<%=basePath%>auth_center_info/expertGroup_info/list";
						}
					},
					cache : false,
					error : function(msg) {
						alert(msg.responseText);
					}
				});
			}
			else{
				alert("请选择专家组成员");	
			}
		}
	}
	
	/*
	function changeExpertLevel(objc) {
		if(objc.options[objc.options.selectedIndex].text == "基层专家"){
			$("#div_primary").show();
			$("#div_provincal").hide();
		}
		else{
			$("#div_primary").hide();
			$("#div_provincal").show();
		}
		var url = "<%=basePath%>auth_center_info/expertGroup_info/experts?code="+ objc.value;
		$.ajax({
			type : "get",
			url : url,
			dataType : "html",
			data : "",
			success : function(data) {
				var json = eval("(" + data +")");
				var list = json["list"];
				var optionHtml = "";
				for(var i = 0; i < list.length; i++){
					optionHtml +=  "<span  class=\"width15per\"><input type=\"checkbox\" name=\"expertsCheckBox\" value=\""+list[i].expertNo+"\">"+list[i].expertname+"</span>";
				}
				$("#td_experts").html(optionHtml);
			},
			cache : false,
			error : function(msg) {
				alert(msg.responseText);
			}
		});
	}
	function changeSelect(value, nextObjc) {
		if(value != 0){
			var url = "<%=basePath%>auth_center_info/expertGroup_info/dictionary?code="+ value;
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
	*/
	
	function back(){
		window.location.href = "<%=basePath%>auth_center_info/expertGroup_info/list";
	}
</script>
</head>
<body>
	<form id="form1">
		<table  class="info" cellpadding="0" cellspacing="0">
			<tr>
				<td class="text"><span style="color: #FF0000">*</span>专家组名称</td>
				<td class="value"><input type="text" id = "expertsName" name="expertsName" /></td>
			</tr>
			<tr>
				<td class="text">专家层次</td>
				<td> 
					<span id="expertLevel">${expertLevel}</span>
					<!-- 
					<select  class="width15per" id= "expertLevel" onchange="changeExpertLevel(this)">
						<option value ="${primaryExpertID}">基层专家</option>
						<option value ="${provincalExpertID}">省级专家</option>
					</select>
					 -->
				 </td>
			</tr>
			<tr>
				<td class="text">所属区域</td>
				<td class="value">
					<c:if test="${expertLevel == '省级专家'}">
						<select id="provinceSelect"  class="width25per">
							<option value="0">请选择所在省</option>
							<c:forEach var="area" items="${provinceList}">
								<option value="${area.getCuitMoonAreaCode()}">${area.getCuitMoonAreaName()}</option>
							</c:forEach>
						</select>
					</c:if>
					<c:if test="${expertLevel == '基层专家'}">
						<span id="area_span">${AreaName}</span>
						<input type="hidden" id ="areacode" value="${AreaCode}" />
					</c:if>
				<!-- 
				<div id="div_primary">
				 <select id="provinceSelect"  class="width15per"
					onchange="changeSelect(this.value,$('#citySelect'));">
						<option value="0">请选择所在省</option>
						<c:forEach var="area" items="${provinceList}">
							<option value="${area.getCuitMoonAreaCode()}">${area.getCuitMoonAreaName()}</option>
						</c:forEach>
				</select> <select id="citySelect"  class="width15per" name="city"
					onchange="changeSelect(this.value,$('#countySelect'));">
						<option value="0">请先选择省级</option>
				</select> <select id="countySelect"  class="width15per" name="county">
						<option value="0">请先选择县级</option>
				</select>
				</div>
				<div id = "div_provincal" style="display: none;">
					 <select id="provinceSelect2" class="width25per">
						<option value="0">请选择所在省</option>
						<c:forEach var="area" items="${provinceList}">
							<option value="${area.getCuitMoonAreaCode()}">${area.getCuitMoonAreaName()}</option>
						</c:forEach>
					</select>
				</div>
				 -->
				</td>
			</tr>
			<tr>
				<td class="text">专家成员</td>
				<td class="value" id = "td_experts">
				<c:forEach var="expertVar" items="${expertList}">
						<span  class="width15per"> <input type="checkbox" name="expertsCheckBox"
							value="${expertVar.getExpertNo()}">${expertVar.getExpertname()}
						</span>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td class="text">&nbsp;</td>
				<td class="value submit"><input type="button" class="btn_blue" value="提交"
					onclick="submitToServer()">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="返回" class="btn_blue" onclick="back()"></td>
			</tr>
		</table>
	</form>
</body>
</html>