package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainWkfCmdBossLevelApprovalModel;

public interface MainWkfCmdBossLevelApprovalDAO {

	public List<MainWkfCmdBossLevelApprovalModel> list();
	
	public Long count();
	
}
