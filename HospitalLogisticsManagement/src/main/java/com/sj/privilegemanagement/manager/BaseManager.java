package com.sj.privilegemanagement.manager;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

public interface BaseManager<T> {

	public List<T> findAll();
	public List<T> findAll(int page,int rows,String hql);
	public List<T> findAll(String hql, Map<String, Object> Params);
	public T findById(Long id);
	public void deleteById(Long id);
	public void insert(T t);
	public void update(T t);
	public Session getSession();
	/**
	 * 查询数据条数（根据条件）
	 * @param hql
	 * @return
	 */
	public Long findCountNum(String hql);
	/**
	 * 查询数据条数
	 * @return
	 */
	public Long findCountNum();
}
