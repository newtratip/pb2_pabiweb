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
	
	@Autowired
	private AdminHrEmployeeService adminHrEmployeeService;

	public List<Map<String, Object>> list(String type,String searchTerm, String lang) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
        try {
        	List<Map<String, Object>> tmpList;
        	if (type.equals("U")) {
        		tmpList = sectionService.list(searchTerm,lang);
        		
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
        		tmpList = projectService.list(searchTerm,lang);
        		
        		lang = (lang!=null && lang.startsWith("th")) ? "_th" : "";
        		
            	for(Map<String, Object> tmpMap : tmpList) {
            		Map<String, Object> map = new HashMap<String, Object>();
            		
            		map.put("id", tmpMap.get("id")+"|"+"["+((String)tmpMap.get("code")).trim() + "] "+tmpMap.get("name"));
            		map.put("type", tmpMap.get("name"));
            		map.put("name", "["+((String)tmpMap.get("code")).trim() + "] " + tmpMap.get("name"));
            		Map<String,Object> dtl = adminHrEmployeeService.getWithDtl((String)tmpMap.get("pm_code"));
            		map.put("cc", "["+((String)tmpMap.get("pm_code")).trim() + "] "+dtl.get("first_name"+lang) + " " + dtl.get("last_name"+lang)); // project manager
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
