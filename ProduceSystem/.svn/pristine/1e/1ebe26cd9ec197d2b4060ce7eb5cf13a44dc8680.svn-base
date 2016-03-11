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
<title>模型管理-气象模型-列表</title>
<link href="<%=basePath%>res/css/right/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/sys/tableStyle.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>res/js/jquery.validate.min.js"></script>
<style type="text/css">
body{
  font-size: 12px;
}
.add{
	-moz-border-radius: 50px;      /* Gecko browsers */
    -webkit-border-radius: 50px;   /* Webkit browsers */
    border-radius:50px;
    background-color: #009EEA;
    color: #FFF;
    height: 30px;
    width: 30px;
	padding: 2px 5px;
	cursor: pointer;
	
}
.reduce{
	-moz-border-radius: 50px;      /* Gecko browsers */
    -webkit-border-radius: 50px;   /* Webkit browsers */
    border-radius:50px;
    background-color: red;
    color: #FFF;
    height: 30px;
    width: 30px;
	padding: 2px 5px;;
	cursor: pointer;
}
</style>
</head>

<body>
	<form id="form1" method="post" action="" enctype="multipart/form-data"
		onsubmit="return doSubmit()">
		<table class="info" cellpadding="0" cellspacing="0" style="margin:20px auto;">
			<tr>
				<td class="text"><span class="required">*</span>模型名称：</td>
				<td class="value"><input id="txtName" name="txtName"
					class="width60per" /></td>
			</tr>
			<tr>
				<td class="text"><span class="required">*</span>模型类别：</td>
				<td class="value"><select id="selType" name="selType"
					class="width60per">
						<c:forEach items="${dictionaryList}" var="diclist">
							<option value="${diclist.cuitMoonDictionaryCode }">${diclist.cuitMoonDictionaryName }</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="text"><span class="required">*</span>所属产品：</td>
				<td class="value"><select id="selProduct" name="selProduct"
					class="width60per">
						<c:forEach items="${dicType}" var="dicItem">
							<option value="${dicItem.cuitMoonDictionaryCode}">≡${ dicItem.cuitMoonDictionaryName}</option>
							<c:forEach items="${productList}" var="pfItem">
								<c:if
									test="${pfItem.productType.trim().equals(dicItem.cuitMoonDictionaryCode.toString())}">
									<option value="${ pfItem.productNumber}">└ ${ pfItem.productName}</option>
								</c:if>
							</c:forEach>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="text">生育期<span class="add" onclick="addGrowthPeriod()">+</span>
					<input id="growthPeriodValue" name="growthPeriodValue" type="hidden">
				</td>
				<td>
					<div id="growthPeriod"></div>
				</td>
				<%-- <td class="value">
					<c:forEach items="${elementList}" var="elelist">
						<div><label><input name="cheElement" type="checkbox" value="${elelist.elementNumber }" onclick="checkElement()">${elelist.elementName }</label></div>
					</c:forEach>
					<input id="txtElements" name="txtElements" type="hidden" value="" />
				</td> --%>
			</tr>
			<tr>
				<td class="text">最优生长环境描述：</td>
				<td class="value"><textarea id="txtDecription"
						name="txtDecription" rows="8" cols="25"></textarea></td>
			</tr>
			<tr>
				<td class="text">&nbsp;</td>
				<td class="value"><input type="submit" class="btn_blue"
					value="提交" />&nbsp; &nbsp;<a id="btnBack"
					href="javascript:history.go(-1);" class="btn_blue">返回</a></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
	function validate(){
	    return $("#form1").validate({
	        ignore: "",
	        rules: {
	            txtName: { required: true, maxlength: 20 }
	        },
	        messages: {
	            txtName: { required: "请输入模型名称", maxlength: "模型名称不能超过20个字符" }
	        }
	    }).form();
	}
	
	function doSubmit(){
		makeGrowthValue();
		if(validate()){
			$.post("<%=basePath%>model/model/add", $("#form1").serialize(), function(data){
				if(data.state=="fail"){
					alert(data.result);
				}
				else{
					alert(data.result);
					location.href="<%=basePath%>model/model/list";
				}
			});
		}
		return false;
	}
	function checkElement(){
		var objItem=$("input[name=cheElement]:checked");
		var items="";
		for(var i=0;i<objItem.length;i++){
			items+=objItem[i].value+"|";
		}
		$("#txtElements").val(items);
	}
	
	function addGrowthPeriod(){
		var nodeHtml = '<table class="growth"><tr><td><span class="reduce" onclick="deleteElem(this)">-</span>名称：<input type="text" name="growthPeroidName"></td><td><table><c:forEach items="${elementList}" var="elelist"><tr><td><input type="checkbox" value="${elelist.elementNumber }">${elelist.elementName }</td><td><input type="text" name="elemvalue" class="elemvalue"></td></tr></c:forEach></table></td></tr></table>';
		$("#growthPeriod").append(nodeHtml);
	}
	
	function deleteElem(elem){
		$(elem).parentsUntil("table").remove();
	}
	function makeGrowthValue(){
		var json = "["
		$(".growth").each(function(){
			json = json + "{"
			var growthName = $(this).find("input[type=text]").val();
			json = json + "\""+growthName+"\":";
			json = json + "{"
			$(this).find("input[type=checkbox]").each(function(){
				//json = json + "\"" $(this).val()+"\":";
				//alert(this.checked);
				if(this.checked){
					json = json+"\""+$(this).val()+"\":";
					json = json+"\""+$(this).parent().parent().find("input[type=text]").first().val()+"\",";
				}
				
			});
			json = json.substring(0,json.length - 1) + "}},"
		});
		
		json = json.substring(0,json.length - 1) + "]";
		if(json=="]"){
			json = "";
		}
		//alert(json);
		$("#growthPeriodValue").val(json);
	}
	
	</script>
</body>
</html>