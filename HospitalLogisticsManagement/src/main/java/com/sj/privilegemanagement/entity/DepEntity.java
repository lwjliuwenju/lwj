package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;
/**
 * 科室字段
 * @author Administrator
 *
 */
@Entity
@Table(name = "HOP_DEP")
public class DepEntity extends BaseEntity{
	/**
	 * 科室名称
	 */
	@Column(name = "DEP_NAME_")
	private String depName;
	/**
	 * 科室电话
	 */
	@Column(name = "DEP_PHONE_")
	private String depPhone;
	/**
	 * 科室的状态
	 */
	@Column(name = "DEP_STATE_")
	private String depState;
	/**
	 * 备注
	 */
	@Column(name = "mark")
	private String mark;
	/**
	 * 是否启用为运输部门 默认0不启用
	 * @return
	 */
	@Column(name = "TRANSPORT_")
	private Integer transport;
	
	public Integer getTransport() {
		return transport;
	}
	public void setTransport(Integer transport) {
		this.transport = transport;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getDepPhone() {
		return depPhone;
	}
	public void setDepPhone(String depPhone) {
		this.depPhone = depPhone;
	}
	public String getDepState() {
		return depState;
	}
	public void setDepState(String depState) {
		this.depState = depState;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
}
