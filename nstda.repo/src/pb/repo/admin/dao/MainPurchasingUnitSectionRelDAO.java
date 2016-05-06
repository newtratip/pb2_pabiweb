package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainPurchasingUnitSectionRelModel;

public interface MainPurchasingUnitSectionRelDAO {

	public List<MainPurchasingUnitSectionRelModel> list();
	
	public Long count();
	
}
