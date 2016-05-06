package pb.repo.admin.model;

import java.sql.Timestamp;

public class MainWorkflowNextActorModel {

	Long id;
	String masterId;
	Integer level;
	String actor;
	String actorGroup;
	String actorUser;
	Timestamp createdTime;
	String createdBy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getActorGroup() {
		return actorGroup;
	}
	public void setActorGroup(String actorGroup) {
		this.actorGroup = actorGroup;
	}
	public String getActorUser() {
		return actorUser;
	}
	public void setActorUser(String actorUser) {
		this.actorUser = actorUser;
	}
	
}
