package com.yejf.ativiti.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.util.StringUtils;

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
		workFlowService.completeTaskByProcInsId(pi.getId());
		return SUCCESS;
	}
	
	public String completeTask(){
		String taskId = achieveRequest().getParameter("taskId");
		String comment = achieveRequest().getParameter("comment");
		String option = achieveRequest().getParameter("option");
		Map<String, Object> variable = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(option) && !option.equals("默认提交")) {
			variable.put("approve", option);
		}
		workFlowService.completeTask(taskId,comment,variable);
		return SUCCESS;
	}
	
}
