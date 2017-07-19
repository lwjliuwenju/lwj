package com.sj.privilegemanagement.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.User;

public interface DepManager extends BaseManager<DepEntity>{

	/**
	 * 设置科长
	 * @param username
	 * @param id
	 * @param userid
	 */
	void editkezhang(String username, String[] id, String userid);

	/**
	 * 添加科员
	 * @param keyuanName
	 * @param depId
	 */
	void addkeyuan(String keyuanName, String depId);
	/**
	 * 根据userid 查询部门(userid 0 代表admin)
	 * @param userId
	 * @return
	 */
	List<DepEntity> findByUserId(Long userId);

	/**
	 * 根据人员id查询工作次数
	 * @param endTime 
	 * @param startTime 
	 * @param valueOf
	 * @return
	 */
	Map<String, Object> getWorkNumberByRYId(Long ryId, Date startTime, Date endTime);

	List<DepEntity> findByTransport();

	/**
	 * 根据部门id获取科长
	 * @return
	 */
	List<Long> findUserIdByDepId(long depId);

	List<DepEntity> findbyparam(Map<String, String> param);
}
