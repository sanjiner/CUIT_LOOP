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
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>申请审核</title>
<script type="text/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
	
<script type="text/javascript">
	//登录者角色
	var Identify = "${role}";
	var IdentifyName = "${name}";
	var checkProcess = "${process}";
	//审核进度
	var handleResult = "${handleResult}";
	var IsSubmit = false;
	/*先判断是否是退回的，如果是退回的所有的意见和签名全部显示,如果是退回的，要记录下是从哪一次审核退下来的,然后做权限的选择,如果不是退回的，就要做权限限制,权限控制,检测是否是退回的退回：true*/
	function fun_validate(value) {
		if ("已退回" == value) {
			return true;
		} else {
			return false;
		}
	}

	function IdentifyVerify() {

		//没有被退回，不显示
		if (!fun_validate(handleResult)) {
			switch (Identify) {
			case "县级气象局管理员":
				$("#txtRegionPerson").val(IdentifyName); //县级审核人
				$("#countryPass").hide();
				$("#City").hide();
				$("#Province").hide();
				break;
			case "市级气象局管理员":
				//县级气象局
				$("#countryPre").hide();
				$("#cityPass").hide();
				$("#txtCityPerson").val(IdentifyName); //市级审核人
				$("#Province").hide();
				break;
			case "省级气象局管理员":
				//县级气象局
				$("#countryPre").hide();
				//市级气象局
				$("#cityPre").hide();
				$("#provincePass").hide();
				$("#txtProvincePerson").val(IdentifyName); //省级审核人;
				break;
			}
		}
		//被退回，不影藏而是显示但不能修改。审核进度（从哪一级退回的）
		else {
			switch (checkProcess) {
			case "待县级气象局管理员审核":
				$("#countyPass").hide();
				break;
			case "待市级气象局管理员审核":
				$("#cityPre").hide();
				$("#Province").hide();
				//如果当前登录的县级
				if ("县级气象局管理员" == Identify) {
					$("#countyPass").hide();
				}
				break;
			case "待省级气象局管理员审核":
				$("#provincePre").hide();
				//如果当前登录的是市级
				if ("市级气象局管理员" == Identify) {
					$("#countyPre").hide();
					$("#cityPass").hide();
				}
				//如果当前登录的是县级
				if ("县级气象局管理员" == Identify) {
					$("#countyPass").hide();
					$("#cityPre").hide();
				}
			}
		}
		return true;
	}

	$(document)
			.ready(
					function() {

						IdentifyVerify();

						$("#back")
								.click(
										function() {
											window.location.href = "/SysModule/ApproveCheckModule/List.aspx";
										});

						//通过
						$("#Submit")
								.click(
										function() {
											$
													.ajax({
														type : "POST",
														url : "<%=basePath%>exam/examApply?applyBh=${apply.applyBh}",
														dataType : "html",
														data : {
															'IsPass' : "Yes",
															'Region' : $("#txtRegionPerson").val(),
															'RegionAdvice' : $("#txtRegionAdvice").val(),
															'City' : $("#txtCityPerson").val(),
															'CityAdvice' : $("#txtCityAdvice").val(),
															'Province' : $("#txtProvincePerson").val(),
															'ProvinceAdvice' : $("#txtProvinceAdvice").val()
														},
														success : function(data) {
															 var obj = eval('(' + data + ')');
											            	 if(obj.success == "true"){
											            		 alert("审核成功");
											            		 window.location.href = "<%=basePath%>exam/list";
											            	 }else
											            		 alert("审核失败");
														},
														cache : false,
														error : function(msg) {
															alert(msg.responseText);
														}
													});
										});

						//不通过
						$("#No")
								.click(
										function() {
											$
													.ajax({
														type : "POST",
														url : "<%=basePath%>exam/examApply?applyBh=${apply.applyBh}",
														dataType : "html",
														data : {
															'IsPass' : "No",
															'Region' : $("#txtRegionPerson").val(),
															'RegionAdvice' : $("#txtRegionAdvice").val(),
															'City' : $("#txtCityPerson").val(),
															'CityAdvice' : $("#txtCityAdvice").val(),
															'Province' : $("#txtProvincePerson").val(),
															'ProvinceAdvice' : $("#txtProvinceAdvice").val()
														},
														success : function(data) {
															 var obj = eval('(' + data + ')');
											            	 if(obj.success == "true"){
											            		 alert("审核成功");
											            		 window.location.href = "<%=basePath%>exam/list";
											            	 }else
											            		 alert("审核失败");
														},
														cache : false,
														error : function(msg) {
															alert(msg.responseText);
														}
													});
										});

						//退回
						$("#CallBack")
								.click(
										function() {
											$
													.ajax({
														type : "POST",
														url : "<%=basePath%>exam/examApply?applyBh=${apply.applyBh}",
														dataType : "html",
														data : {
															'IsPass' : "Back",
															'Region' : $("#txtRegionPerson").val(),
															'RegionAdvice' : $("#txtRegionAdvice").val(),
															'City' : $("#txtCityPerson").val(),
															'CityAdvice' : $("#txtCityAdvice").val(),
															'Province' : $("#txtProvincePerson").val(),
															'ProvinceAdvice' : $("#txtProvinceAdvice").val()
														},
														success : function(data) {
															 var obj = eval('(' + data + ')');
											            	 if(obj.success == "true"){
											            		 alert("审核成功");
											            		 window.location.href = "<%=basePath%>exam/list";
											            	 }else
											            		 alert("审核失败");
														},
														cache : false,
														error : function(msg) {
															alert(msg.responseText);
														}
													});
										});
					});
</script>
<style type="text/css">
.table td {
	width: 25%;
}

.father {
	padding: 5px;
	margin: 10px;
	border: 1px solid #dcdcdc;
	overflow: hidden;
}

.child {
	
}

a:link {
	color: #000000;
	text-decoration: underline;
}

a:visited {
	color: #000000;
	text-decoration: none;
}
</style>
</head>
<body>
	<div style="background: #4c9ee7;width: 100%;height: 30px;">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">应用中心>>认证审核>>审核应用</label>
	</div>
	<div id="div_first" style="background: #edf1fc" class="father">
		<label style="margin-left: 10px"><font size="+1.5"><b>企业信息</b></font></label>
		<table width="90%" class="table">
			<tbody>
				<tr>
					<td class="text_right">单位名称：</td>
					<td class="content_left"><label> ${apply.unityName }</label></td>
					<td class="text_right">通讯地址：</td>
					<td class="content_left"><label> ${apply.address }</label></td>
				</tr>
				<tr>
					<td class="text_right">法人代表：</td>
					<td class="content_left"><label>
							${apply.repersentative }</label></td>
					<td class="text_right">单位性质：</td>
					<td class="content_left"><label> ${apply.unitProperty }</label></td>
				</tr>
				<tr>
					<td class="text_right">业务联系人：</td>
					<td class="content_left"><label> ${apply.counterMan }</label></td>
					<td class="text_right">业务电话：</td>
					<td class="content_left"><label>
							${apply.countermanPhone }</label></td>
				</tr>
				<tr>
					<td class="text_right">手机：</td>
					<td class="content_left"><label> ${apply.phone }</label></td>
					<td class="text_right">商家邮箱：</td>
					<td class="content_left"><label> ${apply.countermanFax }</label></td>
				</tr>
				<tr>
					<td class="text_right" style="width: auto;">申请人类型：</td>
					<td class="content_left"><c:choose>
							<c:when test="${apply.applicationType eq '1000171'}">
								<label>自产自销型：</label>
							</c:when>
							<c:when test="${apply.applicationType eq '1000172' }">
								<label>公司加农户型：</label>
							</c:when>
							<c:when test="${apply.applicationType eq '1000173' }">
								<label>农业合作组织：</label>
							</c:when>
							<c:otherwise>
								<label> 其他：</label>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="div_second" style="background: #edf1fc" class="father">
		<label style="margin-left: 10px"><font size="+1.5"><b>产地信息</b></font></label>
		<table width="90%" class="table">
			<tbody>
				<tr>
					<td class="text_right">生产基地：</td>
					<td class="content_left"><label>${address }</label></td>
					<td class="text_right">地址：</td>
					<td class="content_left"><label>${apply.location }</label></td>
				</tr>
				<tr>
					<td class="text_right">产地负责人：</td>
					<td class="content_left"><label>${apply.productionCharger }</label></td>
					<td class="text_right">生产基地联系电话：</td>
					<td class="content_left"><label>${apply.contact }</label></td>
				</tr>
				<tr>
					<td class="text_right">手机：</td>
					<td class="content_left"><label>${apply.phone }</label></td>
					<td class="text_right">传真：</td>
					<td class="content_left"><label>${apply.fax }</label></td>
				</tr>
				<tr>
					<td class="text_right">是否申请标签：</td>
					<c:choose>
						<c:when test="${apply.isApplyOriginCode eq '100011' }">
							<td class="content_left"><label>是</label></td>
						</c:when>
						<c:otherwise>
							<td class="content_left"><label>否</label></td>
						</c:otherwise>
					</c:choose>
					<td class="text_right">E_MAIL：</td>
					<td class="content_left"><label>${apply.email}</label></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="div_third" style="background: #edf1fc" class="father">
		<label style="margin-left: 10px"><font size="+1.5"><b>已获得的资质、认证、荣誉、注册商标</b></font></label>
		<div
			style="width: 100%; height: auto; float: left; padding: 30px; text-align: left;"
			align="center">
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
				<c:forEach items="${certs}" var="item">
					<tr>
						<td><img height="50px" width="50px" src="<%=path %>${item.pictureUrl }"></td>
						<td>${item.certificateName }</td>
						<td>${item.awardDepart }</td>
						<td>${item.getTime }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	</div>
	<div id="div_forth" style="background: #edf1fc" class="father">
		<label style="margin-left: 10px"><font size="+1.5"><b>申报产品情况</b></font></label>
		<table width="90%" class="table">
			<tbody>
				<tr>
					<td class="text_right">产品类别：</td>
					<td class="content_left"><label>${type}</label></td>
					<td class="text_right">产品名称：</td>
					<td class="content_left"><label>${apply.productName }</label></td>
				</tr>
				<tr>
					<td class="text_right">农产品品牌：</td>
					<td class="content_left"><label>${apply.productBrand }</label></td>
					<td class="text_right">保质期：</td>
					<td class="content_left"><label>${apply.remark }</label></td>
				</tr>
				<tr>
					<td class="text_right">产量：</td>
					<td class="content_left"><label>${apply.produtOutput }</label></td>
					<td class="text_right">产值：</td>
					<td class="content_left"><label>${apply.outputValue }</label></td>
				</tr>
				<tr>
					<td class="text_right">规模：</td>
					<td class="content_left"><label>${apply.scale }</label></td>
					<td class="text_right">包装规格：</td>
					<td class="content_left"><label>${apply.format }</label></td>
				</tr>
				<tr>
					<td class="text_right">标签数量：</td>
					<td class="content_left"><label>${apply.number }</label></td>
				</tr>
				<tr>
					<td class="text_right">申请类别：</td>
					<c:choose>
						<c:when test="${apply.isApplyOriginCode eq '1000161' }">
							<td class="content_left"><label>气候品质认证</label></td>
						</c:when>
						<c:otherwise>
							<td class="content_left"><label>纯气候品质认证</label></td>
						</c:otherwise>
					</c:choose>
					<td class="text_right">费用：</td>
					<td class="content_left"><label>暂不收费</label></td>
				</tr>
				<tr style="height: auto;">
					<td class="text_right">气候控制措施：</td>
					<td class="content_left" colspan="3"><label>${apply.controlImplement }</label></td>
				</tr>
				<tr>
					<td class="text_right">产品特征、特性描述：</td>
					<td class="content_left" colspan="3"><label>${apply.productDescription }</label></td>
				</tr>
				<tr style="height: auto;">
					<td class="text_right">产地概况：</td>
					<td class="content_left" colspan="3"><label>${apply.productOverview }</label></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="Country" style="background: #edf1fc"  class="father">
		<label title="label"><font size="+1.5"><b>县气象局审核</b></font></label>
		<div class="child">
			<table class="table">
				<tr id="countryPre" class="item">
					<td class="text" style="width: 30%;">县级审核人签名</td>
					<td class="value" style="width: 20%;"><input
						id="txtRegionPerson" width="2000px" type="text"></td>
					<td class="text" style="width: 10%;">县级审核意见</td>
					<td class="value" style="width: 40%;"><input
						id="txtRegionAdvice" width="2000px" type="text"></td>
				</tr>
				<tr id="countryPass" class="item">
					<td class="text" style="width: 30%;">县级审核人签名</td>
					<td class="value" style="width: 20%;"><b><label>${apply.regionHeader}</label></b></td>
					<td class="text" style="width: 10%;">县级审核意见</td>
					<td class="value" style="width: 40%;"><b><label>${apply.regionManageOpinion}</label></b></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="City" style="background: #edf1fc"  class="father">
		<label title="label"><font size="+1.5"><b>市气象局审核</b></font></label>
		<div class="child">
			<table class="table">
				<tr id="cityPre" class="item">
					<td class="text" style="width: 30%;">市级审核人签名</td>
					<td class="value" style="width: 20%;"><input
						id="txtCityPerson" width="2000px" type="text"></td>
					<td class="text" style="width: 10%;">市级审核意见</td>
					<td class="value" style="width: 40%;"><input
						id="txtCityAdvice" width="2000px" type="text"></td>
				</tr>
				<tr id="cityPass" class="item">
					<td class="text" style="width: 30%;">市级审核人签名</td>
					<td class="value" style="width: 20%;"><b><label>${apply.cityHeader}</label></b></td>
					<td class="text" style="width: 10%;">市级审核意见</td>
					<td class="value" style="width: 40%;"><b><label>${apply.cityManageAudit}</label></b></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="Province" style="background: #edf1fc"  class="father">
		<label title="label"><font size="+1.5"><b>省级气象局审核</b></font></label>
		<div class="child">
			<table class="table">
				<tr id="provincePre" class="item">
					<td class="text" style="width: 30%;">省级审核人签名</td>
					<td class="value" style="width: 20%;"><input
						id="txtProvincePerson" width="2000px" type="text"></td>
					<td class="text" style="width: 10%;">省级审核意见</td>
					<td class="value" style="width: 40%;"><input
						id="txtProvinceAdvice" width="2000px" type="text"></td>
				</tr>
				<tr id="provincePass" class="item">
					<td class="text" style="width: 30%;">省级审核人签名</td>
					<td class="value" style="width: 20%;"><b><label>${apply.provinceHeader}</label></b></td>
					<td class="text" style="width: 10%;">省级审核意见</td>
					<td class="value" style="width: 40%;"><b><label>${apply.provinceManageOpinion}</label></b></td>
				</tr>
			</table>
		</div>
	</div>
	<center>
		<table class="table">
			<tbody>
				<tr>
					<td><input type="button" class="btn_blue"	id="Submit" value="通过"></td>
					<td><input type="button" class="btn_blue"	id="CallBack" value="退回"></td>
					<td><input type="button" class="btn_blue"	id="No" value="不通过"></td>
					<td><a type="button" class="btn_blue" 
						style="display: inline-block; vertical-align: middle; text-decoration: none; line-height: 30px;color: white;"
						href="<%=basePath%>exam/list">返回</a></td>

				</tr>
			</tbody>
		</table>
	</center>
</body>
</html>