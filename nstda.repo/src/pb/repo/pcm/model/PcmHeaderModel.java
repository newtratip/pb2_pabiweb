package pb.repo.pcm.model;

import java.sql.Timestamp;

public class PcmHeaderModel {
	
	Long id;
	String name;
	Integer labelWidth;
	String cond1Type;
	String cond1Detail;
	String cond2Type;
	String cond2Detail;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(Integer labelWidth) {
		this.labelWidth = labelWidth;
	}
	public String getCond1Type() {
		return cond1Type;
	}
	public void setCond1Type(String cond1Type) {
		this.cond1Type = cond1Type;
	}
	public String getCond1Detail() {
		return cond1Detail;
	}
	public void setCond1Detail(String cond1Detail) {
		this.cond1Detail = cond1Detail;
	}
	public String getCond2Type() {
		return cond2Type;
	}
	public void setCond2Type(String cond2Type) {
		this.cond2Type = cond2Type;
	}
	public String getCond2Detail() {
		return cond2Detail;
	}
	public void setCond2Detail(String cond2Detail) {
		this.cond2Detail = cond2Detail;
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
