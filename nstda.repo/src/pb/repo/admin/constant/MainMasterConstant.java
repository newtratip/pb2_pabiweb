package pb.repo.admin.constant;


public class MainMasterConstant {
	
	public static final String TABLE_NAME = "pb2_main_master";
	
    /*
     * Table Field Name
     */
	public static final String TFN_ID = "ID";
	public static final String TFN_TYPE = "TYPE";
	public static final String TFN_CODE = "CODE";
	public static final String TFN_NAME = "NAME";
	public static final String TFN_FLAG1 = "FLAG1";
	public static final String TFN_FLAG2 = "FLAG2";
	public static final String TFN_FLAG3 = "FLAG3";
	public static final String TFN_FLAG4 = "FLAG4";
	public static final String TFN_FLAG5 = "FLAG5";
	public static final String TFN_CREATED_TIME = "CREATEDTIME";
	public static final String TFN_CREATED_BY = "CREATEDBY";
	public static final String TFN_UPDATED_TIME = "UPDATEDTIME";
	public static final String TFN_UPDATED_BY = "UPDATEDBY";
	public static final String TFN_ACTIVE = "IS_ACTIVE";

    /*
     * JSON Field Name
     */
	public static final String JFN_ID = "id";
	public static final String JFN_TYPE = "type";
	public static final String JFN_CODE = "code";
	public static final String JFN_NAME = "name";
	public static final String JFN_FLAG1 = "flag1";
	public static final String JFN_FLAG2 = "flag2";
	public static final String JFN_FLAG3 = "flag3";
	public static final String JFN_FLAG4 = "flag4";
	public static final String JFN_FLAG5 = "flag5";
	public static final String JFN_CREATED_TIME = "createdTime";
	public static final String JFN_CREATED_BY = "createdBy";
	public static final String JFN_UPDATED_TIME = "updatedTime";
	public static final String JFN_UPDATED_BY = "updatedBy";
	public static final String JFN_ACTIVE = "active";
	public static final String JFN_ACTION = "action";

	/*
	 * Type
	 */
	public static final String TYPE_MASTER_TYPE = "A";
	public static final String TYPE_SYSTEM_CONFIG = "SYSTEM";
	public static final String TYPE_PROJECT = "P";
	public static final String TYPE_STATUS = "S";
	public static final String TYPE_WORKFLOW = "W";
	public static final String TYPE_REPORT = "R";
	public static final String TYPE_PROTOTYPE = "PTT";
	
	public static final String TYPE_EXP_BRW_TYPE = "BRW_TYPE";
	public static final String TYPE_EXP_EXP_TYPE = "EXP_TYPE";

	/*
	 * System Configuration Code
	 */
    public static final String SCC_MAIN_SIGNATURE_PATH = "MAIN_SIGNATURE_PATH";  // Main Signature Path Node Ref
    public static final String SCC_MAIN_TEMP_PATH      = "MAIN_TEMP_PATH";  // Main TEMP Path Node Ref
    public static final String SCC_MAIN_PAGING_SIZE    = "MAIN_PAGING_SIZE";  // Main Grid Paging Size
    public static final String SCC_MAIN_ADMIN_AM       = "MAIN_ADMIN_AM";  // Show Admin Approval Matrix Tab : '0' or '1'
//    public static final String SCC_MAIN_ODOO_URL       = "MAIN_ODOO_URL";
//    public static final String SCC_MAIN_ODOO_DB        = "MAIN_ODOO_DB";
    
    public static final String SCC_PCM_ODOO_URL       = "PCM_ODOO_URL";
    public static final String SCC_PCM_ODOO_DB        = "PCM_ODOO_DB";

    public static final String SCC_EXP_ODOO_URL       = "EXP_ODOO_URL";
    public static final String SCC_EXP_ODOO_DB        = "EXP_ODOO_DB";
    
    public static final String SCC_MAIN_RESET_SEQUENCE        = "MAIN_RESET_SEQUENCE"; // DB Sequence names : comma delimited
    
    public static final String SCC_MAIN_INF_CHECK_BUDGET 	    = "MAIN_INF_CHECK_BUDGET";  // Check Budget
    public static final String SCC_MAIN_INF_PD_UPDATE_STATUS 	= "MAIN_INF_PD_UPDATE_STATUS";  // PD Update Status
    public static final String SCC_MAIN_INF_AV_CREATE_AV 		= "MAIN_INF_AV_CREATE_AV";  // Create AV
    
	// PCM
    public static final String SCC_PCM_REQ_SITE_ID 	    		= "PCM_REQ_SITE_ID";  // Site ID for Store Files
    public static final String SCC_PCM_ORD_SITE_ID 	    		= "PCM_ORD_SITE_ID";  // Site ID for Store Files
    
    public static final String SCC_PCM_REQ_PATH_FORMAT     		= "PCM_REQ_PATH_FORMAT";  // Path Format
    public static final String SCC_PCM_ORD_PATH_FORMAT     		= "PCM_ORD_PATH_FORMAT";  // Path Format
    
    public static final String SCC_PCM_REQ_ID_FORMAT     		= "PCM_REQ_ID_FORMAT";  // ID Format
    public static final String SCC_PCM_ORD_ID_FORMAT     		= "PCM_ORD_ID_FORMAT";  // ID Format
    
    public static final String SCC_PCM_REQ_IMAGE 	 	    	= "PCM_REQ_IMAGE";  // Image Node Ref for Pdf File
    public static final String SCC_PCM_ORD_IMAGE 	 	    	= "PCM_ORD_IMAGE";  // Image Node Ref for Pdf File
    
	public static final String SCC_PCM_REQ_RPT_TAB				= "PCM_REQ_RPT_TAB"; // 0=off , 1=on
	public static final String SCC_PCM_ORD_RPT_TAB				= "PCM_ORD_RPT_TAB"; // 0=off , 1=on
	
    public static final String SCC_PCM_REQ_CRITERIA 			= "PCM_REQ_CRITERIA";  // Pcm Req Criteria Combo Box
    public static final String SCC_PCM_ORD_CRITERIA 			= "PCM_ORD_CRITERIA";  // Pcm Ord Criteria Combo Box
    
    public static final String SCC_PCM_REQ_WF_DESC_FORMAT 		= "PCM_REQ_WF_DESC_FORMAT";  // Workflow Description Format
    public static final String SCC_PCM_ORD_WF_DESC_FORMAT 		= "PCM_ORD_WF_DESC_FORMAT";  // Workflow Description Format
    
    public static final String SCC_PCM_REQ_GRID_FIELD 			= "PCM_REQ_GRID_FIELD";  // Grid Field
    public static final String SCC_PCM_ORD_GRID_FIELD 			= "PCM_ORD_GRID_FIELD";  // Grid Field
    
	public static final String SCC_PCM_REQ_SEARCH_GRID_ORDER_BY	= "PCM_REQ_SEARCH_GRID_ORDER_BY";
	public static final String SCC_PCM_ORD_SEARCH_GRID_ORDER_BY	= "PCM_ORD_SEARCH_GRID_ORDER_BY";
	
	public static final String SCC_PCM_REQ_MSG_MISSING_NEXT_APP	= "PCM_REQ_MSG_MISSING_NEXT_APP";
	public static final String SCC_PCM_ORD_MSG_MISSING_NEXT_APP	= "PCM_ORD_MSG_MISSING_NEXT_APP";
	
	public static final String SCC_PCM_REQ_WF_FAIL_COND			= "PCM_REQ_WF_FAIL_COND";
	public static final String SCC_PCM_ORD_WF_FAIL_COND			= "PCM_ORD_WF_FAIL_COND";
	
	public static final String SCC_PCM_REQ_MAIL_NOTIFY 			= "PCM_REQ_MAIL_NOTIFY";
	public static final String SCC_PCM_ORd_MAIL_NOTIFY 			= "PCM_ORD_MAIL_NOTIFY";

	public static final String SCC_PCM_REQ_MAIL_TEMPLATE 			= "PCM_REQ_MAIL_TEMPLATE";
	public static final String SCC_PCM_REQ_MAIL_SUBJECT 			= "PCM_REQ_MAIL_SUBJECT";
	public static final String SCC_PCM_REQ_MAIL_FROM				= "PCM_REQ_MAIL_FROM";
	
	public static final String SCC_PCM_REQ_MAIL_RELATED				= "PCM_REQ_MAIL_RELATED";
	public static final String SCC_PCM_REQ_MAIL_RELATED_TEMPLATE   	= "PCM_REQ_MAIL_RELATED_TEMPLATE";
	public static final String SCC_PCM_REQ_MAIL_RELATED_SUBJECT 	= "PCM_REQ_MAIL_RELATED_SUBJECT";
	public static final String SCC_PCM_REQ_MAIL_RELATED_FROM		= "PCM_REQ_MAIL_RELATED_FROM";
	
	public static final String SCC_PCM_REQ_MAIL_REWARN				= "PCM_REQ_MAIL_REWARN";
	public static final String SCC_PCM_REQ_MAIL_REWARN_TEMPLATE   	= "PCM_REQ_MAIL_REWARN_TEMPLATE";
	public static final String SCC_PCM_REQ_MAIL_REWARN_SUBJECT 	= "PCM_REQ_MAIL_REWARN_SUBJECT";
	public static final String SCC_PCM_REQ_MAIL_REWARN_FROM		= "PCM_REQ_MAIL_REWARN_FROM";
	
	public static final String SCC_PCM_REQ_MAIL_COMPLETE			= "PCM_REQ_MAIL_COMPLETE";
	public static final String SCC_PCM_REQ_MAIL_COMPLETE_TEMPLATE   = "PCM_REQ_MAIL_COMPLETE_TEMPLATE";
	public static final String SCC_PCM_REQ_MAIL_COMPLETE_SUBJECT 	= "PCM_REQ_MAIL_COMPLETE_SUBJECT";
	public static final String SCC_PCM_REQ_MAIL_COMPLETE_FROM		= "PCM_REQ_MAIL_COMPLETE_FROM";
	
	public static final String SCC_PCM_REQ_REF_PR_PERCENT		= "PCM_REQ_REF_PR_PERCENT";
	public static final String SCC_PCM_REQ_CMT_CHECK_PARAMS		= "PCM_REQ_CMT_CHECK_PARAMS";
	
	public static final String SCC_PCM_ORD_SIGN_POS 			= "PCM_ORD_SIGN_POS";
	public static final String SCC_PCM_ORD_SIGN_FONT_SIZE 		= "PCM_ORD_SIGN_FONT_SIZE";
	public static final String SCC_PCM_ORD_SIGN_DATE_OFFSET 		= "PCM_ORD_SIGN_DATE_OFFSET";
	
	// EXP
    public static final String SCC_EXP_BRW_SITE_ID 	    		= "EXP_BRW_SITE_ID";  // Site ID for Store Files
    public static final String SCC_EXP_USE_SITE_ID 	    		= "EXP_USE_SITE_ID";  // Site ID for Store Files
    
    public static final String SCC_EXP_BRW_PATH_FORMAT     		= "EXP_BRW_PATH_FORMAT";  // Path Format
    public static final String SCC_EXP_USE_PATH_FORMAT     		= "EXP_USE_PATH_FORMAT";  // Path Format
    
    public static final String SCC_EXP_BRW_ID_FORMAT     		= "EXP_BRW_ID_FORMAT";  // ID Format
    public static final String SCC_EXP_USE_ID_FORMAT     		= "EXP_USE_ID_FORMAT";  // ID Format
    
	public static final String SCC_EXP_BRW_RPT_TAB				= "EXP_BRW_RPT_TAB"; // 0=off , 1=on
	public static final String SCC_EXP_USE_RPT_TAB				= "EXP_USE_RPT_TAB"; // 0=off , 1=on
	
    public static final String SCC_EXP_BRW_CRITERIA 			= "EXP_BRW_CRITERIA";  // Pcm Req Criteria Combo Box
    public static final String SCC_EXP_USE_CRITERIA 			= "EXP_USE_CRITERIA";  // Pcm Ord Criteria Combo Box
    
    public static final String SCC_EXP_BRW_WF_DESC_FORMAT 		= "EXP_BRW_WF_DESC_FORMAT";  // Workflow Description Format
    public static final String SCC_EXP_USE_WF_DESC_FORMAT 		= "EXP_USE_WF_DESC_FORMAT";  // Workflow Description Format
    
    public static final String SCC_EXP_BRW_GRID_FIELD 			= "EXP_BRW_GRID_FIELD";  // Grid Field
    public static final String SCC_EXP_USE_GRID_FIELD 			= "EXP_USE_GRID_FIELD";  // Grid Field
    
	public static final String SCC_EXP_BRW_SEARCH_GRID_ORDER_BY	= "EXP_BRW_SEARCH_GRID_ORDER_BY";
	public static final String SCC_EXP_USE_SEARCH_GRID_ORDER_BY	= "EXP_USE_SEARCH_GRID_ORDER_BY";

}
