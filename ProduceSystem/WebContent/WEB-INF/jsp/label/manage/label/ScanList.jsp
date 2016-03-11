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
<title>标签列表</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
function back(){
	var pid = document.getElementById("key").value;
	window.location.href = "<%=basePath%>label/manage/label/list?key="+pid;
}
function goToPage() {
	var totalPage = "${DataList.pageCount}";
	if(parseInt(totalPage) < parseFloat(document.getElementById("page").value) || parseFloat(document.getElementById("page").value) < 1){
		alert("不存在该页");
	}
	else{
		var url = "<%=basePath%>label/manage/label/scanlist?requestPageNo="
				+ document.getElementById("page").value
				+ "&queryString="
				+ document.getElementById("queryString").value
				+ "key="
				+ document.getElementById("key").value
				+ "&Id="
				+ document.getElementById("Id").value;
		window.location.href = url;
	}
}
</script>
</head>
<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">认证标签管理>>标签管理>>标签扫描记录</label>
</div>
<br/>
<div class="container">
		<div class="" style="margin: 0 auto;">
			<table>
				<tr>
					<td>
						<form name="form1" action="<%=basePath%>label/manage/label/scanlist" method="post">
							<input type="hidden" id="key" name="key" value="${productId}">
							<input type="hidden" id="Id" name="Id" value="${labelId}">
							&nbsp;&nbsp;&nbsp; 扫描地点：
							<input type="text" id="queryString" name="queryString" value="${queryString}" />
								&nbsp;<input type="submit" value="查询" class="btn_blue" />
						</form>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;							
						&nbsp;<input type="button" value="返回" onclick="back()" class="btn_blue" />
					</td>
				</tr>
			</table>
		</div>		
		<div style="margin: 0 auto; margin-top: 20px">
			<!-- 数据绑定 -->
			<c:if test="${DataList.pageList.size() > 0}">
				<table class="gvtable" border="0">
					<thead>
						<tr>
							<th>扫描序号</th>
							<th>扫描时间</th>
							<th>扫描地点</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${DataList.pageList}">
							<tr>
								<td>${data.scanId}</td>
								<td><fmt:formatDate value="${data.scanTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${data.scanPlace}</td>															
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${DataList.pageList.size()==0}">
				<span style="color: red">没有相关数据</span>
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
							href="<%=basePath%>label/manage/label/scanlist?requestPageNo=${DataList.prePage}
							&queryString=${queryString}&key=${productId}&Id=${labelId}">上一页</a>
					</c:if>
			</span> <span> <c:if test="${DataList.nextPage != 0}">
						<a
							href="<%=basePath%>label/manage/label/scanlist?requestPageNo=${DataList.nextPage}
							&queryString=${queryString}&key=${productId}&Id=${labelId}">下一页</a>
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