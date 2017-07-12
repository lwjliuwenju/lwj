package com.sj.privilegemanagement.action;

import java.io.IOException;
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
		List<ProjectEntity> projects = projectManager.findByDepId(depId, page, rows);
		int l = projectManager.findByDepId(depId).size();
		JSONObject jsonObject = new JSONObject();
		 jsonObject.put("rows", projects);  
		 jsonObject.put("total", l);
		AjaxUtils.ajaxResponse(jsonObject.toString());
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
		List<ProjectEntity> projectEntitys = projectManager.findByDepId(id);
		JSONArray jsonArray = new JSONArray();
		if(projectEntitys != null)
		for (ProjectEntity ProjectEntity : projectEntitys) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", ProjectEntity.getId());
			jsonObject.put("text",ProjectEntity.getProjectName());
			jsonArray.add(jsonObject);
		}
		AjaxUtils.ajaxResponse(jsonArray.toString());
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
	 * 修改服务分值
	 * update 
	 */
	public void update(){
		long projectId = this.getIntegerParameter("id");
		Integer grade = this.getIntegerParameter("grade");
		String projectName = this.getParameter("projectName");
		Integer standardHour = this.getIntegerParameter("standardHour");
		ProjectEntity projectEntity = projectManager.findById(projectId);
		projectEntity.setProjectName(projectName);
		projectEntity.setStandardHour(standardHour);
		projectEntity.setModifyStamp(new Date());
		projectEntity.setGrade(grade);
		projectManager.update(projectEntity);
		AjaxUtils.ajaxResponse(AjaxUtils.getSuccessMessage(null));
	}
}
	


