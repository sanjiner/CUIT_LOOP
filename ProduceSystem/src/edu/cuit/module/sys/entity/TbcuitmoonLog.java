package edu.cuit.module.sys.entity;

// Generated 2015-4-13 16:59:09 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * TbcuitmoonLog generated by hbm2java
 */
public class TbcuitmoonLog implements java.io.Serializable {

	private String cuitMoonLogId;
	private Long cuitMoonLogType;
	private String cuitMoonLogOperationUserName;
	private String cuitMoonLogOperationIpv4;
	private String cuitMoonLogOperationIpv6;
	private Date cuitMoonLogOperationTime;
	private String cuitMoonLogOperationName;
	private String cuitMoonLogOperationUrl;
	private String cuitMoonLogRemarks;

	public TbcuitmoonLog() {
	}

	public TbcuitmoonLog(String cuitMoonLogId) {
		this.cuitMoonLogId = cuitMoonLogId;
	}

	public TbcuitmoonLog(String cuitMoonLogId, Long cuitMoonLogType,
			String cuitMoonLogOperationUserName,
			String cuitMoonLogOperationIpv4, String cuitMoonLogOperationIpv6,
			Date cuitMoonLogOperationTime, String cuitMoonLogOperationName,
			String cuitMoonLogOperationUrl, String cuitMoonLogRemarks) {
		this.cuitMoonLogId = cuitMoonLogId;
		this.cuitMoonLogType = cuitMoonLogType;
		this.cuitMoonLogOperationUserName = cuitMoonLogOperationUserName;
		this.cuitMoonLogOperationIpv4 = cuitMoonLogOperationIpv4;
		this.cuitMoonLogOperationIpv6 = cuitMoonLogOperationIpv6;
		this.cuitMoonLogOperationTime = cuitMoonLogOperationTime;
		this.cuitMoonLogOperationName = cuitMoonLogOperationName;
		this.cuitMoonLogOperationUrl = cuitMoonLogOperationUrl;
		this.cuitMoonLogRemarks = cuitMoonLogRemarks;
	}

	public String getCuitMoonLogId() {
		return this.cuitMoonLogId;
	}

	public void setCuitMoonLogId(String cuitMoonLogId) {
		this.cuitMoonLogId = cuitMoonLogId;
	}

	public Long getCuitMoonLogType() {
		return this.cuitMoonLogType;
	}

	public void setCuitMoonLogType(Long cuitMoonLogType) {
		this.cuitMoonLogType = cuitMoonLogType;
	}

	public String getCuitMoonLogOperationUserName() {
		return this.cuitMoonLogOperationUserName;
	}

	public void setCuitMoonLogOperationUserName(
			String cuitMoonLogOperationUserName) {
		this.cuitMoonLogOperationUserName = cuitMoonLogOperationUserName;
	}

	public String getCuitMoonLogOperationIpv4() {
		return this.cuitMoonLogOperationIpv4;
	}

	public void setCuitMoonLogOperationIpv4(String cuitMoonLogOperationIpv4) {
		this.cuitMoonLogOperationIpv4 = cuitMoonLogOperationIpv4;
	}

	public String getCuitMoonLogOperationIpv6() {
		return this.cuitMoonLogOperationIpv6;
	}

	public void setCuitMoonLogOperationIpv6(String cuitMoonLogOperationIpv6) {
		this.cuitMoonLogOperationIpv6 = cuitMoonLogOperationIpv6;
	}

	public Date getCuitMoonLogOperationTime() {
		return this.cuitMoonLogOperationTime;
	}

	public void setCuitMoonLogOperationTime(Date cuitMoonLogOperationTime) {
		this.cuitMoonLogOperationTime = cuitMoonLogOperationTime;
	}

	public String getCuitMoonLogOperationName() {
		return this.cuitMoonLogOperationName;
	}

	public void setCuitMoonLogOperationName(String cuitMoonLogOperationName) {
		this.cuitMoonLogOperationName = cuitMoonLogOperationName;
	}

	public String getCuitMoonLogOperationUrl() {
		return this.cuitMoonLogOperationUrl;
	}

	public void setCuitMoonLogOperationUrl(String cuitMoonLogOperationUrl) {
		this.cuitMoonLogOperationUrl = cuitMoonLogOperationUrl;
	}

	public String getCuitMoonLogRemarks() {
		return this.cuitMoonLogRemarks;
	}

	public void setCuitMoonLogRemarks(String cuitMoonLogRemarks) {
		this.cuitMoonLogRemarks = cuitMoonLogRemarks;
	}

}