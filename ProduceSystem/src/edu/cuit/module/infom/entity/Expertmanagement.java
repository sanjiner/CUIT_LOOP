package edu.cuit.module.infom.entity;

// Generated 2015-4-15 13:38:49 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * Expertmanagement generated by hbm2java
 */
public class Expertmanagement implements java.io.Serializable {

	private String expertNo;//专家编号
	private String username;//用户名称
	private String loginPassword;//登录密码
	private String expertname;//专家名称
	private String belongToMeteorology;//所属区域（气象局新加）
	private String expertLevel;//专家层次（新加）
	private String expertIntroduction;//专家简介
	private String expertCategory;//专家类别
	private String idnumber;//身份证号码
	private String workUnits;//工作单位
	private String schools;//毕业学校
	private String educationalBackground;//最后学历
	private String address;//联系地址
	private String tel;//联系电话
	private String postCode;//邮编
	private String mailBox;//电子邮箱
	private String qq;//QQ
	private String photo;//照片
	private Date addTime;//添加时间
	private String remark;//备注

	public Expertmanagement() {
	}

	public Expertmanagement(String expertNo, String username,
			String loginPassword, String expertname) {
		this.expertNo = expertNo;
		this.username = username;
		this.loginPassword = loginPassword;
		this.expertname = expertname;
	}

	public Expertmanagement(String expertNo, String username,
			String loginPassword, String expertname,
			String belongToMeteorology, String expertLevel,
			String expertIntroduction, String expertCategory, String idnumber,
			String workUnits, String schools, String educationalBackground,
			String address, String tel, String postCode, String mailBox,
			String qq, String photo, Date addTime, String remark) {
		this.expertNo = expertNo;
		this.username = username;
		this.loginPassword = loginPassword;
		this.expertname = expertname;
		this.belongToMeteorology = belongToMeteorology;
		this.expertLevel = expertLevel;
		this.expertIntroduction = expertIntroduction;
		this.expertCategory = expertCategory;
		this.idnumber = idnumber;
		this.workUnits = workUnits;
		this.schools = schools;
		this.educationalBackground = educationalBackground;
		this.address = address;
		this.tel = tel;
		this.postCode = postCode;
		this.mailBox = mailBox;
		this.qq = qq;
		this.photo = photo;
		this.addTime = addTime;
		this.remark = remark;
	}

	public String getExpertNo() {
		return this.expertNo;
	}

	public void setExpertNo(String expertNo) {
		this.expertNo = expertNo;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getExpertname() {
		return this.expertname;
	}

	public void setExpertname(String expertname) {
		this.expertname = expertname;
	}

	public String getBelongToMeteorology() {
		return this.belongToMeteorology;
	}

	public void setBelongToMeteorology(String belongToMeteorology) {
		this.belongToMeteorology = belongToMeteorology;
	}

	public String getExpertLevel() {
		return this.expertLevel;
	}

	public void setExpertLevel(String expertLevel) {
		this.expertLevel = expertLevel;
	}

	public String getExpertIntroduction() {
		return this.expertIntroduction;
	}

	public void setExpertIntroduction(String expertIntroduction) {
		this.expertIntroduction = expertIntroduction;
	}

	public String getExpertCategory() {
		return this.expertCategory;
	}

	public void setExpertCategory(String expertCategory) {
		this.expertCategory = expertCategory;
	}

	public String getIdnumber() {
		return this.idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getWorkUnits() {
		return this.workUnits;
	}

	public void setWorkUnits(String workUnits) {
		this.workUnits = workUnits;
	}

	public String getSchools() {
		return this.schools;
	}

	public void setSchools(String schools) {
		this.schools = schools;
	}

	public String getEducationalBackground() {
		return this.educationalBackground;
	}

	public void setEducationalBackground(String educationalBackground) {
		this.educationalBackground = educationalBackground;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getMailBox() {
		return this.mailBox;
	}

	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
