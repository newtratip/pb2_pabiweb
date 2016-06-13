Ext.define('PBExpUse.view.MainFormInfoTab', {
    extend: 'Ext.form.Panel',
    alias:'widget.expUseInfoTab',
    
	autoScroll:true,
	
	initComponent: function(config) {
		var me = this;
		
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
		
		var avStore = Ext.create('PB.store.common.ComboBoxStore');
		avStore.getProxy().api.read = ALF_CONTEXT+'/exp/brw/old/list';
		avStore.getProxy().extraParams = {
			all : true
		}
		avStore.load();
		
		var lbw = 120;
		
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
		
		var pd1={},pd2={},pd3={},pd4={};
		var payType = parseInt(me.rec.pay_type);
		if (payType==1) {
			pd1.sup_code = me.rec.pay_dtl1;
		} 
		else
		if (payType==2) {
			pd2.sup_code = me.rec.pay_dtl1;
			pd2.po_no = me.rec.pay_dtl2;
			pd2.asset_no = me.rec.pay_dtl3;
		} 
		else
		if (payType==3) {
			pd3.av_code = me.rec.pay_dtl1;
		}
		else
		if (payType==4) {
			pd4.icharge_code = me.rec.pay_dtl1;
			pd4.icharge_name = me.rec.icharge_name;
			pd4.icharge_type = me.rec.pay_dtl2;
			pd4.icharge_type_name = me.rec.icharge_type_name;
		}
		
		Ext.applyIf(me, {
			items:[{
				xtype:'panel',
				title:'<font color="red">ข้อมูลเพื่อการ เบิกจ่าย / หักล่างค่าใช้จ่ายเงินยืม</font>',
				margin:'0 0 0 0',
				items:[{
					xtype:'container',
					layout:'hbox',
					anchor:'-10',
					margin:'0 10 0 0',
					items:[{
						xtype:'textfield',
						name:'objective',
						fieldLabel:mandatoryLabel('วัตถุประสงค์'),
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
				}]
			},{
				xtype:'panel',
				title:'วิธีเบิกจ่าย / หักล้างค่าใช้จ่าย(เงินยืม)',
				margin:'0 0 0 0',
				items:[{
					xtype:'container',
					layout:'hbox',
					margin:"0 10 0 0",
					anchor:"-10",
					items:[{
						xtype:'radio',
						name:'payType',
						boxLabel:'จ่ายพนักงาน',
						inputValue:'0',
						margin:'5 0 0 10',
						width:190,
						checked:replaceIfNull(me.rec.pay_type, "0") == "0",
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectPayType",rad, '0');
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
						boxLabel:'จ่าย Supplier',
						inputValue:'1',
						margin:'5 0 0 10',
						width:190,
						checked:replaceIfNull(me.rec.pay_type, "0") == "1",
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectPayType",rad, '1');
								}
							}
						}
					},{
						xtype:'textfield',
						name:'supName',
						hideLabel:true,
						fieldLabel:'จ่าย Supplier',
						flex:1,
						margin:"5 0 0 10",
						value:replaceIfNull(pd1.sup_code, ''),
						allowBlank:false,
						disabled:true
					}]
				},{
					xtype:'container',
					layout:'hbox',
					margin:"0 10 0 0",
					anchor:"-10",
					items:[{
						xtype:'radio',
						name:'payType',
						boxLabel:'จ่าย Supplier ค่าออกของ',
						inputValue:'2',
						margin:'5 0 0 10',
						width:190,
						checked:replaceIfNull(me.rec.pay_type, "0") == "2",
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectPayType",rad, '2');
								}
							}
						}
					},{
						xtype:'textfield',
						name:'supFeeName',
						hideLabel:true,
						fieldLabel:'จ่าย Supplier ค่าออกของ',
						flex:1,
						margin:"5 0 0 10",
						value:replaceIfNull(pd2.sup_code, ''),
						allowBlank:false,
						disabled:true
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
						disabled:true
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
						disabled:true
					}]
				},{
					xtype:'container',
					layout:'hbox',
					margin:"0 10 0 0",
					anchor:"-10",
					items:[{
						xtype:'radio',
						name:'payType',
						boxLabel:'เคลียร์เงินยืมพนักงาน',
						inputValue:'3',
						margin:'5 0 0 10',
						width:190,
						checked:replaceIfNull(me.rec.pay_type, "0") == "3",
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectPayType",rad, '3');
								}
							}
						}
					},{
						xtype:'combo',
						name:'avCode',
						margin:'5 0 0 10',
						fieldLabel:mandatoryLabel('เลขที่รับเอกสาร AV'),
						labelWidth:150,
						store:avStore,
				    	displayField:'id', ///////////////////////////////////////////////////
				    	valueField:'id', 
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
						width:310,
						allowBlank:false,
				        listConfig : {
						    getInnerTpl: function () {
								return '<div>{id}</div>';
						        //return '<div>{name}<tpl if="id != \'\'"> ({id})</tpl></div>';
						    }
						},
				        listeners:{
							beforequery : function(qe) {
								qe.query = new RegExp(qe.query, 'i');
				//				qe.forceAll = true;
		    	       	    }
						},
						value:replaceIfNull(pd3.av_code, null),
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
						inputValue:'4',
						margin:'5 0 0 10',
						width:190,
						checked:replaceIfNull(me.rec.pay_type, "0") == "4",
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectPayType",rad, '4');
								}
							}
						}
					},{
						xtype:'hidden',
						name:'ichargeType',
						value:replaceIfNull(pd4.icharge_type, null)
					},{
						xtype:'hidden',
						name:'ichargeCode',
						value:replaceIfNull(pd4.icharge_code, null)
					},{
						xtype:'trigger',
						name:'ichargeTypeName',
						fieldLabel:mandatoryLabel('หน่วยงาน (ผู้ให้บริการ)'),
						width:310,
						labelWidth:150,
						margin:"5 0 0 10",
						triggerCls:'x-form-search-trigger',
						editable:false,
						onTriggerClick:function(evt) {
							me.fireEvent("selectIcharge");
						},
						value:replaceIfNull(pd4.icharge_type_name, ''),
						allowBlank:false,
						disabled:true
					},{
						xtype:'textfield',
						name:'ichargeName',
						hideLabel:true,
						flex:1,
						margin:'5 0 0 10',
						value:replaceIfNull(pd4.icharge_name, ''),
						readOnly:true,
						fieldStyle:READ_ONLY
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
						fieldLabel:'ธนาคาร',
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : "โปรดเลือก",
				        store: bankStore,
				        queryMode: 'local',
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
				        listeners:{
							beforequery : function(qe) {
								qe.query = new RegExp(qe.query, 'i');
				//				qe.forceAll = true;
		    	       	    }
						},
						value:replaceIfNull(me.rec.bank, null),		
						disabled:true
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
			me.fireEvent("selectPayType", me.down("radio[name=payType]"), replaceIfNull(me.rec.pay_type, "0"));
		}
	}	

});
