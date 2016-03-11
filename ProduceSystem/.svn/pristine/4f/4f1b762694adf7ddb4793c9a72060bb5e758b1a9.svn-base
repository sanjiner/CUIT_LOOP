<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>res/css/sys/sysStyle.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>res/css/right/bread.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>res/css/right/sysIndexStyle.css">
<link rel="stylesheet"
	href="<%=basePath%>res/js/jquery.treeview/jquery.treeview.css" />
<script src="<%=basePath%>res/js/jquery.treeview/lib/jquery.js"
	type="text/javascript"></script>
<script src="<%=basePath%>res/js/jquery.treeview/lib/jquery.cookie.js"
	type="text/javascript"></script>
<script src="<%=basePath%>res/js/jquery.treeview/jquery.treeview.js"
	type="text/javascript"></script>
<title>字典管理</title>

<style type="text/css">

</style>
</head>

<body>

		<div class="bread">
			<ul>
				<li>系统管理>></li>
				<li>字典管理</li>
			</ul>
		</div>


		<div class="left_nav">

			<div>
				<ul id="tree" class="filetree">
					<c:forEach items="${dicList}" var="dic">
						<c:choose>
							<c:when test="${dic.hasChild eq true}">
								<li onClick="listChild(${dic.cuitMoonDictionaryCode})" ><span class="folder"><a href="<%=basePath%>sys/dic/dicList?parentCode=${dic.cuitMoonDictionaryCode}" target="dicFrame">${dic.cuitMoonDictionaryName }</a></span>
									<ul id="ul_${dic.cuitMoonDictionaryCode}" style="display: none;"></ul>
								</li>
							</c:when>
							<c:otherwise>
								<li><span class="file"><a href="<%=basePath%>sys/dic/dicList?parentCode=${dic.cuitMoonDictionaryCode}" target="dicFrame">${dic.cuitMoonDictionaryName }</a></span>
									</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</div>
		</div>


	<!-- iframe -->

	<div class="right_content">
		<iframe name="dicFrame"
			src="<%=basePath%>sys/dic/dicList"></iframe>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#tree").treeview({
				collapsed : true,
				unique: true
			});
		});
		
		function listChild(parentCode){
			var htmlcontent = $("#ul_"+parentCode).html();
			if(htmlcontent == ""){
				var tree = $("#tree").treeview();
				$.get("<%=basePath%>sys/dic/getChildList?parentCode="+parentCode
						, function(data){
							for(var i = 0 ; i<= data.length; i++){
								if((data[i].hasChild == true) && (i != data.length - 1)){
									$("#ul_"+parentCode).append('<li onClick="listChild('+data[i].cuitMoonDictionaryCode+')" class="expandable"><div class="hitarea expandable-hitarea"></div><span class="folder"><a target="dicFrame" href="<%=basePath%>sys/dic/dicList?parentCode='+data[i].cuitMoonDictionaryCode+'">'+data[i].cuitMoonDictionaryName+'</a></span><ul id="ul_'+ data[i].cuitMoonDictionaryCode+'" style="display: none;"></ul><li>');
								}else if((data[i].hasChild == true) && (i == data.length - 1)){
									$("#ul_"+parentCode).append('<li class="expandable lastExpandable" onClick="listChild('+data[i].cuitMoonDictionaryCode+')"><div class="hitarea expandable-hitarea lastExpandable-hitarea"></div><span class="folder"><a target="dicFrame" href="<%=basePath%>sys/dic/dicList?parentCode='+data[i].cuitMoonDictionaryCode+'">'+data[i].cuitMoonDictionaryName+'</a></span><ul id="ul_'+ data[i].cuitMoonDictionaryCode+'" style="display: none;"></ul><li>');
								}else if((data[i].hasChild == false) && (i != data.length - 1)){
									$("#ul_"+parentCode).append('<li><span class="file"><a target="dicFrame" href="<%=basePath%>sys/dic/dicList?parentCode='+data[i].cuitMoonDictionaryCode+'">'+data[i].cuitMoonDictionaryName+'</a></span><li>');
								}else{
									$("#ul_"+parentCode).append('<li class="last"><span class="file"><a target="dicFrame" href="<%=basePath%>sys/dic/dicList?parentCode='+data[i].cuitMoonDictionaryCode+'">'+data[i].cuitMoonDictionaryName+'</a></span><li>');
								}
								
							}
						}
				);
				
			}
		}
	</script>

</body>
</html>
