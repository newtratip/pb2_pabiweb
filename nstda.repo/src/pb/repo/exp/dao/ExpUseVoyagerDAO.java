package pb.repo.exp.dao;

import java.util.List;
import java.util.Map;

import pb.repo.exp.model.ExpUseVoyagerModel;

public interface ExpUseVoyagerDAO {

	public void add(ExpUseVoyagerModel model);

	public void delete(Long id);
	public void deleteByMasterId(String masterId);
	
	public Long count();
	
	public ExpUseVoyagerModel get(Long id);

	public List<ExpUseVoyagerModel> list(Map<String, Object> params);
	
	public List<Map<String, Object>> listForInf(Map<String, Object> params);
}