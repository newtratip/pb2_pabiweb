package pb.repo.pcm.workflow.pd.reviewer;

import java.util.Locale;
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
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.repo.admin.constant.MainWorkflowConstant;
import pb.repo.admin.model.MainWorkflowModel;
import pb.repo.admin.model.MainWorkflowReviewerModel;
import pb.repo.admin.service.AdminCompleteNotificationService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminViewerService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.admin.util.MainUserGroupUtil;
import pb.repo.admin.util.MainUtil;
import pb.repo.admin.util.MainWorkflowUtil;
import pb.repo.pcm.constant.PcmOrdWorkflowConstant;
import pb.repo.pcm.constant.PcmReqConstant;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.service.InterfaceService;
import pb.repo.pcm.service.PcmOrdService;
import pb.repo.pcm.service.PcmOrdSignatureService;
import pb.repo.pcm.service.PcmOrdWorkflowService;

@Component("pb.pcm.workflow.pd.reviewer.CompleteTask")
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
	PcmOrdService pcmOrdService;
	
	@Autowired
	PcmOrdWorkflowService mainWorkflowService;

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
	
	@Autowired
	InterfaceService interfaceService;
	
	@Autowired
	AuthenticationService authService;
	
	private static final String WF_PREFIX = PcmOrdWorkflowConstant.MODEL_PREFIX;
	
	public void notify(final DelegateTask task) {
		
		log.info("<- pd.reviewer.CompleteTask ->");
		
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
					log.info("  task.executionId="+task.getExecutionId());
					
						Object id = ObjectUtils.defaultIfNull(task.getVariable(WF_PREFIX+"id"), "");
						log.info("  id :: " + id.toString());
						PcmOrdModel model = pcmOrdService.get(id.toString(), null);
						Integer level = model.getWaitingLevel();
						Integer lastLevel = mainWorkflowService.getLastReviewerLevel(model.getId());
						ExecutionEntity executionEntity = ((ExecutionEntity)task.getExecution()).getProcessInstance();
						
						String curUser = authenticationService.getCurrentUserName();
						String taskKey = task.getTaskDefinitionKey();
						String outcomeName = MainWorkflowConstant.TO_REVIEW;
						String action = task.getVariable(WF_PREFIX+outcomeName)!=null ? task.getVariable(WF_PREFIX+outcomeName).toString():null;
						
						log.info("  level:"+level);
						log.info("  last level:"+lastLevel);
						log.info("  action:"+action);
						
						mainWorkflowService.setModuleService(pcmOrdService);
						
						String finalAction = action;
						if (action.equalsIgnoreCase(MainWorkflowConstant.TA_REJECT)) {
							Object comment = task.getVariable("bpm_comment");
							if (comment==null || comment.toString().trim().equals("")) {
								String lang = (String)task.getVariable(WF_PREFIX+"lang");
								String errMsg = MainUtil.getMessageWithOutCode("ERR_WF_REJECT_NO_COMMENT", new Locale(lang));
								throw new FormException(CommonConstant.FORM_ERR+errMsg);
							}
							
							String result = interfaceService.updateStatusPD(model, finalAction, curUser, (String)comment, authService.getCurrentUserName());
							
							if (!result.equals("OK")) {
								throw new FormException(CommonConstant.FORM_ERR+result);
							}
							
							model.setStatus(PcmReqConstant.ST_WAITING_REJECT);
							model.setWaitingLevel(0);
							
							MainWorkflowModel wfModel = new MainWorkflowModel();
							wfModel.setMasterId(id.toString());
									
							wfModel = mainWorkflowService.getLastWorkflow(wfModel);
							wfModel.setExecutionId(task.getExecutionId());
							mainWorkflowService.update(wfModel);
						}
						else
						if (action.equalsIgnoreCase(MainWorkflowConstant.TA_APPROVE)) {
							if (lastLevel.equals(level)) {
								Object comment = task.getVariable("bpm_comment");
								if (comment==null) {
									comment = "";
								}
								String result = interfaceService.updateStatusPD(model, finalAction, curUser, (String)comment, model.getCreatedBy());
								
								if (!result.equals("OK")) {
									throw new FormException(CommonConstant.FORM_ERR+result);
								}
							}
							
							model.setWaitingLevel(model.getWaitingLevel()!=null ? model.getWaitingLevel()+1 : null);
							
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
						else
						if (action.equalsIgnoreCase(MainWorkflowConstant.TA_CONSULT)) {
							Object  consultant = task.getVariable(WF_PREFIX+"consultant");
							if (consultant==null || consultant.equals("")) {
								String lang = (String)task.getVariable(WF_PREFIX+"lang");
								String errMsg = MainUtil.getMessageWithOutCode("ERR_WF_CONSULT_NO_CONSULTANT", new Locale(lang));
								throw new FormException(CommonConstant.FORM_ERR+errMsg);
							}
							
							model.setStatus(PcmReqConstant.ST_CONSULT);
							executionEntity.setVariable(WF_PREFIX+"nextReviewers", consultant);
							executionEntity.setVariable(WF_PREFIX+"counselee", curUser);
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
	
						log.info("  status : "+model.getStatus()+", waitingLevel:"+model.getWaitingLevel());
//						pcmOrdService.updateStatus(model);
						
						executionEntity.setVariable(WF_PREFIX+"workflowStatus", action);
											
						// Comment History
						String taskComment = "";
						Object tmpComment = task.getVariable("bpm_comment");
						if(tmpComment != null && !tmpComment.equals("")){
							taskComment = tmpComment.toString();
						}
						
						action = mainWorkflowService.saveWorkflowHistory(executionEntity, curUser, MainWorkflowConstant.TN_REVIEWER, taskComment, finalAction, task,  model.getId(), level, model.getStatus());
						
						pcmOrdService.update(model);
						mainWorkflowService.updateWorkflow(model, task);
						
						if (finalAction.equalsIgnoreCase(MainWorkflowConstant.TA_APPROVE) && level.equals(lastLevel)) {
							signatureService.addSignature(task, curUser, lastLevel);
						}
						
					return null;
				}
			}, AuthenticationUtil.getAdminUserName()); // runAs()
		
		}
		catch (Exception ex) {
			log.error("",ex);
			throw ex;
		}
	}

}
