package edu.cuit.module.auchc.entity;

// Generated 2015-6-15 21:45:52 by Hibernate Tools 4.3.1

/**
 * Climatopeinfo generated by hbm2java
 */
public class Climatopeinfo implements java.io.Serializable {

	private String climatopeInfoNo;
	private String meteorologicalfactor;
	private String symbol;
	private String unit;
	private String avgValues;
	private String months;
	private String years;
	private String qualityIdentificationNum;

	public Climatopeinfo() {
	}

	public Climatopeinfo(String climatopeInfoNo, String qualityIdentificationNum) {
		this.climatopeInfoNo = climatopeInfoNo;
		this.qualityIdentificationNum = qualityIdentificationNum;
	}

	public Climatopeinfo(String climatopeInfoNo, String meteorologicalfactor,
			String symbol, String unit, String avgValues, String months,
			String years, String qualityIdentificationNum) {
		this.climatopeInfoNo = climatopeInfoNo;
		this.meteorologicalfactor = meteorologicalfactor;
		this.symbol = symbol;
		this.unit = unit;
		this.avgValues = avgValues;
		this.months = months;
		this.years = years;
		this.qualityIdentificationNum = qualityIdentificationNum;
	}

	public String getClimatopeInfoNo() {
		return this.climatopeInfoNo;
	}

	public void setClimatopeInfoNo(String climatopeInfoNo) {
		this.climatopeInfoNo = climatopeInfoNo;
	}

	public String getMeteorologicalfactor() {
		return this.meteorologicalfactor;
	}

	public void setMeteorologicalfactor(String meteorologicalfactor) {
		this.meteorologicalfactor = meteorologicalfactor;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getAvgValues() {
		return this.avgValues;
	}

	public void setAvgValues(String avgValues) {
		this.avgValues = avgValues;
	}

	public String getMonths() {
		return this.months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getYears() {
		return this.years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getQualityIdentificationNum() {
		return this.qualityIdentificationNum;
	}

	public void setQualityIdentificationNum(String qualityIdentificationNum) {
		this.qualityIdentificationNum = qualityIdentificationNum;
	}

}