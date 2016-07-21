package pb.repo.pcm.workflow.pr.consultant;

import java.util.Properties;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.alfresco.repo.forms.FormException;
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
import org.springframework.extensions.surf.util.I18NUtil;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.repo.admin.constant.MainWorkflowConstant;
import pb.repo.admin.model.MainWorkflowReviewerModel;
import pb.repo.admin.service.AdminCompleteNotificationService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminViewerService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.admin.util.MainUserGroupUtil;
import pb.repo.admin.util.MainUtil;
import pb.repo.admin.util.MainWorkflowUtil;
import pb.repo.pcm.constant.PcmReqConstant;
import pb.repo.pcm.constant.PcmReqWorkflowConstant;
import pb.repo.pcm.model.PcmReqModel;
import pb.repo.pcm.service.PcmReqService;
import pb.repo.pcm.service.PcmReqWorkflowService;
import pb.repo.pcm.service.PcmOrdSignatureService;

@Component("pb.pcm.workflow.pr.consultant.CompleteTask")
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
	PcmReqService pcmReqService;
	
	@Autowired
	PcmReqWorkflowService mainWorkflowService;

	@Autowired
	PcmOrdSignatureService signatureService;
	
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
	
	private static final String WF_PREFIX = PcmReqWorkflowConstant.MODEL_PREFIX;
	
	public void notify(final DelegateTask task)  {
		
		log.info("<- pr.consultant.CompleteTask ->");
		
		try {
		
			AuthenticationUtil.runAs(new RunAsWork<String>() {
				public String doWork() throws Exception
				{
					log.info("  task.getTaskDefinitionKey():" + task.getTaskDefinitionKey());
					log.info("  task.id="+task.getId());
					log.info("  task.Description="+task.getDescription());
					log.info("  task.EventName="+task.getEventName());
					log.info("  task.Name="+task.getName());
					log.info("  task.Owner="+task.getOwner());
					
						Object id = ObjectUtils.defaultIfNull(task.getVariable(WF_PREFIX+"id"), "");
						log.info("  id :: " + id.toString());
						PcmReqModel model = pcmReqService.get(id.toString());
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
						
						mainWorkflowService.setModuleService(pcmReqService);
						
						String finalAction = action;
						if (action.equalsIgnoreCase(MainWorkflowConstant.TA_COMMENT)) {
							Object comment = task.getVariable("bpm_comment");
							if (comment==null || comment.toString().trim().equals("")) {
								String errMsg = MainUtil.getMessageWithOutCode("ERR_WF_COMMENT_NO_COMMENT", I18NUtil.getLocale());
								throw new FormException(CommonConstant.FORM_ERR+errMsg);
							}
							
							Object counselee = task.getVariable(WF_PREFIX+"counselee");
							log.info("::::counselee:::::"+counselee);
							
							executionEntity.setVariable(WF_PREFIX+"nextReviewers", counselee);
							
							model.setStatus(PcmReqConstant.ST_WAITING);
							executionEntity.setVariable(WF_PREFIX+"workflowStatus", action);
						}
						
						executionEntity.setVariable(WF_PREFIX+outcomeName, action);
						
						mainWorkflowService.updateExecutionEntity(executionEntity, task, "requestedTime");
						mainWorkflowService.updateExecutionEntity(executionEntity, task, "remark");
						
						mainWorkflowService.updateExecutionEntity(executionEntity, task, "document");
						mainWorkflowService.updateExecutionEntity(executionEntity, task, "attachDocument");
						
						
						// Keep TaskId to pcmwf:taskHistory.
						String taskHistory = (String)executionEntity.getVariable(WF_PREFIX+"taskHistory");
						String finalTaskHistory = MainWorkflowUtil.appendTaskKey(taskHistory, taskKey, level);
						executionEntity.setVariable(WF_PREFIX+"taskHistory", finalTaskHistory);
						log.info("  taskHistory:" + finalTaskHistory);
	
						log.info("  status : "+model.getStatus()+", waitingLevel:"+model.getWaitingLevel());
						pcmReqService.updateStatus(model);
											
						// Comment History
						String taskComment = "";
						Object tmpComment = task.getVariable("bpm_comment");
						if(tmpComment != null && !tmpComment.equals("")){
							taskComment = tmpComment.toString();
						}
						
						action = mainWorkflowService.saveWorkflowHistory(executionEntity, curUser, task.getName(), taskComment, finalAction, task,  model.getId(), level);
	
					return null;
				}
			}, AuthenticationUtil.getAdminUserName()); // runAs()
		
		}
		catch (Exception ex) {
			log.error("",ex);
			ex.printStackTrace();
			throw ex;
		}		
	}
	
}
