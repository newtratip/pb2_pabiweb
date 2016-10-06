package pb.repo.exp.workflow.av.consultant;

import java.util.Properties;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.coci.CheckOutCheckInService;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.TemplateService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pb.repo.admin.constant.MainWorkflowConstant;
import pb.repo.admin.service.AdminCompleteNotificationService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminViewerService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.admin.util.MainWorkflowUtil;
import pb.repo.exp.constant.ExpBrwConstant;
import pb.repo.exp.constant.ExpBrwWorkflowConstant;
import pb.repo.exp.model.ExpBrwModel;
import pb.repo.exp.service.ExpBrwService;
import pb.repo.exp.service.ExpBrwWorkflowService;

@Component("pb.exp.workflow.av.consultant.CompleteTask")
public class CompleteTask implements TaskListener {
	
	private static Logger log = Logger.getLogger(CompleteTask.class);
	
	private static final long serialVersionUID = 1L;
	
	Properties properties = new Properties();
	
	@Autowired
	FileFolderService fileFolderService;
	
	@Autowired
	ContentService contentService;
	
	@Autowired
	CheckOutCheckInService checkOutCheckInService;
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	AuthenticationUtil authenticationUtil;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	ExpBrwService expBrwService;
	
	@Autowired
	ExpBrwWorkflowService mainWorkflowService;

	@Autowired
	AdminMasterService adminMasterService;
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	AlfrescoService alfrescoService;
	
	@Autowired
	AdminCompleteNotificationService completeNotificationService;
	
	@Autowired
	WorkflowService workflowService;
	
	@Autowired
	AdminViewerService viewerService;
	
	@Autowired
	TemplateService templateService;
	
	private static final String WF_PREFIX = ExpBrwWorkflowConstant.MODEL_PREFIX;
	
	public void notify(final DelegateTask task)  {
		
		log.info("<- pr.consultant.CompleteTask ->");
		
		AuthenticationUtil.runAs(new RunAsWork<String>() {
			public String doWork() throws Exception
			{
				log.info("  task.getTaskDefinitionKey():" + task.getTaskDefinitionKey());
				log.info("  task.id="+task.getId());
				log.info("  task.Description="+task.getDescription());
				log.info("  task.EventName="+task.getEventName());
				log.info("  task.Name="+task.getName());
				log.info("  task.Owner="+task.getOwner());
				
				try {
					Object id = ObjectUtils.defaultIfNull(task.getVariable(WF_PREFIX+"id"), "");
					log.info("  id :: " + id.toString());
					ExpBrwModel model = expBrwService.get(id.toString());
					Integer level = model.getWaitingLevel();
					Integer lastLevel = mainWorkflowService.getLastReviewerLevel(model.getId());
					ExecutionEntity executionEntity = ((ExecutionEntity)task.getExecution()).getProcessInstance();
					
					String curUser = authenticationService.getCurrentUserName();
					String taskKey = task.getTaskDefinitionKey();
					String outcomeName = MainWorkflowConstant.TO_CONSULT;
					String action = task.getVariable(WF_PREFIX+outcomeName)!=null ? task.getVariable(WF_PREFIX+outcomeName).toString():null;
					
					log.info("  level:"+level);
					log.info("  last level:"+lastLevel);
					log.info("  action:"+action);
					
					mainWorkflowService.setModuleService(expBrwService);
					
					String finalAction = action;
					if (action.equalsIgnoreCase(MainWorkflowConstant.TA_COMMENT)) {
						Object counselee = task.getVariable(WF_PREFIX+"counselee");
						log.info("::::counselee:::::"+counselee);
						
						executionEntity.setVariable(WF_PREFIX+"nextReviewers", counselee);
						
						model.setStatus(ExpBrwConstant.ST_WAITING);
						executionEntity.setVariable(WF_PREFIX+"workflowStatus", action);
					}
					
					executionEntity.setVariable(WF_PREFIX+outcomeName, action);
					
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "requestedTime");
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "remark");
					
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "document");
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "attachDocument");
					
					
					// Keep TaskId to expbrwwf:taskHistory.
					String taskHistory = (String)executionEntity.getVariable(WF_PREFIX+"taskHistory");
					String finalTaskHistory = MainWorkflowUtil.appendTaskKey(taskHistory, taskKey, level);
					executionEntity.setVariable(WF_PREFIX+"taskHistory", finalTaskHistory);
					log.info("  taskHistory:" + finalTaskHistory);

					log.info("  status : "+model.getStatus()+", waitingLevel:"+model.getWaitingLevel());
					expBrwService.updateStatus(model);
										
					// Comment History
					String taskComment = "";
					Object tmpComment = task.getVariable("bpm_comment");
					if(tmpComment != null && !tmpComment.equals("")){
						taskComment = tmpComment.toString();
					}
					
					action = mainWorkflowService.saveWorkflowHistory(executionEntity, curUser, MainWorkflowConstant.TN_CONSULTANT, taskComment, finalAction, task,  model.getId(), level, model.getStatus());

				}
				catch (Exception ex) {
					log.error(ex);
				}
				
				return null;
			}
		}, AuthenticationUtil.getAdminUserName()); // runAs()
	}
	
}
