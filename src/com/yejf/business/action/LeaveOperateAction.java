package com.yejf.business.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.ModelDriven;
import com.yejf.base.BaseAction;
import com.yejf.business.entity.LeaveBill;
import com.yejf.business.service.LeaveBillService;
import com.yejf.utils.SessionContext;

@ParentPackage("json")
@Namespace("/business")
@Action("leaveOpt")
public class LeaveOperateAction extends BaseAction implements ModelDriven<LeaveBill>{
	@Resource
	LeaveBillService leaveBillService;
	
	public void setLeaveBillService(LeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}
	
	private LeaveBill leaveBill;

	@Override
	public LeaveBill getModel() {
		if (leaveBill == null) {
			leaveBill = new LeaveBill();
		}
		return leaveBill;
	}
	
	public String applyForm_save(){
		leaveBill.setState(0);
		leaveBill.setUser(SessionContext.get());
		leaveBillService.create(leaveBill);
		return SUCCESS;
	}
}
