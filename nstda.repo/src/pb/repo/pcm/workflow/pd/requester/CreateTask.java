package pb.repo.pcm.workflow.pd.requester;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.alfresco.repo.workflow.activiti.ActivitiConstants;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.PersonService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminViewerService;
import pb.repo.pcm.constant.PcmWorkflowConstant;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.model.PcmOrdWorkflowModel;
import pb.repo.pcm.model.PcmWorkflowModel;
import pb.repo.pcm.service.PcmOrdService;
import pb.repo.pcm.service.PcmOrdWorkflowService;
import pb.repo.pcm.service.PcmSignatureService;

@Component("pb.pcm.workflow.pd.requester.CreateTask")
public class CreateTask implements TaskListener {
	
	private static Logger log = Logger.getLogger(CreateTask.class);

	@Autowired
	PcmOrdWorkflowService pcmWorkflowService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	PcmOrdService pcmReqService;
	
	@Autowired
	AdminViewerService viewerService;
	
	@Autowired
	PcmSignatureService memoSignatureService;
	
	@Autowired
	AdminMasterService adminMasterService;
	
	private static final String WF_PREFIX = PcmWorkflowConstant.MODEL_PREFIX;
	
	@Override
	public void notify(DelegateTask task) {
		
		ExecutionEntity executionEntity = ((ExecutionEntity)task.getExecution()).getProcessInstance();
	
		String taskKey = task.getTaskDefinitionKey();
		log.info("<- pd.requester.CreateTask -> No:"+task.getName()+", ID:"+taskKey);
		String curUser = authenticationService.getCurrentUserName();
		task.setVariable(WF_PREFIX+"currentTaskKey", taskKey);
		executionEntity.setVariable(WF_PREFIX+"currentTaskKey", taskKey);
		try {
		
//			/*
//			 * Percent Complete
//			 */
//			Double actual = 0.0;
//			if (executionEntity.getVariable(WF_PREFIX+"actualPercentComplete") != null) {
//				actual = (Double) executionEntity.getVariable(WF_PREFIX+"actualPercentComplete");
//			}
//			
//			if(actual.intValue() > 0){
//				
//				log.info("<- Reset ActualPercentComplete To 0 ->");
//				
//				executionEntity.setVariable(WF_PREFIX+"actualPercentComplete", Double.valueOf("0"));
//				
//			}
			String id = (String)ObjectUtils.defaultIfNull(executionEntity.getVariable(WF_PREFIX+"id"), "");
			log.info("  id:" + id);
			
			if(taskKey.equalsIgnoreCase("nextActionTask")) {
				// Sign Attach Doc : Method 2 : last reviewer approve
				
		    	HashMap<String, Object> config = new HashMap<String, Object>();
		    	List<Map<String,Object>> listMaster = adminMasterService.listByType(MainMasterConstant.TYPE_SYSTEM_CONFIG, null, null, null, null);
		    	for(Map<String,Object> m : listMaster) {
		    		config.put((String)m.get(MainMasterConstant.TFN_CODE), m.get(MainMasterConstant.TFN_FLAG1));
		    	}
				
				signAttachDoc(task, config);
			}
			else
			if (taskKey.equalsIgnoreCase("requesterTask")) {
				PcmOrdModel pcmReqModel = pcmReqService.get(id.toString());
				pcmReqModel.setWaitingLevel(0);
				pcmReqService.updateStatus(pcmReqModel);
			}

			
//			if(!taskKey.equalsIgnoreCase("nextActionTask")) {
//				
//				String str = taskKey.replaceAll("[^0-9]+", " ");
//				MemoModel memoModel = new MemoModel();
//				memoModel.setUpdatedBy(curUser);
//				memoModel.setId(memoId);
//				if (str!=null && str.equals("")) {
//					memoModel.setWaitingLevel(Integer.valueOf(str.trim()));
//				} else {
//					memoModel.setWaitingLevel(0);
//				}
//				memoService.updateStatus(memoModel);
//			}
			
			/*
			 * Status
			 */
			String varName = WF_PREFIX+"workflowStatus";
			String workflowStatus = (String)executionEntity.getVariable(varName);

			log.info("  workflowStatus:" + workflowStatus);
			
			if(workflowStatus == null || !workflowStatus.toUpperCase().equals("REJECT")){
				workflowStatus = "WAPPR";
				
				//Check current task is in taskHistory?
				log.info("  Check Task History");
				String taskHistory = (String)executionEntity.getVariable(WF_PREFIX+"taskHistory");
				log.info("  taskHistory:" + taskHistory);
				if(taskHistory.contains("|" + taskKey)){
					log.info("  Found Task in Task History -> RESUBMIT");
					workflowStatus = "RESUBMIT";
				}
			}
			
//			for(String n : task.getVariableNames()) {
//				Object obj = task.getVariable(n);
//				log.info(n+":"+obj+":+"+((obj!=null) ? obj.getClass().getName() : ""));
//			}
			
			workflowStatus = workflowStatus.toUpperCase();
			task.setVariable("bpm_status", workflowStatus);
			
			
			
//			task.setAssignee("admin");
//			if(taskKey.equals("requesterTask")){
//				task.setName("ผู้ขออนุมัติ ทบทวนอีกครั้ง:" + workflowStatus);
//			}			
//			else if(taskKey.equals("consultantTask")){
//				task.setName("ที่ปรึกษาให้ความเห็น:" + workflowStatus);
//			}
//			else if(taskKey.equals("nextActionTask")){
//				task.setName("หน่วยงานที่เกี่ยวข้องดำเนินการต่อ:" + workflowStatus);
//			}
//			else {
//				for(int i=1; i<=MemoConstant.MAX_APPROVER; i++) {
//					if(taskKey.equals("reviewer"+i+"Task")){
//						task.setName("ผู้อนุมัติขั้นที่ "+i+":" + workflowStatus);
//						break;
//					}
//				}
//			}
			
			/*
			 * Update DB
			 */
			if (executionEntity.getVariable(varName)==null) {
				Long wfKey = Long.valueOf(pcmWorkflowService.getKey());
				PcmOrdWorkflowModel pcmWorkflowModel = new PcmOrdWorkflowModel();
				pcmWorkflowModel.setId(wfKey);
				pcmWorkflowModel.setMasterId(id);
				pcmWorkflowModel.setType("M");
				pcmWorkflowModel.setWorkflowInsId((String)executionEntity.getVariable("workflowinstanceid"));
				pcmWorkflowModel.setTaskId("activiti$"+task.getId());
				pcmWorkflowModel.setStatus(null);
				pcmWorkflowModel.setBy(curUser);
				pcmWorkflowModel.setAssignee(task.getAssignee());
				pcmWorkflowModel.setCreatedBy(curUser);
				pcmWorkflowService.addWorkflow(pcmWorkflowModel);
			} else {
				PcmOrdWorkflowModel pcmWorkflowModel = new PcmOrdWorkflowModel();
				pcmWorkflowModel.setMasterId(id.toString());
				pcmWorkflowModel = pcmWorkflowService.getLastWorkflow(pcmWorkflowModel);
				pcmWorkflowModel.setTaskId("activiti$"+task.getId());
				pcmWorkflowService.update(pcmWorkflowModel);
			}
	
		} catch (Exception ex) {
			log.error("", ex);
		}
        
	}
	
	protected ServiceRegistry getServiceRegistry() {

        ProcessEngineConfigurationImpl config = Context.getProcessEngineConfiguration();

        if (config != null) {

            // Fetch the registry that is injected in the activiti spring-configuration

            ServiceRegistry registry = (ServiceRegistry) config.getBeans().get(ActivitiConstants.SERVICE_REGISTRY_BEAN_KEY);

            if (registry == null) {

                throw new RuntimeException(

                            "Service-registry not present in ProcessEngineConfiguration beans, expected ServiceRegistry with key" + 

                            ActivitiConstants.SERVICE_REGISTRY_BEAN_KEY);

            }

            return registry;

        }

        throw new IllegalStateException("No ProcessEngineConfiguration found in active context");

    }
	
	private void signAttachDoc(DelegateTask task, HashMap<String, Object> config) throws Exception {
		log.info("signAttachDoc()");
		MainMasterModel docSignModel = adminMasterService.getSystemConfig(MainMasterConstant.SCC_MEMO_DOC_SIGN);
		if (docSignModel != null && docSignModel.getFlag1().equals(CommonConstant.V_ENABLE)) {
			MainMasterModel docSignMethodModel = adminMasterService.getSystemConfig(MainMasterConstant.SCC_MEMO_DOC_SIGN_METHOD);
			if (docSignMethodModel != null && docSignMethodModel.getFlag1().equals("2")) { // method 2
				List<NodeRef> attachDocList = memoSignatureService.addDocSignatureAllApprover(task, config, WF_PREFIX);
				for(NodeRef attachDocRef : attachDocList) {
					viewerService.prepareForViewer(attachDocRef);
				}
			}
		}
	}
}

