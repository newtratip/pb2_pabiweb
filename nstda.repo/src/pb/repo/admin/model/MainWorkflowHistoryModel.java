package pb.repo.admin.model;

import java.sql.Timestamp;

import org.json.JSONObject;

public class MainWorkflowHistoryModel {

	Long id;
	Long masterId;
	Timestamp time;
	String user_;
	String action;
	String task;
	String comment;
	Integer level;
	
    public MainWorkflowHistoryModel() {
    }
	
    public MainWorkflowHistoryModel(JSONObject jobj) throws Exception {
        time = (Timestamp)jobj.get("time");
        user_ = jobj.getString("user");
        action = jobj.getString("action");
        task = jobj.getString("task");
        comment = jobj.getString("comment");
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getUser_() {
		return user_;
	}
	public void setUser_(String user_) {
		this.user_ = user_;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
	
}
