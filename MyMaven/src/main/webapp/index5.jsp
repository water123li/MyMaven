<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>jQuery UI 工具提示框（Tooltip） - 自定义样式</title>
<link rel="stylesheet" href="jquery/jquery-ui/jquery-ui.min.css" />
<script src="jquery/jquery-ui/external/jquery/jquery.js"></script>
<script src="jquery/jquery-ui/jquery-ui.min.js"></script>

<link rel="stylesheet" type="text/css" href="jquery/wTooltip/wTooltip.css" />
<script type="text/javascript" src="jquery/wTooltip/wTooltip.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
.hoverBox {
	display: inline-block;
	*display: inline;
	zoom: 1;
	margin: 10px;
	padding: 10px 30px;
	border: solid #CACACA 1px;
	cursor: pointer;
}
</style>
</head>
<body>
	<div id="content">

		<div class="content-box">
			<a id="wTooltip7" href="#" class="hoverBox"
				title="This is <img src="images/11.jpg">" >hover html</a>
		</div>
	</div>

<script type="text/javascript"> 
	$("#wTooltip7").wTooltip({
		//html : true,
		theme : 'orange',
		style: {
			border: "2px solid green",
			background: "blue",
			color: "white",
			fontWeight: "bold"
			}
	}); 
</script>
</body>
	<h2>Norwegian Mountain Trip</h2>
	<img border="0" src="http://www.4493.com/mingxingxiezhen/29427/1.htm" alt="Pulpit rock" width="304" height="228">
	<img src="images/11.jpg">
</html>
