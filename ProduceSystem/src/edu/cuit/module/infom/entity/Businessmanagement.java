package edu.cuit.module.infom.entity;

// Generated 2015-4-15 13:38:49 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * Businessmanagement generated by hbm2java
 */
public class Businessmanagement implements java.io.Serializable {

	private String campanyNo;//单位编号
	private String businessArea;//商家所在区域（新加）
	private String campanyName;//单位名称
	private String campanyType;//单位性质
	private String legalPerson;//法人代表
	private String address;//通讯地址
	private String cantactPerson;//业务联系人
	private String tel;//联系电话
	private String mobilePhone;//手机
	private String fax;//传真
	private String logo;//注册商标
	private String companyType;//企业类型
	private String businessType;//商家类型
	private String qualification;//相关资质
	private String mailBox;//企业邮箱
	private Date addTime;//添加时间
	private String userName;//用户名称
	private String loginPassword;//登录密码
	private String remark;//备注
	private String legalPresentCode;//法人代码

	public Businessmanagement() {
	}

	public Businessmanagement(String campanyNo) {
		this.campanyNo = campanyNo;
	}

	public Businessmanagement(String campanyNo, String businessArea,
			String campanyName, String campanyType, String legalPerson,
			String address, String cantactPerson, String tel,
			String mobilePhone, String fax, String logo, String companyType,
			String businessType, String qualification, String mailBox,
			Date addTime, String userName, String loginPassword, String remark,
			String legalPresentCode) {
		this.campanyNo = campanyNo;
		this.businessArea = businessArea;
		this.campanyName = campanyName;
		this.campanyType = campanyType;
		this.legalPerson = legalPerson;
		this.address = address;
		this.cantactPerson = cantactPerson;
		this.tel = tel;
		this.mobilePhone = mobilePhone;
		this.fax = fax;
		this.logo = logo;
		this.companyType = companyType;
		this.businessType = businessType;
		this.qualification = qualification;
		this.mailBox = mailBox;
		this.addTime = addTime;
		this.userName = userName;
		this.loginPassword = loginPassword;
		this.remark = remark;
		this.legalPresentCode = legalPresentCode;
	}

	public String getCampanyNo() {
		return this.campanyNo;
	}

	public void setCampanyNo(String campanyNo) {
		this.campanyNo = campanyNo;
	}

	public String getBusinessArea() {
		return this.businessArea;
	}

	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}

	public String getCampanyName() {
		return this.campanyName;
	}

	public void setCampanyName(String campanyName) {
		this.campanyName = campanyName;
	}

	public String getCampanyType() {
		return this.campanyType;
	}

	public void setCampanyType(String campanyType) {
		this.campanyType = campanyType;
	}

	public String getLegalPerson() {
		return this.legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCantactPerson() {
		return this.cantactPerson;
	}

	public void setCantactPerson(String cantactPerson) {
		this.cantactPerson = cantactPerson;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getQualification() {
		return this.qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getMailBox() {
		return this.mailBox;
	}

	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLegalPresentCode() {
		return this.legalPresentCode;
	}

	public void setLegalPresentCode(String legalPresentCode) {
		this.legalPresentCode = legalPresentCode;
	}

}