package pb.repo.exp.dao;

import java.util.List;
import java.util.Map;

import pb.repo.exp.model.ExpUseModel;

public interface ExpUseDAO {

	public void add(ExpUseModel model);
	public void update(ExpUseModel model);
	public void updateStatus(ExpUseModel model);
	public void delete(String id);
	
	public Long count();
	
	public ExpUseModel get(String id);

	public List<ExpUseModel> list(Map<String, Object> params);
	public List<ExpUseModel> listForSearch(Map<String, Object> params);
	public List<Map<String, Object>> listForInf(Map<String, Object> params);
	
	public String genNewId(Map<String, Object> params);
	public String getLastId(Map<String, Object> params);
	
	public Long getNewRunningNo();
	public Long resetRunningNo();
	
	public Map<String, Object> getFirstApprover(String id);
	public Map<String, Object> getLastApprover(String id);
}
