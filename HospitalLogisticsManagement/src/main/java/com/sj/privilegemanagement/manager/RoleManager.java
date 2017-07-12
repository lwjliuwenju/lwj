package com.sj.privilegemanagement.manager;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.sj.privilegemanagement.entity.Role;
import com.sj.privilegemanagement.entity.UserOfRole;

public interface RoleManager extends BaseManager<Role> {

	public void setMenus();
	
	public List<Role> findRolesByUserId(long UserId);

	public JSONArray findJsonList();

	public List<Map<String, Object>> findall(int pageSize, int start);

	public Long findcount(int pageSize, int start);

	public void removerMenusByRoleId();

	public List<UserOfRole> findbyuserid(long userid);

	public void setUser();

	public List<UserOfRole> findbyroleId(long roleId);

	public void removerUserByRoleId();
}
