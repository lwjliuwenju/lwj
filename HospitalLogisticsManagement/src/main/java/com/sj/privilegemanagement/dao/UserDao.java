package com.sj.privilegemanagement.dao;

import java.util.List;
import java.util.Map;

import com.sj.common.dao.BaseDao;
import com.sj.privilegemanagement.entity.User;

public interface UserDao extends BaseDao<User> {

	/**
	 * 根据用户名查询用户列表
	 * @param userName
	 * @return
	 */
	User findByUserName(String userName);
}
