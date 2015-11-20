package com.yejf.ativiti.action;

import javax.annotation.Resource;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.yejf.ativiti.service.WorkFlowService;
import com.yejf.base.BaseAction;
import com.yejf.business.service.LeaveBillService;

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
		String billId = achieveRequest().getParameter("billId");
		ProcessInstance pi = workFlowService.startProcessWithBillId(billId);
		workFlowService.completeTask(pi.getId());
		return SUCCESS;
	}
	
	public void completeTask(){
		workFlowService.completeTask("37501");
	}
}
