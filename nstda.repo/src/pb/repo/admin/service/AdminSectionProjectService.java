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

import pb.repo.admin.dao.MainSectionDAO;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminSectionProjectService {
	
	private static Logger log = Logger.getLogger(AdminSectionProjectService.class);

	@Autowired
	AdminProjectService projectService;
	
	@Autowired
	AdminSectionService sectionService;

	public List<Map<String, Object>> list(String type,String searchTerm) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
        try {
        	List<Map<String, Object>> tmpList;
        	if (type.equals("U")) {
        		tmpList = sectionService.list(searchTerm);
        		
            	for(Map<String, Object> tmpMap : tmpList) {
            		Map<String, Object> map = new HashMap<String, Object>();
            		
            		map.put("id", tmpMap.get("id")+"|"+tmpMap.get("description"));
            		map.put("type", tmpMap.get("name"));
            		map.put("name", tmpMap.get("description"));
            		map.put("cc", tmpMap.get("costcenter"));
            		
            		list.add(map);
            	}
        	}
        	else
        	if (type.equals("P")) {
        		tmpList = projectService.list(searchTerm);
        		
            	for(Map<String, Object> tmpMap : tmpList) {
            		Map<String, Object> map = new HashMap<String, Object>();
            		
            		map.put("id", tmpMap.get("id")+"|"+tmpMap.get("name")+" - "+tmpMap.get("description"));
            		map.put("type", tmpMap.get("name"));
            		map.put("name", tmpMap.get("name") + " - " + tmpMap.get("description"));
            		map.put("cc", tmpMap.get("pm_code")+" - "+tmpMap.get("pm_name")); // project manager
            		map.put("org", tmpMap.get("org_name"));
            		
            		list.add(map);
            	}
            	
        	} else {
        		// do nothing
        	}
        	
        	
        } catch (Exception ex) {
        	log.error(ex);
        }
		
		return list;
	}
}
