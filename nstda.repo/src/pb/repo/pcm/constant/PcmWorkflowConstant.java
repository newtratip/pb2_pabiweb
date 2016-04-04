package pb.repo.pcm.constant;

import org.alfresco.service.namespace.QName;


public class PcmWorkflowConstant {
    
    /*
     * Workflow Model Properties
     */
    public static final String MODEL_URI = "http://www.nstda.or.th/model/workflow/pcmwf/1.0";
    public static final String WF_URI = "{"+MODEL_URI+"}";
    public static final String MODEL_PREFIX = "pcmwf_";
    
    /*
     * Properties
     */
    public static final QName PROP_ID = QName.createQName(MODEL_URI, "id");
    public static final QName PROP_TARGET_FOLDER_NODE_REF = QName.createQName(MODEL_URI, "targetFolderNodeRef");
    public static final QName PROP_REQUESTED_TIME = QName.createQName(MODEL_URI, "requestedTime");
    
    public static final QName PROP_DOCUMENT = QName.createQName(MODEL_URI, "document");
    public static final QName PROP_ATTACH_DOCUMENT = QName.createQName(MODEL_URI, "attachDocument");
    public static final QName PROP_COMMENT_HISTORY = QName.createQName(MODEL_URI, "commentHistory");
    
    public static final QName PROP_REMARK = QName.createQName(MODEL_URI, "remark");
    public static final QName PROP_TASK_HISTORY = QName.createQName(MODEL_URI, "taskHistory");
    
    public static final QName PROP_NEXT_REVIEWERS = QName.createQName(MODEL_URI, "nextReviewers");
    
    public static final QName PROP_REVIEWER_OUTCOME = QName.createQName(MODEL_URI, "reviewerOutcome");
    public static final QName PROP_CONSULT_OUTCOME = QName.createQName(MODEL_URI, "consultOutcome");
    public static final QName PROP_RESUBMIT_OUTCOME = QName.createQName(MODEL_URI, "reSubmitOutcome");
    
}
