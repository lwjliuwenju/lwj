package com.sj.privilegemanagement.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.Proposer;
import com.sj.privilegemanagement.entity.StaffOfProposer;
import com.sj.privilegemanagement.entity.Supplies;
import com.sj.privilegemanagement.entity.SuppliesOfproposer;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.manager.DepManager;
import com.sj.privilegemanagement.manager.ProposerManager;
import com.sj.privilegemanagement.manager.StaffManager;
import com.sj.privilegemanagement.manager.StaffOfProposerManager;
import com.sj.privilegemanagement.manager.SuppliesManager;
import com.sj.privilegemanagement.manager.SuppliesProposerManager;

public class SuppliesproposerAction extends
		BaseAction<SuppliesProposerManager, SuppliesOfproposer> {
	private static final long serialVersionUID = 1L;

	private SuppliesProposerManager suppliesProposerManager;
	private SuppliesManager suppliesManager;
	private DepManager depManager;
	private ProposerManager proposerManager;
	private StaffManager staffManager;
	private StaffOfProposerManager staffOfProposerManager;
	
	public void setStaffManager(StaffManager staffManager) {
		this.staffManager = staffManager;
	}

	public void setStaffOfProposerManager(
			StaffOfProposerManager staffOfProposerManager) {
		this.staffOfProposerManager = staffOfProposerManager;
	}

	public void setProposerManager(ProposerManager proposerManager) {
		this.proposerManager = proposerManager;
	}

	public DepManager getDepManager() {
		return depManager;
	}

	public void setDepManager(DepManager depManager) {
		this.depManager = depManager;
	}

	public SuppliesManager getSuppliesManager() {
		return suppliesManager;
	}

	public void setSuppliesManager(SuppliesManager suppliesManager) {
		this.suppliesManager = suppliesManager;
	}

	public SuppliesProposerManager getSuppliesProposerManager() {
		return suppliesProposerManager;
	}

	public void setSuppliesProposerManager(
			SuppliesProposerManager suppliesProposerManager) {
		this.suppliesProposerManager = suppliesProposerManager;
	}

	/**
	 * 耗材响应
	 * 
	 * @return
	 */
	@Jurisdiction(name = "耗材响应单", level = -1)
	public String index() {
		setUrl("index");
		return SUCCESS;
	}

	/**
	 * 根据查询条件获取耗材响应内容 2017年5月26日8:57:32 HLH
	 */
	public void findByConditions() {
		int page = this.getIntegerParameter("page");
		int rows = this.getIntegerParameter("rows");
		String submitId = getRequest().getParameter("tijiaorenId");
		String reponsePerson = getRequest().getParameter("responseperson");
		String status = getRequest().getParameter("status");
		String dep = getRequest().getParameter("dep");
		String reponseDep = this.getParameter("reponseDep");
		String paixu = this.getParameter("paixu");
		Object obj = this.getSession().getAttribute("userInfo");
		User user = null;
		if (obj != null)
			user = (User) obj;
		if (user == null)
			AjaxUtils
					.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.NO_USER));
		List<DepEntity> list = null;
		if (StringUtils.isBlank(reponseDep)) {
			list = depManager.findByUserId(user.getId());
			if (list.size() == 1)
				reponseDep = list.get(0).getDepName();
		}
		if (rows == 0)
			rows = 10;
		List<SuppliesOfproposer> suppliesOfproposes = suppliesProposerManager
				.findbyParam(submitId, reponsePerson, status, dep, page, rows,
						reponseDep,paixu);
		int l = suppliesProposerManager.findbyParam(submitId, reponsePerson,
				status, dep, 0, 0, reponseDep,paixu).size();
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for(SuppliesOfproposer sp : suppliesOfproposes){
			JSONObject json = new JSONObject();
			Proposer proposer = proposerManager.findById(sp.getProposerId());
			Supplies supplies = suppliesManager.findById(sp.getSuppliesId());
			json.put("id", sp.getId());
			//申请人名称
			json.put("proName", proposer.getUserName());
			//执行人名称
			List<StaffOfProposer> staffs = staffOfProposerManager.findStaffByProId(proposer.getId());
			StringBuffer responseStaffBuffer = new StringBuffer();
			for (StaffOfProposer staff : staffs) {
				responseStaffBuffer.append(staffManager.findById(staff.getStaffId()).getName() + ",");
			}
			String responseStaff="";
			if(responseStaffBuffer.length()>0)
			 responseStaff = responseStaffBuffer.substring(0, responseStaffBuffer.length() - 1);
			json.put("resName", responseStaff);
			//申请部门名称
			json.put("proDepName", proposer.getDepName());
			//耗材名称
			json.put("suppliesName", supplies.getName());
			//耗材数量
			json.put("suppliesNum", sp.getSuppliesNum());
			//申请时间
			json.put("suppliesSendTime", sp.getCreateStamp());
			//申请状态
			json.put("suppliesProState", sp.getStatus() == 0 ? "未发放" : "已发放");
			jsonArray.add(json);
		}
		jsonObject.put("rows", jsonArray);
		jsonObject.put("total", l);
		AjaxUtils.ajaxResponse(jsonObject.toString());
	}
	/**
	 * 保存耗材
	 */
	public void saveSupplies() {
		suppliesProposerManager.saveSupplies();
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 重写发放耗材状态 2017年6月19日09:22:40 元冬冬
	 */
	public void updateStatus(){
		long suppliesproposerId = this.getIntegerParameter("id");
		SuppliesOfproposer suppliesOfproposer = suppliesProposerManager.findById(suppliesproposerId);
		suppliesOfproposer.setStatus(1);
		suppliesProposerManager.update(suppliesOfproposer);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
}