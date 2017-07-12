package com.sj.privilegemanagement.manager;

import java.util.List;

import com.sj.privilegemanagement.entity.StaffOfProposer;

public interface StaffOfProposerManager extends BaseManager<StaffOfProposer> {

	/**
	 * 根据申请单id 获取申请单正在执行的人员
	 * @param pid
	 * @return
	 */
	List<StaffOfProposer> findStaffByProId(long pid);

	/**
	 * 根据申请单id 获取申请单所有执行过得人员
	 * @param id
	 * @return
	 */
	List<StaffOfProposer> findStaffAllByProId(long pid);
    /**
     * 根据申请单id和执行人员id判断执行人员是否第一次派遣
     * @param userId
     * @param proId
     * @return
     */
	StaffOfProposer isexist(String userId, long proId);
   /**
    *  根据执行人员Id查询执行人员正在执行的申请单数量
    * @param staffId
    * @return
    */
	long findbystaffId(Long staffId);
  /**
   * 根据人员ID获取申请单列表
   * @param id
   * @return
   */
	List<StaffOfProposer> findbystaffid(long id);
}
