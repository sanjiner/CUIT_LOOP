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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目归档添加</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js" type="text/javascript" language="javascript"></script>
<script src="<%=basePath%>res/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=basePath%>res/js/validate.js" type="text/javascript"></script>

<style type="text/css">
div.wrapper {
	position: relative;
	width: 125px;
	height: 125px;
	border: 0;
	float:left;
	background-color: transparent;
	text-align: center;
}
</style>

<script type="text/javascript">

	$(document).ready(function(){
		var imags = "${report_attachmentUrl}";
		var strs = imags.split("|");
		for(i = 0; i < strs.length;i++){
			if(strs[i].length <=0 )
				continue;
			if(strs[i].length >= 0){
				var html = "<div id=\""+strs[i].substring(strs[i].lastIndexOf('/',strs[i].length))+"\" class=\"wrapper\"  style=\"cursor: pointer;\"><img style=\"width: 120px; height: 120px;margin:5px; background: #c2c2c2; cursor: pointer;\" src=\""+'<%=path%>'+strs[i]+"\"/><div onclick=\"deleImg(this)\" class=\"divdelete\" style=\"display: block;\"></div></div>";
				$("#imgContainer").append(html);
			}
		}
	});
	
	$(document).ready(function(){
		var imags = "${report_certificateImage}";
		var strs = imags.split("|");
		for(i = 0; i < strs.length;i++){
			if(strs[i].length <=0 )
				continue;
			if(strs[i].length >= 0){
				var html = "<div id=\""+strs[i].substring(strs[i].lastIndexOf('/',strs[i].length))+"\" class=\"wrapper\"  style=\"cursor: pointer;\"><img style=\"width: 120px; height: 120px;margin:5px; background: #c2c2c2; cursor: pointer;\" src=\""+'<%=path%>'+strs[i]+"\"/><div onclick=\"deleImg(this)\" class=\"divdelete\" style=\"display: block;\"></div></div>";
				$("#imgSign").append(html);
			}
		}
	});

function validate() {
    return $("#form1").validate({
        rules: {
        	proName: { maxlength: 50, required: true },
        	proNo: { maxlength: 50, required: true },
        	Person: { maxlength: 10, required: true },
        	protime: { maxlength: 10, required: true }
        },
        messages: {
        	proName: { required: "请输入项目名称", maxlength: " 项目名称不能超过50字符" },
        	proNo: { required: "请输入档案编号", maxlength: " 档案编号不能超过50字符" },
        	Person: { required: "请输入归档人", maxlength: " 归档人不能超过10字符" },
        	protime: { required: "请输入归档时间", maxlength: " 归档时间不能超过10字符" }
        }
    }).form();
}

function btnOK() {
	if(validate()){
		document.getElementById('form1').submit();
	}
}
function back() {
	window.location.href = "<%=basePath%>file_recode/list";
}
</script>
</head>
<body style="background-color: #FFFFFF">
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">应用中心>>项目归档>>添加归档</label>
</div>
<br/>
<div id="div01" style="background-color: #FFFFFF">
	<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
		<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">认证申请信息</font></label>
	</div>
	<div>
	<table style="width: 70%">
		<tr>
			<td>申请商家：${unityName}</td>
			<td>申请时间：${applyTime}</td>
		</tr>
		<tr>
			<td>商家类型：${applicationType}</td>
			<td>业务联系人：${counterMan}</td>
		</tr>
		<tr>
			<td>联系电话：${contact}</td>
			<td>传真：${fax}</td>
		</tr>
		<tr>
			<td>手机：${phone}</td>
			<td>邮箱：${email}</td>
		</tr>
		<tr>
			<td colspan="2">地址：${address}</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td>产品名称：${productName}</td>
			<td>产品品牌：${productBand}</td>
		</tr>
		<tr>
			<td>产地：${productBase}</td>
			<td>规模：${scale}</td>
		</tr>
		<tr>
			<td>产量：${produtOutput}</td>
			<td>产值：${outputValue}</td>
		</tr>
		<tr>
			<td>保质期：${baozhiqi}</td>
			<td></td>
		</tr>
		<tr>
			<td colspan="2">产品描述：${productDescription}</td>
		</tr>	
	</table>
	</div>
</div>
<br/>
<div id="div02" style="background-color: #FFFFFF">
<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
	<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">认证申请审核信息</font></label>
</div>
<div>
	<table>
		<tr>
			<td style="width: 100px">县级审核</td>
			<td>
				审核人：${regionHeader}<br/>
				审核时间：${regionAuditTime}<br/>
				审核意见：${regionManageOpinion}<br/>
			</td>
		</tr>
		<tr>
			<td>市级审核</td>
			<td>
				审核人：${cityHeader}<br/>
				审核时间：${cityAuditTime}<br/>
				审核意见：${cityManageAudit}<br/>
			</td>
		</tr>
		<tr>
			<td>省级审核</td>
			<td>
				审核人：${provinceHeader}<br/>
				审核时间：${provinceAuditTime}<br/>
				审核意见：${provinceManageOpinion}<br/>
			</td>
		</tr>
	</table>
</div>	
</div>
<br/>
<div id="div03" style="background-color: #FFFFFF">
<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
	<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">实施方案信息</font></label>
</div>
<div>
	<div>产品名称：${productName}</div>
	<div>产地：${productBase}</div>
	<div>申请频率：${applyFrequency}</div>
	<div>农作物生育期信息：
		<table class="gvtable" border="0" style="margin-left:50px; width: 70%">
			<tr>
				<th>生育期</th>
				<th>气象要素</th>
				<th>气象站</th>
				<th>采集时间</th>
			</tr>
			<c:forEach var="blist" items="${bearlist}">
				<tr>
					<td>${blist.cropGrowthPeriod}</td>
					<td>${blist.meteIndicators}</td>
					<td>${blist.stationId}</td>
					<td><fmt:formatDate value="${blist.startCollectionTime }" type="date" pattern="yyyy-MM-dd"/>至
						<fmt:formatDate value="${blist.endCollectionTime }" type="date" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<br/>
	<div>产地概况：${originSituation}</div>
	<div>产地主要气象灾害：${meteorologicalDisaster}</div>
	<div>农产品主要病虫害：${diseasesInsectPests}</div>
</div>
</div>
<br/>
<div id="div04" style="background-color: #FFFFFF">
<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
	<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">实施方案审核信息</font></label>
</div>
<div>
	<table>
		<tr>
			<td style="width: 100px">基层专家组</td>
			<td>
				组长签名：${basicPanelSign}<br/>
				审核时间：${basicAuditTime}<br/>
				审核意见：${basicPanelAuditOpinion}<br/>
			</td>
		</tr>
		<tr>
			<td>省级专家组</td>
			<td>
				组长签名：${provincialPanelSign}<br/>
				审核时间：${provincialAuditTime}<br/>
				审核意见：${provincialPanelAuditOpinion}<br/>
			</td>
		</tr>
	</table>
</div>	
</div>
<br/>
<div id="div05" style="background-color: #FFFFFF">
<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
	<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">专家组信息</font></label>
</div>
<div>
	<table>
		<tr>
			<td style="width: 100px">基层专家组</td>
			<td>
				专家组名称：${bgroupName}<br/>
				组长：${bleader}<br/>
				成员：${bpeople}<br/>
			</td>
		</tr>
		<tr>
			<td>省级专家组</td>
			<td>
				专家组名称：${pgroupName}<br/>
				组长：${pleader}<br/>
				成员：${ppeople}<br/>
			</td>
		</tr>
	</table>
</div>	
</div>
<br/>
<div id="div06" style="background-color: #FFFFFF">
<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
	<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">专家打分信息</font></label>
</div>
<div style="margin-top: 10px;">
	<table class="gvtable">
			<thead>
				<tr>
					<th width="10%">生育期</th>
					<th width="30%">时间范围</th>
					<th width="10%">气象因子</th>
					<th width="10%">最适范围</th>
					<th width="20%">实测气象条件</th>
					<th width="10%">专家评级</th>
					<th width="10%">具体分值</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${Egradeslist}" var="item">
					<tr title="bearInfo" id="${item.id }">
						<td rowspan="${item.grades.size() +1}">${item.name }</td>
						<td rowspan="${item.grades.size() +1}"><fmt:formatDate
								value="${item.start}" pattern="yyyy-MM-dd" />~<fmt:formatDate
								value="${item.end}" pattern="yyyy-MM-dd" /></td>
					</tr>
					<c:forEach items="${item.grades}" var="grade">
						<tr>
							<td>${grade.element}</td>
							<td>${grade.optimalRange }</td>
							<td>${grade.realCondition }</td>
							<td>${grade.gradeLevel }</td>
							<td>${grade.gradeValue }</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</tbody>
		</table>
</div>	
</div>
<br/>
<div id="div06" style="background-color: #FFFFFF">
<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
	<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">品质档案信息</font></label>
</div>
<div>
	<table>
		<tr><td>产品名称：</td> <td>${report_pName}</td></tr>
		<tr><td>产地：</td> <td>${report_pArea}</td></tr>
		<tr><td>认证等级：</td> <td>${report_pName}</td></tr>
		<tr><td>认证时间：</td> <td>${report_level}</td></tr>
		<tr><td>认证机构：</td> <td>${report_time}</td></tr>
		<tr><td>认证结论：</td> <td>${report_agency}</td></tr>
		<tr><td>专家签名照：</td> <td><div id="imgContainer" style="height: auto;min-height: 150px">
					</div><%-- <img src="<%=path%>${report_attachmentUrl}"width="120px" height="150px"> --%></td></tr>
		<tr><td>认证证书：</td> <td><div id="imgSign" style="height: auto;min-height: 150px">
					</div><%-- <img src="<%=path%>${report_certificateImage}"width="120px" height="150px"> --%></td></tr>
		<tr><td>认证报告：</td> <td><a style="width: auto" href="<%=path%>${report_certificateAttachment}" target="_blank">${report_ifOther}</a></td></tr>
	</table>
</div>
</div>
<br/>
<div id="div00" style="background-color: #FFFFFF">
<form id="form1" action="<%=basePath%>file_recode/add" method="post">	
	<div style="width: 100%; height: 50px;background: #8cbeef;text-align: left;">
		<label style="margin-left: 10px;text-align: center;position:relative; top:25%;"><font size="+1.5px">添加归档信息</font></label>
	</div>
	<div>
		<input id="applyBh" name="applyBh" type="hidden" value="${applyBh}"/>
		<table>	
			<tr>
				<td>项目名称</td>
				<td><input type="text" id="proName" name = "proName" value="${productName}"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>档案编号</td>
				<td><input type="text" id="proNo" name = "proNo"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>归档人</td>
				<td><input type="text" id="Person" name = "Person"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>归档时间</td>
				<td><input type="text" id="protime" name = "protime" onfocus="WdatePicker()"/><span style="color: #FF0000">*</span></td>
			</tr>
			<tr>
				<td>备注</td>
				<td><textarea id="remark" name="remark" rows="5" cols="50" ></textarea></td>
			</tr>	
		</table>
	</div>
	<br/>
	<div>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="提交" onclick="btnOK()" class="btn_blue" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="返回" onclick="back()" class="btn_blue" />
	</div>
</form>
</div>
</body>
</html>