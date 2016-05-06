package pb.repo.admin.model;


public class MainHrEmployeeModel {
	
	Long id;
	String employeeCode;
	String title;
	String firstName;
	String lastName;
	String gender;
	String mobilePhone;
	String workEmail;
	String workPhone;
	String workLocation;
	Integer orgId;
	Integer costcenterId;
	Integer sectionId;
	Integer departmentId;
	byte[] image;
	byte[] imageSmall;
	String nameRelated;
	Integer positionId;
	Integer positionManagementId;
	Boolean isManagement;
	
	private Long totalRowCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getWorkEmail() {
		return workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getCostcenterId() {
		return costcenterId;
	}

	public void setCostcenterId(Integer costcenterId) {
		this.costcenterId = costcenterId;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public byte[] getImageSmall() {
		return imageSmall;
	}

	public void setImageSmall(byte[] imageSmall) {
		this.imageSmall = imageSmall;
	}
	
	public String getNameRelated() {
		return nameRelated;
	}

	public void setNameRelated(String nameRelated) {
		this.nameRelated = nameRelated;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getPositionManagementId() {
		return positionManagementId;
	}

	public void setPositionManagementId(Integer positionManagementId) {
		this.positionManagementId = positionManagementId;
	}

	public Boolean getIsManagement() {
		return isManagement;
	}

	public void setIsManagement(Boolean isManagement) {
		this.isManagement = isManagement;
	}

	public Long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

}
