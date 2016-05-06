package pb.repo.admin.model;


public class MainWkfCmdSpecialAmountProjectApprovalModel {
	
	Long id;
	Integer empId;
	Integer doctypeId;
	Double minimum;
	Double maximum;
	
	private Long totalRowCount;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getDoctypeId() {
		return doctypeId;
	}

	public void setDoctypeId(Integer doctypeId) {
		this.doctypeId = doctypeId;
	}

	public Double getMinimum() {
		return minimum;
	}

	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	public Double getMaximum() {
		return maximum;
	}

	public void setMaximum(Double maximum) {
		this.maximum = maximum;
	}

	public Long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

}
