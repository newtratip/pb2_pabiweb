Ext.define('PBPcm.view.committee.DtlDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.pcmCommitteeDtlDlg',
	
	initComponent: function(config) {
		var me = this;
		
		var lbw = 70;
		
		var store = Ext.create('PB.store.common.UserStore');
		
		Ext.applyIf(me, {
		        renderTo : Ext.getBody(),
	            modal: true,
	            width: 650,
	            height: 500,
	            layout: 'border',
	            resizable: false,
	            items : [{
	            	region:'center',
		        	xtype : 'form',
			        itemId : 'formDetail',
			        border : 0,
			        items:[{
						xtype: 'hidden',
						name: 'id'
					},{
					    xtype: 'textfield',
					    fieldLabel : 'ชื่อ', 
					    labelWidth: 70,
					    anchor:"-10",
					    name : 'first_name',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    listeners:{
							afterrender:function(txt) {
								Ext.defer(function(){
									txt.focus();
								},100);
							}
						}
					},{
					    xtype: 'textfield',
					    fieldLabel : 'นามสกุล', 
					    labelWidth: 70,
					    anchor:"-10",
					    name : 'last_name',
					    msgTarget: 'side',
					    margin: '10 0 0 10'
					}]
	            },{
	            	region:'south',
//	            	collapsible:true,
//	            	collapsed:true,
	            	title:'ค้นหาพนักงาน',
	            	layout:'border',
	            	height:350,
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
			        	     { text:'ศูนย์', dataIndex: 'org_name', width: 100 },
			        	     { text:'หน่วยงาน', dataIndex: 'section_name', flex:1 },
			        	     { text:'รหัสพนักงาน', dataIndex: 'emp_id', width: 100 },
			        	     { text:'ชื่อ-นามสกุล', dataIndex: 'first_name', flex:1, renderer:function(v,m,r){ return v+" "+r.get('last_name');}}
			        	],
			        	store:store
			        }]
	            }],
		        buttons : [{
		          text: 'บันทึก', 
	//	          disabled : true,
		          action : 'ok',
		          itemId: 'okButton',
		          iconCls:'icon_ok'
		        },{
		          text: 'ยกเลิก',
		          handler:this.destroy,
		          scope:this,
		          iconCls:'icon_no'
		        }]
		});
		
        this.callParent(arguments);
	}
});		