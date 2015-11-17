package com.yejf.ativiti.service;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import com.yejf.ativiti.dao.WorkFlowDao;

@Service("workFloweService")
public class WorkFlowServiceImpl implements WorkFlowService {

	@Resource
	WorkFlowDao workFlowDao;

	public void setWorkFlowDao(WorkFlowDao workFlowDao) {
		this.workFlowDao = workFlowDao;
	}
	
	@Override
	public List<Deployment> findDeploymentList() {
		return workFlowDao.findDeploymentList();
	}
	
	@Override
	public List<ProcessDefinition> findProcessDefinitionlist() {
		return workFlowDao.findProcessDefinitionlist();
	}
}
