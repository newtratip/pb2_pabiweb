package pb.repo.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.admin.dao.MainCostControlDAO;
import pb.repo.admin.dao.MainCostControlTypeDAO;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminCostControlService {
	
	private static Logger log = Logger.getLogger(AdminCostControlService.class);

	@Autowired
	DataSource dataSource;

	public List<Map<String, Object>> list(Integer type,String searchTerm) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainCostControlDAO dao = session.getMapper(MainCostControlDAO.class);
            
        	Map<String, Object> params = new HashMap<String, Object>();
        	log.info("type="+type);
        	params.put("type", type);
        	if (searchTerm!=null) {
        		String[] terms = searchTerm.split(" ");
        	
        		params.put("terms", terms);
        	}
        	
    		List<Map<String,Object>> tmpList = dao.list(params);
    		
        	for(Map<String, Object> tmpMap : tmpList) {
        		Map<String, Object> map = new HashMap<String, Object>();
        		
        		map.put("id", tmpMap.get("id"));
        		map.put("type", tmpMap.get("name"));
        		map.put("name", tmpMap.get("description"));
        		
        		list.add(map);
        	}
            
            
        } catch (Exception ex) {
        	log.error(ex);
        } finally {
        	session.close();
        }
		
		return list;
	}
	
	public List<Map<String, Object>> listType() {
		
		List<Map<String, Object>> list = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainCostControlTypeDAO dao = session.getMapper(MainCostControlTypeDAO.class);
        	
    		list = dao.list();
    		
        } catch (Exception ex) {
        	log.error(ex);
        } finally {
        	session.close();
        }
		
		return list;
	}
	
	public Map<String, Object> get(Integer id) {
		
		Map<String, Object> map = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainCostControlDAO dao = session.getMapper(MainCostControlDAO.class);
            
    		map = dao.get(id);
            
        } catch (Exception ex) {
        	log.error(ex);
        } finally {
        	session.close();
        }
		
		return map;
	}
}
