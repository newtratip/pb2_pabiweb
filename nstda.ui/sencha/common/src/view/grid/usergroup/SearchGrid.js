Ext.define('PB.view.common.usergroup.SearchGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.usergroupSearchGrid',
	
	initComponent: function(config) {
		
		var me = this;
		
		var store = Ext.create('PB.store.common.UserGroupStore');

		Ext.applyIf(me, {
			
			   columns: [ 
			          { text: 'Name', dataIndex: 'name', flex: 1 },
			          { text: 'Type', dataIndex: 'type', width: 60, 
				    	renderer: function (v) {
				    		return (v=='G') ? 'Group' : 'User';
				    	}
			          },
			          { xtype: 'actioncolumn',
				        	dataIndex: 'action',
				        	itemId : 'actionGrid',
				        	text: 'Action',
				            width: 50,
				            items: [{
				                tooltip: 'Add',
				                action : 'addGroup',
				        	    getClass: function(v) {
				        	    	return getActionIcon(v, "A", 'add');
				                }, 
				                handler: function(grid, rowIndex, colIndex) {
				                	
				                	var rec = grid.getStore().getAt(rowIndex);
				                    me.fireEvent('addUserGroup', me, rec);

					            }
				            }]
				      }
			    ],
			    store : store,
			    tbar: Ext.create('Ext.Toolbar', {
		            items: [{ 
			            	xtype : 'form',
			            	width : '100%',
			            	border : 0,
			            	layout : {
		            			type : 'hbox',
		            			align : 'stretch'
		            		},
			            	items : [{
			            		xtype      : 'fieldcontainer',
					            defaultType: 'radiofield',
					            defaults: {
					                flex: 1
					            },
					            layout: 'hbox',
					            items: [
					                {
					                    boxLabel  : 'Both',
					                    name      : 'type',
					                    inputValue: 'B',
					                    margin: '0px 0px 0px 5px',
					                    checked : true
					                }, {
					                    boxLabel  : 'Group',
					                    name      : 'type',
					                    margin: '0px 0px 0px 5px',
					                    inputValue: 'G'
					                }, {
					                    boxLabel  : 'User',
					                    name      : 'type',
					                    margin: '0px 0px 0px 5px',
					                    inputValue: 'U'
					                }
					            ]
			            	},{
			            		xtype: 'textfield',
				            	margin: '0px 0px 0px 5px',
				                name: 'search',
				                flex : 1,
				            	enableKeyEvents: true,
					           	listeners: {
					           		
					 	 			specialkey: function(field, e){
					 	 				if(e.getKey() == e.ENTER){
					 	 					me.search();
					 	 				}
					 	 			}
							
					           	}
			            	},{
			            		xtype: 'button',
				                text: "Search",
				                iconCls:'icon_search',
				                listeners: {
				                    click: function(){
				                    	me.search();
				                    }
				                }
			            	}]
			            	
			        }]
			    })
		
		});		
				
        this.callParent(arguments);
	},
	
	search:function() {
		var me = this;
	
    	me.fireEvent('searchUserGroup',
    	   me
        );	
	}

});		