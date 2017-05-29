package pb.repo.admin.wscript;

import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonUtil;

import com.github.dynamicextensionsalfresco.webscripts.annotations.Authentication;
import com.github.dynamicextensionsalfresco.webscripts.annotations.AuthenticationType;
import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
@Authentication(value=AuthenticationType.ADMIN)
public class AdminUtilWebScript {
	
	private static Logger log = Logger.getLogger(AdminUtilWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/admin/util";
	
	@Autowired
	private AuthorityService authorityService;

  /**
   * Handles the "list" request. Note the use of Spring MVC-style annotations to map the Web Script URI configuration
   * and request handling objects.
   * 
   * @param response
   * @throws Exception
   */
	@Uri(method=HttpMethod.POST, value=URI_PREFIX+"/u2g")
  public void handleAddUserToGroup(@RequestParam(required=false) String u
		  					  ,@RequestParam(required=false) String g
		  					  ,final WebScriptResponse response)  throws Exception {
    
	  	JSONObject jobj = new JSONObject();
	  	
	  	final String group = authorityService.getName(AuthorityType.GROUP, g);
	  	 
	  	if (u!=null) {
	  		String[] users = u.split(",");
	  		for(String user : users) {
		  		authorityService.addAuthority(group, user);
	  		}
	  	}
	  	jobj.put("success", true);
		
	  	String json = null;
	  	
		try {
			json = jobj.toString();
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
		}
    
  }
  
}
