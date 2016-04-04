package pb.repo.pcm.dao;

import java.util.List;
import java.util.Map;

import pb.repo.pcm.model.PcmOrdModel;

public interface PcmOrdDAO {

	public void add(PcmOrdModel model);
	public void update(PcmOrdModel model);
	public void updateStatus(PcmOrdModel model);
	public void delete(String id);
	
	public Long count();
	
	public PcmOrdModel get(String id);

	public List<PcmOrdModel> list(Map<String, Object> params);
	public List<Map<String, Object>> listWorkflowPath(String id);
	
	public String genNewId(Map<String, Object> params);
	public String getLastId(Map<String, Object> params);
}