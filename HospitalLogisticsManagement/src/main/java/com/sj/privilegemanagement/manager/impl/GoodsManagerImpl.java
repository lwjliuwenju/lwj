package com.sj.privilegemanagement.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sj.privilegemanagement.dao.GoodsDao;
import com.sj.privilegemanagement.entity.GoodsEntity;
import com.sj.privilegemanagement.entity.Proposer;
import com.sj.privilegemanagement.manager.GoodsManager;

public class GoodsManagerImpl extends BaseManagerImpl<GoodsEntity> implements GoodsManager{
		private GoodsDao goodsDao;

		public GoodsDao getGoodsDao() {
			return goodsDao;
		}

		public void setGoodsDao(GoodsDao goodsDao) {
			this.goodsDao = goodsDao;
		}

		@Override
		public List<GoodsEntity> findByPid(long proposerId) {
			String hql = "select g "
					+"from GoodsEntity g "
					+"where g.proposerId = :proposerId ";
			Map<String,Object> map = new HashMap();
			map.put("proposerId", proposerId);
			return findAll(hql, map);
		}


}
