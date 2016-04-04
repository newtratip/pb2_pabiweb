package pb.repo.pcm.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PcmOrdModel {
	
	String id;
	String refId;
	String reqType;
	
	String purType;
	
	String domestic;
	String currency;
	Double currencyRate;
	String objective;
	String reason;
	
	String costType;
	String reqOu;
	String stockOu;
	Timestamp needDate;
	
	Double total;
	String remark;
	
	String docRef;
	String folderRef;
	
	Integer rewarning;
	Integer waitingDay;
	Integer waitingLevel;
	String status;
	String wfStatus;
	
	String workflowId;
	String workflowInsId;
	Long approvalMatrixId;
	
	Timestamp requestedTime;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDocRef() {
		return docRef;
	}
	public void setDocRef(String docRef) {
		this.docRef = docRef;
	}
	public String getFolderRef() {
		return folderRef;
	}
	public void setFolderRef(String folderRef) {
		this.folderRef = folderRef;
	}
	public Integer getWaitingLevel() {
		return waitingLevel;
	}
	public void setWaitingLevel(Integer waitingLevel) {
		this.waitingLevel = waitingLevel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	public String getWorkflowInsId() {
		return workflowInsId;
	}
	public void setWorkflowInsId(String workflowInsId) {
		this.workflowInsId = workflowInsId;
	}
	public Long getApprovalMatrixId() {
		return approvalMatrixId;
	}
	public void setApprovalMatrixId(Long approvalMatrixId) {
		this.approvalMatrixId = approvalMatrixId;
	}
	public Timestamp getRequestedTime() {
		return requestedTime;
	}
	public void setRequestedTime(Timestamp requestedTime) {
		this.requestedTime = requestedTime;
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
	public String getWfStatus() {
		return wfStatus;
	}
	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}
	public List<String> getListAttachDoc() {
		return listAttachDoc;
	}
	public void setListAttachDoc(List<String> listAttachDoc) {
		this.listAttachDoc = listAttachDoc;
	}
	public void setAttachDoc(String doc) {
		if(this.listAttachDoc == null) {
			this.listAttachDoc = new ArrayList<String>();
		}
		this.listAttachDoc.add(doc);
	}
	public Integer getRewarning() {
		return rewarning;
	}
	public void setRewarning(Integer rewarning) {
		this.rewarning = rewarning;
	}
	public Integer getWaitingDay() {
		return waitingDay;
	}
	public void setWaitingDay(Integer waitingDay) {
		this.waitingDay = waitingDay;
	}
	public Boolean getOverDue() {
		return (rewarning!=null) && (rewarning > 0) && (waitingDay > rewarning);
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getPurType() {
		return purType;
	}
	public void setPurType(String purType) {
		this.purType = purType;
	}
	public String getDomestic() {
		return domestic;
	}
	public void setDomestic(String domestic) {
		this.domestic = domestic;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getReqOu() {
		return reqOu;
	}
	public void setReqOu(String reqOu) {
		this.reqOu = reqOu;
	}
	public String getStockOu() {
		return stockOu;
	}
	public void setStockOu(String stockOu) {
		this.stockOu = stockOu;
	}
	public Timestamp getNeedDate() {
		return needDate;
	}
	public void setNeedDate(Timestamp needDate) {
		this.needDate = needDate;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	
}
