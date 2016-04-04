Ext.define('PB.view.common.upload.Grid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.uploadGrid',
	
	initComponent: function(config) {
		var me = this;
		
		Ext.applyIf(me, {
			
			   columns: [ 
			     {
			    	xtype: 'actioncolumn',
		        	dataIndex: 'action',
		        	itemId : 'actionGrid',
		        	text: 'Action',
		            width: 60,
		            items: [{
		                tooltip: 'Delete',
		        	    getClass: function(v) {
		        	    	return getActionIcon(v, "D", 'delete');
		                }, 
		                handler: function(grid, rowIndex, colIndex) {
		                    me.fireEvent('deleteFile',me, rowIndex, colIndex);
			            }
		            }]
			     },
			     {xtype: 'rownumberer', text: 'NO.', width:60},
			     {text: 'File Name', dataIndex: 'name', flex: 1 }
			    ],
			    tbar:[{
		    	    xtype:'label',
		    	    html:"&nbsp;&nbsp;<b>File</b>"
		        },{
            		xtype: 'filefield',
	            	padding: '0px 0px 0px 5px',
	                name: 'file',
	                msgTarget: 'side',
	                width : 350,
	                buttonConfig: {
	                    text: 'เลือก',
                        iconCls: "icon_search"               
	                }
            	},{
            		xtype: 'button',
	                text: "Upload",
	                iconCls: "icon_upload",
	                listeners: {
	                    click: function(){
	                    	var file = me.down('[name=file]').getValue();			                    	
	                    	if(file != null && file != undefined && file != ''){
	                    		me.fireEvent('uploadFile',me.up('form').getForm(),me);	
	                    	}
	                    	
	                    }
	                }
            	},{
             	    xtype:'hiddenfield',
            	    name:'uuid'
               	}]
		
		});		
				
        this.callParent(arguments);
	}

});		