package pb.repo.exp.dao;

import java.util.List;
import java.util.Map;

import pb.repo.exp.model.ExpBrwVoyagerModel;

public interface ExpBrwVoyagerDAO {

	public void add(ExpBrwVoyagerModel model);

	public void delete(Long id);
	public void deleteByMasterId(String masterId);
	
	public Long count();
	
	public ExpBrwVoyagerModel get(Long id);

	public List<ExpBrwVoyagerModel> list(Map<String, Object> params);
	
	public List<Map<String, Object>> listForInf(Map<String, Object> params);
}