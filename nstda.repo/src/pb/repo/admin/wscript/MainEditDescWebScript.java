package pb.repo.admin.wscript;

import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.model.MainMsgModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminMsgService;
import pb.repo.admin.service.AlfrescoService;

import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class MainEditDescWebScript {
	
	private static Logger log = Logger.getLogger(MainEditDescWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/main";
	
	@Autowired
	AlfrescoService alfrescoService;
	
	@Autowired
	AuthenticationService authService;
	
	@Autowired
	NodeService nodeService;
	
	@Uri(method=HttpMethod.POST, value=URI_PREFIX+"/editDesc")
	public void handleEditDescPost(@RequestParam String d,
								   @RequestParam String n,
								   final WebScriptResponse response) throws Exception {
		
		String json = null;
		
		try {
			JSONObject jsObj = new JSONObject();
			
			NodeRef nodeRef = new NodeRef(n);
			
			nodeService.setProperty(nodeRef, ContentModel.PROP_DESCRIPTION, d);
			
			jsObj.put("result", true);
			
			json = CommonUtil.jsonSuccess(jsObj);
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
		} finally {
			CommonUtil.responseWrite(response, json);
		}
	}
	
	@Uri(method=HttpMethod.GET, value=URI_PREFIX+"/editDesc")
	public void handleEditDescGet(@RequestParam String n,
								   final WebScriptResponse response) throws Exception {
		
		String json = null;
		
		try {
			JSONObject jsObj = new JSONObject();
			
			NodeRef nodeRef = new NodeRef(n);
			
			String desc = (String)nodeService.getProperty(nodeRef, ContentModel.PROP_DESCRIPTION);
			
			jsObj.put("desc", desc);
			
			json = CommonUtil.jsonSuccess(jsObj);
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
		} finally {
			CommonUtil.responseWrite(response, json);
		}
	}
}
