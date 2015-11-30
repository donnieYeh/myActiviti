<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/js/common.jsp"%>
<script type="text/javascript">
	$(function() {
		initForm();
	})

	function initForm() {
		$('#applyForm').form({
			url : "leaveOpt!applyForm_save.do",
			onSubmit : function() {
				var isValidate = $(this).form("validate");
				if (!isValidate) {
					showMsg("show", 1000, "提交失败", "您有必填信息需要填", 2000)
				}
				return isValidate;
			},
			success : function(data) {
				var node = parent.$("#nl1").tree('find', 'qjgl');
				parent.addTab(node.text, node.attributes.url);
				parent.$("#center_tabs").tabs("close", '申请请假');
			}
		});
	}

	function submitForm() {
		$('#applyForm').form('submit');
	}
</script>
</head>
<body>
	<form id="applyForm" method="post">
		<table>
			<tr>
				<td><label for="content">理由:</label></td>
				<td><input class="easyui-textbox" type="text" name="content" value="<s:property value='#bill.content'/>" data-options="required:true" /></td>
			</tr>
			<tr>
				<td><label for="days">请假天数:</label></td>
				<td><input class="easyui-textbox easyui-numberbox" type="text" name="days" value="<s:property value='#bill.days'/>"
						data-options="min:0,required:true"
					/></td>
			</tr>
			<tr>
				<td><label for="remark">备注:</label></td>
				<td><input class="easyui-textbox" name="remark" value="<s:property value='#bill.remark'/>" data-options="multiline:true,height:'200'"
						style="width: 300px"
					></td>
			</tr>
			<tr>
				<td><input type="hidden" name="id" value="<s:property value='#bill.id'/>"></td>
				<td><input type="button" onclick="submitForm()" value="保存" /> <input type="reset" value="重置" /></td>
			</tr>
		</table>
	</form>
</body>
</html>