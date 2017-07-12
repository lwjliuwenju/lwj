package com.sj.common.utils;

import org.apache.commons.lang.StringUtils;

import com.sj.common.manager.ParameterManager;

public class GetParameret {

	private static ParameterManager parameterManager;

	public void setParameterManager(ParameterManager parameterManager) {
		this.parameterManager = parameterManager;
	}
	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static String getParameretByKey(String key){
		return parameterManager.getParam(key);
	}
	public static int getIntParameretByKey(String key){
		String val = getParameretByKey(key);
		if(StringUtils.isNotBlank(val))
			return Integer.valueOf(val);
		return -99999;
	}
	
	
}
