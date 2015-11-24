package com.yejf.ativiti.service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
	public void completeTaskById(String taskId, Map<String, Object> variable) {
		processEngine.getTaskService().complete(taskId, variable, true);
	}

	@Override
	public ProcessDefinition getProcDefByTask(String taskId) {
		String procDefId = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult()
				.getProcessDefinitionId();
		return processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(procDefId)
				.singleResult();
	}

	@Override
	public Map<String, Object> findTaskCoodinate(ActivityImpl activityImpl) {
		Map<String, Object> coordinate = new HashMap<String, Object>();
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

	@Override
	public ActivityImpl getActivityImplByTask(String taskId) {
		// 任务对象用于获取对应的流程实例和流程定义
		Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		// 流程实例用户当前活动节点id
		ProcessInstance processInstance = (ProcessInstance) processEngine.getRuntimeService()
				.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		// 流程定义用于根据活动节点id获取活动节点的信息
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine
				.getRepositoryService().getProcessDefinition(task.getProcessDefinitionId());
		// 活动节点对象，取出其中的坐标属性封装并返回
		return processDefinitionEntity.findActivity(processInstance.getActivityId());
	}

	@Override
	public List<String> getFlowList(ActivityImpl activityImpl) {
		List<String> list = new ArrayList<String>();
		List<PvmTransition> branchList = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvm : branchList) {
			String name = (String) pvm.getProperty("name");
			if (!StringUtils.isEmpty(name)) {
				list.add(name);
			} else {
				list.add("默认批准");
			}
		}
		return list;
	}

	@Override
	public void completeTask(String taskId, String comment, Map<String, Object> variable) {
		if (!StringUtils.isEmpty(comment)) {
			String procInsId = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult()
					.getProcessInstanceId();
			// 由于流程用户上下文对象是线程独立的，所以要在需要的位置设置，要保证设置和获取操作在同一个线程中
			Authentication.setAuthenticatedUserId(SessionContext.get().getName());// 批注人的名称
			// 添加批注信息
			processEngine.getTaskService().addComment(taskId, procInsId, comment);// comment为批注内容
		}

		processEngine.getTaskService().complete(taskId, variable);
	}

	@Override
	public List<E> findCommentByProcIns(ProcessInstance processInstance) {
		List<HistoricDetail> list = processEngine.getHistoryService().createHistoricDetailQuery()
				.processInstanceId(processInstance.getId()).list();
		for (HistoricDetail hd : list) {
			hd.get
		}
		return null;
	}
}
