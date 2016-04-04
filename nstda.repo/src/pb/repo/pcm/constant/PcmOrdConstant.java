package pb.repo.pcm.constant;

import java.util.LinkedHashMap;
import java.util.Map;


public class PcmOrdConstant {

	public static final String TABLE_NAME = "pb2_pcm_ord";
	
    /*
     * Table Field Name
     */
	public static final String TFN_ID = "ID";
	public static final String TFN_REQ_TYPE = "REQ_TYPE"; // PR05, PR06, PR07
	public static final String TFN_REF_ID = "REF_ID";
	
	public static final String TFN_PUR_TYPE = "PUR_TYPE"; // buy, employ, hire
	
	public static final String TFN_DOMESTIC = "DOMESTIC"; // 0=FALSE, 1=TRUE
	public static final String TFN_CURRENCY = "CURRENCY"; // THB, USD, EUR, JPY, CNY
	public static final String TFN_CURRENCY_RATE = "CURRENCY_RATE";
	public static final String TFN_OBJECTIVE = "OBJECTIVE";
	public static final String TFN_REASON = "REASON";
	
	public static final String TFN_REQ_OU = "REQ_OU"; // OU = Operating Unit
	public static final String TFN_STOCK_OU = "STOCK_OU";
	public static final String TFN_NEED_DATE = "NEED_DATE";
	
	public static final String TFN_TOTAL = "TOTAL";
	public static final String TFN_REMARK = "REMARK";
	public static final String TFN_WAITING_LEVEL = "WAITING_LEVEL";
	public static final String TFN_STATUS = "STATUS";
	public static final String TFN_WORKFLOW_ID = "WORKFLOW_ID";
	public static final String TFN_WORKFLOW_INS_ID = "WORKFLOW_INS_ID";
	public static final String TFN_APPROVAL_MATRIX_ID = "APPROVAL_MATRIX_ID";
	public static final String TFN_DOC_REF = "DOC_REF";
	public static final String TFN_FOLDER_REF = "FOLDER_REF";
	public static final String TFN_REQUESTED_TIME = "REQUESTED_TIME";
	public static final String TFN_CREATED_TIME = "CREATED_TIME";
	public static final String TFN_CREATED_BY = "CREATED_BY";
	public static final String TFN_UPDATED_TIME = "UPDATED_TIME";
	public static final String TFN_UPDATED_BY = "UPDATED_BY";
	
    /*
     * JSON Field Name
     */
	public static final String JFN_ID = "id";
	public static final String JFN_REQ_TYPE = "req_type";
	public static final String JFN_REF_ID = "ref_id";
	
	public static final String JFN_PUR_TYPE = "pur_type";
	
	public static final String JFN_DOMESTIC = "domestic";
	public static final String JFN_CURRENCY = "currency";
	public static final String JFN_CURRENCY_RATE = "currency_rate";
	public static final String JFN_OBJECTIVE = "objective";
	public static final String JFN_REASON = "reason";
	
	public static final String JFN_REQ_OU = "req_ou";
	public static final String JFN_STOCK_OU = "stock_ou";
	public static final String JFN_NEED_DATE = "need_date";
	
	public static final String JFN_TOTAL = "total";
	public static final String JFN_REMARK = "remark";
	public static final String JFN_WAITING_LEVEL = "wLvl";
	public static final String JFN_STATUS = "status";
	public static final String JFN_WF_STATUS = "wfStatus";
	public static final String JFN_WORKFLOW_ID = "workflow_id";
	public static final String JFN_WORKFLOW_INS_ID = "workflow_ins_id";
	public static final String JFN_APPROVAL_MATRIX_ID = "approval_matrix_id";
	public static final String JFN_DOC_REF = "doc_ref";
	public static final String JFN_FOLDER_REF = "folder_ref";
	public static final String JFN_OVER_DUE = "overDue";
	public static final String JFN_REQUESTED_TIME = "requested_time";
	public static final String JFN_CREATED_TIME = "created_time";
	public static final String JFN_CREATED_BY = "created_by";
	public static final String JFN_UPDATED_TIME = "updated_time";
	public static final String JFN_UPDATED_BY = "updated_by";
	public static final String JFN_ACTION = "action";
	public static final String JFN_RUNNING_NO = "running_no";
	
	public static final String JFN_REQUESTED_TIME_SHOW = "reqTimeShow";


	/*
	 * Status
	 */
	public static final String ST_DRAFT = "D";
	public static final String ST_WAITING = "W"; // Waiting Reviewer
	public static final String ST_WAITING_PARCEL = "WP";
	public static final String ST_CLOSED = "C";
	public static final String ST_CANCEL = "X";
	
	public static final Map<String, Object> DISPLAY_TYPES = new LinkedHashMap<String, Object>();
	
    static {
    	DISPLAY_TYPES.put(ST_DRAFT, "Draft");
    	DISPLAY_TYPES.put(ST_WAITING, "Waiting Reviewer");
    	DISPLAY_TYPES.put(ST_WAITING_PARCEL, "Waiting Parcel");
    	DISPLAY_TYPES.put(ST_CLOSED, "Closed");
    	DISPLAY_TYPES.put(ST_CANCEL, "Cancel");
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
