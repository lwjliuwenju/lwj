package com.sj.common.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

public interface BaseDao<T> {
	
	public Session getCurrentSession();
	
	public Serializable save(T o);
	
	public void update(T o);
	
	public void saveOrUpdate(T o);
	
	public T get(Serializable id);
	
	public T get(String hql);
	
	public T get(String hql, Map<String, Object> params);
	
	public void delete(T o);
	
	public void delete(Serializable id);
	
	public void merge(T model);
	
	public Long findCountNum();
	
	public Long findCountNum(String hql);
	
	public List<T> findAll();

	public List<T> findList(int page, int rows);

	public List<T> findList(String hql);
	
	public List<T> findList(String hql, Map<String, Object> params);
	
	public List<T> findList(String hql, Map<String, Object> params, int page, int rows);
	
	public List<T> findTopList(String hql, int topCount);
	
	public  int executeHql(String hql);
	
	public int executeHql(String hql, Map<String, Object> params);
	
	public int executeHql(String hql,Object... objects);
	
	public List<Map<String, Object>> findListBySql(String sql);
	
	public List<Map<String, Object>> findListBySql(String sql,Map<String,Object> params);
	
	public List<Map<String, Object>> findListBySql(String sql, Object... params);
	
	public List<Map<String, Object>> findListBySql(String sql,Map<String,Object> params, int page, int rows);
	
	public <E> List<E> findListBeanBySql(Class<E> beanClass,String sql,Map<String,Object> params, int page, int rows);
	
	public int executeBySql(String sql) throws Exception;
}
