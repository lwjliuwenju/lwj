package com.sj.privilegemanagement.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;
/**
 * 服务申请单
 * @author Administrator
 *
 */
@Entity
@Table(name = "HOP_PROPOSER")
public class Proposer extends BaseEntity{
	/**
	 * 服务id
	 */
	@Column(name = "PROJECT_ID_")
	private Long projectId;
	/**
	 * 科室名称
	 */
	@Column(name = "DEP_NAME_")
	private String depName ;
	/**
	 * 科室ID
	 */
	@Column(name = "DEP_ID_")
	private Long depId ;
	
	/**
	 * 
	 */
	@Column(name = "PROPOSER_CONTENT_")
	private String proposerContent;
	/**
	 * 对应部门
	 */
	@Column(name = "RESPONSE_DEPNAME_")
	private String responseDepName;
	
	/**
	 * 服务名称
	 */
	@Column(name = "PROJECT_NAME_")
	private String projectName;
	/**
	 * 申请人
	 */
	@Column(name = "USER_NAME_")
	private String userName ;
	
	/**
	 * 返修标志
	 * 1：返修 0：未返修
	 */
	@Column(name = "REPAIR_FLAG_")
	private int repairFlag ;
	/**
	 * 返修原因
	 */
	@Column(name = "REPAIR_REASON_")
	private String repairReason;
	
	/**
	 * 状态
	 */
	@Column(name = "STATUS_")
	private int status ;
	/**
	 * USER_ID_
	 */
	@Column(name = "USER_ID_")
	private Long userId ;
	/**
	 * 评价内容
	 */
	@Column(name="APPRAISE_MARK_")
	private String appraiseMark;
	/**
	 * 评价
	 * 1:优 2：良 3：中 4：差
	 */
	@Column(name = "APPRAISE_")
	private int appraise ;
	/**
	 * 备注
	 */
	@Column(name = "REMARK_")
	private String remark ;
	/**
	 * RESPONSE_DEP_ID_响应部门ID
	 * @return
	 */
	@Column(name = "REPONSE_DEP_ID_")
	private long reponseDepId ;
	/**
	 * 审核标记
	 * CHECKFALG_
	 * @return
	 */
	@Column(name ="CHECKFALG_")
	private int checkflag;
	/**
	 * 审核内容
	 * CHECK_CONTENT
	 * @return
	 */
	@Column(name ="CHECK_CONTENT")
	private String checkContent;
	/**
	 * 取货人姓名Pickup person
	 * @return
	 */
	@Column(name ="PICK_USER_")
	private String pickUser;
	/**
	 * 取货人联系方式phone
	 * @return
	 */
	@Column(name ="PICK_PHONE_")
	private String pickPhone;
	/**
	 * GOODSNAME
	 * @return
	 */
	@Column(name ="GOODS_NAME_")
	private String goodsName;
	/**
	 * 物流队
	 * @return
	 */
	@Column(name ="GOODSTEAM_ID_")
	private Long goodsteamId;
	/**
	 * 挂起标记
	 * 1:挂起  0：默认
	 */
	@Column(name = "GQBJ_")
	private int gqbj;
	
	public int getGqbj() {
		return gqbj;
	}
	public void setGqbj(int gqbj) {
		this.gqbj = gqbj;
	}
	public void setResponseTimes(int responseTimes) {
		this.responseTimes = responseTimes;
	}
	public Long getGoodsteamId() {
		return goodsteamId;
	}
	public void setGoodsteamId(Long goodsteamId) {
		this.goodsteamId = goodsteamId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getPickPhone() {
		return pickPhone;
	}
	public void setPickPhone(String pickPhone) {
		this.pickPhone = pickPhone;
	}
	public String getPickUser() {
		return pickUser;
	}
	public void setPickUser(String pickUser) {
		this.pickUser = pickUser;
	}
	/**
	 * 外修标志
	 * 0：非外修
	 * 1：外修
	 */
	@Column(name = "OUTSOURCING_FLAG_")
	private String outSourcIngFlag;
	/**
	 * 响应次数
	 */
	@Column(name = "RESPONSE_TIMES_")
	private int responseTimes = 0;
	/**
	 * 响应开始时间
	 */
	@Column(name = "RESPONSE_START_TIME_")
	private Date responseStartTime;
	/**
	 * 响应结束时间
	 */
	@Column(name = "RESPONSE_END_TIME_")
	private Date responseEndTime;
	/**
	 * 异常终止原因
	 */
	@Column(name = "TERMINATION_REASON_")
	private String terminationReason;
	/**
	 * 终止人ID
	 */
	@Column(name="TERMINATION_REASON_ID_")
	private Long terminationId;
	/**
	 * 外修原因
	 */
	@Column(name = "OUT_REPAIR_REASON_")
	private String outRepairReason;
	/**
	 * 终止人姓名
	 */
	@Column(name ="TERMINATION_REASON_USER_NAME_")
	private String terminationReasonUserName;
	
	public int getCheckflag() {
		return checkflag;
	}
	public void setCheckflag(int checkflag) {
		this.checkflag = checkflag;
	}
	public String getCheckContent() {
		return checkContent;
	}
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	public String getAppraiseMark() {
		return appraiseMark;
	}
	public void setAppraiseMark(String appraiseMark) {
		this.appraiseMark = appraiseMark;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public Long getDepId() {
		return depId;
	}
	public void setDepId(Long depId) {
		this.depId = depId;
	}
	public String getProposerContent() {
		return proposerContent;
	}
	public void setProposerContent(String proposerContent) {
		this.proposerContent = proposerContent;
	}
	public String getResponseDepName() {
		return responseDepName;
	}
	public void setResponseDepName(String responseDepName) {
		this.responseDepName = responseDepName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getRepairFlag() {
		return repairFlag;
	}
	public void setRepairFlag(int repairFlag) {
		this.repairFlag = repairFlag;
	}
	public String getRepairReason() {
		return repairReason;
	}
	public void setRepairReason(String repairReason) {
		this.repairReason = repairReason;
	}
   
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public int getAppraise() {
		return appraise;
	}
	public void setAppraise(int appraise) {
		this.appraise = appraise;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public long getReponseDepId() {
		return reponseDepId;
	}
	public void setReponseDepId(long reponseDepId) {
		this.reponseDepId = reponseDepId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getOutSourcIngFlag() {
		return outSourcIngFlag;
	}
	public void setOutSourcIngFlag(String outSourcIngFlag) {
		this.outSourcIngFlag = outSourcIngFlag;
	}
	public int getResponseTimes() {
		return responseTimes;
	}
	public void setResponseTimes(Integer responseTimes) {
		this.responseTimes = responseTimes;
	}
	public Date getResponseStartTime() {
		return responseStartTime;
	}
	public void setResponseStartTime(Date responseStartTime) {
		this.responseStartTime = responseStartTime;
	}
	public Date getResponseEndTime() {
		return responseEndTime;
	}
	public void setResponseEndTime(Date responseEndTime) {
		this.responseEndTime = responseEndTime;
	}
	public String getTerminationReason() {
		return terminationReason;
	}
	public void setTerminationReason(String terminationReason) {
		this.terminationReason = terminationReason;
	}
	public Long getTerminationId() {
		return terminationId;
	}
	public void setTerminationId(Long terminationId) {
		this.terminationId = terminationId;
	}
	public String getOutRepairReason() {
		return outRepairReason;
	}
	public void setOutRepairReason(String outRepairReason) {
		this.outRepairReason = outRepairReason;
	}
	public String getTerminationReasonUserName() {
		return terminationReasonUserName;
	}
	public void setTerminationReasonUserName(String terminationReasonUserName) {
		this.terminationReasonUserName = terminationReasonUserName;
	}
}
