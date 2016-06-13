package pb.repo.admin.dao;

import java.util.List;
import java.util.Map;

import pb.repo.admin.model.MainHrEmployeeModel;

public interface MainHrEmployeeDAO {

	public List<MainHrEmployeeModel> list();
	public List<Map<String, Object>> listBySection(Map<String, Object> params);
	public List<Map<String, Object>> listByProject(Map<String, Object> params);
	
	public Long count();
	
	public MainHrEmployeeModel get(String code);

	public Map<String, Object> getWithDtl(String code);
	
	

}
