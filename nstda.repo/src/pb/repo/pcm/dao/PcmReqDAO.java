package pb.repo.pcm.dao;

import java.util.List;
import java.util.Map;

import pb.repo.pcm.model.PcmReqModel;

public interface PcmReqDAO {

	public void add(PcmReqModel model);
	public void update(PcmReqModel model);
	public void updateStatus(PcmReqModel model);
	public void delete(String id);
	
	public Long count();
	
	public PcmReqModel get(String id);

	public List<PcmReqModel> list(Map<String, Object> params);
	public List<Map<String, Object>> listForInf(Map<String, Object> params);
	
	public String genNewId(Map<String, Object> params);
	public String getLastId(Map<String, Object> params);
	
	public Long getNewRunningNo();
	public Long resetRunningNo();
}
