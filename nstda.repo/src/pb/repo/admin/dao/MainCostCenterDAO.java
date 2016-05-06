package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainCostCenterModel;

public interface MainCostCenterDAO {

	public List<MainCostCenterModel> list();
	
	public Long count();
	
}
