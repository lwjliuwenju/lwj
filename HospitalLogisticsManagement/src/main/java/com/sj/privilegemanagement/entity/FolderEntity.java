package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;

/**
 * 2017年4月19日 15:55:38
 * @author lwj
 * 文件夹实体类
 */
@Entity
@Table(name = "HOP_FOLDER_ENTITY")
public class FolderEntity extends BaseEntity {

	/**
	 * 文件夹名称
	 */
	@Column(name = "FOLDER_NAME_")
	private String folderName;
	
	/**
	 * 文件夹图标
	 */
	@Column(name = "IMAGE_")
	private String image;

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
