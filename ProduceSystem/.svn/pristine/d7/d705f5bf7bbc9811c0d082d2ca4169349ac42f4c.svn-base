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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/calendar.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.text_right{
text-align: right;
}
</style>
<script type="text/javascript">
var identify = "${role}";
var identifyName ="${name}";

	function identifyVerify() {
		if (identify == "基层专家组") {
			$("#basePass").hide();
			$("#Advanced").hide();
			$("#txtBasicSign").val(identifyName);
		}
		else if (identify == "省级专家组") {
			$("#basePre").hide();
			$("#advancePass").hide();
			$("#txtAdvanceSign").val(identifyName);
		}
		else{
			$("#basePre").hide();
			$("#Advanced").hide();
			$("#txtAdvanceSign").hide();
			$("#submit").parent().hide();
			$("#no").parent().hide();
			$("#back").parent().css("text-align","center");
		}
	}

	$(document).ready(function() {
		identifyVerify();
		$("#back")
				.click(
						function() {
							window.location.href = "<%=basePath%>/impl_plan/list";
						});

		$("#submit").click(function() {
			$.ajax({
				type : "POST",
				url : "<%=basePath%>/impl_plan/audit",
				dataType : "html",
				data : {'applyBh':'${applyBh}','num':'${num}','IsPass' : "Pass",'Base' : $("#txtBasicSign").val(),'BaseAdvice' : $("#txtBasicAdvice").val(),'Advance' : $("#txtAdvanceSign").val(),'AdvanceAdvice' : $("#txtAdvanceAdvice").val()},
				success : function(data) {
					data = eval("("+data+")");
					if (data.success == "false") {
 						alert("审核失败！");
					} else {
						alert("审核成功！");
						window.location.href = "<%=basePath%>/impl_plan/list";
						}
					},
					cache : false,
					error : function(msg) {
						alert(msg.responseText);
						}
					});
			});
		$("#no").click(function(){
			$.ajax({
				type : "POST",
				url : "<%=basePath%>/impl_plan/audit",
				dataType : "html",
				data : {
					'IsPass' : "No",
					'Base' : $("#txtBasicSign").val(),'BaseAdvice' : $("#txtBasicAdvice").val(),'Advance' : $("#txtAdvanceSign").val(),'AdvanceAdvice' : $("#txtAdvanceAdvice").val()},
					success : function(data) {
						data = eval("("+data+")");
						if (data.success == "false") {
							alert("审核失败！");
							} else {
								alert("审核成功！");
								window.location.href = "<%=basePath%>/impl_plan/list";
					}
				},
				cache : false,
				error : function(msg) {
					alert(msg.responseText);
				}
			});
		});
	});
</script>
<title></title>
</head>
<body>
	<form id="form1">
		<div class="detail">
			<div class="border_black" style="min-height: 100px;">
				<div style="width: 100%;height: 30px;line-height: 30px;background: #0090d7;padding-left: 10px">
			<label><font color="#fff" size="+1.5"><b>产品信息</b></font></label>
			</div>
				<table style="margin-top: 10px; width: 100%; background:#edf1fc; panding: 10px;">
					<tbody>
						<tr>
							<td class="text_right"><b>认证项目编号:</b></td>
							<td class="text_left">${apply.applyBh }</td>
							<td class="text_right"><b>认证申请时间:</b></td>
							<td class="text_left">${apply.applyTime }</td>
						</tr>
						<tr>
							<td class="text_right"><b>产品名称:</b></td>
							<td class="text_left">
									${apply.productName}</td>
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
			<div style="width: 100%;height: 30px;line-height: 30px;background: #0090d7;padding-left: 10px;margin-top: 10dp">
			<label><font color="#fff" size="+1.5"><b>要素信息</b></font></label>
			</div>
			<c:forEach items="${bearList }" var="item">
			<table title="item" border="0"
				style="margin-top: 10px; width: 100%; background:#edf1fc; panding: 10px;">
				<tbody>
					<tr>
						<td class="text_right"><b>气象站：</b></td>
						<td class="text_left" >${item.stationId }</td>
						<td class="text_right"></td>
						<td class="text_left" style="text-align: center;"></td>
					</tr>
					<tr>
						<td class="text_right"><b>气象指标：</b></td>
						<td id="tdElement" class="text_left"
							colspan="3">${item.meteIndicators }</td>
					</tr>
					<tr>
						<td class="text_right"><b>农作物生育期：</b></td>
						<td class="text_left"><label id="labStage">${item.cropGrowthPeriod }</label></td>
						<td class="text_right"><b>数据采集时间：</b></td>
						<td class="text_left"><fmt:formatDate value="${item.startCollectionTime}"
								pattern="yyyy-MM-dd" />~<fmt:formatDate value="${item.endCollectionTime}"
								pattern="yyyy-MM-dd" /></td>
					</tr>
					<tr style="width: 80%">
						<td class="text_right"><b>理由：</b></td>
						<td colspan="3" style="text-align: left;"><label
							id="labReason">${item.meteIndicatorsReason }</label></td>
					</tr>
				</tbody>
			</table>
			</c:forEach>
			
			<div
				style="width: 100%; height: 30px; line-height: 30px; background: #0090d7; padding-left: 10px; margin-top: 10dp; margin-right: 10dp">
				<label><font color="#fff" size="+1.5"><b>产地信息</b></font></label>
				</div>
				<table 
					style="margin-top: 10px; width: 100%; background: #edf1fc; panding: 10px;text-align: left;">
					<tbody>
						<tr>
							<td class="text_right" style="width: 25%"><b>产地概况：</b></td>
							<td class="text_left">${entity.originSituation }</td>
						</tr>
						<tr>
							<td class="text_right"><b>产地主要气象灾害：</b></td>
							<td id="tdElement" class="text_left">${entity.meteorologicalDisaster}</td>
						</tr>
						<tr>
							<td class="text_right"><b>农产品主要病虫害：</b></td>
							<td class="text_left">${entity.diseasesInsectPests}</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div id="Basic">
				<div>
					<div class="text" style="font-size: +1.5; text-indent: 5px;">
						<b>基层专家组审核</b>
					</div>
				</div>
				<div style="padding: 10px">
					<table class="table">
						<tr id="basePre" class="item">
							<td class="text" style="width: 30%;">组长签名</td>
							<td class="value" style="width: 20%;"><input type="text"
								id="txtBasicSign" width="200px"></input></td>
							<td class="text" style="width: 10%;">基层专家组意见</td>
							<td class="value" style="width: 40%;"><input tyep="text"
								id="txtBasicAdvice" /></td>
						</tr>
						<tr id="basePass" class="item">
							<td class="text" style="width: 30%;">组长签名</td>
							<td class="value" style="width: 20%;"><b><label
								id="basePassSign">${entity.basicPanelSign }</label></b></td>
							<td class="text" style="width: 10%;">基层专家组意见</td>
							<td class="value" style="width: 40%;"><b><label
								id="basePassAdvice">${entity.basicPanelAuditOpinion }</label></b></td>
						</tr>
					</table>
				</div>
			</div>
			<div id="Advanced">
				<div >
					<div class="text" style="font-size: +1.5; text-indent: 5px;">
						<b>省级专家组审核</b>
					</div>
				</div>
				<div  style="padding: 10px">
					<table class="table" style ="margin-top: 10px";>
						<tr id="advancePre" class="item">
							<td class="text" >组长签名</td>
							<td class="value"><input type="text"
								id="txtAdvanceSign" /></td>
							<td class="text" >省级专家组意见</td>
							<td class="value" ><input
								id="txtAdvanceAdvice" type="text" /></td>
						</tr>
						<tr id="advancePass" class="item">
							<td class="text">组长签名</td>
							<td class="value"><label
								id="labAdvancePassSign"></label></td>
							<td class="text">省级专家组意见</td>
							<td class="value"><label
								id="labAdvanceAdvice"></label></td>
						</tr>
					</table>
				</div>
			</div>
			<table class="table" width="100%" style="margin: auto;">
				<tr class="item">
					<td class="value" style="width: 10%; text-align: right;"><input
						id="submit" class="btn_blue" type="button" value="通过" />
						&nbsp;&nbsp;</td>
					<td class="text" style="width: 10%; text-align: center;"><input
						id="no" class="btn_blue" type="button" value="不通过" /></td>
					<td class="value" style="width: 10%;">&nbsp;&nbsp;<input
						id="back" class="btn_blue"  type="button" value="返回" />
					</td>
				</tr>
			</table>
	</form>
</body>
</html>
