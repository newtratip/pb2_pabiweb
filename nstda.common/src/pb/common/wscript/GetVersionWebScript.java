package pb.common.wscript;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.util.CommonUtil;

import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class GetVersionWebScript {

	private static Logger log = Logger.getLogger(GetVersionWebScript.class);
	
	 @Autowired
	 ServiceRegistry serviceRegistry;
	 
	 @Autowired
	 NodeService nodeService;
	
	 @Autowired
	 FileFolderService fileFolderService;
	 
	 
	 @Uri("/getVersion")
	  public void handleTestSystem(@RequestParam(required=false) String pdfNodeRef, final WebScriptResponse response)
	      throws IOException {
		  
		 Map<String, Object> map = new HashMap<String, Object>();
		
		 try {
			 
				final NodeRef nodeRef = new NodeRef(pdfNodeRef);
				
				log.info("pdfNodeRef:" + pdfNodeRef);
				
				AuthenticationUtil.setFullyAuthenticatedUser("admin");
				
				final Map<QName, Serializable> props = AuthenticationUtil.runAs(new RunAsWork<Map<QName, Serializable>>()
					    {
							public Map<QName, Serializable> doWork() throws Exception
							{
								return nodeService.getProperties(nodeRef);
							}
					    }, AuthenticationUtil.getFullyAuthenticatedUser());
				
				String version = null;
				for(QName key : props.keySet()) {
					log.info("key:"+key );
					if (key.toString().endsWith("versionLabel")) {
						version = (String)props.get(key);
					}
				}
				
				map.put("result", version);
	        
		 } catch (Exception ex) {
			 log.error("",ex);
		 }
		 
		 CommonUtil.responseWrite(response, map.toString());
		  
		  
	  }
	
}
