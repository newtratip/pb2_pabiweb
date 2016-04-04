package pb.repo.pcm.dao;

import java.util.List;
import java.util.Map;

import pb.repo.pcm.model.PcmWorkflowHistoryModel;

public interface PcmReqWorkflowHistoryDAO {

	public void add(PcmWorkflowHistoryModel pcmWorkflowHistoryModel);
	public void update(PcmWorkflowHistoryModel pcmWorkflowHistoryModel);
	public List<PcmWorkflowHistoryModel> listHistory(Map<String,Object> params);
	public void deleteByPcmReqId(String id); // memoId

	public List<PcmWorkflowHistoryModel> listByMasterId(Map<String, Object> params);

}
