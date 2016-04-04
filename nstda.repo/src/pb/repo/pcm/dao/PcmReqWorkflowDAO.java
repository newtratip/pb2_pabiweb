package pb.repo.pcm.dao;

import java.util.List;

import pb.repo.pcm.model.PcmWorkflowModel;

public interface PcmReqWorkflowDAO {

	public void add(PcmWorkflowModel pcmWorkflowModel);
	public void update(PcmWorkflowModel pcmWorkflowModel);
	
	public void deleteByMasterId(String masterId);
	
	public PcmWorkflowModel getLastWorkflow(PcmWorkflowModel pcmWorkflowModel);
	public Long getKey();
	
    public List<PcmWorkflowModel> listByMasterId(String id);
	
}
