package com.yejf.business.service.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gdth.base.service.impl.BaseServiceImpl;
import com.yejf.business.dao.LeaveBillDao;
import com.yejf.business.entity.LeaveBill;
import com.yejf.business.service.LeaveBillService;

@Service("leaveBillService")
//@Component("leaveBillService")
public class LeaveBillServiceImpl extends BaseServiceImpl<LeaveBill, Long> implements LeaveBillService {

	LeaveBillDao leaveBillDao;
	
	@Resource
	public void setLeaveBillDao(LeaveBillDao leaveBillDao) {
		this.leaveBillDao = leaveBillDao;
		super.setDao(leaveBillDao);
	}

	@Override
	public LeaveBill othersDeal(LeaveBill t) {
		// TODO Auto-generated method stub
		return null;
	}


}
