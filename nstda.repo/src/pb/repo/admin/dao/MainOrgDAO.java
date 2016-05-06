package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainOrgModel;

public interface MainOrgDAO {

	public List<MainOrgModel> list();
	
	public Long count();
	
}
