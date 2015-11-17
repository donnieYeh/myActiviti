<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="/js/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function submitUpload() {
		var fileName = $("#processFile").val();
		nameArr = fileName.split(".");
		if (nameArr.length > 1 && nameArr[nameArr.length - 1] == "zip") {
			document.forms[0].submit();
		} else {
			showMsg("fade", 1000, "文件格式错误", "文件只能是zip格式", 2000);
		}
	}
	function showDiagram(name,id){
		var url ="workFlow!showDiagram.do?procDefId="+id;
		parent.addTab(name,url);
	}
</script>
</head>
<body >
<div class="easyui-panel" data-options="fit:true">
	<div  title="部署列表">
		<table class="easyui-datagrid" data-options="title:'部署列表',rownumbers:true,singleSelect:true">
			<thead>
				<tr>
					<th data-options="field:'id'">id</th>
					<th data-options="field:'content'">部署名</th>
					<th data-options="field:'days'">部署时间</th>
					<th data-options="field:'opt'">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#deployList">
					<tr>
						<td><s:property value="id" /></td>
						<td><s:property value="name" /></td>
						<td><s:date name="deploymentTime" format="YYYY-MM-dd HH:mm:ss" /></td>
						<td><a href="#">删除</a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<div title="流程定义列表">
		<table class="easyui-datagrid" data-options="title:'流程定义列表',rownumbers:true,singleSelect:true">
			<thead>
				<tr>
					<th data-options="field:'id'">id</th>
					<th data-options="field:'name'">流程名称</th>
					<th data-options="field:'key'">流程Key</th>
					<th data-options="field:'version'">流程版本</th>
					<th data-options="field:'resourceName'">流程资源名称</th>
					<th data-options="field:'diagramResourceName'">流程图名称</th>
					<th data-options="field:'deploymentId'">部署id</th>
					<th data-options="field:'opt'">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#prodefList">
					<tr>
						<td><s:property value="id" /></td>
						<td><s:property value="name" /></td>
						<td><s:property value="key" /></td>
						<td><s:property value="version" /></td>
						<td><s:property value="resourceName" /></td>
						<td><s:property value="diagramResourceName" /></td>
						<td><s:property value="deploymentId" /></td>
						<td><a onclick="showDiagram(<s:property value="name" />)">查看流程图</a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<div class="easyui-panel" title="流程上传部署" >
		<form action="workFlow!deployProcess.do" enctype="multipart/form-data" method="post">
			<table>
				<tr>
					<td>文件名：</td>
					<td><input class="easyui-textbox" name="fileName" style="width: 200px;"></td>
				</tr>
				<tr>
					<td>流程文件：</td>
					<td><input type="file" name="processFile" id="processFile" style="width: 200px;"></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="button" value="提交" onclick="submitUpload()"></td>
				</tr>
			</table>
		</form>
	</div>
</div>
</body>
</html>