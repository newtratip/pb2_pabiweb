Ext.define('PBPcm.view.workflow.DtlDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.memoWfDtlDlg',
	initComponent: function(config) {
		
		var me = this;
		var assigneeStore = Ext.create('PBPcm.store.workflow.AssigneeGridStore');
		var curTaskStore = Ext.create('PBPcm.store.workflow.CurTaskGridStore');
		var store = Ext.create('PBPcm.store.workflow.DtlGridStore');
		
		Ext.applyIf(me, {
	            modal: true,
	            width: 950,
	            height: 570,
	            layout: 'border',
	            resizable: true,
	            title:'Workflow Detail',
	            items : [{
	            	title : 'History',
        			xtype : 'memoWfDtlGrid',
        			actionType : 'related',
	            	store : store,
	            	region : 'east',
	            	width : 580,
	            	border:1
	            },{
	            	region:'center',
	            	layout:'border',
	            	split:true,
	                items:[{
		            	title : 'Path',
		    			xtype : 'memoWfAssigneeGrid',
		    			actionType : 'related',
		            	store : assigneeStore,
		            	region : 'north',
		            	height : 300
		            },{
		            	title : 'Current Tasks',
	        			xtype : 'memoWfCurTaskGrid',
	        			actionType : 'related',
		            	store : curTaskStore,
		            	region : 'center'
		            }]
	            }]
		});
		
        this.callParent(arguments);
	}
});		