package pb.repo.admin.dao;

import pb.repo.admin.model.MainEmployeeModel;

public interface MainEmployeeDAO {

	public void add(MainEmployeeModel model);
	
	public void delete(String id);
	
	public Long count();
	
}
