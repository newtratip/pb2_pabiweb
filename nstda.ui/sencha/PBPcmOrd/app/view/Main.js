/*
 * - MainGrid
 */
Ext.define('PBPcmOrd.view.Main', {
    extend: 'Ext.tab.Panel',
    requires:[
        'PBPcmOrd.view.MainGrid'
    ],
    
    alias : 'widget.pcmOrdMain',

	initComponent: function(config) {
		var me = this;
		
		var items = [];
		
		var store = Ext.create('PBPcmOrd.store.GridStore',{storeId:'pcmOrdGridStore',autoLoad:true});
		
		if (!ID) {
			items.push({
				xtype:'pcmOrdMainGrid',
				title:'Search',
				store:store
			});
			/*
			items.push({
				xtype:'pcmOrdReportForm',
				title:'Report'
			});
			*/
		}
		
		Ext.applyIf(me, {
			
			border : 0,
	
			items:items
				
		});		
		
	    this.callParent(arguments);
	}

});