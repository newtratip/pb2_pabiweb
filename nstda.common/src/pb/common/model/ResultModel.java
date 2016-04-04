package pb.common.model;

public class ResultModel {
	private Boolean success;
	private Object data;
	private Long total;
	
	public ResultModel() {
	}
	
	public ResultModel(Boolean success, Object data) {
		this.success = success;
		this.data = data;
	}
	
	public ResultModel(Boolean success, Object data, Long total) {
		this(success,data);
		this.total = total;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
}
