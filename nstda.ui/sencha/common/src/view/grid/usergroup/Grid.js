Ext.define('PB.view.common.usergroup.Grid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.usergroupGrid',
	
	enableColumnHide : true,
	
	initComponent: function(config) {
		
		var me = this;
	
		var mandaIndic = "";
		if (me.mandatory) {
			mandaIndic = ' <font color="red">*</font>';
		}

		Ext.applyIf(me, {
			
			   columns: [{ 
			        xtype: 'actioncolumn',
		        	dataIndex: 'action',
		        	itemId : 'actionGroup',
		        	text: 'Action',
		            width: 60,
		            items: [{
		                tooltip: 'Delete',
		        	    getClass: function(v) {
		        	    	return getActionIcon('D', "D", 'delete');
		                },
		                handler: function (grid, rowIndex, colIndex) {
		                	me.fireEvent("delUserGroup", grid, rowIndex, colIndex);
		                }
		            }] 
		        },{
		        	text: 'ชื่อ', dataIndex: 'name', flex: 1 
		        },{ 
		        	text: 'ประเภท', dataIndex: 'type', width: 60, 
			    	renderer: function (v) {
			    		return (v=='G') ? 'Group' : 'User';
			    	}
			    }],
			    
			    tbar : [{
					xtype : 'label',
					html : '&nbsp;&nbsp;<b>'+me.tbTitle+' '+mandaIndic+'</b>'
    			},{
                	xtype: 'button',
                    iconCls: "icon_search",                
					text : 'เลือก',
					action : 'openSearchUserGroupDlg'
    			}]
		
		});		
				
        this.callParent(arguments);
	}

});		