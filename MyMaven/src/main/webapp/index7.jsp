<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>jquery实现点击链接弹出层效果</title>

<script src="../../jquery.js"></script>

<style>

*{margin:0px;padding:0px;}

body{padding:100px;font-size:14px;}

a{display:block;margin:20px;text-decoration:none;}

.box{border:1px solid black;width:400px;height:300px;display:none;}

.box h1{background:#abcdef;height:35px;line-height:35px;border:1px solid black;} 

.box span{float:right;font-size:14px;}

.box p{padding:10px;}

</style>

<script>
	$(document).ready(function() {

		$('a').click(function() {

			$('.box').css('display', 'block');

		})

		$('.guanbi').click(function() {

			$('.box').hide();

		})

	})
</script>

</head>

<body>

<a href="javascript:void">点击弹出层效果</a>

<div class="box">

<h1>
<span class="guanbi">关闭</span>
</h1>

<p> 这是内容区域 </p>

</div>

</body>

</html>