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
	function leaveApply(name,billId){
		var param = "";
		if(billId){
			param = "?billId="+billId;
		}
		var url = "business/leave!toApplyForm.do"+param;
		parent.addTab(name,url);
	}
	
	function initToolBar(){
		$('.easyui-datagrid').datagrid({
			onClickRow:function(index,row){
				if(row.state.trim() == '审核中' || row.state.trim() == '审核通过'){
					$('#startBtn').linkbutton('disable');
 					$('#modifyBtn').linkbutton('disable');
					$('#deleteBtn').linkbutton('disable');
				} else{
					$('#startBtn').linkbutton('enable');
 					$('#modifyBtn').linkbutton('enable');
					$('#deleteBtn').linkbutton('enable');
				}
			},
			toolbar: [{
				iconCls: 'icon-edit',
				text:"请假申请",
				handler: function(){leaveApply("申请请假");}
			},{
				id:'startBtn',
				iconCls: 'icon-edit',
				text:"启动请假流程",
				disabled:true,
				handler: function(){
					var row = $('.easyui-datagrid').datagrid('getSelected');
					if (row == null){
						showMsg("show", "没有流程可启动", "请选择一行")
					}else{
						var url="<%=contextPath %>/activiti/workFlowOpt!startProcess.do";
						var data = {"billId" : row.id};
						$.post(url,data,function(n){
							location.reload(true);
						})
					}
				}
			},{
				id:'modifyBtn',
				iconCls: 'icon-edit',
				text:"修改",
				disabled:true,
				handler: function(){
					var row = $('.easyui-datagrid').datagrid('getSelected');
					leaveApply("修改申请",row.id);
				}
			},{
				id:'deleteBtn',
				iconCls: 'icon-edit',
				text:"删除",
				disabled:true,
				handler: function(){
					var row = $('.easyui-datagrid').datagrid('getSelected');
					var url="leaveOpt!removeBill.do";
					var data={"id":row.id}
					function callback(){
						window.location.reload(true);
					}
					$.post(url,data,callback());
				}
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