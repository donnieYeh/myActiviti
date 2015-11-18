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
		initToolBar();
	});
	function leaveApply(){
		var url = "business/leave!toApplyForm.do";
		parent.addTab("申请请假",url);
	}
	
	function initToolBar(){
		$('.easyui-datagrid').datagrid({
			toolbar: [{
				iconCls: 'icon-edit',
				text:"请假申请",
				handler: function(){leaveApply();}
			},{
				iconCls: 'icon-edit',
				text:"启动请假流程",
				handler: function(){
					window.location.href= "<%=contextPath %>/activiti/workFlow!startProcess.do?procDefId="+procDefId
				}
			},{
				iconCls: 'icon-edit',
				text:"修改",
				handler: function(){window.location.href= ""}
			},{
				iconCls: 'icon-edit',
				text:"删除",
				handler: function(){leaveApply();}
			}]
		});
	}
</script>
</head>
<body class="easyui-layout">
<div region="north" style="height: 150px;">
测试
</div>
<div region="center">
	<table class="easyui-datagrid" data-options="rownumbers:true,singleSelect:true">   
    <thead>   
        <tr>   
            <th data-options="field:'id',hidden:true," >id</th>   
            <th data-options="field:'content'">理由</th>   
            <th data-options="field:'days'">请假天数</th>   
            <th data-options="field:'leaveDate'">请假时间</th>   
            <th data-options="field:'remark'">备注</th>   
            <th data-options="field:'state'">假单状态</th>   
        </tr>   
    </thead>   
    <tbody> 
    	<s:iterator value="billList">
    	<tr>
    		<td><s:property value="id"/></td>	
    		<td><s:property value="content"/></td>
    		<td><s:property value="days"/></td>
    		<td><s:date name="leaveDate" format="YYYY-MM-dd HH:mm:ss"/></td>
    		<td><s:property value="remark"/></td>
    		<td>
				<s:if test="state == 0">初始录入</s:if>
				<s:elseif test="state==1">审核中</s:elseif>
				<s:elseif test="state==2">审核通过</s:elseif>
			</td>
    	</tr>
    	</s:iterator>  
    </tbody>   
</table>  

	
</div>
</body>
</html>