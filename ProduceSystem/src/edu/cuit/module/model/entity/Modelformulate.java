package edu.cuit.module.model.entity;

// Generated 2015-4-14 21:24:14 by Hibernate Tools 4.3.1

/**
 * Modelformulate generated by hbm2java
 */
public class Modelformulate implements java.io.Serializable {

	private String productApproModelId;
	private String productNumber;
	private String modelType;
	private String modelName;
	private String modelDescription;
	private String remark;
	private String modelTypeAclass;
	private String modelTypeBclass;
	private String growthPeriod;
	private String element;

	public Modelformulate() {
	}

	public Modelformulate(String productApproModelId, String modelName) {
		this.productApproModelId = productApproModelId;
		this.modelName = modelName;
	}

	public Modelformulate(String productApproModelId, String productNumber,
			String modelType, String modelName, String modelDescription,
			String remark, String modelTypeAclass, String modelTypeBclass,
			String growthPeriod, String element) {
		this.productApproModelId = productApproModelId;
		this.productNumber = productNumber;
		this.modelType = modelType;
		this.modelName = modelName;
		this.modelDescription = modelDescription;
		this.remark = remark;
		this.modelTypeAclass = modelTypeAclass;
		this.modelTypeBclass = modelTypeBclass;
		this.growthPeriod = growthPeriod;
		this.element = element;
	}

	public String getProductApproModelId() {
		return this.productApproModelId;
	}

	public void setProductApproModelId(String productApproModelId) {
		this.productApproModelId = productApproModelId;
	}

	public String getProductNumber() {
		return this.productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelDescription() {
		return this.modelDescription;
	}

	public void setModelDescription(String modelDescription) {
		this.modelDescription = modelDescription;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModelTypeAclass() {
		return this.modelTypeAclass;
	}

	public void setModelTypeAclass(String modelTypeAclass) {
		this.modelTypeAclass = modelTypeAclass;
	}

	public String getModelTypeBclass() {
		return this.modelTypeBclass;
	}

	public void setModelTypeBclass(String modelTypeBclass) {
		this.modelTypeBclass = modelTypeBclass;
	}

	public String getGrowthPeriod() {
		return this.growthPeriod;
	}

	public void setGrowthPeriod(String growthPeriod) {
		this.growthPeriod = growthPeriod;
	}

	public String getElement() {
		return this.element;
	}

	public void setElement(String element) {
		this.element = element;
	}

}
