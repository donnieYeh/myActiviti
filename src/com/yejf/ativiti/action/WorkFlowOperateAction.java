package com.yejf.ativiti.action;

import java.io.File;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.yejf.ativiti.service.WorkFlowService;
import com.yejf.base.BaseAction;

@ParentPackage("json")
@Namespace("/activiti")
@Action("workFlowOpt")
public class WorkFlowOperateAction extends BaseAction {

	@Resource
	WorkFlowService workFlowService;

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
	
	
}
