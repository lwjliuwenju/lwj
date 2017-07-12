package com.sj.privilegemanagement.manager;

import java.util.List;
import java.util.Map;

import com.sj.privilegemanagement.entity.GoodsEntity;
import com.sj.privilegemanagement.entity.Proposer;
import com.sj.privilegemanagement.entity.Staff;

public interface GoodsManager extends BaseManager<GoodsEntity> {

	List<GoodsEntity> findByPid(long proposerId);



}
