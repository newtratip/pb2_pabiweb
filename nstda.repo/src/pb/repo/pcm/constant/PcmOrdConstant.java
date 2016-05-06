package pb.repo.pcm.constant;

import java.util.LinkedHashMap;
import java.util.Map;

import pb.repo.admin.constant.SubModuleConstant;


public class PcmOrdConstant extends SubModuleConstant {

	public static final String TABLE_NAME = "pb2_pcm_ord";
	
    /*
     * Table Field Name
     */
	public static final String TFN_OBJECTIVE = "OBJECTIVE";
	public static final String TFN_SECTION_ID = "SECTION_ID";
	public static final String TFN_PR_ID = "PR_ID";
	public static final String TFN_APP_BY = "APP_BY";
	public static final String TFN_DOC_TYPE = "DOC_TYPE";
	
	public static final String TFN_TOTAL = "TOTAL";
	public static final String TFN_STATUS = "STATUS";
	
    /*
     * JSON Field Name
     */
	public static final String JFN_OBJECTIVE = "objective";
	public static final String JFN_SECTION_ID = "section_id";
	public static final String JFN_PR_ID = "pr_id";
	public static final String JFN_APP_BY = "app_by";
	public static final String JFN_DOC_TYPE = "doc_type";
	
	public static final String JFN_TOTAL = "total";
	public static final String JFN_STATUS = "status";
	public static final String JFN_WF_STATUS = "wfStatus";
	public static final String JFN_ACTION = "action";
	
	public static final String JFN_CREATED_TIME_SHOW = "created_time_show";


	/*
	 * Status
	 */
	public static final String ST_WAITING = "W1";
	public static final String ST_WAITING_REJECT = "W2";
	public static final String ST_CLOSED_BY_ACT = "C1";
	public static final String ST_CANCEL_BY_PCM = "X1";
	public static final String ST_CONSULT = "S";
	
	public static final Map<String, Object> DISPLAY_TYPES = new LinkedHashMap<String, Object>();
	
    static {
    	DISPLAY_TYPES.put(ST_WAITING, "Waiting Reviewer");
    	DISPLAY_TYPES.put(ST_WAITING_REJECT, "Reviewer Reject");
    	DISPLAY_TYPES.put(ST_CLOSED_BY_ACT, "PO");
    	DISPLAY_TYPES.put(ST_CANCEL_BY_PCM, "Cancel by Procurement");
    	DISPLAY_TYPES.put(ST_CONSULT, "Consult");
    }
    
    /*
     * ACTION
     */
    public static final String ACTION_SHOW_DIAGRAM = "S";
    public static final String ACTION_GOTO_FOLDER = "F";
    public static final String ACTION_SHOW_HISTORY = "H";
    public static final String ACTION_SHOW_DETAIL = "V";
    
    /*
     * Workflow Name
     */
    public static final String WF_NAME = "NSTDAPcmPD";
    
}
