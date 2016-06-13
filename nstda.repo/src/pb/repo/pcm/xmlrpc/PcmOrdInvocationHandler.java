package pb.repo.pcm.xmlrpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.util.NodeUtil;
import pb.repo.pcm.constant.PcmOrdConstant;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.service.PcmOrdService;
import pb.repo.pcm.service.PcmOrdWorkflowService;

@Service
public class PcmOrdInvocationHandler
{
	
	private static Logger log = Logger.getLogger(PcmOrdInvocationHandler.class);

	@Autowired
	private PcmOrdService pcmOrdService;
	
	@Autowired
	private PcmOrdWorkflowService mainWorkflowService;
	
    public Map<String, Object> create(Map<String, Object> params)
    {
    	log.info("create()");
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	try {
    		/*
    		 * Log Parameters
    		 */
    		log.info(params.get("action"));
    		log.info(params.get("pdNo"));
    		log.info(params.get("sectionId"));
    		log.info(params.get("prNo"));
    		log.info(params.get("docType"));
    		log.info(params.get("objective"));
    		log.info(params.get("total"));
    		log.info(params.get("reqBy"));
    		log.info(params.get("appBy"));
//    		log.info(params.get("doc"));
//    		log.info(params.get("attachments"));
    		
    		/*
    		 * Create Pcm Ord
    		 */
    		PcmOrdModel model = new PcmOrdModel();
    		model.setId((String)params.get("pdNo"));
    		model.setSectionId(Integer.parseInt((String)params.get("sectionId")));
    		model.setPrId((String)params.get("prNo"));
    		model.setDocType((String)params.get("docType"));
    		model.setObjective((String)params.get("objective"));
    		model.setTotal(Double.parseDouble((String)params.get("total")));
    		model.setCreatedBy((String)params.get("reqBy"));
    		model.setAppBy((String)params.get("appBy"));
    		
    		Map<String, Object> srcDocMap = (Map<String, Object>)params.get("doc");
    		List<Map<String, Object>> srcAttList = (List<Map<String,Object>>) params.get("attachments");

    		String action = (String)params.get("action");
    		
    		if (action.equals("1")) { // Start New Workflow
	    		model.setStatus(PcmOrdConstant.ST_WAITING);
	    		model.setWaitingLevel(1);
	    		
	    		model = pcmOrdService.save(model, srcDocMap, srcAttList);
	   		
	    		/*
	    		 * Start Workflow
	    		 */
	    		mainWorkflowService.setModuleService(pcmOrdService);
				mainWorkflowService.startWorkflow(model, (String)params.get("docType"));
	    		
	    		/*
	    		 * Result
	    		 */
	    		Map<String, Object> docMap = new HashMap<String, Object>();
	    		docMap.put("name", srcDocMap.get("name"));
	    		docMap.put("url", NodeUtil.trimNodeRef(model.getDocRef()));
	    		
	    		List<Map<String, Object>> attList = new ArrayList<Map<String, Object>>();
	    		int i = 0;
	    		for(Map<String, Object> srcAttMap:srcAttList) {
		    		Map<String, Object> attMap = new HashMap<String, Object>();
		    		attMap.put("name", srcAttMap.get("name"));
		    		attMap.put("url", NodeUtil.trimNodeRef(model.getListAttachDoc().get(i++)));
		    		attList.add(attMap);
	    		}
	    		
				result.put("success",true);
				result.put("message","Success");
				result.put("doc", docMap);
				result.put("attachments", attList);
    		}
    		else 
    		if (action.equals("2")) { // Resubmit
    			result.put("success",false);
    			result.put("message","Error : Not Implemented yet : Action : "+action);
    		} 
    		else {
    			result.put("success",false);
    			result.put("message","Error : Invalid Action : "+action);
    		}
    	} catch (Exception ex) {
    		log.error(ex);
    		ex.printStackTrace();
			result.put("success",false);
			result.put("message","Error : "+ex.toString());
    	}
    	
        return result;
    }
    
    public Map<String, Object> updateStatus(String pdNo, String type, String by) {
    	
    	log.info("updateStatus("+pdNo+","+type+","+by+")");
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
       	try {
    		String statusDesc = null;
    		
			if (type.equals(PcmOrdConstant.ST_CANCEL_BY_PCM)) {
				statusDesc = "Cancel";
			} else {
    			result.put("success",false);
    			result.put("message","Invalid Type : "+type);
    			return result;
			}
    		
	    	PcmOrdModel pcmOrdModel = pcmOrdService.get(pdNo);
	    	pcmOrdModel.setStatus(type);
	    	pcmOrdService.update(pcmOrdModel);
    		
	    	mainWorkflowService.setModuleService(pcmOrdService);
			mainWorkflowService.saveWorkflowHistory(null, by, "ผู้จัดซื้อ" , null, statusDesc, null,  pdNo, null);
			
			result.put("success",true);
			result.put("message","Success");
		} catch (Exception ex) {
			log.error("",ex);
			result.put("success",false);
			result.put("message","Error : "+ex.toString());
		}
		
	    return result;
    }

}
