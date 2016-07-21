package pb.repo.pcm.xmlrpc;

import java.util.HashMap;
import java.util.Map;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.PermissionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.pcm.constant.PcmReqConstant;
import pb.repo.pcm.model.PcmReqModel;
import pb.repo.pcm.service.PcmReqService;
import pb.repo.pcm.service.PcmReqWorkflowService;

@Service
public class PcmReqInvocationHandler
{
	
	private static Logger log = Logger.getLogger(PcmReqInvocationHandler.class);

	@Autowired
	private PcmReqService pcmReqService;
	
	@Autowired
	private PcmReqWorkflowService mainWorkflowService;
	
	@Autowired
	private PermissionService permissionService;
	
    public Map<String, Object> action(String prNo, String type, String comment, final String by)
//    public Map<String, Object> action(String prNo, String type, final String by)
    {
//    	String comment = null;
    	log.info("action("+prNo+","+type+","+comment+","+by+")");
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	try {
    		String statusDesc = null;
    		
	    	PcmReqModel pcmReqModel = null;

			if (type.equals(PcmReqConstant.ST_CLOSED_BY_PCM)) {
				statusDesc = "Approve";
				
		    	pcmReqModel = pcmReqService.get(prNo);
				final NodeRef folderNodeRef = new NodeRef(pcmReqModel.getFolderRef());
				
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
			if (type.equals(PcmReqConstant.ST_CANCEL_BY_PCM)) {
				statusDesc = "Cancel";
			} else {
    			result.put("success",false);
    			result.put("message","Invalid Type : "+type);
    			return result;
			}
    		
			if (pcmReqModel==null) {
				pcmReqModel = pcmReqService.get(prNo);
			}
	    	pcmReqModel.setStatus(type);
	    	pcmReqService.update(pcmReqModel);
    		
	    	mainWorkflowService.setModuleService(pcmReqService);
			mainWorkflowService.saveWorkflowHistory(null, by, "ผู้จัดซื้อ" , comment, statusDesc, null,  prNo, null);
			
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
