Ext.define('PB.view.common.SearchSectionProjectDlg', {
	extend : 'Ext.window.Window',
    alias:'widget.searchSectionProjectDlg',
    
	initComponent: function(config) {
		var me = this;
		
		var lbw = 80;
		
		var store = Ext.create('PB.store.common.SectionProjectStore');
		
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
		        itemId : 'searchBudgetCc',
		        border : 0,
		        items:[{
		        	xtype:'container',
		        	margin:'5 0 0 10',
		        	layout:{
						type:'hbox',
						align:'middle'
					},
					items:[{
						xtype:'label',
						text:'ประเภท:',
						width:lbw
					},{
						xtype:'radio',
						name:'type',
						boxLabel:'หน่วยงาน',
						inputValue:'U',
						margin:'0 0 0 5',
						checked:true,
						listeners:{
							change:function(rad, newV, oldV) {
								me.fireEvent("selectRadio",rad,newV);
							}
						}
					},{
						xtype:'radio',
						name:'type',
						boxLabel:'โครงการ',
						inputValue:'P',
						margin:'0 0 0 5',
						checked:false
					}]
		        },{
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
			           	listeners: {
			 	 			specialkey: function(field, e){
			 	 				if(e.getKey() == e.ENTER){
			 	 					this.fireEvent("searchSectionProject",this);
			 	 				}
			 	 			},
							afterrender:function(txt) {
								Ext.defer(function(){
									txt.focus();
								},100);
							}
			           	}			        		
					},{
			        	xtype:'button',
			        	action:'searchSectionProject',
			        	text:'ค้นหา',
			        	iconCls:'icon_search',
			        	margin:'5 0 0 10',
		                listeners: {
		                    click: function(){
		                    	this.fireEvent("searchSectionProject", this);
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
	        	     { text:'ศูนย์', dataIndex: 'type', width: 150 },
	        	     { text:'หน่วยงาน', dataIndex: 'name', flex:1 }
	        	],
	        	store:store
	        }],
	        buttons : [{
	          text: 'ยืนยัน', 
	          action : 'ok',
	          iconCls:'icon_ok',
	          listeners: {
	               click: function(){
	                	this.fireEvent("confirmSectionProject", this);
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