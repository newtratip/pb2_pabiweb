package pb.repo.pcm.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PcmReqModel {
	
	String id;
	
	String reqBy;
	String reqBu;
	String reqOu;
	
	String objectiveType;
	String objective;
	String reason;
	
	String currency;
	Double currencyRate;

	String budgetCc;
	String stockOu;
	
	String prototype;
	String event;
	
	String pcmOu;
	String location;

	Double acrossBudget;
	String refId;

	String method;
	String methodCond2Rule;
	String methodCond2;
	String methodCond2Dtl;
	
	Double total;
	
	String docRef;
	String folderRef;
	
	Integer rewarning;
	Integer waitingDay;
	Integer waitingLevel;
	String status;
	String wfStatus;
	
	String workflowInsId;
	
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
	public String getWorkflowInsId() {
		return workflowInsId;
	}
	public void setWorkflowInsId(String workflowInsId) {
		this.workflowInsId = workflowInsId;
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
	public String getReqBy() {
		return reqBy;
	}
	public void setReqBy(String reqBy) {
		this.reqBy = reqBy;
	}
	public String getReqBu() {
		return reqBu;
	}
	public void setReqBu(String reqBu) {
		this.reqBu = reqBu;
	}
	public String getReqOu() {
		return reqOu;
	}
	public void setReqOu(String reqOu) {
		this.reqOu = reqOu;
	}
	public String getObjectiveType() {
		return objectiveType;
	}
	public void setObjectiveType(String objectiveType) {
		this.objectiveType = objectiveType;
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
	public String getBudgetCc() {
		return budgetCc;
	}
	public void setBudgetCc(String budgetCc) {
		this.budgetCc = budgetCc;
	}
	public String getStockOu() {
		return stockOu;
	}
	public void setStockOu(String stockOu) {
		this.stockOu = stockOu;
	}
	public String getPrototype() {
		return prototype;
	}
	public void setPrototype(String prototype) {
		this.prototype = prototype;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getPcmOu() {
		return pcmOu;
	}
	public void setPcmOu(String pcmOu) {
		this.pcmOu = pcmOu;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Double getAcrossBudget() {
		return acrossBudget;
	}
	public void setAcrossBudget(Double acrossBudget) {
		this.acrossBudget = acrossBudget;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethodCond2Rule() {
		return methodCond2Rule;
	}
	public void setMethodCond2Rule(String methodCond2Rule) {
		this.methodCond2Rule = methodCond2Rule;
	}
	public String getMethodCond2() {
		return methodCond2;
	}
	public void setMethodCond2(String methodCond2) {
		this.methodCond2 = methodCond2;
	}
	public String getMethodCond2Dtl() {
		return methodCond2Dtl;
	}
	public void setMethodCond2Dtl(String methodCond2Dtl) {
		this.methodCond2Dtl = methodCond2Dtl;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
}
