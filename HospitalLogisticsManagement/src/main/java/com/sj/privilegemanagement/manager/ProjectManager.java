package com.sj.privilegemanagement.manager;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.sj.privilegemanagement.entity.ProjectEntity;

public interface ProjectManager extends BaseManager<ProjectEntity> {

	/**
	 * 根据部门id 查询服务
	 * @param Id
	 * @return
	 */
	List<ProjectEntity> findByDepId(long Id);
	/**
	 * 根据部门id 查询服务
	 * @param Id
	 * @return
	 */
	List<ProjectEntity> findByDepId(long Id, int page, int rows);
	/**
	 * 服务登记
	 * @param id
	 */
	void editdengji(String id);
	/**
	 * 根据服务名获取服务
	 * @param projectName
	 * @return
	 */
	List<ProjectEntity> findbyproName(String projectName);
	/**
	 * 根据fatherid和depid获取所有的一级菜单
	 * @param depId
	 * @param fatherid
	 * @return
	 */
	List<Map<String, Object>> findbyfatherId(long depId, String fatherid);
}
