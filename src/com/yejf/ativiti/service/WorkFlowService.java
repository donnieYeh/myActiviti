package com.yejf.ativiti.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

public interface WorkFlowService {

	List<Deployment> findDeploymentList();

	List<ProcessDefinition> findProcessDefinitionlist();

	void deployProcess(File file, String deployName);

	InputStream getDiagramInputStream(String proceDefId);

	void removeDeploymentById(String deployId);

	void startProcessWithBillId(String billId);
}
