package com.sj.privilegemanagement.entity.VO;

import com.sj.common.utils.annotation.Column;
import com.sj.common.utils.annotation.Table;

@Table(name = "申请单数据", colNum = 11)
public class ProposerVO {
	
	private String sqTime;
	
	private String sqDep;
	
	private String sqRen;
	
	private String fwCon;
	
	private String fx;
	
	private String xyNum;
	
	private String wx;
	
	private String xxTime;
	
	private String xxRen;
	
	private String yyTime;
	
	private String wcTime;
	
	private String state;
	@Column(name = "申请时间", sort = 0)
	public String getSqTime() {
		return sqTime;
	}
	public void setSqTime(String sqTime) {
		this.sqTime = sqTime;
	}
	@Column(name = "申请部门", sort = 1)
	public String getSqDep() {
		return sqDep;
	}

	public void setSqDep(String sqDep) {
		this.sqDep = sqDep;
	}

	@Column(name = "申请人", sort = 2)
	public String getSqRen() {
		return sqRen;
	}

	public void setSqRen(String sqRen) {
		this.sqRen = sqRen;
	}

	@Column(name = "服务内容", sort = 3)
	public String getFwCon() {
		return fwCon;
	}

	public void setFwCon(String fwCon) {
		this.fwCon = fwCon;
	}

	@Column(name = "是否返修", sort = 4)
	public String getFx() {
		return fx;
	}

	public void setFx(String fx) {
		this.fx = fx;
	}

	@Column(name = "响应次数", sort = 5)
	public String getXyNum() {
		return xyNum;
	}

	public void setXyNum(String xyNum) {
		this.xyNum = xyNum;
	}

	@Column(name = "是否外修", sort = 6)
	public String getWx() {
		return wx;
	}

	public void setWx(String wx) {
		this.wx = wx;
	}

	@Column(name = "响应时间", sort = 7)
	public String getXxTime() {
		return xxTime;
	}

	public void setXxTime(String xxTime) {
		this.xxTime = xxTime;
	}

	@Column(name = "响应人员", sort = 8)
	public String getXxRen() {
		return xxRen;
	}

	public void setXxRen(String xxRen) {
		this.xxRen = xxRen;
	}

	@Column(name = "已用工时", sort = 9)
	public String getYyTime() {
		return yyTime;
	}

	public void setYyTime(String yyTime) {
		this.yyTime = yyTime;
	}

	@Column(name = "完成时间", sort = 10)
	public String getWcTime() {
		return wcTime;
	}

	public void setWcTime(String wcTime) {
		this.wcTime = wcTime;
	}

	@Column(name = "状态", sort = 11)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
