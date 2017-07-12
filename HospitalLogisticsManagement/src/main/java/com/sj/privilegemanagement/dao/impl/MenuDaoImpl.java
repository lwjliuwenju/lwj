package com.sj.privilegemanagement.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;

import com.sj.common.dao.impl.BaseDaoImpl;
import com.sj.privilegemanagement.dao.MenuDao;
import com.sj.privilegemanagement.entity.FolderEntity;
import com.sj.privilegemanagement.entity.Menu;

public class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDao {

	@Override
	public List<Menu> findTreeByUserId(Long string) {
		String hql = "select m from Menu as "
				+ "m,UserOfRole as u,MenuOfRole as r "
				+ "where u.userId=" + string + " and "
				+ "u.roleId=r.roleId and "
				+ "r.menuId=m.id and m.folderId=null and "
				+ "m.menuLevel = -1";
		if(string == null || string.equals(0l)){
			hql = "from Menu as m "
					+ "where m.folderId=null and m.menuLevel = -1";
		}
		
		List<Menu> list = this.findList(hql);
		return list;
	}
	/**
	 * 根据文件夹ID和用户id获取菜单列表
	 */
	@Override
	public List<Menu> findByUserIdAndFolId(Long id, long l) {
		String hql = "select m from "
				+ "Menu as m "
				+ "where "
				+ "m.folderId = " + l  
				+ " and m.menuLevel = -1";
		if(id != null && id != 0){
			hql = "select m from "
					+ "Menu as m,"
					+ "UserOfRole as u,"
					+ "MenuOfRole as r "
					+ "where "
					+ "m.folderId = " + l
					+ " and u.userId=" + id + " and "
					+ "u.roleId=r.roleId and "
					+ "r.menuId=m.id"
					+ " and m.menuLevel = -1";
		}
		List<Menu> list = this.findList(hql);
		return list;
	}
	@Override
	public List<String> findLinkUrlByUserId(long id) {
		List<Menu> list = this.findList("select m from Menu as "
				+ "m,UserOfRole as u,MenuOfRole as r "
				+ "where u.userId=" + id + " and "
				+ "u.roleId=r.roleId and "
				+ "r.menuId=m.id");
		List<String> LinkUrls = new ArrayList<String>();
		for(Menu menu : list){
			LinkUrls.add(menu.getLinkUrl());
		}
		return LinkUrls;
	}
	@Override
	public boolean findByLinkUrl(String linkUrl) {
		String hql = "from Menu m where m.linkUrl = :linkUrl";
		Map params = new HashedMap();
		params.put("linkUrl", linkUrl);
		List<Menu> menus = this.findList(hql, params);
		if(menus.size() > 0)
			return true;
		return false;
	}
	@Override
	public List<Map<String, Object>> findall() {
		String sql ="SELECT e.* FROM hop_folder_entity e";
		Session session = this.getCurrentSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
	    List<Map<String, Object>> list = query.list();
		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> findmlist(BigInteger bigint) {
		int i =-1;
		String sql = "SELECT * FROM hop_menu m WHERE m.FOLDER_ID_='"+bigint+"' and m.MENU_LEVEL_='"+i+"'";
		Session session = this.getCurrentSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
	    List<Map<String, Object>> list = query.list();
		if(list!=null && list.size()>0){
			return list;
		}
		return new ArrayList<Map<String,Object>>() ;
	}
	@Override
	public List<Map<String, Object>> getTopMenu() {
		String sql ="SELECT e.ID_,e.FOLDER_NAME_ FROM hop_folder_entity e ";
		Session session = this.getCurrentSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
	    List<Map<String, Object>> list = query.list();
		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	@Override
	public void editemty(String menuname,String mid) {
		 BigInteger b= new BigInteger(mid);
		 String sql ="UPDATE hop_folder_entity e SET e.FOLDER_NAME_='"+menuname+"' where e.ID_='"+mid+"'";
	     Session session = this.currentSession();
	     SQLQuery query = session.createSQLQuery(sql);
	     query.executeUpdate();
	}
	@Override
	public void editmeny(String id, String menuname,String mid) {
		BigInteger b =new BigInteger(id);
		BigInteger c =new BigInteger(mid);
		int a =-1;
		String sql ="UPDATE hop_menu m SET m.MENU_LEVEL_='"+a+"',m.FOLDER_ID_='"+b+"',m.NAME_='"+menuname+"' where m.ID_='"+c+"'";
		Session session = this.currentSession();
	    SQLQuery query = session.createSQLQuery(sql);
	    query.executeUpdate();
	}

	

}
