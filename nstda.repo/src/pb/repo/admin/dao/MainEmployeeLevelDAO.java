package pb.repo.admin.dao;

import pb.repo.admin.model.MainEmployeeLevelModel;

public interface MainEmployeeLevelDAO {

	public void add(MainEmployeeLevelModel model);
	
	public void delete(MainEmployeeLevelModel model);
	
	public Long count();
}
