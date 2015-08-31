<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>jQuery UI 日期选择器（Datepicker） - 默认功能</title>
<link rel="stylesheet" href="jquery/jquery-ui/jquery-ui.min.css" />
<script src="jquery/jquery-ui/external/jquery/jquery.js"></script>
<script src="jquery/jquery-ui/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script>
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true
		});
		
		$("#elem").text(sumAll(1, 123, 500, 115, 44, 88));
		
	});
	
	function sumAll() {
	    var i, sum = 0;
	    for (i = 0; i < arguments.length; i++) {
	        sum += arguments[i];
	    }
		debugger
	    return sum;
	}
	
</script>

<style type="text/css">
body {
	text-align: center;
}
</style>

</head>

<body>
	<p>
		日期：<input type="text" id="datepicker">
	</p>
	
	滚动条：<div id ="elem"> </div>

</body>
</html>