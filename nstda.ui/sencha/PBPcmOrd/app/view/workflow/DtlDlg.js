Ext.define('PBPcmOrd.view.workflow.DtlDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.pcmOrdWfDtlDlg',
	initComponent: function(config) {
		
		var me = this;
		var assigneeStore = Ext.create('PBPcmOrd.store.workflow.AssigneeGridStore');
		var curTaskStore = Ext.create('PBPcmOrd.store.workflow.CurTaskGridStore');
		var store = Ext.create('PBPcmOrd.store.workflow.DtlGridStore');
		
		Ext.applyIf(me, {
	            modal: true,
	            width: 950,
	            height: 570,
	            layout: 'border',
	            resizable: true,
	            title:'Workflow Detail',
	            items : [{
	            	title : 'History',
        			xtype : 'pcmOrdWfDtlGrid',
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
		    			xtype : 'pcmOrdWfAssigneeGrid',
		    			actionType : 'related',
		            	store : assigneeStore,
		            	region : 'north',
		            	height : 300
		            },{
		            	title : 'Current Tasks',
	        			xtype : 'pcmOrdWfCurTaskGrid',
	        			actionType : 'related',
		            	store : curTaskStore,
		            	region : 'center'
		            }]
	            }]
		});
		
        this.callParent(arguments);
	}
});		