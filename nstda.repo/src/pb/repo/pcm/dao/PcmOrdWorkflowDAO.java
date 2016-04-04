package pb.repo.pcm.dao;

import java.util.List;

import pb.repo.pcm.model.PcmOrdWorkflowModel;

public interface PcmOrdWorkflowDAO {

	public void add(PcmOrdWorkflowModel pcmWorkflowModel);
	public void update(PcmOrdWorkflowModel pcmWorkflowModel);
	
	public void deleteByMasterId(String masterId);
	
	public PcmOrdWorkflowModel getLastWorkflow(PcmOrdWorkflowModel pcmWorkflowModel);
	public Long getKey();
	
    public List<PcmOrdWorkflowModel> listByMasterId(String id);
	
}
