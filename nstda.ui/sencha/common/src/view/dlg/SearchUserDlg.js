Ext.define('PB.view.common.SearchUserDlg', {
	extend : 'Ext.window.Window',
    alias:'widget.searchUserDlg',
    
	initComponent: function(config) {
		var me = this;
		
		var lbw = 80;
		
		var store = Ext.create('PB.store.common.UserStore');
		
		Ext.applyIf(me, {
	        renderTo : Ext.getBody(),
	        modal: true,
	        width: 790,
	        height: 500,
	        layout: 'border',
	        resizable: false,
	        items : [{
	        	region:'north',
	        	height:40,
	        	xtype : 'form',
		        itemId : 'searchUser',
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
			 	 					this.fireEvent("searchUser",this);
			 	 				}
			 	 			},
			 	 			afterrender: function(txt) {
								Ext.defer(function(){
									txt.focus();
								},100);
								this.setHeight(22);
			 	 			}
			           	}			        		
					},{
			        	xtype:'button',
			        	text:'ค้นหา',
			        	iconCls:'icon_search',
			        	margin:'5 0 0 10',
		                listeners: {
		                    click: function(){
		                    	this.fireEvent("searchUser", this);
		                    }
		                }
					}]
		        }]
	        },{
	        	region:'center',
	        	xtype:'grid',
	        	margin:'5 0 0 0',
	        	columns:[
	        	     { text:'', dataIndex: 'id', width: 50, align:'center', renderer:
	        	    	 function(v) {
	        	    		return '<input type="radio" name="id" value="'+v+'"/>'; 
	        	    	 }
	        	     },
	        	     { text:'ศูนย์', dataIndex: 'org_name', width: 100 },
	        	     { text:'หน่วยงาน', dataIndex: 'section_name', flex:1 },
	        	     { text:'รหัสพนักงาน', dataIndex: 'emp_id', width: 100 },
	        	     { text:'ชื่อ-นามสกุล', dataIndex: 'first_name', flex:1, renderer:function(v,m,r){ return v+" "+r.get('last_name');}},
	        	     { text:'ตำแหน่ง', dataIndex: 'pos_name', width: 150 }
	        	],
	        	store:store
	        }],
	        buttons : [{
	          text: 'ยืนยัน', 
	          action : 'ok',
	          iconCls:'icon_ok',
	          listeners: {
	               click: function(){
	                	this.fireEvent("confirmUser", this);
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