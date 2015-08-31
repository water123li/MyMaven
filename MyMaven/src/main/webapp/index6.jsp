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
			<div id="wTooltip1" class="hoverBox">hover me</div>
			<div id="wTooltip2" class="hoverBox"
				title="Title from title attribute">hover me</div>
			<div id="wTooltip3" class="hoverBox">hover me</div>
			<div id="wTooltip4" class="hoverBox active"
				onClick="tooltip_toggle();">click me</div>
			<div id="wTooltip5" class="hoverBox" title="This is tooltip 5">hover
				me</div>
			<div id="wTooltip6" class="hoverBox" title="This is tooltip 6">hover
				me</div>
			<a id="wTooltip7" href="#" class="hoverBox"
				title="This is <span style='color:red;'>html</span>">hover
				html</a>
			<div id="wTooltip8" class="hoverBox"
				title="This is <span style='color:red;'>html</span>">hover no
				html</div>
			<div id="wTooltip9" class="hoverBox active"
				title="<span style='color:red;'>no html</span>"
				onClick="html_toggle();">click me</div>
			<div id="wTooltip10" class="hoverBox active"
				title="<span style='color:red;'>no html</span>"
				onClick="html_toggle2();">click me</div>
			<div id="wTooltip11" class="hoverBox" title="Home">hover me</div>
			<div id="wTooltip12" class="hoverBox">hover me</div>

			<div class="mooTest" title="moo">moo</div>
			<div class="mooTest" title="poo">poo</div>
			<div class="mooTest" title="zoo">zoo</div>

		</div>
	</div>

<script type="text/javascript">
	$('.mooTest').wTooltip();

	console.log($('.mooTest').wTooltip('opacity'));
	$('.mooTest :first').wTooltip('opacity', 0.2)
	console.log($('.mooTest').wTooltip('opacity'));

	$("#wTooltip1").wTooltip();

	$("#wTooltip2").wTooltip({
		timeToStop : 2000,
		theme : "blue"
	});

	$("#wTooltip3").wTooltip({
		position : "mouse",
		timeToStop : 2000,
		theme : "plum"
	});

	$("#wTooltip4").wTooltip({
		position : "mouse",
		title : "This box is on",
		theme : "green"
	});

	$("#wTooltip5, #wTooltip6").wTooltip({
		position : "mouse",
		theme : 'white'
	});
	$("#wTooltip7").wTooltip({
		//html : true,
		theme : 'orange'
	}); 
	$("#wTooltip8").wTooltip({
		html : false,
		theme : 'black'
	});
	$("#wTooltip9").wTooltip({
		html : false,
		theme : 'red'
	});
	$("#wTooltip10").wTooltip({
		html : false,
		theme : 'cream'
	});
	$("#wTooltip11").wTooltip({
		html : false,
		theme : 'black'
	});
	$("#wTooltip12").wTooltip({
		html : false,
		theme : 'yellow'
	});

	function tooltip_toggle() {
		if ($("#wTooltip4").hasClass("active")) {
			$("#wTooltip4").removeClass("active");
			$("#wTooltip4").wTooltip("title", "This box is off");
		} else {
			$("#wTooltip4").addClass("active");
			$("#wTooltip4").wTooltip("title", "This box is on");
		}
	}

	function html_toggle() {
		$("#wTooltip9").wTooltip("title",
				"<span style='color:red;'>still no html</span>");
	}

	function html_toggle2() {
		$("#wTooltip10").wTooltip('html', true);
		$("#wTooltip10").wTooltip('title',
				'<span style="color:red;">yes html</span>');
	}
</script>
</body>
</html>
