package pb.repo.pcm.model;

import java.sql.Timestamp;

import pb.common.model.BaseDataModel;

public class PcmReqDtlModel extends BaseDataModel {
	
	Long id;
	String masterId;
	Integer actGrpId;
	String actGrp;
	String description;
	Double quantity;
	Integer unitId;
	String unit;
	Double price;
	Double total;
	Integer fiscalYear;
	
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
	
	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	
	public Integer getActGrpId() {
		return actGrpId;
	}

	public void setActGrpId(Integer actGrpId) {
		this.actGrpId = actGrpId;
	}

	public String getActGrp() {
		return actGrp;
	}

	public void setActGrp(String actGrp) {
		this.actGrp = actGrp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Integer getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(Integer fiscalYear) {
		this.fiscalYear = fiscalYear;
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
