package com.sj.privilegemanagement.manager.impl;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.dispatcher.Dispatcher;
import org.hibernate.Query;
import org.hibernate.Session;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.RuntimeConfiguration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.sj.common.impl.FilterChainDefinitionsService;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.dao.MenuDao;
import com.sj.privilegemanagement.dao.MenuOfRoleDao;
import com.sj.privilegemanagement.entity.FolderEntity;
import com.sj.privilegemanagement.entity.Menu;
import com.sj.privilegemanagement.entity.MenuOfRole;
import com.sj.privilegemanagement.entity.Role;
import com.sj.privilegemanagement.manager.FolderEntityManager;
import com.sj.privilegemanagement.manager.MenuManager;

public class MenuManagerImpl extends BaseManagerImpl<Menu> implements MenuManager {

	private FolderEntityManager folderEntityManager;
	private FilterChainDefinitionsService shiroUtils;
	private MenuOfRoleDao menuOfRoleDao;
	
	public void setMenuOfRoleDao(MenuOfRoleDao menuOfRoleDao) {
		this.menuOfRoleDao = menuOfRoleDao;
	}
	public void setFolderEntityManager(FolderEntityManager folderEntityManager) {
		this.folderEntityManager = folderEntityManager;
	}
	@Override
	public MenuDao getMenuDao(){
		return (MenuDao)this.getBaseDao();
	}
	@Override
	public List<Menu> findByUserId(Long string) {
		return this.getMenuDao().findTreeByUserId(string);
	}
	@Override
	public void setMenuOfFloder() {
		Long floderId = Long.valueOf(getRequest().getParameter("folderId"));
		String[] menuIds = getRequest().getParameterValues("menuIds");
		for(String menuId : menuIds){
			Menu menu = this.getMenuDao().get(menuId);
			menu.setFolderId(floderId);
			this.getMenuDao().update(menu);
		}
	}
	/**
	 * 获取菜单树
	 */
	@Override
	public JSONArray findMenuTreeByUserId(Long id) {
		List<FolderEntity> folderEntities = folderEntityManager.findAll();
		JSONArray folderEntitiesOfJson = new JSONArray();
		for(FolderEntity folderEntity : folderEntities){
//			JSONObject jsonObject = JSONObject.fromObject(folderEntity);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("menuid", folderEntity.getId());
			jsonObject.put("icon", folderEntity.getImage());
			jsonObject.put("menuname", folderEntity.getFolderName());
			Set set = new  HashSet();
			List<Menu> menus = getMenuDao().findByUserIdAndFolId(id,folderEntity.getId());
			List<Menu> menusT = new ArrayList<Menu>();
			for(Menu menu : menus){
				if(set.add(menu)){
					menusT.add(menu);
				}
			}
			JSONArray jsonArray = new JSONArray();
			for(Menu menu : menusT){
				JSONObject jsonObject1 = new JSONObject();
				jsonObject1.put("menuid", menu.getId());
				jsonObject1.put("icon", menu.getImage());
				jsonObject1.put("menuname", menu.getName());
				jsonObject1.put("url", menu.getLinkUrl() + "?into=true");
				jsonArray.add(jsonObject1);
			}
			jsonObject.put("menus", jsonArray);
			folderEntitiesOfJson.add(jsonObject);
		}
		return folderEntitiesOfJson;
	}
	@Override
	public List<String> findLinkUrlByUserId(long id) {
		return getMenuDao().findLinkUrlByUserId(id);
	}
	
	@Override
	public String findRoleStringByMenuId(Long MenuId){
		String hql = "select r from Role r,MenuOfRole mr where "
				+ MenuId + " = mr.menuId and "
				+ "r.id = mr.roleId";
		Map params = new HashedMap();
		List<Role> roles = getBaseDao().findList(hql, params);
		StringBuffer rolesStr = new StringBuffer();
		rolesStr.append("roles[adm");
		roles.forEach(e -> {rolesStr.append("," + e.getName());});
		rolesStr.append("]");
		return rolesStr.toString();
	}
	/**
	 * 保存菜单
	 */
	@Override
	public void saveMenuInfo() {
		Dispatcher dispatcher = Dispatcher.getInstance();  
        ConfigurationManager configurationManager = dispatcher.getConfigurationManager();  
        Configuration config = configurationManager.getConfiguration();  
        RuntimeConfiguration c  =  config.getRuntimeConfiguration();  
        Map<String,Map<String,ActionConfig>> d = c.getActionConfigs(); 
        JSONObject jsonInfo = JSONObject.fromObject(JSONObject.fromObject(d).get(""));
        Iterator it = jsonInfo.keys();
        while(it.hasNext()){
        	String key = (String) it.next();
        	System.out.println(key);
        	String className = (String) JSONObject.fromObject(jsonInfo.get(key)).get("className");
        	System.out.println(className);
        	Object obj;
			try {
				Class clz = Class.forName(className);
				Method[] methods = clz.getDeclaredMethods();
				for (Method method : methods) {
	                System.out.println(method.getName() + ":");
					if (method.isAnnotationPresent(Jurisdiction.class)) {
						Jurisdiction jurisdiction = method.getAnnotation(Jurisdiction.class);
	                	String linkUrl = key + "_" + method.getName() + ".action";
	                	if(jurisdiction.url() != null && !jurisdiction.url().trim().equals("")){
	                		linkUrl = key + "_" + jurisdiction.url() + ".action";
	                	}
	                	if(this.getMenuDao().findByLinkUrl(linkUrl))
	                		continue;
	                	Menu menu = new Menu();
	                	menu.setCreateStamp(new Date());
	                	menu.setEnable(true);
	                	menu.setModifyStamp(new Date());
	                	menu.setName(jurisdiction.name());
	                	menu.setMenuLevel(jurisdiction.level());
	                	menu.setLinkUrl(linkUrl);
	                	if(jurisdiction.resources())
	                		menu.setMenuLevel(99);
	                	this.getMenuDao().save(menu);
	                }
	            }
				
				
			} catch (ClassNotFoundException e) {
				System.out.println("保存菜单出错，找不到类：" + className);
				return;
			} catch (Exception e) {
				return;
			}
        	
        }
        shiroUtils.updatePermission();
	}
	public void setShiroUtils(FilterChainDefinitionsService shiroUtils) {
		this.shiroUtils = shiroUtils;
	}
	@Override
	public List<Map<String, Object>> findall() {
		return this.getMenuDao().findall();
	}
	@Override
	public boolean isHasChildren(BigInteger b) {
		Session session = this.getSession();
		String sql ="SELECT COUNT(1) from hop_menu m where m.FATHERID_='"+b+"'";
		Query query = session.createSQLQuery(sql);
		BigInteger bigInteger = (BigInteger) query.uniqueResult();
		Long l = bigInteger.longValue();
		if(l>0){
			return true;
		}
		return false;
	}
	@Override
	public List<Map<String, Object>> findmlist(BigInteger bigint) {
		return getMenuDao().findmlist(bigint);
	}
	@Override
	public List<Map<String, Object>> getTopMenu() {
		return getMenuDao().getTopMenu();
	}
	@Override
	public void editemty(String menuname,String mid) {
		 getMenuDao().editemty(menuname,mid);
	}
	@Override
	public void editmenu(String id, String menuname,String mid) {
		getMenuDao().editmeny(id,menuname,mid);
	}
	@Override
	public List<MenuOfRole> findbyroleId(long roleId) {
		String hql = "from MenuOfRole m where m.roleId = :roleId";
		Map params = new HashMap();
		params.put("roleId", roleId);
		return menuOfRoleDao.findList(hql, params);
	}
	
}
