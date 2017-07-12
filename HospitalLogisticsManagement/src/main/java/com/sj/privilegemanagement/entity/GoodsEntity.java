package com.sj.privilegemanagement.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;

@Entity
@Table(name = "HOP_GOODS")
public class GoodsEntity extends BaseEntity{
	/**
	 * 申请单id
	 */
	@Column(name ="PROPOSER_ID_")
	private Long proposerId;
	/**
	 * 抽血部门id
	 */
	@Column(name ="DEP_ID_")
	private Long depId;
	/**
	 * 部门booldDep
	 */
	@Column(name ="BOOLD_DEP_")
	private String booldDep;
	/**
	 * STAFF_ID_
	 */
	@Column(name ="STAFF_ID_")
	private Long staffId;
	/**
	 * 2017年6月13日16:03:29
	 * 床位号 
	 * @return
	 */
	@Column(name ="BEDNO_")
	private String bedNo;
	/**
	 * 住院号
	 * HOSPITALIZATION_NUMBER_
	 * @return
	 */
	@Column(name ="HOSPITALIZATION_NUMBER_")
	private String hospitalizationNumber;
	/**
	 * 抽血单号BLOOD_COUNT_
	 * @return
	 */
	@Column(name ="BLOOD_COUNT_")
	private String bloodCount;
	/**
	 * 抽血时间DRAWBLOOD_TIME_
	 * @return
	 */
	@Column(name ="DRAWBLOOD_TIME_")
	private Date drawbloodTime;
	/**
	 * 抽血人 Blood people
	 */
	@Column(name ="BLOOD_PEOPLE_")
	private String bloodPeople;
	/**
	 * 血液数量
	 * @return
	 */
	@Column(name ="BLOODNUM_")
	private int bloodNum;
	/**
	 * 货物名称
	 * @return
	 */
	@Column(name ="GOODSNAME_")
	private String goodsName;
	
	public Long getDepId() {
		return depId;
	}
	public void setDepId(Long depId) {
		this.depId = depId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getBloodPeople() {
		return bloodPeople;
	}
	public void setBloodPeople(String bloodPeople) {
		this.bloodPeople = bloodPeople;
	}
	public int getBloodNum() {
		return bloodNum;
	}
	public void setBloodNum(int bloodNum) {
		this.bloodNum = bloodNum;
	}
	public Long getProposerId() {
		return proposerId;
	}
	public void setProposerId(Long proposerId) {
		this.proposerId = proposerId;
	}
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getHospitalizationNumber() {
		return hospitalizationNumber;
	}
	public void setHospitalizationNumber(String hospitalizationNumber) {
		this.hospitalizationNumber = hospitalizationNumber;
	}
	public String getBloodCount() {
		return bloodCount;
	}
	public void setBloodCount(String bloodCount) {
		this.bloodCount = bloodCount;
	}
	public Date getDrawbloodTime() {
		return drawbloodTime;
	}
	public void setDrawbloodTime(Date drawbloodTime) {
		this.drawbloodTime = drawbloodTime;
	}
	public String getBooldDep() {
		return booldDep;
	}
	public void setBooldDep(String booldDep) {
		this.booldDep = booldDep;
	}
	
	
}