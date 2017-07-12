package com.sj.privilegemanagement.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.CommonUtil;
import com.sj.common.utils.JSONUtils;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.comparator.MapComparator;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.ProjectEntity;
import com.sj.privilegemanagement.entity.Proposer;
import com.sj.privilegemanagement.entity.Staff;
import com.sj.privilegemanagement.entity.StaffOfProposer;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.entity.VO.RenyuanGongzuoVO;
import com.sj.privilegemanagement.manager.DepManager;
import com.sj.privilegemanagement.manager.ProjectManager;
import com.sj.privilegemanagement.manager.ProposerManager;
import com.sj.privilegemanagement.manager.StaffManager;
import com.sj.privilegemanagement.manager.StaffOfProposerManager;
import com.sj.privilegemanagement.manager.UserManager;

public class DataAction extends BaseAction<UserManager, User> {

	private DepManager depManager;
	private ProposerManager proposerManager;
	private StaffManager staffManager;
	private StaffOfProposerManager staffOfProposerManager;
	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public void setStaffOfProposerManager(
			StaffOfProposerManager staffOfProposerManager) {
		this.staffOfProposerManager = staffOfProposerManager;
	}

	public void setStaffManager(StaffManager staffManager) {
		this.staffManager = staffManager;
	}

	public DepManager getDepManager() {
		return depManager;
	}

	public void setDepManager(DepManager depManager) {
		this.depManager = depManager;
	}

	public ProposerManager getProposerManager() {
		return proposerManager;
	}

	public void setProposerManager(ProposerManager proposerManager) {
		this.proposerManager = proposerManager;
	}

	private static final long serialVersionUID = 1L;

	/**
	 * 人员工作量统计分析
	 * 
	 * @throws IOException
	 */
	@Jurisdiction(name = "人员工作量分析", level = -1)
	public String personnelWorkloadData() throws IOException {
		// 判断如果into的话进行跳转
		String into = this.getParameter("into");
		if (into != null && "true".equals(into)) {
			setUrl("personnelWorkloadData");
			return SUCCESS;
		}
		long depId = this.getIntegerParameter("id");
		Date startTime = this.getEasyUiDateParameter("startTime");
		Date endTime = this.getEasyUiDateParameter("endTime");
		List<Staff> staffs = staffManager.findStaffByDepId(depId);
		// 人员数据分析
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (Staff staff : staffs) {
			Map<String, Object> map = new HashedMap();
			// 人员ID
			String ryId = String.valueOf(staff.getId());
			// 人员名称
			String ryName = String.valueOf(staff.getName());
			// 部门名称
			String depName = "";
			if (staff.getDeptId() == 0) {
				depName = "admin";
			} else {
				depName = String.valueOf(depManager.findById(staff.getDeptId())
						.getDepName());
			}
			map.put("ryId", ryId);
			map.put("ryName", ryName);
			map.put("depName", depName);
			// 人员工作次数
			map.putAll(depManager.getWorkNumberByRYId(Long.valueOf(ryId),
					startTime, endTime));
			maps.add(map);
		}
		String down = this.getParameter("down");
		if (StringUtils.isNotBlank(down) && "true".equals(down)) {
			List<RenyuanGongzuoVO> list = new ArrayList<RenyuanGongzuoVO>();
			if (maps.size() > 0 && maps != null) {
				for (Map<String, Object> map : maps) {
					RenyuanGongzuoVO renyuanGongzuoVO = new RenyuanGongzuoVO();
					renyuanGongzuoVO.setXingMing(getStringIsNull(map
							.get("ryName")));
					renyuanGongzuoVO.setBuMen(getStringIsNull(map
							.get("depName")));
					renyuanGongzuoVO.setGongzuoNumber(Integer.parseInt(map.get(
							"workNumber").toString()));
					renyuanGongzuoVO.setYou(Integer.valueOf(map
							.get("YouNumber").toString()));
					renyuanGongzuoVO.setLiang(Integer.valueOf(map.get(
							"LiangNumber").toString()));
					renyuanGongzuoVO.setZhong(Integer.valueOf(map.get(
							"centerNumber").toString()));
					renyuanGongzuoVO.setCha(Integer.valueOf(map
							.get("chaNumber").toString()));
					renyuanGongzuoVO.setZonghe(Double.valueOf(
							map.get("PJNumber").toString()).intValue());
					renyuanGongzuoVO.setFanxiu(Integer.valueOf(map.get(
							"fxNumber").toString()));
					renyuanGongzuoVO.setTongguolv(Double.valueOf(
							map.get("SHexamineL").toString()).intValue());
					if (map.get("grade") == null) {
						renyuanGongzuoVO.setFenshu(0);
					} else {
						String string = map.get("grade").toString();
						renyuanGongzuoVO.setFenshu(Integer.valueOf(string));
					}
					list.add(renyuanGongzuoVO);
				}
			}
			String filename = "人员工作量.xls";
			getResponse().setContentType("application/x-msexcel");
			getResponse().setHeader("content-type", "application/x-msexcel");
			getResponse().setHeader("content-disposition",
					"inline;filename=" + URLEncoder.encode(filename, "UTF-8"));

			OutputStream out = getResponse().getOutputStream();
			CommonUtil.outExcle(list, out);
			return null;
		}

		// MapComparator mapComparator = new MapComparator();
		// MapComparator.mapParam.put("workNumber", true);
		// Collections.sort(maps, mapComparator);
		// maps.sort((Map<String,Object> a,Map<String,Object> b) ->
		// (Integer.valueOf(a.get("workNumber").toString()) >
		// Integer.valueOf(b.get("workNumber").toString()) ? -1 : 1));
		this.setAttribute("rows", maps);
		this.setAttribute("total", maps.size());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rows", maps);
		jsonObject.put("total", maps.size());
		AjaxUtils.ajaxResponse(jsonObject.toString());
		return null;
	}

	/**
	 * 人员绩效统计分析
	 */
	// @Jurisdiction(name="人员绩效",level=-1)
	public String personnelAchievementsData() {
		setUrl("personnelAchievementsData");
		return SUCCESS;
	}

	public String depSendData() {
		setUrl("depSendData");
		return SUCCESS;
	}

	private String getStringIsNull(Object object) {
		if (object != null)
			return object.toString();
		return "";
	}

	/**
	 * 科室响应单量数据 元冬冬 2017年5月22日14:37:44
	 */
	@Jurisdiction(name = "科室响应量分析", level = -1, url = "depSendData")
	public void findAll() {
		Long depId = this.getIntegerParameter("id").longValue();
		if (depId == null)
			depId = 0l;
		String s = this.getParameter("starttime");
		String e = this.getParameter("endtime");
		String shunxu = this.getParameter("shunxu");
		MapComparator mapComparator = new MapComparator();
		NumberFormat nf = new DecimalFormat("0.00");
		List<DepEntity> depEntities = new ArrayList<DepEntity>();
		if (depId == 0)
			depEntities.addAll(depManager.findAll());
		else
			depEntities.add(depManager.findById(depId));
		List<Map<String, Object>> alist = new ArrayList();
		if (depEntities != null)
			for (DepEntity depEntitie : depEntities) {
				int haoping = 0;
				int chaping = 0;
				int zhongping = 0;
				int waixiu = 0;
				int fanxiu = 0;
				int hp = 0;
				int cp = 0;
				int zp = 0;
				int wp = 0;
				int fp = 0;
				Map<String, String> param = new HashMap<String, String>();
				param.put("reponseDepId", String.valueOf(depEntitie.getId()));
				param.put("startTime", s);
				param.put("endTime", e);
				List<Proposer> proposers = proposerManager.findByParam(param);
				int xiangyingcishu = proposers.size();
				if (proposers.size() > 0) {
					for (Proposer proposer : proposers) {
						if (proposer.getOutSourcIngFlag() != null)
							if (proposer.getOutSourcIngFlag().equals("1"))
								waixiu++;
						if (proposer.getRepairFlag() == 1)
							fanxiu++;
						switch (proposer.getAppraise()) {
						case 1:
						case 2:
							haoping++;
							break;
						case 3:
							zhongping++;
							break;
						case 4:
							chaping++;
							break;
						default:
							break;
						}
					}
					hp = (int) ((double) haoping / (double) xiangyingcishu * 100);
					cp = (int) ((double) chaping / (double) xiangyingcishu * 100);
					wp = (int) ((double) waixiu / (double) xiangyingcishu * 100);
					zp = (int) ((double) zhongping / (double) xiangyingcishu * 100);
					fp = (int) ((double) fanxiu / (double) xiangyingcishu * 100);
				}
				Map<String, Object> map = new HashMap();
				map.put("depName", depEntitie.getDepName());
				map.put("xiangyingcishu", xiangyingcishu);
				map.put("haoping", haoping);
				map.put("chaping", chaping);
				map.put("zhongping", zhongping);
				map.put("fanxiu", fanxiu);
				map.put("waixiu", waixiu);
				map.put("haopinglv", hp);
				map.put("chapinglv", cp);
				map.put("zhongpinglv", zp);
				map.put("waixiulv", wp);
				map.put("fanxiulv", fp);
				alist.add(map);
			}
		MapComparator.mapParam.clear();
		if ("1".equals(shunxu)) {
			MapComparator.mapParam.put("xiangyingcishu", true);
			Collections.sort(alist, new MapComparator());
		}
		if ("2".equals(shunxu)) {
			MapComparator.mapParam.put("haoping", true);
			Collections.sort(alist, new MapComparator());
		}
		if ("3".equals(shunxu)) {
			MapComparator.mapParam.put("zhongping", true);
			Collections.sort(alist, new MapComparator());
		}
		if ("4".equals(shunxu)) {
			MapComparator.mapParam.put("chaping", true);
			Collections.sort(alist, new MapComparator());
		}
		if ("5".equals(shunxu)) {
			MapComparator.mapParam.put("fanxiu", true);
			Collections.sort(alist, new MapComparator());
		}
		if ("6".equals(shunxu)) {
			MapComparator.mapParam.put("waixiu", true);
			Collections.sort(alist, new MapComparator());
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rows", JSONUtils.toJSONString(alist));
		jsonObject.put("total", alist.size());
		AjaxUtils.ajaxResponse(jsonObject.toString());
	}

	public String depRepairData() {
		setUrl("depRepairData");
		return SUCCESS;
	}

	/**
	 * 科室申请量分类统计分析 2017年5月25日15:59:38 元冬冬
	 */
	@Jurisdiction(name = "科室申请量分析", level = -1, url = "depRepairData")
	public void findalllist() {
		String depId = this.getParameter("id");
		String page = getRequest().getParameter("page");
		String rows = getRequest().getParameter("rows");
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		List<DepEntity> depEntitys = new ArrayList<DepEntity>();
		if (StringUtils.isBlank(depId) || "0".equals(depId)) {
			depEntitys = depManager.findAll();
			DepEntity depEntity = new DepEntity();
			depEntity.setId(0);
			depEntitys.add(depEntity);
		} else {
			String hql = "from DepEntity d where d.id in (" + depId + ")";
			depEntitys = depManager.findAll(hql, null);
		}
		JSONArray jsonArray = new JSONArray();
		Map<String, Integer> map = new HashMap<String, Integer>();
		long size = 0l;
		for (DepEntity depEntity : depEntitys) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("sendDepId", String.valueOf(depEntity.getId()));
			param.put("depId", depId);
			param.put("page", page);
			param.put("rows", rows);
			param.put("startTime", startTime);
			param.put("endTime", endTime);
			List<Proposer> proposers = proposerManager.findByParam(param);
			for (Proposer proposer : proposers) {
				size++;
				String projectName = null;
				if (proposer.getProjectId() != null) {
					ProjectEntity project = projectManager.findById(proposer
							.getProjectId());
					projectName = proposer.getProjectName() + ":::"
							+ project.getGrade() + ":::"
							+ project.getStandardHour();
				}
				if (StringUtils.isBlank(projectName))
					projectName = "未选择服务";
				Integer num = map.get(projectName);
				if (num == null) {
					map.put(projectName, 1);
					continue;
				}
				map.put(projectName, num + 1);
			}
		}
		long fenzhiNum = 0;
		for (Entry entry : map.entrySet()) {
			JSONObject jsonObject = new JSONObject();
			String key = entry.getKey().toString();
			String[] split = key.split(":::");
			String projectName = split[0];
			String fenzhi = null;
			if (split.length > 1)
				fenzhi = split[1];
			String bzgs = null;
			if (split.length > 2)
				bzgs = split[2];
			jsonObject.put("projectName", projectName);
			jsonObject.put("fenzhi", fenzhi);
			jsonObject.put("bzgs", bzgs);
			jsonObject.put("num", entry.getValue());
			if (fenzhi != null
					&& StringUtils.isNotBlank(entry.getValue().toString()))
				fenzhiNum += Long.valueOf(fenzhi)
						* Long.valueOf(entry.getValue().toString());
			jsonArray.add(jsonObject);
		}
		JSONObject jsonNum = new JSONObject();
		jsonNum.put("projectName", "总计");
		jsonNum.put("num", size);
		jsonNum.put("fenzhi", fenzhiNum);
		jsonArray.add(jsonNum);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rows", jsonArray);
		jsonObject.put("total", jsonArray.size());
		AjaxUtils.ajaxResponse(jsonObject.toString());
	}
}
