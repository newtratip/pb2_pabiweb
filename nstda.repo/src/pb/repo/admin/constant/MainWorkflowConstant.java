package pb.repo.admin.constant;

import java.util.HashMap;
import java.util.Map;

public class MainWorkflowConstant {
    
  
    /*
     * Task Name
     */
    public static final String TN_REVIEWER = "Reviewer";
    public static final String TN_REQUESTER = "Requester";
    public static final String TN_CONSULTANT = "Consultant";
    
	public static final Map<String, String> WF_TASK_NAMES = new HashMap<String, String>();
    static {
    	WF_TASK_NAMES.put(TN_REVIEWER, "ผู้อนุมัติขั้นที่");
    	WF_TASK_NAMES.put(TN_REQUESTER, "ผู้ขออนุมัติ ทบทวนอีกครั้ง");
    	WF_TASK_NAMES.put(TN_CONSULTANT, "ที่ปรึกษาให้ความเห็น");
    }
    
    /*
     * Task Outcome
     */
    public static final String TO_REVIEW = "reviewOutcome";
    public static final String TO_RESUBMIT = "reSubmitOutcome";
    public static final String TO_CONSULT = "consultOutcome";
    
    
    /*
     * Task Action
     */
    public static final String TA_START = "Start";
    
    public static final String TA_APPROVE = "Approve"; // TO_REVIEW
    public static final String TA_REJECT = "Reject";
    public static final String TA_CONSULT = "Consult";
    
    public static final String TA_RESUBMIT = "Resubmit"; // TO_RESUBMIT   
    public static final String TA_CANCEL = "Cancel"; 
    
    public static final String TA_COMMENT = "Comment"; // TO_CONSULT
    
    public static final String TA_COMPLETE = "Complete";

}
