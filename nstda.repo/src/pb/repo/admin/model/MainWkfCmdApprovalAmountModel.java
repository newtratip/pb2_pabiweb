package pb.repo.admin.model;


public class MainWkfCmdApprovalAmountModel {
	
	Long id;
	Integer level;
	Integer orgId;
	Integer doctypeId;
	Double amountMin;
	Double amountMax;
	
	private Long totalRowCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getDoctypeId() {
		return doctypeId;
	}

	public void setDoctypeId(Integer doctypeId) {
		this.doctypeId = doctypeId;
	}
	
	public Double getAmountMin() {
		return amountMin;
	}

	public void setAmountMin(Double amountMin) {
		this.amountMin = amountMin;
	}

	public Double getAmountMax() {
		return amountMax;
	}

	public void setAmountMax(Double amountMax) {
		this.amountMax = amountMax;
	}

	public Long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

}
