Ext.define('PBAdmin.view.main.group.Grid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.adminGroupGrid',
	parentName : '',
	initComponent: function(config) {
	
		var me = this;
		
		var groupStore = Ext.create('PBAdmin.store.main.GroupStore',{storeId:'groupStore', autoLoad : false});		

		var columns = [
				  { text: 'ID',  dataIndex: 'code', width:150},
		          { text: 'Name', dataIndex: 'name', flex: 1 },
		          {
		        	xtype: 'actioncolumn',
		        	dataIndex: 'action',
		        	text: 'Action',
		            width: 50,
		            items: [{
		                tooltip: 'Add',
		                //action : 'addApproverRole',
		        	    getClass: function(v) {
		        	    	return getActionIcon(v, "A", 'add');
		                },
		                handler: function(grid, rowIndex, colIndex) {
		                	var rec = grid.getStore().getAt(rowIndex);
		                	me.fireEvent('addGroup', me, rec);
		                }
		            }]
		          }
				];
		
		Ext.applyIf(me, {
			columns : columns,
			store : groupStore
		
		});
		
        this.callParent(arguments);
	}
});		