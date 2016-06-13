package pb.repo.exp.model;

import java.sql.Timestamp;
import java.util.List;

import pb.repo.admin.model.SubModuleModel;

public class ExpUseModel extends SubModuleModel {
	
	String reqBy;
	Integer reqSectionId;
	
	String objective;
	
	Integer budgetCc;
	String budgetCcType;
	String budgetCcName;
	String budgetCcTypeName;
	
	Integer costControlTypeId;
	Integer costControlId;
	String costControl;
	Timestamp costControlFrom;
	Timestamp costControlTo;

	String bankType;
	Integer bank;

	Double vat;
	Integer vatId;
	
	String payType;
	String payTypeName;
	String payDtl1;
	String payDtl2;
	String payDtl3;
	
	String ichargeName;
	String ichargeTypeName;
	
	Double total;
	
	Integer rewarning;
	Integer waitingDay;
	String status;
	String wfStatus;
	
	List<ExpUseVoyagerModel> voyagerList;
	List<ExpUseDtlModel> dtlList;
	
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
	public String getReqBy() {
		return reqBy;
	}
	public void setReqBy(String reqBy) {
		this.reqBy = reqBy;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
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
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getVat() {
		return vat;
	}
	public void setVat(Double vat) {
		this.vat = vat;
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
	public Integer getCostControlTypeId() {
		return costControlTypeId;
	}
	public void setCostControlTypeId(Integer costControlTypeId) {
		this.costControlTypeId = costControlTypeId;
	}
	public Integer getCostControlId() {
		return costControlId;
	}
	public void setCostControlId(Integer costControlId) {
		this.costControlId = costControlId;
	}
	public List<ExpUseVoyagerModel> getVoyagerList() {
		return voyagerList;
	}
	public void setVoyagerList(List<ExpUseVoyagerModel> voyagerList) {
		this.voyagerList = voyagerList;
	}
	public String getCostControl() {
		return costControl;
	}
	public void setCostControl(String costControl) {
		this.costControl = costControl;
	}
	public Timestamp getCostControlFrom() {
		return costControlFrom;
	}
	public void setCostControlFrom(Timestamp costControlFrom) {
		this.costControlFrom = costControlFrom;
	}
	public Timestamp getCostControlTo() {
		return costControlTo;
	}
	public void setCostControlTo(Timestamp costControlTo) {
		this.costControlTo = costControlTo;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public Integer getBank() {
		return bank;
	}
	public void setBank(Integer bank) {
		this.bank = bank;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayTypeName() {
		return payTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}
	public String getPayDtl1() {
		return payDtl1;
	}
	public void setPayDtl1(String payDtl1) {
		this.payDtl1 = payDtl1;
	}
	public String getPayDtl2() {
		return payDtl2;
	}
	public void setPayDtl2(String payDtl2) {
		this.payDtl2 = payDtl2;
	}
	public String getPayDtl3() {
		return payDtl3;
	}
	public void setPayDtl3(String payDtl3) {
		this.payDtl3 = payDtl3;
	}
	public String getIchargeName() {
		return ichargeName;
	}
	public void setIchargeName(String ichargeName) {
		this.ichargeName = ichargeName;
	}
	public String getIchargeTypeName() {
		return ichargeTypeName;
	}
	public void setIchargeTypeName(String ichargeTypeName) {
		this.ichargeTypeName = ichargeTypeName;
	}
	public List<ExpUseDtlModel> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<ExpUseDtlModel> dtlList) {
		this.dtlList = dtlList;
	}
	
}
