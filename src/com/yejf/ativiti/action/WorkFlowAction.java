package com.yejf.ativiti.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.util.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.yejf.ativiti.entity.WorkFlowBean;
import com.yejf.ativiti.service.WorkFlowService;
import com.yejf.base.BaseAction;
import com.yejf.business.entity.LeaveBill;
import com.yejf.business.service.LeaveBillService;
import com.yejf.utils.SessionContext;
import com.yejf.utils.ValueContext;

@ParentPackage("default")
@Namespace("/activiti")
@Action("workFlow")
public class WorkFlowAction extends BaseAction implements ModelDriven<WorkFlowBean>{
	@Resource
	WorkFlowService workFlowService;

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
	
	@Resource
	LeaveBillService leaveBillService;
	
	public void setLeaveBillService(LeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}

	WorkFlowBean workFlowBean = new WorkFlowBean();
	
	@Override
	public WorkFlowBean getModel() {
		return workFlowBean;
	}

	public String deployManage() {
		List<Deployment> deployList = workFlowService.findDeploymentList();
		ValueContext.putValueContext("deployList", deployList);
		List<ProcessDefinition> prodefList = workFlowService.findProcessDefinitionlist();
		ValueContext.putValueContext("prodefList", prodefList);
		return "deployManage";
	}
	
	public String taskManage(){
		Long userId = SessionContext.get().getId();
		List<Task> tasks = workFlowService.findTaskListByUserId(""+userId);
		ValueContext.putValueContext("tasks", tasks);
		return "taskManage";
	}
	
	public String deployProcess(){
		String deployName = workFlowBean.getFileName();
		File file = workFlowBean.getProcessFile();
		if (StringUtils.isEmpty(deployName)) {
			deployName = workFlowBean.getProcessFileFileName();
		}
		//开始部署文件
		workFlowService.deployProcess(file,deployName);
		return deployManage();
	}

	public String showDiagram(){
		String proceDefId = "";
		proceDefId = achieveRequest().getParameter("procDefId");
		try {
			InputStream in = workFlowService.getDiagramInputStream(proceDefId);
			OutputStream out = achieveResponse().getOutputStream();
			for (int i = -1; (i = in.read()) != -1;) {
				out.write(i);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String deleteDeployment(){
		String deployId = "";
		deployId = achieveRequest().getParameter("deployId");
		if (!StringUtils.isEmpty(deployId)) {
			workFlowService.removeDeploymentById(deployId);
		}
		return deployManage();
	}
	
	public String toDiagramView(){
		String taskId = achieveRequest().getParameter("taskId");
		ActivityImpl activityImpl = workFlowService.getActivityImplByTask(taskId);
		Map<String, Object> coordinate = workFlowService.findTaskCoodinate(activityImpl);
		ProcessDefinition processDefinition = workFlowService.getProcDefByTask(taskId);
		ValueContext.putValueContext("procDefId", processDefinition.getId());
		ValueContext.putValueContext("taskCoordinate", coordinate);
		return "diagramView";
	}
	
	public String transactTask(){
		String taskId = achieveRequest().getParameter("taskId");
		ProcessInstance processInstance = workFlowService.getProcInsByTask(taskId);
		String businessKey = processInstance.getBusinessKey();
		String billId = businessKey.substring(businessKey.indexOf(".")+1);
		LeaveBill bill = leaveBillService.findById(Long.parseLong(billId));
		ValueContext.putValueContext("leaveBill", bill);
		
		List<String> branchList = workFlowService.getNextFlowList(taskId);
		ValueContext.putValueContext("branchList", branchList);
		ValueContext.putValueContext("taskId", taskId);
		
		List<Comment> commentList = workFlowService.findCommentByProcIns(processInstance);
		ValueContext.putValueContext("commentList", commentList);
		return "transactTask";
	}
	
}
