package com.sj.privilegemanagement.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sj.privilegemanagement.dao.StaffDao;
import com.sj.privilegemanagement.entity.Staff;
import com.sj.privilegemanagement.manager.StaffManager;

public class StaffManagerImpl extends BaseManagerImpl<Staff> implements StaffManager {
	
	@Override
	public List<Staff> findStaffByDepId(Long deptid) {
		String hql = "";
		List<Staff> staffs =new ArrayList<Staff>();
		if(deptid == null || 0==deptid){
			hql = "from Staff s where s.enable=1";
			staffs = getStaffDao().findList(hql);
		}else{
			hql ="from Staff s where s.enable=1 and s.deptId =:deptId";
			Map<String,Object> map = new HashMap();
			map.put("deptId", deptid);
			 staffs = getStaffDao().findList(hql, map);
		}
		return staffs;
	}
	
	@Override
	public Long findCountNum(String deptid) {
		if(StringUtils.isBlank(deptid))
			return getStaffDao().findCountNum();
		String hql = "select count(s) from Staff s where s.deptId = :deptId";
		return getStaffDao().findCountNum(hql);
	}
	
	private StaffDao getStaffDao() {
		return (StaffDao)getBaseDao();
	}


}
