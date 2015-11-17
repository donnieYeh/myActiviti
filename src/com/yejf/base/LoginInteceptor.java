package com.yejf.base;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.yejf.business.entity.Employee;
import com.yejf.utils.SessionContext;
import com.yejf.utils.ValueContext;

public class LoginInteceptor implements Interceptor {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("自定义拦截器，待编辑");
		Employee user = SessionContext.get();
		if (user == null) {
			//获取request对象
			HttpServletRequest request = (HttpServletRequest) ServletActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
		    String requestType = request.getHeader("X-Requested-With");//获取requestType以判断请求类型
			if(requestType!=null && requestType.equals("XMLHttpRequest")){//如果是ajax请求
				//打印字符串用于公共js匹配
				PrintWriter printWriter = ServletActionContext.getResponse().getWriter();  		
                printWriter.print("登陆超时，请重新登陆");  
                printWriter.flush();  
                printWriter.close();  
                return null;
			}else{
				ValueContext.putValueContext("errorMsg", "登陆超时，请重新登陆");
				return "login";
			}
		}
			return invocation.invoke();
	}

}
