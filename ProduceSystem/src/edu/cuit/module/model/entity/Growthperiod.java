package edu.cuit.module.model.entity;

import java.util.ArrayList;
import java.util.List;

// Generated 2015-7-8 17:46:03 by Hibernate Tools 4.3.1

/**
 * Growthperiod generated by hbm2java
 */
public class Growthperiod implements java.io.Serializable {

	private String growthId;
	private String growthName;
	private String produceApproModelId;
	private List<?> growthElemList = new ArrayList<>();

	public Growthperiod() {
	}

	public Growthperiod(String growthId) {
		this.growthId = growthId;
	}

	public Growthperiod(String growthId, String growthName,
			String produceApproModelId) {
		this.growthId = growthId;
		this.growthName = growthName;
		this.produceApproModelId = produceApproModelId;
	}

	public String getGrowthId() {
		return this.growthId;
	}

	public void setGrowthId(String growthId) {
		this.growthId = growthId;
	}

	public String getGrowthName() {
		return this.growthName;
	}

	public void setGrowthName(String growthName) {
		this.growthName = growthName;
	}

	public String getProduceApproModelId() {
		return this.produceApproModelId;
	}

	public void setProduceApproModelId(String produceApproModelId) {
		this.produceApproModelId = produceApproModelId;
	}

	/**
	 * @return the growthElemList
	 */
	public List<?> getGrowthElemList() {
		return growthElemList;
	}

	/**
	 * @param growthElemList the growthElemList to set
	 */
	public void setGrowthElemList(List<?> growthElemList) {
		this.growthElemList = growthElemList;
	}

}
