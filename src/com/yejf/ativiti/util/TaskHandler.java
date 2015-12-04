package com.yejf.ativiti.util;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.explorer.ui.custom.TaskListHeader;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yejf.business.dao.EmployeeDao;
import com.yejf.business.entity.Employee;
import com.yejf.business.service.EmployeeService;
import com.yejf.utils.SessionContext;
import com.yejf.business.dao.EmployeeDao;

public class TaskHandler implements TaskListener {
	@Override
	public void notify(DelegateTask delegateTask) {
		Long userId = SessionContext.get().getId();
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext
				.getServletContext());
		EmployeeDao employeeDao = (EmployeeDao) ac.getBean("employeeDao");
		Employee user = employeeDao.findById(userId);
		delegateTask.setAssignee(""+user.getManager().getId());
	}
}
