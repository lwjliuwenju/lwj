package com.sj.privilegemanagement.manager;

import java.util.List;
import java.util.Map;

import com.sj.privilegemanagement.entity.Supplies;
import com.sj.privilegemanagement.entity.SuppliesOfproposer;

public interface SuppliesProposerManager extends BaseManager<SuppliesOfproposer>{

	/**
	 * 添加耗材
	 */
	void saveSupplies();

	/**
	 * 根据申请单id查询 耗材
	 * @param pid
	 * @return
	 */
	List<Supplies> findbypid(long pid);
	/**
	 * 耗材响应 根据筛选条件
	 * @param submitId
	 * @param reponsePerson
	 * @param status
	 * @param dep
	 * @param page
	 * @param rows
	 * @param reponseDep
	 * @param paixu 
	 * @return
	 */
	List<SuppliesOfproposer> findbyParam(String submitId, String reponsePerson, String status,
			String dep, int page, int rows, String reponseDep, String paixu);

}
