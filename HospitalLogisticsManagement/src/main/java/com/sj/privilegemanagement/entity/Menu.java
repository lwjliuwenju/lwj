package com.sj.privilegemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sj.common.entity.BaseEntity;

/**
 * 菜单列表
 * @author lwj
 *
 */
@Entity
@Table(name = "HOP_MENU")
public class Menu extends BaseEntity {

	/**
	 * 菜单名称
	 */
	@Column(name = "NAME_")
	private String name;
	
	/**
	 * 菜单图标
	 */
	@Column(name = "IMAGE_")
	private String image;
	/**
	 * 菜单链接url
	 */
	@Column(name = "LINK_URL_")
	private String linkUrl;
	/**
	 * 对应文件夹id（不在文件夹中为-1）
	 */
	@Column(name = "FOLDER_ID_")
	private Long folderId = -1l;
	
	/**
	 * 菜单级别
	 * 默认不是菜单树 只做权限使用
	 * 级别为-1时加入菜单树
	 */
	@Column(name = "MENU_LEVEL_")
	private Integer menuLevel = 1;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public int getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}
	
	
}
