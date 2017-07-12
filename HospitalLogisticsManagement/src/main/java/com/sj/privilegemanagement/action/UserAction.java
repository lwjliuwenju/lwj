package com.sj.privilegemanagement.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.EncryptUtil;
import com.sj.common.utils.JSONUtils;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.entity.AdminPass;
import com.sj.privilegemanagement.entity.Staff;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.manager.AdminPassManager;
import com.sj.privilegemanagement.manager.StaffManager;
import com.sj.privilegemanagement.manager.UserManager;
import com.sj.privilegemanagement.websocket.DwrTest;

/**
 * 用户操作action
 * @author lwj
 *
 */
public class UserAction extends BaseAction<UserManager,User> {

	private static final long serialVersionUID = 1L;
	private UserManager userManager;
	private StaffManager staffManager;
	private AdminPassManager adminPassManager;
	public void setStaffManager(StaffManager staffManager) {
		this.staffManager = staffManager;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public void setAdminPassManager(AdminPassManager adminPassManager) {
		this.adminPassManager = adminPassManager;
	}
	public void findall() {
		String page = this.getRequest().getParameter("page");
		String rows = this.getRequest().getParameter("rows");
		String jobNumber = this.getRequest().getParameter("jobNumber");
		String userName = this.getRequest().getParameter("userName");
		String phone = this.getRequest().getParameter("phone");
		if(StringUtils.isBlank(page) || StringUtils.isBlank(rows)){
			page = "1";
			rows = "10";
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("jobNumber", jobNumber);
		param.put("userName", userName);
		param.put("phone", phone);
		param.put("page", page);
		param.put("rows", rows);
		List<User> users =userManager.findbyparam(param);
		param.remove("page");	
		param.remove("rows");
		int total = userManager.findbyparam(param).size();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rows", JSONUtils.toJSONString(users));
		jsonObject.put("total", total);
		AjaxUtils.ajaxResponse(jsonObject.toString());
	}
	public void test() throws IOException{
		
//		Dispatcher dispatcher = Dispatcher.getInstance();  
//        ConfigurationManager configurationManager = dispatcher.getConfigurationManager();  
//        Configuration config = configurationManager.getConfiguration();  
//        RuntimeConfiguration c  =  config.getRuntimeConfiguration();  
//        Map<String,Map<String,ActionConfig>> d = c.getActionConfigs(); 
//        Set<String> set = d.get("/").keySet();
//        for (String str : set) {
//			System.out.println("\n" + str);
//		}
//        AjaxUtils.ajaxResponse(JSONObject.fromObject(d).toString());
		DwrTest.sendMessageAuto("67","dsfdsfgdsg");
//		List<ProposerVO> proposerVOs = new ArrayList<ProposerVO>();
//		ProposerVO proposerVO = new ProposerVO();
//		proposerVO.setSqTime("123456");
//		proposerVOs.add(proposerVO);
//        String filename = "申请单数据.xls";  
//        getResponse().setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));  
//		
//        OutputStream out = getResponse().getOutputStream();
//        CommonUtil.outExcle(proposerVOs, out);
	}
	@Override
	@Jurisdiction(name="用户管理",level=-1)
	public String index() {
		return super.index();
	}
	/**
	 * 设置角色
	 */
	@Jurisdiction(name="设置角色")
	public void setRoles() {
		String str1 = super.getRequest().getParameter("userId");
		Long userId = Long.valueOf(str1);
		String[] TroleIds = super.getRequest().getParameterValues("roleIds[]");
		List<Long> roleIds = new ArrayList<Long>();
		if(TroleIds != null)
		for(String str : TroleIds){
			roleIds.add(Long.valueOf(str));
		}
		this.getManager().removeRolesByUserId(userId);
		this.getManager().setUserOfRole(userId, roleIds);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 获取科员列表
	 * 2017年5月3日17:25:06
	 * 元冬冬
	 */
	public void getuser(){
		List<Staff> staffs = staffManager.findAll();
		JSONArray jsonArray = new JSONArray();
		for (Staff staff : staffs) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", staff.getId());
			jsonObject.put("text", staff.getName());
			jsonArray.add(jsonObject);
		}
		AjaxUtils.ajaxResponse(jsonArray.toString());
	}
	/**
	 * 获取申请人列表
	 * 2017年6月1日09:05:24
	 * 元冬冬
	 */
	public void getusers(){
		List<User> users = userManager.findAll();
		JSONArray jsonArray = new JSONArray();
		for (User user : users) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", user.getId());
			jsonObject.put("text", user.getUserName());
			jsonArray.add(jsonObject);
		}
		AjaxUtils.ajaxResponse(jsonArray.toString());
	}
	public void ape(){
		Object oldPassword = this.getParameter("oldPassword");
		if(oldPassword == null){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.OLD_PWD_ERROR));
			return;
		}
		String encrypt = EncryptUtil.encrypt(oldPassword.toString().trim());
		Map<String, Object> params = new HashMap<String, Object>();
		List<AdminPass> adminPasses = adminPassManager.findAll("from AdminPass", params);
		if(adminPasses != null && adminPasses.size() > 0 && !adminPasses.get(0).equals(encrypt)){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.OLD_PWD_ERROR));
			return;
		}
		Object password = this.getParameter("password");
		if(adminPasses.size() < 1){
			AdminPass adminPass = new AdminPass();
			adminPass.setPassword(EncryptUtil.encrypt(password.toString().trim()));
			adminPassManager.insert(adminPass);
			AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
			return;
		}
		AdminPass adminPass = adminPasses.get(0);
		adminPass.setPassword(EncryptUtil.encrypt(password.toString().trim()));
		adminPassManager.update(adminPass);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
}
