package com.sj.privilegemanagement.manager.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.sj.privilegemanagement.entity.StaffOfProposer;
import com.sj.privilegemanagement.manager.StaffOfProposerManager;

public class StaffOfProposerManagerImpl extends BaseManagerImpl<StaffOfProposer> implements StaffOfProposerManager {

	@Override
	public List<StaffOfProposer> findStaffByProId(long pid) {
		String hql = "from StaffOfProposer sp where sp.ProposerId = :proposerId and sp.complete !=0";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("proposerId", pid);
		return this.findAll(hql, map);
	}
	
	@Override
	public List<StaffOfProposer> findStaffAllByProId(long pid) {
		String hql = "from StaffOfProposer sp where sp.ProposerId = :proposerId and sp.complete !=0";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("proposerId", pid);
		return this.findAll(hql, map);
	}

	@Override
	public StaffOfProposer isexist(String userId, long proId) {
		String sql ="SELECT s.* FROM hop_staff_of_proposer s where s.PROPOSER_ID_='"+proId+"' AND s.STAFF_ID_='"+userId+"'";
		List<StaffOfProposer> StaffOfProposers = getBaseDao().findListBeanBySql(StaffOfProposer.class, sql, null, 0, 0);
		if(StaffOfProposers != null && StaffOfProposers.size() > 0){
			return StaffOfProposers.get(0);
		}
		return null;
	}

	@Override
	public long findbystaffId(Long staffId) {
		String sql ="SELECT COUNT(1) from 	hop_staff_of_proposer s WHERE s.STAFF_ID_='"+staffId+"' AND s.COMPLETE_=-1";
		Session session = this.getBaseDao().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		BigInteger i = (BigInteger) query.uniqueResult();
		long l =i.longValue();
		return l;
	}

	@Override
	public List<StaffOfProposer> findbystaffid(long id) {
		String hql = "from StaffOfProposer sp where sp.staffId = :staffId";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("staffId", id);
		return this.findAll(hql, map);
	}
}
