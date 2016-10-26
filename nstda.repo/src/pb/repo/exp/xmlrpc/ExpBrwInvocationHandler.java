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

import pb.repo.admin.constant.MainWorkflowConstant;
import pb.repo.exp.constant.ExpBrwConstant;
import pb.repo.exp.model.ExpBrwModel;
import pb.repo.exp.service.ExpBrwService;
import pb.repo.exp.service.ExpBrwWorkflowService;

@Service
public class ExpBrwInvocationHandler
{
	
	private static Logger log = Logger.getLogger(ExpBrwInvocationHandler.class);

	@Autowired
	private ExpBrwService expBrwService;
	
	@Autowired
	private ExpBrwWorkflowService mainWorkflowService;
	
	@Autowired
	private PermissionService permissionService;
	
    public Map<String, Object> action(Map<String, Object> params)
    {
    	log.info("params:"+params.toString());
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	try {
    		String statusDesc = null;
    		
	    	ExpBrwModel expBrwModel = null;
	    	
	    	String action = (String)params.get("action");
	    	String type = null;
	    	String avNo = (String)params.get("avNo");
	    	final String by = (String)params.get("by");

			if (action.equals("1")) {
				type = ExpBrwConstant.ST_CLOSED_BY_FIN;
				statusDesc = "Approve";
				
		    	expBrwModel = expBrwService.get(avNo, null);
				final NodeRef folderNodeRef = new NodeRef(expBrwModel.getFolderRef());
				
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
				type = ExpBrwConstant.ST_CANCEL_BY_FIN;
//			} else
//			if (action.equals("3")) {
//				statusDesc = "Paid";
//				type = ExpBrwConstant.ST_PAID_BY_FIN;
			} else {
    			result.put("success",false);
    			result.put("message","Invalid Action : "+action);
    			return result;
			}
    		
			if (expBrwModel==null) {
				expBrwModel = expBrwService.get(avNo, null);
			}
	    	expBrwModel.setStatus(type);
	    	expBrwService.update(expBrwModel);
    		
	    	mainWorkflowService.setModuleService(expBrwService);
			mainWorkflowService.saveWorkflowHistory(null, by, MainWorkflowConstant.TN_FINANCE ,  (String)params.get("comment"), statusDesc, null,  avNo, null, type);
			
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
    
    public Map<String, Object> history(Map<String, Object> params)
    {
    	log.info("params:"+params.toString());
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	try {
	    	String avNo = (String)params.get("avNo");
	    	final String by = (String)params.get("by");
	    	String task = (String)params.get("task");
	    	String taskTh = (String)params.get("task_th");
	    	String status = (String)params.get("status");
	    	String statusTh = (String)params.get("status_th");
	    	
	    	ExpBrwModel expBrwModel = expBrwService.get(avNo, null);
			if (expBrwModel==null) {
    			result.put("success",false);
    			result.put("message","Invalid AV NO. : "+avNo);
    			return result;
			}
    		
			mainWorkflowService.saveWorkflowHistory(avNo, by, task, taskTh, status, statusTh, (String)params.get("comment"));
			
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
