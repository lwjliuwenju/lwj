package com.sj.privilegemanagement.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.metamodel.domain.Superclass;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.JSONUtils;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.Supplies;
import com.sj.privilegemanagement.manager.DepManager;
import com.sj.privilegemanagement.manager.SuppliesManager;
import com.sj.privilegemanagement.manager.UserManager;

public class SuppliesAction extends BaseAction<SuppliesManager, Supplies> {

	private UserManager userManager;
	private SuppliesManager suppliesManager;
	
	public void setSuppliesManager(SuppliesManager suppliesManager) {
		this.suppliesManager = suppliesManager;
	}
	@Override
	@Jurisdiction(name = "耗材管理",level = -1)
	public String index() {
		return super.index();
	}
	@Override
	public void add() throws Exception {
		Map map = new LinkedHashMap();
		map.put("depId", this.getParameter("depId"));
		super.add(map);
	}
	@Override
	public void findAll() throws IOException {	
		String page = this.getRequest().getParameter("page");
		String rows = this.getRequest().getParameter("rows");
		String depId = this.getRequest().getParameter("depid");
		long supId = this.getIntegerParameter("supId");
		if(StringUtils.isBlank(page) || StringUtils.isBlank(rows)){
			page = "1";
			rows = "10";
		}
			String hql = "from Supplies s where 1=1";
			if(!depId.equals("0"))
				hql+=" and s.depId = '"+depId+"'";
			if(supId!=0)
				hql+=" and s.id='"+supId+"'";
		JSONObject jsonObject = new JSONObject();
		List<Supplies> es = getBaseManager().findAll(Integer.valueOf(page), Integer.valueOf(rows),hql);
			hql="select count(*) from Supplies s where s.enable = true";
			if(!depId.equals("0"))
				hql+=" and s.depId = '"+depId+"'";
			if(supId!=0)
				hql+=" and s.id='"+supId+"'";
			jsonObject.put("total", getBaseManager().findCountNum(hql));
		
		jsonObject.put("rows", JSONUtils.toJSONString(es));
		AjaxUtils.ajaxResponse(jsonObject.toString());
	}
	/**
	 * 获取耗材信息
	 * @param userManager
	 */
	public void findSup(){
		List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
		List<Supplies> supplies = suppliesManager.findAll();
		for (Supplies supplie : supplies) {
			Map<String,Object> map = new HashMap();
			map.put("id", supplie.getId());
			map.put("text",supplie.getName());
			list.add(map);
		}
		AjaxUtils.ajaxResponse(JSONArray.fromObject(list).toString());
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
