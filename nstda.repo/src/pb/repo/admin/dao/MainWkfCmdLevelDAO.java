package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainWkfCmdLevelModel;

public interface MainWkfCmdLevelDAO {

	public List<MainWkfCmdLevelModel> list();
	
	public Long count();
	
}
