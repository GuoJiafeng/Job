package com.graduationdesign.zhaopin.model;

/*
 * 简历实体
 */
public class Resume {
	//userid
	private int userID;
    //姓名
	private String name;
	
	//地址
	private String address;
	//年龄
	private int age;
	//学历
	private int xueli;
	//自我评价
	private String ziwopinjia;
	//专业
	private String zhuanye;
	//婚姻
	private int hunying;
	//联系方式
	private String lianxifangshi;
	//政治面貌
	private int zhengzhi;
	//薪水范围
	private int salaryRange;
	
	//学校
	private String xuexiao;
	//性别
	private int sex;
	
	//工作经历
	private String gongzuojingli;
	//工作年限
	private int nianxian;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getXueli() {
		return xueli;
	}

	public void setXueli(int xueli) {
		this.xueli = xueli;
	}

	public String getZiwopinjia() {
		return ziwopinjia;
	}

	public void setZiwopinjia(String ziwopinjia) {
		this.ziwopinjia = ziwopinjia;
	}

	public String getZhuanye() {
		return zhuanye;
	}

	public void setZhuanye(String zhuanye) {
		this.zhuanye = zhuanye;
	}

	public int getHunying() {
		return hunying;
	}

	public void setHunying(int hunying) {
		this.hunying = hunying;
	}

	public String getLianxifangshi() {
		return lianxifangshi;
	}

	public void setLianxifangshi(String lianxifangshi) {
		this.lianxifangshi = lianxifangshi;
	}

	public int getZhengzhi() {
		return zhengzhi;
	}

	public void setZhengzhi(int zhengzhi) {
		this.zhengzhi = zhengzhi;
	}

	public int getSalaryRange() {
		return salaryRange;
	}

	public void setSalaryRange(int salaryRange) {
		this.salaryRange = salaryRange;
	}

	public String getXuexiao() {
		return xuexiao;
	}

	public void setXuexiao(String xuexiao) {
		this.xuexiao = xuexiao;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getGongzuojingli() {
		return gongzuojingli;
	}

	public void setGongzuojingli(String gongzuojingli) {
		this.gongzuojingli = gongzuojingli;
	}

	public int getNianxian() {
		return nianxian;
	}

	public void setNianxian(int nianxian) {
		this.nianxian = nianxian;
	}

	@Override
	public String toString() {
		return "Resume [userID=" + userID + ", name=" + name + ", address="
				+ address + ", age=" + age + ", xueli=" + xueli
				+ ", ziwopinjia=" + ziwopinjia + ", zhuanye=" + zhuanye
				+ ", hunying=" + hunying + ", lianxifangshi=" + lianxifangshi
				+ ", zhengzhi=" + zhengzhi + ", salaryRange=" + salaryRange
				+ ", xuexiao=" + xuexiao + ", sex=" + sex + ", gongzuojingli="
				+ gongzuojingli + ", nianxian=" + nianxian + "]";
	}


	
	
}
