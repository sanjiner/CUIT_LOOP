package edu.cuit.module.auchc.entity;

// Generated 2015-4-14 19:14:43 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * Elementsprocessvalue generated by hbm2java
 */
public class Elementsprocessvalue implements java.io.Serializable {

	private String valueCode;
	private String meteStation;
	private String approveDataCode;
	private String dataOrigin;
	private String maxValue;
	private String minValue;
	private String averageValue;
	private Date addTime;
	private String remark;

	public Elementsprocessvalue() {
	}

	public Elementsprocessvalue(String valueCode) {
		this.valueCode = valueCode;
	}

	public Elementsprocessvalue(String valueCode, String meteStation,
			String approveDataCode, String dataOrigin, String maxValue,
			String minValue, String averageValue, Date addTime, String remark) {
		this.valueCode = valueCode;
		this.meteStation = meteStation;
		this.approveDataCode = approveDataCode;
		this.dataOrigin = dataOrigin;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.averageValue = averageValue;
		this.addTime = addTime;
		this.remark = remark;
	}

	public String getValueCode() {
		return this.valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	public String getMeteStation() {
		return this.meteStation;
	}

	public void setMeteStation(String meteStation) {
		this.meteStation = meteStation;
	}

	public String getApproveDataCode() {
		return this.approveDataCode;
	}

	public void setApproveDataCode(String approveDataCode) {
		this.approveDataCode = approveDataCode;
	}

	public String getDataOrigin() {
		return this.dataOrigin;
	}

	public void setDataOrigin(String dataOrigin) {
		this.dataOrigin = dataOrigin;
	}

	public String getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getMinValue() {
		return this.minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getAverageValue() {
		return this.averageValue;
	}

	public void setAverageValue(String averageValue) {
		this.averageValue = averageValue;
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
