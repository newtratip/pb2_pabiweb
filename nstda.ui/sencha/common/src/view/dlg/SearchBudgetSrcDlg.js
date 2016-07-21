Ext.define('PB.view.common.SearchBudgetSrcDlg', {
	extend : 'Ext.window.Window',
    alias:'widget.searchBudgetSrcDlg',
    
	initComponent: function(config) {
		var me = this;
		
		var lbw = 140;
		
		var store = Ext.create('PB.store.common.BudgetSrcStore');
		
		Ext.applyIf(me, {
	        renderTo : Ext.getBody(),
	        modal: true,
	        width: 720,
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
						text:PB.Label.b.name+":",
						width:lbw
					},{
						xtype:'radio',
						name:'type',
						boxLabel:PB.Label.m.section,
						inputValue:'U',
						margin:'0 0 0 5',
						checked:true,
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectRadio",rad, "U");
								}
							}
						}
					},{
						xtype:'radio',
						name:'type',
						boxLabel:PB.Label.m.project,
						inputValue:'P',
						margin:'0 0 0 5',
						checked:false,
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectRadio",rad, "P");
								}
							}
						}
					},{
						xtype:'radio',
						name:'type',
						boxLabel:PB.Label.b.invAsset,
						inputValue:'A',
						margin:'0 15 0 5',
						checked:false,
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectRadio",rad, "A");
								}
							}
						},
						afterBoxLabelTextTpl: ' <img src="../res/page/img/icon/question.png" class="info_image" data-qtip="ใช้งบประมาณซึ่งวางแผนตั้งแต่ต้นปีงบประมาณ เป็นของส่วนกลาง สนับสนุนให้ใช้ประโยชน์คุ้มค่าสูงสุด"></img>'
					},{
						xtype:'radio',
						name:'type',
						boxLabel:PB.Label.b.constr,
						inputValue:'B',
						margin:'0 0 0 5',
						checked:false,
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectRadio",rad, "B");
								}
							}
						}
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
			        	fieldLabel:PB.Label.m.searchTerm,
			        	labelWidth:lbw,
			        	width:400,
			        	margin:'5 0 0 0',
		            	enableKeyEvents: true,
			           	listeners: {
			 	 			specialkey: function(field, e){
			 	 				if(e.getKey() == e.ENTER){
			 	 					this.fireEvent("searchBudgetSrc",this);
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
			        	action:'searchBudgetSrc',
			        	text:PB.Label.m.search,
			        	iconCls:'icon_search',
			        	margin:'5 0 0 10',
		                listeners: {
		                    click: function(){
		                    	this.fireEvent("searchBudgetSrc", this);
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
	        	     { text:PB.Label.m.org, dataIndex: 'type', width: 150 },
	        	     { text:PB.Label.b.sectName, dataIndex: 'name', flex:1 },
	        	     { text:'Cost Center', dataIndex: 'cc', flex:1 }
	        	],
	        	store:store
	        }],
	        buttons : [{
	          text: PB.Label.m.confirm, 
	          action : 'ok',
	          iconCls:'icon_ok',
	          listeners: {
	               click: function(){
	                	this.fireEvent("confirmBudgetSrc", this);
	               }
	          }
	        },{
	          text: PB.Label.m.cancel,
	          iconCls:'icon_no',
	          handler:this.destroy,
	          scope:this
	        }]
		});
		
        this.callParent(arguments);
	}
});