package pb.repo.admin.wscript;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonUtil;
import pb.repo.admin.service.AdminAccountActivityService;

import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
//@Authentication(value=AuthenticationType.ADMIN)
public class AdminMainAccountActivityWebScript {
	
	private static Logger log = Logger.getLogger(AdminMainAccountActivityWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/admin/main/activity";
	
	@Autowired
	private AdminAccountActivityService activityService;


  /**
   * Handles the "list" request. Note the use of Spring MVC-style annotations to map the Web Script URI configuration
   * and request handling objects.
   * 
   * @param t : type
   * @param s : searchTerm
   * @param response
   * @throws Exception
   */
  @Uri(URI_PREFIX+"/list")
  public void handleList(@RequestParam(required=true) final String name,
		  				final WebScriptResponse response)  throws Exception {
    
		String json = null;
		
		try {
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("name", "%"+name+"%");
    		
			List<Map<String, Object>> list = activityService.list(params);
			
			json = CommonUtil.jsonSuccess(list);
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
		}
    
  }
  
}