package pb.repo.admin.dao;

import java.util.List;

import pb.repo.admin.model.MainMsgModel;

public interface MainMsgDAO {

	public void add(MainMsgModel model);
	
	public void delete(String user);
	
	public Long count();
	
	public List<MainMsgModel> listByUser(String user);
}
