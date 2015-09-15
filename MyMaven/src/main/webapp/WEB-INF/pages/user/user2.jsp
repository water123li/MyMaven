<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<link rel="stylesheet" href="../jquery/jquery-ui/jquery-ui.min.css" />
<script src="../jquery/jquery-ui/external/jquery/jquery.js"></script>
<script src="../jquery/jquery-ui/jquery-ui.min.js"></script>
<script src="../jquery/core/json2.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<script type="text/javascript">
//接收json字符，teacherText
	$(document).ready(function() {
		$("#userLogin").click(function() {
			var username = $("#username").val();
			var password = $("#password").val();
			
			$.ajax({
				dataType : "json", //返回的数据类型
				type : "POST", //提交类型
				url : "<c:url value='/user/userLogin.action'/>",
				data : "username=" + username + "&password=" + password, //提交数据给Action传入数据 
				success : function(result) { //成功时调用的方法
					if (!result.isSuccessed) {
						alert("账号密码错误");
					} else {
						alert("成功");
					}
				}
			});
		});
		$("#userRegist").click(function() {
			var username = $("#username").val();
			var password = $("#password").val();
			var entities = [];
			var teacher = {"id":1, "name":"徐立秋", "students":
				[{"id":1, "name":"李瑞鹏"},{"id":2, "name":"范长安"}]};
			var teacherText = JSON.stringify(teacher);//toJSONString()
			
			$.ajax({
				//contentType: "application/json; charset=utf-8",   //内容类型，一般不加，加了后台getParameter接受为null
				dataType : "json", //返回的数据类型
				type : "POST", //提交类型
				url : "<c:url value='/user/userRegist.action'/>", //这里的需要Struts.xml的<action/>的name属性一致
				data : {"username" : username, "password" : password, "teacher" : teacherText}, //提交数据给Action传入数据 
				success : function(result) { //成功时调用的方法
					if (!result.isSuccessed) {
						alert("账号密码错误");
					} else {
						alert("成功");
					}
				}
			});
		});
	});
</script>
<style>
</style>
</head>
<body>
	账号：<input id="username" name="username" type="text" />
	<br /> 
	密码：<input id="password" name="password" type="password" />
	<br />
	<input id="userLogin" type="button" value="登陆" />
	<input id="userRegist" type="button" value="注册" />
</body>
</html>