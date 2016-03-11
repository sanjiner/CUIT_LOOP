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
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<div>
		&nbsp;&nbsp;要素名称： <input type="text" id="txtName"
			class="height20" /> <input type="button" class="checkButton"
			value="" onclick="doSsearch()" />&nbsp;&nbsp; <a href="<%=basePath%>model/element/add" class="addButton"></a>&nbsp;&nbsp;
	</div>
	<br />
	<div style="margin: 0 auto; margin-top: 20px">
		<table class="gvtable">
			<thead>
				<tr>
					<th>要素名称</th>
					<th>气象指标</th>
					<th>要素单位</th>
					<th>要素类别</th>
					<th>添加时间</th>
					<th>操作</th>
					<th>删除</th>
				</tr>
			</thead>
			<c:forEach items="${elementList.pageList}" var="elelist">
				<tr>
					<td><a
						href="<%=basePath%>model/element/detail?elementNumber=${elelist.elementNumber }">${elelist.elementName }</a></td>
					<td>${elelist.remark}</td>
					<td>${elelist.unit}</td>
					<td>${elelist.elementType}</td>
					<td><fmt:formatDate value="${elelist.addTime }" type="date"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><a
						href="<%=basePath%>model/element/edit?elementNumber=${elelist.elementNumber }">编辑</a></td>
					<td><a href="javascript:doDelete('<%=basePath%>model/element/delete?elementNumber=${elelist.elementNumber }&requsetPageNo=${elementList.currentPage}')">删除</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<!-- 分页 -->
	<div style="margin-top: 30px; font-size: 14px">
		<span>第<font color="red">${elementList.currentPage}</font>页/共${elementList.pageCount}页&nbsp;&nbsp;每页显示<font
			color="red">${elementList.pageSize}</font>条&nbsp;&nbsp;共${elementList.allSize}条记录
		</span> <span style="float: right;"> <span>[总共${elementList.currentPageSize}条数据]</span>
			<span><a
				href="<%=basePath%>model/element/list?requsetPageNo=${elementList.prePage}">上一页</a></span>
			<span><a
				href="<%=basePath%>model/element/list?requsetPageNo=${elementList.nextPage}">下一页</a></span>
			<span>转到<input style="width: 20px" type="text" name="pageNo"
				<c:if test="${elementList.currentPageSize lt 1}">readonly</c:if>>页
				<button>GO</button></span>
		</span>
	</div>
	<script type="text/javascript">
		function doDelete(url){
			if(confirm("确定删除吗？")){
				self.location.href=url;
			}
		}
		function doSsearch(){
			self.location.href='<%=basePath%>model/element/search?keyword='+$("#txtName").val();
		}
	</script>
</body>
</html>