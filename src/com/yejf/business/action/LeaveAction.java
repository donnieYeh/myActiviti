package com.yejf.business.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.ModelDriven;
import com.yejf.base.BaseAction;
import com.yejf.business.entity.LeaveBill;
import com.yejf.business.service.LeaveBillService;
import com.yejf.utils.SessionContext;
import com.yejf.utils.ValueContext;

@ParentPackage("default")
@Namespace("/business")
@Action("leave")
public class LeaveAction extends BaseAction{
	@Resource
	LeaveBillService leaveBillService;
	
	public void setLeaveBillService(LeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}
	
	public String listLeaveBill() {
		String hql = "from LeaveBill o where o.user = ?";
		Object[] user = {SessionContext.get()};
		List<LeaveBill> billList = leaveBillService.findByObj(hql, user);
		if (!billList.isEmpty()) {
			ValueContext.putValueContext("billList", billList);
		}
		return "leaveManage";
	}
	
	public String toApplyForm(){
		return "leaveApplyForm";
	}
	
	public String leaveApply(){
		
		return "leaveApply";
	}
}
