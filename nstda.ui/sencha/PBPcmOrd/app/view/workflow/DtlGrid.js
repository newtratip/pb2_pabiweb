Ext.define('PBPcmOrd.view.workflow.DtlGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.pcmOrdWfDtlGrid',
	anchor : '100%',
	initComponent: function(config) {
		
		var me = this;

		Ext.applyIf(me, {
			
			   columns: [ 
			    { text: 'Date-Time',width: 120, dataIndex: 'time'},
			    { text: 'By',width: 70, dataIndex: 'by'},
			    { text: 'Status',width: 70, dataIndex: 'status'},
			    { text: 'Task',width: 100, dataIndex: 'task'},
			    { text: 'Comment', dataIndex: 'comment', tdCls: 'wrap',flex: 1}
			   ]
		});		
				
        this.callParent(arguments);
	}

});		