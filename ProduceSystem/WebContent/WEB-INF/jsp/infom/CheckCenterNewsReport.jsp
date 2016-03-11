<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专家列表</title>
</head>
<body>


	<form action="<%=basePath%>infom/BusinessManagement" name="form"
		method="post">
		单位名称：<input type="text" name="find"> <input type="submit"
			value="查询">

	</form>
	<button>添加</button>
	<table border="1" bordercolor="black" width="100%" frame="void"
		cellspacing="0">
		<tr bgcolor="#4169E1" border="1" bordercolor="black" width="100%"
			frame="void" cellspacing="0">
			<th>新闻标题</th>
			<th>新闻关键字</th>
			<th>新闻类别</th>
			<th>发布时间</th>
			<th>详情</th>
			<th>操作</th>
			
			
		</tr>
		<c:forEach var="apply" items="${applyPage.pageList}">
			<tr onmouseout="this.style.backgroundColor=''"
				onmouseover="this.style.backgroundColor='#4169E1'">
				<td>${apply.newsTitle}</td>
				<td>${apply.newsKey}</td>
				<td>${apply.newsCategory}</td>
				<td>${apply.reportTime}</td>
				
				<td>详情</td>
				
			<td><table>
						<td><a href="<%=basePath%>/infom/CheckCenterNewsReport?campanyNo=${apply.newsNumber}"><font color="red">编辑</font></a></td>
						<td><a href="http://www.baidu.com"><font color="red">删除</font></a></td>
					</table></td> 
				
			</tr>
		</c:forEach>

		<c:forEach var="apply" items="${campanyName}">

			<tr onmouseout="this.style.backgroundColor=''"
				onmouseover="this.style.backgroundColor='#4169E1'">
				<td>${apply.expertname}</td>
				<td>${apply.expertCategory}</td>
				<td>${apply.schools}</td>
				<td>${apply.educationalBackground}</td>
				<td>详情</td>
				<td><table>
						<td><a href="<%=basePath%>/infom/BusinessManagementXiangQing?campanyNo=${apply.campanyNo}"><font color="red">详情</font></a></td>
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
					href="<%=basePath%>/infom/CheckCenterNewsReport?requsetPageNo=${applyPage.prePage}">上一页</a></span>
				<span><a
					href="<%=basePath%>/infom/CheckCenterNewsReport?requsetPageNo=${applyPage.nextPage}">下一页</a></span>

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
    var c="<%=basePath%>infom/CheckCenterNewsReport?requsetPageNo="
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
					href="<%=basePath%>/infom/CheckCenterNewsReport?requsetPageNo=${applyPage.prePage}">上一页</a></span>
				<span><a
					href="<%=basePath%>/infom/CheckCenterNewsReport?requsetPageNo=${applyPage.nextPage}">下一页</a></span>

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