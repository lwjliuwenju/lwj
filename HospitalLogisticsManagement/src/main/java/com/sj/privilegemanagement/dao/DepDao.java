package com.sj.privilegemanagement.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sj.common.dao.BaseDao;
import com.sj.privilegemanagement.entity.DepEntity;

public interface DepDao extends BaseDao<DepEntity>{

	/**
	 * 根据用户id获取科室信息
	 * @param userId
	 * @return
	 */
	List<DepEntity> findDepByUserId(Long userId);

	/**
	 * 根据人员id获取工作次数
	 * --->参数为人员id*10 + 日期类型
	 * 日期类型：1 今天 2 本周 3 本月
	 * @param ryId * 10
	 * @param endTime 
	 * @param startTime 
	 * @return
	 */
	List<Map<String, Object>> getWorkNumberByRYId(Long ryId, Date startTime, Date endTime);

	List<DepEntity> findByTransport();

}
