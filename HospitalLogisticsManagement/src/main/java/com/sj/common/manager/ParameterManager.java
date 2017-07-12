package com.sj.common.manager;

import com.sj.common.entity.Parameter;
import com.sj.privilegemanagement.manager.BaseManager;

public interface ParameterManager extends BaseManager<Parameter> {
	
	public void putParam(String key,String value,String name);
	
	public String getParam(String key);
}
