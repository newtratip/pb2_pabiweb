package pb.repo.pcm.dao;

import java.util.List;
import java.util.Map;

import pb.repo.pcm.model.PcmOrdWorkflowHistoryModel;

public interface PcmOrdWorkflowHistoryDAO {

	public void add(PcmOrdWorkflowHistoryModel pcmWorkflowHistoryModel);
	public void update(PcmOrdWorkflowHistoryModel pcmWorkflowHistoryModel);
	public List<PcmOrdWorkflowHistoryModel> listHistory(Map<String,Object> params);
	public void deleteByPcmOrdId(String id); // memoId

	public List<PcmOrdWorkflowHistoryModel> listByMasterId(Map<String, Object> params);

}
