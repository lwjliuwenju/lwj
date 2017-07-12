package com.sj.common.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.sj.common.dao.ParameterDao;
import com.sj.common.entity.Parameter;
import com.sj.common.gui.IsKey;
import com.sj.common.manager.ParameterManager;
import com.sj.login.UserLoginAction;

public class InitPro implements InitializingBean  {

	private static Logger logger = LoggerFactory.getLogger(UserLoginAction.class);
	
	private ParameterManager parameterManager;
	
	public static boolean key = false;
	@Override
	public void afterPropertiesSet() throws Exception {
		key = IsKey.idKey();
		//参数： 键 值 名称
		//parameterDao.putParam("key", "val", "name");
		parameterManager.putParam("key", "val", "name");
		parameterManager.putParam("com.standard.time", "0/2 * * * * ?", "标准化刷新时间");
		parameterManager.putParam("com.show.user", "true", "显示和不显示用户");
	}
	public void setParameterManager(ParameterManager parameterManager) {
		this.parameterManager = parameterManager;
	}
}
