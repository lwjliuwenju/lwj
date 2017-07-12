package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;

/**
 * 耗材申请表
 * @author Administrator
 * 2017年5月18日 09:05:12 HLH
 */
@Entity
@Table(name = "HOP_SUPPLIES_PROPOSER")
public class SuppliesOfproposer extends BaseEntity {
	
	/**
	 * 对应申请单id
	 */
	@Column(name = "PROPOSER_ID_")
	private Long proposerId;
	/**
	 * 对应耗材ID
	 */
	@Column(name = "SUPPLIES_ID_")
	private Long suppliesId;
	/**
	 * 耗材数量
	 */
	@Column(name = "SUPPLIES_NUM_")
	private int suppliesNum;
	/**
	 * 耗材名称
	 * @return
	 */
	@Column(name = "NAME_")
	private String suppliesName;
	/**
	 * 状态STATUS_
	 * 0:未发放
	 * 1:已发放
	 * @return
	 */
	@Column(name = "STATUS_")
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSuppliesName() {
		return suppliesName;
	}
	public void setSuppliesName(String suppliesName) {
		this.suppliesName = suppliesName;
	}
	public Long getProposerId() {
		return proposerId;
	}
	public void setProposerId(Long proposerId) {
		this.proposerId = proposerId;
	}
	public Long getSuppliesId() {
		return suppliesId;
	}
	public void setSuppliesId(Long suppliesId) {
		this.suppliesId = suppliesId;
	}
	public int getSuppliesNum() {
		return suppliesNum;
	}
	public void setSuppliesNum(int suppliesNum) {
		this.suppliesNum = suppliesNum;
	}
	
	
}
