Ext.define('PBPcm.view.ReportForm', {
    extend: 'Ext.form.Panel',
    alias:'widget.reportForm',
    requires: [
        'PBPcm.view.ReportTab'
    ],
    
	layout:'fit',
	
	tbar : [{
		xtype : 'tbfill'
	},{
		xtype: 'button',
	    text: "ค้นหา",
	    iconCls: "icon_search",
	    itemId : 'reportSearch',
	    action: "reportSearch",
	    margin:'0 2 0 0'
	},{
		xtype: 'button',
		text: "PDF",
		// iconCls: "icon_search",
		itemId : 'pdf',
		action: "pdf",
        margin:'0 2 0 0'
	},{
		xtype: 'button',
		text: "EXCEL",
		// iconCls: "icon_search",
		itemId : 'excel',
		action: "excel",
        margin:'0 2 0 0'
	}],
    items:[{
		xtype : 'pcmReportTab'
    }]
    
});
