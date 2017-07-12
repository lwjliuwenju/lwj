package com.sj.privilegemanagement.dao.impl;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.sj.common.dao.impl.BaseDaoImpl;
import com.sj.privilegemanagement.dao.UserOfDepDao;
import com.sj.privilegemanagement.entity.UserOfDepAdmin;

public class UserOfDepDaoImpl extends BaseDaoImpl<UserOfDepAdmin> implements UserOfDepDao {

	@Override
	public void deleteByDepId(String id) {
//		String hql = "delete from UserOfDepAdmin ud where ud.depId = :depId";
//		Map<String,Object> map = new HashedMap();
//		map.put("depId", id);
		ArrayList arrayList = new ArrayList();
		arrayList.add(Long.valueOf(id));
		this.deleteByColumns("depId", arrayList);
	}

}
