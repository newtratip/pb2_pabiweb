package pb.repo.pcm.constant;

import org.alfresco.service.namespace.QName;

import pb.repo.admin.constant.MainWorkflowConstant;


public class PcmOrdWorkflowConstant extends MainWorkflowConstant {
    
    /*
     * Workflow Model Properties
     */
    public static final String MODEL_URI = "http://www.nstda.or.th/model/workflow/pcmordwf/1.0";
    public static final String WF_URI = "{"+MODEL_URI+"}";
    public static final String MODEL_PREFIX = "pcmordwf_";
    
    /*
     * Common Properties
     */
    public static final QName PROP_ID = QName.createQName(MODEL_URI, "id");
    public static final QName PROP_FOLDER_REF = QName.createQName(MODEL_URI, "folderRef");
    public static final QName PROP_REQUESTED_TIME = QName.createQName(MODEL_URI, "requestedTime");
    
    public static final QName PROP_DESCRIPTION = QName.createQName(MODEL_URI, "description");
    
    public static final QName PROP_DOCUMENT = QName.createQName(MODEL_URI, "document");
    public static final QName PROP_ATTACH_DOCUMENT = QName.createQName(MODEL_URI, "attachDocument");
//    public static final QName PROP_COMMENT_HISTORY = QName.createQName(MODEL_URI, "commentHistory");
    
    public static final QName PROP_REMARK = QName.createQName(MODEL_URI, "remark");
    public static final QName PROP_TASK_HISTORY = QName.createQName(MODEL_URI, "taskHistory");
    
    public static final QName PROP_NEXT_REVIEWERS = QName.createQName(MODEL_URI, "nextReviewers");
    
    public static final QName PROP_REVIEWER_OUTCOME = QName.createQName(MODEL_URI, TO_REVIEW);
    public static final QName PROP_CONSULT_OUTCOME = QName.createQName(MODEL_URI, TO_CONSULT);
    public static final QName PROP_RESUBMIT_OUTCOME = QName.createQName(MODEL_URI, TO_RESUBMIT);

    /*
     * Pcm Ord Properties
     */
    public static final QName PROP_REQUESTER = QName.createQName(MODEL_URI, "requester");
    public static final QName PROP_OBJECTIVE = QName.createQName(MODEL_URI, "objective");
    public static final QName PROP_METHOD = QName.createQName(MODEL_URI, "method");
    public static final QName PROP_TOTAL = QName.createQName(MODEL_URI, "total");
    
}
