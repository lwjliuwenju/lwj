package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;

@Entity
@Table(name = "HOP_USER_OF_ROLE")
public class UserOfRole extends BaseEntity {

	@Column(name = "USER_ID_")
	private Long userId;
	@Column(name = "ROLE_ID_")
	private Long roleId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
}
