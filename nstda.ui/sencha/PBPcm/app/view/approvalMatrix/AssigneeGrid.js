Ext.define('PBPcm.view.approvalMatrix.AssigneeGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.memoWfAssigneeGrid',
	anchor : '100%',
	initComponent: function(config) {
		
		var me = this;

		Ext.applyIf(me, {
			
			   columns: [ 
			      { text: 'Level', dataIndex: 'assignee', width:120},
			      { text: 'User', dataIndex: 'user', width:95},
			      { text: 'Group', dataIndex: 'group', flex:1}
			   ]
		});
				
        this.callParent(arguments);
	}

});		