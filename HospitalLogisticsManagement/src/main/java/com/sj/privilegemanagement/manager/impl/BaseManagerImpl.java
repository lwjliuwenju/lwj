package com.sj.privilegemanagement.manager.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import antlr.StringUtils;

import com.sj.common.dao.BaseDao;
import com.sj.login.UserLoginAction;
import com.sj.privilegemanagement.entity.Menu;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.manager.BaseManager;

import freemarker.template.utility.StringUtil;

public abstract class BaseManagerImpl<T> implements BaseManager<T> {

	public static Logger logger = LoggerFactory.getLogger(UserLoginAction.class);
	public BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public List<T> findAll() {
		return baseDao.findAll();
	}
	public List<T> findAll(String hql, Map<String, Object> userParams) {
		return baseDao.findList(hql, userParams);
	}
	public T findById(Long id) {
		return (T) baseDao.get(id);
	}
	public void deleteById(Long id) {
		baseDao.delete(id);
	}
	public void insert(T t) {
		baseDao.save(t);
	}
	public void update(T t) {
		baseDao.update(t);
	}
	public List<T> findAll(int page,int rows,String hql){
		if(hql == null || "".equals(hql.trim()))
			return baseDao.findList(page, rows);
		return baseDao.findList(hql, null, page, rows);
	}
	public Long findCountNum(String hql) {
		return baseDao.findCountNum(hql);
	}
	public Long findCountNum() {
		return findCountNum(null);
	}
	public Session getSession(){
		return baseDao.getCurrentSession();
	}
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
}
