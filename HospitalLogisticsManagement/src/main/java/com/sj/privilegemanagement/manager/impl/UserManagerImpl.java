package com.sj.privilegemanagement.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.sj.privilegemanagement.dao.DepDao;
import com.sj.privilegemanagement.dao.UserDao;
import com.sj.privilegemanagement.dao.UserOfDepDao;
import com.sj.privilegemanagement.dao.UserOfRoleDao;
import com.sj.privilegemanagement.entity.AdminPass;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.entity.UserOfRole;
import com.sj.privilegemanagement.manager.AdminPassManager;
import com.sj.privilegemanagement.manager.UserManager;

public class UserManagerImpl extends BaseManagerImpl<User> implements UserManager {
	
	private AdminPassManager adminPassManager;
	private DepDao depDao;
	private UserOfDepDao userOfDepDao;
	private UserOfRoleDao userOfRoleDao;
	@Override
	public User findByUserName(String userName) {
		return this.getUserDao().findByUserName(userName);
	}
	public void setUserOfDepDao(UserOfDepDao userOfDepDao) {
		this.userOfDepDao = userOfDepDao;
	}

	private UserDao getUserDao() {
		return (UserDao)this.getBaseDao();
	}

	public String getAdminPass(){
		Map<String, Object> params = new HashMap<String, Object>();
		List<AdminPass> adminPasses = adminPassManager.findAll("from AdminPass", params);
		return adminPasses.get(0).getPassword();
	}
	
	public void setAdminPassManager(AdminPassManager adminPassManager) {
		this.adminPassManager = adminPassManager;
	}

	@Override
	public void setUserOfRole(Long userId,List<Long> roleIds) {
		for(Long roleId : roleIds){
			UserOfRole  userOfRole = new UserOfRole();
			userOfRole.setCreateStamp(new Date());
			userOfRole.setModifyStamp(new Date());
			userOfRole.setEnable(true);
			userOfRole.setUserId(userId);
			userOfRole.setRoleId(roleId);
			userOfRoleDao.save(userOfRole);
		}
	}

	public void setUserOfRoleDao(UserOfRoleDao userOfRoleDao) {
		this.userOfRoleDao = userOfRoleDao;
	}

	@Override
	public void removeRolesByUserId(Long userId) {
		userOfRoleDao.removeRolesByUserId(userId);
	}

	@Override
	public List<DepEntity> getDepByUserId(Long userId) {
		return depDao.findDepByUserId(userId);
	}

	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}
	@Override
	public List<User> findbyparam(Map<String, String> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql ="SELECT u.* from hop_user u WHERE 1=1 and u.PASSWORD_!=''";
		/*工号*/
		String jobNumber = param.get("jobNumber");
		if(StringUtils.isNotBlank(jobNumber)){
			sql+=" and u.JOB_NUMBER_ LIKE:jobNumber";
			map.put("jobNumber", "%" + jobNumber + "%");
		}
		String userName  = param.get("userName");
		if(StringUtils.isNotBlank(userName )){
			sql += " and u.USER_NAME_ like :userName";
			map.put("userName", "%" + userName + "%");
		}
		String phone  = param.get("phone");
		if(StringUtils.isNotBlank(phone )){
			sql += " and u.NUMBER_ like :phone";
			map.put("phone", "%" + phone + "%");
		}
		String page = param.get("page");
		String rows = param.get("rows");
		List<User> maps = null;
		if(StringUtils.isBlank(rows) || StringUtils.isBlank(page)){
			page = "0";
			rows = "0";
		}
		maps = getUserDao().findListBeanBySql(User.class,sql, map, Integer.valueOf(page.toString()), Integer.valueOf(rows.toString()));
		return maps;
	}

}
