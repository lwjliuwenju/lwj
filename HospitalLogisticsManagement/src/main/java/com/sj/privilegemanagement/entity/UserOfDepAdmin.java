package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;

/**
 * 用户部门关联表
 * @author Administrator
 *
 */
@Entity
@Table(name = "HOP_USER_OF_DEP_ADMIN")
public class UserOfDepAdmin extends BaseEntity {
	
	/**
	 * 用户id
	 */
	@Column(name = "USER_ID_")
	private long userId;
	/**
	 * 部门id 
	 */
	@Column(name = "DEP_ID_")
	private long depId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getDepId() {
		return depId;
	}
	public void setDepId(long depId) {
		this.depId = depId;
	}
}
