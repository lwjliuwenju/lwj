package com.sj.privilegemanagement.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sj.common.dao.BaseDao;
import com.sj.privilegemanagement.entity.Proposer;

public interface ProposerDao extends BaseDao<Proposer> {

	List<Map<String, Object>> searchbypage(String responseUserName,
			String reponseDepId, String userName, String shunxu, String state,
			String proposerDepId, int pageSize, int start, String jieshoubumen, Date startTime, Date endTime);
}
