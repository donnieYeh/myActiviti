package com.yejf.ativiti.dao;

import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

public interface WorkFlowDao {

	List<Deployment> findDeploymentList();

	List<ProcessDefinition> findProcessDefinitionlist();

}
