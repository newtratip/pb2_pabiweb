Ext.define('PB.view.common.SearchCostControlDlg', {
	extend : 'Ext.window.Window',
    alias:'widget.searchCostControlDlg',
    
	initComponent: function(config) {
		var me = this;
		
		var lbw = 80;
		
		var typeStore = Ext.create('PB.store.common.ComboBoxStore',{autoLoad:false});
		typeStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/costcontrol/type/list';
		typeStore.load();
		
		var store = Ext.create('PB.store.common.CostControlStore');
		
		Ext.applyIf(me, {
	        renderTo : Ext.getBody(),
	        modal: true,
	        width: 620,
	        height: 500,
	        layout: 'border',
	        resizable: false,
	        items : [{
	        	region:'north',
	        	height:70,
	        	xtype : 'form',
		        itemId : 'searchCostControl',
		        border : 0,
		        items:[{
		        	xtype:'container',
		        	margin:'5 0 0 5',
		        	layout:{
						type:'hbox',
						align:'middle'
					},
					items:[{
						xtype:'combo',
						name:'type',
						fieldLabel:'ประเภท',
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : "โปรดเลือก",
				        store: typeStore,
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
				        anchor:"-10",
						labelWidth:lbw,
						margin: '10 0 0 0',
						allowBlank:false,
				        listConfig : {
						    getInnerTpl: function () {
								return '<div>{name}</div>';
						        //return '<div>{name}<tpl if="id != \'\'"> ({id})</tpl></div>';
						    }
						},
				        listeners:{
							beforequery : function(qe) {
								qe.query = new RegExp(qe.query, 'i');
				//				qe.forceAll = true;
							}
						}
					}]
		        },{
		        	xtype:'container',
		        	margin:'0 0 0 5',
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
			           	listeners: {
			 	 			specialkey: function(field, e){
			 	 				if(e.getKey() == e.ENTER){
			 	 					this.fireEvent("searchCostControl",this);
			 	 				}
			 	 			}
			           	}			        		
					},{
			        	xtype:'button',
			        	text:'ค้นหา',
			        	iconCls:'icon_search',
			        	margin:'5 0 0 10',
		                listeners: {
		                    click: function(){
		                    	this.fireEvent("searchCostControl", this);
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
	        	     { text:'ประเภท', dataIndex: 'type', width: 150 },
	        	     { text:'ชื่อ', dataIndex: 'name', flex:1 }
	        	],
	        	store:store
	        }],
	        buttons : [{
	          text: 'ยืนยัน', 
	          action : 'ok',
	          iconCls:'icon_ok',
	          listeners: {
	               click: function(){
	                	this.fireEvent("confirmCostControl", this);
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