package com.sj.privilegemanagement.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import com.sj.privilegemanagement.dao.DepDao;
import com.sj.privilegemanagement.dao.StaffDao;
import com.sj.privilegemanagement.dao.UserDao;
import com.sj.privilegemanagement.dao.UserOfDepDao;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.Proposer;
import com.sj.privilegemanagement.entity.Staff;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.entity.UserOfDepAdmin;
import com.sj.privilegemanagement.manager.DepManager;

public class DepManagerImpl extends BaseManagerImpl<DepEntity> implements
		DepManager {
	private UserDao userDao;
	private DepDao depDao;
	private StaffDao staffDao;
	private UserOfDepDao userOfDepDao;

	/*
	 * @Override public List<DepEntity> findAllAndUser() { return
	 * getDepDao().findAllAndUser(); }
	 */

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public void setUserOfDepDao(UserOfDepDao userOfDepDao) {
		this.userOfDepDao = userOfDepDao;
	}
	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}

	@Override
	public void editkezhang(String username, String[] userids, String id) {
		userOfDepDao.deleteByDepId(id);
		for (String i : userids) {
			UserOfDepAdmin userOfDepAdmin = new UserOfDepAdmin();
			userOfDepAdmin.setCreateStamp(new Date());
			userOfDepAdmin.setDepId(Long.valueOf(id));
			userOfDepAdmin.setEnable(true);
			userOfDepAdmin.setModifyStamp(new Date());
			userOfDepAdmin.setUserId(Long.valueOf(i));
			userOfDepDao.save(userOfDepAdmin);
		}
	}

	@Override
	public List<DepEntity> findByUserId(Long uid) {
		return depDao.findDepByUserId(uid);
	}

	@Override
	public void addkeyuan(String keyuanName, String depId) {
		Date date = new Date();
		Staff staff = new Staff();
		staff.setName(keyuanName);
		staff.setDeptId(Long.valueOf(depId));
		staff.setCreateStamp(date);
		staff.setModifyStamp(date);
		staff.setNumber(0);
		staff.setStatus(0);
		staff.setEnable(true);
		staffDao.save(staff);
	}

	@Override
	public Map<String, Object> getWorkNumberByRYId(Long ryId, Date startTime,
			Date endTime) {
		Map<String, Object> maps = new HashMap<String, Object>();
		List<Map<String, Object>> workNumberByRYIds = depDao
				.getWorkNumberByRYId(ryId, startTime, endTime);
		// 工作次数
		Long workNumber = 0l;
		// 优 评价次数
		Long youNumber = 0l;
		// 良
		Long liangNumber = 0l;
		// 中
		Long centerNumber = 0l;
		// 差
		Long chaNumber = 0l;
		// 返修
		Long fxNumber = 0l;
		// 通过 审核
		Long SHexamine = 0l;
		Long number = 0l;
		//分值
		int grade = 0;
		//未完成次数
		int wwcNumber = 0;
		for (Map<String, Object> map : workNumberByRYIds) {
			if(map.get("COMPLETE_").toString().equals("0")){
				wwcNumber++;
			}
			if(map.get("COMPLETE_").toString().equals("1") && map.get("GRADE_") != null){
				grade += Integer.valueOf(map.get("GRADE_").toString());
			}
			
			String str = null;
			workNumber++;
			str = String.valueOf(map.get("APPRAISE_"));
			if (StringUtils.isBlank(str) || "0".equals(str))
				str = "5";
			if (str.equals("1"))
				youNumber++;
			if (str.equals("2"))
				liangNumber++;
			if (str.equals("3"))
				centerNumber++;
			if (str.equals("4"))
				chaNumber++;
			number += 5l - Long.valueOf(str);
			str = String.valueOf(map.get("REPAIR_FLAG_"));
			if (str != null && str.equals("1"))
				fxNumber++;
			str = String.valueOf(map.get("CHECKFALG_"));
			if (str != null && str.equals("1"))
				SHexamine++;
		}
		// 审核通过率
		double SHexamineL = 0;
		// 平均
		double PJNumber = 0;
		// 优评率
		double youLv = 0;
		// 良评率
		double liangLv = 0;
		// 中评率
		double centerLv = 0;
		// 差评率
		double chaLv = 0;

		if (!workNumber.equals(0l) && workNumber != null) {
			PJNumber = (int) ((double) number / (double) workNumber * 100d / 4);
			SHexamineL = (int) ((double) SHexamine / (double) workNumber * 100d);
			youLv = (int) ((double) youNumber / (double) workNumber * 100d);
			liangLv = (int) ((double) liangNumber / (double) workNumber * 100d);
			centerLv = (int) ((double) centerNumber / (double) workNumber * 100d);
			chaLv = (int) ((double) chaLv / (double) workNumber * 100d);
		}
		maps.put("chaLv", chaLv);
		maps.put("centerLv", centerLv);
		maps.put("liangLv", liangLv);
		maps.put("youLv", youLv);
		maps.put("goodLv", youLv + liangLv);
		maps.put("workNumber", workNumber);
		maps.put("YouNumber", youNumber);
		maps.put("LiangNumber", liangNumber);
		maps.put("centerNumber", centerNumber);
		maps.put("chaNumber", chaNumber);
		maps.put("goodNumber", youNumber + liangNumber);
		maps.put("PJNumber", PJNumber);
		maps.put("fxNumber", fxNumber);
		maps.put("SHexamineL", SHexamineL);
		maps.put("grade", grade);
		maps.put("wwcNumber", wwcNumber);
		return maps;
	}

	@Override
	public List<DepEntity> findByTransport() {
		return depDao.findByTransport();
	}
	@Override
	public List<Long> findUserIdByDepId(long depId) {
		String hql = "from UserOfDepAdmin ud where ud.depId = :depId";
		Map<String, Object> params = new HashedMap();
		params.put("depId", depId);
		List<UserOfDepAdmin> UserOfDepAdmins = userOfDepDao.findList(hql, params);
		List<Long> list = new ArrayList<Long>();
		if(UserOfDepAdmins != null)
		for (UserOfDepAdmin userOfDepAdmin : UserOfDepAdmins) {
			list.add(userOfDepAdmin.getUserId());
		}
		return list;
	}
	@Override
	public List<DepEntity> findbyparam(Map<String, String> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		String depname = param.get("depname");
		String sql ="SELECT * from hop_dep d where d.ENABLE_=1";
		if(StringUtils.isNotBlank(depname)){
			sql+=" and d.DEP_NAME_ LIKE :depname";
			map.put("depname", "%" + depname + "%");
		}
		String page = param.get("page");
		String rows = param.get("rows");
		List<DepEntity> maps = null;
		if(StringUtils.isBlank(rows) || StringUtils.isBlank(page)){
			page = "0";
			rows = "0";
		}
		maps = depDao.findListBeanBySql(DepEntity.class,sql, map, Integer.valueOf(page.toString()), Integer.valueOf(rows.toString()));
		return maps;
	}

}
