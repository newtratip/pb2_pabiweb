package pb.repo.admin.wscript;

import org.alfresco.service.cmr.security.AuthorityService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.AlfescoServiceUtil;
import pb.common.util.CommonUtil;
import pb.repo.admin.util.MainUtil;

import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class AdminGroupWebScript {
	
	private static Logger log = Logger.getLogger(AdminGroupWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/admin/main/group";
	
	@Autowired
	AuthorityService authorityService;


	
	@Uri(URI_PREFIX+"/list")
	public void handleList(@RequestParam(required=false)  String s, final WebScriptResponse response)  throws Exception {
		
		AlfescoServiceUtil alfrescoServiceUtil = new AlfescoServiceUtil();
		alfrescoServiceUtil.setAuthorityService(authorityService);
		
		
		String json = null;

		try {
			JSONArray jsArr = alfrescoServiceUtil.getAlfGroup();
			json = MainUtil.jsonSuccess(jsArr);
		} catch (Exception ex) {
			log.error("", ex);
			try {
				json = CommonUtil.jsonFail(ex.toString());
			} catch (JSONException e) {
				log.error("", e);
			}
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
			
		}
		
	}
	
	@Uri(URI_PREFIX+"/listUser")
	public void handleListUser(@RequestParam(required=false)  String s, final WebScriptResponse response)  throws Exception {
		
		AlfescoServiceUtil alfrescoServiceUtil = new AlfescoServiceUtil();
		alfrescoServiceUtil.setAuthorityService(authorityService);
		
		
		String json = null;

		try {
			JSONArray jsArr = alfrescoServiceUtil.getAlfUser();
			json = MainUtil.jsonSuccess(jsArr);
		} catch (Exception ex) {
			log.error("", ex);
			try {
				json = CommonUtil.jsonFail(ex.toString());
			} catch (JSONException e) {
				log.error("", e);
			}
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
			
		}
		
	}

}
