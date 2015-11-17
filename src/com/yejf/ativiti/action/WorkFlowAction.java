package com.yejf.ativiti.action;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.yejf.ativiti.service.WorkFlowService;
import com.yejf.base.BaseAction;
import com.yejf.utils.SessionContext;
import com.yejf.utils.ValueContext;

@ParentPackage("default")
@Namespace("/activiti")
@Action("workFlow")
public class WorkFlowAction extends BaseAction {
	@Resource
	WorkFlowService workFlowService;

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public String deployManage() {
		List<Deployment> deployList = workFlowService.findDeploymentList();
		ValueContext.putValueContext("deployList", deployList);
		List<ProcessDefinition> prodefList = workFlowService.findProcessDefinitionlist();
		ValueContext.putValueContext("prodefList", prodefList);
		return "deployManage";
	}

}
