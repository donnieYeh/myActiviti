package com.yejf.ativiti.dao;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

public interface WorkFlowDao {

	List<Deployment> findDeploymentList();

	List<ProcessDefinition> findProcessDefinitionlist();

	void deployProcess(File file, String deployName);
	
	InputStream getDiagramInputStream(String proceDefId);

	void removeDeploymentById(String deployId);

	void startProcessByKey(String key,String businessKey,Map<String,Object> variables);

}
