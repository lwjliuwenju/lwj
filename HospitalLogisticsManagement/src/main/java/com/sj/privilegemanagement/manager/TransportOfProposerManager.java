package com.sj.privilegemanagement.manager;

import java.util.List;

import com.sj.privilegemanagement.entity.TransportOfProposer;

public interface TransportOfProposerManager extends BaseManager<TransportOfProposer> {
    /**
     * 根据申请单Id获取运输部门人员列表
     * @param proId
     * @return
     */
	List<TransportOfProposer> findbyProId(Long proId);
	  /**
	    *  根据执行人员Id查询执行人员正在执行的申请单数量
	    * @param staffId
	    * @return
	    */
	long findbystaffId(Long staffId);
     
}
