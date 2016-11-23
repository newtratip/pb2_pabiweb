package pb.repo.pcm.xmlrpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.workflow.WorkflowService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.util.NodeUtil;
import pb.repo.admin.constant.MainWorkflowConstant;
import pb.repo.admin.model.MainWorkflowModel;
import pb.repo.pcm.constant.PcmOrdConstant;
import pb.repo.pcm.exception.NotFoundApprovalMatrixException;
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
	
	@Autowired
	private WorkflowService workflowService;
	
    public Map<String, Object> action(Map<String, Object> params)
    {
    	log.info("action()");
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	String action = null;
    	try {
    		/*
    		 * Log Parameters
    		 */
    		log.info(params.get("action"));
    		log.info(params.get("pdNo"));
    		log.info(params.get("reqBy"));

    		/*
    		 * Create Pcm Ord
    		 */
    		Boolean found = false;
    		PcmOrdModel model = pcmOrdService.get((String)params.get("pdNo"), null);
    		if (model==null) {
    			model = new PcmOrdModel();
        		model.setId((String)params.get("pdNo"));
        		model.setCreatedBy((String)params.get("reqBy"));
    		} else {
    			found = true;
    		}
    		model.setUpdatedBy((String)params.get("reqBy"));
    		
    		Map<String, Object> srcDocMap = null;
    		List<Map<String, Object>> srcAttList = null;
    		
    		action = (String)params.get("action");
    		if (action.equals("1") || action.equals("2")) {
        		log.info(params.get("sectionId"));
        		log.info(params.get("prNo"));
        		log.info(params.get("docType"));
        		log.info(params.get("objective"));
        		log.info(params.get("total"));
        		log.info(params.get("appBy"));
//        		log.info(params.get("doc"));
//        		log.info(params.get("attachments"));

	    		model.setSectionId(Integer.parseInt((String)params.get("sectionId")));
	    		model.setPrId((String)params.get("prNo"));
	    		model.setDocType((String)params.get("docType"));
	    		model.setObjective((String)params.get("objective"));
	    		model.setTotal(Double.parseDouble((String)params.get("total")));
	    		model.setAppBy((String)params.get("appBy"));
	    		
	    		srcDocMap = (Map<String, Object>)params.get("doc");
	    		srcAttList = (List<Map<String,Object>>) params.get("attachments");
    		}
    		
    		if (action.equals("1")) { // Start New Workflow
    			if (found) {
    				throw new Exception("Existing PD No."+model.getId());
    			}
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
	    		model.setStatus(PcmOrdConstant.ST_WAITING);
	    		model.setWaitingLevel(1);
	    		
	    		model = pcmOrdService.save(model, srcDocMap, srcAttList);
	    		
    			MainWorkflowModel wfModel = new MainWorkflowModel();
    			wfModel.setMasterId(model.getId());
    			wfModel = mainWorkflowService.getLastWorkflow(wfModel);
    			
    			String comment = (String)params.get("comment");
    			
    			pcmOrdService.continueRequesterTask(wfModel.getExecutionId(), MainWorkflowConstant.TA_RESUBMIT, model, comment, (String)params.get("docType"));
    			
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
    		if (action.equals("3")) { // Cancel
    			MainWorkflowModel wfModel = new MainWorkflowModel();
    			wfModel.setMasterId(model.getId());
    			wfModel = mainWorkflowService.getLastWorkflow(wfModel);
    			
    			String comment = (String)params.get("comment");

    			pcmOrdService.continueRequesterTask(wfModel.getExecutionId(), MainWorkflowConstant.TA_CANCEL, model, comment,(String)params.get("docType"));
    			
    			result.put("success",true);
    			result.put("message","Success");
    		} 
    		else {
    			result.put("success",false);
    			result.put("message","Error : Invalid Action : "+action);
    		}
    	} catch (NotFoundApprovalMatrixException ex) {
    		log.error(ex);
    		ex.printStackTrace();
			result.put("success",false);
			result.put("message","Error : ไม่พบสายงานอนุมัติใบ พด. กรุณาติดต่อ ผู้ดูแลระบบ");
			if(action.equals("1")) {
				try {
					pcmOrdService.delete((String)params.get("pdNo"));
				} catch (Exception ex2) {
					log.error(ex2);
					ex2.printStackTrace();
				}
			}
    	} catch (Exception ex) {
    		log.error(ex);
    		ex.printStackTrace();
			result.put("success",false);
			result.put("message","Error : "+ex.getMessage());
    	}
    	
        return result;
    }
    
//    public Map<String, Object> updateStatus(String pdNo, String type, String comment, String by) {
//    	
//    	log.info("updateStatus("+pdNo+","+type+","+comment+","+by+")");
//    	
//    	Map<String, Object> result = new HashMap<String, Object>();
//    	
//       	try {
//    		String statusDesc = null;
//    		
//			if (type.equals(PcmOrdConstant.ST_WAITING)) {
//				statusDesc = MainWorkflowConstant.TA_RESUBMIT;
//			} 
//			else
//			if (type.equals(PcmOrdConstant.ST_CANCEL_BY_PCM)) {
//				statusDesc = MainWorkflowConstant.TA_CANCEL;
//			} else {
//    			result.put("success",false);
//    			result.put("message","Invalid Type : "+type);
//    			return result;
//			}
//    		
//	    	PcmOrdModel pcmOrdModel = pcmOrdService.get(pdNo);
//	    	pcmOrdModel.setStatus(type);
//	    	pcmOrdService.update(pcmOrdModel);
//    		
//	    	mainWorkflowService.setModuleService(pcmOrdService);
//			mainWorkflowService.saveWorkflowHistory(null, by, "ผู้จัดซื้อ" , comment, statusDesc, null,  pdNo, null);
//			
//			result.put("success",true);
//			result.put("message","Success");
//		} catch (Exception ex) {
//			log.error("",ex);
//			result.put("success",false);
//			result.put("message","Error : "+ex.toString());
//		}
//		
//	    return result;
//    }

}
