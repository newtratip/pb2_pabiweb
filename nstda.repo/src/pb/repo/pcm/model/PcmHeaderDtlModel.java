package pb.repo.pcm.model;

import java.sql.Timestamp;

public class PcmHeaderDtlModel {
	
	Long id;
	Long masterId;
	Integer orderNo;
	Boolean mandatory;
	Boolean editDisable;
	String fieldName;
	String fieldType;
	Integer fieldLength;
	
	String value;
	
	String displayName;
	String displayType;
	Integer displayWidth;
	Integer displayHeight;
	String displayFormat;
	
	String saveToField;
	
	String srcUrl;
	String srcParam;
	String spcParam;
	String triggerField;
	
	Timestamp createdTime;
	String createdBy;
	Timestamp updatedTime;
	String updatedBy;

	private Long totalRowCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public Integer getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(Integer fieldLength) {
		this.fieldLength = fieldLength;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public Integer getDisplayWidth() {
		return displayWidth;
	}

	public void setDisplayWidth(Integer displayWidth) {
		this.displayWidth = displayWidth;
	}

	public String getDisplayFormat() {
		return displayFormat;
	}

	public void setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
	}

	public String getSaveToField() {
		return saveToField;
	}

	public void setSaveToField(String saveToField) {
		this.saveToField = saveToField;
	}
	
	public Boolean getEditDisable() {
		return editDisable;
	}

	public void setEditDisable(Boolean editDisable) {
		this.editDisable = editDisable;
	}

	public Integer getDisplayHeight() {
		return displayHeight;
	}

	public void setDisplayHeight(Integer displayHeight) {
		this.displayHeight = displayHeight;
	}

	public String getSrcUrl() {
		return srcUrl;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	public String getSrcParam() {
		return srcParam;
	}

	public void setSrcParam(String srcParam) {
		this.srcParam = srcParam;
	}

	public String getSpcParam() {
		return spcParam;
	}

	public void setSpcParam(String spcParam) {
		this.spcParam = spcParam;
	}

	public String getTriggerField() {
		return triggerField;
	}

	public void setTriggerField(String triggerField) {
		this.triggerField = triggerField;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

}
