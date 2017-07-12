package com.sj.privilegemanagement.manager;

import java.util.List;

import com.sj.privilegemanagement.entity.Staff;

public interface StaffManager extends BaseManager<Staff> {

	/**
	 * 根据 部门id 查人员
	 * @param deptid
	 * @return
	 */
	public List<Staff> findStaffByDepId(Long deptid);
}
