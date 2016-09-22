package pb.repo.exp.wscript;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminMsgService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.exp.xmlrpc.ExpUseInvocationHandler;
import pb.repo.exp.xmlrpc.ExpBrwInvocationHandler;
import redstone.xmlrpc.XmlRpcDispatcher;
import redstone.xmlrpc.XmlRpcServer;

import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class ExpXmlRpcWebScript {
	
	private static Logger log = Logger.getLogger(ExpXmlRpcWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/exp";
	
	@Autowired
	AlfrescoService alfrescoService;
	
	@Autowired
	AdminMasterService masterService;
	
	@Autowired
	AdminMsgService msgService;
	
	@Autowired
	AuthenticationService authService;
	
	@Autowired
	ExpBrwInvocationHandler expBrwInvocationHandler;
	
	@Autowired
	ExpUseInvocationHandler expUseInvocationHandler;
	
	@Uri(method=HttpMethod.POST, value=URI_PREFIX+"/inf")
	public void handleOdooXmlrpc(final WebScriptRequest request, final WebScriptResponse response) throws Throwable {
		
		try {
			log.info("/exp/inf");
			log.info("  request="+request.getContent().getContent());
			
			XmlRpcServer server = new XmlRpcServer();
			server.addInvocationHandler("brw", expBrwInvocationHandler);
			server.addInvocationHandler("use", expUseInvocationHandler);
			
			XmlRpcDispatcher dispatcher = new XmlRpcDispatcher(server, "");
			
			InputStream is = new ByteArrayInputStream(request.getContent().getContent().getBytes("UTF-8"));
			response.setContentEncoding("UTF-8");
			dispatcher.dispatch(is, response.getWriter());
			
			
		} catch (Exception ex) {
			log.error(ex);
		}

	}
	
}
