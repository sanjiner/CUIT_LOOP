<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
<title>商家列表</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>res/js/jquery-1.11.1.min.js"
	type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	function addFunction(){
		window.location.href = "<%=basePath%>auth_center_info/business_info/add";
	}
	
	//删除功能
	function deleteInfo(id){
		if(confirm("确定要删除这条数据吗？")){
			var url = "<%=basePath%>auth_center_info/business_info/delete";
			$.ajax({
				type : "get",
				url : url,
				dataType : "html",
				data : { 'primaryKey': id},
				success : function(data) {
					if(data == "T"){
						window.location.href = "<%=basePath%>auth_center_info/business_info/list";
					}
				},
				cache : false,
				error : function(msg) {
					alert(msg.responseText);
				}
			});
		 }
	}
</script>
</head>
<body>
	<div class="container">
		<div class="" style="margin: 0 auto;">
			<table>
				<tr>
					<td>
						<form action="<%=basePath%>auth_center_info/business_info/list"
							name="form" method="post">
							单位名称：<input type="text" id="queryString" name="queryString" value="${queryString}" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" class="btn_blue" value="查询" />
						</form>
					<td>
					<td>
					<shiro:hasPermission name="AddBusiness"><input type="button" value="添加" onclick="addFunction()" class="btn_blue" /></shiro:hasPermission>
					</td>
				</tr>
			</table>
		</div>

		<!-- 数据绑定 -->
		<div style="margin: 0 auto; margin-top: 20px">
			<c:if test="${applyPage.pageList.size() > 0}">
				<table class="gvtable" border="0">
					<thead>
						<tr>
							<th>单位名称</th>
							<th>用户名称</th>
							<th>商家所在区域</th>
							<th>企业类型</th>
							<th>添加时间</th>
							<th>详情</th>
							<th>删除</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="apply" items="${applyPage.pageList}">
							<tr>
								<td>${apply.campanyName}</td>
								<td>${apply.userName}</td>
								<td>${apply.businessArea}</td>
								<td>${apply.companyType}</td>
								<td>${apply.addTime}</td>
								<td>
									<shiro:hasPermission name="DetailBusiness"><a href="<%=basePath%>auth_center_info/business_info/details?id=${apply.campanyNo}">详情</a></shiro:hasPermission>
									<shiro:hasPermission name="EditBusiness"><a href="<%=basePath%>auth_center_info/business_info/edit?id=${apply.campanyNo}">编辑</a></shiro:hasPermission>
								</td>
								<td>
								<shiro:hasPermission name="DeleteBusiness"><a href="javascript:deleteInfo('${apply.campanyNo}')">删除</a></shiro:hasPermission>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${applyPage.pageList.size()==0}">
				<span style="color: red">没有相关数据</span>
			</c:if>
		</div>
		<!-- 分页控件 -->
		<div style="margin-top: 30px; font-size: 15px">
			<span>第<font color="red">${applyPage.currentPage}</font>页/共<font
				color="red">${applyPage.pageCount}</font>页&nbsp;&nbsp;每页显示<font
				color="red">${applyPage.pageSize}</font>条&nbsp;&nbsp;共<font
				color="red">${applyPage.allSize}</font>条记录
			</span> <span style="float: right;"> <span>[总共<font
					color="red">${applyPage.currentPageSize}</font>条数据]
			</span> <span> <c:if test="${applyPage.prePage != 0}">
						<a
							href="<%=basePath%>auth_center_info/business_info/list?requestPageNo=${applyPage.prePage}&queryString=${queryString}">上一页</a>
					</c:if>
			</span> <span> <c:if test="${applyPage.nextPage != 0}">
						<a
							href="<%=basePath%>auth_center_info/business_info/list?requestPageNo=${applyPage.nextPage}&queryString=${queryString}">下一页</a>
					</c:if>
			</span> <span>转到 <input style="width: 20px" type="text" name="pageNo"
					id="page" onkeydown="if(event.keyCode==13)event.keyCode=9"
					onkeypress="if ((event.keyCode<48 || event.keyCode>57)) event.returnValue=false">页
					<button onclick="goToPage()">Go</button>
			</span>
			</span>
		</div>
	</div>
	<!-- 跳转至指定页面的脚本 -->
	<script language="javascript">
		function goToPage(){
			var totalPage = "${applyPage.pageCount}";
			if(parseInt(totalPage) < parseFloat(document.getElementById("page").value) || parseFloat(document.getElementById("page").value) < 1){
				alert("不存在该页");
			}
			else{
				var url = "<%=basePath%>auth_center_info/business_info/list?requestPageNo="
						+ document.getElementById("page").value
						+ "&queryString="
						+ document.getElementById("queryString").value;
				alert(url);
				location.href = url;
			}
		}
	</script>
</body>
</html>