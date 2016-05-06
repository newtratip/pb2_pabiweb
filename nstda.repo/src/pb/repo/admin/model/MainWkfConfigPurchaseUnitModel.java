package pb.repo.admin.model;


public class MainWkfConfigPurchaseUnitModel {
	
	Long id;
	String description;
	String name;
	Integer ordId;
	Integer doctypeId;
	  
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
	
	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public Integer getDoctypeId() {
		return doctypeId;
	}

	public void setDoctypeId(Integer doctypeId) {
		this.doctypeId = doctypeId;
	}

	public Long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

}
