<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@include file="/js/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function showCurrentTaskDiagram(id,name){
	var url = "activiti/workFlow!toDiagramView.do?taskId="+id;
	parent.addTab(name,url);
}
</script>
</head>
<body>
	<div class="easyui-panel" data-options="fit:true">
		<div>
			<table class="easyui-datagrid" data-options="title:'部署列表',rownumbers:true,singleSelect:true">
				<thead>
					<tr>
						<th data-options="field:'id'">id</th>
						<th data-options="field:'name'">任务名</th>
						<th data-options="field:'assignee'">任务办理人</th>
						<th data-options="field:'createTime'">任务开始时间</th>
						<th data-options="field:'dueDate'">受理日期</th>
						<th data-options="field:'processInstanceId'">流程实例id</th>
						<th data-options="field:'executionId'">执行流id</th>
						<th data-options="field:'processDefinitionId'">流程定义id</th>
						<th data-options="field:'opt'">操作</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#tasks">
						<tr>
							<td><s:property value="id" /></td>
							<td><s:property value="name" /></td>
							<td><s:property value="assignee" /></td>
							<td><s:date name="createTime" format="YYYY-MM-dd HH:mm:ss" /></td>
							<td><s:date name="dueDate" format="YYYY-MM-dd HH:mm:ss" /></td>
							<td><s:property value="processInstanceId" /></td>
							<td><s:property value="executionId" /></td>
							<td><s:property value="processDefinitionId" /></td>
							<td>
								<a href="workFlow!transactTask.do?taskId=<s:property value='id' />">任务办理</a>
								<a href="javascript:showCurrentTaskDiagram(<s:property value='id' />,'当前任务节点')">显示流程图</a>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>