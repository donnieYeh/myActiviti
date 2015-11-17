package com.yejf.base;

import java.util.List;

import javax.annotation.Resource;





import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.util.StringUtils;

import com.yejf.business.entity.Employee;
import com.yejf.utils.SessionContext;
import com.yejf.utils.ValueContext;


public class LoginAction extends BaseAction{
	private String errorMsg = "";
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	@Resource
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public String login(){	
		String username = achieveRequest().getParameter("username");
		String password = achieveRequest().getParameter("password");
		if (!StringUtils.isEmpty(password) && !StringUtils.isEmpty(username)) {
			String hql = "from Employee o where o.name=?";
			List<Employee> userList = (List<Employee>) hibernateTemplate.find(hql,username);
				if(!userList.isEmpty() && password.equals(userList.get(0).getPassword())){
					SessionContext.setUser(userList.get(0));
					ValueContext.putValueContext("username", username);
					return "welcome";
				}else{
					errorMsg = "用户名或密码错误！！！";
					return "login";
				}
		}else{
			errorMsg = "操作非法，请重新登陆!";
			return "login";
		}
		
	}
}
