<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>模型管理-气象要素-列表</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<div>
		&nbsp;&nbsp;产品名称： <input type="text" id="queryString"
			class="height20" /> <input type="button" class="btn_blue"
			value="查询" onclick="doSearch()" />&nbsp;&nbsp; <a
			href="<%=basePath%>model/product/add" class="btn_blue">添加</a>&nbsp;&nbsp;
	</div>
	<div style="margin: 0 auto; margin-top: 20px">
		<table class="gvtable">
			<thead>
				<tr>
					<th>产品名称</th>
					<th>商品码</th>
					<th>产品类型</th>
					<th>产品描述</th>
					<th>添加时间</th>
					<th>操作</th>
					<th>删除</th>
				</tr>
			</thead>
			<c:forEach items="${productList.pageList}" var="plist">
				<tr>
					<td>${plist.productName }</td>
					<td>${plist.remark}</td>
					<td>${plist.productType}</td>
					<td style="max-width: 600px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">${plist.productDescription}</td>
					<td><fmt:formatDate value="${plist.addTime }" type="date"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><a
						href="<%=basePath%>model/product/edit?productNumber=${plist.productNumber }">编辑</a></td>
					<td><a
						href="javascript:doDelete('<%=basePath%>model/product/delete?productNumber=${plist.productNumber }&requsetPageNo=${productList.currentPage}')">删除</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<!-- 分页控件 -->
		<div style="margin-top: 10px; font-size: 15px">
			<span>第<font color="red">${productList.currentPage}</font>页/共<font
				color="red">${productList.pageCount}</font>页&nbsp;&nbsp;每页显示<font
				color="red">${productList.pageSize}</font>条&nbsp;&nbsp;共<font
				color="red">${productList.allSize}</font>条记录
			</span> <span style="float: right;"> <span>[总共<font
					color="red">${productList.currentPageSize}</font>条数据]
			</span> <span> <c:if test="${productList.prePage != 0}">
						<a
							href="<%=basePath%>model/product/list?requestPageNo=${productList.prePage}
							&queryString=${queryString}&parentId=${parentId}">上一页</a>
					</c:if>
			</span> <span> <c:if test="${productList.nextPage != 0}">
						<a
							href="<%=basePath%>model/product/list?requestPageNo=${productList.nextPage}
							&queryString=${queryString}&parentId=${parentId}">下一页</a>
					</c:if>
			</span> <span>转到 <input style="width: 20px" type="text" name="pageNo"
					id="page" onkeydown="if(event.keyCode==13)event.keyCode=9"
					onkeypress="if ((event.keyCode<48 || event.keyCode>57)) event.returnValue=false">页
					<button onclick="goToPage()">Go</button>
			</span>
			</span>
		</div>
	<script type="text/javascript">
		function doDelete(url){
			if(confirm("确定删除吗？")){
				self.location.href=url;
			}
		}
		function goToPage(){
			var totalPage = "${productList.pageCount}";
			var pid = ${parentId}; 
			if(parseInt(totalPage) < parseFloat(document.getElementById("page").value) || parseFloat(document.getElementById("page").value) < 1){
				alert("不存在该页");
			}
			else{
				var url = "<%=basePath%>model/product/list?requestPageNo="
							+ document.getElementById("page").value + "&queryString="
							+ document.getElementById("queryString").value
							+ "&parentId=" + pid;
					window.location.href = url;
				}
		}
		function doSearch(){
			var pid = ${parentId}; 
			var url = "<%=basePath%>model/product/list?requestPageNo="
				+ document.getElementById("page").value + "&queryString="
				+ document.getElementById("queryString").value
				+ "&parentId=" + pid;
			window.location.href = url;
		}
	</script>
</body>
</html>