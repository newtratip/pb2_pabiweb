package pb.repo.pcm.model;

import pb.repo.admin.model.SubModuleModel;

public class PcmOrdModel extends SubModuleModel {
	
	String objective;
	Integer sectionId;
	String prId;
	String docType;
	String appBy;
	
	Double total;
	
	String status;
	String wfBy;
	String wfByTime;
	String wfStatus;
	String orgName;
	
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public String getPrId() {
		return prId;
	}
	public void setPrId(String prId) {
		this.prId = prId;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
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
	public String getAppBy() {
		return appBy;
	}
	public void setAppBy(String appBy) {
		this.appBy = appBy;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
