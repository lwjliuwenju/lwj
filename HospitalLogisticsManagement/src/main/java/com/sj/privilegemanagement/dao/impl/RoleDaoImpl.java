package com.sj.privilegemanagement.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;

import com.sj.common.dao.impl.BaseDaoImpl;
import com.sj.privilegemanagement.dao.RoleDao;
import com.sj.privilegemanagement.entity.Role;

public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	@Override
	public List<Map<String, Object>> findall(int pageSize, int start) {
		String sql ="SELECT r.* FROM hop_role r ";
		 Session session = this.getCurrentSession();
			Query query = session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
			List<Map<String, Object>> list = query.list();
			if(list!=null && list.size()>0){
				return list;
			}
			return null;
	}

	@Override
	public Long findcount(int pageSize, int start) {
		Session session = this.currentSession();
		String sql ="SELECT COUNT(1) from hop_role";
		Query query = session.createSQLQuery(sql);
		BigInteger bigInteger = (BigInteger) query.uniqueResult();
		Long l = bigInteger.longValue();
		return l;
	}


}
