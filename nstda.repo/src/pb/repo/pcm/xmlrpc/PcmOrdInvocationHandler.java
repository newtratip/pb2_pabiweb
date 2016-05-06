package pb.repo.pcm.xmlrpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.util.NodeUtil;
import pb.repo.admin.service.MainWorkflowService;
import pb.repo.pcm.constant.PcmOrdConstant;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.service.PcmOrdService;

@Service
public class PcmOrdInvocationHandler
{
	
	private static Logger log = Logger.getLogger(PcmOrdInvocationHandler.class);

	@Autowired
	private PcmOrdService pcmOrdService;
	
	@Autowired
	private MainWorkflowService mainWorkflowService;
	
    public Map<String, Object> create(Map<String, Object> params)
    {
    	log.info("create("+params.toString()+")");
    	
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
    		log.info(params.get("doc"));
    		log.info(params.get("attachments"));
    		
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
    		
    		model.setStatus(PcmOrdConstant.ST_WAITING);
    		model.setWaitingLevel(1);
    		
    		Map<String, Object> srcDocMap = (Map<String, Object>)params.get("doc");
    		List<Map<String, Object>> srcAttList = (List<Map<String,Object>>) params.get("attachments");
    		
    		model = pcmOrdService.save(model, srcDocMap, srcAttList);
   		
    		/*
    		 * Start Workflow
    		 */
    		mainWorkflowService.setModuleService(pcmOrdService);
			mainWorkflowService.startWorkflow(model);
    		
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
    	} catch (Exception ex) {
    		log.error(ex);
    		ex.printStackTrace();
			result.put("success",false);
			result.put("message","Error : "+ex.toString());
    	}
    	
        return result;
    }

}
