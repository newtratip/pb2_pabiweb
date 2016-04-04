package pb.repo.pcm.model;

import java.sql.Timestamp;

public class PcmOrdReviewerModel {

	Long id;
	String masterId;
	Integer level;
	String reviewerGroup;
	String reviewerUser;
	Double percent;
	Integer rewarning;
	String hint;
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
	public String getReviewerGroup() {
		return reviewerGroup;
	}
	public void setReviewerGroup(String reviewerGroup) {
		this.reviewerGroup = reviewerGroup;
	}
	public String getReviewerUser() {
		return reviewerUser;
	}
	public void setReviewerUser(String reviewerUser) {
		this.reviewerUser = reviewerUser;
	}
	public Double getPercent() {
		return percent;
	}
	public void setPercent(Double percent) {
		this.percent = percent;
	}
	public Integer getRewarning() {
		return rewarning;
	}
	public void setRewarning(Integer rewarning) {
		this.rewarning = rewarning;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
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
	
}
