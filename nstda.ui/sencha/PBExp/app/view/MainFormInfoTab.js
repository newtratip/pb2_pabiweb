Ext.define('PBExp.view.MainFormInfoTab', {
    extend: 'Ext.form.Panel',
    alias:'widget.expBrwInfoTab',
    
    autoScroll:true,

	initComponent: function(config) {
		var me = this;
		
		var store = Ext.create('PB.store.common.ComboBoxStore');
		store.getProxy().api.read = ALF_CONTEXT+'/srcUrl/main/master?all=false';
		store.getProxy().extraParams = {
			p1 : "type='BRW_TYPE'",
			orderBy : 'flag1',
			all : true
		}
		store.load();
		
		var typeStore = Ext.create('PB.store.common.ComboBoxStore');
		typeStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/activity/list';
		typeStore.getProxy().extraParams = {
		    name : '',
			all : true
		}
		typeStore.load();
		
		var bankStore = Ext.create('PB.store.common.ComboBoxStore');
		bankStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/bank/list';
		bankStore.getProxy().extraParams = {
			all : true
		}
		bankStore.load();
		
		var gridStore = Ext.create('PBExp.store.OldGridStore',{storeId:'oldExpBrwGridStore',autoLoad:false});
		gridStore.getProxy().extraParams = {
			u : 'A'
		}
		gridStore.load();
		
		var lbw = 130;
		
		var columns = [{
        	dataIndex: 'name',
        	text: '', 
        	flex:1
	    },{
	        dataIndex: 'id',
        	text: 'เลขที่เอกสาร AV', 
        	flex:1,
        	align:'center'
	    },{
	    	xtype:'numbercolumn',
	        dataIndex: 'waitAmt',
        	text: 'เงินยืมรอหักล้าง', 
        	flex:1,
        	align:'right',
        	format:DEFAULT_MONEY_FORMAT        		
	    },{
	    	xtype:'numbercolumn',
	        dataIndex: 'balance',
        	text: 'เงินยืมคงเหลือ', 
        	flex:1,
        	align:'right',
        	format:DEFAULT_MONEY_FORMAT
	    }];		
		
		Ext.applyIf(me, {
			items:[{
				xtype:'container',
				layout:'hbox',
				margin:"5 0 0 10",
				anchor:"-10",
				items:[{
					xtype:'combo',
					name:'objectiveType',
					fieldLabel:mandatoryLabel('ประเภทการยืมเงิน'),
			    	displayField:'name',
			    	valueField:'id',
			        emptyText : "โปรดเลือก",
			        store: store,
			        queryMode: 'local',
			        typeAhead:true,
			        multiSelect:false,
			        forceSelection:true,
					width:310,
					labelWidth:lbw,
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
	    	       	    change : function(combo, newValue, oldValue, e){
	    	       		   me.fireEvent("selectObjective",combo, newValue, oldValue);
	    	       	    }
					},
					value:replaceIfNull(me.rec.objective_type, null)
				},{
					xtype:'textfield',
					name:'objective',
					fieldLabel:mandatoryLabel('วัตถุประสงค์'),
					labelWidth:90,
					margin:"0 0 0 15",
					flex:1,
					allowBlank:false,
					value:replaceIfNull(me.rec.objective, null),
					maxLength:255
				}]
			},{
				xtype:'container',
				layout:'hbox',
				anchor:'-10',
				items:[{
					xtype:'hidden',
					name:'budgetCcType',
					value:replaceIfNull(me.rec.budget_cc_type, null)
				},{
					xtype:'hidden',
					name:'budgetCc',
					value:replaceIfNull(me.rec.budget_cc, null)
				},{
					xtype:'trigger',
					name:'budgetCcTypeName',
					fieldLabel:mandatoryLabel('งบประมาณที่ใช้'),
					width:310,
					labelWidth:lbw,
					margin:"5 0 0 10",
					triggerCls:'x-form-search-trigger',
					editable:false,
					onTriggerClick:function(evt) {
						me.fireEvent("selectBudgetCc");
					},
					value:replaceIfNull(me.rec.budget_cc_type_name, ''),
					allowBlank:false
				},{
					xtype:'textfield',
					name:'budgetCcName',
					hideLabel:true,
					flex:1,
					margin:'5 0 0 10',
					value:replaceIfNull(me.rec.budget_cc_name, ''),
					readOnly:true,
					fieldStyle:READ_ONLY
				}]
			},{
				xtype:'panel',
				title:'รายละเอียดเงินยืมคงค้าง',
				layout:'border',
				margin:'5 0 0 0',
				height:150,
				items:[{
					region:'center',
					xtype:'grid',
					columns:columns,
					store:gridStore
				},{
					region:'east',
					xtype:'panel',
					width:500,
					layout:'fit',
					split:true,
					collapsible:true,
					title:mandatoryLabel('เหตุผลคงค้างเงินยืม'),
					items:[{
						xtype:'textarea',
						name:'reason',
						hideLabel:true,
						fieldLabel:'เหตุผลคงค้างเงินยืม',
						margin:"0 0 0 0",
//						fieldLabel:,
//						labelWidth:130,
//						margin:"5 5 5 10",
						value:replaceIfNull(me.rec.reason, null),
						allowBlank:false,
						maxLength:255
					}]
				}]
			},{
				xtype:'panel',
				title:'ข้อมูลรายละเอียดเพื่อเบิกจ่าย',
				margin:'0 0 0 0',
				items:[{
					xtype:'container',
					layout:'hbox',
					margin:"0 0 0 0",
					anchor:"-10",
					items:[{
						xtype:'combo',
						name:'totalType',
						margin:"5 0 5 10",
						fieldLabel:mandatoryLabel('รายการยืมเงิน'),
						labelWidth:lbw,
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : "โปรดเลือก",
				        store: typeStore,
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
						flex:1,
						labelWidth:lbw,
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
						},
						value:replaceIfNull(me.rec.total_type, null)
					},{
						xtype:'numericfield',
						name:'total',
						fieldLabel:mandatoryLabel('จำนวนเงินที่ขอยืม'),
						labelWidth:120,
						flex:0.25,
						margin:"5 10 5 15",
						value:replaceIfNull(me.rec.total, null),
						allowBlank:false,
						maxLength:255,
						hideTrigger:true
					}]
				}]
			},{
				xtype:'panel',
				title:'วิธีการรับเงิน กรุณาโอนเงินผ่านบัญชีธนาคาร',
				margin:'0 0 0 0',
				items:[{
					xtype:'radio',
					name:'bankType',
					boxLabel:'ธนาคาร กรุงเทพ (ระบบ e-Payment)',
					inputValue:'0',
					margin:'5 0 0 10',
					checked:replaceIfNull(me.rec.bank_type, "0") == "0", 
					listeners:{
						change:function(rad, newV, oldV) {
							if (newV) {
								me.fireEvent("selectMainBank",rad, "0");
							}
						}
					}
				},{
					xtype:'container',
					layout:'hbox',
					margin:"0 0 5 5",
					anchor:"-10",
					items:[{
						xtype:'radio',
						name:'bankType',
						boxLabel:'ธนาคาร',
						inputValue:'1',
						margin:'5 0 0 5',
						checked:replaceIfNull(me.rec.bank_type, "0") == "1",
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectMainBank",rad, "1");
								}
							}
						}
					},{
						xtype:'combo',
						name:'bank',
						margin:'5 0 0 5',
						hideLabel:true,
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : "โปรดเลือก",
				        store: bankStore,
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
						flex:1,
						labelWidth:lbw,
						allowBlank:true,
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
						},
						value:replaceIfNull(me.rec.bank, null)						
					},{
						xtype:'label',
						html:'<font color="red">*** กรณีที่ท่านเลือกธนาคารอื่น ต้องรับผิดชอบค่าธรรมเนียมการโอนเอง ***</font>',
						margin:'5 10 0 5'
					}]
				}]
			}]
		});		
		
	    this.callParent(arguments);
	},
	
	listeners:{
		added:function() {
			var me = this;
			
			me.fireEvent("selectMainBank", me.down("radio[name=bankType]"), replaceIfNull(me.rec.bank_type, "0"));
		}
	}	
    
});
