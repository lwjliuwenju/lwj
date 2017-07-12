package com.sj.privilegemanagement.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sj.common.dao.impl.BaseDaoImpl;
import com.sj.common.utils.DateUtil;
import com.sj.common.utils.enums.DateStyle;
import com.sj.privilegemanagement.dao.ProposerDao;
import com.sj.privilegemanagement.entity.Proposer;

public class ProposerDaoImpl extends BaseDaoImpl<Proposer> implements ProposerDao{

	@Override
	public List<Map<String, Object>> searchbypage(String responseUserName,
			String reponseDepId, String userName, String shunxu, String state,
			String proposerDepId, int pageSize, int start,String jieshoubumen,Date startTime, Date endTime) {
		String sql = "SELECT h.*,p.STANDARD_HOUR_,"
				+ "LEFT(timediff(IFNULL(h.RESPONSE_END_TIME_,NOW()),h.CREATE_STAMP_),5) as `TIMEDIFF`"
				+ " FROM hop_proposer h"
				+ " left join hop_project p on p.ID_=h.PROJECT_ID_ WHERE 1=1 ";
		String sql2 = " ORDER BY h.STATUS_,h.CREATE_STAMP_ DESC";
		if(startTime!=null ||endTime!=null){
		endTime = DateUtil.addDay(endTime, 1);
		sql =sql +" and h.CREATE_STAMP_ >'"+DateUtil.DateToString(startTime, DateStyle.YYYY_MM_DD_HH_MM_SS)+"' and h.CREATE_STAMP_<'"+DateUtil.DateToString(endTime, DateStyle.YYYY_MM_DD_HH_MM_SS)+"'";
		}
		if ("1".equals(shunxu)) {
			sql2 = " ORDER BY h.STATUS_";
		}
		if ("2".equals(shunxu)) {
			sql2 = " ORDER BY h.CREATE_STAMP_ DESC";
		}
		if (reponseDepId != null) {
			sql = sql + " and h.REPONSE_DEP_ID_=" + reponseDepId + "";
		}
		if (userName != null && userName != "") {
			sql = sql + " and h.USER_NAME_ like '%" + userName + "%'";
		}
		if (StringUtils.isNotBlank(proposerDepId)) {
			sql = sql + " and h.DEP_ID_ = '" + proposerDepId + "'";
		}
		if(StringUtils.isNotBlank(jieshoubumen)){
			sql = sql + " and h.RESPONSE_DEPNAME_ ='"+jieshoubumen+"'";
		}
		if (responseUserName != "" && responseUserName != null) {
			sql += " and h.ID_ IN (SELECT distinct sp.PROPOSER_ID_ FROM hop_staff_of_proposer AS sp where sp.STAFF_ID_ IN (SELECT s.ID_ FROM hop_staff s where s.NAME_ LIKE '%" + responseUserName + "%'))";
		}
		if (state!=null && !state.equals("0")) {
			sql = sql + " and h.STATUS_ = " + Integer.parseInt(state);
		}
		sql = sql + sql2;
		List<Map<String, Object>> findListBySql = null;
		if(pageSize != 0 && start > -1){
			findListBySql = this.findListBySql(sql, null, start, pageSize);
		}else{
			findListBySql = this.findListBySql(sql);
		}
		return findListBySql;
	}
}
