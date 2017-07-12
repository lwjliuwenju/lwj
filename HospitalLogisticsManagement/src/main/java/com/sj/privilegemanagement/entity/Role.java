package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;


@Entity
@Table(name = "HOP_ROLE")
public class Role extends BaseEntity {

	/**
	 * 角色名称
	 */
	@Column(name = "NAME_")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
