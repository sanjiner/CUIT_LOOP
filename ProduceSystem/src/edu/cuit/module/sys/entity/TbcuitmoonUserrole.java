package edu.cuit.module.sys.entity;

// Generated 2015-4-13 16:59:09 by Hibernate Tools 4.3.1

/**
 * TbcuitmoonUserrole generated by hbm2java
 */
public class TbcuitmoonUserrole implements java.io.Serializable {

	private String cuitMoonUserRoleId;
	private String cuitMoonUserId;
	private String cuitMoonRoleId;

	public TbcuitmoonUserrole() {
	}

	public TbcuitmoonUserrole(String cuitMoonUserRoleId, String cuitMoonUserId) {
		this.cuitMoonUserRoleId = cuitMoonUserRoleId;
		this.cuitMoonUserId = cuitMoonUserId;
	}

	public TbcuitmoonUserrole(String cuitMoonUserRoleId, String cuitMoonUserId,
			String cuitMoonRoleId) {
		this.cuitMoonUserRoleId = cuitMoonUserRoleId;
		this.cuitMoonUserId = cuitMoonUserId;
		this.cuitMoonRoleId = cuitMoonRoleId;
	}

	public String getCuitMoonUserRoleId() {
		return this.cuitMoonUserRoleId;
	}

	public void setCuitMoonUserRoleId(String cuitMoonUserRoleId) {
		this.cuitMoonUserRoleId = cuitMoonUserRoleId;
	}

	public String getCuitMoonUserId() {
		return this.cuitMoonUserId;
	}

	public void setCuitMoonUserId(String cuitMoonUserId) {
		this.cuitMoonUserId = cuitMoonUserId;
	}

	public String getCuitMoonRoleId() {
		return this.cuitMoonRoleId;
	}

	public void setCuitMoonRoleId(String cuitMoonRoleId) {
		this.cuitMoonRoleId = cuitMoonRoleId;
	}

}
