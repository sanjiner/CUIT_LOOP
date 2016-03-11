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

<title>专家分配</title>
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<style type="text/css">
.left {
	width: 200px;
	text-align: right;
}

.right {
	width: 300px;
	text-align: left;
}
</style>
<script type="text/javascript">
	var applyBh;
	function validate() {
		if ($("#edit_search_key").val() == "") {
			alert("请输入要查找的产品名称");
			return false;
		}
		return true;
	}
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
	function addValidate() {
		if (applyBh == "undefined" || applyBh == null) {
			alert("请在左边选择项目");
			return false;
		} else {
			return true;
		}
	}
	$(document).ready(function(){
		getMembers("${expertsId}");
	});
	function getMembers(expertsId){
	$.get("<%=basePath%>expert_assign/getMembers?expertsId="+ expertsId,
						function(data, status) {
							var obj = eval(data);
							if (obj.members != null) {
								var array = obj.members;
								for (var i = 0; i < array.length; i++) {
									var item = array[i];
									var checkbox = "<div><input type = \"checkbox\" text=\"123\"  style=\" text-align: left;\"  value=\""+item.expertNo+"\">"
									+ item.expertname + "</div>";
									//如果当前选择的id跟原id相同，则判断原来的组员
									if(expertsId == "${expertsId}"){
										var members = "${memberStr}";
										var mArray = members.toString().split(',');
										for (var j = 0; j < array.length; j++) {
											var flag = false;
											var str = mArray[j];
											if (str == item.expertNo) {
												flag = true;
												break;
											} 
										}
										if(flag){
											var checkbox = "<div><input type = \"checkbox\" text=\"123\" checked=\"true\"  style=\" text-align: left;\"  value=\""+item.expertNo+"\">"
											+ item.expertname + "</div>";
										} 
									}
									$("#member_container").append(checkbox);
									var radio = "<input type = \"radio\" name = \"grouper\" value=\""+item.expertNo+"\">"
									+ item.expertname;									
									if("${grouper}" == item.expertNo && expertsId == "${expertsId}"){
										radio = "<input type = \"radio\" checked=\"true\" name = \"grouper\" value=\""+item.expertNo+"\">"
										+ item.expertname;
									}
									$("#grouper_container").append(radio);
								}
							}
						});
	}
	function group_change() {
		$("#member_container").empty();
		$("#grouper_container").empty();
		$("#textGroupName").text($("#group_experts option:selected").text());
		getMembers($("#group_experts option:selected").val());
		
	}

	function doSubmit() {
		var members = "";
		var children = $("#member_container").children().length;
		$("#member_container [type='checkbox']:checked").each(function(){
			members += $(this).val()+",";
		});
		if(members.length <= 0){
			alert("请至少选择一个成员");
			return false;
		}		
		var grouper = "";
		var applyBh = "${applyBh}";
		grouper = $("input[name='grouper']:checked").val();
		if(grouper == undefined || grouper.length <= 0){
			alert("请选择组长");
			return false;
		}
		if(!contains(members,grouper,true)){
			alert("组长不在选择的成员中,请重新确定组长");
			return false;
		}
		
		 $.ajax({
             type: "post",
             url: "<%=basePath%>expert_assign/update",
             dataType: "html",
             data: {"expertsNum":"${entity.expertsNum}","applyBh":applyBh,"members": members ,"groupName" :$("#textGroupName").text(),"groupRemarks":$("#textMemo").val(),
            	 "grouper":grouper},
             success:function(data,status){
            	 var obj = eval('(' + data + ')');
            	 if(obj.success == "true"){
            		 alert("更新成功");
            		 window.location.href = "<%=basePath%>expert_assign/list?applyBh="+ applyBh;
						} else {
							alert("更新失败");
						}
					},
					error : function(msg) {
						alert(msg.responseText);
					}
				});
	}
	function contains(string, substr, isIgnoreCase) {
		if (isIgnoreCase) {
			string = string.toLowerCase();
			substr = substr.toLowerCase();
		}
		var startChar = substr.substring(0, 1);
		var strLen = substr.length;
		for (var j = 0; j < string.length - strLen + 1; j++) {
			if (string.charAt(j) == startChar) //如果匹配起始字符,开始查找
			{
				if (string.substring(j, j + strLen) == substr) //如果从j开始的字符与str匹配，那ok
				{
					return true;
				}
			}
		}
		return false;
	}
</script>
</head>

<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">应用中心>>专家分配>>编辑成员</label>
	</div>
	<table style="margin-top: 5px;border: 5px">		<tbody>
			<tr>
				<td class="left">专家组名称</td>
				<td class="right" id="textGroupName">${entity.expertsClass }</td>
			</tr>
			<tr>
				<td class="left">专家组层次</td>
				<td class="right">${entity.expertsLevel == '1012' ? '省级专家'  : '基层专家'}</td>
			</tr>
			<tr>
				<td class="left">请选择专家组</td>
				<td class="right"><select style="width: 100%"
					id="group_experts" onchange="group_change()">
						<c:forEach items="${experts}" var="expert">
							<option value="${expert.expertsId }" <c:if test="${expert.expertsId == expertsId}">selected="selected"</c:if>>${expert.expertsName}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="left">专家组成员</td>
				<td class="right" id="member_container"></td>
			</tr>
			<tr>
				<td class="left">专家组组长</td>
				<td class="right" id="grouper_container"></td>
			</tr>
			<tr height="100px">
				<td class="left">备注</td>
				<td class="right"><textarea id="textMemo"
						style="width: 99%; height: 99%">${entity.remark }</textarea></td>
			</tr>
			<tr>
				<td class="left"><input type="button" class="btn_blue"
					onclick="doSubmit()" value="提交"></td>
				<td class="right"><a type="button" class="btn_blue" href="<%=basePath %>expert_assign/list?applyBh=${applyBh}"
					>取消</a>
			</tr>
		</tbody>
	</table>
</body>

</html>
