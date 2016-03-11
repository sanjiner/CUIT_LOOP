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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />

<title>评分列表</title>
<style type="text/css">
#thisBody{
	position: relative;
	margin:0 auto;
	z-index: 100;
	left: 40%;
  	top: 200px;
}
#thisBody .spinner{
	position: relative;
	
}
</style>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/highcharts.js"></script>
<script type="application/javascript" src="<%=basePath%>res/js/spin.js"></script>
<script type="text/javascript">
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
		for (i = 0; i < val.length; i++) {
			array[i] = parseFloat(val[i]);
		}
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
							enabled : true
						//在数据点上显示对应的数据值 
						},
						enableMouseTracking : false
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
	/*
		[
			 {
		        "name": "",
		        "time": "",
		        "socrs": [
		            {
		                "平均气温": "",
		                "最适范围": "",
		                "等级": "",
		                "分值": ""
		            } 
		        ]
		    }
		]
	*/
	function submit(){
		var flag = true;
		$("input").each(function(){
			if($(this).val() == ""){
				alert("请完成所有数据的录入");
				flag = false;
				return false;
			}
		});
		if(flag == false)
			return;
		var cflag = document.getElementById('flag').value;
		if(cflag != "T"){
			alert("请先生成气候环境信息");
			return;
		}
		var json = "[";
		$("[title='bearInfo']").each(function(){
			//生育期名字、时间
			json +="{\"infoId\":\""+$(this).attr("id")+"\",\"name\":\""+$(this).find("#tdBearName").text()+"\""+",\"time\":\""+$("#tdTime").text()+"\",";
			json+="\"scores\":["
			$(this).nextUntil("[title='bearInfo']").each(function(){
				//要素名称、最适范围、实测条件、等级、分值
				//tdElementName、tdOptimum、tdReal、select_level、textScore
				json +="{\"element\":\""+$(this).find("#tdElementName").text()+"\",";
				json +="\"optimum\":\""+$(this).find("#tdOptimum").val()+"\",";
				json +="\"real\":\""+$(this).find("#tdReal").text()+"\",";
				json +="\"level\":\""+$(this).find("#select_level option:selected").text()+"\",";
				json +="\"score\":\""+$(this).find("#textScore").val()+"\"},";
			});
			json = json.substring(0,json.length - 1);
			json += "]},";
		});
		json = json.substring(0,json.length - 1);
		json +="]";
		$.ajax({
			type:"POST",
			url:"<%=basePath%>expert_grade/grade",
			data : {
				"json" : json,
				"applyBh" : "${applyBh}"
			},
			dataType : "",
			success : function(data) {
				if (data.success == "true"){
					alert("评分成功");
					window.location.href="<%=basePath%>expert_grade/list";
				}
				else
					alert("评分失败");
			}
		});
	}
	
	 //opts 可从网站在线制作
    var opts = {            
        lines: 10, // 花瓣数目
        length: 20, // 花瓣长度
        width: 10, // 花瓣宽度
        radius: 30, // 花瓣距中心半径
        corners: 1, // 花瓣圆滑度 (0-1)
        rotate: 0, // 花瓣旋转角度
        direction: 1, // 花瓣旋转方向 1: 顺时针, -1: 逆时针
        color: '#000', // 花瓣颜色
        speed: 1, // 花瓣旋转速度
        trail: 60, // 花瓣旋转时的拖影(百分比)
        shadow: false, // 花瓣是否显示阴影
        hwaccel: false, //spinner 是否启用硬件加速及高速旋转            
        className: 'spinner', // spinner css 样式名称
        zIndex: 2e9, // spinner的z轴 (默认是2000000000)
        top: 'auto', // spinner 相对父容器Top定位 单位 px
        left: 'auto'// spinner 相对父容器Left定位 单位 px
    };

    var spinner = new Spinner(opts);	
	
function addClimatopeInfo() {
	var qId = document.getElementById('qualityId').value;
	var flag = document.getElementById('flag').value;
	var url = "<%=basePath%>climate/add?qid=" + qId;
	var target = document.getElementById('thisBody');
	spinner.spin(target);  
		$.ajax({
			type : "post",
			data : {
				'qid' : qId,
				'path' : url
			},
			url : url,
			success : function(data) {
				spinner.spin();
				if (data.success == "T") {
					document.getElementById('flag').value = "T";
					alert("生成成功！");
				} else {
					alert("生成失败！");
				}
				document.getElementById("btnOK").setAttribute("class", "btn_blue");
				document.getElementById("btnOK").disabled = "";
			}
		});
	}

function getModule(){
	//http://localhost:8080/ProduceSystem
	var moduleId = $("#select_moduel option:selected").val();
	$("#linkModule").attr("href",'<%=basePath%>expert_grade/grade?applyBh=${applyBh}&mId='
						+ moduleId);
	}
</script>
</head>
<body>
	<div
		style="background: #4c9ee7; width: 100%; height: 30px; margin-bottom: 5px">
		<label
			style="width: auto; height: 30px; text-align: center; color: white; line-height: 30px; margin-left: 5px">气候品质认证>>专家评分>>产品评分</label>
	</div>
	<div id="thisBody"></div>
	<div>
		<label>请为产品选择认证模型：</label> <select id="select_moduel"
			onchange="getModule()">
			<c:if test="${Modules.size()<=0 }">
				<option>未创建认证模型</option>
			</c:if>
			<c:forEach items="${Modules }" var="item">
				<option value="${item.productApproModelId }"
				<c:if test="${mId == item.productApproModelId}">
				selected="selected"
				</c:if>>${item.modelName }</option>
			</c:forEach>
		</select> <a class="btn_blue" id="linkModule">确定</a>
	</div>
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
			<c:forEach items="${list}" var="item">
				<tr title="bearInfo" id="${item.id }">
					<td id="tdBearName" rowspan="${item.elements.size() +1}">${item.name }</td>
					<td id="tdTime" rowspan="${item.elements.size() +1}"><fmt:formatDate
							value="${item.start}" pattern="yyyy-MM-dd" />~<fmt:formatDate
							value="${item.end}" pattern="yyyy-MM-dd" /></td>
				</tr>
				<c:forEach items="${item.elements}" var="element">
					<tr>
						<td id="tdElementName">${element.name}</td>
						<td><input id="tdOptimum" type="text"
							style="width: 90%; height: 90%" value='${element.preInstall }' />
						</td>
						<td id="tdReal">${element.average }</td>
						<td><select id="select_level">
								<option>最适宜</option>
								<option>适宜</option>
								<option>次适宜</option>
						</select></td>
						<td><input id="textScore" type="text"
							style="width: 90%; height: 90%" /></td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>	
	<input type="hidden" id="qualityId" value="${qualityId}" />
	<input type="hidden" id="flag" value="F" />
	<center>
		<input type="button" class="btn_blue" value="生成气候环境信息"
			onclick="addClimatopeInfo()" style="margin-top: 10px; width: 150px" />&nbsp;&nbsp;&nbsp;&nbsp;
		<input id="btnOK" type="button" value="提交" onclick="submit()"
			style="width:100px;height:30px; margin-top: 10px" disabled="disabled"/>
	</center>
	<div id="chart_line"></div>
</body>
</html>

