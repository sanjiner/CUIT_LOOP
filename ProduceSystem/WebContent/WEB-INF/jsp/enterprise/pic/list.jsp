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
<title>产品图片管理——产品列表</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
function goToPage() {
	var totalPage = "${DataList.pageCount}";
	if(parseInt(totalPage) < parseFloat(document.getElementById("page").value) || parseFloat(document.getElementById("page").value) < 1){
		alert("不存在该页");
	}
	else{
		var url = "<%=basePath%>enterprise/pic/list?requestPageNo="
				+ document.getElementById("page").value
				+ "&queryString="
				+ document.getElementById("queryString").value
				+ "&applyType="
				+ document.getElementById("applyType").value;
		window.location.href = url;
	}
}
</script>
</head>
<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">企业咨询中心>>产品图片管理>>认证产品列表</label>
</div>
<br/>
<div class="container">
		<div class="" style="margin: 0 auto;">
			<table>
				<tr>
					<td>
						<form name="form1" action="<%=basePath%>enterprise/pic/list" method="post">
							&nbsp;&nbsp;&nbsp; 产品名称：
							<input type="text" id="queryString" name="queryString" value="${queryString}" />
							&nbsp;&nbsp;&nbsp;&nbsp;认证类型：
							<select id="applyType" name="applyType" style="width: 200px">
								<c:if test="${applyType == 1}">
									<option value="1">气候品质认证</option>
									<!-- <option value="2">农网认证</option> -->
								</c:if>
								<c:if test="${applyType == 2}">
									<!-- <option value="2">农网认证</option> -->
									<option value="1">气候品质认证</option>
								</c:if>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="submit" value="查询" class="btn_blue" />
						</form>
					</td>
				</tr>
			</table>
		</div>		
		<div style="margin: 0 auto; margin-top: 20px">
			<!-- 数据绑定 -->
			<c:if test="${applyType == 1}">
			<c:if test="${DataList.pageList.size() > 0}">
				<table class="gvtable" border="0">
					<thead>
						<tr>
							<th>产品名称</th>
							<th>所属商家</th>
							<th>认证申请时间</th>
							<th>认证类型</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${DataList.pageList}">
							<tr>
								<td>${data.productName}</td>
								<td>${data.unityName}</td>
								<td><fmt:formatDate value="${data.applyTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>气候品质认证</td>
								<td><a href="<%=basePath%>enterprise/pic/ManagePic?key=${data.applyBh}&type=${applyType}">管理图片</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${DataList.pageList.size()==0}">
				<span style="color: red">没有相关数据</span>
			</c:if>
			</c:if>
			
			<c:if test="${applyType == 2}">
				<c:if test="${DataList.pageList.size() > 0}">
				<table class="gvtable" border="0">
					<thead>
						<tr>
							<th>产品名称</th>
							<th>所属商家</th>
							<th>认证申请时间</th>
							<th>认证类型</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${DataList.pageList}">
							<tr>
								<td>${data.originName}</td>
								<td>${data.applyCompany}</td>
								<td><fmt:formatDate value="${data.applyTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>农网认证</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${DataList.pageList.size()==0}">
				<span style="color: red">没有相关数据</span>
			</c:if>
			</c:if>
		</div>
		<!-- 分页控件 -->
		<div style="margin-top: 10px; font-size: 15px">
			<span>第<font color="red">${DataList.currentPage}</font>页/共<font
				color="red">${DataList.pageCount}</font>页&nbsp;&nbsp;每页显示<font
				color="red">${DataList.pageSize}</font>条&nbsp;&nbsp;共<font
				color="red">${DataList.allSize}</font>条记录
			</span> <span style="float: right;"> <span>[总共<font
					color="red">${DataList.currentPageSize}</font>条数据]
			</span> <span> <c:if test="${DataList.prePage != 0}">
						<a
							href="<%=basePath%>enterprise/pic/list?requestPageNo=${DataList.prePage}&queryString=${queryString}&applyType=${applyType}">上一页</a>
					</c:if>
			</span> <span> <c:if test="${DataList.nextPage != 0}">
						<a
							href="<%=basePath%>enterprise/pic/list?requestPageNo=${DataList.nextPage}&queryString=${queryString}&applyType=${applyType}">下一页</a>
					</c:if>
			</span> <span>转到 <input style="width: 20px" type="text" name="pageNo"
					id="page" onkeydown="if(event.keyCode==13)event.keyCode=9"
					onkeypress="if ((event.keyCode<48 || event.keyCode>57)) event.returnValue=false">页
					<button onclick="goToPage()">Go</button>
			</span>
			</span>
		</div>
</div>
</body>
</html>