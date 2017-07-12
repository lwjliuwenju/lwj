package com.sj.privilegemanagement.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.CommonUtil;
import com.sj.common.utils.DateUtil;
import com.sj.common.utils.JSONUtils;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.common.utils.enums.DateStyle;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.GoodsEntity;
import com.sj.privilegemanagement.entity.ProjectEntity;
import com.sj.privilegemanagement.entity.Proposer;
import com.sj.privilegemanagement.entity.Staff;
import com.sj.privilegemanagement.entity.StaffOfProposer;
import com.sj.privilegemanagement.entity.TransportOfProposer;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.entity.VO.ProposerVO;
import com.sj.privilegemanagement.manager.DepManager;
import com.sj.privilegemanagement.manager.GoodsManager;
import com.sj.privilegemanagement.manager.ProjectManager;
import com.sj.privilegemanagement.manager.ProposerManager;
import com.sj.privilegemanagement.manager.StaffManager;
import com.sj.privilegemanagement.manager.StaffOfProposerManager;
import com.sj.privilegemanagement.manager.SuppliesManager;
import com.sj.privilegemanagement.manager.SuppliesProposerManager;
import com.sj.privilegemanagement.manager.TransportOfProposerManager;
import com.sj.privilegemanagement.websocket.DwrTest;

/**
 * 申请单
 * 
 * @author Administrator
 *
 */
public class ProposerAction extends BaseAction<ProposerManager, Proposer> {

	private static final long serialVersionUID = 1L;

	private static ProposerManager proposerManager;
	private DepManager depManager;
	private static ProjectManager projectManager;
	private SuppliesProposerManager suppliesProposerManager;
	private SuppliesManager suppliesManager;
	private StaffOfProposerManager staffOfProposerManager;
	private StaffManager staffManager;
	private GoodsManager goodsManager;
	private TransportOfProposerManager transportOfProposerManager;
	
	public void setTransportOfProposerManager(
			TransportOfProposerManager transportOfProposerManager) {
		this.transportOfProposerManager = transportOfProposerManager;
	}

	public void setGoodsManager(GoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public void setSuppliesManager(SuppliesManager suppliesManager) {
		this.suppliesManager = suppliesManager;
	}

	public void setStaffManager(StaffManager staffManager) {
		this.staffManager = staffManager;
	}

	public void setStaffOfProposerManager(
			StaffOfProposerManager staffOfProposerManager) {
		this.staffOfProposerManager = staffOfProposerManager;
	}
	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public DepManager getDepManager() {
		return depManager;
	}

	public void setDepManager(DepManager depManager) {
		this.depManager = depManager;
	}

	public void setProposerManager(ProposerManager proposerManager) {
		this.proposerManager = proposerManager;
	}

	public SuppliesProposerManager getSuppliesProposerManager() {
		return suppliesProposerManager;
	}

	public void setSuppliesProposerManager(
			SuppliesProposerManager suppliesProposerManager) {
		this.suppliesProposerManager = suppliesProposerManager;
	}

	/**
	 * 添加申请单页面 元冬冬 2017年5月16日15:14:12
	 * 
	 * @return
	 */
	@Jurisdiction(name = "添加申请单")
	public String addServiceform() {
		setUrl("addServiceform");
		return SUCCESS;
	}

	@Override
	@Jurisdiction(level=-1,name="申请单管理")
	public String index() {
		Object userT = this.getSession().getAttribute("userInfo");
		if(userT == null){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.NO_USER));
			return NONE;
		}
		User user = (User)userT;
		getRequest().setAttribute("username", user.getUserName());
		return super.index();
	}
	/**
	 * 评价申请单跳转 2017年5月5日16:03:47 元冬冬
	 * 
	 * @throws Exception
	 */
	@Jurisdiction(name = "评价")
	public String evaluateform() {
		long id = this.getIntegerParameter("id");
		setUrl("evaluateform");
		return SUCCESS;
	}
	/**
	 * 选择服务跳转 2017年6月13日17:15:31 元冬冬
	 */
    @Jurisdiction(name="选择服务")
    public String selectProject(){
    	setUrl("selectProject");
		return SUCCESS;
    }
    public String editProject(){
    	setUrl("editProject");
		return SUCCESS;
    }
	/**
	 * 服务大厅
	 * 
	 * @return
	 */
	@Jurisdiction(name = "服务大厅", level = -1)
	public String hall() {
		setUrl("hall");
		return SUCCESS;
	}
  
	/**
	 * 查询详情页面跳转 元冬冬 2017年5月16日15:12:27
	 */
	public String detailform() {
		long id = this.getIntegerParameter("id");
		Proposer proposer = proposerManager.findById(id);
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> suppliesJsonArray = suppliesManager
				.findByProId(proposer.getId());
		this.setAttribute("supplies", suppliesJsonArray);
		List<StaffOfProposer> staffOfProposers = staffOfProposerManager.findStaffAllByProId(id);
		List<Map<String,Object>> staffs = new JSONArray();
		if(staffOfProposers!=null&&staffOfProposers.size()>0){
		for (StaffOfProposer staffOfProposer : staffOfProposers) {
			Staff staff = staffManager.findById(staffOfProposer.getStaffId());
			Map<String,Object> staffMap = new JSONObject();
			staffMap.put("staffName", staff.getName());
			staffMap.put("phone", staff.getPhone());
			String complete = "";
			switch (staffOfProposer.getComplete()) {
				case 0:complete = "维修中";break;
				case 1:complete = "未完成";break;
				case 2:complete = "已完成";break;
				default:break;
			}
			staffMap.put("complete", complete);
			staffs.add(staffMap);
		}
		}
		this.setAttribute("staffs", staffs);
		// 响应单开始时间
		getRequest().setAttribute("startTime", proposer.getCreateStamp());
		// 科室姓名
		getRequest().setAttribute("sendDepName", proposer.getDepName());
		// 申请时间
		getRequest().setAttribute("sendTime", proposer.getCreateStamp());
		// 申请单状态
		getRequest().setAttribute("state", proposer.getStatus());
		// 申请人
		getRequest().setAttribute("sendUser", proposer.getUserName());
		// 维修原因
		getRequest().setAttribute("repairreason", proposer.getRepairReason());
		// 评价等级
		getRequest().setAttribute("appraise", proposer.getAppraise());
		// 响应部门名称
		getRequest().setAttribute("reponseDepName",
				proposer.getResponseDepName());
		// 服务名称
		getRequest().setAttribute("proName", proposer.getProjectName());
		// 完成时间
		getRequest().setAttribute("endTime", proposer.getResponseEndTime());
		// 评价内容
		getRequest().setAttribute("appraiseMark", proposer.getAppraiseMark());
		// 备注
		getRequest().setAttribute("remark", proposer.getRemark());
		// 终止原因
		getRequest().setAttribute("endReason", proposer.getTerminationReason());
		// 终止人姓名
		getRequest().setAttribute("endName",
				proposer.getTerminationReasonUserName());
		// 返修标记
		getRequest().setAttribute("repairflag", proposer.getRepairFlag());
		// 返修原因
		getRequest().setAttribute("repairReason", proposer.getRepairReason());
		// 审核标记
		getRequest().setAttribute("checkflag", proposer.getCheckflag());
		// 审核内容
		getRequest().setAttribute("checkContent", proposer.getCheckContent());
		//物品名称
		String goodsName = proposer.getGoodsName();
		this.setAttribute("goodsName", goodsName);
		//服务
		Long projectId = proposer.getProjectId();
		ProjectEntity projectEntity = new ProjectEntity();
		if(projectId!=null){
		projectEntity = projectManager.findById(projectId);
		getRequest().setAttribute("projectName", projectEntity.getProjectName());
		getRequest().setAttribute("standardHour", projectEntity.getStandardHour());
		getRequest().setAttribute("grade", projectEntity.getGrade());
		}
		List<GoodsEntity> goodsEntities = goodsManager.findByPid(id);
		List<Map<String,Object>> goods = new JSONArray();
		for (GoodsEntity goodsEntity : goodsEntities) {
			Map<String,Object> goodsMap = new JSONObject();
			if(proposer.getPickUser()==null)
				proposer.setPickUser("");
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
		setUrl("detailform");
		return SUCCESS;
	}
    /**
     * 选择服务功能 元冬冬2017年6月14日08:39:41
     */
	public void saveproject(){
		long responseDeptid = this.getIntegerParameter("responseDeptid");
	    String responseDeptitem = this.getParameter("responseDeptitem");
	    String projectIdT = this.getParameter("ProposerId");
	    DepEntity depEntity = depManager.findById(responseDeptid);
	    long projectId = 0;
	    try {
			projectId = Long.valueOf(projectIdT);
		} catch (Exception e) {
			ProjectEntity projectEntity = new ProjectEntity();
			projectEntity.setCreateStamp(new Date());
			projectEntity.setEnable(true);
			projectEntity.setModifyStamp(new Date());
			projectEntity.setProjectName(projectIdT);
			projectEntity.setResponseDeptId(responseDeptid);
			projectEntity.setResponseDeptName(depEntity.getDepName());
			projectEntity.setStartFlag(0);
			projectEntity.setStandardHour(2);
			projectManager.insert(projectEntity);
			projectId = projectEntity.getId();
		}
	    long proId = this.getIntegerParameter("proId");
	    Proposer proposer = proposerManager.findById(proId);
	    proposer.setProjectId(projectId);
	    proposer.setProjectName(responseDeptitem);
	    proposer.setReponseDepId(responseDeptid);
	    proposer.setResponseDepName(depEntity.getDepName());
	    proposerManager.update(proposer);
	    AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 重写添加申请单 元冬冬 2017年5月16日15:11:59
	 * 
	 * @throws Exception
	 */
	public void saveProposer() throws Exception {
		User user = (User) getRequest().getSession().getAttribute("userInfo");
		if (user == null) {
			AjaxUtils
					.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.NO_USER));
			return;
		}
		String sendDepId = this.getParameter("sendDeptId");
		DepEntity sendDepEntity = depManager.findById(Long.valueOf(sendDepId));
		String userName = user.getUserName();
		String mark = getRequest().getParameter("mark");
		long responseDeptid = this.getIntegerParameter("responseDeptid");
		DepEntity depEntity = depManager.findById(responseDeptid);
		Proposer proposer = new Proposer();
		proposer.setReponseDepId(responseDeptid);
		proposer.setResponseDepName(depEntity.getDepName());
		proposer.setDepId(sendDepEntity.getId());
		proposer.setDepName(sendDepEntity.getDepName());
		proposer.setModifyStamp(new Date());
		proposer.setCheckflag(0);
		proposer.setCreateStamp(new Date());
		proposer.setEnable(true);
		proposer.setRemark(mark);
		proposer.setRepairFlag(0);
		proposer.setUserName(userName);
		proposer.setUserId(user.getId());
		proposer.setOutSourcIngFlag("0");
		proposer.setResponseTimes(0);
		proposer.setStatus(2);
		proposer.setGqbj(0);
		proposerManager.insert(proposer);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
		List<Long> userIds = depManager.findUserIdByDepId(responseDeptid);
		for (Long userId : userIds) {
		DwrTest.sendMessageAuto(userId.toString(), "您有一条来自" + sendDepEntity.getDepName() + "的申请单！");
		}
	}
	/**
	 * 评价功能 2017年5月11日17:46:36 元冬冬
	 */
	public void evaluate() {
		Integer proposerId = this.getIntegerParameter("proposerId");
		if (proposerId.equals(0)) {
			AjaxUtils.ajaxResponse(AjaxUtils
					.getErrorMessage(AjaxUtils.PARAMETER_ERROR));
		}
		String appraiseMark = getRequest().getParameter("appraiseMark");
		int appraise = this.getIntegerParameter("appraise");
		Proposer proposer = proposerManager.findById(proposerId.longValue());
		proposer.setAppraise(appraise);
		proposer.setAppraiseMark(appraiseMark);
		proposer.setStatus(6);
		proposerManager.update(proposer);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
		List<Long> userIds = depManager.findUserIdByDepId(proposer.getReponseDepId());
		for (Long userId : userIds) {
		DwrTest.sendMessageAuto(userId.toString(), "您有一条申请单被评价！");
		}
	}

	/**
	 * 根据查询条件获取申请单列表 2017年5月5日09:18:34 元冬冬
	 * 
	 * @throws IOException
	 */
	public void findAll() throws IOException {
		Map<String, String> param = new HashMap<String, String>();
		Object userT = getRequest().getSession().getAttribute("userInfo");
		User user = null;
		if(userT != null)
			user = (User)userT;
		if (userT != null && user.getId() != 0) {
			List<DepEntity> depEntitys = depManager.findByUserId(user.getId());
			StringBuffer stringBuffer = new StringBuffer();
			for (DepEntity dep : depEntitys) {
				stringBuffer.append("," + dep.getId());
			}
			if(stringBuffer.length() > 0)
			param.put(
					"sendDepId",stringBuffer.substring(1));
		}
		String page = getRequest().getParameter("page");
		String rows = getRequest().getParameter("rows");
		String reponseUser = getRequest().getParameter("reponseUser");
		String sendUserName = getRequest().getParameter("sendUserName");
		String state = getRequest().getParameter("state");
		String sendDepName = getRequest().getParameter("sendDepName");
		String reponseDepId = this.getParameter("reponseDepId");
		String shunxu = this.getParameter("shunxu");
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		param.put("startTime", startTime);
		param.put("endTime",endTime );
		param.put("shunxu",shunxu);
		param.put("page", page);
		param.put("rows", rows);
		param.put("reponseUser", reponseUser);
		param.put("sendUserName", sendUserName);
		param.put("state", state);
		param.put("sendDepName", sendDepName);
		param.put("reponseDepId", reponseDepId);
		List<Proposer> proposers = proposerManager.findByParam(param);
		param.remove("page");	
		param.remove("rows");
		int total = proposerManager.findByParam(param).size();
		JSONArray jsonArray = new JSONArray();
		for (Proposer proposer : proposers) {
			JSONObject jsonObject = JSONUtils.toJSONObject(proposer);
			// 科室名称
			jsonObject.put("depName", proposer.getDepName());
			// 申请时间
			jsonObject.put("proposerTime", DateUtil.DateToString(proposer.getCreateStamp(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			// 申请服务
			jsonObject.put("projectName", proposer.getProjectName());
			// 返修
			jsonObject.put("repairFlag", proposer.getRepairFlag() == 0 ? "" : "<span style='color:red'>返</span>");
			// 响应次数
			jsonObject.put("responseTimes", proposer.getResponseTimes());
			// 外修
			if(proposer.getOutSourcIngFlag()!=null){
				jsonObject.put("outSourcIngFlag", proposer.getOutSourcIngFlag().equals("0") ? "" : "<span style='color:red'>外</span>");
			}else{
				jsonObject.put("outSourcIngFlag", "");
			}
			// 响应时间
			jsonObject.put("responseTime", DateUtil.DateToString(proposer.getResponseStartTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			//取货人
			jsonObject.put("pickUser", proposer.getPickUser());
			//物品名称
			jsonObject.put("goodsName", proposer.getGoodsName());
			//部门
			if(proposer.getGoodsteamId()!=null){
			DepEntity depEntity = depManager.findById(proposer.getGoodsteamId());
			if(depEntity==null){
			jsonObject.put("pickdepName","admin");
			}else{
				jsonObject.put("pickdepName", depEntity.getDepName());
			}
			}else{
				jsonObject.put("pickdepName", "");
			}
			// 响应人员
			List<StaffOfProposer> staffs = staffOfProposerManager.findStaffByProId(proposer.getId());
			StringBuffer responseStaffBuffer = new StringBuffer();
			for (StaffOfProposer staff : staffs) {
				responseStaffBuffer.append(staffManager.findById(staff.getStaffId()).getName() + ",");
			}
			String responseStaff="";
			if(responseStaffBuffer.length()>0)
			responseStaff = responseStaffBuffer.substring(0, responseStaffBuffer.length() - 1);
			jsonObject.put("responseStaff", responseStaff);
			// 完成工时
			Date reponseDate = proposer.getResponseEndTime();
			// 完成时间
			jsonObject.put("endTime", DateUtil.DateToString(reponseDate, DateStyle.YYYY_MM_DD_HH_MM_SS));
			if (reponseDate == null)
				reponseDate = new Date();
			Date startDate = proposer.getCreateStamp();
			long shijian = reponseDate.getTime() - startDate.getTime();
			jsonObject.put("useTime",
					DateUtil.getDateStringByLong(shijian));
			// 申请人
			jsonObject.put("sendUserName", proposer.getUserName());
			// 响应科室名称
			jsonObject.put("reponseDepName", proposer.getResponseDepName());
			// 评价
			jsonObject.put("appraise", proposer.getAppraise());
			// 状态
			String proState = null;
			if(proposer.getGqbj() == 1){
				proState = getState(7);
			}else{
				proState = getState(proposer.getStatus());
			}
			jsonObject.put("state", proState);
			jsonObject.put("status", proposer.getStatus());
			jsonArray.add(jsonObject);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rows", jsonArray);
		jsonObject.put("total", total);
		String outTable = this.getParameter("outTable");
		if (outTable != null && "true".equals(outTable)) {
			List<ProposerVO> proposerVOs = new ArrayList<ProposerVO>();
			for (Proposer proposer : proposers) {
				ProposerVO proposerVO = new ProposerVO();
				proposerVO.setFwCon(proposer.getProjectName());
				proposerVO.setFx(String.valueOf(proposer.getRepairFlag()));
				proposerVO.setSqDep(proposer.getDepName());
				proposerVO.setSqRen(proposer.getUserName());
				proposerVO.setSqTime(DateUtil.DateToString(
						proposer.getCreateStamp(),
						DateStyle.YYYY_MM_DD_HH_MM_SS));
				proposerVO.setState(String.valueOf(proposer.getStatus()));
				proposerVO.setWcTime(DateUtil.DateToString(
						proposer.getResponseEndTime(),
						DateStyle.YYYY_MM_DD_HH_MM_SS));
				proposerVO.setWx(proposer.getOutSourcIngFlag());
				List<StaffOfProposer> staffs = staffOfProposerManager.findStaffByProId(proposer.getId());
				StringBuffer responseStaffBuffer = new StringBuffer();
				for (StaffOfProposer staff : staffs) {
					responseStaffBuffer.append(staffManager.findById(staff.getStaffId()).getName() + ",");
				}
				String responseStaff = responseStaffBuffer.substring(0, responseStaffBuffer.length() - 1);
				proposerVO.setXxRen(responseStaff);
				proposerVO.setXxTime(DateUtil.DateToString(
						proposer.getCreateStamp(),
						DateStyle.YYYY_MM_DD_HH_MM_SS));
				proposerVO.setXyNum(String.valueOf(proposer.getResponseTimes()));
				Date reponseDate = proposer.getResponseEndTime();
				if (reponseDate == null)
					reponseDate = new Date();
				Date startDate = proposer.getCreateStamp();
				Date date = new Date(reponseDate.getTime()
						- startDate.getTime());
				proposerVO.setYyTime(DateUtil.DateToString(date,
						DateStyle.HH_MM));
				proposerVOs.add(proposerVO);
			}
			String filename = "申请单数据.xls";
			getResponse().setHeader(
					"content-disposition",
					"attachment;filename="
							+ URLEncoder.encode(filename, "UTF-8"));
			CommonUtil.outExcle(proposerVOs, getResponse().getOutputStream());
			return;
		}
		AjaxUtils.ajaxResponse(jsonObject.toString());
	}
	private String getStringIsNull(Object object) {
		if (object != null)
			return object.toString();
		return "";
	}
    /**
     * 完成功能操作  元冬冬 2017年6月13日10:51:46
     */
	public void complete(){
		String[] pId = this.getRequest().getParameterValues("proId[]");
		for (String proId : pId) {
			Proposer proposer = proposerManager.findById(Long.parseLong(proId));
			proposer.setStatus(4);
			proposer.setResponseEndTime(new Date());
			proposerManager.update(proposer);
			List<TransportOfProposer> transportOfProposers = transportOfProposerManager.findbyProId(Long.valueOf(proId));
			if(transportOfProposers!=null&& transportOfProposers.size()>0){
				for (TransportOfProposer transportOfProposer : transportOfProposers) {
					transportOfProposer.setComplete(1);
					transportOfProposerManager.update(transportOfProposer);
					long l =transportOfProposerManager.findbystaffId(transportOfProposer.getStaffId());
				    if(l==0){
				    	Staff staff = staffManager.findById(transportOfProposer.getStaffId());
				    	staff.setStatus(0);
				    	staffManager.update(staff);
				    }
				}
			}
			List<StaffOfProposer> staffofproposer = staffOfProposerManager.findStaffByProId(Long.valueOf(proId));
			for (StaffOfProposer staffOfProposers : staffofproposer) {
				staffOfProposers.setComplete(1);
				staffOfProposerManager.update(staffOfProposers);
				long l =staffOfProposerManager.findbystaffId(staffOfProposers.getStaffId());
			    if(l==0){
			    	Staff staff = staffManager.findById(staffOfProposers.getStaffId());
			    	staff.setStatus(0);
			    	staffManager.update(staff);
			    }
			}
			try {
				List<Long> userIds = depManager.findUserIdByDepId(proposer.getReponseDepId());
				for (Long userId : userIds) {
				DwrTest.sendMessageAuto(userId.toString(), "您有一条申请单已完成！");
				}
				} catch (Exception e) {
			}
		}
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 2017年6月13日15:31:22 HLH
	 * 服务标准化
	 */
	public static void findByStandard() {
		List<Proposer> proposers = proposerManager.findByStandard();
		if (proposers == null) {
			return;
		}
		for (Proposer proposer : proposers) {
			if(proposer.getProjectId() == null)
				continue;
			Date createStamp = proposer.getResponseStartTime();
			int standardHour = projectManager.findById(
					Long.valueOf(proposer.getProjectId())).getStandardHour();
			long endDate = standardHour * (60 * 60 * 1000);
			Date date = new Date();
			long time = date.getTime();// 系统当前时间转化为毫秒1494558967803
			long time2 = createStamp.getTime();// 服务开始时间1494559832000
			long time3 = time - time2;
			if (time3 > endDate) {
				proposer.setStatus(1);
				proposerManager.update(proposer);
			}
		}
	}
	/**
	 * 物流派送向物流队发送信息
	 */
	public void sendmessager(){
		String goodsName = this.getRequest().getParameter("goodsName");
		long depId = this.getIntegerParameter("depid");
		long proposerId = this.getIntegerParameter("proId");
		Proposer proposer = proposerManager.findById(proposerId);
		proposer.setGoodsteamId(depId);
		proposer.setGoodsName(goodsName);
		proposerManager.update(proposer);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
		List<Long> userIds = depManager.findUserIdByDepId(depId);
		for (Long userId : userIds) {
			DwrTest.sendMessageAuto(String.valueOf(userId), 
					"新的物流信息:" + goodsName );
		}
	}
	/**
	 * 2017年6月13日15:31:31 HLH
	 * 保存物流信息
	 * @throws ParseException 
	 */
	public void saveLogistics() throws ParseException{
		String parameter = this.getRequest().getParameter("object");
		Proposer proposer = null;
		long pid=0L;
		JSONObject jsonObject = JSONObject.fromObject(parameter);
		 if (jsonObject.has("proid")) {
			String proId = jsonObject.getString("proid");
			pid = Long.parseLong(proId);
			proposer = proposerManager.findById(pid);
		}if (jsonObject.has("pickUser")) {
			String pickUser = jsonObject.getString("pickUser");
			proposer.setPickUser(pickUser);
		}if (jsonObject.has("pickPhone")) {
			String pickPhone = jsonObject.getString("pickPhone");
			proposer.setPickPhone(pickPhone);
		}
		proposerManager.update(proposer);
		if(jsonObject.has("arr")){
			DepEntity depEntity = null;
			Integer bloodNums=null;
			JSONArray jsonArray = jsonObject.getJSONArray("arr"); 
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = JSONObject.fromObject(jsonArray.get(i));
				GoodsEntity goods = new GoodsEntity();
				goods.setBloodPeople(json.getString("bloodUser"));
				bloodNums = Integer.valueOf(json.get("bloodNum").toString());
				goods.setBloodNum(bloodNums);
				String string = json.get("bloodDate").toString();
				Date date = DateUtil.StringToDate(string, DateStyle.EASY_UI_MM_DD_YYYY_HH_MM_SS_EN);
				goods.setDrawbloodTime(date);
				long depid = Long.parseLong(json.get("bloodDep").toString());
				goods.setDepId(depid);
				goods.setBedNo(json.get("bloodPos").toString());
				goods.setProposerId(pid);
				goods.setCreateStamp(new Date());
				goods.setEnable(true);
				goods.setCreateStamp(new Date());
				goodsManager.insert(goods);
			}
			AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
			List<Long> userIds = depManager.findUserIdByDepId(proposer.getReponseDepId());
			for (Long userId : userIds) {
			DwrTest.sendMessageAuto(String.valueOf(userId), 
					proposer.getGoodsName() + "已被取走！");
			}
		}
	}
}
