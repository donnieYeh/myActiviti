package com.yejf.business.dao.Impl;

import org.springframework.stereotype.Repository;

import com.gdth.base.dao.impl.BaseDaoImpl;
import com.yejf.business.dao.EmployeeDao;
import com.yejf.business.entity.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl extends BaseDaoImpl<Employee,Long> implements EmployeeDao {


}
