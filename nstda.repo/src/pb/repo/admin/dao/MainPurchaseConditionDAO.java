package pb.repo.admin.dao;

import java.util.List;
import java.util.Map;

import pb.repo.admin.model.MainProductUomModel;

public interface MainPurchaseConditionDAO {

	public List<Map<String,Object>> list(Map<String,Object> params);
	
}
