package pb.repo.admin.dao;

import java.util.List;
import java.util.Map;

import pb.repo.admin.model.MainEmployeeBossModel;

public interface MainEmployeeBossDAO {

	public void add(MainEmployeeBossModel model);
	
	public void delete(MainEmployeeBossModel model);
	
	public Long count();
	
	public List<Map<String, Object>> listBoss(Map<String, Object> params);

}
