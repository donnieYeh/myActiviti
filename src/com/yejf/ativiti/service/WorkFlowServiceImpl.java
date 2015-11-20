package com.yejf.ativiti.service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
		workFlowDao.deployProcess(file,deployName);
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
		//业务处理，设置流程业务状态为1
		LeaveBill leaveBill = leaveBillDao.findById(Long.parseLong(billId));
		leaveBill.setState(1);
		//启动流程，分别取得3个参数设入流程
		String key = leaveBill.getClass().getSimpleName();
		String businessKey = key+"."+billId;
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("performer", SessionContext.get().getId());
		return workFlowDao.startProcessByKey(key, businessKey, variables);
	}
	
	@Override
	public List<Task> findTaskListByUserId(String userId) {
		return workFlowDao.findTaskListByAssigneeId(userId);
	}
	
	@Override
	public void completeTask(String procInsid) {
		//完成表单填写任务，根据上一步，取得流程实例id来完成任务
		Task task = workFlowDao.findActivityTaskByProcInsId(procInsid);
		workFlowDao.completeTask(task.getId());
	}
}
