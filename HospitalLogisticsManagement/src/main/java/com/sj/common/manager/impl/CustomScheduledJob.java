package com.sj.common.manager.impl;

import java.text.ParseException;

import org.quartz.SchedulerException;

import com.sj.common.utils.GetParameret;
import com.sj.privilegemanagement.action.ProposerAction;


public class CustomScheduledJob extends AbstractScheduledJob {

	private ProposerAction proposerAction;
	
    public void setProposerAction(ProposerAction proposerAction) {
		this.proposerAction = proposerAction;
	}
	@Override
    public void doScheduledJob() {
//    	proposerAction.findByStandard();
//    	System.err.print("\n\n\n 定时器 、\n\n\n");
    }
	public void resetScheduledJob()	throws SchedulerException, ParseException {
		String key = GetParameret.getParameretByKey("com.standard.time");
		super.resetScheduledJob(GetParameret.getParameretByKey("com.standard.time"));
	}

}
