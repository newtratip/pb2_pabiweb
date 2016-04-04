package pb.repo.pcm.workflow.pr.reviewer;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.PersonService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminViewerService;
import pb.repo.pcm.constant.PcmWorkflowConstant;
import pb.repo.pcm.model.PcmReqModel;
import pb.repo.pcm.model.PcmWorkflowModel;
import pb.repo.pcm.service.PcmReqService;
import pb.repo.pcm.service.PcmReqWorkflowService;

@Component("pb.pcm.workflow.pr.reviewer.CreateTask")
public class CreateTask implements TaskListener {
	
	private static Logger log = Logger.getLogger(CreateTask.class);

	@Autowired
	PcmReqWorkflowService pcmWorkflowService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	PcmReqService pcmReqService;
	
	@Autowired
	AdminViewerService viewerService;
	
	@Autowired
	AdminMasterService adminMasterService;
	
	private static final String WF_PREFIX = PcmWorkflowConstant.MODEL_PREFIX;
	
	@Override
	public void notify(DelegateTask task) {
		
		ExecutionEntity executionEntity = ((ExecutionEntity)task.getExecution()).getProcessInstance();
	
		String taskKey = task.getTaskDefinitionKey();
		log.info("<- pr.reviewer.CreateTask -> Name:"+task.getName()+", ID:"+taskKey);
		String curUser = authenticationService.getCurrentUserName();
		task.setVariable(WF_PREFIX+"currentTaskKey", taskKey);
		executionEntity.setVariable(WF_PREFIX+"currentTaskKey", taskKey);
		try {
		
			String id = (String)ObjectUtils.defaultIfNull(executionEntity.getVariable(WF_PREFIX+"id"), "");
			log.info("  id:" + id);
			PcmReqModel model = pcmReqService.get(id.toString());
			Integer level = model.getWaitingLevel();
			
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
				String finalTaskKey = taskKey + (taskKey.equals("reviewerTask") ? (" "+level) : "");
				if(taskHistory.contains("|" + finalTaskKey)){
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
			
			/*
			 * Update DB
			 */
			if (executionEntity.getVariable(varName)==null) {
				Long wfKey = Long.valueOf(pcmWorkflowService.getKey());
				PcmWorkflowModel pcmWorkflowModel = new PcmWorkflowModel();
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
				PcmWorkflowModel pcmWorkflowModel = new PcmWorkflowModel();
				pcmWorkflowModel.setMasterId(id.toString());
				pcmWorkflowModel = pcmWorkflowService.getLastWorkflow(pcmWorkflowModel);
				pcmWorkflowModel.setTaskId("activiti$"+task.getId());
				pcmWorkflowService.update(pcmWorkflowModel);
			}
	
		} catch (Exception ex) {
			log.error("", ex);
		}
        
	}
	
}

