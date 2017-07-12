package com.sj.privilegemanagement.entity.VO;

import com.sj.common.utils.annotation.Column;
import com.sj.common.utils.annotation.Table;

@Table(name = "人员工作量", colNum = 10)
public class RenyuanGongzuoVO {
     private String xingMing;
     
     private String buMen;
     
     private int  gongzuoNumber;
     
     private int you;
     
     private int liang;
     
     private int zhong;
    
     private int cha;
     
     private int zonghe;
     
     private int fanxiu;
    
     private int tongguolv;
     private int fenshu;
     @Column(name = "分数", sort = 10)
     public int getFenshu() {
		return fenshu;
	}

	public void setFenshu(int fenshu) {
		this.fenshu = fenshu;
	}

	@Column(name = "姓名", sort = 0)
	public String getXingMing() {
		return xingMing;
	}

	public void setXingMing(String xingMing) {
		this.xingMing = xingMing;
	}
	@Column(name = "部门", sort = 1)
	public String getBuMen() {
		return buMen;
	}

	public void setBuMen(String buMen) {
		this.buMen = buMen;
	}
	@Column(name = "工作次数", sort = 2)
	public int getGongzuoNumber() {
		return gongzuoNumber;
	}

	public void setGongzuoNumber(int gongzuoNumber) {
		this.gongzuoNumber = gongzuoNumber;
	}
	@Column(name = "优", sort = 3)
	public int getYou() {
		return you;
	}

	public void setYou(int you) {
		this.you = you;
	}
	@Column(name = "良", sort = 4)
	public int getLiang() {
		return liang;
	}

	public void setLiang(int liang) {
		this.liang = liang;
	}
@Column(name = "中", sort = 5)
	public int getZhong() {
		return zhong;
	}

	public void setZhong(int zhong) {
		this.zhong = zhong;
	}
	@Column(name = "差", sort = 6)
	public int getCha() {
		return cha;
	}

	public void setCha(int cha) {
		this.cha = cha;
	}
	@Column(name = "综合", sort = 7)
	public int getZonghe() {
		return zonghe;
	}

	public void setZonghe(int zonghe) {
		this.zonghe = zonghe;
	}
	@Column(name = "返修", sort = 8)
	public int getFanxiu() {
		return fanxiu;
	}

	public void setFanxiu(int fanxiu) {
		this.fanxiu = fanxiu;
	}
	@Column(name = "通过率", sort = 9)
	public int getTongguolv() {
		return tongguolv;
	}

	public void setTongguolv(int tongguolv) {
		this.tongguolv = tongguolv;
	}
     
     
}
