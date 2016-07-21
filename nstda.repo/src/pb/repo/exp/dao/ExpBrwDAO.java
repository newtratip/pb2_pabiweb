package pb.repo.exp.dao;

import java.util.List;
import java.util.Map;

import pb.repo.exp.model.ExpBrwModel;

public interface ExpBrwDAO {

	public void add(ExpBrwModel model);
	public void update(ExpBrwModel model);
	public void updateStatus(ExpBrwModel model);
	public void delete(String id);
	
	public Long count();
	
	public ExpBrwModel get(String id);

	public List<ExpBrwModel> list(Map<String, Object> params);
	public List<ExpBrwModel> listForSearch(Map<String, Object> params);
	public List<Map<String, Object>> listOld(Map<String, Object> params);
	public List<Map<String, Object>> listForInf(Map<String, Object> params);
	
	public String genNewId(Map<String, Object> params);
	public String getLastId(Map<String, Object> params);
	
	public Long getNewRunningNo();
	public Long resetRunningNo();
	
	public Map<String, Object> getFirstApprover(String id);
	public Map<String, Object> getLastApprover(String id);
}
