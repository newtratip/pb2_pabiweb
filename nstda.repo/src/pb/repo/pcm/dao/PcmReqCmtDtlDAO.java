package pb.repo.pcm.dao;

import java.util.List;
import java.util.Map;

import pb.repo.pcm.model.PcmReqCmtDtlModel;

public interface PcmReqCmtDtlDAO {

	public Long count();
	
	public PcmReqCmtDtlModel get(String id);

	public List<PcmReqCmtDtlModel> list(Map<String, Object> params);
}
