package com.sj.privilegemanagement.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;

import com.mysql.fabric.xmlrpc.base.Params;
import com.sj.common.dao.impl.BaseDaoImpl;
import com.sj.privilegemanagement.dao.UserDao;
import com.sj.privilegemanagement.entity.User;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User findByUserName(String userName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		try {
			List<User> users =  this.findList("from User where jobNumber = :userName", params);
			if(users != null && users.size() > 0)
				return users.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
