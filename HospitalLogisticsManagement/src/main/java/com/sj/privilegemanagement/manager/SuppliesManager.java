package com.sj.privilegemanagement.manager;

import java.util.List;
import java.util.Map;

import com.sj.privilegemanagement.entity.Supplies;

public interface SuppliesManager extends BaseManager<Supplies> {

	/**
	 * 根据部门id 查询耗材信息
	 * @param depId
	 * @return
	 */
	List<Supplies> findByDepId(String depId);

	/**
	 * 根据申请单id查询 耗材 耗材详情
	 * @param pid
	 * @return
	 */
	public List<Map<String, Object>> findByProId(long pid);
}
