package com.sj.privilegemanagement.dao;

import java.util.List;
import java.util.Map;

import com.sj.common.dao.BaseDao;
import com.sj.privilegemanagement.entity.Role;

public interface RoleDao extends BaseDao<Role> {

	List<Map<String, Object>> findall(int pageSize, int start);

	Long findcount(int pageSize, int start);

}
