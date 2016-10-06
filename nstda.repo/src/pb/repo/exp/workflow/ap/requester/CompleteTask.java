package pb.repo.exp.workflow.ap.requester;

import java.util.Properties;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.workflow.WorkflowModel;
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
import pb.repo.admin.model.MainWorkflowReviewerModel;
import pb.repo.admin.service.AdminCompleteNotificationService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminViewerService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.admin.util.MainUserGroupUtil;
import pb.repo.admin.util.MainWorkflowUtil;
import pb.repo.exp.constant.ExpUseConstant;
import pb.repo.exp.constant.ExpUseWorkflowConstant;
import pb.repo.exp.model.ExpUseModel;
import pb.repo.exp.service.ExpUseService;
import pb.repo.exp.service.ExpUseWorkflowService;

@Component("pb.exp.workflow.ap.requester.CompleteTask")
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
	ExpUseService expUseService;
	
	@Autowired
	ExpUseWorkflowService mainWorkflowService;

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
	
	private static final String WF_PREFIX = ExpUseWorkflowConstant.MODEL_PREFIX;
	
	public void notify(final DelegateTask task)  {
		
		log.info("<- pr.requester.CompleteTask ->");
		
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
					ExpUseModel model = expUseService.get(id.toString());
					Integer level = model.getWaitingLevel();
					Integer lastLevel = mainWorkflowService.getLastReviewerLevel(model.getId());
					ExecutionEntity executionEntity = ((ExecutionEntity)task.getExecution()).getProcessInstance();
					
					String curUser = authenticationService.getCurrentUserName();
					String taskKey = task.getTaskDefinitionKey();
					String outcomeName = MainWorkflowConstant.TO_RESUBMIT;
					String action = task.getVariable(WF_PREFIX+outcomeName)!=null ? task.getVariable(WF_PREFIX+outcomeName).toString():null;
					
					log.info("  level:"+level);
					log.info("  last level:"+lastLevel);
					log.info("  action:"+action);
					
					mainWorkflowService.setModuleService(expUseService);
					
					String finalAction = action;
					if (action.equalsIgnoreCase(MainWorkflowConstant.TA_CANCEL)) {
						model.setStatus(ExpUseConstant.ST_CANCEL_BY_REQ);
						model.setWaitingLevel(null);
					}
					else
					if (action.equalsIgnoreCase(MainWorkflowConstant.TA_RESUBMIT)) {
						model.setStatus(ExpUseConstant.ST_WAITING);
						model.setWaitingLevel(1);
						
						MainWorkflowReviewerModel paramModel = new MainWorkflowReviewerModel();
						paramModel.setMasterId(id.toString());
						paramModel.setLevel(model.getWaitingLevel());
						MainWorkflowReviewerModel reviewerModel = mainWorkflowService.getReviewer(paramModel);
						if (reviewerModel != null) {
							executionEntity.setVariable(WF_PREFIX+"nextReviewers", MainUserGroupUtil.codes2logins(reviewerModel.getReviewerUser()));
						} else {
							executionEntity.setVariable(WF_PREFIX+"nextReviewers", "");
						}
					}
					
					executionEntity.setVariable(WF_PREFIX+outcomeName, action);
					
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "objectiveType");
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "objective");
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "reason");
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "budgetCc");
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "total");
					
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "requestedTime");
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "remark");
					
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "document");
					mainWorkflowService.updateExecutionEntity(executionEntity, task, "attachDocument");
					
					String desc = expUseService.getWorkflowDescription(model);
					
					task.getExecution().setVariable("bpm_"+WorkflowModel.PROP_DESCRIPTION.getLocalName(), desc);
					task.getExecution().setVariable("bpm_"+WorkflowModel.PROP_WORKFLOW_DESCRIPTION.getLocalName(), desc);	         
					
					// Keep TaskId to expusewf:taskHistory.
					String taskHistory = (String)executionEntity.getVariable(WF_PREFIX+"taskHistory");
					String finalTaskHistory = MainWorkflowUtil.appendTaskKey(taskHistory, taskKey, level);
					executionEntity.setVariable(WF_PREFIX+"taskHistory", finalTaskHistory);

					log.info("  status : "+model.getStatus()+", waitingLevel:"+model.getWaitingLevel());
					expUseService.updateStatus(model);
					
					executionEntity.setVariable(WF_PREFIX+"workflowStatus", action);
										
					// Comment History
					String taskComment = "";
					Object tmpComment = task.getVariable("bpm_comment");
					if(tmpComment != null && !tmpComment.equals("")){
						taskComment = tmpComment.toString();
					}
					
					action = mainWorkflowService.saveWorkflowHistory(executionEntity, curUser, MainWorkflowConstant.TN_PREPARER, taskComment, finalAction, task,  model.getId(), level, model.getStatus());
				}
				catch (Exception ex) {
					log.error(ex);
				}
				
				return null;
			}
		}, AuthenticationUtil.getAdminUserName()); // runAs()
	}
	
}
