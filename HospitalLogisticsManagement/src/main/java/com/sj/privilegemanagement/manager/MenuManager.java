package com.sj.privilegemanagement.manager;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.sj.privilegemanagement.dao.MenuDao;
import com.sj.privilegemanagement.entity.Menu;
import com.sj.privilegemanagement.entity.MenuOfRole;

public interface MenuManager extends BaseManager<Menu> {

	public List<Menu> findByUserId(Long string);

	public void setMenuOfFloder();

	public JSONArray findMenuTreeByUserId(Long id);

	public List<String> findLinkUrlByUserId(long id);
	
	public String findRoleStringByMenuId(Long MenuId);

	public void saveMenuInfo();
	
	public MenuDao getMenuDao();

	public List<Map<String, Object>> findall();

	public boolean isHasChildren(BigInteger b);

	public List<Map<String, Object>> findmlist(BigInteger bigint);

	public List<Map<String, Object>> getTopMenu();

	public void editemty(String menuname, String mid);

	public void editmenu(String id, String menuname, String mid);

	public List<MenuOfRole> findbyroleId(long roleId);
}
