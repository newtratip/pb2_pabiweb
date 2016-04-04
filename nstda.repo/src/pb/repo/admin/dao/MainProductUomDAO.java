package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainProductUomModel;

public interface MainProductUomDAO {

	public List<MainProductUomModel> list();
	
	public Long count();
	
}
