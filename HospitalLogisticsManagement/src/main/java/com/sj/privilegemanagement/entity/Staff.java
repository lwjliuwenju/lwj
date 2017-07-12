package com.sj.privilegemanagement.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;

@Entity
@Table(name = "HOP_STAFF")
public class Staff extends BaseEntity{
	/**
	 * 用户姓名
	 */
	@Column(name = "NAME_")
	private String name;
	/**
	 * 手机号
	 */
	@Column(name = "PHONE_")
	private String phone;
	/**
	 * 部门ID
	 */
	@Column(name = "DEP_ID_")
	private Long deptId;
	/**
	 * 响应人响应次数
	 */
	@Column(name = "NUMBER_")
	private int number;
	/**
	 * 当前状态---》是否忙碌
	 * 1：忙碌
	 * 0：空闲
	 */
	@Column(name = "STATUS")
	private int status;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
