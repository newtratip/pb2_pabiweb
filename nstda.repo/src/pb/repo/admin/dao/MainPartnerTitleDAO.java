package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainPartnerTitleModel;

public interface MainPartnerTitleDAO {

	public List<MainPartnerTitleModel> list();
	
	public Long count();
	
}
