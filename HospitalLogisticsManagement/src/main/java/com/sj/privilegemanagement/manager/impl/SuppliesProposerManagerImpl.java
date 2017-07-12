package com.sj.privilegemanagement.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sj.privilegemanagement.dao.DepDao;
import com.sj.privilegemanagement.dao.SuppliesDao;
import com.sj.privilegemanagement.dao.SuppliesProposerDao;
import com.sj.privilegemanagement.entity.Supplies;
import com.sj.privilegemanagement.entity.SuppliesOfproposer;
import com.sj.privilegemanagement.manager.DepManager;
import com.sj.privilegemanagement.manager.SuppliesProposerManager;
import com.sj.privilegemanagement.websocket.DwrTest;

public class SuppliesProposerManagerImpl extends BaseManagerImpl<SuppliesOfproposer> implements SuppliesProposerManager {
	private SuppliesProposerDao suppliesProposerDao;
	private SuppliesDao suppliesDao; 
	private DepDao depDao;
	private DepManager depManager;
	
	public void setDepManager(DepManager depManager) {
		this.depManager = depManager;
	}
	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}
	public SuppliesProposerDao getSuppliesProposerDao() {
		return suppliesProposerDao;
	}
	public void setSuppliesProposerDao(SuppliesProposerDao suppliesProposerDao) {
		this.suppliesProposerDao = suppliesProposerDao;
	}


	public SuppliesDao getSuppliesDao() {
		return suppliesDao;
	}


	public void setSuppliesDao(SuppliesDao suppliesDao) {
		this.suppliesDao = suppliesDao;
	}



	@Override
	public void saveSupplies() {
		String proposerId = this.getRequest().getParameter("proposerId");
		String[] supplies = this.getRequest().getParameterValues("supplies[]");
		for (String supplie : supplies) {
			SuppliesOfproposer suppliesOfproposer =  new SuppliesOfproposer();
			suppliesOfproposer.setCreateStamp(new Date());
			suppliesOfproposer.setStatus(0);
			suppliesOfproposer.setProposerId(Long.valueOf(proposerId));
			String[] split = supplie.split(":");
			if(split != null && split.length > 0){
				suppliesOfproposer.setSuppliesId(Long.valueOf(split[0]));
			}
			if(split != null && split.length > 1){
				suppliesOfproposer.setSuppliesNum(Integer.valueOf(split[1]));
			}
			suppliesProposerDao.save(suppliesOfproposer); 
			Supplies supplies2 = suppliesDao.get(Long.valueOf(split[0]));
			List<Long> userIds = depManager.findUserIdByDepId(supplies2.getDepId());
			for (Long userId : userIds) {
			DwrTest.sendMessageAuto(String.valueOf(userId), 
					"新的耗材信息:" + supplies2.getName() + "--->" + split[1]);
			}
		}
		
		
	}

	@Override
	public List<Supplies> findbypid(long pid) {
			String hql = "select s from Supplies s,SuppliesOfproposer sp where sp.suppliesId = s.id and sp.proposerId = :proposerId";
			Map map = new HashMap();
			map.put("proposerId", pid);
			List<Supplies> supplies  = suppliesDao.findList(hql, map);
			return supplies;
	}
	@Override
	public List<SuppliesOfproposer> findbyParam(String submitName,
			String reponsePerson, String status, String dep, int page,
			int rows, String reponseDep,String paixu) {
		String hql = "select sp "
				+ "from SuppliesOfproposer sp,Supplies s,DepEntity d,Proposer p "
				+ "where sp.suppliesId = s.id and s.depId = d.id "
				+ "and sp.proposerId = p.id";
		if(StringUtils.isNotBlank(reponseDep))
			hql += " and d.depName like '%" + reponseDep + "%'";
		if(StringUtils.isNotBlank(submitName))
			hql += " and p.userName like '%" + submitName + "%'";
		if(StringUtils.isNotBlank(reponsePerson))
			hql += " and p.responseUserName like '%" + reponsePerson + "%'";
		if(StringUtils.isNotBlank(status)&&!"-1".equals(status))
			hql += " and sp.status = " + status;
		if(StringUtils.isNotBlank(dep))
			hql += " and p.depName like '%" + dep + "%'";
		if(StringUtils.isNotBlank(paixu)&&"0".equals(paixu))
			hql += " order by sp.status";
		if(StringUtils.isNotBlank(paixu)&&"1".equals(paixu))
			hql +=" order by p.createStamp desc";
		return this.findAll(page, rows, hql);
	}

}
