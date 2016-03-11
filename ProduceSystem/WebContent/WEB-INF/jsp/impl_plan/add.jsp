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
<style type="text/css">
.simple_button {
	width: 100px;
	height: 30px;
	background: white;
	border: gray;
	border-width: 1px;
	border-style: solid;
	cursor: default;
}

.border_black {
	border: 1px solid black;
}

.text_right {
	width: 25%;
	text-align: right;
	padding-right: 5px;
}

.text_left {
	width: 25%;
	text-align: left;
	padding-right: 5px;
}

tr {
	margin-top: 3px;
}
</style>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/calendar.js"></script>

<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var counter = 0;
var type = "";
$(function(){
	getElements($("#select_station"));
});

function fun_back(){
	window.location.href = "<%=basePath%>/impl_plan/list"
}


function getElements(thiz){
	$.ajax({
		url:"<%=basePath%>impl_plan/getElements",
					data : {
						"stationId" : $(thiz).find("option:selected").val()
					},
					type : "GET",
					success : function(data, status) {
						if (data.success == "true") {
							$("#chb_container").empty();
							var array = data.msg;
							for (i = 0; i < array.length; i++) {
								var item = array[i];
								var chb = "<input title=\""+item.elementName+"\" value=\""+item.elementNumber+"\" type=\"checkbox\">"
										+ item.elementName;
								$("#chb_container").append(chb);
							}
						} else {
							alert(data.msg);
						}
					}
				});
	}
	
	function addStage(){
		var ele = "";
		var ids = "";
		$("#chb_container [type='checkbox']:checked").each(function(){
			ele += $(this).attr("title")+",";
			ids += $(this).val()+"|";
		});
		if($("#textStart").val == ""){
			alert("请选择开始时间");
			return;
		}
		if($("#textEnd").val == ""){
			alert("请选择开始时间");
			return;
		}
		if(ids.length <= 0){
			alert("请选择至少选择一个气象指标");
			return;
		}
		if($("#textStage").val() == ""){
			alert("请填写作物生育期");
			return;
		}
		counter++;
		$("#td_type").hide();
		$("#select_type").hide();
		type = $("#select_type option:selected").val();
		var htm ="<table title=\"item\" border=\"0\" style=\"margin-top: 10px; width: 100%; background: #edf1fc; panding: 10px;\"><tbody><tr>"
		+"<td class=\"text_right\"><b>气象站：</b></td>"
		+"<td id=\"tdStation\" title=\""+$('#select_station option:selected').val()+"\">"+$('#select_station option:selected').text()+"</td>"
		+"<td class=\"text_right\"></td><td style=\"text-align:center;\"><input type=\"button\" value=\"删除\" onclick=\"delItem(this)\" class=\"btn_blue\"></td>"
		+"</tr>"
		+"<tr>"
		+"<td class=\"text_right\"><b>气象指标：</b></td>"
		+"<td id=\"tdElement\" title=\""+ids+"\" colspan=\"3\">"+ele
		+"</tr>"
		+"<tr>"
		+"<td class=\"text_right\"><b>农作物生育期：</b></td>"
		+"<td><label id=\"labStage\">"+$("#textStage").val()+"</label></td>"
		+"<td class=\"text_right\"><b>数据采集时间：</b></td>"
		+"<td><label id=\"labStart\">"+$("#textStart").val()+"</label>~<label id=\"labEnd\">"+$("#textEnd").val()+"</label></td>"
		+"</tr>"
		+"<tr style=\"width: 80%\">"
		+"<td class=\"text_right\"><b>理由：</b></td>"
		+"<td colspan=\"3\" style=\"text-align: left;\"><label id=\"labReason\">"
		+$("#textReason").val()+"</label></td>"
		+"</tr><tbody></table>";
		//alert(htm);
		$("#textStage").val("");
		$("#textStart").val("");
		$("#textEnd").val("");
		$("#textReason").val("");
		$("#indicator").before(htm);
	}
	
	function delItem(thiz){
		if(confirm("确认删除该项吗?")){
			$(thiz).parents("table").remove();
			counter--;
			if(counter <=0){
				$("#td_type").show();
				$("#select_type").show();
			}
		}
	}
	
	function submit(){
		if(counter <= 0){
			alert("您必须创建至少一个指标");
			return;
		}
		var json="["
		$("table[title='item']").each(function(){
			//获取每一个指标，添加到json中
			json+="{\"station\":\""+$(this).find("#tdStation").attr("title")+"\",";
			json+="\"elements\":\""+$(this).find("#tdElement").attr("title")+"\",";
			json+="\"start\":\""+$(this).find("#labStart").text()+"\",";
			json+="\"end\":\""+$(this).find("#labEnd").text()+"\",";
			json+="\"stage\":\""+$(this).find("#labStage").text()+"\",";
			json+="\"reason\":\""+$(this).find("#labReason").text()+"\"},";
		});
		json = json.substring(0,json.length - 1);
		json +="]";
		$.ajax({
			url:"<%=basePath%>impl_plan/addPlan",
			type : "POST",
			data : {
				"type" : type,
				"applyId":"${applyBh}",
				"staName":$("#select_station option:selected").val(),
				"elements" : json, <!--要素指标-->
				"summary" : $("#textSummary").val(),<!--产地概述-->
				"weather" : $("#textWeather").val(),<!--气象灾害-->
				"worm" : $("#textWorm").val()<!--病虫灾害-->
			},
			success : function(data, status) {
				if (data.success == "true") {
					alert("添加成功");
					window.location.href = "<%=basePath%>/impl_plan/list"
				}else{
					alert("添加失败");
				}
			}
		});
	}
</script>
<title>新建方案</title>
</head>
<body>
	<div style="background: #4c9ee7; width: 100%; height: 30px;">
		<label
			style="width: auto; height: 30px; text-align: center; color: white; line-height: 30px; margin-left: 5px">应用中心>>实施方案>>新建方案</label>
	</div>
	<div style="min-height: 100px; padding: 10px">
		<div
			style="width: 100%; height: 30px; line-height: 30px; background: #0090d7; padding-left: 10px; margin-top: 10dp; margin-right: 10dp">
			<label><font color="#fff" size="+1.5"><b>产品信息</b></font></label>
		</div>
		<table style="margin-top: 10px; width: 100%; background: #edf1fc; panding: 10px;">
			<tbody>
				<tr>
					<td class="text_right"><b>认证项目编号:</b></td>
					<td class="text_left">${apply.applyBh }</td>
					<td class="text_right"><b>认证申请时间:</b></td>
					<td class="text_left">${apply.applyTime }</td>
				</tr>
				<tr>
					<td class="text_right"><b>产品名称:</b></td>
					<td class="text_left">${ apply.productName}</td>
					<td class="text_right"><b>产品品牌:</b></td>
					<td class="text_left">${apply.productBrand }</td>
				</tr>
				<tr>
					<td class="text_right"><b>所属商家:</b></td>
					<td class="text_left">${apply.unityName }</td>
					<td class="text_right"><b>产地:</b></td>
					<td class="text_left">${ apply.address }</td>
				</tr>

			</tbody>
		</table>
	</div>
	<div
		style="min-height: 100px; padding: 10px; margin-top: 10px">
		<div
			style="width: 100%; height: 30px; line-height: 30px; background: #0090d7; padding-left: 10px; margin-top: 10dp; margin-right: 10dp">
			<label><font color="#fff" size="+1.5"><b>添加方案信息</b></font></label>
		</div>
		<table width="100%;margin-top: 3px;" id="indicator" style="margin-top: 10px; width: 100%; background: #edf1fc; panding: 10px;">
			<tbody>
				<tr>
					<td class="text_right"><b>气象站：</b></td>
					<td><select id="select_station" onchange="getElements(this)">
							<c:forEach items="${stations }" var="station">
								<option value="${station.weatherStationInfoId }">${station.name}</option>
							</c:forEach>
					</select></td>
					<td class="text_right" id="td_type"><b>申请频率：</b></td>
					<td><select id="select_type">
							<option value="1000141">第一次申请</option>
							<option value="1000142">再次申请</option>
					</select></td>
				</tr>
				<tr>
					<td class="text_right"><b>气象指标：</b></td>
					<td colspan="3" id="chb_container">
				</tr>
				<tr>
					<td class="text_right"><b>农作物生育期：</b></td>
					<td><input id="textStage" type="text" placeholder="如：开花期、结果期"></td>
					<td class="text_right"><b>数据采集时间：</b></td>
					<td><input type="text" id="textStart" placeholder="开始时间"
						onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})">~<input
						type="text" id="textEnd" placeholder="结束时间"
						onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})"></td>
				</tr>
				<tr style="width: 80%">
					<td class="text_right"><b>理由：</b></td>
					<td colspan="3" style="text-align: left;"><textarea
							id="textReason" placeholder="选择上述指标的理由"
							style="width: 50%; height: 100px"></textarea></td>
				</tr>
				<tr style="text-align: center; width: 55%; margin: 5px">
					<td colspan="4"><input type="button" onclick="addStage()"
						value="添加" class="btn_blue"></td>
				</tr>
				<tr style="width: 80%">
					<td class="text_right"><b>产地概况：</b></td>
					<td colspan="3" style="text-align: left;"><textarea
							id="textSummary" style="width: 50%; height: 100px"></textarea></td>
				</tr>
				<tr style="width: 80%">
					<td class="text_right"><b>产地气象灾害：</b></td>
					<td colspan="3" style="text-align: left;"><textarea
							id="textWeather" style="width: 50%; height: 100px"></textarea></td>
				</tr>
				<tr style="width: 80%">
					<td class="text_right"><b>病虫灾害：</b></td>
					<td colspan="3" style="text-align: left;"><textarea
							id="textWorm" style="width: 50%; height: 100px"></textarea></td>
				</tr>
				<tr style="width: 100%; margin-top: 5px">
					<td colspan="2" class="text_right"><input type="button"
						value="提交" class="btn_blue" onclick="submit()"></td>
					<td colspan="2" class="text_left"><input type="button"
						value="返回" onclick="fun_back()" class="btn_blue"></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>

