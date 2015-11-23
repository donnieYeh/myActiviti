package com.yejf.ativiti.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public interface WorkFlowService {

	List<Deployment> findDeploymentList();

	List<ProcessDefinition> findProcessDefinitionlist();

	void deployProcess(File file, String deployName);

	InputStream getDiagramInputStream(String proceDefId);

	void removeDeploymentById(String deployId);

	ProcessInstance startProcessWithBillId(String billId);

	List<Task> findTaskListByUserId(String userId);

	void completeTaskByProcInsId(String id);

	void completeTaskById(String taskId);

	ProcessDefinition getProcDefByTask(String taskId);

	Map<String, Object> findTaskCoodinate(String taskId);

	ProcessInstance getProcInsByTask(String taskId);
}
