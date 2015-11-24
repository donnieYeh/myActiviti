package com.yejf.ativiti.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.yejf.ativiti.action.E;


public interface WorkFlowService {

	List<Deployment> findDeploymentList();

	List<ProcessDefinition> findProcessDefinitionlist();

	void deployProcess(File file, String deployName);

	InputStream getDiagramInputStream(String proceDefId);

	void removeDeploymentById(String deployId);

	ProcessInstance startProcessWithBillId(String billId);

	List<Task> findTaskListByUserId(String userId);

	void completeTaskByProcInsId(String id);

	void completeTaskById(String taskId, Map<String, Object> variable);

	ProcessDefinition getProcDefByTask(String taskId);

	Map<String, Object> findTaskCoodinate(ActivityImpl activityImpl);

	ProcessInstance getProcInsByTask(String taskId);

	ActivityImpl getActivityImplByTask(String taskId);

	List<String> getFlowList(ActivityImpl activityImpl);

	void completeTask(String taskId, String comment, Map<String, Object> variable);

	List<E> findCommentByProcIns(ProcessInstance processInstance);
}
