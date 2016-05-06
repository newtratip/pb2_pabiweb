package pb.repo.admin.model;


public class MainCostControlModel {
	
	Long id;
	Long costControlTypeId;
	String description;
	String name;
	  
	private Long totalRowCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getCostControlTypeId() {
		return costControlTypeId;
	}

	public void setCostControlTypeId(Long costControlTypeId) {
		this.costControlTypeId = costControlTypeId;
	}

	public Long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

}
