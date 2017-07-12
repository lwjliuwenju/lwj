package com.sj.privilegemanagement.manager;

import java.util.List;
import java.util.Map;

import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.User;

public interface UserManager extends BaseManager<User> {

	/**
	 * 根据用户名获取用户
	 * @param string
	 * @return
	 */
	public User findByUserName(String string);
	/**
	 * 获取管理密码
	 * @return
	 */
	public String getAdminPass();
	
	/**
	 * 设置用户对应的角色信息
	 * @param userId
	 * @param roleIds
	 */
	public void setUserOfRole(Long userId,List<Long> roleIds);

	
	/**
	 * 根据用户id 用户对应的角色信息
	 * @param userId
	 */
	public void removeRolesByUserId(Long userId);

	/**
	 * 根据用户（科长）id获取科室信息
	 * @param userId
	 * @return
	 */
	public List<DepEntity> getDepByUserId(Long userId);
	public List<User> findbyparam(Map<String, String> param);

}
