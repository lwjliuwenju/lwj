package com.sj.privilegemanagement.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sj.privilegemanagement.dao.DepDao;
import com.sj.privilegemanagement.dao.SuppliesDao;
import com.sj.privilegemanagement.dao.SuppliesProposerDao;
import com.sj.privilegemanagement.entity.Supplies;
import com.sj.privilegemanagement.entity.SuppliesOfproposer;
import com.sj.privilegemanagement.manager.SuppliesManager;

public class SuppliesManagerImpl extends BaseManagerImpl<Supplies> implements SuppliesManager {
	private SuppliesDao suppliesDao;
	private DepDao depDao;
	private SuppliesProposerDao suppliesProposerDao;
	
	public void setSuppliesProposerDao(SuppliesProposerDao suppliesProposerDao) {
		this.suppliesProposerDao = suppliesProposerDao;
	}

	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}

	public void setSuppliesDao(SuppliesDao suppliesDao) {
	this.suppliesDao = suppliesDao;
}

	@Override
	public List<Supplies> findByDepId(String depId) {
		String hql = "from Supplies s where s.depId = :depId";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("depId", depId);
		return findAll(hql, map);
	}
	
	@Override
	public List<Map<String, Object>> findByProId(long pid){
		String hql = "from SuppliesOfproposer sp where sp.proposerId = :proposerId";
		Map map = new HashMap();
		map.put("proposerId", pid);
		List<SuppliesOfproposer> suppliesOfproposers  = suppliesProposerDao.findList(hql, map);
		List<Map<String,Object>> suppliesJSONArray = new ArrayList();
		if(suppliesOfproposers!=null && suppliesOfproposers.size()>0){
			for (SuppliesOfproposer suppliesOfproposer : suppliesOfproposers) {
				Map<String,Object> jsonObject = new HashMap();
				jsonObject.put("supplieNum", suppliesOfproposer.getSuppliesNum());
				Supplies supplies = suppliesDao.get(suppliesOfproposer.getSuppliesId());
				//耗材名称
				jsonObject.put("supplieName", supplies.getName());
				jsonObject.put("supplieValue", supplies.getValue());
				//部门名称
				String depName = depDao.get(supplies.getDepId()).getDepName();
				jsonObject.put("depName", depName);
				jsonObject.put("mark", suppliesOfproposer.getStatus() == 1 ? "已发放" : "未发放");
				suppliesJSONArray.add(jsonObject);
			}
		}
		return suppliesJSONArray;
	}

}
