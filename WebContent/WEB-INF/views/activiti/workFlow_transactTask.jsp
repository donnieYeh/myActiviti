<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
    <%@include file="/js/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
	initForm();
})

function initForm(){
	$('#applyForm').form({    
	    url:"workFlowOpt!completeTask.do",    
	    success:function(data){ 
	        var node = parent.$("#nl1").tree('find','rwgl');
	        parent.addTab(node.text,node.attributes.url);
	        parent.$("#center_tabs").tabs("close",'任务办理');
	    }    
	});   
}

function submitForm(btn){
	var option = btn.value;
	$('#applyForm').form('submit',{
		queryParams:{
			"option":option,
			"taskId":'<s:property value="#taskId"/>'
		}
	});
}
</script>
</head>
<body>
<form id="applyForm" method="post">
		<table>
			<tr>
				<td><label for="content">理由:</label></td>
				<td><input class="easyui-textbox" type="text" value="<s:property value="#leaveBill.content"/>" disabled="disabled" data-options="required:true" /></td>
			</tr>
			<tr>
				<td><label for="days">请假天数:</label></td>
				<td><input class="easyui-textbox easyui-numberbox" type="text" value="<s:property value="#leaveBill.days"/>" disabled="disabled" data-options="min:0,required:true" />
				</td>
			</tr>
			<tr>
				<td><label for="remark">备注:</label></td>
				<td><input class="easyui-textbox" disabled="disabled" value="<s:property value="#leaveBill.remark"/>" data-options="multiline:true,height:'100'" style="width: 300px">
				</td>
			</tr>
			<tr>
				<td><label for="comment">评注:</label></td>
				<td><input class="easyui-textbox" name="comment" data-options="multiline:true,height:'100'" style="width: 300px">
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<s:if test="#branchList != null && #branchList.size()>0">
						<s:iterator value="#branchList" var="branch">
							<input type="button" onclick="submitForm(this);" value="<s:property value="branch"/>"/>
						</s:iterator>
					</s:if>
				</td>
			</tr>
		</table>
	</form>
	<div>
			<table class="easyui-datagrid" data-options="title:'批注列表',rownumbers:true,singleSelect:true">
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
								<a href="javascript:transactTask(<s:property value='id' />)">任务办理</a>
								<a href="javascript:showCurrentTaskDiagram(<s:property value='id' />)">显示流程图</a>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
</body>
</html>