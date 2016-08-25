Ext.define('PBExp.view.workflow.AssigneeGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.expWfAssigneeGrid',
	anchor : '100%',
	initComponent: function(config) {
		
		var me = this;

		Ext.applyIf(me, {
			
			   columns: [ 
			      { text: 'Task', dataIndex: 'assignee', width:120},
			      { text: 'User', dataIndex: 'user', flex:1, renderer:me.userRenderer, tdCls:'wrap'}
			   ]
		});
				
        this.callParent(arguments);
	},
	
	userRenderer:function(v, m, r) {
		var pos = v.indexOf("(");
		if (pos >= 0) {
			v = v.substring(0, pos) + '<br/><font color="red">' + v.substring(pos)+'</font>';
		}
		
		return v; 
	}

});		