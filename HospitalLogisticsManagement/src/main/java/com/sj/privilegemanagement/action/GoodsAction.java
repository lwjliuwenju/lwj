package com.sj.privilegemanagement.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.DateUtil;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.common.utils.enums.DateStyle;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.GoodsEntity;
import com.sj.privilegemanagement.entity.Proposer;
import com.sj.privilegemanagement.entity.Staff;
import com.sj.privilegemanagement.entity.StaffOfProposer;
import com.sj.privilegemanagement.entity.TransportOfProposer;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.manager.DepManager;
import com.sj.privilegemanagement.manager.GoodsManager;
import com.sj.privilegemanagement.manager.ProposerManager;
import com.sj.privilegemanagement.manager.StaffManager;
import com.sj.privilegemanagement.manager.StaffOfProposerManager;
import com.sj.privilegemanagement.manager.TransportOfProposerManager;

public class GoodsAction extends BaseAction<GoodsManager,GoodsEntity>{
	private static final long serialVersionUID = 1L;
	private GoodsManager goodsManager;
	private ProposerManager proposerManager;
	private DepManager depManager;
	private StaffOfProposerManager staffOfProposerManager;
	private StaffManager staffManager;
	private TransportOfProposerManager transportOfProposerManager;
	
	
	public void setTransportOfProposerManager(
			TransportOfProposerManager transportOfProposerManager) {
		this.transportOfProposerManager = transportOfProposerManager;
	}
	public void setStaffOfProposerManager(
			StaffOfProposerManager staffOfProposerManager) {
		this.staffOfProposerManager = staffOfProposerManager;
	}
	public void setStaffManager(StaffManager staffManager) {
		this.staffManager = staffManager;
	}
	public void setDepManager(DepManager depManager) {
		this.depManager = depManager;
	}
	public void setProposerManager(ProposerManager proposerManager) {
		this.proposerManager = proposerManager;
	}
	public void setGoodsManager(GoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}
	/**
	 * 运输部门
	 * 
	 * @return
	 */
	@Jurisdiction(name = "运输部门", level = -1)
	public String transportationDep() {
		setUrl("index");
		return SUCCESS;
	}
	
	/**
	 * 物流查询
	 */
	public void findAll(){
		int page = this.getIntegerParameter("page");
		int rows = this.getIntegerParameter("rows");
		long reponseDepId = this.getIntegerParameter("id");
		List<Proposer> proposers = proposerManager.findbyreponseDepId(reponseDepId);
		JSONArray jsonArray= new JSONArray();
		for (Proposer proposer : proposers) {
			if(proposer.getGoodsteamId()!=null){
			JSONObject jsonObject = new JSONObject();
             jsonObject.put("pickUser", proposer.getPickUser());
             jsonObject.put("pickPhone", proposer.getPickPhone());
             jsonObject.put("goodsName", proposer.getGoodsName());
             jsonObject.put("transportName", depManager.findById(proposer.getGoodsteamId()).getDepName());
             jsonObject.put("id", proposer.getId());
			jsonArray.add(jsonObject);
			}
			}
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("rows", jsonArray);
		jsonobject.put("total", jsonArray.size());
		AjaxUtils.ajaxResponse(jsonobject.toString());
	}
	/**
	 * 运输部门查询详情
	 */
	public String details(){
		long proposerId = this.getIntegerParameter("id");
		List<GoodsEntity> goodsEntities = goodsManager.findByPid(proposerId);
		Proposer proposer = proposerManager.findById(proposerId);
		this.setAttribute("goodsName", proposer.getGoodsName());
		List<Map<String,Object>> goods = new JSONArray();
		for (GoodsEntity goodsEntity : goodsEntities) {
			Map<String,Object> goodsMap = new JSONObject();
			if(proposer.getPickUser()==null){
				proposer.setPickUser("");
			}
			this.setAttribute("pickUser", proposer.getPickUser());
			this.setAttribute("pickPhone", proposer.getPickPhone());
			Date createStamp = proposer.getCreateStamp();
			String string = DateUtil.DateToString(createStamp, DateStyle.YYYY_MM_DD_HH_MM_SS);
			this.setAttribute("createStamp", string);
			goodsMap.put("goodsteamId", proposer.getGoodsteamId());
			goodsMap.put("bedNo", goodsEntity.getBedNo());			
			goodsMap.put("bloodPeople", goodsEntity.getBloodPeople());				
			DepEntity depEntity = depManager.findById(goodsEntity.getDepId());
			goodsMap.put("booldDep", depEntity.getDepName());
			Date drawbloodTime = goodsEntity.getDrawbloodTime();
			String drawblood = DateUtil.DateToString(drawbloodTime, DateStyle.YYYY_MM_DD_HH_MM_SS);
			goodsMap.put("drawbloodTime", drawblood);
			goodsMap.put("bloodNum", goodsEntity.getBloodNum());
			goods.add(goodsMap);
		}
		getRequest().setAttribute("goods",goods);
		setUrl("details");
		return SUCCESS;
	}
	/**
	 * 派遣人员
	 * @return
	 */
	public String sendUser() {
		setUrl("sendUser");
		return SUCCESS;
	}
	/**
	 * 派遣人员保存
	 */
	public void saveUser(){
		// 申请单ID
		long proId = this.getIntegerParameter("proId");
		// 派遣人员ID
		String[] userIds = getRequest().getParameterValues("userId[]");
		Proposer proposer = proposerManager.findById(proId);
		StringBuffer sf =new StringBuffer();
		for (String userId : userIds) {
		    TransportOfProposer transportOfProposer = new TransportOfProposer();
		    transportOfProposer.setEnable(true);
		    transportOfProposer.setCreateStamp(new Date());
		    transportOfProposer.setModifyStamp(new Date());
		    transportOfProposer.setProposerId(proId);
		    transportOfProposer.setStaffId(Long.valueOf(userId));
		    transportOfProposer.setComplete(-1);
		    transportOfProposerManager.insert(transportOfProposer);
			Staff staff = staffManager.findById(Long.valueOf(userId));
			staff.setNumber(staff.getNumber()+1);
			staff.setStatus(staff.getStatus() + 1);
			staffManager.update(staff);
			sf.append(staff.getName()+",");
		}
		proposer.setPickUser(sf.substring(0, sf.length()-1).toString());
		proposerManager.update(proposer);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
}
