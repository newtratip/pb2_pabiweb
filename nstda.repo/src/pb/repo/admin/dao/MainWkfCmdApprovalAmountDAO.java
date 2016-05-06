package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainWkfCmdApprovalAmountModel;

public interface MainWkfCmdApprovalAmountDAO {

	public List<MainWkfCmdApprovalAmountModel> list();
	
	public Long count();
	
}
