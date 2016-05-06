package pb.repo.admin.constant;

public class AdminConstant {
	public static final StringBuffer CHANGE_LOG = new StringBuffer();
	
	public static final String VERSION = "1.0.10";
	
    static {
		CHANGE_LOG.append("\nV."+AdminConstant.VERSION+" --- (05/05/2016)\n");
		CHANGE_LOG.append("  - WF Form : Update Task Share UI Data from Edited Data\n");
		CHANGE_LOG.append("  - Interface : Create PR\n");
		CHANGE_LOG.append("  - Interface : Update PR Status\n");
		CHANGE_LOG.append("  - Interface : Create PD\n");
		CHANGE_LOG.append("  - Main Menu : Change Caption\n");
		CHANGE_LOG.append("\nV.1.0.9 --- (30/04/2016)\n");
		CHANGE_LOG.append("  - WF Form : Workflow History : Currenct Task shows 'Procurement' instead of '-'\n");
		CHANGE_LOG.append("  - WF Form : Update My Task Title from Edited Data\n");
		CHANGE_LOG.append("  - WF Form : Hide Equipment Radio on Line Item Dialog\n");
		CHANGE_LOG.append("  - WF Form : Hide Stock Location\n");
		CHANGE_LOG.append("  - WF Form : Event Clear Button\n");
		CHANGE_LOG.append("\nV.1.0.8 --- (28/04/2016)\n");
		CHANGE_LOG.append("  - WF Form : Search Event Dialog\n");
		CHANGE_LOG.append("  - WF Form : Search Section and Project Dialog\n");
		CHANGE_LOG.append("\nV.1.0.7 --- (25/04/2016)\n");
		CHANGE_LOG.append("  - WF Share UI : Edit Button\n");
		CHANGE_LOG.append("  - PR Workflow : Find Procurement Boss\n");
		CHANGE_LOG.append("  - PR Form : Remove Field : 'Stock','Pcm Ou'\n");
		CHANGE_LOG.append("  - PR Form : Tab User : Fill User Information\n");
		CHANGE_LOG.append("\nV.1.0.6 --- (21/04/2016)\n");
		CHANGE_LOG.append("  - PR Grid : Workflow History\n");
		CHANGE_LOG.append("  - PR Form : Add Committee\n");
		CHANGE_LOG.append("  - PR : Gen ID from DB sequence and Fiscal Year\n");
		CHANGE_LOG.append("  - PR Form : Add Line Item\n");
		CHANGE_LOG.append("  - PR Form : Add Field prototype_contract_no\n");
		CHANGE_LOG.append("  - PR Form : Download Middle Price Document\n");
		CHANGE_LOG.append("  - PR Search : Show Grid Field Value\n");
		CHANGE_LOG.append("  - PR Form : Prototype Combo Box\n");
		CHANGE_LOG.append("  - WF Share UI : Remove 'Reassign' Button\n");
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
