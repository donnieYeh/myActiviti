package com.yejf.ativiti.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;


import java.util.Map;
import java.util.Set;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class ProcessTest {
	ProcessEngine processEngine;
	ProcessEngineConfiguration peConfiguration;
	
	@Before
	public void before(){
		peConfiguration = 
				ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti-context.xml");
		processEngine = peConfiguration.buildProcessEngine();
	}
	
	@Test
	public void deploy() {
		Deployment deployment = processEngine.getRepositoryService().createDeployment()
				.addClasspathResource("diagrams/SequenceFlow.bpmn")
				.addClasspathResource("diagrams/SequenceFlow.png")
				.name("连线")
				.deploy();
	}
	
	@Test
	public void start(){
		String pdk = "myProcess";
		ProcessDefinition pd = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey(pdk).singleResult();
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceById(pd.getId());
		System.out.println(processInstance.getProcessDefinitionId() + "流程已启动");
	}
	
	@Test
	public void queryProcessDef() throws Exception{
		List<ProcessDefinition> pdList = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().asc().list();
		for (ProcessDefinition pd : pdList) {
			System.out.println("id "+pd.getId());
			System.out.println("key "+pd.getKey());
			System.out.println("name "+pd.getName());
			System.out.println("deployId "+pd.getDeploymentId());
			System.out.println("version "+pd.getVersion());
			System.out.println("diagrams "+pd.getDiagramResourceName());
			System.out.println("=============================================");
		}
	}
	
	@Test
	public void queryProcessInst(){
		List<ProcessInstance> piList = processEngine.getRuntimeService().createProcessInstanceQuery().orderByProcessInstanceId()
				.asc().active().list();
		for (ProcessInstance pi : piList) {
			System.out.println("id "+pi.getId());
			System.out.println("key "+pi.getProcessDefinitionKey());
			System.out.println("name "+pi.getName());
			System.out.println("deployId "+pi.getDeploymentId());
			System.out.println("deployVersion "+pi.getProcessDefinitionVersion());
			System.out.println("=============================================");
		}
	}
	
	@Test//2501
	public void queryActiveTask(){
		Task task = processEngine.getTaskService().createTaskQuery().active().processInstanceId("15001").singleResult();
		System.out.println("id "+task.getId());
		System.out.println("assignee "+task.getAssignee());
		System.out.println("name "+task.getName());
		System.out.println("executionId "+task.getExecutionId());
		System.out.println("ProcessInstanceId "+task.getProcessInstanceId());
	}
	
	@Test
	public void completeTask(){
		Task task = processEngine.getTaskService().createTaskQuery().active().processInstanceId("15001").singleResult();
		Map<String, Object> vmap  = new HashMap<String,Object>();
		vmap.put("性格", "听话");
		processEngine.getTaskService().complete(task.getId(),vmap);
		System.out.println("任务："+task.getName()+":"+task.getId()+"已完成");
	}
	
	@Test
	public void queryHistoryTask(){
		List<HistoricTaskInstance> taskList = processEngine.getHistoryService().createHistoricTaskInstanceQuery()
				.processInstanceId("15001").list();
		for (HistoricTaskInstance hti : taskList) {
			System.out.println("getStartTime "+hti.getStartTime());
			System.out.println("getEndTime "+hti.getEndTime());
			System.out.println("id "+hti.getId());
			System.out.println("name "+hti.getName());
			System.out.println("owner "+hti.getOwner());
	 		System.out.println("assignee "+hti.getAssignee());
	 		System.out.println("Category "+hti.getCategory());
			System.out.println("============================================");
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void deleteDeploy(){
		ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey("测试流程").singleResult();
		Deployment deployment = processEngine.getRepositoryService().createDeploymentQuery().deploymentId(processDefinition.getDeploymentId())
				.singleResult();
		String deployName = deployment.getName()+" "+deployment.getId();
		processEngine.getRepositoryService().deleteDeploymentCascade(deployment.getId());
		
		System.out.println("删除部署"+deployName+"成功");
	}
	
	@Test
	public void viewImage(){
		InputStream in = null;
		File f = null;
		ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey("测试流程").singleResult();
		Deployment deployment = processEngine.getRepositoryService().createDeploymentQuery().deploymentId(processDefinition.getDeploymentId())
				.singleResult();
		List<String> names = processEngine.getRepositoryService().getDeploymentResourceNames(deployment.getId());
		for (String name : names) {
			System.out.println("sorceName: " + name);
			if (name.indexOf(".png")>0) {
				System.out.println("imageName: "+name);
				in = processEngine.getRepositoryService().getResourceAsStream(deployment.getId(), name);
				f = new File("e:/upload/activiti/"+name);
				try {
					FileUtils.copyInputStreamToFile(in, f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	@Test
	public void queryActivityHistroy(){
		List<HistoricActivityInstance> haiList = processEngine.getHistoryService().createHistoricActivityInstanceQuery().list();
		for (HistoricActivityInstance hai : haiList) {
			System.out.println(hai.getActivityId()+" "+hai.getActivityName()+" "+hai.getProcessInstanceId()+" "+hai.getEndTime());
//		List<HistoricProcessInstance> hpiList = processEngine.getHistoryService().createHistoricProcessInstanceQuery().list();
//		for (HistoricProcessInstance hpi : hpiList) {
//			System.out.println(hpi.getId()+" "+hpi.getName()+" "+hpi.getStartUserId()+" "+hpi.getEndTime());
		}
		
	}
	
	@Test
	public void queryUserTask(){
		String assignee = "肥肥";
		List<Task> taskList = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
		for (Task task : taskList) {
			System.out.println(task.getId()+" "+task.getName()+" "+task.getProcessInstanceId()+ " "+task.getAssignee());
		}
	}
	
	@Test
	public void queryVariables(){
		Execution exe = processEngine.getRuntimeService().createExecutionQuery().processInstanceId("15001").singleResult();
		Map<String,Object> vars = processEngine.getRuntimeService().getVariables(exe.getId());
		Set<String> varskey = vars.keySet();
		for (String string : varskey) {
			System.out.println(string+" "+vars.get(string));
		}
//		HistoricVariableInstance hvi = processEngine.getHistoryService().createHistoricVariableInstanceQuery().singleResult();
//		System.out.println(hvi.getVariableName()+" "+hvi.getValue());
	}
	
}
