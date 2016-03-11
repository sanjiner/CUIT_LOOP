package edu.cuit.module.label.entity;

// Generated 2015-4-14 20:41:11 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * Labermanagement generated by hbm2java
 */
public class Labermanagement implements java.io.Serializable {

	private String labelId;
	private String labelStyle;
	private String labelQueryId;
	private String originId;
	private String labelKinds;
	private Integer scanNum;
	private String productInformation;
	private String companyImation;
	private String certificateImation;
	private Date makeTime;
	private String labelStatus;
	private String reark;
	private String applyOriginType;
	private String applyOriginCode;
	private String applyBh;
	private String abnormal;
	private String abnormalType;

	public Labermanagement() {
	}

	public Labermanagement(String labelId) {
		this.labelId = labelId;
	}

	public Labermanagement(String labelId, String labelStyle,
			String labelQueryId, String originId, String labelKinds,
			Integer scanNum, String productInformation, String companyImation,
			String certificateImation, Date makeTime, String labelStatus,
			String reark, String applyOriginType, String applyOriginCode,
			String applyBh, String abnormal, String abnormalType) {
		this.labelId = labelId;
		this.labelStyle = labelStyle;
		this.labelQueryId = labelQueryId;
		this.originId = originId;
		this.labelKinds = labelKinds;
		this.scanNum = scanNum;
		this.productInformation = productInformation;
		this.companyImation = companyImation;
		this.certificateImation = certificateImation;
		this.makeTime = makeTime;
		this.labelStatus = labelStatus;
		this.reark = reark;
		this.applyOriginType = applyOriginType;
		this.applyOriginCode = applyOriginCode;
		this.applyBh = applyBh;
		this.abnormal = abnormal;
		this.abnormalType = abnormalType;
	}

	public String getLabelId() {
		return this.labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelStyle() {
		return this.labelStyle;
	}

	public void setLabelStyle(String labelStyle) {
		this.labelStyle = labelStyle;
	}

	public String getLabelQueryId() {
		return this.labelQueryId;
	}

	public void setLabelQueryId(String labelQueryId) {
		this.labelQueryId = labelQueryId;
	}

	public String getOriginId() {
		return this.originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	public String getLabelKinds() {
		return this.labelKinds;
	}

	public void setLabelKinds(String labelKinds) {
		this.labelKinds = labelKinds;
	}

	public Integer getScanNum() {
		return this.scanNum;
	}

	public void setScanNum(Integer scanNum) {
		this.scanNum = scanNum;
	}

	public String getProductInformation() {
		return this.productInformation;
	}

	public void setProductInformation(String productInformation) {
		this.productInformation = productInformation;
	}

	public String getCompanyImation() {
		return this.companyImation;
	}

	public void setCompanyImation(String companyImation) {
		this.companyImation = companyImation;
	}

	public String getCertificateImation() {
		return this.certificateImation;
	}

	public void setCertificateImation(String certificateImation) {
		this.certificateImation = certificateImation;
	}

	public Date getMakeTime() {
		return this.makeTime;
	}

	public void setMakeTime(Date makeTime) {
		this.makeTime = makeTime;
	}

	public String getLabelStatus() {
		return this.labelStatus;
	}

	public void setLabelStatus(String labelStatus) {
		this.labelStatus = labelStatus;
	}

	public String getReark() {
		return this.reark;
	}

	public void setReark(String reark) {
		this.reark = reark;
	}

	public String getApplyOriginType() {
		return this.applyOriginType;
	}

	public void setApplyOriginType(String applyOriginType) {
		this.applyOriginType = applyOriginType;
	}

	public String getApplyOriginCode() {
		return this.applyOriginCode;
	}

	public void setApplyOriginCode(String applyOriginCode) {
		this.applyOriginCode = applyOriginCode;
	}

	public String getApplyBh() {
		return this.applyBh;
	}

	public void setApplyBh(String applyBh) {
		this.applyBh = applyBh;
	}

	public String getAbnormal() {
		return this.abnormal;
	}

	public void setAbnormal(String abnormal) {
		this.abnormal = abnormal;
	}

	public String getAbnormalType() {
		return this.abnormalType;
	}

	public void setAbnormalType(String abnormalType) {
		this.abnormalType = abnormalType;
	}

}