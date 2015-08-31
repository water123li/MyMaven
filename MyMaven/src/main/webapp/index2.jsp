<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="jquery/jquery-ui/jquery-ui.min.css" />
<script src="jquery/jquery-ui/external/jquery/jquery.js"></script>
<script src="jquery/jquery-ui/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style>
.ui-progressbar {
	position: relative;
}

.progress-label {
	position: absolute;
	left: 50%;
	top: 4px;
	font-weight: bold;
	text-shadow: 1px 1px 0 #fff;
}
</style>
<script>
	$(function() {
		var progressbar = $("#progressbar"), progressLabel = $(".progress-label");

		progressbar.progressbar({
			value : false,
			change : function() {
				progressLabel.text(progressbar.progressbar("value") + "%");
			},
			complete : function() {
				progressLabel.text("完成！");
			}
		});

		function progress() {
			debugger;
			var val = progressbar.progressbar("value") || 0;

			progressbar.progressbar("value", val + 1);

			if (val < 99) {
				setTimeout(progress, 100);
			}
		}

		setTimeout(progress, 3000);
	});
</script>
</head>
<body>

	<div id="progressbar">
		<div class="progress-label">加载...</div>
	</div>

</body>
</html>
