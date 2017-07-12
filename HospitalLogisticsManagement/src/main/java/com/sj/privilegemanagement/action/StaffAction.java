package com.sj.privilegemanagement.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.Staff;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.manager.DepManager;
import com.sj.privilegemanagement.manager.StaffManager;

public class StaffAction extends BaseAction<StaffManager, Staff> {

	/**
	 * 人员管理 action 2017年6月14日10:40:30 元冬冬
	 */
	private static final long serialVersionUID = -4458938944199930493L;
     private StaffManager staffManager;
     private DepManager depManager;
	public void setStaffManager(StaffManager staffManager) {
		this.staffManager = staffManager;
	}
	public void setDepManager(DepManager depManager) {
		this.depManager = depManager;
	}
	public String addKeyuan(){
		setUrl("addKeyuan");
		return SUCCESS;
	}
	/**
	 * 科室查询
	 * @throws IOException 
	 */
	@Jurisdiction(name="人员管理",level=-1,url="index")
	public void findAll(){
		long deptid = this.getIntegerParameter("id");
	     List<Staff> staff = staffManager.findStaffByDepId(deptid);
	     JSONArray jsonArray = new JSONArray();
	     for (Staff staffs : staff) {
	    	 DepEntity depEntity = depManager.findById(staffs.getDeptId());
	    	 JSONObject jsonObject =new JSONObject();
	    	 jsonObject.put("id", staffs.getId());
	    	 jsonObject.put("name", staffs.getName());
	    	 jsonObject.put("depName", depEntity.getDepName());
	    	 jsonObject.put("phone", staffs.getPhone());
	    	 jsonObject.put("number", staffs.getNumber());
	    	 jsonObject.put("state", staffs.getStatus());
	    	 jsonArray.add(jsonObject);
		}
	     AjaxUtils.ajaxResponse(jsonArray.toString());
	}
	/**
	 * 添加科员
	 * 2017年6月14日10:56:13
	 * 元冬冬
	 */
	public void addkeyuan(){
		long depId = this.getIntegerParameter("depId");
		String staffName = this.getParameter("keyuanName");
		String phone = this.getParameter("phone");
		Staff staff = new Staff();
		staff.setCreateStamp(new Date());
		staff.setDeptId(depId);
		staff.setEnable(true);
		staff.setModifyStamp(new Date());
		staff.setName(staffName);
		staff.setNumber(0);
		staff.setStatus(0);
		staff.setPhone(phone);
		staffManager.insert(staff);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 获取部门下的人员 2017年6月14日11:22:43 元冬冬
	 */
	public void getdepartmentrenyuan(){
		long deptid = this.getIntegerParameter("deptid");
	     List<Staff> staff = staffManager.findStaffByDepId(deptid);
	     JSONArray jsonArray = new JSONArray();
	     for (Staff staffs : staff) {
	    	 DepEntity depEntity = depManager.findById(staffs.getDeptId());
	    	 JSONObject jsonObject =new JSONObject();
	    	 jsonObject.put("id", staffs.getId());
	    	 jsonObject.put("name", staffs.getName());
	    	 jsonObject.put("phone", staffs.getPhone());
	    	 jsonObject.put("depName", depEntity.getDepName());
	    	 jsonObject.put("number", staffs.getNumber());
	    	 jsonObject.put("state", staffs.getStatus());
	    	 jsonArray.add(jsonObject);
		}
	     AjaxUtils.ajaxResponse(jsonArray.toString());
	}
	/**
	 * 获取派遣人员列表 2017年6月14日14:26:52 元冬冬
	 */
	public void getdepartment(){
		long useid = this.getIntegerParameter("userid");
	    JSONArray jsonArray = new JSONArray();
		List<DepEntity> depEntities = depManager.findByUserId(useid);
		for (DepEntity depEntity : depEntities) {
			List<Staff> staffs = staffManager.findStaffByDepId(depEntity.getId());
			for (Staff staff : staffs) {
		    	 JSONObject jsonObject =new JSONObject();
		    	 jsonObject.put("id", staff.getId());
		    	 jsonObject.put("name", staff.getName());
		    	 jsonObject.put("depName", depEntity.getDepName());
		    	 jsonObject.put("number", staff.getNumber());
		    	 jsonObject.put("phone", staff.getPhone());
		    	 jsonObject.put("state", staff.getStatus());
		    	 jsonArray.add(jsonObject);
			}
		}
		 AjaxUtils.ajaxResponse(jsonArray.toString());
	}
	/**
	 * 删除人员操作 元冬冬 2017年6月15日15:17:45
	 */
	  public void deletekeyuan(){
		  long userId = this.getIntegerParameter("userId");
			Staff staff = staffManager.findById(Long.valueOf(userId));
			staff.setEnable(false);
			staffManager.update(staff);
		 AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));  
	  }
}
