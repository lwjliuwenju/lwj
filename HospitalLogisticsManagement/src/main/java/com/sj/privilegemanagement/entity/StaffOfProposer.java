package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;

@Entity
@Table(name = "HOP_STAFF_OF_PROPOSER")
public class StaffOfProposer extends BaseEntity{

	/**
	 * 人员id
	 */
	@Column(name = "STAFF_ID_")
	private Long staffId;
	/**
	 * 申请单id
	 */
	@Column(name = "PROPOSER_ID_")
	private Long ProposerId;
	/**
	 * 完成状态
	 * -1:初始值 正在维修
	 * 1:完成
	 * 0:未完成
	 */
	@Column(name = "COMPLETE_")
	private int complete;
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public Long getProposerId() {
		return ProposerId;
	}
	public void setProposerId(Long proposerId) {
		ProposerId = proposerId;
	}
	public int getComplete() {
		return complete;
	}
	public void setComplete(int complete) {
		this.complete = complete;
	}
	
}
