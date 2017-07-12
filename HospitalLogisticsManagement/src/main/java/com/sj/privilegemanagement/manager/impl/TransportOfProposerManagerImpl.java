package com.sj.privilegemanagement.manager.impl;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.sj.privilegemanagement.dao.TransportOfProposerDao;
import com.sj.privilegemanagement.entity.TransportOfProposer;
import com.sj.privilegemanagement.manager.TransportOfProposerManager;

public class TransportOfProposerManagerImpl extends BaseManagerImpl<TransportOfProposer> implements
		TransportOfProposerManager {
	  private TransportOfProposerDao transportOfProposerDao;

	public void setTransportOfProposerDao(
			TransportOfProposerDao transportOfProposerDao) {
		this.transportOfProposerDao = transportOfProposerDao;
	}

	@Override
	public List<TransportOfProposer> findbyProId(Long proId) {
		String hql = "from TransportOfProposer tp where tp.ProposerId = :proposerId and tp.complete !=0";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("proposerId", proId);
		return this.findAll(hql, map);
	}

	@Override
	public long findbystaffId(Long staffId) {
		String sql ="SELECT COUNT(1) from 	hop_transport_of_proposer s WHERE s.STAFF_ID_='"+staffId+"' AND s.COMPLETE_=-1";
		Session session = this.getBaseDao().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		BigInteger i = (BigInteger) query.uniqueResult();
		long l =i.longValue();
		return l;
	}
	  
}
