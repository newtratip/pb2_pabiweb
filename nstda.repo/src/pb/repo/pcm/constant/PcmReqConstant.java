package pb.repo.pcm.constant;

import java.util.LinkedHashMap;
import java.util.Map;


public class PcmReqConstant {

	public static final String TABLE_NAME = "pb2_pcm_req";
	
    /*
     * Table Field Name
     */
	public static final String TFN_ID = "ID";
	
	public static final String TFN_TOTAL = "TOTAL";
	public static final String TFN_DOC_REF = "DOC_REF";
	public static final String TFN_FOLDER_REF = "FOLDER_REF";
	public static final String TFN_WORKFLOW_INS_ID = "WORKFLOW_INS_ID";
	public static final String TFN_WAITING_LEVEL = "WAITING_LEVEL";
	public static final String TFN_STATUS = "STATUS";
	
	public static final String TFN_REQ_BY = "REQ_BY"; 
	public static final String TFN_REQ_BU = "REQ_BU"; 
	public static final String TFN_REQ_OU = "REQ_OU"; 

	public static final String TFN_OBJECTIVE_TYPE = "OBJECTIVE_TYPE"; // buy, employ, hire
	public static final String TFN_OBJECTIVE = "OBJECTIVE";
	public static final String TFN_REASON = "REASON";
	
	public static final String TFN_CURRENCY = "CURRENCY"; // THB, USD, EUR, JPY, CNY
	public static final String TFN_CURRENCY_RATE = "CURRENCY_RATE";
	
	public static final String TFN_BUDGET_CC = "BUDGET_CC";
	public static final String TFN_STOCK_OU = "STOCK_OU";

	public static final String TFN_PROTOTYPE = "PROTOTYPE";
	public static final String TFN_EVENT = "EVENT";
	
	public static final String TFN_PCM_OU = "PCM_OU";
	public static final String TFN_LOCATION = "LOCATION";
	
	public static final String TFN_ACROSS_BUDGET = "ACROSS_BUDGET";
	public static final String TFN_REF_ID = "REF_ID";
	
	public static final String TFN_METHOD = "METHOD";
	public static final String TFN_METHOD_COND2_RULE = "METHOD_COND2_RULE";
	public static final String TFN_METHOD_COND2 = "METHOD_COND2";
	public static final String TFN_METHOD_COND2_DTL = "METHOD_COND2_DTL";
	
	public static final String TFN_CREATED_TIME = "CREATED_TIME";
	public static final String TFN_CREATED_BY = "CREATED_BY";
	public static final String TFN_UPDATED_TIME = "UPDATED_TIME";
	public static final String TFN_UPDATED_BY = "UPDATED_BY";
	
    /*
     * JSON Field Name
     */
	public static final String JFN_ID = "id";
	
	public static final String JFN_TOTAL = "total";
	public static final String JFN_WAITING_LEVEL = "wLvl";
	public static final String JFN_STATUS = "status";
	public static final String JFN_WF_STATUS = "wfStatus";
	public static final String JFN_WORKFLOW_INS_ID = "workflow_ins_id";
	public static final String JFN_DOC_REF = "doc_ref";
	public static final String JFN_FOLDER_REF = "folder_ref";
	public static final String JFN_OVER_DUE = "overDue";
	
	public static final String JFN_REQ_BY = "req_by"; 
	public static final String JFN_REQ_BU = "req_bu"; 
	public static final String JFN_REQ_OU = "req_ou"; 

	public static final String JFN_OBJECTIVE_TYPE = "objective_type"; // buy, employ, hire
	public static final String JFN_OBJECTIVE = "objective";
	public static final String JFN_REASON = "reqson";
	
	public static final String JFN_CURRENCY = "currency"; // THB, USD, EUR, JPY, CNY
	public static final String JFN_CURRENCY_RATE = "currency_rate";
	
	public static final String JFN_BUDGET_CC = "budget_cc";
	public static final String JFN_STOCK_OU = "stock_ou";

	public static final String JFN_PROTOTYPE = "prototype";
	public static final String JFN_EVENT = "event";
	
	public static final String JFN_PCM_OU = "pcm_out";
	public static final String JFN_LOCATION = "location";
	
	public static final String JFN_ACROSS_BUDGET = "across_budget";
	public static final String JFN_REF_ID = "ref_id";
	
	public static final String JFN_METHOD = "method";
	public static final String JFN_METHOD_COND2_RULE = "method_cond2_rule";
	public static final String JFN_METHOD_COND2 = "method_cond2";
	public static final String JFN_METHOD_COND2_DTL = "method_cond2_dtl";	
	
	public static final String JFN_CREATED_TIME = "created_time";
	public static final String JFN_CREATED_BY = "created_by";
	public static final String JFN_UPDATED_TIME = "updated_time";
	public static final String JFN_UPDATED_BY = "updated_by";
	public static final String JFN_ACTION = "action";
	public static final String JFN_RUNNING_NO = "running_no";
	public static final String JFN_FISCAL_YEAR = "fiscal_year";
	
	public static final String JFN_REQUESTED_TIME_SHOW = "reqTimeShow";


	/*
	 * Status
	 */
	public static final String ST_DRAFT = "D";
	public static final String ST_WAITING = "W";
	public static final String ST_CLOSED_BY_ACT = "C1";
	public static final String ST_CLOSED_BY_PCM = "C2";
	public static final String ST_CANCEL_BY_REQ = "X1";
	public static final String ST_CANCEL_BY_PCM = "X2";
	
	public static final Map<String, Object> DISPLAY_TYPES = new LinkedHashMap<String, Object>();
	
    static {
    	DISPLAY_TYPES.put(ST_DRAFT, "Draft");
    	DISPLAY_TYPES.put(ST_WAITING, "Waiting Reviewer");
    	DISPLAY_TYPES.put(ST_CLOSED_BY_ACT, "Waiting Procurement");
    	DISPLAY_TYPES.put(ST_CLOSED_BY_PCM, "Procurement Accepted");
    	DISPLAY_TYPES.put(ST_CANCEL_BY_REQ, "Cancel by Requester");
    	DISPLAY_TYPES.put(ST_CANCEL_BY_PCM, "Cancel by Procurement");
    }
    
    /*
     * Report System Variable Name
     */
    public static final String RPTSV_IMAGE = "S_IMAGE";
    public static final String RPTSV_SIGN_REQ = "S_SIGN_REQ";
    
    /*
     * ACTION
     */
    public static final String ACTION_EDIT = "E";
    public static final String ACTION_DELETE = "D";
    public static final String ACTION_SHOW_DIAGRAM = "S";
    public static final String ACTION_GOTO_FOLDER = "F";
    public static final String ACTION_SHOW_HISTORY = "H";
    public static final String ACTION_SHOW_DETAIL = "V";
}
