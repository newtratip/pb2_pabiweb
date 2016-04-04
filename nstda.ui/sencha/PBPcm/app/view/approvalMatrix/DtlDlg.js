Ext.define('PBPcm.view.approvalMatrix.DtlDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.memoAmDtlDlg',
	initComponent: function(config) {
		
		var me = this;
		var assigneeStore = Ext.create('PBPcm.store.approvalMatrix.AssigneeGridStore');
		
		Ext.applyIf(me, {
	            modal: true,
	            width: 500,
	            height: 350,
	            layout: 'fit',
	            resizable: true,
	            title:'Approval Matrix',
	            items : [{
	            	title : 'Path',
	    			xtype : 'memoWfAssigneeGrid',
	    			actionType : 'related',
	            	store : assigneeStore
	            }]
		});
		
        this.callParent(arguments);
	}
});		