<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/js/common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(function() {
		initTabs();
		InitLeftMenu();
		initSouth();
	})

	//初始化底部时间
	function initSouth() {
		setInterval(function() {
			$("#time").html(
					new Date().toLocaleString() + ' 星期'
							+ '日一二三四五六'.charAt(new Date().getDay()))
		}, 1000);
	}

	//初始化标签栏
	function initTabs() {
		$("#center_tabs").tabs({
			narrow : true,
			fit : true
		});
	}

	//初始化左侧菜单栏
	function InitLeftMenu() {
		$("#nl1").tree({
			data : [ {
				id:'qjgl',
				text : "请假管理",
				attributes : {
					url : "business/leave!listLeaveBill.do"
				}
			}, {
				id:'bsgl',
				text : "部署管理",
				attributes : {
					url:"activiti/workFlow!deployManage.do"
				}
			}, {
				id:'rwgl',
				text : "任务管理",
				attributes : ""
			} ],
			onClick : function(node) {
				addTab(node.text, node.attributes.url);
			}
		});
	}
	function addTab(name, url) {
		if ($("#center_tabs").tabs("exists", name)) {
			var tab = $("#center_tabs").tabs("getTab", name);
			$("#center_tabs").tabs("update", {
				tab : tab,
				options : {
					content:createFrame(url)
				}
			});
			$("#center_tabs").tabs("select", name);

		} else {
			var options = {};
			options.title = name;
			options.content = createFrame(url);
			options.closable = true;
			$("#center_tabs").tabs("add", options);
		}
	}
	function createFrame(url) {
		var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		return s;
	}
</script>
<style type="text/css">
#south_msg {
	font-size: 14px;
	height: 100%;
	line-height: 30px;
	padding-right: 10px;
}

.tree-node {
	height: 25px;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height: 100px;">
		<div align="right" style="padding-right: 20px; top: 50%;">
			<a href="business/demo!logout.do">注销</a>
		</div>
	</div>
	<div data-options="region:'south'" style="height: 30px;">
		<div align="right" id="south_msg">
			你好：<b>${username }</b><span id="time" style="margin-left: 100px;"></span>
		</div>
	</div>
	<div data-options="region:'west',title:'West',split:true" style="width: 200px;">
		<div id="west_accordion" class="easyui-accordion" style="height: 100%;">
			<div title="流程功能" style="overflow: auto;">
				<ul class="navlist" id="nl1"></ul>
			</div>
			<div title="Title2" style="padding: 10px;">
				<ul class="navlist" id="nl2"></ul>
			</div>
			<div title="Title3">
				<ul class="navlist" id="nl3"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center',fit:true" style="background: rgb(245, 245, 245);">
		<div id="center_tabs">
			<div title="首页" style="padding: 20px;">
				<h1>欢迎登陆</h1>
			</div>
		</div>
	</div>
</body>
</html>