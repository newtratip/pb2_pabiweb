package pb.repo.pcm.dao;

import java.util.List;
import java.util.Map;

import pb.repo.pcm.model.PcmReqMethodCommitteeModel;

public interface PcmReqMethodCommitteeDAO {

	public List<Map<String, Object>> list(Map<String, Object> params);
	
	public Long count();
	
	public PcmReqMethodCommitteeModel get(String code);

}
