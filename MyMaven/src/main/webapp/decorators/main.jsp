<%@ include file="/decorators/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="author" content="Yeeku.H.Lee(CrazyIt.org)" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><decorator:title default="我的Maven项目"/></title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/decorators/main.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jquery-ui/jquery-ui.min.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jstree/themes/default/style.min.css'/>" />
	<script type="text/javascript" src="<c:url value='/jquery/core/jquery-2.1.1.js'/>"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-ui/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<c:url value='/jquery/core/json2.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jstree/jstree.min.js'/>"></script>
	<decorator:head/>
	<decorator:title/>
</head>
<body>
<decorator:body />
<div id="foot" align="center">
		<tr>
			<td id="footer"><b>被包含的内容</b><br /> SithMesh提供页面装饰支持</td>
		</tr>
	</div>
</body>
</html>