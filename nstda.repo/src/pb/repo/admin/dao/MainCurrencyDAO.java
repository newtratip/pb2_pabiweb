package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainCurrencyModel;

public interface MainCurrencyDAO {

	public List<MainCurrencyModel> list();
	
	public Long count();
	
}
