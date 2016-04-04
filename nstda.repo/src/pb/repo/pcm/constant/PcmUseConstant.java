package pb.repo.pcm.constant;

import java.util.LinkedHashMap;
import java.util.Map;


public class PcmUseConstant {

	public static final String TABLE_NAME = "pb2_pcm_use";
	
    /*
     * Table Field Name
     */
	public static final String TFN_ID = "ID";
	public static final String TFN_FIELD1 = "FIELD1";
	public static final String TFN_FIELD2 = "FIELD2";
	public static final String TFN_FIELD3 = "FIELD3";
	public static final String TFN_FIELD4 = "FIELD4";
	public static final String TFN_FIELD5 = "FIELD5";
	public static final String TFN_FIELD6 = "FIELD6";
	public static final String TFN_FIELD7 = "FIELD7";
	public static final String TFN_FIELD8 = "FIELD8";
	public static final String TFN_FIELD9 = "FIELD9";
	public static final String TFN_FIELD10 = "FIELD10";
	public static final String TFN_AMOUNT = "AMOUNT";
	public static final String TFN_REMARK = "REMARK";
	public static final String TFN_WAITING_LEVEL = "WAITING_LEVEL";
	public static final String TFN_STATUS = "STATUS";
	public static final String TFN_HEADER_ID = "HEADER_ID";
	public static final String TFN_FORMAT_ID = "FORMAT_ID";
	public static final String TFN_WORKFLOW_ID = "WORKFLOW_ID";
	public static final String TFN_WORKFLOW_INS_ID = "WORKFLOW_INS_ID";
	public static final String TFN_APPROVAL_MATRIX_ID = "APPROVAL_MATRIX_ID";
	public static final String TFN_DOC_REF = "DOC_REF";
	public static final String TFN_FOLDER_REF = "FOLDER_REF";
	public static final String TFN_CONTENT1 = "CONTENT1";
	public static final String TFN_CONTENT2 = "CONTENT2";
	public static final String TFN_ACTION_USER = "ACTION_USER";
	public static final String TFN_ACTION_GROUP = "ACTION_GROUP";
	public static final String TFN_RELATED_USER = "RELATED_USER";
	public static final String TFN_RELATED_GROUP = "RELATED_GROUP";
	public static final String TFN_REQUESTER_USER = "REQUESTER_USER";
	public static final String TFN_REQUESTER_GROUP = "REQUESTER_GROUP";
	public static final String TFN_REQUESTED_TIME = "REQUESTED_TIME";
	public static final String TFN_CREATED_TIME = "CREATED_TIME";
	public static final String TFN_CREATED_BY = "CREATED_BY";
	public static final String TFN_UPDATED_TIME = "UPDATED_TIME";
	public static final String TFN_UPDATED_BY = "UPDATED_BY";
	
    /*
     * JSON Field Name
     */
	public static final String JFN_ID = "id";
	public static final String JFN_FIELD1 = "field1";
	public static final String JFN_FIELD2 = "field2";
	public static final String JFN_FIELD3 = "field3";
	public static final String JFN_FIELD4 = "field4";
	public static final String JFN_FIELD5 = "field5";
	public static final String JFN_FIELD6 = "field6";
	public static final String JFN_FIELD7 = "field7";
	public static final String JFN_FIELD8 = "field8";
	public static final String JFN_FIELD9 = "field9";
	public static final String JFN_FIELD10 = "field10";
	public static final String JFN_AMOUNT = "amount";
	public static final String JFN_REMARK = "remark";
	public static final String JFN_WAITING_LEVEL = "wLvl";
	public static final String JFN_STATUS = "status";
	public static final String JFN_WF_STATUS = "wfStatus";
	public static final String JFN_HEADER_ID = "hId";
	public static final String JFN_FORMAT_ID = "format_id";
	public static final String JFN_WORKFLOW_ID = "workflow_id";
	public static final String JFN_WORKFLOW_INS_ID = "workflow_ins_id";
	public static final String JFN_APPROVAL_MATRIX_ID = "approval_matrix_id";
	public static final String JFN_DOC_REF = "doc_ref";
	public static final String JFN_FOLDER_REF = "folder_ref";
	public static final String JFN_CONTENT1 = "c1";
	public static final String JFN_CONTENT2 = "c2";
	public static final String JFN_ACTION_USER = "au";
	public static final String JFN_ACTION_GROUP = "ag";
	public static final String JFN_RELATED_USER = "ru";
	public static final String JFN_RELATED_GROUP = "rg";
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
	public static final String ST_WAITING = "W";
	public static final String ST_CLOSED = "C";
	public static final String ST_REJECTED = "R";
	public static final String ST_CANCEL = "X";
	
	public static final Map<String, Object> DISPLAY_TYPES = new LinkedHashMap<String, Object>();
	
    static {
    	DISPLAY_TYPES.put(ST_DRAFT, "Draft");
    	DISPLAY_TYPES.put(ST_WAITING, "Waiting");
    	DISPLAY_TYPES.put(ST_CLOSED, "Closed");
    	DISPLAY_TYPES.put(ST_REJECTED, "Rejected");
    	DISPLAY_TYPES.put(ST_CANCEL, "Cancel");
    }	
    
    
    public static final int MAX_DUMMY_FIELD = 10; // field1, field2 ... field10

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
