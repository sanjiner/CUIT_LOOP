package edu.cuit.module.sys.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;

// Generated 2015-4-13 16:59:09 by Hibernate Tools 4.3.1

/**
 * TbcuitmoonRole generated by hbm2java
 */
public class TbcuitmoonRole implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6159474041608633859L;
	private String cuitMoonRoleId;
	@NotBlank(message="名称不能为空")
	private String cuitMoonRoleName;
	private String cuitMoonParentRoleId;
	/*自己添加的*/
	private String cuitMoonParentRoleName;
	private Long cuitMoonRoleStatus;
	private Integer cuitMoonRoleOrderNum = 1;
	private String cuitMoonRoleDescription;
	private String cuitMoonRoleRemarks;
	private Integer cuitMoonRoleLevel;
	private Set<TbcuitmoonModule> tbcuitmoonModules = new HashSet<TbcuitmoonModule>();
	private Set<TbcuitmoonUser> tbcuitmoonUsers = new HashSet<TbcuitmoonUser>();
	private Set<TbcuitmoonJurisdiction> tbcuitmoonJurisdictions = new HashSet<TbcuitmoonJurisdiction>();
	private List<TbcuitmoonRole> childRoles = new ArrayList<TbcuitmoonRole>();
	public TbcuitmoonRole() {
	}

	public TbcuitmoonRole(String cuitMoonRoleId, String cuitMoonRoleName) {
		this.cuitMoonRoleId = cuitMoonRoleId;
		this.cuitMoonRoleName = cuitMoonRoleName;
	}

	public TbcuitmoonRole(String cuitMoonRoleId, String cuitMoonRoleName,
			String cuitMoonParentRoleId, Long cuitMoonRoleStatus,
			Integer cuitMoonRoleOrderNum, String cuitMoonRoleDescription,
			String cuitMoonRoleRemarks, Integer cuitMoonRoleLevel) {
		this.cuitMoonRoleId = cuitMoonRoleId;
		this.cuitMoonRoleName = cuitMoonRoleName;
		this.cuitMoonParentRoleId = cuitMoonParentRoleId;
		this.cuitMoonRoleStatus = cuitMoonRoleStatus;
		this.cuitMoonRoleOrderNum = cuitMoonRoleOrderNum;
		this.cuitMoonRoleDescription = cuitMoonRoleDescription;
		this.cuitMoonRoleRemarks = cuitMoonRoleRemarks;
		this.cuitMoonRoleLevel = cuitMoonRoleLevel;
	}

	public String getCuitMoonRoleId() {
		return this.cuitMoonRoleId;
	}

	public void setCuitMoonRoleId(String cuitMoonRoleId) {
		this.cuitMoonRoleId = cuitMoonRoleId;
	}

	public String getCuitMoonRoleName() {
		return this.cuitMoonRoleName;
	}

	public void setCuitMoonRoleName(String cuitMoonRoleName) {
		this.cuitMoonRoleName = cuitMoonRoleName;
	}

	public String getCuitMoonParentRoleId() {
		return this.cuitMoonParentRoleId;
	}

	public void setCuitMoonParentRoleId(String cuitMoonParentRoleId) {
		this.cuitMoonParentRoleId = cuitMoonParentRoleId;
	}

	public Long getCuitMoonRoleStatus() {
		return this.cuitMoonRoleStatus;
	}

	public void setCuitMoonRoleStatus(Long cuitMoonRoleStatus) {
		this.cuitMoonRoleStatus = cuitMoonRoleStatus;
	}

	public Integer getCuitMoonRoleOrderNum() {
		return this.cuitMoonRoleOrderNum;
	}

	public void setCuitMoonRoleOrderNum(Integer cuitMoonRoleOrderNum) {
		this.cuitMoonRoleOrderNum = cuitMoonRoleOrderNum;
	}

	public String getCuitMoonRoleDescription() {
		return this.cuitMoonRoleDescription;
	}

	public void setCuitMoonRoleDescription(String cuitMoonRoleDescription) {
		this.cuitMoonRoleDescription = cuitMoonRoleDescription;
	}

	public String getCuitMoonRoleRemarks() {
		return this.cuitMoonRoleRemarks;
	}

	public void setCuitMoonRoleRemarks(String cuitMoonRoleRemarks) {
		this.cuitMoonRoleRemarks = cuitMoonRoleRemarks;
	}

	public Integer getCuitMoonRoleLevel() {
		return this.cuitMoonRoleLevel;
	}

	public void setCuitMoonRoleLevel(Integer cuitMoonRoleLevel) {
		this.cuitMoonRoleLevel = cuitMoonRoleLevel;
	}

	/**
	 * @return the tbcuitmoonUsers
	 */
	public Set<TbcuitmoonUser> getTbcuitmoonUsers() {
		return tbcuitmoonUsers;
	}

	/**
	 * @param tbcuitmoonUsers the tbcuitmoonUsers to set
	 */
	public void setTbcuitmoonUsers(Set<TbcuitmoonUser> tbcuitmoonUsers) {
		this.tbcuitmoonUsers = tbcuitmoonUsers;
	}

	/**
	 * @return the tbcuitmoonJurisdictions
	 */
	public Set<TbcuitmoonJurisdiction> getTbcuitmoonJurisdictions() {
		return tbcuitmoonJurisdictions;
	}

	/**
	 * @param tbcuitmoonJurisdictions the tbcuitmoonJurisdictions to set
	 */
	public void setTbcuitmoonJurisdictions(
			Set<TbcuitmoonJurisdiction> tbcuitmoonJurisdictions) {
		this.tbcuitmoonJurisdictions = tbcuitmoonJurisdictions;
	}

	/**
	 * @return the cuitMoonParentRoleName
	 */
	public String getCuitMoonParentRoleName() {
		return cuitMoonParentRoleName;
	}

	/**
	 * @param cuitMoonParentRoleName the cuitMoonParentRoleName to set
	 */
	public void setCuitMoonParentRoleName(String cuitMoonParentRoleName) {
		this.cuitMoonParentRoleName = cuitMoonParentRoleName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the tbcuitmoonModules
	 */
	public Set<TbcuitmoonModule> getTbcuitmoonModules() {
		return tbcuitmoonModules;
	}

	/**
	 * @param tbcuitmoonModules the tbcuitmoonModules to set
	 */
	public void setTbcuitmoonModules(Set<TbcuitmoonModule> tbcuitmoonModules) {
		this.tbcuitmoonModules = tbcuitmoonModules;
	}

	/**
	 * @return the childRoles
	 */
	public List<TbcuitmoonRole> getChildRoles() {
		return childRoles;
	}

	/**
	 * @param childRoles the childRoles to set
	 */
	public void setChildRoles(List<TbcuitmoonRole> childRoles) {
		this.childRoles = childRoles;
	}
	

}
