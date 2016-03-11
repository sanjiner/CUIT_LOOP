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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>归档列表</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/sys/sysStyle.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>/res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
function addFunction(){
	var pid = document.getElementById("select01").value;
	window.location.href = "<%=basePath%>file_recode/add?pid=" + pid;
}
function goToPage() {
	var totalPage = "${DataList.pageCount}";
	if(parseInt(totalPage) < parseFloat(document.getElementById("page").value) || parseFloat(document.getElementById("page").value) < 1){
		alert("不存在该页");
	}
	else{
		var url = "<%=basePath%>file_recode/list?requestPageNo="
					+ document.getElementById("page").value + "&queryString="
					+ document.getElementById("queryString").value;
			window.location.href = url;
		}
	}
</script>
</head>
<body>
<div style="background: #4c9ee7;width: 100%;height: 30px;margin-bottom: 5px">
	<label style="width: auto;height: 30px;text-align: center;color: white;line-height: 30px;margin-left: 5px">应用中心>>项目归档>>归档列表</label>
</div>
<br/>
	<div class="container">
		<div class="" style="margin: 0 auto;">
			<table>
				<tr>
					<td>
						<form name="form1" action="<%=basePath%>file_recode/list"
							method="post">
							&nbsp;&nbsp;&nbsp; 项目名称： <input type="text" id="queryString"
								name="queryString" value="${queryString}" /> &nbsp;<input
								type="submit" value="查询" class="btn_blue" />
						</form>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;待归档的项目： <select id="select01"
						style="width: 200px">
							<c:if test="${productlist.size() > 0}">
								<c:forEach var="plist" items="${productlist}">
									<option value="${plist.applyBh}">${plist.productName}</option>
								</c:forEach>
							</c:if>
							<c:if test="${productlist.size() == 0}">
								<option value="0">没有待归档的项目</option>
							</c:if>
					</select> &nbsp;<shiro:hasPermission name="AddControl">
							<input type="button" value="添加归档" onclick="addFunction()"
								class="btn_blue" />
						</shiro:hasPermission>
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
							<th>项目名称</th>
							<th>档案编号</th>
							<th>归档人</th>
							<th>归档时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${DataList.pageList}">
							<tr>
								<td>${data.projectName}</td>
								<td>${data.fileCode}</td>
								<td>${data.archivePeople}</td>
								<td>${data.filingTime}</td>
								<td><shiro:hasPermission name="DetailControl">
										<a
											href="<%=basePath%>file_recode/detail?key=${data.recordNum}">详情</a>
									</shiro:hasPermission> <shiro:hasPermission name="EditControl">
										<a href="<%=basePath%>file_recode/edit?key=${data.recordNum}">编辑</a>
									</shiro:hasPermission> <shiro:hasPermission name="DeleteControl">
										<a
											href="<%=basePath%>file_recode/delete?key=${data.recordNum}">删除</a>
									</shiro:hasPermission></td>
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
							href="<%=basePath%>file_recode/list?requestPageNo=${DataList.prePage}&queryString=${queryString}">上一页</a>
					</c:if>
			</span> <span> <c:if test="${DataList.nextPage != 0}">
						<a
							href="<%=basePath%>file_recode/list?requestPageNo=${DataList.nextPage}&queryString=${queryString}">下一页</a>
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