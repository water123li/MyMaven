<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="jquery/jquery-ui/jquery-ui.min.css" />
<script src="jquery/jquery-ui/external/jquery/jquery.js"></script>
<script src="jquery/jquery-ui/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script>
	$(function() {
		$(document).tooltip();
		$("#buttonId").click(function(){
			$("#date").html("哈哈哈哈");
		});
	});
</script>
<style>
label {
	display: inline-block;
	width: 5em;
}
</style>
</head>
<body>

	<p>
		<a href="#" title="部件的名称">Tooltips</a> 可被绑定到任意的元素上。当您的鼠标悬停在元素上时，title
		属性会显示在元素旁边的一个小框中，就像原生的工具提示框一样。
	</p>
	<p>
		但是由于它不是一个原生的工具提示框，所以它可以被定义样式。通过 <a href="http://themeroller.com"
			title="ThemeRoller：jQuery UI 的主题创建应用程序">ThemeRoller</a>
		创建的主题也可以相应地定义工具提示框的样式。
	</p>
	<p>工具提示框也可以用于表单元素，来显示每个区域中的一些额外的信息。</p>
	<p>
		<label for="age">您的年龄：</label><input id="age" title="年龄仅用于统计。">
	</p>
	<p>悬停在相应的区域上查看工具提示框。</p>
日期：<div id ="date">备注</div>
<input id="buttonId" type="button" value="登录">
</body>
</html>
