package edu.cuit.module.origin.entity;

// Generated 2015-4-14 22:06:25 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * Productapply generated by hbm2java
 */
public class Productapply implements java.io.Serializable {

	private String originId;    //溯源编号
	private String productValue;  //产值
	private String productNum;   //产量
	private String applyCompany; //申请商家
	private String applyPerson;  //申请人
	private Date applyTime;     //申请时间
	private String constract;   //联系电话
	private String personAdress;  //联系地址
	private String originName;  //产品名称
	private String productBrand; //产品品牌
	private String originAddress;  //产地
	private String originDescription;  //产品描述
	private String isApllyCode;
	private Integer codeNumber;
	private String originCode;   //用作溯源代理机构名称
	private Integer labelNum;   //预申请标签数量
	private String auditPerson;
	private String orignStatus;  //审核状态
	private Date auditTime;
	private String auditAdvice;
	private String cityPerson;
	private String cityAdvice;
	private Date cityTime;
	private String provincesPerson;
	private String provinceAdvice;
	private Date provinceTime;
	private String inTotal;
	private String remark;    //商家邮箱
	private String handleResult;
	private String isWithDraw;

	public Productapply() {
	}

	public Productapply(String originId) {
		this.originId = originId;
	}

	public Productapply(String originId, String productValue,
			String productNum, String applyCompany, String applyPerson,
			Date applyTime, String constract, String personAdress,
			String originName, String productBrand, String originAddress,
			String originDescription, String isApllyCode, Integer codeNumber,
			String originCode, Integer labelNum, String auditPerson,
			String orignStatus, Date auditTime, String auditAdvice,
			String cityPerson, String cityAdvice, Date cityTime,
			String provincesPerson, String provinceAdvice, Date provinceTime,
			String inTotal, String remark, String handleResult,
			String isWithDraw) {
		this.originId = originId;
		this.productValue = productValue;
		this.productNum = productNum;
		this.applyCompany = applyCompany;
		this.applyPerson = applyPerson;
		this.applyTime = applyTime;
		this.constract = constract;
		this.personAdress = personAdress;
		this.originName = originName;
		this.productBrand = productBrand;
		this.originAddress = originAddress;
		this.originDescription = originDescription;
		this.isApllyCode = isApllyCode;
		this.codeNumber = codeNumber;
		this.originCode = originCode;
		this.labelNum = labelNum;
		this.auditPerson = auditPerson;
		this.orignStatus = orignStatus;
		this.auditTime = auditTime;
		this.auditAdvice = auditAdvice;
		this.cityPerson = cityPerson;
		this.cityAdvice = cityAdvice;
		this.cityTime = cityTime;
		this.provincesPerson = provincesPerson;
		this.provinceAdvice = provinceAdvice;
		this.provinceTime = provinceTime;
		this.inTotal = inTotal;
		this.remark = remark;
		this.handleResult = handleResult;
		this.isWithDraw = isWithDraw;
	}

	public String getOriginId() {
		return this.originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	public String getProductValue() {
		return this.productValue;
	}

	public void setProductValue(String productValue) {
		this.productValue = productValue;
	}

	public String getProductNum() {
		return this.productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getApplyCompany() {
		return this.applyCompany;
	}

	public void setApplyCompany(String applyCompany) {
		this.applyCompany = applyCompany;
	}

	public String getApplyPerson() {
		return this.applyPerson;
	}

	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getConstract() {
		return this.constract;
	}

	public void setConstract(String constract) {
		this.constract = constract;
	}

	public String getPersonAdress() {
		return this.personAdress;
	}

	public void setPersonAdress(String personAdress) {
		this.personAdress = personAdress;
	}

	public String getOriginName() {
		return this.originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getProductBrand() {
		return this.productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getOriginAddress() {
		return this.originAddress;
	}

	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}

	public String getOriginDescription() {
		return this.originDescription;
	}

	public void setOriginDescription(String originDescription) {
		this.originDescription = originDescription;
	}

	public String getIsApllyCode() {
		return this.isApllyCode;
	}

	public void setIsApllyCode(String isApllyCode) {
		this.isApllyCode = isApllyCode;
	}

	public Integer getCodeNumber() {
		return this.codeNumber;
	}

	public void setCodeNumber(Integer codeNumber) {
		this.codeNumber = codeNumber;
	}

	public String getOriginCode() {
		return this.originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public Integer getLabelNum() {
		return this.labelNum;
	}

	public void setLabelNum(Integer labelNum) {
		this.labelNum = labelNum;
	}

	public String getAuditPerson() {
		return this.auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public String getOrignStatus() {
		return this.orignStatus;
	}

	public void setOrignStatus(String orignStatus) {
		this.orignStatus = orignStatus;
	}

	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditAdvice() {
		return this.auditAdvice;
	}

	public void setAuditAdvice(String auditAdvice) {
		this.auditAdvice = auditAdvice;
	}

	public String getCityPerson() {
		return this.cityPerson;
	}

	public void setCityPerson(String cityPerson) {
		this.cityPerson = cityPerson;
	}

	public String getCityAdvice() {
		return this.cityAdvice;
	}

	public void setCityAdvice(String cityAdvice) {
		this.cityAdvice = cityAdvice;
	}

	public Date getCityTime() {
		return this.cityTime;
	}

	public void setCityTime(Date cityTime) {
		this.cityTime = cityTime;
	}

	public String getProvincesPerson() {
		return this.provincesPerson;
	}

	public void setProvincesPerson(String provincesPerson) {
		this.provincesPerson = provincesPerson;
	}

	public String getProvinceAdvice() {
		return this.provinceAdvice;
	}

	public void setProvinceAdvice(String provinceAdvice) {
		this.provinceAdvice = provinceAdvice;
	}

	public Date getProvinceTime() {
		return this.provinceTime;
	}

	public void setProvinceTime(Date provinceTime) {
		this.provinceTime = provinceTime;
	}

	public String getInTotal() {
		return this.inTotal;
	}

	public void setInTotal(String inTotal) {
		this.inTotal = inTotal;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHandleResult() {
		return this.handleResult;
	}

	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}

	public String getIsWithDraw() {
		return this.isWithDraw;
	}

	public void setIsWithDraw(String isWithDraw) {
		this.isWithDraw = isWithDraw;
	}

}