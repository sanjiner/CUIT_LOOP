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
<title>四川省农产品气候品质认证中心标签查询</title>
<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/highcharts.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=jHyMKjlbG2mFtbAYWdQpWYRB"></script>
<script type="text/javascript">
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

function goCH(){
	var labelId = document.getElementById("labelQueryId").innerHTML;
	window.location.href = "<%=basePath%>labelscan/ScanResultPC?id="+labelId;
}
</script>
</head>
<body bgcolor="#cccccc" style="margin-left:15%; margin-right: 15%">
 	<div>
 		<img src="<%=basePath%>res/images/labelscan/${logoUrl}" style="height: 20%;width: 100%">
 	</div>
 	<div style="text-align: right;"><input type="button" value="中文版" onclick="goCH()"/></div>
 	<br/>
 	<div style="display: none"><label id="labelId">${labelInfo.labelId}</label></div>
 	<div style="display: none"><label id="labelApplyBH">${labelInfo.applyBh}</label></div>
 	<div style="display: none"><label id="originID">${labelInfo.applyOriginCode}</label></div>
 	<div style="display: none"><label id="labelQueryId">${labelInfo.labelQueryId}</label></div>
 	<div style="display: none"><label id="addressNum">${scanNum}</label></div>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >LabelInfo</label></div>
 	<div style="background-color: #FFFFFF"> 
 		<table>
 			<tr><td>Query code:${labelInfo.labelQueryId}</td></tr>
 			<tr><td>Label status:${labelInfo.labelStatus}${cautious}</td></tr>
 			<tr><td>Label format:${format}</td></tr>
 			<tr><td>Scan times:${scanNum}</td></tr>
 			<tr><td>First time:${scantime1}</td></tr>
 			<tr><td>Address：<label id="txtaddress1">${address1}</label></td></tr>
 			<tr><td>Second time:${scantime2}</td></tr>
 			<tr><td>Address:<label id="txtaddress2">${address2}</label></td></tr>
 			<tr><td>Last time:${scantimen}</td></tr>
 			<tr><td>Address:<label id="txtaddressn">${addressn}</label></td></tr>
 			<tr><td>The batch total：${labelNum}枚</td></tr>
 			<tr><td>Please check whether the product packaging two-dimensional code below the security code is the same as this page, tag query code, or log on to the Sichuan rural information network<a href="http://zs.scnjw.gov.cn">http://zs.scnjw.gov.cn</a> 
                          Carry out the code to identify the authenticity of the code.</td></tr>
 		</table>
 	</div>
 	<br/>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >ProductImage</label></div>
 	<div style="background-color: #FFFFFF;text-align:center;"> 
 		<table style="margin: auto;">
 			<tr><td><img alt="" style="width: 70%" src="<%=basePath2%>${imgURL}"></td></tr>
 		</table>
 	</div>
 	<br />
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >ProductInfo</label></div>
 	<div style="background-color: #FFFFFF"> 
 		<table>
 			<tr><td>Product name:${productName}</td></tr>
 			<tr><td>Product brand:${productBrand}</td></tr>
 			<tr><td>Merchant:<a href="<%=basePath%>bussiness_center/center?id=${businessId}">${businessName}</a></td></tr>
 			<tr><td>Contact phone:${phone}</td></tr>
 			<tr><td>Place of Production:${productBase}</td></tr>
 			<tr><td>Address:${address}</td></tr>
 			<tr><td>Typical product features:${description}</td></tr>
 		</table>
 	</div>
 	<br/>
 	<div style="${divStyle}">
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >ClimateInfo</label></div>
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
 	</div>
 	<br/>
 	</div>
 	<div style="${divStyle}">
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >OriginImage</label></div>
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
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >Quality certification</label></div>
 	<div style="background-color: #FFFFFF">
 		<table>
 			<tr><td>Product name:${report_pName}</td></tr>
 			<tr><td>Address:${report_pArea}</td></tr>
 			<tr><td>Authentication level:${report_level}</td></tr>
 			<tr><td>Authentication time:${report_time}</td></tr>
 			<tr><td>Authentication mechanism:${report_agency}</td></tr>
 			<tr><td>Authentication conclusion:${report_conclusion}</td></tr>
 		</table>
 	</div>
 	<br/>
 	</div>
 	<div style="${divStyle}">
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >Qualificate</label></div>
 	<div style="background-color: #FFFFFF">
 		<table style="margin:auto;">
 			<tr><td><img alt="" style="width: 70%" src="<%=basePath2%>${certificateImage}"></td></tr>
 		</table>
 	</div>
 	<br/>
 	</div>
 	<div><label style="font-size: x-large; font-weight: 700; background-color: #01733F; color: #FFFFFF;" >Relevant qualification certificates</label></div>
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
 			<tr><td>ICP 11018099 -1</td></tr>
 			<tr><td>Copyright:Sichuan Rural Economic Information Center</td></tr>
 			<tr><td>Business advisory telephone:028-87360982 028-87345251</td></tr>
 			<tr><td>Email:scnjw@sina.com scnjw@foxmail.com</td></tr>
 			<tr><td>Address: Guanghua village street Qingyang District No. 20 (Sichuan Province Chengdu Sichuan Province Bureau of Meteorology tower)</td></tr>
 		</table>
 	</div>
</body>
</html>