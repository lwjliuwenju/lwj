package com.sj.privilegemanagement.dao;

import com.sj.common.dao.BaseDao;
import com.sj.privilegemanagement.entity.MenuOfRole;

public interface MenuOfRoleDao extends BaseDao<MenuOfRole> {

	void removerMenusByRoleId(String roleId);

	void removerUserByRoleId(String roleId);

}
