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

<title>添加气象灾害数据</title>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/calendar.js"></script>
<script type="text/javascript" src="<%=basePath%>/res/js/public.js"></script>

<script type="text/javascript"
	src="<%=basePath%>/res/js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	function edit(){
		if($("#select_station option:selected").val() == "0"){
			alert("必须选择气象站才能添加数据");
			return;
		} 
		if($("#textDescript").val() == ""){
			alert("必须添加描述信息");
			return;
		}
		if(getQueryString("code") == null){
			alert("获取项目信息错误,请重新进入添加页面");
			return;
		}
		$.ajax({
			 type: "POST",
			 url:"<%=basePath%>autc_data/weather_edit",
			data : {
				"id":"${entity.dataCode}",
				"code" : getQueryString("code"),
				"station" : $("#select_station option:selected").text(),
				"descript" : $("#textDescript").val(),
				"memo" : $("#memo").val()
			},
			dataType : "html",
			success : function(data) {
				var data = eval("("+data+")");
				if(data.success == "true"){
					alert("编辑成功");
					 window.location.href = "<%=basePath%>autc_data/weather?id="+getQueryString("code");
				}else{
					alert("编辑失败");
				}
			}
		});
	}
	function back() {
		window.location.href ="<%=basePath%>autc_data/weather?id="
				+ getQueryString("code");
	}
</script>
</head>
<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">应用中心>>数据管理>>编辑数据</label>
	</div>
	<table
		style="background:#edf1fc; padding: 10px; width: 100%; text-align: center;">
		<tbody>
			<tr>
				<td style="text-align: right; width: 200px">数据来源：</td>
				<td style="text-align: left"><select id="select_station">
						<c:choose>
							<c:when test="${stations.size() > 0 }">
								<c:forEach items="${stations}" var="item">
									<option value="item.weatherStationInfoId">${item.name}</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="0">无气象站信息</option>
							</c:otherwise>
						</c:choose>
				</select>
			</tr>
			<tr>
				<td style="text-align: right; width: 200px">气象灾害描述：</td>
				<td style="text-align: left;"><textarea id="textDescript"
						style="height: 200px; margin-right: 5px; width: 300px">
						${entity.disastoursDescription}
						</textarea></td>
			</tr>
			<tr>
				<td style="text-align: right; width: 200px">备注：</td>
				<td style="text-align: left;"><textarea id="memo"
						style="height: 100px; margin-right: 5px; width: 300px">
						${entity.remark}
						</textarea></td>
			</tr>
			<tr style="width: auto; text-align: center;">
				<td style="text-align: right;"><input type="button" value="提交"
					class="btn_blue" onclick="edit()"></td>
				<td style="text-align: left;"><a onclick="back()"
					class="btn_blue">返回</a></td>

			</tr>
		</tbody>
	</table>
</body>
</html>