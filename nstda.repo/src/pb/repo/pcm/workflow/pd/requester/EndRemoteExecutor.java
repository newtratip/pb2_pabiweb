package pb.repo.pcm.workflow.pd.requester;

import java.util.Properties;
import java.util.Set;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
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
import pb.repo.admin.model.MainWorkflowHistoryModel;
import pb.repo.admin.model.MainWorkflowModel;
import pb.repo.admin.model.MainWorkflowReviewerModel;
import pb.repo.admin.service.AdminCompleteNotificationService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminViewerService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.admin.util.MainUserGroupUtil;
import pb.repo.admin.util.MainWorkflowUtil;
import pb.repo.pcm.constant.PcmOrdConstant;
import pb.repo.pcm.constant.PcmOrdWorkflowConstant;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.service.PcmOrdService;
import pb.repo.pcm.service.PcmOrdWorkflowService;
import pb.repo.pcm.service.PcmOrdSignatureService;

@Component("pb.pcm.workflow.pd.requester.EndRemoteExecutor")
public class EndRemoteExecutor implements ExecutionListener {
	
	private static Logger log = Logger.getLogger(EndRemoteExecutor.class);
	
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
	
	private static final String WF_PREFIX = PcmOrdWorkflowConstant.MODEL_PREFIX;
	
	@Override
	public void notify(final DelegateExecution execution) throws Exception {
		log.info("<- pd.requester.EndRemoteExecutor ->");

		AuthenticationUtil.runAs(new RunAsWork<String>() {
			public String doWork() throws Exception
			{
				try {
					Object id = ObjectUtils.defaultIfNull(execution.getVariable(WF_PREFIX+"id"), "");

					log.info("  id :: " + id.toString());
					log.info("  execution.id :: "+execution.getId());

					Set<String> names =  execution.getVariableNames();
					for(String name : names) {
						log.info(" - name:"+name+":"+execution.getVariable(name));
					}
					
					String finalAction = (String)execution.getVariable(WF_PREFIX+"reSubmitOutcome");
					PcmOrdModel model = pcmOrdService.get(id.toString());
					Integer level = model.getWaitingLevel();
					log.info("finalAction:"+finalAction);
					mainWorkflowService.setModuleService(pcmOrdService);
					
					String curUser = (String)execution.getVariable("reqBy");
					String taskKey = MainWorkflowConstant.TN_PREPARER;
					if (finalAction.equals(MainWorkflowConstant.TA_CANCEL)) {
						model.setStatus(PcmOrdConstant.ST_CANCEL_BY_PCM);
						model.setWaitingLevel(null);
					} 
					else 
					if (finalAction.equals(MainWorkflowConstant.TA_RESUBMIT)) {
						log.info("RESUBMIT");
						model.setStatus(PcmOrdConstant.ST_WAITING);
						model.setWaitingLevel(1);
						
						MainWorkflowReviewerModel paramModel = new MainWorkflowReviewerModel();
						paramModel.setMasterId(id.toString());
						paramModel.setLevel(model.getWaitingLevel());
						MainWorkflowReviewerModel reviewerModel = mainWorkflowService.getReviewer(paramModel);
						if (reviewerModel != null) {
							execution.setVariable(WF_PREFIX+"nextReviewers", MainUserGroupUtil.codes2logins(reviewerModel.getReviewerUser()));
						} else {
							execution.setVariable(WF_PREFIX+"nextReviewers", "");
						}
					}
					
					String desc = pcmOrdService.getWorkflowDescription(model);
					
					execution.setVariable("bpm_"+WorkflowModel.PROP_DESCRIPTION.getLocalName(), desc);
					execution.setVariable("bpm_"+WorkflowModel.PROP_WORKFLOW_DESCRIPTION.getLocalName(), desc);	         
					
					// Keep TaskId to pcmwf:taskHistory.
					String taskHistory = (String)execution.getVariable(WF_PREFIX+"taskHistory");
					String finalTaskHistory = MainWorkflowUtil.appendTaskKey(taskHistory, taskKey, level);
					execution.setVariable(WF_PREFIX+"taskHistory", finalTaskHistory);

					log.info("  status : "+model.getStatus()+", waitingLevel:"+model.getWaitingLevel());
					pcmOrdService.updateStatus(model);
					
					// Comment History
					String taskComment = "";
					Object tmpComment = execution.getVariable("comment");
					if(tmpComment != null && !tmpComment.equals("")){
						taskComment = tmpComment.toString();
					}
					
					String action = mainWorkflowService.saveWorkflowHistory(execution, curUser, taskKey, taskComment, finalAction, null,  model.getId(), level, model.getStatus());
					
					if (finalAction.equals(MainWorkflowConstant.TA_RESUBMIT)) {
						MainWorkflowModel workflowModel = new MainWorkflowModel();
						workflowModel.setMasterId(id.toString());
						workflowModel = mainWorkflowService.getLastWorkflow(workflowModel);
				        MainWorkflowHistoryModel workflowHistoryModel = pcmOrdService.getAppByWorkflowHistory(model); // Extra History
				        if (workflowHistoryModel != null) {
					        workflowHistoryModel.setMasterId(workflowModel.getId());
					        mainWorkflowService.addWorkflowHistory(workflowHistoryModel);
				        }
					}
				}
				catch (Exception ex) {
					log.error(ex);
				}
				
				return null;
			}
		}, AuthenticationUtil.getAdminUserName()); // runAs()		
	}
	
}
