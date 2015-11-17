package com.yejf.business.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.yejf.base.BaseAction;
import com.yejf.utils.SessionContext;

@Namespace("/business")
@ParentPackage("default")
@Action("demo")
public class DemoAction extends BaseAction {
	public String testReturn(){
		return "demo";
	}
	
	public String logout(){
		SessionContext.setUser(null);
		return "login";
	}
}
