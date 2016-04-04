package pb.repo.admin.dao;

import pb.repo.admin.model.MainCostCenterOrgRelModel;

public interface MainCostCenterOrgRelDAO {

	public void add(MainCostCenterOrgRelModel model);
	
	public void delete(MainCostCenterOrgRelModel model);
	
	public Long count();
}
