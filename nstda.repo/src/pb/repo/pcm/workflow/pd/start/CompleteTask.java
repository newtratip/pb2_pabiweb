package pb.repo.pcm.workflow.pd.start;

import java.util.Properties;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
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
import pb.repo.pcm.constant.PcmOrdWorkflowConstant;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.service.PcmOrdService;
import pb.repo.pcm.service.PcmOrdWorkflowService;
import pb.repo.pcm.service.PcmSignatureService;

@Component("pb.pcm.workflow.pd.start.CompleteTask")
public class CompleteTask implements ExecutionListener {
	
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
	PcmSignatureService signatureService;
	
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
		log.info("<- pd.start.CompleteTask ->");
		
		AuthenticationUtil.runAs(new RunAsWork<String>() {
			public String doWork() throws Exception
			{
				try {
					Object id = ObjectUtils.defaultIfNull(execution.getVariable(WF_PREFIX+"id"), "");
					
					log.info("  id :: " + id.toString());
					PcmOrdModel model = pcmOrdService.get(id.toString());
					Integer level = model.getWaitingLevel();
					
					String curUser = authenticationService.getCurrentUserName();
					String taskKey = MainWorkflowConstant.TN_REQUESTER_CAPTION;
					String finalAction = MainWorkflowConstant.TA_START;
					
					String taskComment = model.getObjective()+ " " + model.getDocType();
					
					mainWorkflowService.setModuleService(pcmOrdService);
					finalAction = mainWorkflowService.saveWorkflowHistory(execution, curUser, taskKey, taskComment, finalAction, null,  model.getId(), level);
				}
				catch (Exception ex) {
					log.error(ex);
				}
				
				return null;
			}
		}, AuthenticationUtil.getAdminUserName()); // runAs()		
	}
	
}
