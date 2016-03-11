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
<link href="<%=basePath%>res/css/yibao.css" rel="stylesheet"
	type="text/css" />
	
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>	
<script type="text/javascript"
	src="<%=basePath%>/res/js/mzp-packed-me.js"></script>	

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>认证申请详情</title>
<style type="text/css">
.table td {
	width: 25%;
}
</style>

</head>
<body style="background: #edf1fc">
	<div id="div_first">
		<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
			<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">企业信息</font></label>
		</div>
		<table width="90%" class="table">
			<tbody>
				<tr>
					<td class="text_right">单位名称</td>
					<td class="content_left"><label> ${apply.unityName }</label></td>
					<td class="text_right">通讯地址</td>
					<td class="content_left"><label> ${apply.address }</label></td>
				</tr>
				<tr>
					<td class="text_right">法人代表</td>
					<td class="content_left"><label>
							${apply.repersentative }</label></td>
					<td class="text_right">单位性质</td>
					<td class="content_left"><label> ${apply.unitProperty }</label></td>
				</tr>
				<tr>
					<td class="text_right">业务联系人</td>
					<td class="content_left"><label> ${apply.counterMan }</label></td>
					<td class="text_right">业务电话</td>
					<td class="content_left"><label>
							${apply.countermanPhone }</label></td>
				</tr>
				<tr>
					<td class="text_right">手机</td>
					<td class="content_left"><label> ${apply.phone }</label></td>
					<td class="text_right">商家邮箱</td>
					<td class="content_left"><label> ${apply.countermanFax }</label></td>
				</tr>
				<tr>
					<td class="text_right" style="width: auto;">申请人类型</td>
					<td class="content_left"><c:choose>
							<c:when test="${apply.applicationType eq '1000171'}">
								<label>自产自销型</label>
							</c:when>
							<c:when test="${apply.applicationType eq '1000172' }">
								<label>公司加农户型</label>
							</c:when>
							<c:when test="${apply.applicationType eq '1000173' }">
								<label>农业合作组织</label>
							</c:when>
							<c:otherwise>
								<label> 其他</label>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="div_second">
		<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
			<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">产地信息</font></label>
		</div>
		<table width="90%" class="table">
			<tbody>
				<tr>
					<td class="text_right">生产基地</td>
					<td class="content_left"><label>${address }</label></td>
					<td class="text_right">地址</td>
					<td class="content_left"><label>${apply.location }</label></td>
				</tr>
				<tr>
					<td class="text_right">产地负责人</td>
					<td class="content_left"><label>${apply.productionCharger }</label></td>
					<td class="text_right">生产基地联系电话</td>
					<td class="content_left"><label>${apply.contact }</label></td>
				</tr>
				<tr>
					<td class="text_right">手机</td>
					<td class="content_left"><label>${apply.phone }</label></td>
					<td class="text_right">传真</td>
					<td class="content_left"><label>${apply.fax }</label></td>
				</tr>
				<tr>
					<td class="text_right">是否申请标签</td>
					<c:choose>
						<c:when test="${apply.isApplyOriginCode eq '100011' }">
							<td class="content_left"><label>是</label></td>
						</c:when>
						<c:otherwise>
							<td class="content_left"><label>否</label></td>
						</c:otherwise>
					</c:choose>
					<td class="text_right">E_MAIL</td>
					<td class="content_left"><label>${apply.email}</label></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="div_third" class="table">
		<div
			style="width: 100%; height: 50px; background: #8cbeef; text-align: left;">
			<label
				style="margin-left: 10px; text-align: center; position: relative; top: 25%;"><font
				size="+1.5px">已获得的资质、认证、荣誉、注册商标</font></label>
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
				<c:forEach items="${certs}" var="item">
					<tr>
						<%-- <td><img height="50px" width="50px" src="<%=path %>${item.pictureUrl }"></td> --%>
						<td align="center" ><a class="MagicZoom MagicThumb" href="<%=path %>${item.pictureUrl }"><img height="50px" width="50px" src="<%=path %>${item.pictureUrl }" /></a></td>
						<td>${item.certificateName }</td>
						<td>${item.awardDepart }</td>
						<td>${item.getTime }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="div_forth" class="table">
		<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
			<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">申报产品情况</font></label>
		</div>
		<table width="90%">
			<tbody>
				<tr>
					<td class="text_right">产品类别</td>
					<td class="content_left"><label>${type}</label></td>
					<td class="text_right">产品名称</td>
					<td class="content_left"><label>${apply.productName }</label></td>
				</tr>
				<tr>
					<td class="text_right">农产品品牌</td>
					<td class="content_left"><label>${apply.productBrand }</label></td>
					<td class="text_right">保质期</td>
					<td class="content_left"><label>${apply.remark }</label></td>
				</tr>
				<tr>
					<td class="text_right">产量</td>
					<td class="content_left"><label>${apply.produtOutput }</label></td>
					<td class="text_right">产值</td>
					<td class="content_left"><label>${apply.outputValue }</label></td>
				</tr>
				<tr>
					<td class="text_right">规模</td>
					<td class="content_left"><label>${apply.scale }</label></td>
					<td class="text_right">包装规格</td>
					<td class="content_left"><label>${apply.format }</label></td>
				</tr>
				<tr>
					<td class="text_right">标签数量</td>
					<td class="content_left"><label>${apply.number }</label></td>
				</tr>
				<tr>
					<td class="text_right">申请类别</td>
					<c:choose>
						<c:when test="${apply.isApplyOriginCode eq '1000161' }">
							<td class="content_left"><label>气候品质认证</label></td>
						</c:when>
						<c:otherwise>
							<td class="content_left"><label>纯气候品质认证</label></td>
						</c:otherwise>
					</c:choose>
					<td class="text_right">费用</td>
					<td class="content_left"><label>暂不收费</label></td>
				</tr>
				<tr style="height: auto;">
					<td class="text_right">气候控制措施</td>
					<td class="content_left"><label>${apply.controlImplement }</label></td>
					<td class="text_right">产品特征、特性描述</td>
					<td class="content_left"><label>${apply.productDescription }</label></td>
				</tr>
				<tr style="height: auto;">
					<td class="text_right">产地概况</td>
					<td class="content_left"><label>${apply.productOverview }</label></td>
				</tr>
			</tbody>
		</table>
	</div>
	<center>
		<a type="button" class="btn_blue"
			style="display: inline-block; vertical-align: middle; text-decoration: none; line-height: 30px;color:white;"
			href="<%=basePath%>authc/showApply">返回</a>
	</center>
</body>
</html>