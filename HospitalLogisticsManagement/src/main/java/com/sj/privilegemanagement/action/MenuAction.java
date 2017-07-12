package com.sj.privilegemanagement.action;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.entity.Menu;
import com.sj.privilegemanagement.entity.MenuOfRole;
import com.sj.privilegemanagement.manager.MenuManager;
import com.sj.privilegemanagement.manager.ProjectManager;

/**
 * 菜单管理Action
 * @author lwj
 * 2017年4月20日 09:10:01
 */
public class MenuAction extends BaseAction<MenuManager,Menu> {
	private ProjectManager projectManager;
	
	public ProjectManager getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}
	/**
	 * 设置菜单对应文件夹
	 */
	public void setMenuOfFloder() {
		this.getMenuManager().setMenuOfFloder();
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 跳转添加菜单页面
	 * 元冬冬
	 * 2017年5月18日15:48:38
	 */
	public String addmenufrom(){
		setUrl("addmenufrom");
		return SUCCESS;
	}
	
	/**
	 * 跳转修改菜单页面
	 * 元冬冬
	 * 2017年5月17日09:13:18
	 */
	public String menufrom(){
		setUrl("menufrom");
		return SUCCESS;
	}
	/**
	 * 获取所有的顶级菜单
	 * 元冬冬
	 * 2017年5月17日09:47:22
	 */
	public void getTopMenu(){
		List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
		list = this.getManager().getTopMenu();
		if(list!=null &&list.size()>0){
			for (Map<String, Object> map : list) {
				map.put("id", map.get("ID_"));
				map.put("text",map.get("FOLDER_NAME_"));
			}
		}
		AjaxUtils.ajaxResponse(JSONArray.fromObject(list).toString());
	}
	/**
	 * 修改菜单功能
	 * 元冬冬
	 * 2017年5月17日10:25:47
	 */
	  public void editmenu(){
		  String id = getRequest().getParameter("id");
		  String fatherid = getRequest().getParameter("fatherid");
		  String menuname = getRequest().getParameter("menuname");
		  String mid = getRequest().getParameter("mid");
	      if(fatherid==null){
	    	   this.getMenuManager().editemty(menuname,mid);
	      }else{
	    	  this.getMenuManager().editmenu(id,menuname,mid);
	      }
	      AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	  }
	/**
	 * 获取菜单树
	 * params 用户id
	 */
	public void findByMenuTree(){
		Long id = (Long) getRequest().getSession().getAttribute("userId");
		if(id == null){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.NO_OBJECT));
			return;
		}
		JSONArray jsonArray = this.getMenuManager().findMenuTreeByUserId(Long.valueOf(id));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("menus", jsonArray);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(jsonObject));
	}
	@Override
	@Jurisdiction(name="菜单管理",level=-1,url="index")
	public String index() {
		return super.index();
	}
	public void findall(){
		String id = getRequest().getParameter("id");
		List<Map<String, Object>> alist =new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> mlist = new ArrayList<Map<String,Object>>();
		JSONArray jsonArray = new JSONArray();
		int total = 0;
	     if(id==null){
	    	 alist = this.getManager().findall();
	    	 total += alist.size();
	    	 for (Map<String, Object> map : alist) {
				BigInteger bigint = (BigInteger) map.get("ID_");
				mlist = (List<Map<String, Object>>) this.getManager().findmlist(bigint);
				total += mlist.size();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", bigint);
				jsonObject.put("name", map.get("FOLDER_NAME_"));
				jsonObject.put("iconCls", "icon-ok");
				jsonArray.add(jsonObject);
				for (Map<String, Object> m1 : mlist) {
					JSONObject jsonObjectT = new JSONObject();
					BigInteger b = (BigInteger) m1.get("ID_");
					BigInteger b1 = (BigInteger) m1.get("FOLDER_ID_");
					jsonObjectT.put("id", b);
					jsonObjectT.put("name", m1.get("NAME_"));
					jsonObjectT.put("_parentId", b1);
					jsonArray.add(jsonObjectT);
				}
			}
	     }
	     JSONObject data = new JSONObject();
	     data.put("rows", jsonArray);
	     data.put("total", total);
	     AjaxUtils.ajaxResponse(data.toString());
	}
	/**
	 * 根据角色获取菜单
	 * @return
	 */
	public void getmenu(){
		long roleId = this.getIntegerParameter("roleId");
		List<MenuOfRole> menuOfRoles = getMenuManager().findbyroleId(roleId);
	    JSONArray jsonArray = new JSONArray();
		if(menuOfRoles!=null && menuOfRoles.size()>0){
			for (MenuOfRole menuOfRole : menuOfRoles) {
				JSONObject jsObject =new JSONObject();
				jsObject.put("ID_", menuOfRole.getMenuId());
				jsonArray.add(jsObject);
			}
			AjaxUtils.ajaxResponse(jsonArray.toString());
		}	
	}
	private MenuManager getMenuManager() {
		return (MenuManager)super.getBaseManager();
	}
}
