package edu.cuit.module.auchc.entity;

// Generated 2015-5-28 16:10:35 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * Bearinginfo generated by hbm2java
 */
public class Bearinginfo implements java.io.Serializable {

	private String bearingInfoId;
	private String meteIndicatorsReason;
	private String meteIndicators;
	private String cropGrowthPeriod;
	private Date startCollectionTime;
	private Date endCollectionTime;
	private String qualityIdentificationNum;
	private String stationId;

	public Bearinginfo() {
	}

	public Bearinginfo(String bearingInfoId) {
		this.bearingInfoId = bearingInfoId;
	}

	public Bearinginfo(String bearingInfoId, String meteIndicatorsReason,
			String cropGrowthPeriod, Date startCollectionTime,
			Date endCollectionTime, String qualityIdentificationNum) {
		this.bearingInfoId = bearingInfoId;
		this.meteIndicatorsReason = meteIndicatorsReason;
		this.cropGrowthPeriod = cropGrowthPeriod;
		this.startCollectionTime = startCollectionTime;
		this.endCollectionTime = endCollectionTime;
		this.qualityIdentificationNum = qualityIdentificationNum;
	}

	public String getBearingInfoId() {
		return this.bearingInfoId;
	}

	public void setBearingInfoId(String bearingInfoId) {
		this.bearingInfoId = bearingInfoId;
	}

	public String getMeteIndicatorsReason() {
		return this.meteIndicatorsReason;
	}

	public void setMeteIndicatorsReason(String meteIndicatorsReason) {
		this.meteIndicatorsReason = meteIndicatorsReason;
	}

	public String getCropGrowthPeriod() {
		return this.cropGrowthPeriod;
	}

	public void setCropGrowthPeriod(String cropGrowthPeriod) {
		this.cropGrowthPeriod = cropGrowthPeriod;
	}

	public Date getStartCollectionTime() {
		return this.startCollectionTime;
	}

	public void setStartCollectionTime(Date startCollectionTime) {
		this.startCollectionTime = startCollectionTime;
	}

	public Date getEndCollectionTime() {
		return this.endCollectionTime;
	}

	public void setEndCollectionTime(Date endCollectionTime) {
		this.endCollectionTime = endCollectionTime;
	}

	public String getQualityIdentificationNum() {
		return this.qualityIdentificationNum;
	}

	public void setQualityIdentificationNum(String qualityIdentificationNum) {
		this.qualityIdentificationNum = qualityIdentificationNum;
	}

	public String getMeteIndicators() {
		return meteIndicators;
	}

	public void setMeteIndicators(String meteIndicators) {
		this.meteIndicators = meteIndicators;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	

}
