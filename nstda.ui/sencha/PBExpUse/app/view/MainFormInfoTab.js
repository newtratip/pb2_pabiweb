Ext.define('PBExpUse.view.MainFormInfoTab', {
    extend: 'Ext.form.Panel',
    alias:'widget.expUseInfoTab',
    
	autoScroll:true,
	
	initComponent: function(config) {
		var me = this;
		
		var bankStore = Ext.create('PB.store.common.ComboBoxStore',{autoLoad:false});
		bankStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/bank/list';
		bankStore.getProxy().extraParams = {
			lang:getLang()
		}
		bankStore.load();
		
		var poStore = Ext.create('PB.store.common.ComboBoxStore');
		poStore.getProxy().api.read = ALF_CONTEXT+'/exp/use/po/list';
		poStore.getProxy().extraParams = {
			all : true
		}
		poStore.load();

		var assetStore = Ext.create('PB.store.common.ComboBoxStore');
		assetStore.getProxy().api.read = ALF_CONTEXT+'/exp/use/asset/list';
		assetStore.getProxy().extraParams = {
			all : true
		}
		assetStore.load();
		
		var avStore = Ext.create('PBExpUse.store.OldComboStore');
//		avStore.getProxy().api.read = ALF_CONTEXT+'/exp/brw/old/list';
		avStore.getProxy().extraParams = {
			r : replaceIfNull(me.rec.req_by, null)
		}
		avStore.load();
		
		var astore = Ext.create('PB.store.common.ComboBoxStore',{
			autoLoad:false,
			sorters: [{
		         property: 'name',
		         direction: 'ASC'
		    }]
		});
		astore.getProxy().api.read = ALF_CONTEXT+'/admin/main/activity/listIcharge';
		astore.load({params:{query:getLang()+' '}});
		
		var lbw = 160;
		var ptw = 180;
		
		var pd1={},pd2={},pd3={},pd4={};
		var payType = parseInt(me.rec.pay_type);
		if (payType==1) {
			pd1.sup_code = me.rec.pay_dtl1;
		} 
		else
//		if (payType==2) {
//			pd2.sup_code = me.rec.pay_dtl1;
//			pd2.po_no = me.rec.pay_dtl2;
//			pd2.asset_no = me.rec.pay_dtl3;
//		} 
//		else
		if (payType==2) {
			pd2.av_code = me.rec.pay_dtl1;
		}
		else
		if (payType==3) {
			pd3.icharge_code = me.rec.pay_dtl1;
			pd3.icharge_name = me.rec.icharge_name;
			pd3.icharge_type = me.rec.pay_dtl2;
			pd3.icharge_type_name = me.rec.icharge_type_name;
			pd3.icharge_act_id = parseInt(me.rec.pay_dtl3);
		}
		
		Ext.applyIf(me, {
			items:[{
				xtype:'panel',
				title:'<font color="red">'+PBExpUse.Label.n.payTitle+'</font>',
				margin:'0 0 0 0',
				items:[{
				xtype:'container',
				layout:'hbox',
				anchor:'-10',
				margin:'0 10 0 0',
				items:[{
						xtype:'hidden',
						name:'emotion',
						value:replaceIfNull(me.rec.emotion, "")
					},{
						xtype:'hidden',
						name:'fundId',
						value:replaceIfNull(me.rec.fund_id, null)
					},{
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
						fieldLabel:mandatoryLabel(PB.Label.b.name),
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
						xtype:'button',
						hidden:replaceIfNull(me.rec.budget_cc_type, null) == null,
						iconCls:'icon_money',
						margin:"5 0 0 10",
						text:'',
						action:'showBudget'
					},{
						xtype:'textfield',
						name:'budgetCcName',
						hideLabel:true,
						flex:1,
						margin:'5 0 0 10',
						value:replaceIfNull(me.rec.budget_cc_name, ''),
						readOnly:true,
						fieldStyle:READ_ONLY
					},{
						xtype:'textfield',
						name:'fundName',
						hideLabel:true,
						flex:1,
						margin:'5 0 0 10',
						value:replaceIfNull(me.rec.fund_name, ''),
						readOnly:true,
						fieldStyle:READ_ONLY
					}]
				},{
					xtype:'container',
					layout:'hbox',
					anchor:'-10',
					margin:'0 10 0 0',
					items:[{
						xtype:'textfield',
						name:'objective',
						fieldLabel:mandatoryLabel(PBExpUse.Label.n.obj),
						labelWidth:lbw,
						margin:"5 0 0 10",
						flex:1,
						allowBlank:false,
						value:replaceIfNull(me.rec.objective, null),
						maxLength:255
					}]
				},{
					xtype:'container',
					layout:'hbox',
					anchor:'-10',
					margin:'0 10 5 0',
					items:[{
						xtype:'hidden',
						name:'costControlId',
						value:replaceIfNull(me.rec.cost_control_id, null)
					},{
						xtype:'hidden',
						name:'costControlTypeId',
						value:replaceIfNull(me.rec.cost_control_type_id, null)
					},{
						xtype:'trigger',
						name:'costControlTypeName',
						fieldLabel:PBExpUse.Label.n.cc,
						width:310,
						labelWidth:lbw,
						margin:"5 0 0 10",
						trigger1Cls: 'x-form-clear-trigger',
					    trigger2Cls: 'x-form-search-trigger',
					    hideTrigger1: true,
					    hasSearch : false,
						editable:false,
						onTrigger1Click:function(evt) {
							this.triggerEl.item(0).dom.parentNode.style.width = "0px";
							me.fireEvent("clearCostControl");
						},
						onTrigger2Click:function(evt) {
							me.fireEvent("selectCostControl");
						},
						listeners:{
							afterrender:function() {
								var w = this.getValue() ? "17" : "0";
								this.triggerEl.item(0).dom.parentNode.style.width = w+"px";
							},
							change:function(trigger, newV, oldV) {
								var w = newV ? "17" : "0";
								this.triggerEl.item(0).dom.parentNode.style.width = w+"px";
							}
						},
						value:replaceIfNull(me.rec.cost_control_type_name, null)
					},{
						xtype:'textfield',
						name:'costControlName',
						hideLabel:true,
						flex:1,
						margin:'5 0 0 10',
						value:replaceIfNull(me.rec.cost_control_name, null),
						readOnly:true,
						fieldStyle:READ_ONLY
					}]
				}]
			},{
				xtype:'panel',
				title:PBExpUse.Label.n.typeTitle,
				margin:'0 0 0 0',
				items:[{
					xtype:'container',
					layout:'hbox',
					margin:"0 10 0 0",
					anchor:"-10",
					items:[{
						xtype:'radio',
						name:'payType',
						boxLabel:PBExpUse.Label.n.payEmp,
						inputValue:'0',
						margin:'5 0 0 10',
						width:ptw,
						checked:replaceIfNull(me.rec.pay_type, "0") == "0",
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectPayType",rad, '0');
								} else {
									me.oldPayType = "0";
								}
							}
						}
					}]
				},{
					xtype:'container',
					layout:'hbox',
					margin:"0 10 0 0",
					anchor:"-10",
					items:[{
						xtype:'radio',
						name:'payType',
						boxLabel:PBExpUse.Label.n.paySup,
						inputValue:'1',
						margin:'5 0 0 10',
						width:ptw,
						checked:replaceIfNull(me.rec.pay_type, "0") == "1",
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectPayType",rad, '1');
								} else {
									me.oldPayType = "1";
								}
							}
						}
					},{
						xtype:'textfield',
						name:'supName',
						hideLabel:true,
						fieldLabel:PBExpUse.Label.n.paySup,
						flex:1,
						margin:"5 0 0 10",
						value:replaceIfNull(pd1.sup_code, ''),
						allowBlank:false,
						disabled:true,
						emptyText:PBExpUse.Label.n.paySupEmpty,
						maxLength:255
					},{
						xtype:'combo',
						name:'poNo',
						margin:'5 0 0 5',
						emptyText:'PO No.',
						fieldLabel:'PO No.',
						store:poStore,
						hideLabel:true,
				    	displayField:'name',
				    	valueField:'id',
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
						width:100,
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
						value:replaceIfNull(pd2.po_no, null),
						disabled:true,
						hidden:true
					},{
						xtype:'combo',
						name:'assetNo',
						margin:'5 0 0 5',
						emptyText:'Asset No.',
						fieldLabel:'Asset No.',
						store:assetStore,
						hideLabel:true,
				    	displayField:'name',
				    	valueField:'id',
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
						width:100,
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
						value:replaceIfNull(pd2.asset_no, null),
						disabled:true,
						hidden:true
					}]
				},{
					xtype:'container',
					layout:'hbox',
					margin:"0 10 0 0",
					anchor:"-10",
					items:[{
						xtype:'radio',
						name:'payType',
						boxLabel:PBExpUse.Label.n.payAv,
						inputValue:'2',
						margin:'5 0 0 10',
						width:ptw,
						checked:replaceIfNull(me.rec.pay_type, "0") == "2",
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectPayType",rad, '2');
								} else {
									me.oldPayType = "2";
								}
							}
						}
					},{
						xtype:'combo',
						name:'avCode',
						margin:'5 0 0 10',
						fieldLabel:mandatoryLabel(PBExpUse.Label.n.payAvNo),
						labelWidth:150,
						store:avStore,
				    	displayField:'number', 
				    	valueField:'number', 
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
				        flex:1,
						allowBlank:false,
				        listConfig : {
						    getInnerTpl: function () {
								return '<div>{number} {name} (คงเหลือ {balance:number("0,000.00")} จาก {waitamt:number("0,000.00")})</div>';
						        //return '<div>{name}<tpl if="id != \'\'"> ({id})</tpl></div>';
						    }
						},
				        listeners:{
							beforequery : function(qe) {
								qe.query = new RegExp(qe.query, 'i');
				//				qe.forceAll = true;
		    	       	    },
		    	       	    change : function(cmb, newV, oldV, e){
			    	       		me.fireEvent("selectOldAv", cmb, newV, oldV);
			    	       	}
						},
						value:replaceIfNull(pd2.av_code, null),
						disabled:true
					}]
				},{
					xtype:'container',
					layout:'hbox',
					margin:"0 10 5 0",
					anchor:"-10",
					items:[{
						xtype:'radio',
						name:'payType',
						boxLabel:'Internal Charge',
						inputValue:'3',
						margin:'5 0 0 10',
						width:ptw,
						checked:replaceIfNull(me.rec.pay_type, "0") == "3",
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectPayType",rad, '3');
								} else {
									me.oldPayType = "3";
								}
							}
						}
					},{
						xtype:'hidden',
						name:'ichargeType',
						value:replaceIfNull(pd3.icharge_type, null)
					},{
						xtype:'hidden',
						name:'ichargeCode',
						value:replaceIfNull(pd3.icharge_code, null)
					},{
						xtype:'trigger',
						name:'ichargeTypeName',
						fieldLabel:mandatoryLabel(PBExpUse.Label.n.payIntUnit),
						width:310,
						labelWidth:150,
						margin:"5 0 0 10",
						triggerCls:'x-form-search-trigger',
						editable:false,
						onTriggerClick:function(evt) {
							me.fireEvent("selectIcharge");
						},
						value:replaceIfNull(pd3.icharge_type_name, ''),
						allowBlank:false,
						disabled:true
					},{
						xtype:'textfield',
						name:'ichargeName',
						hideLabel:true,
						flex:1,
						margin:'5 0 0 10',
						value:replaceIfNull(pd3.icharge_name, ''),
						readOnly:true,
						fieldStyle:READ_ONLY
					}]
				},{
					xtype:'radio',
					name:'payType',
					boxLabel:PBExpUse.Label.n.payCash,
					inputValue:'4',
					margin:'5 0 0 10',
					width:ptw,
					checked:replaceIfNull(me.rec.pay_type, "0") == "4",
					listeners:{
						change:function(rad, newV, oldV) {
							if (newV) {
								me.fireEvent("selectPayType",rad, '4');
							} else {
								me.oldPayType = "4";
							}
						}
					}
				}]
			},{
				xtype:'panel',
				title:PBExpUse.Label.n.methodTitle,
				margin:'0 0 0 0',
				border:0,
				items:[{
					xtype:'radio',
					name:'bankType',
					itemId:'bankType0',
					boxLabel:PBExpUse.Label.n.bankBbl,
					disabled:(payType!=0 && payType!=2),
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
						itemId:'bankType1',
						boxLabel:PBExpUse.Label.n.bank,
						disabled:(payType!=0 && payType!=2),
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
						margin:'5 10 0 5',
						hideLabel:true,
						fieldLabel:'ธนาคาร',
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : PB.Label.m.select,
				        store: bankStore,
//				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
						flex:1,
						allowBlank:false,
				        listConfig : {
						    getInnerTpl: function () {
								return '<div>{name}</div>';
						        //return '<div>{name}<tpl if="id != \'\'"> ({id})</tpl></div>';
						    }
						},
//				        listeners:{
//							beforequery : function(qe) {
//								qe.query = new RegExp(qe.query, 'i');
				//				qe.forceAll = true;
//		    	       	    }
//						},
						value:replaceIfNull(me.rec.bank, null),		
						disabled:true
					}]
				},{
					xtype:'label',
					html:'<font color="red">'+PBExpUse.Label.n.bankWarn+'</font>',
					margin:'5 10 5 15',
					anchor:"-10"
				}]
			}]
		});		
		
	    this.callParent(arguments);
	},
	
	listeners:{
		added:function() {
			var me = this;
			
			me.fireEvent("selectPayType", me.down("radio[name=payType]"), replaceIfNull(me.rec.pay_type, "0"));
			me.fireEvent("selectMainBank", me.down("radio[name=bankType]"), replaceIfNull(me.rec.bank_type, "0"));
		}
	}	

});
