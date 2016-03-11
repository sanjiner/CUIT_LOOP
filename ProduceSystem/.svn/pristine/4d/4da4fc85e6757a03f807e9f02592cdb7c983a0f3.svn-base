<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
</head>
<body>
	<form action="<%=basePath%>infom/BusinessManagement" name="form"
		method="post">
		单位名称：<input type="text" name="find"> <input type="submit"
			value="查询">

	</form>

	<table border="1" bordercolor="black" width="100%" frame="void"
		cellspacing="0">
		<tr bgcolor="#4169E1" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<th>单位名称</th>
			<th>用户名称</th>
			<th>商家所在地区</th>
			<th>企业类型</th>
			<th>添加时间</th>
			<th>详情</th>
			<th>删除</th>
		</tr>
		<c:forEach var="apply" items="${applyPage.pageList}">
			<tr onmouseout="this.style.backgroundColor=''"
				onmouseover="this.style.backgroundColor='#4169E1'">
				<td>${apply.campanyName}</td>
				<td>${apply.userName}</td>
				<td>${apply.businessArea}</td>
				<td>${apply.companyType}</td>
				<td>${apply.addTime}</td>
				<td><table>
						<td><a href="<%=basePath%>/infom/BusinessManagementXiangQing?campanyNo=${apply.campanyNo}"><font color="red">详情</font></a></td>
						<td><a href="<%=basePath%>/infom/BusinessManagementInformationEditor"><font color="red">编辑</font></a></td>
					</table></td>
				<td><font color="red">删除</font></td>
			</tr>
		</c:forEach>

		<c:forEach var="apply" items="${campanyName}">

			<tr onmouseout="this.style.backgroundColor=''"
				onmouseover="this.style.backgroundColor='#4169E1'">
				<td>${apply.campanyName}</td>
				<td>${apply.userName}</td>
				<td>${apply.businessArea}</td>
				<td>${apply.companyType}</td>
				<td>${apply.addTime}</td>
				<td><table>
						<td><a href="http://www.baidu.com"><font color="red">详情</font></a></td>
						<td><a href="http://www.baidu.com"><font color="red">编辑</font></a></td>
					</table></td>
				<td><font color="red">删除</font></td>
			</tr>

		</c:forEach>
		<c:if test="${campanyName.size()==0 }">
			<font size="6" color="red">没有相关的数据</font>
		</c:if>

	</table>


	<!-- 分页 -->
	<c:if test="${panduan_zhuxinxi}">
		<div>
			<span>第<font color="red">${applyPage.currentPage}</font>页/共<font
				color="red">${applyPage.pageCount}</font>页&nbsp;&nbsp;每页显示<font
				color="red">${applyPage.pageSize}</font>条&nbsp;&nbsp;共<font
				color="red">${applyPage.allSize}</font>条记录
			</span> <span style="float: right;"> <span>[总共<font
					color="red">${applyPage.currentPageSize}</font>条数据]
			</span> <span><a
					href="<%=basePath%>/infom/BusinessManagement?requsetPageNo=${applyPage.prePage}">上一页</a></span>
				<span><a
					href="<%=basePath%>/infom/BusinessManagement?requsetPageNo=${applyPage.nextPage}">下一页</a></span>

				<span>转到 <input style="width: 20px" type="text" name="pageNo"
					id="page"
					" 
			
			
			onkeydown="if(event.keyCode==13)event.keyCode=9"
					onkeypress="if ((event.keyCode<48 || event.keyCode>57)) event.returnValue=false">
					页


					<button onclick="print()">Go</button>
			</span>
			</span>
		</div>
	</c:if>
	<script language="javascript">   
  function print(){   
  
    var b="javascript:window.location.href='<%=basePath%>infom/BusinessManagement?requsetPageNo="; 
    var c="<%=basePath%>infom/BusinessManagement?requsetPageNo="
					+ document.getElementById("page").value;

			location.href = c;

		}
	</script>

	<!--                            -->

	<c:if test="${panduan_chaxunxinxi}">



		<div>
			<span>第<font color="red">${applyPage.currentPage}</font>页/共<font
				color="red">${applyPage.pageCount}</font>页&nbsp;&nbsp;每页显示<font
				color="red">${applyPage.pageSize}</font>条&nbsp;&nbsp;共<font
				color="red">${applyPage.allSize}</font>条记录
			</span> <span style="float: right;"> <span>[总共<font
					color="red">${applyPage.currentPageSize}</font>条数据]
			</span> <span><a
					href="<%=basePath%>/infom/BusinessManagement?requsetPageNo=${applyPage.prePage}">上一页</a></span>
				<span><a
					href="<%=basePath%>/infom/BusinessManagement?requsetPageNo=${applyPage.nextPage}">下一页</a></span>

				<span>转到 <input style="width: 20px" type="text" name="pageNo"
					id="page"
					" 
			
			
			onkeydown="if(event.keyCode==13)event.keyCode=9"
					onkeypress="if ((event.keyCode<48 || event.keyCode>57)) event.returnValue=false">
					页


					<button onclick="print()">Go</button>
			</span>
			</span>
		</div>

	</c:if>
	<c:if test="${!panduan_chaxunxinxi&&!panduan_zhuxinxi}">
	<font color="red">没有相关的数据</font>
	</c:if>


	<!--                    -->

</body>
</html>