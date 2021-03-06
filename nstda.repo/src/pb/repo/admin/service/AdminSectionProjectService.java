package pb.repo.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.admin.constant.MainBudgetSrcConstant;

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
        	
        	if (type.equals(MainBudgetSrcConstant.TYPE_UNIT)) {
        		tmpList = sectionService.list(searchTerm,lang);
        		
        		lang = lang!=null && lang.startsWith("th") ? "_th" : "";
        		
            	for(Map<String, Object> tmpMap : tmpList) {
            		Map<String, Object> map = new HashMap<String, Object>();
            		
            		map.put("id", tmpMap.get("id")+"|"+tmpMap.get("description"+lang)+"|"+tmpMap.get("emotion"));
            		map.put("type", tmpMap.get("section_name_short"));
            		map.put("org", tmpMap.get("name_short"+lang));
            		map.put("name", tmpMap.get("description"+lang));
            		map.put("cc", tmpMap.get("costcenter"+lang));
            		
            		list.add(map);
            	}
        	}
        	else
        	if (type.equals(MainBudgetSrcConstant.TYPE_PROJECT)) {
        		tmpList = projectService.list(searchTerm,lang);
        		
        		lang = lang!=null && lang.startsWith("th") ? "_th" : "";
        		
            	for(Map<String, Object> tmpMap : tmpList) {
            		Map<String, Object> map = new HashMap<String, Object>();
            		
            		map.put("id", tmpMap.get("id")+"|"+"["+((String)tmpMap.get("code")).trim() + "] "+tmpMap.get("name"));
            		map.put("type", tmpMap.get("name"));
            		map.put("name", "["+((String)tmpMap.get("code")).trim() + "] " + tmpMap.get("name"+lang));
//            		Map<String,Object> dtl = adminHrEmployeeService.getWithDtl((String)tmpMap.get("pm_code"));
//            		map.put("cc", "["+((String)tmpMap.get("pm_code")).trim() + "] "+dtl.get("title"+lang) + " " +dtl.get("first_name"+lang) + " " + dtl.get("last_name"+lang)); // project manager
            		map.put("cc", "["+((String)tmpMap.get("pm_code")).trim() + "] "+tmpMap.get("pm_name"+lang)); // project manager
            		map.put("org", tmpMap.get("org_name_short"+lang));
            		
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
	
	public Map<String, Object> get(String type, String id, String lang) {
		
		Map<String, Object> map = new HashMap<String,Object>();
		
        try {
        	if (type.equals(MainBudgetSrcConstant.TYPE_UNIT)) {
        		Map<String, Object> tmpMap = sectionService.get(Integer.parseInt(id));
        		
        		lang = lang!=null && lang.startsWith("th") ? "_th" : "";
        		
        		map.put("data", tmpMap.get("description"+lang));
        		map.put("data2", tmpMap.get("name_short"+lang)+" "+tmpMap.get("description"+lang));
        	}
        	else
        	if (type.equals(MainBudgetSrcConstant.TYPE_PROJECT)) {
        		Map<String, Object> tmpMap = projectService.get(Integer.parseInt(id));
        		
        		lang = lang!=null && lang.startsWith("th") ? "_th" : "";
        		
        		map.put("data", "["+((String)tmpMap.get("code")).trim() + "] " + tmpMap.get("name"+lang));
        		map.put("data2", tmpMap.get("org_name_short"+lang)+" "+"["+((String)tmpMap.get("code")).trim() + "] " + tmpMap.get("name"+lang));
        	} else {
        		// do nothing
        	}
        	
        	
        } catch (Exception ex) {
        	log.error(ex);
        }
		
		return map;
	}
	
	public JSONObject getDtl(String type, String id, String lang) {
		
		JSONObject obj = new JSONObject();
		
        try {
        	if (type.equals(MainBudgetSrcConstant.TYPE_UNIT)) {
        		Map<String, Object> tmpMap = sectionService.get(Integer.parseInt(id));
        		
        		lang = lang!=null && lang.startsWith("th") ? "_th" : "";
        		
        		obj.put("id", tmpMap.get("id"));
        		obj.put("budget_name", tmpMap.get("description"+lang));
//        		obj.put("budget_name", tmpMap.get("name_short"+lang)+" "+tmpMap.get("description"+lang));
        		obj.put("budget_type_name", lang.equals("") ? "Section" : "หน่วยงาน");
        	}
        	else
        	if (type.equals(MainBudgetSrcConstant.TYPE_PROJECT)) {
        		Map<String, Object> tmpMap = projectService.get(Integer.parseInt(id));
        		
        		lang = lang!=null && lang.startsWith("th") ? "_th" : "";
        		
        		obj.put("id", tmpMap.get("id"));
        		obj.put("budget_name", "["+((String)tmpMap.get("code")).trim() + "] " + tmpMap.get("name"+lang));
        		obj.put("budget_type_name", lang.equals("") ? "Project" : "โครงการ");
        	} else {
        		// do nothing
        	}
        	
        	
        	
        } catch (Exception ex) {
        	log.error(ex);
        }
		
		return obj;
	}

}
