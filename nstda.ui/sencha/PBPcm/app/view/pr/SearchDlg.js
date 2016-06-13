Ext.define('PBPcm.view.pr.SearchDlg', {
	extend : 'Ext.window.Window',
    alias:'widget.pcmReqSearchDlg',
    
	initComponent: function(config) {
		var me = this;
		
		var lbw = 80;
		
		var store = Ext.create('PBPcm.store.SearchGridStore',{autoLoad:false});
		
		Ext.applyIf(me, {
	        renderTo : Ext.getBody(),
	        modal: true,
	        width: 750,
	        height: 470,
	        layout: 'border',
	        resizable: false,
	        items : [{
	        	region:'north',
	        	height:40,
	        	xtype : 'form',
		        itemId : 'searchPR',
		        border : 0,
		        items:[{
		        	xtype:'container',
		        	margin:'5 0 0 10',
		        	layout:{
						type:'hbox',
						align:'middle'
					},
					items:[{
			        	xtype:'textfield',
			        	itemId:'searchTerm',
			        	fieldLabel:'คำค้นหา',
			        	labelWidth:lbw,
			        	width:400,
			        	margin:'5 0 0 0',
		            	enableKeyEvents: true,
		            	hasfocus:true,
			           	listeners: {
			 	 			specialkey: function(field, e){
			 	 				if(e.getKey() == e.ENTER){
			 	 					this.fireEvent("searchPR",this);
			 	 				}
			 	 			},
			 	 			afterrender: function(txt) {
								Ext.defer(function(){
									txt.focus();
								},100);
			 	 			}
			           	}			        		
					},{
			        	xtype:'button',
			        	text:'ค้นหา',
			        	iconCls:'icon_search',
			        	margin:'5 0 0 10',
		                listeners: {
		                    click: function(){
		                    	this.fireEvent("searchPR", this);
		                    }
		                }
					}]
		        }]
	        },{
	        	region:'center',
	        	xtype:'grid',
	        	margin:'5 0 0 0',
	        	columns:[
	        	     { text:'', dataIndex: 'id', width: 40, align:'center', renderer:
	        	    	 function(v) {
	        	    		return '<input type="radio" name="id" value="'+v+'"/>'; 
	        	    	 }
	        	     },
	        	     { text:'PR NO.', dataIndex: 'id', width: 100 },
	        	     { text:'ประเภท', dataIndex: 'objective_type', align:'center', width:60 },
	        	     { text:'งบประมาณที่ใช้', dataIndex: 'budget_cc_name', flex:1 },
	        	     { text:'วัตถุประสงค์', dataIndex: 'objective', width: 100 },
	        	     { text:'จำนวนเงิน', dataIndex: 'total', align:'right', xtype:'numbercolumn', width: 110},
	        	     { text:'ผู้ขอเบิก', dataIndex: 'req_by', align:'center', width: 120 },
	        	     { text:'วันที่ขอ', dataIndex: 'created_time_show', width: 110 },
	        	     {
		 	        	xtype: 'actioncolumn',
		 	        	dataIndex: 'action',
		 	        	text: '', 
		 	            width: 40,
		 	            align:'center',
		 	            items: [{
			                tooltip: 'Detail', 
			                action : 'viewDetail',
			        	    getClass: function(v) {
			        	    	return getActionIcon(v, "V", 'view');
			        	    }
		 	            }]
	        	     }
	        	],
	        	store:store
	        }],
	        buttons : [{
	          text: 'ยืนยัน', 
	          action : 'ok',
	          iconCls:'icon_ok',
	          listeners: {
	               click: function(){
	                	this.fireEvent("confirmPR", this);
	               }
	          }
	        },{
	          text: 'ยกเลิก',
	          iconCls:'icon_no',
	          handler:this.destroy,
	          scope:this
	        }]
		});
		
	    this.callParent(arguments);
	
	}
});