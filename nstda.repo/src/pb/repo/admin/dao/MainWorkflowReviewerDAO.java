package pb.repo.admin.dao;

import java.util.List;
import java.util.Map;

import pb.repo.admin.model.MainWorkflowReviewerModel;

public interface MainWorkflowReviewerDAO {
	
	public void deleteByMasterId(String masterId);
	public void add(MainWorkflowReviewerModel model);
	
	public Integer getLastLevel(String id);
	public List<MainWorkflowReviewerModel> listByLevel(MainWorkflowReviewerModel model);
	public List<Map<String, Object>> listWorkflowPath(String id);
}