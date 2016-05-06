package pb.repo.admin.model;


public class MainPurchasingUnitSectionRelModel {
	
	Integer purchasingInitId;
	Integer sectionId;
	  
	private Long totalRowCount;
	
	public Integer getPurchasingInitId() {
		return purchasingInitId;
	}

	public void setPurchasingInitId(Integer purchasingInitId) {
		this.purchasingInitId = purchasingInitId;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

}
