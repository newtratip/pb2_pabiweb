/*
 * - MainGrid
 * 		- workflow
 * 			- DtlDlg
 * 				- AssigneeGrid
 * 				- CurTaskGrid
 * 				- DtlGrid
 * - MainForm
 * 		- HdrTab
 * 		- HdrStockTab
 * 		- HdrHireTab
 * 		- HdrAssetTab
 * 		- DtlTab
 * 		- FileTab
 * 		- CmtTab
 */
Ext.define('PBPcm.view.Main', {
    extend: 'Ext.tab.Panel',
    requires:[
        'PBPcm.view.MainGrid',
        'PBPcm.view.MainForm'
    ],

    alias : 'widget.pcmReqMain',

	initComponent: function(config) {
		var me = this;
		
		var items = [];
		
		var store = Ext.create('PBPcm.store.GridStore',{storeId:'pcmReqGridStore',autoLoad:true});
		
		if (!ID) {
			items.push({
				xtype:'pcmReqMainGrid',
				title:'Search',
				store:store
			});
			
		//if (me.tasks.pcmReqRptTab) {
				items.push({
					xtype:'reportForm',
					title:'Report'
				});
		//	}
		}
		
		Ext.applyIf(me, {
			
			border : 0,
	
			items:items
				
		});		
		
	    this.callParent(arguments);
	}
    
});