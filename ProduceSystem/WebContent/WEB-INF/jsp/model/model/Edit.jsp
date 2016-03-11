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
<link href="<%=basePath%>res/css/right/addandedit.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>res/css/sys/tableStyle.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>res/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>res/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>res/js/public.js"></script>
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
		<table class="info" cellpadding="0" cellspacing="0">
			<tr>
				<td class="text"><span class="required">*</span>模型名称：</td>
				<td class="value"><input id="txtName" name="txtName"
					class="width60per" value="${modelDetail[0].modelName}" /> <input
					id="txtModel" name="txtModel" type="hidden"
					value="${ modelDetail[0].productApproModelId}" /></td>
			</tr>
			<tr>
				<td class="text"><span class="required">*</span>模型类别：</td>
				<td class="value"><select id="selType" name="selType"
					class="width60per">
						<c:forEach items="${dictionaryList}" var="diclist">
							<option value="${diclist.cuitMoonDictionaryCode }">${diclist.cuitMoonDictionaryName }</option>
						</c:forEach>
				</select> <script type="text/javascript">
						$("#selType").val(("${ modelDetail[0].modelType}").endsTrim());
					</script></td>
			</tr>
			<tr>
				<td class="text"><span class="required">*</span>所属产品：</td>
				<td class="value"><select id="selProduct" name="selProduct"
					class="width60per">
						<c:forEach items="${productList}" var="prolist">
							<option value="${prolist.productNumber }">${prolist.productName }</option>
						</c:forEach>
				</select> <script type="text/javascript">
						$("#selProduct").val("${ modelDetail[0].productNumber}");
					</script></td>
			</tr>
			<tr>
				<td class="text">生育期<span class="add" onclick="addGrowthPeriod()">+</span>
				<input id="growthPeriodValue" name="growthPeriodValue" type="hidden"></td>
				<td class="value">
					<div id="growthPeriod">
					<c:forEach items="${growthPeriodList}" var="gp">
						<table class="growth">
							<tr>
								<td>
									<span onclick="deleteElem(this)">-</span>
									名称：<input type="text" name="growthPeroidName" value="${gp.growthName}">
								</td>
								<td>
									<table>
										<c:forEach items="${elementList}" var="elelist">
											<c:set var="flag" value="0" />
											<c:set var="elementValue" value="" />
											<c:forEach items="${gp.growthElemList }" var="elem">
												<c:if test="${elem.elementNumber eq elelist.elementNumber}">
													<c:set var="flag" value="1" />
													<c:set var="elementValue" value="${elem.elementValue}" />
												</c:if>
											</c:forEach>
											<c:choose>
													<c:when test="${flag eq 1}">
														<tr>
															<td>
																<input type="checkbox" checked="checked" value="${elelist.elementNumber }">${elelist.elementName }
															</td>
															<td>
																<input type="text" name="elemvalue" class="elemvalue" value="${elementValue}">
															</td>
														</tr>
													</c:when>
													<c:otherwise>
														<tr>
															<td>
																<input type="checkbox" value="${elelist.elementNumber }">${elelist.elementName }
															</td>
															<td>
																<input type="text" name="elemvalue" class="elemvalue">
															</td>
														</tr>
													</c:otherwise>
												</c:choose>
										</c:forEach>
									</table>
								</td>
							</tr>
						</table>
					</c:forEach>
					</div>
					<%-- <c:forEach items="${elementList}"
						var="elelist">
						<div>
							<label><input id="${elelist.elementNumber }" name="cheElement" type="checkbox"
								value="${elelist.elementNumber }" onclick="checkElement()">${elelist.elementName }</label>
						</div>
					</c:forEach> <input id="txtElements" name="txtElements" type="hidden" value="" />
					<script type="text/javascript">
						var strElementinfo="${strElementinfo}";
						var elementinfoList=strElementinfo.split('|');
						for(var i=0;i<elementinfoList.length;i++){
							$("#"+elementinfoList[i]).attr("checked",true);
						}
						checkElement();
						function checkElement() {
							var objItem = $("input[name=cheElement]:checked");
							var items = "";
							for (var i = 0; i < objItem.length; i++) {
								items += objItem[i].value + "|";
							}
							$("#txtElements").val(items);
						}
					</script> --%>
				</td>
			</tr>
			<tr>
				<td class="text">最优生长环境描述：</td>
				<td class="value"><textarea id="txtDecription"
						name="txtDecription" rows="8" cols="25">${modelDetail[0].modelDescription}</textarea></td>
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
			$.post("<%=basePath%>model/model/edit", $("#form1").serialize(), function(data){
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