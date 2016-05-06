package pb.repo.admin.dao;

import java.util.List;
import java.util.Map;

import pb.repo.admin.model.MainWkfConfigPurchaseUnitResponsibleModel;

public interface MainWkfConfigPurchaseUnitResponsibleDAO {

	public List<MainWkfConfigPurchaseUnitResponsibleModel> list();
	
	public Long count();
	
	public List<Map<String, Object>> listSupervisor(Integer id);
	
}
