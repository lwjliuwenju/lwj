package com.sj.privilegemanagement.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sj.privilegemanagement.entity.Proposer;

public interface ProposerManager extends BaseManager<Proposer> {

	/**
	 * 查询正在运行的申请单
	 * @return
	 */
	List<Proposer> findByStandard();
	/**
	 * 根据筛选条件 查询申请单
	 * @param reponseDepId 响应部门id （-1 或者 null 查询全部）
	 * @param sendDepId 申请部门id （-1 或者 null 查询全部）
	 * @param page 页码 (page he rows  必须都传才起作用，不传默认不分页，查询全部)
	 * @param rows 页面数据条数（传0 代表全部）
	 * @param reponseUser 响应人员名称
	 * @param reponseDep 响应部门名称
	 * @param sendUserName 申请人名称
	 * @param state 状态
	 * @param sendDepName 申请部门名称
	 * @param starttime 开始时间（date类型）
	 * @param endtime 结束时间（date类型）
	 * @return
	 */
	List<Proposer> findByParam(Map<String, String> param,Date starttime,Date endtime);
	/**
	 * 根据筛选条件 查询申请单
	 * @param reponseDepId 响应部门id （-1 或者 null 查询全部）
	 * @param sendDepId 申请部门id （-1 或者 null 查询全部）
	 * @param page 页码 (page he rows  必须都传才起作用，不传默认不分页，查询全部)
	 * @param rows 页面数据条数（传0 代表全部）
	 * @param reponseUser 响应人员名称
	 * @param reponseDep 响应部门名称
	 * @param sendUserName 申请人名称
	 * @param state 状态
	 * @param sendDepName 申请部门名称
	 * @return
	 */
	List<Proposer> findByParam(Map<String, String> param);
	/**
	 * 响应查询
	 * @param pageSize
	 * @param start
	 * @param endTime 
	 * @param startTime 
	 * @return
	 */
	List<Map<String, Object>> searchbypage(int pageSize, int start, Date startTime, Date endTime);
	/**
	 * 根据运输队Id获取响应单
	 * @param goodteamId
	 * @return
	 */
	List<Proposer> findbyreponseDepId(long reponseDepId);
}
