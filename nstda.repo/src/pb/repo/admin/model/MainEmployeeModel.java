package pb.repo.admin.model;


public class MainEmployeeModel {
	
	Long id;
	String defaultCostCenter;
	String assignCostCenterIds;
	  
	private Long totalRowCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDefaultCostCenter() {
		return defaultCostCenter;
	}

	public void setDefaultCostCenter(String defaultCostCenter) {
		this.defaultCostCenter = defaultCostCenter;
	}

	public String getAssignCostCenterIds() {
		return assignCostCenterIds;
	}

	public void setAssignCostCenterIds(String assignCostCenterIds) {
		this.assignCostCenterIds = assignCostCenterIds;
	}

	public Long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

}
