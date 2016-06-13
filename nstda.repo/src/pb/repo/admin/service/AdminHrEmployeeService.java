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
	
	public Map<String, Object> getWithDtl(String id) {
		
		Map<String, Object> map = null;
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainHrEmployeeDAO dao = session.getMapper(MainHrEmployeeDAO.class);
    		map = dao.getWithDtl(id);
            
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

        	params.put("searchTerm", searchTerm);
        	params.put("code", Integer.parseInt(code));
        	
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
}
