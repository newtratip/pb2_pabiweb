package pb.repo.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pb.repo.admin.model.MainCurrencyRateModel;


public interface MainCurrencyRateDAO {

	public List<MainCurrencyRateModel> list(@Param("name") String name);

	public Long count();
	
}
