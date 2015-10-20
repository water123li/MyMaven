<%@ include file="/decorators/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<script type="text/javascript">
	$(function() {
		initTreeStruct();
		
	});
	function initTreeStruct(){
		$("#treeStruct").jstree({
			'core' : {
				'data' : {
					'type': 'json',
					'url' : "<c:url value='/treestruct/getTreeData.action'/>",
					'data' : function (node) {
						return { 'id' : node.id };
					}
				},
				'check_callback' : function(o, n, p, i, m) {
					if(m && m.dnd && m.pos !== 'i') { return false; }
					if(o === "move_node" || o === "copy_node") {
						if(this.get_node(n).parent === this.get_node(p).id) { return false; }
					}
					return true;
				},
				'themes' : {
					'responsive' : false,
					'variant' : 'small',
					'stripes' : true
				}
			}
		});
		$("#treeStruct").jstree("open_all");
	}
	function initTreeStruct2(){
		$("#treeStruct").jstree({
			"plugins":["core", "themes", "ui", "json_data", "types"],
			"json_data" :{
					"ajax":{
						contentType: "application/json",  
						url : "<c:url value='/treestruct/getTreeData.action'/>",
						type : "POST", 
						async: false
					} 
			},
			"themes":{
				"theme" : "classic",
				"dots" : true,
				"icons" : true,
				"url" : "<c:url value='/jquery/jstree/themes/default/style.css'/>"
			},
			"types":{
				type_attr:"nType",
				"types":{
					"catlog":{
						"icon":{
							"image": "<c:url value='/images/node.gif'/>"
						}
					}
				}
			}
		});
		$("#treeStruct").jstree("open_all");
	}
</script>
<style>
html, body{ margin:0; height:100%; }
#treeContainer {
	width: 25%;
	height: 400px;
	background: #CCCC66;
	float: left;
	margin: 0;
}

#dataContainer {
	width: 75%;
	height: 400px;
	background: white;
	float: right;
	margin: 0;
}
</style>
</head>
<body>
<div id="mainDiv" class="main">
	<h3 id="header" class="header" align="center">数据管理 </h3>
	<h3 id="header2" class="header" align="center"><fmt:message key='ccc'><fmt:param value="李瑞鹏"></fmt:param></fmt:message></h3>
	<div id="contentDiv" class="content">
		<table class="maintable">
			<div id="treeContainer" align="left">
				<div id="treeStruct" class="treeStruct" ></div>
			</div>
			<div id="dataContainer" valign="top" align="right">
				<div id="dataDiv" class="dataDiv" >
					<div id="buttonDiv">
						<input id="add" type="button" value="添加" />
						<input id="delete" type="button" value="删除" />
						<input id="save" type="button" value="保存" />
					</div>
					<table id="dataElementTable"></table>
				</div>
			</div>
		</table>
	</div>
</div>
</body>
</html>