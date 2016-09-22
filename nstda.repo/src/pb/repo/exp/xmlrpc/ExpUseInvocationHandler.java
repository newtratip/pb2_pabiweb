package pb.repo.exp.xmlrpc;

import java.util.HashMap;
import java.util.Map;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.PermissionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.exp.constant.ExpUseConstant;
import pb.repo.exp.model.ExpUseModel;
import pb.repo.exp.service.ExpUseService;
import pb.repo.exp.service.ExpUseWorkflowService;

@Service
public class ExpUseInvocationHandler
{
	
	private static Logger log = Logger.getLogger(ExpUseInvocationHandler.class);

	@Autowired
	private ExpUseService expUseService;
	
	@Autowired
	private ExpUseWorkflowService mainWorkflowService;
	
	@Autowired
	private PermissionService permissionService;
	
    public Map<String, Object> action(Map<String, Object> params)
    {
    	log.info("params:"+params.toString());
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	try {
    		String statusDesc = null;
    		
	    	ExpUseModel expUseModel = null;
	    	
	    	String action = (String)params.get("action");
	    	String type = null;
	    	String apNo = (String)params.get("exNo");
	    	final String by = (String)params.get("by");

			if (action.equals("1")) {
				type = ExpUseConstant.ST_CLOSED_BY_FIN;
				statusDesc = "Approve";
				
		    	expUseModel = expUseService.get(apNo);
				final NodeRef folderNodeRef = new NodeRef(expUseModel.getFolderRef());
				
		        AuthenticationUtil.runAs(new RunAsWork<String>()
	    	    {
	    			public String doWork() throws Exception
	    			{
		        		if(!permissionService.getPermissions(folderNodeRef).contains(by)) {
		        			permissionService.setPermission(folderNodeRef, by, "SiteCollaborator", true);
		        		}

	    	        	return null;
	    			}
	    	    }, AuthenticationUtil.getAdminUserName());  
				
			} else 
			if (action.equals("2")) {
				statusDesc = "Cancel";
				type = ExpUseConstant.ST_CANCEL_BY_FIN;
			} else
			if (action.equals("3")) {
				statusDesc = "Paid";
				type = ExpUseConstant.ST_PAID_BY_FIN;
			} else {
    			result.put("success",false);
    			result.put("message","Invalid Action : "+action);
    			return result;
			}
    		
			if (expUseModel==null) {
				expUseModel = expUseService.get(apNo);
			}
	    	expUseModel.setStatus(type);
	    	expUseService.update(expUseModel);
    		
	    	mainWorkflowService.setModuleService(expUseService);
			mainWorkflowService.saveWorkflowHistory(null, by, "การเงิน" ,  (String)params.get("comment"), statusDesc, null,  apNo, null, type);
			
			result.put("success",true);
			result.put("message","Success");
    	} catch (Exception ex) {
    		log.error(ex);
    		ex.printStackTrace();
			result.put("success",false);
			result.put("message","Error : "+ex.toString());
    	}
    	
        return result;
    }
    
}
