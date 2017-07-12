package com.sj.common.filter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.sj.common.impl.AbstractFilterChainDefinitionsService;
import com.sj.privilegemanagement.dao.AdminPassDao;
import com.sj.privilegemanagement.entity.Menu;
import com.sj.privilegemanagement.manager.MenuManager;

public class FilterChainDefinitionMapBuilder extends AbstractFilterChainDefinitionsService {
	
	private AdminPassDao adminPassDao;
	public void setAdminPassDao(AdminPassDao adminPassDao) {
		this.adminPassDao = adminPassDao;
	}
	public static Map<String,String> jurisdictionInfoMap = new HashedMap();
	public static int mark = -1;
	
	private MenuManager menuManager;
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		if(mark < 0){
			mark = 1;
			//菜单权限赋值
			List<Menu> menus = menuManager.findAll();
			menus.forEach(e -> {jurisdictionInfoMap.put("/" + e.getLinkUrl() + "*"
					, menuManager.findRoleStringByMenuId(e.getId()));});
		}
		
		map.put("/login_login.action", "anon");
		map.put("/logout", "logout");
		map.put("/images/**", "anon");
		map.put("/Js/**", "anon");
		map.put("/views/**", "anon");
		map.put("/hall.action", "anon");
		map.put("/common-lwj/**", "anon");
		map.put("/ape.html", "anon");
		map.put("/user_ape.action", "anon");
		map.put("/proposer_findAll.action", "anon");
		
		
//		Container con = Dispatcher.getInstance().getConfigurationManager().getConfiguration
//		().getContainer();   
//		String s = con.getInstance(String.class, "struts.action.extension"); 
//        /system/verifyCode.action* = anon  
//        /system/userLogin_login.action* = anon  
//        /error/** = anon  
//        /Json/** = anon  
//        /Css/** = anon  
		if(jurisdictionInfoMap != null && jurisdictionInfoMap.size() > 0)
		map.putAll(jurisdictionInfoMap);
		map.put("/**", "authc");
		return map;
	}
	
	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}
	
	@Override
	public Map<String, String> initOtherPermission() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		List<Menu> menus = menuManager.findAll();
		menus.forEach(e -> {jurisdictionInfoMap.put("/" + e.getLinkUrl() + "*"
				, menuManager.findRoleStringByMenuId(e.getId()));});
		map.put("/login_login.action", "anon");
		map.put("/logout", "logout");
		map.put("/images/**", "anon");
		map.put("/Js/**", "anon");
		map.put("/views/**", "anon");
		map.put("/hall.action", "anon");
		map.put("/ape.html", "anon");
		map.put("/user_ape.action", "anon");
		map.put("/proposer_findAll.action", "anon");
		map.put("/common-lwj/**", "anon");
		if(jurisdictionInfoMap != null && jurisdictionInfoMap.size() > 0)
		map.putAll(jurisdictionInfoMap);
		map.put("/**", "authc");
		return map;
	}
}
