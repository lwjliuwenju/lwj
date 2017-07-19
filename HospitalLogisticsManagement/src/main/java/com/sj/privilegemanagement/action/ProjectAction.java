package com.sj.privilegemanagement.action;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.sj.common.utils.AjaxUtils;
import com.sj.common.utils.JSONUtils;
import com.sj.common.utils.annotation.Jurisdiction;
import com.sj.privilegemanagement.action.base.BaseAction;
import com.sj.privilegemanagement.entity.DepEntity;
import com.sj.privilegemanagement.entity.ProjectEntity;
import com.sj.privilegemanagement.entity.Proposer;
import com.sj.privilegemanagement.entity.User;
import com.sj.privilegemanagement.manager.DepManager;
import com.sj.privilegemanagement.manager.ProjectManager;
import com.sj.privilegemanagement.manager.ProposerManager;

public class ProjectAction extends BaseAction<ProjectManager, ProjectEntity>{
	private static final long serialVersionUID = 1L;
	
	private ProjectManager projectManager;
	private DepManager depManager;
	private ProposerManager proposerManager;

	public void setProposerManager(ProposerManager proposerManager) {
		this.proposerManager = proposerManager;
	}
	public DepManager getDepManager() {
		return depManager;
	}
	public void setDepManager(DepManager depManager) {
		this.depManager = depManager;
	}
	public ProjectManager getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}
	/**
	 * 跳转服务管理
	 */
	public String projectform(){
		setUrl("projectform");
		return SUCCESS;
	}
	/**
	 * 添加下级服务
	 */
	public String addProject(){
		setUrl("addProject");
		return SUCCESS;
	}
	public void findAll() throws IOException{
		super.findAll();
	}
	/** 
	 * 2017年5月11日17:17:43 
	 * 通过部门id查询对应的项目
	 * @throws IOException 
	 */
	@Jurisdiction(name="服务管理",level=-1,url="index")
	public void findByDepId(){
		int page = this.getIntegerParameter("page") == 0 ? 1 : this.getIntegerParameter("page");
		int rows = this.getIntegerParameter("rows") == 0 ? 15 : this.getIntegerParameter("rows");
		long depId = this.getIntegerParameter("id");
		String fatherid ="0";
		JSONArray jsonArray = new JSONArray();
		int total = 0;
		List<Map<String, Object>> alist =new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> mlist =new ArrayList<Map<String,Object>>();
		alist = projectManager.findbyfatherId(depId,fatherid);
		if(alist!=null && alist.size()>0){
		total+=alist.size();
		for (Map<String, Object> map : alist) {
			 	String string = map.get("ID_").toString();
				fatherid =string;
			 	JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", string);
				jsonObject.put("name", map.get("PROJECT_NAME_"));
				jsonObject.put("standardHour", map.get("STANDARD_HOUR_"));
				jsonObject.put("startFlag", map.get("STARTFLAG"));
				jsonObject.put("grade", map.get("GRADE_"));
				jsonObject.put("Rid", map.get("RESPONSE_DEPT_ID_"));
				jsonObject.put("fatherId", map.get("FATHER_ID_"));
				jsonObject.put("iconCls", "icon-ok");
				jsonArray.add(jsonObject);
				mlist = projectManager.findbyfatherId(depId, fatherid);
				if(mlist!=null && mlist.size()>0){
				total+=mlist.size();
				for (Map<String, Object> m1 : mlist) {
					JSONObject jsonObjectT = new JSONObject();
					BigInteger b = (BigInteger) m1.get("ID_");
					String b1 = m1.get("FATHER_ID_").toString();
					jsonObjectT.put("id", b);
					jsonObjectT.put("name", m1.get("PROJECT_NAME_"));
					jsonObjectT.put("standardHour", m1.get("STANDARD_HOUR_"));
					jsonObjectT.put("startFlag", m1.get("STARTFLAG"));
					jsonObjectT.put("grade", m1.get("GRADE_"));
					jsonObjectT.put("Rid", map.get("RESPONSE_DEPT_ID_"));
					jsonObjectT.put("_parentId", b1);
					jsonObjectT.put("fatherId", b1);
					jsonArray.add(jsonObjectT);
				}
				}
		}
		}
		  	JSONObject data = new JSONObject();
		     data.put("rows", jsonArray);
		     data.put("total", total);
		     AjaxUtils.ajaxResponse(data.toString());
	}
		/**
		 * 	添加服务
		 */
	public void addproject(){
		String projectName = getRequest().getParameter("projectname");
		//为服务增加分值
		int grade=Integer.parseInt(getRequest().getParameter("grade"));
		long depId = this.getIntegerParameter("depId");
		if(depId == 0){
			AjaxUtils.ajaxResponse(AjaxUtils.getErrorMessage(AjaxUtils.REQUEST_PARAMETER_LACK));
			return;
		}
		String fatherId = this.getParameter("fatherId");
		if(StringUtils.isBlank(fatherId)){
			fatherId="0";
		}
		int standardtime = this.getIntegerParameter("standardtime");
		if(standardtime == 0)
			standardtime = 2;
		Date date = new Date();
		ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setProjectName(projectName);
		projectEntity.setResponseDeptId(depId);
		projectEntity.setCreateStamp(date);
		projectEntity.setStandardHour(standardtime);
		projectEntity.setStartFlag(0);
		projectEntity.setEnable(true);
		projectEntity.setModifyStamp(date);
		projectEntity.setGrade(grade);
		projectEntity.setFatherId(fatherId);
		projectManager.insert(projectEntity);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 删除
	 */
	public void delproject(){
		String[] projectId = getRequest().getParameterValues("id[]");
        for (String projectid : projectId) {
			ProjectEntity project = projectManager.findById(Long.valueOf(projectid));
			project.setEnable(false);
			projectManager.update(project);
		}
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 添加申请单对应的响应科室项目管理
	 * 2017年5月17日9:49:23 HLH
	 */
	public void findById(){
		long id = this.getIntegerParameter("id");
		String fatherId = this.getParameter("fatherid");
		if(StringUtils.isBlank(fatherId)){
			fatherId ="0";
		}
		List<Map<String,Object>> list = projectManager.findbyfatherId(id, fatherId);
		JSONArray jsonArray = new JSONArray();
		if(list!=null&&list.size()>0){
			 for (Map<String, Object> map : list) {
				JSONObject jsonObject =new JSONObject();
				jsonObject.put("id", Long.valueOf(map.get("ID_").toString()));
				jsonObject.put("text", map.get("PROJECT_NAME_"));
				jsonArray.add(jsonObject);
			}
		}
		AjaxUtils.ajaxResponse(jsonArray.toString());
	}
	public void addyiproject(){
		String fatherId = this.getParameter("projectId");
		int grade = this.getIntegerParameter("grade");
		String projectName = this.getParameter("projectName");
		int standardHour = this.getIntegerParameter("standardHour");
		long Rid = this.getIntegerParameter("Rid");
		if(standardHour==0){
			standardHour =2;
		}
		ProjectEntity projectEntity =new ProjectEntity();
		projectEntity.setCreateStamp(new Date());
		projectEntity.setEnable(true);
		projectEntity.setFatherId(fatherId);
		projectEntity.setGrade(grade);
		projectEntity.setModifyStamp(new Date());
		projectEntity.setResponseDeptId(Rid);
		DepEntity depEntity = depManager.findById(Rid);
		projectEntity.setResponseDeptName(depEntity.getDepName());
		projectEntity.setProjectName(projectName);
		projectEntity.setStandardHour(standardHour);
		projectEntity.setStartFlag(0);
		projectManager.insert(projectEntity);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 登记服务
	 * 2017年5月25日16:59:12
	 * 元冬冬
	 */
	public void editdengji(){
		String id = this.getParameter("id");
		projectManager.editdengji(id);
		 AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	/**
	 * 2017年6月13日8:57:27  HLH
	 * 修改服务
	 * update 
	 */
	public void update(){
		long projectId = this.getIntegerParameter("id");
		Integer grade = this.getIntegerParameter("grade");
		String projectName = this.getParameter("projectName");
		Integer standardHour = this.getIntegerParameter("standardHour");
		String fatherId = this.getParameter("fatherId");
		String fId =this.getParameter("fId");
		if(StringUtils.isBlank(fatherId)){
			fatherId=fId;
		}
		ProjectEntity projectEntity = projectManager.findById(projectId);
		projectEntity.setProjectName(projectName);
		projectEntity.setStandardHour(standardHour);
		projectEntity.setModifyStamp(new Date());
		projectEntity.setGrade(grade);
		projectEntity.setFatherId(fatherId);
		projectManager.update(projectEntity);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
	public  void itemtree(){
		    long depid = this.getIntegerParameter("id");
			JSONArray outArray = new JSONArray();
		    List<Map<String, Object>> fatherproject = projectManager.findbyfatherId(depid, "0");
		    if(fatherproject!=null&&fatherproject.size()>0){
		    	for (Map<String, Object> map : fatherproject) {
		    		JSONObject fatherObject = new JSONObject();
					Long fatherid = Long.valueOf(map.get("ID_").toString());
					fatherObject.put("id", fatherid);
					fatherObject.put("text", map.get("PROJECT_NAME_").toString());
					List<Map<String, Object>> childproject = projectManager.findbyfatherId(depid, fatherid.toString());
				    if(childproject==null){
					    outArray.add(fatherObject);
				    	continue;
				    }else{
				    	JSONArray childarray = new JSONArray();
				    	for (Map<String, Object> m1 : childproject) {
							JSONObject childObject = new JSONObject();
							childObject.put("id", m1.get("ID_").toString());
							childObject.put("text", m1.get("PROJECT_NAME_").toString());
							childarray.add(childObject);
						}
				    	  fatherObject.put("children", childarray);
				    }
				    outArray.add(fatherObject);
		    	}
		    }
		    AjaxUtils.ajaxResponse(outArray.toString());
	}
}
	


