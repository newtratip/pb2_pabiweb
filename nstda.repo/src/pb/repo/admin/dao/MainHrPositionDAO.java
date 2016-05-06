package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainHrPositionModel;

public interface MainHrPositionDAO {

	public List<MainHrPositionModel> list();
	
	public Long count();
	
}
