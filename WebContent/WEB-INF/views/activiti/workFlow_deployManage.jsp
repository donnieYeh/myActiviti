<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="/js/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body class="easyui-layout">
	<div region="north" style="height: 200px;">
		<table class="easyui-datagrid" data-options="rownumbers:true,singleSelect:true">
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
	<div region="center">
		<table class="easyui-datagrid" data-options="rownumbers:true,singleSelect:true">
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
						<td><a href="#">查看流程图</a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<div region="south" style="height: 200px;"></div>
</body>
</html>