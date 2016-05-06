package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainWkfCmdSpecialAmountProjectApprovalModel;

public interface MainWkfCmdSpecialAmountProjectApprovalDAO {

	public List<MainWkfCmdSpecialAmountProjectApprovalModel> list();
	
	public Long count();
	
}
