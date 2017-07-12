package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;

@Entity
@Table(name = "HOP_MENU_OF_ROLE")
public class MenuOfRole extends BaseEntity {

	@Column(name = "MENU_ID_")
	private Long menuId;
	@Column(name = "ROLE_ID_")
	private Long roleId;
	
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
}
