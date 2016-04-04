package pb.repo.admin.constant;


public class MainCurrencyRateConstant {
	
	public static final String TABLE_NAME = AdminConstant.TABLE_EXT_PREFIX + "res_currency_rate";
	
    /*
     * Table Field Name
     */
	public static final String TFN_ID = "ID";
	public static final String TFN_MASTER_ID = "CURRENCY_ID";
	public static final String TFN_RATE = "RATE";

    /*
     * JSON Field Name
     */
	public static final String JFN_ID = "id";
	public static final String JFN_MASTER_ID = "currency_id";
	public static final String JFN_RATE = "rate";
	
}
