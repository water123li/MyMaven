<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>jQuery UI 工具提示框（Tooltip） - 自定义样式</title>
<link rel="stylesheet" href="jquery/jquery-ui/jquery-ui.min.css" />
<script src="jquery/jquery-ui/external/jquery/jquery.js"></script>
<script src="jquery/jquery-ui/jquery-ui.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script>
	$(function() {   
		$(document).tooltip(
				{
					position : {
						my : "center bottom-20",
						at : "center top",
						using : function(position, feedback) {
							$(this).css(position);
							$("<div>").addClass("arrow").addClass(
									feedback.vertical).addClass(
									feedback.horizontal).appendTo(this);
						}
					}
				});
	});
</script>
<style>
.ui-tooltip,.arrow:after {
	background: red;
	border: 2px solid white;
}

.ui-tooltip {
	padding: 10px 20px;
	color: white;
	border-radius: 20px;
	font: bold 14px "Helvetica Neue", Sans-Serif;
	text-transform: uppercase;
	box-shadow: 0 0 7px black;
}

.arrow {
	width: 70px;
	height: 16px;
	overflow: hidden;
	position: absolute;
	left: 50%;
	margin-left: -35px;
	bottom: -16px;
}

.arrow.top {
	top: -16px;
	bottom: auto;
}

.arrow.left {
	left: 20%;
}

.arrow:after {
	content: "";
	position: absolute;
	left: 20px;
	top: -20px;
	width: 25px;
	height: 25px;
	box-shadow: 6px 5px 9px -9px black;
	-webkit-transform: rotate(45deg);
	-moz-transform: rotate(45deg);
	-ms-transform: rotate(45deg);
	-o-transform: rotate(45deg);
	tranform: rotate(45deg);
}

.arrow.top:after {
	bottom: -20px;
	top: auto;
}
</style>
</head>
<body>

	<p>
		<a href="#" title="<img src="images/11.jpg">部件的名称：可被绑定到任意的元素上。当您的鼠标悬停在元素上时，title
		属性会显示在元素旁边的一个小框中，就像原生的工具提示框一样">Tooltips 哈哈</a> 可被绑定到任意的元素上。当您的鼠标悬停在元素上时，title
		属性会显示在元素旁边的一个小框中，就像原生的工具提示框一样。
	</p>
	<p>
		但是由于它不是一个原生的工具提示框，所以它可以被定义样式。通过 <a href="http://themeroller.com"
			title="ThemeRoller：jQuery UI 的主题创建应用程序">ThemeRoller</a>
		创建的主题也可以相应地定义工具提示框的样式。
	</p>
	<p>工具提示框也可以用于表单元素，来显示每个区域中的一些额外的信息。</p>
	<p>
		<label for="age">您的年龄：</label><input id="age" title="<p>年龄仅用于。<p/>">
	</p>
	<p>悬停在相应的区域上查看工具提示框。</p>


</body>
</html>
