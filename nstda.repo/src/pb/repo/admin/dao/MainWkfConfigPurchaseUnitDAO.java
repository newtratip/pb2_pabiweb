package pb.repo.admin.dao;

import java.util.List;
import java.util.Map;

import pb.repo.admin.model.MainWkfConfigPurchaseUnitModel;

public interface MainWkfConfigPurchaseUnitDAO {

	public List<MainWkfConfigPurchaseUnitModel> list();
	
	public Long count();
	
	public List<Map<String, Object>> listPurchasingUnitBySectionId(Integer id);
	
}
