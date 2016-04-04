package pb.repo.admin.model;


public class MainEmployeeBossModel {
	
	String costCenter;
	String level;
	String bossIds;
	  
	private Long totalRowCount;

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getBossIds() {
		return bossIds;
	}

	public void setBossIds(String bossIds) {
		this.bossIds = bossIds;
	}

	public Long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

}
