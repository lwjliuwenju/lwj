package com.sj.privilegemanagement.manager.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;

import com.sj.privilegemanagement.dao.DepDao;
import com.sj.privilegemanagement.dao.ProjectDao;
import com.sj.privilegemanagement.dao.SuppliesProposerDao;
import com.sj.privilegemanagement.entity.ProjectEntity;
import com.sj.privilegemanagement.manager.ProjectManager;

public class ProjectManagerImpl extends BaseManagerImpl<ProjectEntity>implements ProjectManager {
	private ProjectDao projectDao;
	private DepDao depDao;
	private SuppliesProposerDao suppliesProposerDao;
	public SuppliesProposerDao getSuppliesProposerDao() {
		return suppliesProposerDao;
	}
	public void setSuppliesProposerDao(SuppliesProposerDao suppliesProposerDao) {
		this.suppliesProposerDao = suppliesProposerDao;
	}
	public DepDao getDepDao() {
		return depDao;
	}
	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}
	public ProjectDao getProjectDao() {
		return projectDao;
	}
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	@Override
	public List<ProjectEntity> findByDepId(long Id) {
		return findByDepId(Id, 0, 0);
	}
	@Override
	public List<ProjectEntity> findByDepId(long Id, int page, int rows) {
		String hql = "from ProjectEntity p where 1 = 1 and p.enable=1";
		Map<String,Object> map = new HashMap();
		if(Id != -1){
			hql += " and p.responseDeptId = :responseDeptId";
			map.put("responseDeptId", Id);
		}
		return projectDao.findList(hql, map, page, rows);
	}

	@Override
	public void editdengji(String id) {
		ProjectEntity projectEntity = projectDao.get(Long.valueOf(id));
		projectEntity.setStartFlag(1);
		projectDao.update(projectEntity);
	}
	@Override
	public List<ProjectEntity> findbyproName(String projectName) {
		String hql = "from ProjectEntity p where 1 = 1 and p.enable=1 and p.projectName=:projectName";
		Map<String,Object> map = new HashMap();
		map.put("projectName", projectName);
		return projectDao.findList(hql, map);
	}
	@Override
	public List<Map<String, Object>> findbyfatherId(long depId, String fatherid) {
		String sql ="SELECT p.* FROM hop_project p WHERE p.FATHER_ID_= '"+fatherid+"' and p.ENABLE_=1";
		if(depId!=-1){
			sql+=" and p.RESPONSE_DEPT_ID_='"+depId+"'";
		}
		Session session = this.getSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
	    List<Map<String, Object>> list = query.list();
		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}

}
