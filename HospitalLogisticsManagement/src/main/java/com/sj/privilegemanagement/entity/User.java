package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;

@Entity
@Table(name = "HOP_USER")
public class User extends BaseEntity {

	/**
	 * userId 用户唯一id
	 */
	@Column(name = "USER_ID_")
	private Long userId;
	
	/**
	 * 用户密码
	 */
	@Column(name = "PASSWORD_")
	private String password;
	
	/**
	 * 用户名
	 */
	@Column(name = "USER_NAME_")
	private String userName;
	
	/**
	 * 手机号
	 */
	@Column(name = "NUMBER_")
	private String number;
	
	/**
	 * 性别
	 */
	@Column(name = "SEX_")
	private Boolean sex;
	
	/**
	 * 邮箱
	 */
	@Column(name = "EMAIL_")
	private String email;
	
	/**
	 * 用户昵称
	 */
	@Column(name = "JOB_NUMBER_")
	private String jobNumber;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	
}
