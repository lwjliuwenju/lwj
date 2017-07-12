package com.sj.privilegemanagement.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.JSONUtils;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.entity.Role;
import com.sj.privilegemanagement.entity.UserOfRole;
import com.sj.privilegemanagement.manager.RoleManager;
/**
 * 2017年4月20日 14:29:25
 * @author lwj
 * 角色Action
 */
public class RoleAction extends BaseAction<RoleManager,Role> {

	private static final long serialVersionUID = 1L;

	/**
	 * 分配权限
	 */
	public void setMenus() {
		this.getRoleManager().removerMenusByRoleId();
		this.getRoleManager().setMenus();
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	@Override
	@Jurisdiction(name="角色管理",level=-1)
	public String index() {
		return super.index();
	}
	/**
	 * 列表
	 * @throws IOException 
	 */
	@Jurisdiction(name="角色管理",level=-1,url="index")
	public void findAll() throws IOException {
		List<Map<String , Object>> alist = new ArrayList<Map<String,Object>>();
		String page = getRequest().getParameter("page");
		String rows = getRequest().getParameter("rows");
		String flag = getRequest().getParameter("flag");
		int intPage = Integer.parseInt((page == null || "0".equals(page)) ? "1"
				: page);
		// 每页显示条数
		int pageSize = Integer
				.parseInt((rows == null || "0".equals(rows)) ? "15" : rows);
		// 每页的开始记录 第一页为1 第二页为number +1
		int start = (intPage - 1) * pageSize;
		if ("1".equals(flag)) {
			start = 0;
		}
		alist = this.getRoleManager().findall(pageSize,start);
		Long l = this.getRoleManager().findcount(pageSize,start);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rows", JSONUtils.toJSONString(alist));
		jsonObject.put("total", l);
		AjaxUtils.ajaxResponse(jsonObject.toString());
	/*	JSONArray jsonArray = this.getRoleManager().findJsonList();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rows", jsonArray);
		AjaxUtils.ajaxResponse(jsonObject.toString());*/
	}
	
	public void getrole(){
    	long userid = this.getIntegerParameter("userid");
        List<UserOfRole> roles = this.getRoleManager().findbyuserid(userid);
        JSONArray jsonArray = new JSONArray();
        if(roles!=null && roles.size()>0){
        	  for (UserOfRole userOfRole : roles) {
        		JSONObject jsObject =new JSONObject();
				jsObject.put("ID_", userOfRole.getRoleId());
				jsonArray.add(jsObject);
			}
        	AjaxUtils.ajaxResponse(jsonArray.toString());
        }
    }
	/**
	 * 授权用户
	 */
	public void addUser(){
		this.getRoleManager().removerUserByRoleId();
		this.getRoleManager().setUser();
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 根据角色ID获取用户
	 * @return
	 */
	public void getUser(){
		long roleId = this.getIntegerParameter("roleId");
		List<UserOfRole> userOfRoles = getRoleManager().findbyroleId(roleId);
		   JSONArray jsonArray = new JSONArray();
	        if(userOfRoles!=null && userOfRoles.size()>0){
	        	  for (UserOfRole userOfRole : userOfRoles) {
	        		JSONObject jsObject =new JSONObject();
					jsObject.put("ID_", userOfRole.getUserId());
					jsonArray.add(jsObject);
				}
	        	AjaxUtils.ajaxResponse(jsonArray.toString());
	        }
	}
	private RoleManager getRoleManager() {
		return (RoleManager)super.getBaseManager();
	}
}
