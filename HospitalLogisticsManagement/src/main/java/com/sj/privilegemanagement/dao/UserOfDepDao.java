package com.sj.privilegemanagement.dao;

import com.sj.common.dao.BaseDao;
import com.sj.privilegemanagement.entity.UserOfDepAdmin;

public interface UserOfDepDao extends BaseDao<UserOfDepAdmin> {

	/**
	 * 根据部门id删除对应数据
	 * @param id 
	 */
	void deleteByDepId(String id);

}
