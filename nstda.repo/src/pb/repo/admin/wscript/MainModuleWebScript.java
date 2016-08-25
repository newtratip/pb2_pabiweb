package pb.repo.admin.wscript;

import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminModuleService;

import com.github.dynamicextensionsalfresco.webscripts.annotations.Authentication;
import com.github.dynamicextensionsalfresco.webscripts.annotations.AuthenticationType;
import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class MainModuleWebScript {
	
	private static Logger log = Logger.getLogger(MainModuleWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/admin/main";
	
	@Autowired
	private AdminModuleService moduleService;

  /**
   * Handles the "totalPreBudget" request. Note the use of Spring MVC-style annotations to map the Web Script URI configuration
   * and request handling objects.
   * 
   * @param response
   * @throws Exception
   */
  @Uri(URI_PREFIX+"/totalPreBudget")
  public void handleTotalPreBudget(@RequestParam Integer budgetCc,
								  @RequestParam String budgetCcType,
								  final WebScriptResponse response)  throws Exception {

	  	JSONObject jobj = new JSONObject();
	  	String json = null;
		try {
			log.info("budgetCc:"+budgetCc);;
			log.info("budgetCcType:"+budgetCcType);
			
		  	Map<String, Object> map = moduleService.getTotalPreBudget(budgetCc, budgetCcType);
		  	
		  	jobj.put("data", map);
			
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
