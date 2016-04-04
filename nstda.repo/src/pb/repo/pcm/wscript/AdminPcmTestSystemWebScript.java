package pb.repo.pcm.wscript;


import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;

import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class AdminPcmTestSystemWebScript {
	
	private static Logger log = Logger.getLogger(AdminPcmTestSystemWebScript.class);

	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/admin/pcm/testSystem";
	
//  @Autowired	
//  AdminApprovalMatrixService approvalMatrixService;
  /**
   * Handles the "memoTestSystem" request. Note the use of Spring MVC-style annotations to map the Web Script URI configuration
   * and request handling objects.
   * 
   * @param name
   * @param response
   * @throws IOException
   */
  @Uri(URI_PREFIX+"/test")
  public void handleTestSystem(final WebScriptResponse response)
      throws IOException {
	  
	 // approvalMatrixService.test();
	  
	  final String message = String.format("Test Memo System, %s", "ABC");
	  response.getWriter().write(message);
  }

}
