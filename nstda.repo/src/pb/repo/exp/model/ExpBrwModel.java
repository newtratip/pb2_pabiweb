package pb.repo.exp.model;

import java.sql.Timestamp;
import java.util.List;

import pb.repo.admin.model.SubModuleModel;

public class ExpBrwModel extends SubModuleModel {
	
	String reqBy;
	Integer reqSectionId;
	
	String objectiveType;
	String objectiveTypeName;
	String objective;
	String reason;
	
	Integer budgetCc;
	String budgetCcType;
	String budgetCcName;
	String budgetCcTypeName;
	
	Integer costControlTypeId;
	Integer costControlId;
	String costControl;
	Timestamp costControlFrom;
	Timestamp costControlTo;
	
	Double total;
	
	String bankType;
	Integer bank;
	
	Integer rewarning;
	Integer waitingDay;
	String status;
	String wfStatus;
	
	List<ExpBrwDtlModel> dtlList;
	List<ExpBrwAttendeeModel> attendeeList;
	
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
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
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
	public String getCostControl() {
		return costControl;
	}
	public void setCostControl(String costControl) {
		this.costControl = costControl;
	}
	
}
