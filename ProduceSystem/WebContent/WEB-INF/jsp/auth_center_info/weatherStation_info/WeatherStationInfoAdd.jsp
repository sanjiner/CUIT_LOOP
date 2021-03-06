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
<title>添加气象站</title>
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
        	stationID: { required: true },
        	name: { required: true },
        	belongTo: { required: true },
        	longitude: { required: true },
        	latitude: { required: true },
        	remark: { maxlength: 200 }
        },
        messages: {
        	stationID: { required: "请输入气象站编号"},
        	name: { required: "请输入气象站名称"},
        	belongTo: { required: "请输入所属气象局"},
        	longitude: { required: "请输入 经度 "},
        	latitude: { required: "请输入纬度"},
        	remark: { required: "不能超过200个字符"}
        }
    }).form();
}

////////添加数据
function submitToServer(){
	if(validateInfo()){
		//String stationID, String name, String belongTo,
		//String longitude, String latitude, String measuringelements, String remark)
		var measuringelements = ""; //要素
		$("input[name='elementCheckBox']").each(function(){
				if($(this).is(':checked')){
					measuringelements += $(this).val() + "|";
				}
		 }); 
		//alert(measuringelements);	
		var a = $("#belongTo").val();
		var url = "<%=basePath%>auth_center_info/weatherStation_info/add";
		$.ajax({
			type : "post",
			url : url,
			dataType : "html",
			data : { 'stationID': $("#stationID").val(),'name': $("#name").val(),'belongTo': $("#belongTo2").val(),'longitude':$("#longitude").val(),'latitude': $("#latitude").val(),'measuringelements':measuringelements,'remark':$("#remark").val()},
			success : function(data) {
				if(data == "T"){
					alert("添加成功");
					window.location.href = "<%=basePath%>auth_center_info/weatherStation_info/list";
				}
			},
			cache : false,
			error : function(msg) {
				alert("添加失败！"+msg.responseText);
			}
		});
	}
}

function back(){
	window.location.href = "<%=basePath%>auth_center_info/weatherStation_info/list";
}
</script>
</head>
<body>
	<form id="form1">
		<div class="detail">
			<table  class="info" cellpadding="0" cellspacing="0">
				<tr>
					<td class="text"><span style="color: #FF0000">*</span>气象站编号</td>
					<td class="value"><input type="text" id="stationID" name="stationID" />  S开头的气象站号，请将s改为83，如s207改为83207</td>
				</tr>
				<!-- <tr>
					<td class="text"><span style="color: #FF0000"> </td>
					<td class="value"><span style="color: #FF0000">提示：</span>S开头的气象站号，请将s改为83，如s207改为83207</td>
				</tr> -->
				<tr>
					<td class="text"><span style="color: #FF0000">*</span>气象站名称</td>
					<td class="value"><input type="text" id="name" name="name" /></td>
				</tr>
				<tr>
					<td class="text"><span style="color: #FF0000">*</span>所属气象局</td>
					<td class="value"><span id="belongTo" >${belongTo}</span></td>
					<td><input type="hidden" id="belongTo2" value="${belongToCode}" /></td>
				</tr>
				<tr>
					<td class="text ">请选择气象站所在位置</td>
					<td class="value">
						<div id="mapContainer" style="height:300px; width: 70%">
						</div>
					</td>
				</tr>
				<tr>
					<td class="text"><span style="color: #FF0000">*</span>所在经度</td>
					<td class="value"><input type="text" id="longitude" name="longitude" /></td>
				</tr>
				<tr>
					<td class="text"><span style="color: #FF0000">*</span>所在纬度</td>
					<td class="value"><input type="text" id="latitude" name="latitude" /></td>
				</tr>
				<tr>
					<td class="text">选择要素</td>
					<td class="value">
						<c:forEach var="elementVar"
							items="${ElementList}">
							<span>
								<input type="checkbox" name="elementCheckBox"value="${elementVar.getElementNumber()}">${elementVar.getElementName()}
							</span>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td class="text">备注</td>
					<td class="value"><input type="text" id="remark" name = "remark" /></td>
				</tr>
				<tr>
					<td class="text">&nbsp;</td>
					<td class="value submit"><input type="button" class="btn_blue" value="提交"
						onclick="submitToServer()">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="返回" class="btn_blue"  onclick="back()"></td>
				</tr>
			</table>
		</div>
	</form>
	<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=2346a568152ffc0d1791fcb86c2854e1"></script>
	<script type="text/javascript">
		//初始化地图对象，加载地图
		var map = new AMap.Map('mapContainer', {
			resizeEnable: true
		});
		AMap.event.addListener(map,'click',getLnglat);	
		//鼠标在地图上点击，获取经纬度坐标
		function getLnglat(e){ 
			document.getElementById('longitude').value = e.lnglat.getLng();  
			document.getElementById('latitude').value = e.lnglat.getLat();   
		}
	</script>
</body>
</html>