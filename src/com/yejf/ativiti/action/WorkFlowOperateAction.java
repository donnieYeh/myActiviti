package com.yejf.ativiti.action;

import java.io.File;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.gdth.base.entity.BaseEntity;
import com.yejf.ativiti.service.WorkFlowService;
import com.yejf.base.BaseAction;
import com.yejf.business.entity.LeaveBill;
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
		String key = "";
		String billId = achieveRequest().getParameter("billId");
		workFlowService.startProcessWithBillId(billId);
		
		return SUCCESS;
	}
}
