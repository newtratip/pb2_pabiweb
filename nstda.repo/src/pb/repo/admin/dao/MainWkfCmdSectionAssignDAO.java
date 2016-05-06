package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainWkfCmdSectionAssignModel;

public interface MainWkfCmdSectionAssignDAO {

	public List<MainWkfCmdSectionAssignModel> list();
	
	public Long count();
	
}
