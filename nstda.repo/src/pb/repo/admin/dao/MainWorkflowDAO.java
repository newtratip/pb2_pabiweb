package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainWorkflowModel;

public interface MainWorkflowDAO {

	public void add(MainWorkflowModel model);
	public void update(MainWorkflowModel model);
	
	public void deleteByMasterId(String masterId);
	
	public MainWorkflowModel getLastWorkflow(MainWorkflowModel model);
	public Long getKey();
	
    public List<MainWorkflowModel> listByMasterId(String id);
	
}
