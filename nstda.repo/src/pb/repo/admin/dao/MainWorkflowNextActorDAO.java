package pb.repo.admin.dao;

import java.util.List;
import java.util.Map;

import pb.repo.admin.model.MainWorkflowNextActorModel;

public interface MainWorkflowNextActorDAO {
	
	public void deleteByMasterId(String masterId);
	public void add(MainWorkflowNextActorModel model);
	
	public Integer getLastLevel(String id);
	public List<MainWorkflowNextActorModel> listByLevel(MainWorkflowNextActorModel model);
	public List<Map<String, Object>> listWorkflowPath(String id);
}