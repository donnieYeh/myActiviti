package com.yejf.ativiti.dao;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Repository;

@Repository("workFlowDao")
public class WorkFlowDaoImpl implements WorkFlowDao {

	@Resource
	ProcessEngine processEngine;

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	@Override
	public List<Deployment> findDeploymentList() {
		return processEngine.getRepositoryService().createDeploymentQuery().orderByDeploymenTime().desc().list();
	}

	@Override
	public List<ProcessDefinition> findProcessDefinitionlist() {
		List<ProcessDefinition> proDef = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().desc().list();
		return proDef;
	}
}
