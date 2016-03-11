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

<title>数据列表</title>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/highcharts.js"></script>

<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/calendar.js"></script>

<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
	
	function getData() {
		var beginDate=$("#textStart").val();  
		var endDate=$("#textEnd").val();  
		var d1 = new Date(beginDate.replace(/\-/g, "\/"));  
		var d2 = new Date(endDate.replace(/\-/g, "\/"));  
		if(beginDate!=""&&endDate!=""&&d1 >=d2)  {  
			  alert("开始时间不能大于结束时间！");  
			  return false;  
		  }
		$.ajax({
			url:"<%=basePath%>autc_data/getData",
			type : "GET",
			data : {
				"start" : $('#textStart').val(),
				"end" : $("#textEnd").val(),
				"modelId" : $("#select_element option:selected").val(),
				"stationId" : "${stationId}",
				"code" : getQueryString("id")
			},
			success : function(data) {
				//获取到返回数据，绘制图形
				getCharts(data);
			}
		});
	}

	function getCharts(source) {
		source = eval(source);
		var val = source.value;
		var array = new Array();
		for (i =0; i < val.length; i++) {
			array[i] = parseFloat(val[i]);
		}
		interval = 30;
		var chart;
		$(function() {
			chart = new Highcharts.Chart({
				chart : {
					renderTo : 'chart_line', //图表放置的容器，DIV 
					defaultSeriesType : 'line', //图表类型line(折线图), 
					zoomType : 'x' //x轴方向可以缩放 
				},
				credits : {
					enabled : false
				},
				title : {
					text : $("#select_element option:selected").text()
				},
				subtitle : {
					text : $("#textStart").val() + "~" + $("#textEnd").val()
				},
				xAxis : { //x轴 
					tickInterval:interval,
					categories : source.time, //x轴标签名称 
					gridLineWidth : 1, //设置网格宽度为1 
					lineWidth : 2, //基线宽度 
					labels : {
						y : 26
					}
				//x轴标签位置：距X轴下方26像素 
				},
				yAxis : { //y轴 
					title : {
						text : '平均气温(°C)'
					}, //标题 
					lineWidth : 2
				//基线宽度 
				},
				plotOptions : { //设置数据点 
					line : {
						dataLabels : {
							enabled : false
						//在数据点上显示对应的数据值 
						},
						marker:{
							enabled : false
						},
						enableMouseTracking : true
					//取消鼠标滑向触发提示框 
					}
				},
				legend : { //图例 
					layout : 'horizontal', //图例显示的样式：水平（horizontal）/垂直（vertical） 
					backgroundColor : '#ffc', //图例背景色 
					align : 'left', //图例水平对齐方式 
					verticalAlign : 'top', //图例垂直对齐方式 
					x : 100, //相对X位移 
					y : 70, //相对Y位移 
					floating : true, //设置可浮动 
					shadow : true
				//设置阴影 
				},
				exporting : {
					enabled : false
				//设置导出按钮不可用 
				},
				series : [ { //数据列 
					name : source.unit,
					data : array
				} ]
			});
		});
	}
	
	function back_f(){
		window.location.href="<%=basePath%>/autc_data/list"
	}
</script>
</head>

<body>
	<div
		style="background: #4c9ee7; width: 100%; height: 30px; margin-bottom: 5px">
		<label
			style="width: auto; height: 30px; text-align: center; color: white; line-height: 30px; margin-left: 5px">应用中心>>数据管理>>要素过程查询</label>
	</div>
	<div>
		<label><b>日期:</b></label> <input id="textStart" value="开始日期"
			onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})"> ~
		<input id="textEnd" value="结束日期"
			onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})"> <select
			id="select_element">
			<c:if test="${elements.size()  <= 0 }">
				<option>无要素信息</option>
			</c:if>
			<c:forEach items="${elements}" var="element">
				<option value="${element.elementNumber }">${element.elementName }</option>
			</c:forEach>
		</select><input style="margin-left: 5dp" type="button" value="查询"
			class="btn_blue" onclick="getData()"> <input type="button"
			value="返回" class="btn_blue" onclick="back_f()">
	</div>
	<div id="chart_line"></div>
</body>

</html>
