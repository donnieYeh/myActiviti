package com.yejf.ativiti.service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.gdth.base.dao.BaseDao;
import com.yejf.ativiti.dao.WorkFlowDao;
import com.yejf.business.dao.LeaveBillDao;
import com.yejf.business.entity.LeaveBill;
import com.yejf.utils.SessionContext;

@Service("workFloweService")
public class WorkFlowServiceImpl implements WorkFlowService {
	@Resource
	ProcessEngine processEngine;

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	@Resource
	WorkFlowDao workFlowDao;

	@Resource
	private LeaveBillDao leaveBillDao;

	public void setWorkFlowDao(WorkFlowDao workFlowDao) {
		this.workFlowDao = workFlowDao;
	}

	public void setLeaveBillDao(LeaveBillDao leaveBillDao) {
		this.leaveBillDao = leaveBillDao;
	}

	@Override
	public List<Deployment> findDeploymentList() {
		return workFlowDao.findDeploymentList();
	}

	@Override
	public List<ProcessDefinition> findProcessDefinitionlist() {
		return workFlowDao.findProcessDefinitionlist();
	}

	@Override
	public void deployProcess(File file, String deployName) {
		workFlowDao.deployProcess(file, deployName);
	}

	@Override
	public InputStream getDiagramInputStream(String proceDefId) {
		return workFlowDao.getDiagramInputStream(proceDefId);
	}

	@Override
	public void removeDeploymentById(String deployId) {
		workFlowDao.removeDeploymentById(deployId);
	}

	@Override
	public ProcessInstance startProcessWithBillId(String billId) {
		// 业务处理，设置流程业务状态为1
		LeaveBill leaveBill = leaveBillDao.findById(Long.parseLong(billId));
		leaveBill.setState(1);
		// 启动流程，分别取得3个参数设入流程
		String key = leaveBill.getClass().getSimpleName();
		String businessKey = key + "." + billId;
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("performer", SessionContext.get().getId());
		return workFlowDao.startProcessByKey(key, businessKey, variables);
	}

	@Override
	public List<Task> findTaskListByUserId(String userId) {
		return workFlowDao.findTaskListByAssigneeId(userId);
	}

	@Override
	public void completeTaskByProcInsId(String procInsid) {
		// 完成表单填写任务，根据上一步，取得流程实例id来完成任务
		Task task = workFlowDao.findActivityTaskByProcInsId(procInsid);
		workFlowDao.completeTaskById(task.getId());
	}

	@Override
	public void completeTaskById(String taskId) {
		workFlowDao.completeTaskById(taskId);
	}

	@Override
	public ProcessDefinition getProcDefByTask(String taskId) {
		String procDefId = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult()
				.getProcessDefinitionId();
		return processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(procDefId)
				.singleResult();
	}

	@Override
	public Map<String, Object> findTaskCoodinate(String taskId) {
		Map<String, Object> coordinate = new HashMap<String, Object>();
		// 任务对象用于获取对应的流程实例和流程定义
		Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		// 流程实例用户当前活动节点id
		ProcessInstance processInstance = (ProcessInstance) processEngine.getRuntimeService()
				.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		// 流程定义用于根据活动节点id获取活动节点的信息
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine
				.getRepositoryService().getProcessDefinition(task.getProcessDefinitionId());
		// 活动节点对象，取出其中的坐标属性封装并返回
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(processInstance.getActivityId());
		coordinate.put("x", activityImpl.getX());
		coordinate.put("y", activityImpl.getY());
		coordinate.put("height", activityImpl.getHeight());
		coordinate.put("width", activityImpl.getWidth());
		return coordinate;
	}

	@Override
	public ProcessInstance getProcInsByTask(String taskId) {
		Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		return processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
	}
}
