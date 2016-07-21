package pb.repo.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.admin.dao.MainHrEmployeeDAO;
import pb.repo.admin.dao.MainProjectDAO;
import pb.repo.admin.model.MainHrEmployeeModel;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminHrEmployeeService {
	
	private static Logger log = Logger.getLogger(AdminHrEmployeeService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	AuthenticationService authService;

	public MainHrEmployeeModel get(String id) {
			
		MainHrEmployeeModel model = null;
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainHrEmployeeDAO dao = session.getMapper(MainHrEmployeeDAO.class);
    		model = dao.get(id);
    		if (model!=null) {
    			model.setTotalRowCount(1l);
    		}
            
        } finally {
        	session.close();
        }
        
        return model;
	}
	
	public Map<String, Object> getWithDtl(String id, String lang) {
		
		Map<String, Object> map = null;
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainHrEmployeeDAO dao = session.getMapper(MainHrEmployeeDAO.class);
        	
        	Map<String, Object> params = new HashMap<String, Object>();

        	params.put("code", id);
        	params.put("lang", lang!=null && lang.startsWith("th") ? "_th" : "");
        	
    		map = dao.getWithDtl(params);
            
        } finally {
        	session.close();
        }
        
        return map;
	}

	public List<Map<String, Object>> list(String type, String code, String searchTerm) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainHrEmployeeDAO dao = session.getMapper(MainHrEmployeeDAO.class);
            
        	Map<String, Object> params = new HashMap<String, Object>();

        	params.put("code", Integer.parseInt(code));
        	if (searchTerm!=null) {
        		String[] terms = searchTerm.split(" ");
        	
        		params.put("terms", terms);
        	}
        	
        	if (type.equals("P")) {
                List<Map<String,Object>>tmpList = dao.listByProject(params);
                
            	for(Map<String, Object> tmpMap : tmpList) {
            		Map<String, Object> map = new HashMap<String, Object>();
            		
            		String name = tmpMap.get("first_name")+" "+tmpMap.get("last_name");
            		map.put("id", tmpMap.get("employee_code")+"|"+name);
            		map.put("code", tmpMap.get("employee_code"));
            		map.put("name", name);
            		map.put("utype", tmpMap.get("utype"));
            		map.put("position", tmpMap.get("position"));
            		
            		list.add(map);
            	}                
        	
        	} else {
                List<Map<String,Object>>tmpList = dao.listBySection(params);
            	for(Map<String, Object> tmpMap : tmpList) {
            		Map<String, Object> map = new HashMap<String, Object>();
            		
            		String name = tmpMap.get("first_name")+" "+tmpMap.get("last_name");
            		map.put("id", tmpMap.get("employee_code")+"|"+name);
            		map.put("code", tmpMap.get("employee_code"));
            		map.put("name", name);
            		map.put("utype", tmpMap.get("utype"));
            		map.put("position", tmpMap.get("position"));
            		
            		list.add(map);
            	}                
        	}
        } catch (Exception ex) {
        	log.error(ex);
        } finally {
        	session.close();
        }
		
		return list;
	}
	
	public List<Map<String, Object>> list(String searchTerm, String lang) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainHrEmployeeDAO dao = session.getMapper(MainHrEmployeeDAO.class);
            
        	Map<String, Object> params = new HashMap<String, Object>();

        	if (searchTerm!=null) {
        		String[] terms = searchTerm.split(" ");
        	
        		params.put("terms", terms);
        	}
        	
        	params.put("lang", lang!=null && lang.startsWith("th") ? "_th" : "");
        	
        	log.info("params="+params);
        	
            List<Map<String,Object>>tmpList = dao.listForSearch(params);
            
        	for(Map<String, Object> tmpMap : tmpList) {
        		Map<String, Object> map = new HashMap<String, Object>();
        		
        		String name = tmpMap.get("first_name")+" "+tmpMap.get("last_name");
        		map.put("id", tmpMap.get("employee_code")+"|"+name);
        		map.put("code", tmpMap.get("employee_code"));
        		map.put("fname", tmpMap.get("first_name"));
        		map.put("lname", tmpMap.get("last_name"));
        		map.put("utype", tmpMap.get("utype"));
        		map.put("position", tmpMap.get("position"));
        		map.put("position_id", tmpMap.get("position_id"));
        		
        		list.add(map);
        	}                
    	
        } catch (Exception ex) {
        	log.error(ex);
        } finally {
        	session.close();
        }
		
		return list;
	}	
	
}
