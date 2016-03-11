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
<title>结果展示</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>res/js/highcharts.js"></script>
<script type="text/javascript">
getChart();
function getChart(){	
	var monYlist = ${monYlist};
	var monXlist = ${monXlist};
	var montitle = ${montitle};
	var monsubtitle = ${monsubtitle};
	var monYname = ${monYname};
	var monXname = ${monXname};
	drawChart(monYlist,monXlist,montitle,monsubtitle,monYname,monXname,'chart1');
	
	var proYlist = ${proYlist};
	var proXlist = ${proXlist};
	var protitle = ${protitle};
	var prosubtitle = ${prosubtitle};
	var proYname = ${proYname};
	var proXname = ${proXname};
	drawChart(proYlist,proXlist,protitle,prosubtitle,proYname,proXname,'chart2');
	
	var numYlist = ${numYlist};
	var numXlist = ${numXlist};
	var numtitle = ${numtitle};
	var numsubtitle = ${numsubtitle};
	var numYname = ${numYname};
	var numXname = ${numXname};
	drawChart(numYlist,numXlist,numtitle,numsubtitle,numYname,numXname,'chart3');
	
	var piciYlist = ${piciYlist};
	var piciXlist = ${piciXlist};
	var picititle = ${picititle};
	var picisubtitle = ${picisubtitle};
	var piciYname = ${piciYname};
	var piciXname = ${piciXname};
	drawChart(piciYlist,piciXlist,picititle,picisubtitle,piciYname,piciXname,'chart4');
}
function drawChart(valueY,valueX,title,subtitle,Yname,Xname,divChart){
	var chart;
	$(function() { 
		chart = new Highcharts.Chart({ 
			chart: { 
				renderTo: divChart, //图表放置的容器，DIV 
				defaultSeriesType: 'column',  
			}, 
			credits: { 
				enabled: false   //右下角不显示LOGO 
			}, 
			title: { 
				text: title //图表标题 
			}, 
			subtitle: { 
				text: subtitle  //副标题 
			}, 
			xAxis: {  //x轴 
				 categories: valueX 
			}, 
			yAxis: {  //y轴 
				min: 0,
				title: {
					text: Yname
				}
			}, 
			plotOptions:{ //设置数据点 
				column: {
					pointPadding: 0,
					borderWidth: 0
				}
			},  
			series: [{
            name: Xname,
            data: valueY
			}]
		});
	}); 
}

function back() {
	window.location.href = "<%=basePath%>ScanAnalysis/list";
}
</script>
</head>
<body bgcolor="#F2F2F2">
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">认证标签管理>>二维码扫描分析>>结果展示</label>
</div>
<br/>
&nbsp&nbsp${name}二维码扫描情况分析&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp申请时间：${time}<br/>
<div style="background: #FFFFFF;margin-left: 1%;margin-right: 1%" >
	<div id="chart1" style="margin-left: 5%;margin-right: 5%"></div>
	<br/>
</div>
<br/>
<div style="background: #FFFFFF;margin-left: 1%;margin-right: 1%" >
	<div id="chart2" style="margin-left: 5%;margin-right: 5%"></div>
	<br/>
</div>
<br/>
<div style="background: #FFFFFF;margin-left: 1%;margin-right: 1%" >
	<div id="chart3" style="margin-left: 5%;margin-right: 5%"></div>
	<br/>
</div>
<br/>
<div style="background: #FFFFFF;margin-left: 1%;margin-right: 1%" >
	<div id="chart4" style="margin-left: 5%;margin-right: 5%"></div>
	<br/>
</div>
<br/>
<div style="text-align: center;"><input type="button" value="返回" onclick="back()" class="btn_blue" /></div>
</body>
</html>