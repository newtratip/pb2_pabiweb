Ext.define('PBPcmOrd.view.workflow.AssigneeGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.pcmOrdWfAssigneeGrid',
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