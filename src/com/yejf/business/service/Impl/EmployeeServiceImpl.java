package com.yejf.business.service.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdth.base.service.impl.BaseServiceImpl;
import com.yejf.business.dao.EmployeeDao;
import com.yejf.business.entity.Employee;
import com.yejf.business.service.EmployeeService;

@Service("employeeService")
public class EmployeeServiceImpl extends BaseServiceImpl<Employee,Long> implements EmployeeService {

	@Resource
	EmployeeDao employeeDao;
	
	public void setEmployeeDao(EmployeeDao employeeDao) {
		super.setDao(employeeDao);
		this.employeeDao = employeeDao;
	}

	@Override
	public Employee othersDeal(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}


}
