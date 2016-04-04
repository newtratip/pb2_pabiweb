package pb.repo.admin.dao;

import java.util.List;
import java.util.Map;

import pb.repo.admin.model.MainCompleteNotificationModel;

public interface MainCompleteNotificationDAO {

	public void add(MainCompleteNotificationModel model);
	
	public void delete(Long id);
	
	public Long count();
	
	public MainCompleteNotificationModel get(Long id);
	
	public List<MainCompleteNotificationModel> list(Map<String,Object> params);
}