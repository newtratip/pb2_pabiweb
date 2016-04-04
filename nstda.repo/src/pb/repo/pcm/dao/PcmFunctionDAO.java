package pb.repo.pcm.dao;

import java.util.Map;


public interface PcmFunctionDAO {

	public String listType(Map<String,Object> param);
	public String listTeam(Map<String,Object> param);
	public String listLevel(Map<String,Object> param);
	public String listHour(Map<String,Object> param);
	
	public String listTypeChange(Map<String,Object> param);
	public String listRequestDay(Map<String,Object> param);
	public String listRequestEffect(Map<String,Object> param);
	public String listChange(Map<String,Object> param);
	public String listTrain(Map<String,Object> param);
	
}
