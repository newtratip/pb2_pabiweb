Ext.define('PBExpUse.view.item.DtlDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.expUseItemDtlDlg',
	
	initComponent: function(config) {
		var me = this;
		
		var astore = Ext.create('PB.store.common.ComboBoxStore',{
			autoLoad:false,
			sorters: [{
		         property: 'name',
		         direction: 'ASC'
		    }]
		});
		astore.getProxy().api.read = ALF_CONTEXT+'/admin/main/activity/list';
		astore.getProxy().extraParams = {
			name:''
		}
		astore.load();
		
		var cstore = Ext.create('PB.store.common.ComboBoxStore',{
			autoLoad:false,
			listeners:{
				load:function(st, recs) {
					me.fireEvent("cond1Load", recs);
				}
			}
		});
		cstore.getProxy().api.read = ALF_CONTEXT+'/admin/main/expenserule/listDistinct';
		if (me.rec) {
			cstore.getProxy().extraParams = {
				id:me.rec.get("activityId")
			}
			cstore.load();
		}

		var store = Ext.create('PBExpUse.store.item.ActivityGridStore',{autoLoad:false});
		if (me.rec) {
			store.getProxy().extraParams = {
				id:me.rec.get("activityId"),
				cond:me.rec.get("condition1")
			}
			store.load();
		}
		
		
		var lbw = 140;
		
		Ext.applyIf(me, {
		        renderTo : Ext.getBody(),
	            modal: true,
	            width: 800,
	            height: 500,
	            layout: 'border',
	            resizable: false,
	            items : [{
	            	region:'north',
		        	xtype : 'form',
			        itemId : 'formDetail',
			        border : 0,
			        height:110,
			        items:[{
						xtype: 'hidden',
						name: 'id',
						value : me.rec ? me.rec.get("id") : null
					},{
						xtype:'combo',
						name:'activityId',
						fieldLabel:mandatoryLabel('รายการเบิกจ่าย'),
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : "โปรดเลือก",
				        store: astore,
//				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
				        anchor:"-10",
						labelWidth:lbw,
						margin: '10 0 0 10',
						allowBlank:false,
				        listConfig : {
						    getInnerTpl: function () {
								return '<div>{name}</div>';
						        //return '<div>{name}<tpl if="id != \'\'"> ({id})</tpl></div>';
						    }
						},
				        listeners:{
							beforequery : function(qe) {
								qe.query = getLang()+" "+qe.query;
							},
							select : function(combo, rec){
		    	       		    me.fireEvent("selectActivity",combo, rec);
		    	       	    }
						},
						value:me.rec ? me.rec.get("activityId") : null
					},{
						xtype:'combo',
						name:'condition1',
						fieldLabel:mandatoryLabel('เงื่อนไขการเบิกจ่าย'),
				    	displayField:'name',
				    	valueField:'name',
				        emptyText : "โปรดเลือก",
				        store: cstore,
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
				        anchor:"-10",
						labelWidth:lbw,
						margin: '10 0 0 10',
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
							},
							select : function(combo, rec){
		    	       		    me.fireEvent("selectCond1",combo, rec);
		    	       	    }
						},
						value:me.rec ? me.rec.get("condition1") : null
					},{
					    xtype: 'numericfield',
					    fieldLabel : mandatoryLabel('จำนวนเงินที่ขอเบิก'), 
					    labelWidth: lbw,
//					    anchor:"-10",
						width:300,
					    hideTrigger:true,
					    name : 'amount',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    allowBlank:false,
						value : me.rec ? me.rec.get("amount") : null
					}]
	            },{
		        	region:'center',
		        	xtype:'grid',
		        	margin:'5 0 0 0',
		        	columns:[
		        	     { text:'รายการเบิกจ่าย', dataIndex: 'activity_name', flex:1 },
		        	     { text:'เงื่อนไขการเบิกจ่าย', dataIndex: 'condition_1', width:120},
		        	     { text:'ตำแหน่ง', dataIndex: 'position', flex: 1 },
		        	     { text:'จำนวนเงินอนุมัติ', dataIndex: 'amount', width:100, align:'right', xtype:"numbercolumn", format:DEFAULT_MONEY_FORMAT }
		        	],
		        	store:store
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