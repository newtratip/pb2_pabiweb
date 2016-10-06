package pb.repo.exp.model;

import java.sql.Timestamp;
import java.util.List;

import pb.repo.admin.model.SubModuleModel;

public class ExpBrwModel extends SubModuleModel {
	
	String reqBy;
//	Integer reqSectionId;
	
	String objectiveType;
	String objectiveTypeName;
	String objective;
	String reason;
	
	Integer budgetCc;
	String budgetCcType;
	String budgetCcName;
	String budgetCcTypeName;
	
	Integer fundId;
	String fundName;
	
	Integer costControlTypeId;
	Integer costControlId;
	String costControlName;
	String costControlTypeName;

	Timestamp dateBack;
	
	Double total;
	
	String bankType;
	Integer bank;
	
	Integer rewarning;
	Integer waitingDay;
	String status;
	String wfBy;
	String wfByTime;
	String wfStatus;
	
	List<ExpBrwDtlModel> dtlList;
	List<ExpBrwAttendeeModel> attendeeList;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWfBy() {
		return wfBy;
	}
	public void setWfBy(String wfBy) {
		this.wfBy = wfBy;
	}
	public String getWfByTime() {
		return wfByTime;
	}
	public void setWfByTime(String wfByTime) {
		this.wfByTime = wfByTime;
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
	public String getObjectiveType() {
		return objectiveType;
	}
	public void setObjectiveType(String objectiveType) {
		this.objectiveType = objectiveType;
	}
	public String getObjectiveTypeName() {
		return objectiveTypeName;
	}
	public void setObjectiveTypeName(String objectiveTypeName) {
		this.objectiveTypeName = objectiveTypeName;
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
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
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
	public List<ExpBrwDtlModel> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<ExpBrwDtlModel> dtlList) {
		this.dtlList = dtlList;
	}
	public List<ExpBrwAttendeeModel> getAttendeeList() {
		return attendeeList;
	}
	public void setAttendeeList(List<ExpBrwAttendeeModel> attendeeList) {
		this.attendeeList = attendeeList;
	}
	public String getCostControlName() {
		return costControlName;
	}
	public void setCostControlName(String costControlName) {
		this.costControlName = costControlName;
	}
	public String getCostControlTypeName() {
		return costControlTypeName;
	}
	public void setCostControlTypeName(String costControlTypeName) {
		this.costControlTypeName = costControlTypeName;
	}
	public Timestamp getDateBack() {
		return dateBack;
	}
	public void setDateBack(Timestamp dateBack) {
		this.dateBack = dateBack;
	}
	
}
