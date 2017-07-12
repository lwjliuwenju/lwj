package com.sj.privilegemanagement.dao.impl;

import com.sj.common.dao.impl.BaseDaoImpl;
import com.sj.privilegemanagement.dao.MenuOfRoleDao;
import com.sj.privilegemanagement.entity.MenuOfRole;

public class MenuOfRoleDaoImpl extends BaseDaoImpl<MenuOfRole> implements MenuOfRoleDao {

	@Override
	public void removerMenusByRoleId(String roleId) {
		String hql = "delete from MenuOfRole m where m.roleId = " + roleId;
		this.executeHql(hql);
	}

	@Override
	public void removerUserByRoleId(String roleId) {
		String hql = "delete from UserOfRole m where m.roleId = " + roleId;
		this.executeHql(hql);
	}


}
