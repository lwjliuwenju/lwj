package com.sj.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CON_PARAMETER")
public class Parameter extends BaseEntity {

	/**
	 * 参数名称
	 */
	@Column(name = "PAR_NAME_")
	private String parName;
	/**
	 * 参数标记
	 */
	@Column(name = "PAR_MARK_")
	private String parMark;
	
	/**
	 * 参数值
	 */
	@Column(name = "PAR_VAL_")
	private String parVal;
	
	public Parameter() {
	}
	
	public Parameter(String parName,String parMark,String parVal) {
		this.parName = parName;
		this.parMark = parMark;
		this.parVal = parVal;
	}
	public String getParName() {
		return parName;
	}
	public void setParName(String parName) {
		this.parName = parName;
	}
	public String getParMark() {
		return parMark;
	}
	public void setParMark(String parMark) {
		this.parMark = parMark;
	}
	public String getParVal() {
		return parVal;
	}
	public void setParVal(String parVal) {
		this.parVal = parVal;
	}
	
}
