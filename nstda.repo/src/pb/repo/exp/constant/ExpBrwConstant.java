package pb.repo.exp.constant;

import java.util.LinkedHashMap;
import java.util.Map;

import pb.repo.admin.constant.SubModuleConstant;


public class ExpBrwConstant extends SubModuleConstant {

	public static final String TABLE_NAME = "pb2_exp_brw";
	
    /*
     * Table Field Name
     */
	public static final String TFN_TOTAL = "TOTAL";
	public static final String TFN_STATUS = "STATUS";
	
	public static final String TFN_REQ_BY = "REQ_BY";
	public static final String TFN_REQ_SECTION_ID = "REQ_SECTION_ID"; 

	public static final String TFN_OBJECTIVE_TYPE = "OBJECTIVE_TYPE";
	public static final String TFN_OBJECTIVE_TYPE_NAME = "OBJECTIVE_TYPE_NAME";
	public static final String TFN_OBJECTIVE = "OBJECTIVE";
	public static final String TFN_REASON = "REASON";
	
	public static final String TFN_BUDGET_CC = "BUDGET_CC";
	public static final String TFN_BUDGET_CC_TYPE = "BUDGET_CC_TYPE";
	
	public static final String TFN_COST_CONTROL_TYPE_ID = "COST_CONTROL_TYPE_ID";
	public static final String TFN_COST_CONTROL_ID = "COST_CONTROL_ID";
	public static final String TFN_COST_CONTROL = "COST_CONTROL";
	public static final String TFN_COST_CONTROL_FROM = "COST_CONTROL_FROM";
	public static final String TFN_COST_CONTROL_TO = "COST_CONTROL_TO";
	
	public static final String TFN_ACTIVITY = "ACTIVITY";
	
	public static final String TFN_BANK_TYPE = "BANK_TYPE";
	public static final String TFN_BANK = "BANK";
	
    /*
     * JSON Field Name
     */
	public static final String JFN_ID = "id";
	
	public static final String JFN_TOTAL = "total";
	public static final String JFN_STATUS = "status";
	public static final String JFN_WF_STATUS = "wfStatus";
	public static final String JFN_OVER_DUE = "overDue";
	
	public static final String JFN_REQ_BY = "req_by"; 
	public static final String JFN_REQ_BY_NAME = "req_by_name";
	public static final String JFN_REQ_TEL_NO = "req_tel_no";
	public static final String JFN_REQ_BY_DEPT = "req_by_dept"; 
	
	public static final String JFN_REQ_BU = "req_bu"; 
	
	public static final String JFN_REQ_SECTION_ID = "req_ou"; 
	public static final String JFN_REQ_SECTION_NAME = "req_ou_name";

	public static final String JFN_OBJECTIVE_TYPE = "objective_type";
	public static final String JFN_OBJECTIVE_TYPE_NAME = "objective_type_name";
	public static final String JFN_OBJECTIVE = "objective";
	public static final String JFN_REASON = "reason";
	
	public static final String JFN_BUDGET_CC = "budget_cc";
	public static final String JFN_BUDGET_CC_NAME = "budget_cc_name";
	public static final String JFN_BUDGET_CC_TYPE = "budget_cc_type";
	public static final String JFN_BUDGET_CC_TYPE_NAME = "budget_cc_type_name";
	
	public static final String JFN_COST_CONTROL_TYPE_ID = "cost_control_type_id";
	public static final String JFN_COST_CONTROL_ID = "cost_control_id";
	public static final String JFN_COST_CONTROL = "cost_control";
	public static final String JFN_COST_CONTROL_FROM = "cost_control_from";
	public static final String JFN_COST_CONTROL_TO = "cost_control_to";
	
	public static final String JFN_ACTIVITY = "activity";
	
	public static final String JFN_BANK_TYPE = "bank_type";
	public static final String JFN_BANK = "bank";
	
	public static final String JFN_ACTION = "action";
	public static final String JFN_RUNNING_NO = "running_no";
	public static final String JFN_FISCAL_YEAR = "fiscal_year";
	
	public static final String JFN_CREATED_BY_SHOW = "created_by_show";
	public static final String JFN_CREATED_TIME_SHOW = "created_time_show";
	public static final String JFN_TEL_NO = "tel_no";

	
	/*
	 * Status
	 */
	public static final String ST_DRAFT = "D";
	public static final String ST_WAITING = "W1";
	public static final String ST_WAITING_REJECT = "W2";
	public static final String ST_CLOSED_BY_ACT = "C1";
	public static final String ST_CLOSED_BY_ACC = "C2";
	public static final String ST_CANCEL_BY_REQ = "X1";
	public static final String ST_CANCEL_BY_ACC = "X2";
	public static final String ST_CONSULT = "S";
	
	public static final Map<String, Object> DISPLAY_TYPES = new LinkedHashMap<String, Object>();
	
    static {
    	DISPLAY_TYPES.put(ST_DRAFT, "Draft");
    	DISPLAY_TYPES.put(ST_WAITING, "Waiting Reviewer");
    	DISPLAY_TYPES.put(ST_WAITING_REJECT, "Reviewer Reject");
    	DISPLAY_TYPES.put(ST_CLOSED_BY_ACT, "Waiting Accounting");
    	DISPLAY_TYPES.put(ST_CLOSED_BY_ACC, "Accounting Accepted");
    	DISPLAY_TYPES.put(ST_CANCEL_BY_REQ, "Cancel by Requester");
    	DISPLAY_TYPES.put(ST_CANCEL_BY_ACC, "Cancel by Accounting");
    	DISPLAY_TYPES.put(ST_CONSULT, "Consult");
    }
    
    /*
     * ACTION
     */
    public static final String ACTION_COPY = "C";
    public static final String ACTION_EDIT = "E";
    public static final String ACTION_DELETE = "D";
    public static final String ACTION_SHOW_DIAGRAM = "S";
    public static final String ACTION_GOTO_FOLDER = "F";
    public static final String ACTION_SHOW_HISTORY = "H";
    public static final String ACTION_SHOW_DETAIL = "V";

    /*
     * Workflow Name
     */
    public static final String WF_NAME = "NSTDAExpAV";
    
    /*
     * Next Actor
     */
    public static final String NA_BOSS = "หัวหน้าพัสดุ";
    public static final String NA_OFFICER = "เจ้าหน้าที่พัสดุ";
    
    /*
     * Budget CC Type
     */
    public static final String BCCT_UNIT = "U";
    public static final String BCCT_PROJECT = "P";
}
