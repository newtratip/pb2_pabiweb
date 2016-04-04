package pb.repo.pcm.workflow.pd.reviewer;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
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
import pb.repo.pcm.model.PcmWorkflowModel;
import pb.repo.pcm.service.PcmOrdService;
import pb.repo.pcm.service.PcmOrdWorkflowService;
import pb.repo.pcm.service.PcmSignatureService;

@Component("pb.pcm.workflow.pd.reviewer.EndTask")
public class EndTask implements ExecutionListener {
	
	private static Logger log = Logger.getLogger(EndTask.class);

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
	public void notify(DelegateExecution execution) throws Exception {
		
		log.info("<- pd.reviewer.EndTask ->");
		String curUser = authenticationService.getCurrentUserName();
		try {
		
			String id = (String)ObjectUtils.defaultIfNull(execution.getVariable(WF_PREFIX+"id"), "");
			log.info("  id:" + id);
			
			String varName = WF_PREFIX+"reviewOutcome";
			String workflowStatus = (String)execution.getVariable(varName);
			log.info("  reviewOutcome:" + workflowStatus);

			varName = WF_PREFIX+"nextReviewers";
			workflowStatus = (String)execution.getVariable(varName);
			log.info("  nextReviewers:" + workflowStatus);
		} catch (Exception ex) {
			log.error("", ex);
		}
        
	}
	
}

