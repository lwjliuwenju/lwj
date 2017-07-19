package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;
/**
 * 项目表
 * @author Administrator
 *
 */
@Entity
@Table(name = "HOP_PROJECT")
public class ProjectEntity extends BaseEntity{

	/**
	 * 项目名称
	 */
	@Column(name = "PROJECT_NAME_")
	private String projectName;
	/**
	 * 响应科室名称
	 */
	@Column(name = "RESPONSE_DEPT_NAME_")
	private String responseDeptName;
	/**
	 * 响应部门ID
	 */
	@Column(name = "RESPONSE_DEPT_ID_")
	private Long responseDeptId;
	/**
	 * 标准时间
	 * @return
	 */
	@Column(name = "STANDARD_HOUR_")
	private int standardHour;
	/**
	 * 服务标记
	 * 1：启用 0：未启用
	 */
	@Column(name = "STARTFLAG")
	private int startFlag;
	/**
	 * 分值
	 */
	@Column(name = "GRADE_")
	private Integer grade;
	/**
	 * 父ID
	 */
	@Column(name ="FATHER_ID_")
	private String fatherId;
	
	public String getFatherId() {
		return fatherId;
	}
	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public int getStartFlag() {
		return startFlag;
	}
	public void setStartFlag(int startFlag) {
		this.startFlag = startFlag;
	}
	public int getStandardHour() {
		return standardHour;
	}
	public void setStandardHour(int standardHour) {
		this.standardHour = standardHour;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getResponseDeptId() {
		return responseDeptId;
	}
	public void setResponseDeptId(Long responseDeptId) {
		this.responseDeptId = responseDeptId;
	}
	public String getResponseDeptName() {
		return responseDeptName;
	}
	public void setResponseDeptName(String responseDeptName) {
		this.responseDeptName = responseDeptName;
	}
}
