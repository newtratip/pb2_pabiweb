Ext.define('PBPcm.view.workflow.CurTaskGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.memoWfCurTaskGrid',
	anchor : '100%',
	initComponent: function(config) {
		
		var me = this;

		Ext.applyIf(me, {
			
			   columns: [ 
			    { text: 'Type', dataIndex: 'type', tdCls: 'wrap',flex: 1},
			    { text: 'Assigned To', dataIndex: 'assignedTo',width: 120},
			    { text: 'Status', dataIndex: 'status',flex: 1}
			   ]
		});		
				
        this.callParent(arguments);
	}

});		