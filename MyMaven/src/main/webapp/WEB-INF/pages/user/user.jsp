<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="s" uri="/struts-tags" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<script type="text/javascript">
	$(function() {
		$("#userLogin").click(function() {
			var username = $("#username").val();
			var password = $("#password").val();
			var url = "<c:url value="/jsp/index.htm"/>";
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
			var user = {"username":username, "password":password};
			var teacher = {"id":1, "name":"徐立秋", "students":
				[{"id":1, "name":"李瑞鹏"},{"id":2, "name":"范长安"}]};
			var students = [{"id":1, "name":"李瑞鹏"},{"id":2, "name":"范长安"}];
			
			var userJson  = JSON.stringify({"user":user});
			var teacherJson = JSON.stringify({"teacher":teacher});
			var studentsJson = JSON.stringify({"students":students});
			
			$.ajax({
				//contentType: "application/json; charset=utf-8",   //内容类型，一般不加，加了后台getParameter接受为null
				dataType : "json", //返回的数据类型
				type : "POST", //提交类型
				url : "<c:url value='/user/userRegist.action'/>", //这里的需要Struts.xml的<action/>的name属性一致
				data : {"username" : username, "password" : password},
				//data : user, 			//提交数据给Action传入数据 
				//data : JSON.stringify({"username" : username, "password" : password}),
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
	<input id="userRegist" type="button" value="注册 " />
	<br>
	<br>
	<div>文件上传</div>
	<div id="fileDiv">
		<form action="<c:url value='/file/upload.action'/>" enctype="multipart/form-data" method="post">
	    	<input name="image" type="file">
	    	<input type="submit" value="上传"/>
	    </form>
	    
	    <div>文件下载</div>
	    <form action="<c:url value='/file/download.action?fileName=aa.txt'/>" method="post">
	    	<input type="submit" value="下载图片"/>
	    </form>
	</div>
</body>
</html>