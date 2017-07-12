package com.sj.common.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import com.sj.common.dao.ParameterDao;
import com.sj.common.dao.impl.ParameterDaoImpl;
import com.sj.common.entity.Parameter;
import com.sj.common.manager.ParameterManager;
import com.sj.privilegemanagement.manager.impl.BaseManagerImpl;

public class ParameterManagerImpl extends BaseManagerImpl<Parameter> implements ParameterManager {

	@Override
	public void putParam(String key, String value,String name) {
		Parameter parameter = new Parameter(name,key,value);
		if(StringUtils.isBlank(this.getParam(key)))
		try {
			parameter.setEnable(true);
			parameter.setCreateStamp(new Date());
			getBaseDao().save(parameter);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("该参数已存在！");
		}
	}
	@Override
	public String getParam(String key) {
		Parameter parameter = getParameter(key);
		if(parameter != null)
			return parameter.getParVal();
		return null;
	}
	private Parameter getParameter(String key){
		String hql = "from Parameter p where p.parMark = :parMark";
		Map<String,Object> map = new HashedMap();
		map.put("parMark", key);
		List<Parameter> pars = getBaseDao().findList(hql,map);
		if(pars != null && pars.size() > 0)
			return pars.get(0);
		return null;
	}
}
