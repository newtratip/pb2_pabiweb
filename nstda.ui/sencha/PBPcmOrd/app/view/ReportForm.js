Ext.define('PBPcmOrd.view.ReportForm', {
    extend: 'Ext.form.Panel',
    alias:'widget.pcmOrdReportForm',
    requires: [
        'PBPcmOrd.view.ReportTab'
    ],
    
	layout:'fit',
    items:[{
		xtype : 'tabpanel',
		plain : true,
		tabBar : {
			items : [{
				xtype : 'tbfill'
			},{
				xtype: 'button',
			    text: "ค้นหา",
			    iconCls: "icon_search",
			    itemId : 'reportSearch',
			    action: "reportSearch",
			    margin:'2 2 0 0'
			},{
				xtype: 'button',
				text: "PDF",
				// iconCls: "icon_search",
				itemId : 'pdf',
				action: "pdf",
	            margin:'2 2 0 0'
			},{
				xtype: 'button',
				text: "EXCEL",
				// iconCls: "icon_search",
				itemId : 'excel',
				action: "excel",
	            margin:'2 2 0 0'
			}]
		},
		items : [{
			xtype : 'pcmOrdReportTab',
			title : 'รายงาน'
		}]
    }]
    
});
