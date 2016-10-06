package pb.repo.admin.constant;

import java.util.HashMap;
import java.util.Map;

public class MainWorkflowConstant {
  
    /*
     * Task Name
     */
    public static final String TN_REQUESTER = "Requester";
    public static final String TN_PREPARER = "Preparer";
    public static final String TN_SUPERVISOR = "Supervisor";
    public static final String TN_PREPARER_RESUBMIT = "Preparer Resubmit";
    public static final String TN_REVIEWER = "Reviewer";
    public static final String TN_CONSULTANT = "Consultant";
    
    public static final String TN_PROCUREMENT = "Procurement";
    public static final String TN_PROCUREMENT_SUPERVISOR = "Procurement Supervisor";
    public static final String TN_FINANCE = "Finance";
    public static final String TN_FINANCE_OFFICER = "Finance Officer";
    
	public static final Map<String, String> WF_TASK_NAMES = new HashMap<String, String>();
    static {
    	WF_TASK_NAMES.put(TN_REQUESTER, "Requester");
    	WF_TASK_NAMES.put(TN_PREPARER, "Preparer");
    	WF_TASK_NAMES.put(TN_SUPERVISOR, "Supervisor");
    	WF_TASK_NAMES.put(TN_REVIEWER, "Reviewer");
    	WF_TASK_NAMES.put(TN_PREPARER_RESUBMIT, "Preparer Resubmit");
    	WF_TASK_NAMES.put(TN_CONSULTANT, "Consultant");
    	
    	WF_TASK_NAMES.put(TN_PROCUREMENT, "Procurement");
    	WF_TASK_NAMES.put(TN_PROCUREMENT_SUPERVISOR, "Procurement Supervisor");
    	
    	WF_TASK_NAMES.put(TN_FINANCE, "Finance");
    	WF_TASK_NAMES.put(TN_FINANCE_OFFICER, "Finance Officer");
    }
    
	public static final Map<String, String> WF_TASK_NAMES_TH = new HashMap<String, String>();
    static {
    	WF_TASK_NAMES_TH.put(TN_REQUESTER, "ผู้ขอ");
    	WF_TASK_NAMES_TH.put(TN_PREPARER, "ผู้บันทึก");
    	WF_TASK_NAMES_TH.put(TN_SUPERVISOR, "ผู้ตรวจสอบ");
    	WF_TASK_NAMES_TH.put(TN_REVIEWER, "ผู้อนุมัติขั้นที่");
    	WF_TASK_NAMES_TH.put(TN_PREPARER_RESUBMIT, "ผู้บันทึก ทบทวนอีกครั้ง");
    	WF_TASK_NAMES_TH.put(TN_CONSULTANT, "ที่ปรึกษาให้ความเห็น");
    	
    	WF_TASK_NAMES_TH.put(TN_PROCUREMENT, "ฝ่ายพัสดุ");
    	WF_TASK_NAMES_TH.put(TN_PROCUREMENT_SUPERVISOR, "หัวหน้าพัสดุ");
    	WF_TASK_NAMES_TH.put(TN_FINANCE, "การเงิน");
    	WF_TASK_NAMES_TH.put(TN_FINANCE_OFFICER, "เจ้าหน้าที่การเงิน");
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

    public static final String TA_PAID = "Paid";
    
    
}
