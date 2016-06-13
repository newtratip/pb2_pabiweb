Ext.define('PB.view.common.SearchSectionProjectUserDlg', {
	extend : 'Ext.window.Window',
    alias:'widget.searchSectionProjectUserDlg',
    
	initComponent: function(config) {
		var me = this;
		
		var lbw = 70;
		
		var cmbStore = Ext.create('PB.store.common.ComboBoxStore',{
			autoLoad:false,
			sorters: [
		        {
		            property : 'name',
		            direction: 'ASC'
		        }
		    ]
		});
		cmbStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/sectionProject/list';
		cmbStore.getProxy().extraParams = {
			t:"U"
		}
		cmbStore.load();
		
		var store = Ext.create('PB.store.common.SectionProjectUserStore',{autoLoad:false});
		store.getProxy().api.read = ALF_CONTEXT+'/admin/main/employee/list';
//		store.getProxy().extraParams = {
//			t:"U",
//			c:0
//		}
//		store.load();

		var selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode:"MULTI",
			showHeaderCheckbox:true
		});
		
		Ext.applyIf(me, {
	        renderTo : Ext.getBody(),
	        modal: true,
	        width: 650,
	        height: 500,
	        layout: 'border',
	        resizable: false,
	        items : [{
	        	region:'north',
	        	height:70,
	        	xtype : 'form',
		        itemId : 'searchSectProjUser',
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
					},{
						xtype:'combo',
						itemId:'code',
						hideLabel:true,
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : "โปรดเลือก",
				        store: cmbStore,
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
						flex:1,
						margin:'0 10 0 10',
						editable:true,
						allowBlank:false,
						listeners:{
							beforequery : function(qe) {
								qe.query = new RegExp(qe.query, 'i');
				//				qe.forceAll = true;
							},
							select : function(combo, rec){
		    	       		   me.fireEvent("selectCourse",combo, rec);
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
			        	fieldLabel:'คำค้นหา',
			        	labelWidth:lbw,
			        	flex:1,
			        	margin:'5 0 0 0',
		            	enableKeyEvents: true,
			           	listeners: {
			 	 			specialkey: function(field, e){
			 	 				if(e.getKey() == e.ENTER){
			 	 					this.fireEvent("searchUser",this);
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
			        	margin:'5 10 0 10',
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
	        	     { text:'รหัสพนักงาน', dataIndex: 'code', width: 100 },
	        	     { text:'ชื่อ - นามสกุล', dataIndex: 'name', flex: 1 },
	        	     { text:'หน่วยงาน / โครงการ', dataIndex: 'utype', flex: 1 },
	        	     { text:'ตำแหน่ง', dataIndex: 'position', flex:1 }
	        	],
	        	store:store,
	        	selModel:selModel
	        }],
	        buttons : [{
	          text: 'ยืนยัน', 
	          action : 'ok',
	          iconCls:'icon_ok',
	          listeners: {
	               click: function(){
	                	this.fireEvent("confirm", this);
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