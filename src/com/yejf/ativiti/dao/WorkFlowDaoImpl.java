package com.yejf.ativiti.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
	
	@Override
	public void deployProcess(File file, String deployName) {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			processEngine.getRepositoryService()
				.createDeployment()
				.addZipInputStream(zipInputStream)
				.name(deployName)
				.deploy();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public InputStream getDiagramInputStream(String proceDefId) {
		InputStream is = processEngine.getRepositoryService().getProcessDiagram(proceDefId);
		return is;
	}
	
	@Override
	public void removeDeploymentById(String deployId) {
		processEngine.getRepositoryService().deleteDeploymentCascade(deployId);
	}
	
	@Override
	public ProcessInstance startProcessByKey(String key,String businessKey,Map<String,Object> variables) {
		return processEngine.getRuntimeService().startProcessInstanceByKey(key, businessKey, variables);
	}
	
	@Override
	public Task findActivityTaskByProcInsId(String procInsId) {
		return processEngine.getTaskService().createTaskQuery().processInstanceId(procInsId).active().singleResult();
	}
	
	@Override
	public void completeTask(String taskId) {
		processEngine.getTaskService().complete(taskId);
	}
	
	@Override
	public List<Task> findTaskListByAssigneeId(String userId) {
		return processEngine.getTaskService().createTaskQuery().taskAssignee(userId).list();
	}
}
