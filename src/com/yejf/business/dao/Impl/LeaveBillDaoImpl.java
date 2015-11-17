package com.yejf.business.dao.Impl;


import org.springframework.stereotype.Repository;

import com.gdth.base.dao.impl.BaseDaoImpl;
import com.yejf.business.dao.LeaveBillDao;
import com.yejf.business.entity.LeaveBill;

@Repository("leaveBillDao")
//@Component("leaveBillDao")
public class LeaveBillDaoImpl extends BaseDaoImpl<LeaveBill, Long> implements LeaveBillDao {

}
