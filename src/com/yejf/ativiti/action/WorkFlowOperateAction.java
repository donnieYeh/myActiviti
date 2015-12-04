
package com.yejf.ativiti.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.util.StringUtils;

import com.yejf.ativiti.service.WorkFlowService;
import com.yejf.base.BaseAction;
import com.yejf.business.entity.LeaveBill;
import com.yejf.business.service.LeaveBillService;
import com.yejf.utils.SessionContext;

@ParentPackage("json")
@Namespace("/activiti")
@Action("workFlowOpt")
public class WorkFlowOperateAction extends BaseAction {

	@Resource
	WorkFlowService workFlowService;

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
	
	@Resource
	LeaveBillService leaveBillService;
	
	public void setLeaveBillService(LeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}

	public String startProcess(){
		workFlowService.setAuthenticatedUserId(SessionContext.get().getId()+"");
		String billId = achieveRequest().getParameter("billId");
		ProcessInstance pi = workFlowService.startProcessWithBillId(billId);
		workFlowService.completeTaskByProcInsId(pi.getId());
		return SUCCESS;
	}
	
	public String completeTask(){
		String taskId = achieveRequest().getParameter("taskId");
		String comment = achieveRequest().getParameter("comment");
		String option = achieveRequest().getParameter("option");
		Map<String, Object> variable = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(option) && !"默认提交".equals(option) && !"提交申请".equals(option)) {
			variable.put("approve", option);
			comment += option;//评论末尾增加操作内容
		}
		String businessKey = workFlowService.getProcInsByTask(taskId).getBusinessKey();
		workFlowService.completeTask(taskId,comment,variable);
		ProcessInstance processInstance = workFlowService.getProcInsByTask(taskId);
		if (processInstance == null) {
			String billId = businessKey.substring(businessKey.indexOf(".")+1);
			LeaveBill bill = leaveBillService.findById(Long.parseLong(billId));
			bill.setState(2);
			leaveBillService.createOrModify(bill);
		}
		return SUCCESS;
	}
	
}

