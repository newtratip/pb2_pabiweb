package pb.repo.pcm.dao;

import java.util.List;
import java.util.Map;

import pb.repo.pcm.model.PcmReqCmtModel;

public interface PcmReqCmtDAO {

	public Long count();
	
	public PcmReqCmtModel get(String id);

	public List<PcmReqCmtModel> list(Map<String, Object> params);
}
