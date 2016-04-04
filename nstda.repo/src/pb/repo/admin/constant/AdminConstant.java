package pb.repo.admin.constant;

public class AdminConstant {
	public static final StringBuffer CHANGE_LOG = new StringBuffer();
	
	public static final String VERSION = "1.0.6";
	
    static {
		CHANGE_LOG.append("\nV."+AdminConstant.VERSION+" --- (03/04/2016)\n");
		CHANGE_LOG.append("  - PR Form : Tab Committee\n");
		CHANGE_LOG.append("  - PR Form : Currency Combo Box\n");
		CHANGE_LOG.append("  - PR Form : Line Item : Product Uom Combo Box\n");
		CHANGE_LOG.append("  - New Table : pb2_ext_product_uom, pb2_ext_res_currency, pb2_ext_res_currency_rate\n");
		CHANGE_LOG.append("  - Delete Approval Matrix Code and Tables\n");
		CHANGE_LOG.append("  - Change Code Prefix 'NSTDA' to 'PB'\n");
		CHANGE_LOG.append("  - Make Ext JS Theme Font larger\n");
		CHANGE_LOG.append("\nV.1.0.5 --- (14/03/2016)\n");
		CHANGE_LOG.append("  - Add Procurement Order Page\n");
		CHANGE_LOG.append("\nV.1.0.4 --- (03/03/2016)\n");
		CHANGE_LOG.append("  - Add Approval Feature\n");
		CHANGE_LOG.append("\nV.1.0.3 --- (29/02/2016)\n");
		CHANGE_LOG.append("  - Update PR Form : Add Detail, Committee\n");
		CHANGE_LOG.append("\nV.1.0.2 --- (24/02/2016)\n");
		CHANGE_LOG.append("  - Update PR Form\n");
		CHANGE_LOG.append("  - Create XML RPC URL for Odoo\n");
		CHANGE_LOG.append("\nV.1.0.1 --- (23/02/2016)\n");
		CHANGE_LOG.append("  - Update PR UI\n");
		CHANGE_LOG.append("\nV.1.0.0 --- (17/02/2016)\n");
		CHANGE_LOG.append("  - Initial");
    }
    
    public static final String TABLE_PREFIX = "pb2_";
    public static final String TABLE_EXT_PREFIX = TABLE_PREFIX + "ext_";
}
