package com.sj.common.action;

import com.sj.common.entity.Parameter;
import com.sj.common.manager.ParameterManager;
import com.sj.common.manager.impl.CustomScheduledJob;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.action.base.BaseAction;

public class ParamAction extends BaseAction<ParameterManager, Parameter> {

	private CustomScheduledJob customScheduledJob;
	@Override
	@Jurisdiction(name = "参数管理",level = -1)
	public String index() {
		return super.index();
	}
	
	@Override
	public void update() throws Exception {
		super.update();
		customScheduledJob.resetScheduledJob();
	}
	public void setCustomScheduledJob(CustomScheduledJob customScheduledJob) {
		this.customScheduledJob = customScheduledJob;
	}
	
	
}
