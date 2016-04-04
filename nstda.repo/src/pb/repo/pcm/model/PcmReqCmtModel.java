package pb.repo.pcm.model;

import java.sql.Timestamp;
import java.util.List;

public class PcmReqCmtModel {
	
	String id;
	String objType;
	String method;
	
	String cond1;
	String cond2;
	
	String committee1;
	String committee2;
	String committee3;
	String committee4;
	
	Double amountForm;
	Double amountTo;
	
	Timestamp createdTime;
	String createdBy;
	Timestamp updatedTime;
	String updatedBy;
	
	List<String> listAttachDoc;

	private Long totalRowCount;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getObjType() {
		return objType;
	}
	public void setObjType(String objType) {
		this.objType = objType;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getCond1() {
		return cond1;
	}
	public void setCond1(String cond1) {
		this.cond1 = cond1;
	}
	public String getCond2() {
		return cond2;
	}
	public void setCond2(String cond2) {
		this.cond2 = cond2;
	}
	public String getCommittee1() {
		return committee1;
	}
	public void setCommittee1(String committee1) {
		this.committee1 = committee1;
	}
	public String getCommittee2() {
		return committee2;
	}
	public void setCommittee2(String committee2) {
		this.committee2 = committee2;
	}
	public String getCommittee3() {
		return committee3;
	}
	public void setCommittee3(String committee3) {
		this.committee3 = committee3;
	}
	public String getCommittee4() {
		return committee4;
	}
	public void setCommittee4(String committee4) {
		this.committee4 = committee4;
	}
	public Double getAmountForm() {
		return amountForm;
	}
	public void setAmountForm(Double amountForm) {
		this.amountForm = amountForm;
	}
	public Double getAmountTo() {
		return amountTo;
	}
	public void setAmountTo(Double amountTo) {
		this.amountTo = amountTo;
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
