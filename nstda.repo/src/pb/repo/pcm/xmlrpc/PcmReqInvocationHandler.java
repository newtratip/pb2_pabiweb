package pb.repo.pcm.xmlrpc;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.admin.service.MainWorkflowService;
import pb.repo.pcm.constant.PcmReqConstant;
import pb.repo.pcm.model.PcmReqModel;
import pb.repo.pcm.service.PcmReqService;

@Service
public class PcmReqInvocationHandler
{
	
	private static Logger log = Logger.getLogger(PcmReqInvocationHandler.class);

	@Autowired
	private PcmReqService pcmReqService;
	
	@Autowired
	private MainWorkflowService mainWorkflowService;
	
    public Map<String, Object> action(String prNo, String type, String by)
    {
    	log.info("action("+prNo+","+type+","+by+")");
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	try {
    		String statusDesc = null;
    		
			if (type.equals(PcmReqConstant.ST_CLOSED_BY_PCM)) {
				statusDesc = "Approve";
			} else 
			if (type.equals(PcmReqConstant.ST_CANCEL_BY_PCM)) {
				statusDesc = "Cancel";
			} else {
    			result.put("success",false);
    			result.put("message","Invalid Type : "+type);
    			return result;
			}
    		
	    	PcmReqModel pcmReqModel = pcmReqService.get(prNo);
	    	pcmReqModel.setStatus(type);
	    	pcmReqService.update(pcmReqModel);
    		
	    	mainWorkflowService.setModuleService(pcmReqService);
			mainWorkflowService.saveWorkflowHistory(null, by, "ผู้จัดซื้อ" , null, statusDesc, null,  prNo, null);
			
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
