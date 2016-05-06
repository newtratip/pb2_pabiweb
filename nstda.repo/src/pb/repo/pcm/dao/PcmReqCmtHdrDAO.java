package pb.repo.pcm.dao;

import java.util.List;
import java.util.Map;

import pb.repo.pcm.model.PcmReqCmtHdrModel;

public interface PcmReqCmtHdrDAO {
	
	public void add(PcmReqCmtHdrModel model);

	public Long count();
	
	public PcmReqCmtHdrModel get(String id);

	public List<PcmReqCmtHdrModel> list(Map<String, Object> params);
	
	public void deleteByMasterId(String masterId);

	public List<Map<String, Object>> listForInf(Map<String, Object> params);
	
}
