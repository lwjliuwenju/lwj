package com.sj.privilegemanagement.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sj.common.utils.DateUtil;
import com.sj.privilegemanagement.dao.DepDao;
import com.sj.privilegemanagement.dao.ProjectDao;
import com.sj.privilegemanagement.dao.ProposerDao;
import com.sj.privilegemanagement.entity.Proposer;
import com.sj.privilegemanagement.manager.ProposerManager;

public class ProposerManagerImpl extends BaseManagerImpl<Proposer> implements
		ProposerManager {
	private ProposerDao proposerDao;
	private DepDao depDao;
    private ProjectDao projectDao;
    
	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}

	public void setProposerDao(ProposerDao proposerDao) {
		this.proposerDao = proposerDao;
	}


	@Override
	public List<Proposer> findByStandard() {
		String hql = "from Proposer p where p.status = 2 or p.status = 3";
		return findAll(hql, null);
	}

	private ProposerDao getProposerDao(){
		return (ProposerDao)this.baseDao;
	}
	@Override
	public List<Proposer> findByParam(Map<String,String> param,Date starttime,Date endtime) {
		Map<String, Object> map = new HashMap<String, Object>();
		String shunxu = param.get("shunxu");
		String sql ="select p.* from HOP_PROPOSER p"
				+ " where p.CREATE_STAMP_ > :createStamp and p.CREATE_STAMP_ < :endStamp";
		//响应部门id
		String reponseDepId = param.get("reponseDepId");
		if(StringUtils.isNotBlank(reponseDepId) && !reponseDepId.equals("-1")){
			sql += " and p.REPONSE_DEP_ID_ = :reponseDepId";
			map.put("reponseDepId", Long.valueOf(reponseDepId));
		}
		//申请部门id
		String sendDepId = param.get("sendDepId");
		if(StringUtils.isNotBlank(sendDepId) && !sendDepId.equals("-1")){
			sql += " and p.DEP_ID_ in ("+ sendDepId +")";
		}
		//响应人员名称
		String reponseUser = param.get("reponseUser");
		if(StringUtils.isNotBlank(reponseUser)){
			sql += " and p.ID_ IN (SELECT distinct sp.PROPOSER_ID_ FROM hop_staff_of_proposer AS sp where sp.STAFF_ID_ IN (SELECT s.ID_ FROM hop_staff s where s.NAME_ LIKE :reponseUser))";
			map.put("reponseUser", "%" + reponseUser + "%");
		}
		//申请人名称
		String sendUserName  = param.get("sendUserName");
		if(StringUtils.isNotBlank(sendUserName )){
			sql += " and p.USER_NAME_ like :sendUserName";
			map.put("sendUserName", "%" + sendUserName + "%");
		}
		//状态
		String state  = param.get("state");
		if(StringUtils.isNotBlank(state )&&!"0".equals(state)){
			sql += " and p.STATUS_ = :state";
			map.put("state", Integer.valueOf(state));
		}
		//申请部门名称
		String sendDepName  = param.get("sendDepName");
		if(StringUtils.isNotBlank(sendDepName )){
			sql += " and p.DEP_NAME_ like :sendDepName";
			map.put("sendDepName","%" + sendDepName + "%");
		}
		if(shunxu==null||shunxu.equals("0")){
			 sql+= " order by p.STATUS_,p.CREATE_STAMP_ desc";
			}
			if(StringUtils.isNotBlank(shunxu)&&shunxu.equals("1")){
				sql+= " order by p.STATUS_";
			}
			if(StringUtils.isNotBlank(shunxu)&&shunxu.equals("2")){
				sql+= " order by p.CREATE_STAMP_ desc";
			}
		String stime = param.get("startTime");
		String etime = param.get("endTime");
		Date stringToDate = DateUtil.StringToDate(stime);
		Date stringToDate2 = DateUtil.StringToDate(etime);
		starttime = stringToDate;
		endtime = stringToDate2;
		if(stime==null||stime=="")
			starttime = new Date(0);
		if(etime==null||etime=="")
			endtime =new Date();
		map.put("createStamp", starttime);
		map.put("endStamp", endtime);
		String page = param.get("page");
		String rows = param.get("rows");
		List<Proposer> maps = null;
		if(StringUtils.isBlank(rows) || StringUtils.isBlank(page)){
			page = "0";
			rows = "0";
		}
		maps = getProposerDao().findListBeanBySql(Proposer.class,sql, map, Integer.valueOf(page.toString()), Integer.valueOf(rows.toString()));
		return maps;
	}

	//@Override
	//public List<Map<String,Object>> findMapsByParam(Map<String,String> param,Date starttime,Date endtime) {
	//	Map<String, Object> map = new HashMap<String, Object>();
	//	String shunxu = param.get("shunxu");
	//	String querySql = "select p.*";
	//	String fromSql = " from HOP_PROPOSER p";
	//	String sql =" where p.CREATE_STAMP_ > :createStamp and p.CREATE_STAMP_ < :endStamp";
	//	//响应部门id
	//	String reponseDepId = param.get("reponseDepId");
	//	if(StringUtils.isNotBlank(reponseDepId) && !reponseDepId.equals("-1")){
	//		sql += " and p.REPONSE_DEP_ID_ = :reponseDepId";
	//		map.put("reponseDepId", Long.valueOf(reponseDepId));
	//	}
	//	//申请部门id
	//	String sendDepId = param.get("sendDepId");
	//	if(StringUtils.isNotBlank(sendDepId) && !sendDepId.equals("-1")){
	//		sql += " and p.DEP_ID_ in ("+ sendDepId +")";
	//	}
	//	//响应人员名称
	//	String reponseUser = param.get("reponseUser");
	//	if(StringUtils.isNotBlank(reponseUser)){
	//		sql += " and p.ID_ IN (SELECT distinct sp.PROPOSER_ID_ FROM hop_staff_of_proposer AS sp where sp.STAFF_ID_ IN (SELECT s.ID_ FROM hop_staff s where s.NAME_ LIKE :reponseUser))";
	//		map.put("reponseUser", "%" + reponseUser + "%");
	//	}
	//	//申请人名称
	//	String sendUserName  = param.get("sendUserName");
	//	if(StringUtils.isNotBlank(sendUserName )){
	//		sql += " and p.USER_NAME_ like :sendUserName";
	//		map.put("sendUserName", "%" + sendUserName + "%");
	//	}
	//	//状态
	//	String state  = param.get("state");
	//	if(StringUtils.isNotBlank(state )&&!"0".equals(state)){
	//		sql += " and p.STATUS_ = :state";
	//		map.put("state", Integer.valueOf(state));
	//	}
	//	//申请部门名称
	//	String sendDepName  = param.get("sendDepName");
	//	if(StringUtils.isNotBlank(sendDepName )){
	//		sql += " and p.DEP_NAME_ like :sendDepName";
	//		map.put("sendDepName","%" + sendDepName + "%");
	//	}
	//	if(shunxu==null||shunxu.equals("0")){
	//		sql+= " order by p.STATUS_,p.CREATE_STAMP_ desc";
	//	}
	//	if(StringUtils.isNotBlank(shunxu)&&shunxu.equals("1")){
	//		sql+= " order by p.STATUS_";
	//	}
	//	if(StringUtils.isNotBlank(shunxu)&&shunxu.equals("2")){
	//		sql+= " order by p.CREATE_STAMP_ desc";
	//	}
	//	String stime = param.get("startTime");
	//	String etime = param.get("endTime");
	//	Date stringToDate = DateUtil.StringToDate(stime);
	//	Date stringToDate2 = DateUtil.StringToDate(etime);
	//	starttime = stringToDate;
	//	endtime = stringToDate2;
	//	if(stime==null||stime=="")
	//		starttime = new Date(0);
	//	if(etime==null||etime=="")
	//		endtime =new Date();
	//	map.put("createStamp", starttime);
	//	map.put("endStamp", endtime);
	//	String page = param.get("page");
	//	String rows = param.get("rows");
	//	List<Map<String,Object>> maps = null;
	//	if(StringUtils.isBlank(rows) || StringUtils.isBlank(page)){
	//		page = "0";
	//		rows = "0";
	//	}
	//	maps = getProposerDao().findListBySql(querySql + sql, map, Integer.valueOf(page.toString()), Integer.valueOf(rows.toString()));
	//	return maps;
	//}
	
	@Override
	public List<Map<String, Object>> searchbypage(int pageSize, int start,Date startTime, Date endTime) {
		String responseUserName = getRequest().getParameter("responseUserName");
		String reponseDepId = getRequest().getParameter("reponseDepId");
		String userName = getRequest().getParameter("userName");
		String shunxu = getRequest().getParameter("shunxu");
		String state = getRequest().getParameter("state");
		String proposerDepId = getRequest().getParameter("proposerDepId");
		String reponseDepName = getRequest().getParameter("reponseDepName");
		return getProposerDao().searchbypage(responseUserName, reponseDepId,
				userName, shunxu, state, proposerDepId, pageSize, start,
				reponseDepName,startTime,endTime);
	}
	@Override
	public List<Proposer> findByParam(Map<String, String> param) {
		return this.findByParam(param, null, null);
	}

	@Override
	public List<Proposer> findbyreponseDepId(long reponseDepId) {
		String hql = null;
		if(reponseDepId==0){
			hql ="from Proposer p";
			return findAll(hql, null);
		}else{
			hql = "from Proposer p where p.reponseDepId= :reponseDepId";
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("reponseDepId",reponseDepId);
			return findAll(hql, map);
		}
	}

}
