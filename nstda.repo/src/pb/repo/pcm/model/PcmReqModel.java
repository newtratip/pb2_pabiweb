package pb.repo.pcm.model;

import java.sql.Timestamp;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;

import pb.repo.admin.model.SubModuleModel;

public class PcmReqModel extends SubModuleModel {
	
	String reqBy;
	Integer reqSectionId;
	
	String objectiveType;
	String objective;
	String reason;
	
	String currency;
	Double currencyRate;

	Integer budgetCc;
	String budgetCcType;
	String budgetCcName;
	String budgetCcTypeName;
	Integer fundId;
	String fundName;
	
	String isStock;
	Integer stockSectionId;
	
	String isPrototype;
	String prototype;
	String prototypeContractNo;
	
	Integer costControlTypeId;
	String costControlTypeName;
	Integer costControlId;
	String costControlName;
	
	Integer pcmSectionId;
	Integer pcmOrgId;
	String location;

	String isAcrossBudget;
	Double acrossBudget;
	
	String isRefId;
	String refId;
	String refDocRef;

	Long prWebMethodId;
	String methodCond2Rule;
	String methodCond2;
	String methodCond2Dtl;
	
	Double vat;
	Integer vatId;
	
	Double total;
	Double totalCnv;
	
	Timestamp contractDate;
	
	Integer rewarning;
	Integer waitingDay;
	String status;
	String wfStatus;
	
	List<PcmReqDtlModel> dtlList;
	List<PcmReqCmtHdrModel> cmtList;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWfStatus() {
		return wfStatus;
	}
	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
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
	public String getBudgetCcType() {
		return budgetCcType;
	}
	public void setBudgetCcType(String budgetCcType) {
		this.budgetCcType = budgetCcType;
	}
	public Integer getBudgetCc() {
		return budgetCc;
	}
	public void setBudgetCc(Integer budgetCc) {
		this.budgetCc = budgetCc;
	}
	public String getBudgetCcName() {
		return budgetCcName;
	}
	public void setBudgetCcName(String budgetCcName) {
		this.budgetCcName = budgetCcName;
	}
	public String getBudgetCcTypeName() {
		return budgetCcTypeName;
	}
	public void setBudgetCcTypeName(String budgetCcTypeName) {
		this.budgetCcTypeName = budgetCcTypeName;
	}
	public Integer getFundId() {
		return fundId;
	}
	public void setFundId(Integer fundId) {
		this.fundId = fundId;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getPrototype() {
		return prototype;
	}
	public void setPrototype(String prototype) {
		this.prototype = prototype;
	}
	public String getPrototypeContractNo() {
		return prototypeContractNo;
	}
	public void setPrototypeContractNo(String prototypeContractNo) {
		this.prototypeContractNo = prototypeContractNo;
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
	public Long getPrWebMethodId() {
		return prWebMethodId;
	}
	public void setPrWebMethodId(Long prWebMethodId) {
		this.prWebMethodId = prWebMethodId;
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
	public Double getTotalCnv() {
		return totalCnv;
	}
	public void setTotalCnv(Double totalCnv) {
		this.totalCnv = totalCnv;
	}
	public Double getVat() {
		return vat;
	}
	public void setVat(Double vat) {
		this.vat = vat;
	}
	public String getIsStock() {
		return isStock;
	}
	public void setIsStock(String isStock) {
		this.isStock = isStock;
	}
	public String getIsPrototype() {
		return isPrototype;
	}
	public void setIsPrototype(String isPrototype) {
		this.isPrototype = isPrototype;
	}
	public String getIsAcrossBudget() {
		return isAcrossBudget;
	}
	public void setIsAcrossBudget(String isAcrossBudget) {
		this.isAcrossBudget = isAcrossBudget;
	}
	public String getIsRefId() {
		return isRefId;
	}
	public void setIsRefId(String isRefId) {
		this.isRefId = isRefId;
	}
	public String getRefDocRef() {
		return refDocRef;
	}
	public void setRefDocRef(String refDocRef) {
		this.refDocRef = refDocRef;
	}
	public Integer getVatId() {
		return vatId;
	}
	public void setVatId(Integer vatId) {
		this.vatId = vatId;
	}
	public Integer getReqSectionId() {
		return reqSectionId;
	}
	public void setReqSectionId(Integer reqSectionId) {
		this.reqSectionId = reqSectionId;
	}
	public Integer getStockSectionId() {
		return stockSectionId;
	}
	public void setStockSectionId(Integer stockSectionId) {
		this.stockSectionId = stockSectionId;
	}
	public Integer getPcmSectionId() {
		return pcmSectionId;
	}
	public void setPcmSectionId(Integer pcmSectionId) {
		this.pcmSectionId = pcmSectionId;
	}
	public Integer getPcmOrgId() {
		return pcmOrgId;
	}
	public void setPcmOrgId(Integer pcmOrgId) {
		this.pcmOrgId = pcmOrgId;
	}
	public Integer getCostControlTypeId() {
		return costControlTypeId;
	}
	public void setCostControlTypeId(Integer costControlTypeId) {
		this.costControlTypeId = costControlTypeId;
	}
	public String getCostControlTypeName() {
		return costControlTypeName;
	}
	public void setCostControlTypeName(String costControlTypeName) {
		this.costControlTypeName = costControlTypeName;
	}
	public Integer getCostControlId() {
		return costControlId;
	}
	public void setCostControlId(Integer costControlId) {
		this.costControlId = costControlId;
	}
	public String getCostControlName() {
		return costControlName;
	}
	public void setCostControlName(String costControlName) {
		this.costControlName = costControlName;
	}
	public Timestamp getContractDate() {
		return contractDate;
	}
	public void setContractDate(Timestamp contractDate) {
		this.contractDate = contractDate;
	}
	public List<PcmReqDtlModel> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<PcmReqDtlModel> dtlList) {
		this.dtlList = dtlList;
	}
	public List<PcmReqCmtHdrModel> getCmtList() {
		return cmtList;
	}
	public void setCmtList(List<PcmReqCmtHdrModel> cmtList) {
		this.cmtList = cmtList;
	}
	
}
