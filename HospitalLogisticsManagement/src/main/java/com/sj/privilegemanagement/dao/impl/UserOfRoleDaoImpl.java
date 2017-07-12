package com.sj.privilegemanagement.dao.impl;

import com.sj.common.dao.impl.BaseDaoImpl;
import com.sj.privilegemanagement.dao.UserOfRoleDao;
import com.sj.privilegemanagement.entity.UserOfRole;

public class UserOfRoleDaoImpl extends BaseDaoImpl<UserOfRole> implements UserOfRoleDao {

	@Override
	public void removeRolesByUserId(Long userId) {
		String hql = "delete from UserOfRole u where u.userId = " + userId;
		this.executeHql(hql);
	}

}
