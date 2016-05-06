package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainDivisionModel;

public interface MainDivisionDAO {

	public List<MainDivisionModel> list();
	
	public Long count();
	
}
