package com.sj.privilegemanagement.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.JSONUtils;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.Proposer;
import com.sj.privilegemanagement.entity.Staff;
import com.sj.privilegemanagement.entity.StaffOfProposer;
import com.sj.privilegemanagement.entity.Supplies;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.manager.DepManager;
import com.sj.privilegemanagement.manager.ProposerManager;
import com.sj.privilegemanagement.manager.StaffManager;
import com.sj.privilegemanagement.manager.StaffOfProposerManager;
import com.sj.privilegemanagement.manager.SuppliesProposerManager;
import com.sj.privilegemanagement.websocket.DwrTest;

public class ReponseAction extends BaseAction<ProposerManager, Proposer> {

	private static final long serialVersionUID = 1L;

	private DepManager depManager;
	private ProposerManager proposerManager;
	private SuppliesProposerManager suppliesProposerManager;
	private StaffOfProposerManager staffOfProposerManager;
	private StaffManager staffManager;
	public void setStaffManager(StaffManager staffManager) {
		this.staffManager = staffManager;
	}

	public void setStaffOfProposerManager(
			StaffOfProposerManager staffOfProposerManager) {
		this.staffOfProposerManager = staffOfProposerManager;
	}

	public SuppliesProposerManager getSuppliesProposerManager() {
		return suppliesProposerManager;
	}

	public void setSuppliesProposerManager(
			SuppliesProposerManager suppliesProposerManager) {
		this.suppliesProposerManager = suppliesProposerManager;
	}

	public ProposerManager getProposerManager() {
		return proposerManager;
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

	/**
	 * 跳转响应页面
	 */
	@Jurisdiction(name = "响应单管理",level = -1)
	public String responsedepartment() {
		setUrl("index");
		return SUCCESS;
	}

	/**
	 * 跳转终止原因 2017年5月9日14:57:31 元冬冬
	 */
	@Jurisdiction(name = "终止")
	public String breakform() {
		setUrl("breakform");
		return SUCCESS;
	}

	/**
	 * 跳转审核页面 元冬冬 2017年5月18日17:04:05
	 */
	public String shenheform() {
		setUrl("shenheform");
		return SUCCESS;
	}

	/**
	 * 跳转派遣人员 2017年5月10日11:18:35 元冬冬
	 * 
	 * @return
	 */
	public String sendrenyuan() {
		setUrl("sendrenyuan");
		return SUCCESS;
	}
	/**
	 * 派遣人员 2017年5月10日17:26:32 元冬冬
	 */
	public void saverenyuan() {
		// 申请单ID
		long proId = this.getIntegerParameter("proId");
		// 派遣人员ID
		String[] userIds = getRequest().getParameterValues("userId[]");
		String flag = getRequest().getParameter("flag");
		String repairReason = this.getParameter("repairReason");
		Proposer proposer = proposerManager.findById(proId);
		List<StaffOfProposer> staffofproposers = staffOfProposerManager.findStaffByProId(proId);
		proposer.setResponseTimes(proposer.getResponseTimes() + 1);
		if(staffofproposers != null && staffofproposers.size() > 0){
			 for (StaffOfProposer staffOfProposer : staffofproposers) {
					staffOfProposer.setComplete(0);
					staffOfProposerManager.update(staffOfProposer);
					Staff staff = staffManager.findById(staffOfProposer.getStaffId());
					staff.setStatus(staff.getStatus() - 1);
					staffManager.update(staff);
			 }
		}else{
			proposer.setModifyStamp(new Date());
			proposer.setStatus(3);
			proposer.setResponseStartTime(new Date());
			proposerManager.update(proposer);
		}
		if(userIds==null && flag != null && "1".equals(flag)){
			proposer.setOutSourcIngFlag(flag);
			proposer.setRepairReason(repairReason);
		}
		else for (String userId: userIds) {
			 StaffOfProposer staffOfProposerT = staffOfProposerManager.isexist(userId,proId);
			if(staffOfProposerT == null){
				StaffOfProposer staffOfProposer = new StaffOfProposer();
				staffOfProposer.setEnable(true);
				staffOfProposer.setCreateStamp(new Date());
				staffOfProposer.setModifyStamp(new Date());
				staffOfProposer.setProposerId(proId);
				staffOfProposer.setStaffId(Long.valueOf(userId));
				staffOfProposer.setComplete(-1);
				staffOfProposerManager.insert(staffOfProposer);
				Staff staff = staffManager.findById(Long.valueOf(userId));
				staff.setNumber(staff.getNumber()+1);
				staff.setStatus(staff.getStatus() + 1);
				staffManager.update(staff);
			}else{
					staffOfProposerT.setComplete(-1);
					staffOfProposerManager.update(staffOfProposerT);
					Staff staff = staffManager.findById(Long.valueOf(userId));
					staff.setStatus(staff.getStatus() + 1);
					staffManager.update(staff);
			}
		}
		proposerManager.update(proposer);
		DwrTest.sendMessageAuto(proposer.getUserId().toString(), "您有一条申请单已派人！");
		DwrTest.sendMessageAuto("0", "您有一条申请单已派人！");
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));

	}
	/**
	 * 获取科室所属用户 2017年5月10日14:23:20 元冬冬
	 */
	public void getdepartment() {
		String userid = getRequest().getParameter("userid");
		long id = Long.parseLong(userid);
		long depid = 0;
		List<DepEntity> list = depManager.findByUserId(id);
		List<Staff> staffs = new ArrayList<Staff>();
		for (DepEntity depEntity : list) {
			staffs.addAll(staffManager.findStaffByDepId(depid));
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rows", JSONUtils.toJSONString(staffs));
		jsonObject.put("total", staffs.size());
		AjaxUtils.ajaxResponse(jsonObject.toString());
	}
    /**
     * 审核功能 2017年6月15日09:44:32 元冬冬
     */
	public void complete(){
		long proId = this.getIntegerParameter("pid");
	    int checkStatus = this.getIntegerParameter("checkStatus");
	    String content = this.getParameter("content");
	    Proposer proposer = proposerManager.findById(proId);
	    proposer.setCheckflag(checkStatus);
	    proposer.setCheckContent(content);
	    proposer.setModifyStamp(new Date());
	    proposerManager.update(proposer);
	    
	    AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 挂起
	 */
	public void gq() {
		Integer proId = this.getIntegerParameter("proId");
		if(proId.equals(0)){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.REQUEST_PARAMETER_LACK));
			return;
		}
		Proposer proposer = proposerManager.findById(proId.longValue());
		if(proposer.getGqbj() == 1){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.NO_DATA));
			return;
		}
		proposer.setGqbj(1);
		proposerManager.update(proposer);
		List<StaffOfProposer> StaffOfProposers = staffOfProposerManager.findStaffByProId(proId);
		for (StaffOfProposer staffOfProposer : StaffOfProposers) {
			Staff staff = staffManager.findById(staffOfProposer.getStaffId());
			staff.setStatus(staff.getStatus()-1);
			staffManager.update(staff);
		}
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 取消挂起
	 */
	public void qxgq() {
		Integer proId = this.getIntegerParameter("proId");
		if(proId.equals(0)){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.REQUEST_PARAMETER_LACK));
			return;
		}
		Proposer proposer = proposerManager.findById(proId.longValue());
		if(proposer.getGqbj() == 0){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.NO_DATA));
			return;
		}
		proposer.setGqbj(0);
		proposerManager.update(proposer);
		List<StaffOfProposer> StaffOfProposers = staffOfProposerManager.findStaffByProId(proId);
		for (StaffOfProposer staffOfProposer : StaffOfProposers) {
			Staff staff = staffManager.findById(staffOfProposer.getStaffId());
			staff.setStatus(staff.getStatus()+1);
			staffManager.update(staff);
		}
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 终止操作 2017年5月9日15:39:05 元冬冬
	 * 
	 * @throws Exception
	 */
	public void breakforms() throws Exception {
		Object userT = this.getSession().getAttribute("userInfo");
		if(userT == null){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.NO_USER));
			return;
		}
		User user = (User)userT;
		String breakReason = getRequest().getParameter("breakReason");
		long proId = this.getIntegerParameter("proId");
		Proposer proposer = proposerManager.findById(proId);
		proposer.setStatus(5);
		proposer.setModifyStamp(new Date());
		proposer.setTerminationId(user.getId());
		proposer.setTerminationReasonUserName(user.getUserName());
		proposer.setTerminationReason(breakReason);
		proposer.setResponseEndTime(new Date());
		proposer.setModifyStamp(new Date());
		proposerManager.update(proposer);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 重写返修功能 2017年6月13日16:01:49 元冬冬
	 */
	public void fanxiu(){
		long proId = this.getIntegerParameter("proId");
	    Proposer proposer = proposerManager.findById(proId);
	    proposer.setRepairFlag(1);
	    proposerManager.update(proposer);
	    AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 根据查询条件获取列表 2017年5月10日10:02:46 元冬冬
	 */

	public void findAll() {
		User user = (User) getRequest().getSession().getAttribute("userInfo");
		if (user == null) {
			AjaxUtils.ajaxResponseAlert(AjaxUtils
					.getErrorMessage(AjaxUtils.NO_BAND_CARD));
			return;
		}
		Long userId = user.getId();
		Integer page = this.getIntegerParameter("page");
		Integer rows = this.getIntegerParameter("rows");
		int intPage = (page.equals(0) ? 1 : page);
		// 每页显示条数
		int pageSize = (rows.equals(0) ? 10 : rows);
		// 每页的开始记录 第一页为1 第二页为number +1
		List<Map<String, Object>> alist = new ArrayList();
		Date startTime = this.getEasyUiDateParameter("startTime");
		Date endTime = this.getEasyUiDateParameter("endTime");
		alist = proposerManager.searchbypage(pageSize, intPage,startTime,endTime);
		int total = proposerManager.searchbypage(0, 0,startTime,endTime).size();
		JSONArray jsonArray = new JSONArray();
		if (alist.size() > 0 && alist != null)
			for (Map<String, Object> map : alist) {
				JSONObject proposeJSON = JSONObject.fromObject(map);
				if(map.get("GQBJ_").toString().equals("1"))
					proposeJSON.put("STATUS_", 7);
				long pid = Long.valueOf(map.get("ID_").toString());
				List<Supplies> supplies = suppliesProposerManager
						.findbypid(pid);
				if (supplies != null && supplies.size() > 0) {
					JSONArray suppliesJSONArray = new JSONArray();
					for (Supplies supplie : supplies) {
						JSONObject jsonObject = JSONObject.fromObject(supplie);
						jsonObject.put("depName",
								depManager.findById(supplie.getDepId()));
						suppliesJSONArray.add(jsonObject);
					}
					proposeJSON.put("supplies", suppliesJSONArray);
				}
				List<StaffOfProposer> staffofproposer = staffOfProposerManager.findStaffAllByProId(pid);
				StringBuffer sb =new StringBuffer();
				StringBuffer staffIds = new StringBuffer();
				for (StaffOfProposer staffOfProposers : staffofproposer) {
					 Staff staff = staffManager.findById(staffOfProposers.getStaffId());
					 staffIds.append("," + staff.getId());
					 sb.append("," + staff.getName());
				}
				if(sb.length() < 1)
					sb.append(",");
				proposeJSON.put("staffNames", sb.substring(1));
				proposeJSON.put("staffIds", staffIds.toString());
				jsonArray.add(proposeJSON);
			}
		JSONObject data = new JSONObject();
		data.put("rows", jsonArray);
		data.put("total", total);
		AjaxUtils.ajaxResponse(data.toString());
	}
}
