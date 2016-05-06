package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainWkfCmdBossSpecialLevelModel;

public interface MainWkfCmdBossSpecialLevelDAO {

	public List<MainWkfCmdBossSpecialLevelModel> list();
	
	public Long count();
	
}
