package com.sj.privilegemanagement.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.JSONUtils;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.Staff;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.manager.DepManager;
import com.sj.privilegemanagement.manager.StaffManager;
import com.sj.privilegemanagement.manager.UserManager;
/**
 * 科室
 * @author Administrator
 *
 */
public class DepAction extends BaseAction<DepManager, DepEntity>{
	private static final long serialVersionUID = 1L;
	private UserManager userManager;
	private DepManager depManager;
	private StaffManager staffManager;
	
	public void setStaffManager(StaffManager staffManager) {
		this.staffManager = staffManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public void setDepManager(DepManager depManager) {
		this.depManager = depManager;
	}

	/**
	 * 添加科室跳转
	 * 2017年5月9日08:59:58
	 * 元冬冬
	 * @return
	 */
	@Jurisdiction(name="添加科室",url="addDepartment")
	public String addDepartment(){
		setUrl("addDepartment");
		return SUCCESS;
	}
	public void addDep(){
		String mark = this.getRequest().getParameter("mark");
		String depName = this.getRequest().getParameter("depName");
		String depPhone = this.getRequest().getParameter("depPhone");
		String selectvalue = this.getRequest().getParameter("selectvalue");
		DepEntity depEntity= new DepEntity();
		if(selectvalue.equals("1")){
			depEntity.setTransport(1);
		}else{
			depEntity.setTransport(0);
		}
		depEntity.setMark(mark);
		depEntity.setDepName(depName);
		depEntity.setDepPhone(depPhone);
		depEntity.setCreateStamp(new Date());
		depEntity.setEnable(true);
		depManager.insert(depEntity);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 科室查询
	 * @throws IOException 
	 */
	@Jurisdiction(name="科室管理",level=-1,url="index")
	public void findAll(){
		User user = (User) getRequest().getSession().getAttribute("userInfo");
		String username = user.getUserName();
		int page = this.getIntegerParameter("page");
		int rows = this.getIntegerParameter("rows");
		Map<String, String> param = new HashMap<String, String>();
		String depname = this.getParameter("depname");
		List<DepEntity> depEntities = null;
		JSONObject jsonObject = new JSONObject();
		JSONArray depArray = new JSONArray();
		Long i =0l;
		if(StringUtils.isBlank(depname)){
		if("admin".equals(username)){
			depEntities = depManager.findAll(page, rows, null);
			 i = depManager.findCountNum();
		}else{
			depEntities = depManager.findByUserId(user.getId());
			String hql = "select count(*) from DepEntity d,UserOfDepAdmin ud where ud.depId = d.id and ud.userId = '"+user.getId()+"'"
					+ " and d.enable = true";
			 i = depManager.findCountNum(hql);
		}
		}else{
			param.put("page", String.valueOf(page));
			param.put("rows", String.valueOf(rows));
			param.put("depname", depname);
			depEntities =depManager.findbyparam(param);
			param.remove("page");	
			param.remove("rows");
			i =(long) depManager.findbyparam(param).size();
		}
		for (DepEntity dep : depEntities) {
			JSONObject depJson = JSONObject.fromObject(dep);
			List<Long> userIds = depManager.findUserIdByDepId(dep.getId());
			depJson.put("userIds", userIds);
			StringBuffer buffer = new StringBuffer();
			for (Long userId : userIds) {
				buffer.append("," + userManager.findById(userId).getUserName());
			}
			if(buffer.length() > 0)
				depJson.put("userName", buffer.substring(1));
			depArray.add(depJson);
		}
		jsonObject.put("rows", depArray);
		jsonObject.put("total", i);
		AjaxUtils.ajaxResponse(jsonObject.toString());
		
	}
	/**
	 * 获取科室列表
	 * 2017年5月3日16:43:29
	 */
	public void getdepartment(){
		List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
		List<DepEntity> depEntitys = depManager.findAll();
		for (DepEntity depEntity : depEntitys) {
			Map<String,Object> map = new HashMap();
			map.put("id", depEntity.getId());
			map.put("text",depEntity.getDepName());
			list.add(map);
		}
		AjaxUtils.ajaxResponse(JSONArray.fromObject(list).toString());
	}

	  /**
	   * 设置科室科长
	   * 2017年5月3日09:09:16
	   * 元冬冬
	   * @return
	   */
	@Jurisdiction(name="设置科长")
	public void editkezhang(){
		String username = getRequest().getParameter("userName");
		String[] userids = getRequest().getParameterValues("userid[]");
		String id = getRequest().getParameter("id");
		depManager.editkezhang(username,userids,id);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	private DepManager getDepManager(){
		return (DepManager) getBaseManager();
	}
	/**
	 * 查询部门为运输部的部门
	 */
	public void findByTransport(){
		List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
		List<DepEntity> depEntitys=depManager.findByTransport();
		for (DepEntity depEntity : depEntitys) {
			Map<String,Object> map = new HashMap();
			map.put("id", depEntity.getId());
			map.put("text",depEntity.getDepName());
			list.add(map);
		}
		//JSONObject jsonObject = new JSONObject();
		//jsonObject.put("depEntity", depEntitys);
		AjaxUtils.ajaxResponse(JSONArray.fromObject(list).toString());
	}
	/**
	 * 删除科室功能 2017年6月21日17:47:40 元冬冬
	 */
	public void deletekeyuan(){
		long depid = this.getIntegerParameter("id");
	    DepEntity depEntity = depManager.findById(depid);
	    depEntity.setEnable(false);
	    depManager.update(depEntity);
	    AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
}
