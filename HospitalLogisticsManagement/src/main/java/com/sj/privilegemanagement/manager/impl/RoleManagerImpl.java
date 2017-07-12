package com.sj.privilegemanagement.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import com.sj.common.impl.FilterChainDefinitionsService;
import com.sj.common.utils.MapOfObject;
import com.sj.privilegemanagement.dao.MenuDao;
import com.sj.privilegemanagement.dao.MenuOfRoleDao;
import com.sj.privilegemanagement.dao.RoleDao;
import com.sj.privilegemanagement.dao.UserOfRoleDao;
import com.sj.privilegemanagement.entity.MenuOfRole;
import com.sj.privilegemanagement.entity.Role;
import com.sj.privilegemanagement.entity.UserOfRole;
import com.sj.privilegemanagement.manager.RoleManager;

public class RoleManagerImpl extends BaseManagerImpl<Role> implements RoleManager {

	private MenuOfRoleDao menuOfRoleDao;
	private MenuDao menuDao;
	private UserOfRoleDao userOfRoleDao;
	private FilterChainDefinitionsService shiroUtils;
	/**
	 * 设置权限（菜单）
	 */
	@Override
	public void setMenus() {
		String roleId = this.getRequest().getParameter("roleId");
		String[] menuIds = this.getRequest().getParameterValues("menuIds[]");
		if(menuIds != null)
		for(String menuId : menuIds){
			MenuOfRole menuOfRole = new MenuOfRole();
			menuOfRole.setCreateStamp(new Date());
			menuOfRole.setEnable(true);
			menuOfRole.setModifyStamp(new Date());
			menuOfRole.setMenuId(Long.valueOf(menuId));
			menuOfRole.setRoleId(Long.valueOf(roleId));
			menuOfRoleDao.save(menuOfRole);
		}
		shiroUtils.updatePermission();
	}
	/**
	 * 列表
	 */
	@Override
	public JSONArray findJsonList() {
		String page = this.getRequest().getParameter("page");
		String rows = this.getRequest().getParameter("rows");
		if(StringUtils.isBlank(page) || StringUtils.isBlank(rows)){
			page = "1";
			rows = "10";
		}
		List<Role> roles = this.findAll(Integer.valueOf(page), Integer.valueOf(rows),null);
		JSONArray jsonArray = new JSONArray();
		for(Role role : roles){
			Map map = new LinkedHashMap();
			Map mapT = MapOfObject.objectToMap(role);
			map.putAll(mapT);
			String hql = "from MenuOfRole m where m.roleId = :roleId";
			Map params = new LinkedHashMap();
			params.put("roleId", role.getId());
			List<MenuOfRole> menuOfRoles = menuOfRoleDao.findList(hql, params);
			StringBuffer stringBuffer = new StringBuffer();
			for(MenuOfRole menuOfRole : menuOfRoles){
				stringBuffer.append(menuDao.get(menuOfRole.getMenuId()).getName());
				stringBuffer.append(",");
			}
			if(stringBuffer.length()>0)
			map.put("menus", stringBuffer.substring(0, stringBuffer.length()-1).toString());
			jsonArray.add(map);
		}
		return jsonArray;
	}
	public void setMenuOfRoleDao(MenuOfRoleDao menuOfRoleDao) {
		this.menuOfRoleDao = menuOfRoleDao;
	}
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
	@Override
	public List<Role> findRolesByUserId(long userId) {
		Map<String,Object> params = new HashedMap();
		params.put("userId", userId);
		List<UserOfRole> userOfRoles = userOfRoleDao.findList("from UserOfRole ur where ur.userId = :userId", params);
		List<Role> roles = new ArrayList<Role>();
		for(UserOfRole userOfRole : userOfRoles){
			Role role = this.getRoleDao().get(userOfRole.getRoleId());
			if(role != null)
				roles.add(role);
		}
		return roles;
	}
	private RoleDao getRoleDao(){
		return (RoleDao)this.getBaseDao();
	}
	public void setUserOfRoleDao(UserOfRoleDao userOfRoleDao) {
		this.userOfRoleDao = userOfRoleDao;
	}
	@Override
	public List<Map<String, Object>> findall(int pageSize, int start) {
		return getRoleDao().findall(pageSize,start);
	}
	@Override
	public Long findcount(int pageSize, int start) {
		return getRoleDao().findcount(pageSize,start);
	}
	@Override
	public void removerMenusByRoleId() {
		String roleId = this.getRequest().getParameter("roleId");
		menuOfRoleDao.removerMenusByRoleId(roleId);
	}
	public void setShiroUtils(FilterChainDefinitionsService shiroUtils) {
		this.shiroUtils = shiroUtils;
	}
	@Override
	public List<UserOfRole> findbyuserid(long userid) {
		String hql = "from UserOfRole u where u.userId = :userId";
		Map params = new HashMap();
		params.put("userId", userid);
		return userOfRoleDao.findList(hql, params);
	}
	@Override
	public void setUser() {
		String roleId = this.getRequest().getParameter("roleId");
		String[] userIds = this.getRequest().getParameterValues("userIdList[]");
		for (String userId : userIds) {
		     UserOfRole userOfRole =new UserOfRole();
		     userOfRole.setCreateStamp(new Date());
		     userOfRole.setModifyStamp(new Date());
		     userOfRole.setEnable(true);
		     userOfRole.setRoleId(Long.valueOf(roleId));
		     userOfRole.setUserId(Long.valueOf(userId));
		     userOfRoleDao.save(userOfRole);
		}
		shiroUtils.updatePermission();
	}
	@Override
	public List<UserOfRole> findbyroleId(long roleId) {
		String hql = "from UserOfRole u where u.roleId = :roleId";
		Map params = new HashMap();
		params.put("roleId", roleId);
		return userOfRoleDao.findList(hql, params);
	}
	@Override
	public void removerUserByRoleId() {
		String roleId = this.getRequest().getParameter("roleId");
		menuOfRoleDao.removerUserByRoleId(roleId);
	}
	
}
