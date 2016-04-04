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
	public static final String TYPE_MEMO_TYPE = "M";
	public static final String TYPE_DEPARTMENT = "D";
	public static final String TYPE_PROJECT = "P";
	public static final String TYPE_STATUS = "S";
	public static final String TYPE_WORKFLOW = "W";
	public static final String TYPE_REPORT = "R";

	/*
	 * System Configuration Code
	 */
    public static final String SCC_MAIN_SIGNATURE_PATH = "MAIN_SIGNATURE_PATH";  // Main Signature Path Node Ref
    public static final String SCC_MAIN_TEMP_PATH      = "MAIN_TEMP_PATH";  // Main TEMP Path Node Ref
    public static final String SCC_MAIN_PAGING_SIZE    = "MAIN_PAGING_SIZE";  // Main Grid Paging Size
    public static final String SCC_MAIN_ADMIN_AM       = "MAIN_ADMIN_AM";  // Show Admin Approval Matrix Tab : '0' or '1'
    
    // PR
    public static final String SCC_PR_SITE_ID 	    	= "PR_SITE_ID";  // PR Site ID for Store Files
    public static final String SCC_PR_PATH_FORMAT     	= "PR_PATH_FORMAT";  // PR Path Format
    public static final String SCC_PR_ID_FORMAT     	= "PR_ID_FORMAT";  // PR ID Format
    
    public static final String SCC_PR_CHECKED_IMAGE   		= "PR_CHECKED_IMAGE";  // PR Checked Image Node Ref for Pdf File
    public static final String SCC_PR_UNCHECKED_IMAGE 		= "PR_UNCHECKED_IMAGE";  // PR Unchecked Image Node Ref for Pdf File

    public static final String SCC_PR_CHECKED_RADIO_IMAGE   = "PR_CHECKED_RADIO_IMAGE";  // PR Checked Radio Image Node Ref for Pdf File
    public static final String SCC_PR_UNCHECKED_RADIO_IMAGE = "PR_UNCHECKED_RADIO_IMAGE";  // PR Unchecked Radio Image Node Ref for Pdf File
    
	public static final String SCC_PR_MAIL_NOTIFY 			= "PR_MAIL_NOTIFY";
	public static final String SCC_PR_MAIL_REWARN 			= "PR_MAIL_REWARN";
	
	public static final String SCC_PR_MAIL_TEMPLATE 		= "PR_MAIL_TEMPLATE";
	public static final String SCC_PR_MAIL_SUBJECT 			= "PR_MAIL_SUBJECT";
	public static final String SCC_PR_MAIL_FROM				= "PR_MAIL_FROM";
	
	public static final String SCC_PR_WF_FAIL_COND			= "PR_WF_FAIL_COND";
    
    // FR
    public static final String SCC_FR_SITE_ID 	    	= "FR_SITE_ID";  // FR Site ID for Store Files
    public static final String SCC_FR_PATH_FORMAT     	= "FR_PATH_FORMAT";  // FR Path Format
    public static final String SCC_FR_ID_FORMAT     	= "FR_ID_FORMAT";  // FR ID Format
    
    public static final String SCC_FR_CHECKED_IMAGE   		= "FR_CHECKED_IMAGE";  // FR Checked Image Node Ref for Pdf File
    public static final String SCC_FR_UNCHECKED_IMAGE 		= "FR_UNCHECKED_IMAGE";  // FR Unchecked Image Node Ref for Pdf File

    public static final String SCC_FR_CHECKED_RADIO_IMAGE   = "FR_CHECKED_RADIO_IMAGE";  // FR Checked Radio Image Node Ref for Pdf File
    public static final String SCC_FR_UNCHECKED_RADIO_IMAGE = "FR_UNCHECKED_RADIO_IMAGE";  // FR Unchecked Radio Image Node Ref for Pdf File
    
	public static final String SCC_FR_MAIL_NOTIFY 			= "FR_MAIL_NOTIFY";
	public static final String SCC_FR_MAIL_REWARN 			= "FR_MAIL_REWARN";
    
	public static final String SCC_FR_MAIL_TEMPLATE 		= "FR_MAIL_TEMPLATE";
	public static final String SCC_FR_MAIL_SUBJECT 			= "FR_MAIL_SUBJECT";
	public static final String SCC_FR_MAIL_FROM				= "FR_MAIL_FROM";
	
	public static final String SCC_FR_WF_FAIL_COND			= "FR_WF_FAIL_COND";
	
    // MEMO
    public static final String SCC_MEMO_ADMIN_HEADER    		= "MEMO_ADMIN_HEADER";  // Show Admin Header Tab : '0' or '1'
    
    public static final String SCC_MEMO_CHECKED_IMAGE   		= "MEMO_CHECKED_IMAGE";  // Memo Checked Image Node Ref for Pdf File
    public static final String SCC_MEMO_UNCHECKED_IMAGE 		= "MEMO_UNCHECKED_IMAGE";  // Memo Unchecked Image Node Ref for Pdf File

    public static final String SCC_MEMO_CHECKED_RADIO_IMAGE   		= "MEMO_CHECKED_RADIO_IMAGE";  // Memo Checked Radio Image Node Ref for Pdf File
    public static final String SCC_MEMO_UNCHECKED_RADIO_IMAGE 		= "MEMO_UNCHECKED_RADIO_IMAGE";  // Memo Unchecked Radio Image Node Ref for Pdf File
    
    public static final String SCC_MEMO_GRID_FIELD 				= "MEMO_GRID_FIELD";  // Memo Grid Field
    
    public static final String SCC_MEMO_WF_DESC_FORMAT 			= "MEMO_WF_DESC_FORMAT";  // Memo Workflow Description Format
    
	public static final String SCC_MEMO_SIGNATURE_DATE_POSITION = "MEMO_SIGN_DATE_POS"; // Signature
	public static final String SCC_MEMO_SIGNATURE_FONT_SIZE 	= "MEMO_SIGN_FONT_SIZE";
	public static final String SCC_MEMO_SIGNATURE_SPACE 		= "MEMO_SIGN_SPACE";
	public static final String SCC_MEMO_SIGNATURE_PIXEL 		= "MEMO_SIGN_PIXEL";
	
	public static final String SCC_MEMO_PAGE_MARGIN_T 			= "MEMO_PAGE_MARGIN_T"; // Top
	public static final String SCC_MEMO_PAGE_MARGIN_B 			= "MEMO_PAGE_MARGIN_B"; // Bottom
	public static final String SCC_MEMO_PAGE_MARGIN_L 			= "MEMO_PAGE_MARGIN_L"; // Left
	public static final String SCC_MEMO_PAGE_MARGIN_R 			= "MEMO_PAGE_MARGIN_R"; // Right
	
	public static final String SCC_MEMO_MAIL_NOTIFY 			= "MEMO_MAIL_NOTIFY";
	public static final String SCC_MEMO_MAIL_REWARN 			= "MEMO_MAIL_REWARN";
	public static final String SCC_MEMO_MOBILE_NOTIFY 			= "MEMO_MOBILE_NOTIFY";
	public static final String SCC_MEMO_MOBILE_REWARN 			= "MEMO_MOBILE_REWARN";

	public static final String SCC_MEMO_MAIL_TEMPLATE 			= "MEMO_MAIL_TEMPLATE";
	public static final String SCC_MEMO_MAIL_SUBJECT 			= "MEMO_MAIL_SUBJECT";
	public static final String SCC_MEMO_MAIL_FROM				= "MEMO_MAIL_FROM";
	
	public static final String SCC_MEMO_MAIL_REWARN_TEMPLATE 	= "MEMO_MAIL_REWARN_TEMPLATE";
	public static final String SCC_MEMO_MAIL_REWARN_SUBJECT 	= "MEMO_MAIL_REWARN_SUBJECT";
	public static final String SCC_MEMO_MAIL_REWARN_FROM		= "MEMO_MAIL_REWARN_FROM";
	
	public static final String SCC_MEMO_MAIL_RELATED			= "MEMO_MAIL_RELATED";
	public static final String SCC_MEMO_MAIL_RELATED_TEMPLATE 	= "MEMO_MAIL_RELATED_TEMPLATE";
	public static final String SCC_MEMO_MAIL_RELATED_SUBJECT 	= "MEMO_MAIL_RELATED_SUBJECT";
	public static final String SCC_MEMO_MAIL_RELATED_FROM		= "MEMO_MAIL_RELATED_FROM";
	
	public static final String SCC_MEMO_MAIL_COMPLETE			= "MEMO_MAIL_COMPLETE";
	public static final String SCC_MEMO_MAIL_COMPLETE_TEMPLATE  = "MEMO_MAIL_COMPLETE_TEMPLATE";
	public static final String SCC_MEMO_MAIL_COMPLETE_SUBJECT 	= "MEMO_MAIL_COMPLETE_SUBJECT";
	public static final String SCC_MEMO_MAIL_COMPLETE_FROM		= "MEMO_MAIL_COMPLETE_FROM";
	
	
	public static final String SCC_MEMO_DOC_SIGN				= "MEMO_DOC_SIGN"; // 0=off, 1=on
	public static final String SCC_MEMO_DOC_SIGN_METHOD			= "MEMO_DOC_SIGN_METHOD"; // 1=Sign every step, 2=Sign when finish
	public static final String SCC_MEMO_DOC_SIGN_PATTERN		= "MEMO_DOC_SIGN_PATTERN"; // flag1=Doc Name RegExp, flag2=Position

	public static final String SCC_MEMO_GEN_COMMENT				= "MEMO_GEN_COMMENT"; // 0=off, 1=on
	
	// PCM
    public static final String SCC_PCM_REQ_SITE_ID 	    		= "PCM_REQ_SITE_ID";  // Site ID for Store Files
    public static final String SCC_PCM_ORD_SITE_ID 	    		= "PCM_ORD_SITE_ID";  // Site ID for Store Files
    public static final String SCC_PCM_USE_SITE_ID 	    		= "PCM_USE_SITE_ID";  // Site ID for Store Files
    
    public static final String SCC_PCM_REQ_PATH_FORMAT     		= "PCM_REQ_PATH_FORMAT";  // Path Format
    public static final String SCC_PCM_ORD_PATH_FORMAT     		= "PCM_ORD_PATH_FORMAT";  // Path Format
    public static final String SCC_PCM_USE_PATH_FORMAT     		= "PCM_USE_PATH_FORMAT";  // Path Format
    
    public static final String SCC_PCM_REQ_ID_FORMAT     		= "PCM_REQ_ID_FORMAT";  // ID Format
    public static final String SCC_PCM_ORD_ID_FORMAT     		= "PCM_ORD_ID_FORMAT";  // ID Format
    public static final String SCC_PCM_USE_ID_FORMAT     		= "PCM_USE_ID_FORMAT";  // ID Format
    
    public static final String SCC_PCM_REQ_IMAGE 	 	    	= "PCM_REQ_IMAGE";  // Image Node Ref for Pdf File
    public static final String SCC_PCM_ORD_IMAGE 	 	    	= "PCM_ORD_IMAGE";  // Image Node Ref for Pdf File
    public static final String SCC_PCM_USE_IMAGE 	 	    	= "PCM_USE_IMAGE";  // Image Node Ref for Pdf File
    
	public static final String SCC_PCM_REQ_RPT_TAB				= "PCM_REQ_RPT_TAB"; // 0=off , 1=on
	public static final String SCC_PCM_ORD_RPT_TAB				= "PCM_ORD_RPT_TAB"; // 0=off , 1=on
	public static final String SCC_PCM_USE_RPT_TAB				= "PCM_USE_RPT_TAB"; // 0=off , 1=on
	
	public static final String SCC_PCM_REQ_DELETE_BUTTON		= "PCM_REQ_DELETE_BUTTON"; // 0=off , 1=on
	public static final String SCC_PCM_ORD_DELETE_BUTTON		= "PCM_ORD_DELETE_BUTTON"; // 0=off , 1=on
	public static final String SCC_PCM_USE_DELETE_BUTTON		= "PCM_USE_DELETE_BUTTON"; // 0=off , 1=on
	
    public static final String SCC_PCM_REQ_CRITERIA 			= "PCM_REQ_CRITERIA";  // Pcm Req Criteria Combo Box
    public static final String SCC_PCM_ORD_CRITERIA 			= "PCM_ORD_CRITERIA";  // Pcm Ord Criteria Combo Box
    public static final String SCC_PCM_USE_CRITERIA 			= "PCM_USE_CRITERIA";  // Pcm Use Criteria Combo Box
    
    public static final String SCC_PCM_REQ_WF_DESC_FORMAT 		= "PCM_REQ_WF_DESC_FORMAT";  // Workflow Description Format
    public static final String SCC_PCM_ORD_WF_DESC_FORMAT 		= "PCM_ORD_WF_DESC_FORMAT";  // Workflow Description Format
    public static final String SCC_PCM_USE_WF_DESC_FORMAT 		= "PCM_USE_WF_DESC_FORMAT";  // Workflow Description Format
    
    public static final String SCC_PCM_REQ_GRID_FIELD 			= "PCM_REQ_GRID_FIELD";  // Grid Field
    public static final String SCC_PCM_ORD_GRID_FIELD 			= "PCM_ORD_GRID_FIELD";  // Grid Field
    public static final String SCC_PCM_USE_GRID_FIELD 			= "PCM_USE_GRID_FIELD";  // Grid Field

	// EXP
	public static final String SCC_EXP_BRW_RPT_TAB				= "EXP_BRW_RPT_TAB"; // 0=off , 1=on
	public static final String SCC_EXP_USE_RPT_TAB				= "EXP_USE_RPT_TAB"; // 0=off , 1=on
	
	public static final String SCC_EXP_BRW_DELETE_BUTTON		= "EXP_BRW_DELETE_BUTTON"; // 0=off , 1=on
	public static final String SCC_EXP_USE_DELETE_BUTTON		= "EXP_USE_DELETE_BUTTON"; // 0=off , 1=on
	
    public static final String SCC_EXP_BRW_CRITERIA 			= "EXP_BRW_CRITERIA";  // Exp Req Criteria Combo Box
    public static final String SCC_EXP_USE_CRITERIA 			= "EXP_USE_CRITERIA";  // Exp Use Criteria Combo Box

	public static final String SCC_MEMO_SEARCH_GRID_ORDER_BY	= "MEMO_SEARCH_GRID_ORDER_BY";
	public static final String SCC_MEMO_REQ_SHOW_GROUP			= "MEMO_REQ_SHOW_GROUP"; // 0=individual , 1=group
	public static final String SCC_MEMO_MONITOR_USER			= "MEMO_MONITOR_USER";
	
	public static final String SCC_MEMO_HEADER_LEFT				= "MEMO_HEADER_LEFT";
	public static final String SCC_MEMO_HEADER_RIGHT			= "MEMO_HEADER_RIGHT";
	public static final String SCC_MEMO_HEADER_CENTER			= "MEMO_HEADER_CENTER";
	public static final String SCC_MEMO_HEADER_SPACING			= "MEMO_HEADER_SPACING";
	public static final String SCC_MEMO_HEADER_FONT_NAME		= "MEMO_HEADER_FONT_NAME";	
	public static final String SCC_MEMO_HEADER_FONT_SIZE		= "MEMO_HEADER_FONT_SIZE";
	
	public static final String SCC_MEMO_FOOTER_LEFT				= "MEMO_FOOTER_LEFT";
	public static final String SCC_MEMO_FOOTER_RIGHT			= "MEMO_FOOTER_RIGHT";
	public static final String SCC_MEMO_FOOTER_CENTER			= "MEMO_FOOTER_CENTER";
	public static final String SCC_MEMO_FOOTER_SPACING			= "MEMO_FOOTER_SPACING";
	public static final String SCC_MEMO_FOOTER_FONT_NAME		= "MEMO_FOOTER_FONT_NAME";	
	public static final String SCC_MEMO_FOOTER_FONT_SIZE		= "MEMO_FOOTER_FONT_SIZE";
	
	public static final String SCC_MEMO_MSG_MISSING_NEXT_APP	= "MEMO_MSG_MISSING_NEXT_APP";
	public static final String SCC_MEMO_WF_FAIL_COND			= "MEMO_WF_FAIL_COND";
	
}
