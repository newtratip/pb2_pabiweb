package pb.repo.admin.model;


public class MainWkfConfigPurchaseUnitResponsibleModel {
	
	Long id;
	String level;
	Integer empId;
	Integer purchasingUnitId;
	  
	private Long totalRowCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getPurchasingUnitId() {
		return purchasingUnitId;
	}

	public void setPurchasingUnitId(Integer purchasingUnitId) {
		this.purchasingUnitId = purchasingUnitId;
	}

	public Long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

}
