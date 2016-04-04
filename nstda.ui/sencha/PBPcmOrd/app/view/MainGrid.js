Ext.define('PBPcmOrd.view.MainGrid', {
    extend: 'Ext.grid.Panel',
    alias:'widget.pcmOrdMainGrid',
    
	initComponent: function(config) {
		var me = this;
	
		var columns = [
		      {
	        	xtype: 'actioncolumn',
	        	dataIndex: 'action',
	        	text: '', 
	            width: 130,
	            items: [{
	                tooltip: 'Detail', 
	                action : 'viewDetail',
	        	    getClass: function(v) {
	        	    	return getActionIcon(v, "V", 'view');
	                }
	            }, {
	                tooltip: 'Goto Folder', 
	                action : 'gotoFolder',
	        	    getClass: function(v) {
	        	    	return getActionIcon(v, "F", 'detail');
	                }
	            }]
	          },
			  { text: 'PD NO.', dataIndex:'id', width:150}
		];
		
		Ext.Ajax.request({
		      url:ALF_CONTEXT+"/pcm/ord/gridfield/list",
		      method: "GET",
		      success: function(response){
		    	  
		    	var data = Ext.decode(response.responseText).data;
		    	
				for(var i=0; i<data.length; i++) {
					var d = data[i];
					
					var col = {
						text: d.label,  
						dataIndex: d.field 
					}
					
					if (d.width) {
						col.width = parseInt(d.width);
					} else {
						col.flex = 1;
					}
					
					columns.push(col); 
				}
    				
		      },
		      failure: function(response, opts){
		          alert("failed");
		      },
		      headers: getAlfHeader(),
		      async:false
		}); 
		
		columns.push({ text: 'Requested Time',  dataIndex: 'reqTimeShow', width:130});
		columns.push({ text: 'Status',  dataIndex: 'wfStatus', width:300,
		  	  renderer: function (v, m, r) {
			
					if (r.get("overDue")) {
						m.style = "background-color:#FFFF66";
					}
					
					var status = r.get("status");
        			if (status != "D") {
			            var id = Ext.id();
			            var id1 = Ext.id();
			            Ext.defer(function () {
			            	me.createIconViewHistory(id, v, r);
			            }, 50);
			            return Ext.String.format('<div><div style="float:left;margin:0;padding:0;width:12px;height:20px" class="icon_bar_{2}" id="{1}"></div><div id="{0}"></div></div>', id, id1, me.iconColor(status));
                    } else {
			            var id = Ext.id();
			            return Ext.String.format('<div style="float:left;margin:0;padding:0;width:12px;height:20px" class="icon_bar_{1}" id="{0}"></div>', id, me.iconColor(status));
                    }
		      }
		 });
		
		Ext.applyIf(me, {
			
			columns : columns,
				
			tbar : [{
            	xtype: 'textfield',
            	itemId:'txtSearch',
            	width:120,
            	enableKeyEvents: true,
	           	listeners: {
	           		
	 	 			specialkey: function(field, e){
	 	 				if(e.getKey() == e.ENTER){
	 	 					me.fireEvent("search");
	 	 				}
	 	 			}
			
	           	}
            },{
            	xtype: 'button',
                text: "Search",
                iconCls: "icon_search",                
                action: "search"
            },
                "->"
            ,{ 
            	xtype: 'button',
                text: "New Order",
                iconCls: "icon_add",
                action: "add"
            }],
            
			bbar : {
				xtype:'pagingtoolbar',
			    displayInfo    : true,
			    pageSize       : PAGE_SIZE,
			    store          : me.store
			}
				
		});		
		
	    this.callParent(arguments);
	    
		Ext.apply(me.store, {pageSize:PAGE_SIZE});
		me.store.load();	    
	},
	
	iconColor:function(v) {
	
		var c = {
			"D":"gray",
			"W":"yellow",
			"X":"red",
			"C":"green"
		}
		
		return c[v];
	},
	
	createIconViewHistory:function(id, v, r) {
		var me = this;
		if (Ext.get(id)) {
            Ext.widget('linkbutton', {
                renderTo: id,
                text: '(' + r.get("wfStatus") + ')',
                iconCls:'icon_postpone',
                width: 75,
                handler: function () { 
                	me.fireEvent("viewHistory",r);
                }
            });
	    }
	    else {
		    Ext.defer(function () {
		    	me.createIconViewHistory(id, v, r);
		    }, 50);
	    }
	}
});
