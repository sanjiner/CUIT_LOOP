<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String basePath2 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<title>四川省农产品气候品质认证中心标签查询</title>
<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>res/js/highcharts.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=jHyMKjlbG2mFtbAYWdQpWYRB"></script>
<script type="text/javascript">

$(document).ready(function(){
		var imags = "${certificateImage}";
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

getChart();
function getChart(){
	var xlist = new Array(); 
	var ylist = new Array(); 
	var divchar = "chart_line";
	var eName;
	var symbol;
	var unit;
	var xlen = ${xmLen};
	<c:forEach var="clist" items="${climatelist}" varStatus="status">	
		ylist = [${clist.avgValues}];
		xlist = [${clist.months}];
		divchar = "chart_line" + "${status.index}";
		eName = "${clist.meteorologicalfactor}";
		symbol = "${clist.symbol}";
		unit = "${clist.unit}";
		drawchart(xlist,ylist,divchar,eName,symbol,unit,xlen);
	</c:forEach>
}

function drawchart(xValue, yValue,divchar,eName,symbol,unit,xlen){
	var chart;
	$(function() { 
		chart = new Highcharts.Chart({ 
			chart: { 
				renderTo: divchar, //图表放置的容器，DIV 
				defaultSeriesType: 'line', //图表类型line(折线图), 
				zoomType: 'x'   //x轴方向可以缩放 
			}, 
			credits: { 
				enabled: false   //右下角不显示LOGO 
			}, 
			title: { 
				text: eName //图表标题 
			}, 
			subtitle: { 
				text: ''  //副标题 
			}, 
			xAxis: {  //x轴 
				tickInterval: xlen,
				categories: xValue, 
				labels:{y:26}  //x轴标签位置：距X轴下方26像素 
			}, 
			yAxis: {  //y轴 
				title: {text: eName+"("+unit+")"}, //标题 
				lineWidth: 2 //基线宽度 
			}, 
			plotOptions:{ //设置数据点 
				line:{ 
					marker: {
                        enabled: false
                    }, 
					enableMouseTracking: false //取消鼠标滑向触发提示框 
				} 
			}, 
			legend: {  //图例 
				layout: 'horizontal',  //图例显示的样式：水平（horizontal）/垂直（vertical） 
				backgroundColor: '#ffc', //图例背景色 
				align: 'left',  //图例水平对齐方式 
				verticalAlign: 'top',  //图例垂直对齐方式 
				x: 100,  //相对X位移 
				y: 70,   //相对Y位移 
				floating: true, //设置可浮动 
				shadow: true  //设置阴影 
			}, 
			exporting: { 
				enabled: false  //设置导出按钮不可用 
			}, 
			series: [{  //数据列 
				name: symbol, 
				data: yValue,
			}] 
		}); 
	}); 
}

//百度地图API功能
var point = new BMap.Point(103.996723,30.586552);	
var geolocation = new BMap.Geolocation();		
geolocation.getCurrentPosition(function(r){
	if(this.getStatus() == BMAP_STATUS_SUCCESS){
		point = r.point;
		var geoc = new BMap.Geocoder();
		geoc.getLocation(point, function(rs){
		var addComp = rs.addressComponents;
		
		var province = addComp.province;
		var city = addComp.city;
		var district = addComp.district;
		var street = addComp.street;
		var streetNumber = addComp.streetNumber;
		var address = city + district + street + streetNumber;
		
		//设置地址
		var addressNum = document.getElementById("addressNum").innerHTML;
		if(addressNum == "1"){
			document.getElementById("txtaddress1").innerHTML = address;
		}else if(addressNum == "2"){
			document.getElementById("txtaddress2").innerHTML = address;
		}
		else{
			document.getElementById("txtaddressn").innerHTML = address;
		}
		
		//更新扫描记录 
		var labelId = document.getElementById("labelId").innerHTML;
		var labelApplyBh =  document.getElementById("labelApplyBH").innerHTML;
		var originID =  document.getElementById("originID").innerHTML;
		var url =  "<%=basePath%>labelscan/ScanResult";
		$.ajax({
            type: "post",
            data: { 'labelId':labelId,'province':province,'city':city,'district':district,
            	'address': address,'labelApplyBh': labelApplyBh,'originID':originID},
            url: url,
            success: function (data) {
            	//alert(labelId);
            }
        });
	  }); 
	}
	else {
		alert('failed'+this.getStatus());
	}        
},{enableHighAccuracy: true})

function goEN(){
	var labelId = document.getElementById("labelId").innerHTML;
	var languageType = document.getElementById("select01").value;
	window.location.href = "<%=basePath%>labelscan/ScanResultEN?id="+labelId+"&lan="+languageType;
}

</script>
</head>
<body bgcolor="#cccccc" style="margin-left:1%; margin-right: 1%">
 	<div>
 		<img src="<%=basePath%>res/images/labelscan/${logoUrl}" style="height: 20%;width: 100%">
 	</div>
 	<div style="${divStyle};text-align: right;">
 		<select id="select01">
 			<c:if test="${languagelist.size() > 0}">
				<c:forEach var="lanlist" items="${languagelist}">
					<option value="${lanlist.languageType}">${lanlist.languageType}</option>
				</c:forEach>
			</c:if>
			<c:if test="${languagelist.size() == 0}">
				<option value="0"></option>
			</c:if>
 		</select>
 		<input type="button" value="go" onclick="goEN()"/>
 	</div>
 	<br/>
 	<div style="display: none"><label id="labelId">${labelInfo.labelId}</label></div>
 	<div style="display: none"><label id="labelApplyBH">${labelInfo.applyBh}</label></div>
 	<div style="display: none"><label id="originID">${labelInfo.applyOriginCode}</label></div>
 	<div style="display: none"><label id="addressNum">${scanNum}</label></div>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >标签信息</label></div>
 	<div style="background-color: #FFFFFF"> 
 		<table>
 			<tr><td>标签查询码：${labelInfo.labelQueryId}</td></tr>
 			<tr><td>标签状态：${labelInfo.labelStatus}${cautious}</td></tr>
 			<tr><td>标签规格：${format}</td></tr>
 			<tr><td>被扫描的次数：${scanNum}</td></tr>
 			<tr><td>第一次扫描的时间：${scantime1}</td></tr>
 			<tr><td>地点：<label id="txtaddress1">${address1}</label></td></tr>
 			<tr><td>第二次扫描的时间：${scantime2}</td></tr>
 			<tr><td>地点：<label id="txtaddress2">${address2}</label></td></tr>
 			<tr><td>最后一次扫描的时间：${scantimen}</td></tr>
 			<tr><td>地点：<label id="txtaddressn">${addressn}</label></td></tr>
 			<tr><td>该批次共发放溯源标签：${labelNum}枚</td></tr>
 			<tr><td>请检查产品外包装二维码下方的防伪码是否与本页“标签查询码”相同，或登录四川农村信息网溯源网站<a href="http://zs.scnjw.gov.cn">http://zs.scnjw.gov.cn</a> 
                           进行输码查询以辨别标签真伪。</td></tr>
 		</table>
 	</div>
 	<br/>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >产品图片</label></div>
 	<div style="background-color: #FFFFFF;text-align:center;"> 
 		<table class="gvtable" style="margin: 10dp">
 		
 			<%-- <tr>
	 			<td>
	 			<img alt="" style="width: 70%" src="<%=basePath2%>${imgURL}">
	 			</td>
 			</tr> --%>
 			<thead style="text-align: center;">
				<tr>
					<th>图片</th>
					<th>图片名称</th>
					<th>上传时间</th>
				</tr>
				</thead>
			<tbody>
				<c:if test="${piclist.size() <= 0}">
					<tr>
						<td colspan="5" style="margin: 5px">暂无数据</td>
					</tr>
				</c:if>
				<c:forEach items="${piclist}" var="item">
					<tr>
						<td><img height="200px" width="150px" src="<%=path %>${item.picsrc }"></td>
						<td>${item.picName }</td>
						<td>${item.time }</td>
					</tr>
				</c:forEach>
			</tbody>
 		</table>
 	</div>
 	<br />
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >产品信息</label></div>
 	<div style="background-color: #FFFFFF"> 
 		<table>
 			<tr><td>产品名称：${productName}</td></tr>
 			<tr><td>产品品牌：${productBrand}</td></tr>
 			<tr><td>所属商家：<a href="<%=basePath%>labelscan/CertiBusinessInfo?id=${applyPerson}">${company}</a></td></tr>
 			<tr><td>联系电话：${phone}</td></tr>
 			<tr><td>产地：${productBase}</td></tr>
 			<tr><td>地址：${address}</td></tr>
 			<tr><td>产品典型特征：${description}</td></tr>
 		</table>
 	</div>
 	<br/>
 	<div style="${divStyle}">
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >气候环境信息</label></div>
 	<div style="background-color: #FFFFFF">
 		<div id="chart_line0"></div>
 		<div id="chart_line1"></div>
 		<div id="chart_line2"></div>
 		<div id="chart_line3"></div>
 		<div id="chart_line4"></div>
 		<div id="chart_line5"></div>
 		<div id="chart_line6"></div>
 		<div id="chart_line7"></div>
 		<div id="chart_line8"></div>
 		<div id="chart_line9"></div>
 		<br/>
 		<div>最佳生长条件：</div>
 		<div>${bestCondition}</div>
 	</div>
 	<br/>
 	</div>
 	<div style="${divStyle}">
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >产地图片</label></div>
 	<div style="background-color: #FFFFFF">
 		<table style="margin:auto;">
 			<c:forEach items="${growthPlaceImagelist}" var="gpilist" varStatus="gpivs">
 				<tr><td><img alt="" style="width: 70%" src="<%=basePath2%>${growthPlaceImagelist.get(gpivs.index)}"></td></tr>	
 			</c:forEach>
 		</table>
 	</div>
 	<br/>
 	</div>
 	<div style="${divStyle}">
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >品质认证</label></div>
 	<div style="background-color: #FFFFFF">
 		<table>
 			<tr><td>产品名称：${report_pName}</td></tr>
 			<tr><td>产地：${report_pArea}</td></tr>
 			<tr><td>认证等级：${report_level}</td></tr>
 			<tr><td>认证时间：${report_time}</td></tr>
 			<tr><td>认证机构：${report_agency}</td></tr>
 			<tr><td>认证结论：${report_conclusion}</td></tr>
 		</table>
 	</div>
 	<br/>
 	</div>
 	<div style="${divStyle}">
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >品质证书</label></div>
 	<div style="background-color: #FFFFFF">
 		<table style="margin:auto;">
 			<tr><td><div id="imgSign" style="height: auto;min-height: 150px">
					</div><%-- <img alt="" style="width: 70%" src="<%=basePath2%>${certificateImage}"> --%></td></tr>
 		</table>
 	</div>
 	<br/>
 	</div>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >相关资质证书</label></div>
 	<div style="background-color: #FFFFFF">
 		<table style="margin:auto;">
 			<c:forEach items="${certiImages}" var="certilist" varStatus="certivs">
 				<tr><td><img alt="" style="width: 70%" src="<%=basePath2%>${certilist.pictureUrl}"></td></tr>	
 			</c:forEach>
 		</table>	
 	</div>
 	<br/>
 	<div style="text-align: center; width: 100%">
 		<table style="text-align: center; width: 100%">
 			<tr><td>蜀ICP备11018099号-1</td></tr>
 			<tr><td>版权所有：四川农村经济综合信息中心</td></tr>
 			<tr><td>业务咨询电话：028-87360982 028-87345251</td></tr>
 			<tr><td>邮箱：scnjw@sina.com scnjw@foxmail.com</td></tr>
 			<tr><td>地址：四川省成都市青羊区光华村街20号（四川省气象局塔楼）</td></tr>
 		</table>
 	</div>
</body>
</html>