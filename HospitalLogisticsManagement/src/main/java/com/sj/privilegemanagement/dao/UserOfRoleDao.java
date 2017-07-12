package com.sj.privilegemanagement.dao;

import com.sj.common.dao.BaseDao;
import com.sj.privilegemanagement.entity.UserOfRole;

public interface UserOfRoleDao extends BaseDao<UserOfRole> {

	void removeRolesByUserId(Long userId);

}
