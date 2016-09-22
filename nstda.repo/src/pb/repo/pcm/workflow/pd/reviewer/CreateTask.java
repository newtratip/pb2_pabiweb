package pb.repo.pcm.workflow.pd.reviewer;

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

import pb.repo.admin.constant.MainWorkflowConstant;
import pb.repo.admin.model.MainWorkflowModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminViewerService;
import pb.repo.pcm.constant.PcmOrdWorkflowConstant;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.service.PcmOrdService;
import pb.repo.pcm.service.PcmOrdWorkflowService;

@Component("pb.pcm.workflow.pd.reviewer.CreateTask")
public class CreateTask implements TaskListener {
	
	private static Logger log = Logger.getLogger(CreateTask.class);

	@Autowired
	PcmOrdWorkflowService mainWorkflowService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	PcmOrdService pcmOrdService;
	
	@Autowired
	AdminViewerService viewerService;
	
	@Autowired
	AdminMasterService adminMasterService;
	
	private static final String WF_PREFIX = PcmOrdWorkflowConstant.MODEL_PREFIX;
	
	@Override
	public void notify(DelegateTask task) {
		
		ExecutionEntity executionEntity = ((ExecutionEntity)task.getExecution()).getProcessInstance();
	
		String taskKey = task.getTaskDefinitionKey();
		log.info("<- pd.reviewer.CreateTask -> Name:"+task.getName()+", ID:"+taskKey);
		log.info("  task.executionId="+task.getExecutionId());
		String curUser = authenticationService.getCurrentUserName();
		task.setVariable(WF_PREFIX+"currentTaskKey", taskKey);
		executionEntity.setVariable(WF_PREFIX+"currentTaskKey", taskKey);
		try {
		
			String id = (String)ObjectUtils.defaultIfNull(executionEntity.getVariable(WF_PREFIX+"id"), "");
			log.info("  id:" + id);
			PcmOrdModel model = pcmOrdService.get(id.toString());
			Integer level = model.getWaitingLevel();
			
			task.setVariable("bpm_status", mainWorkflowService.getWorkflowStatus(WF_PREFIX, executionEntity, taskKey, level));
			task.setVariable("bpm_reassignable", Boolean.FALSE);
			
			task.setName(MainWorkflowConstant.WF_TASK_NAMES.get(MainWorkflowConstant.TN_REVIEWER)+" "+level);
			
			log.info("  assignee:"+ task.getAssignee()+", level:"+level);
			/*
			 * Update DB
			 */
			String varName = WF_PREFIX+"workflowStatus";

			if (executionEntity.getVariable(varName)!=null) {
				MainWorkflowModel workflowModel = new MainWorkflowModel();
				workflowModel.setMasterId(id.toString());
				
				workflowModel = mainWorkflowService.getLastWorkflow(workflowModel);
				
				workflowModel.setAssignee(task.getAssignee());
				workflowModel.setTaskId("activiti$"+task.getId());
				mainWorkflowService.update(workflowModel);
			}
			
		} catch (Exception ex) {
			log.error("", ex);
		}
        
	}
	
}

