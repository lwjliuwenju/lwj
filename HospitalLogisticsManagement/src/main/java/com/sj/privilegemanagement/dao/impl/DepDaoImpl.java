package com.sj.privilegemanagement.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.sj.common.dao.impl.BaseDaoImpl;
import com.sj.privilegemanagement.dao.DepDao;
import com.sj.privilegemanagement.entity.DepEntity;

public class DepDaoImpl extends BaseDaoImpl<DepEntity>implements DepDao{

	@Override
	public List<DepEntity> findDepByUserId(Long userId) {
		String hql = "select d from DepEntity d,UserOfDepAdmin ud where ud.depId = d.id and ud.userId = :userId"
				+ " and d.enable = true";
		if (userId == null || userId.equals(0l))
			return this.findAll();
		Map map = new HashMap();
		map.put("userId", userId);
		List<DepEntity> depEntitys = this.findList(hql, map);
		return depEntitys;
	}

	@Override
	public List<Map<String,Object>> getWorkNumberByRYId(Long ryId, Date startTime, Date endTime) {
		Map params = new HashedMap();
		String sql = "SELECT h.CHECKFALG_,h.APPRAISE_,h.REPAIR_FLAG_,pro.GRADE_,o.COMPLETE_ "
				+ "FROM hop_proposer h LEFT JOIN hop_staff_of_proposer o ON h.ID_=o.PROPOSER_ID_ LEFT JOIN HOP_PROJECT pro ON pro.ID_=h.PROJECT_ID_,hop_staff s "
				+ "WHERE s.ID_=o.STAFF_ID_ "
				+ "AND o.STAFF_ID_ = :ryId "
				+ "AND h.CREATE_STAMP_ > :startTime "
				+ "AND h.CREATE_STAMP_ < :endTime";
		params.put("ryId", ryId);
		params.put("startTime", startTime == null ? new Date(0l) : startTime);
		params.put("endTime", endTime == null ? new Date() : endTime);
		List<Map<String,Object>> maps = this.findListBySql(sql, params);
		return maps;
	}

	@Override
	public List<DepEntity> findByTransport() {
		String hql = "from DepEntity d where d.transport = 1";
		List<DepEntity> depEntities = this.findList(hql);
		return depEntities;
	}
}
