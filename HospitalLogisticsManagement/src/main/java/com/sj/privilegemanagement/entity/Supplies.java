package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;

/**
 * 耗材
 * @author Administrator
 * 2017年5月18日 09:05:12 lwj
 */
@Entity
@Table(name = "HOP_SUPPLIES")
public class Supplies extends BaseEntity {
	
	/**
	 * 对应的部门id
	 */
	@Column(name = "DEP_ID_")
	private Long depId;
	/**
	 * 耗材名称
	 */
	@Column(name = "NAME_")
	private String name;
	/**
	 * 价值
	 */
	@Column(name = "VALUE_")
	private String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getDepId() {
		return depId;
	}
	public void setDepId(Long depId) {
		this.depId = depId;
	}
	
}
