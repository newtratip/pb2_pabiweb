Ext.define('PBPcm.view.MainFormInfoTab', {
    extend: 'Ext.form.Panel',
    alias:'widget.pcmReqInfoTab',
    
    autoScroll:true,

	initComponent: function(config) {
		var me = this;
		
		var store = Ext.create('PB.store.common.ComboBoxStore');
		store.getProxy().api.read = ALF_CONTEXT+'/srcUrl/main/master?all=false';
		store.getProxy().extraParams = {
			p1 : "type='PC'",
			orderBy : 'flag1',
			all : true
		}
		store.load();
		
		var currencyStore = Ext.create('PB.store.common.ComboBoxStore');
		currencyStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/currency/list';
		currencyStore.getProxy().extraParams = {
		}
		currencyStore.load();
		
		var stockStore = Ext.create('PB.store.common.ComboBoxStore');
		stockStore.getProxy().api.read = ALF_CONTEXT+'/srcUrl/main/master?all=false';
		stockStore.getProxy().extraParams = {
			p1 : "type='STOCK'",
			orderBy : 'code',
			all : true
		}
		stockStore.load();
		
		var purUnitStore = Ext.create('PB.store.common.ComboBoxStore');
		purUnitStore.getProxy().api.read = ALF_CONTEXT+'/srcUrl/main/master?all=false';
		purUnitStore.getProxy().extraParams = {
			p1 : "type='PU'",
			orderBy : 'code',
			all : true
		}
		purUnitStore.load();
		
		var prototypeStore = Ext.create('PB.store.common.ComboBoxStore');
		prototypeStore.getProxy().api.read = ALF_CONTEXT+'/srcUrl/main/master?all=false';
		prototypeStore.getProxy().extraParams = {
			p1 : "type='PTT'",
			orderBy : 'code',
			all : true
		}
		prototypeStore.load();
		
		var lbw = 180;
		
		Ext.applyIf(me, {
			items:[{
				xtype:'container',
				layout:'hbox',
				margin:"5 0 0 10",
				anchor:"-10",
				items:[{
					xtype:'combo',
					name:'objectiveType',
					fieldLabel:mandatoryLabel('มีความประสงค์ที่จะขออนุมัติ'),
			    	displayField:'name',
			    	valueField:'id',
			        emptyText : "โปรดเลือก",
			        store: store,
			        queryMode: 'local',
			        typeAhead:true,
			        multiSelect:false,
			        forceSelection:true,
					width:280,
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
					labelWidth:110,
					margin:"0 0 0 15",
					flex:1,
					allowBlank:false,
					value:replaceIfNull(me.rec.objective, null),
					maxLength:255
				}]
			},{
				xtype:'textfield',
				name:'reason',
				fieldLabel:mandatoryLabel('เหตุผล / ความจำเป็น'),
				labelWidth:lbw,
				margin:"5 0 0 10",
				anchor:"-10",
				value:replaceIfNull(me.rec.reason, null),
				allowBlank:false,
				maxLength:255
			},{
				xtype:'container',
				border:0,
				layout:'hbox',
				items:[{
					xtype:'combo',
					name:'currency',
					fieldLabel:mandatoryLabel('สกุลเงิน'),
			    	displayField:'name',
			    	valueField:'id',
			        emptyText : "โปรดเลือก",
			        store: currencyStore,
			        queryMode: 'local',
			        typeAhead:true,
			        multiSelect:false,
			        forceSelection:true,
					width:280,
					labelWidth:lbw,
					margin:"5 0 0 10",
					allowBlank:false,
	     	        listeners: {
	    	       	   change : function(combo, newValue, oldValue, e){
							if (newValue.length == 3) {
								me.fireEvent("selectCurrency",combo, newValue.toUpperCase(), oldValue);
							}
	    	       	   }
	                },
					value:replaceIfNull(me.rec.currency, "THB")
				},{
					xtype:'textfield',
					name:'currencyRate',
					fieldLabel:mandatoryLabel('อัตราแลกเปลี่ยน'),
					labelWidth:110,
					width:180,
					margin:"5 0 0 15",
					allowBlank:false,
					value:replaceIfNull(me.rec.currency_rate, "1"),
					disabled:true
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
					width:280,
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
//			},{
//				xtype:'container',
//				margin:'5 0 0 10',
//				layout:{
//					type:'hbox',
//					align:'middle'
//				},
//				items:[{
//					xtype:'label',
//					text:'ประเภทการซื้อ:',
//					width:lbw
//				},{
//					xtype:'radio',
//					name:'isStock',
//					boxLabel:'เข้า  Stock',
//					inputValue:'1',
//					margin:'0 0 0 5',
//					checked:replaceIfNull(me.rec.is_stock, "0") == "1" 
//				},{
//					xtype:'combo',
//					name:'stockOu',
//					hideLabel:true,
//			    	displayField:'name',
//			    	valueField:'id',
//			        emptyText : "",
//			        store: stockStore,
//			        queryMode: 'local',
//			        typeAhead:true,
//			        multiSelect:false,
//			        forceSelection:true,
//					width:320,
//					margin:"5 0 0 10",
//					value:replaceIfNull(me.rec.stock_ou, null),
//					readOnly:true
//				},{
//					xtype:'radio',
//					name:'isStock',
//					boxLabel:'ไม่เข้า Stock',
//					inputValue:'0',
//					margin:'0 0 0 20',
//					checked:replaceIfNull(me.rec.is_stock, "0") == "0",
//					listeners:{
//						change:function(rad, newV) {
//							if (newV) {
//								me.fireEvent("notStock",rad);
//							}
//						}
//					}
//				}]
			},{
				xtype:'container',
				margin:'5 0 0 10',
				layout:{
					type:'hbox',
					align:'middle'
				},
				items:[{
					xtype:'label',
					text:'การจัดซื้อ/จัดจ้างครั้งนี้เป็นรายการครุภัณฑ์ต้นแบบ:',
					width:lbw
				},{
					xtype:'radio',
					name:'isPrototype',
					boxLabel:'ใช่',
					inputValue:'1',
					margin:'0 0 0 5',
					checked:replaceIfNull(me.rec.is_prototype, "0") == "1"
				},{
					xtype:'combo',
					name:'prototype',
					hideLabel:true,
			    	displayField:'name',
			    	valueField:'id',
			        emptyText : "โปรดเลือก",
			        store: prototypeStore,
			        queryMode: 'local',
			        editable:false,
			        multiSelect:false,
			        forceSelection:true,
					width:155,
					margin:"5 0 0 10",
					listeners:{
						change:function(cmd, newV, oldV) {
							me.fireEvent('selectPrototype', cmd, newV, oldV);
						}
					},
					value:replaceIfNull(me.rec.prototype, null)
				},{
					xtype:'textfield',
					name:'pttContractNo',
					fieldLabel:mandatoryLabel('เลขที่สัญญา'),
					labelWidth:90,
					width:200,
					margin:"5 0 0 10",
					disabled:true,
					value:replaceIfNull(me.rec.prototype_contract_no, null)
				},{
					xtype:'radio',
					name:'isPrototype',
					boxLabel:'ไม่ใช่',
					inputValue:'0',
					margin:'0 0 0 20',
					checked:replaceIfNull(me.rec.is_prototype, "0") == "0",
					listeners:{
						change:function(rad, newV) {
							if (newV) {
								me.fireEvent("notPrototype",rad);
							}
						}
					}
				}]
			},{
				xtype:'container',
				layout:'hbox',
				items:[{
					xtype:'label',
					text:'',
					width:lbw
				},{
					xtype:'label',
					html:'<font color="blue">ครุภัณฑ์ต้นแบบ/ส่งมอบ คือ ครุภัณฑ์ที่นำมาดัดแปลงหรือประกอบเป็นต้นแบบตามแผนงานในโครงการที่ได้รับอนุมัติ<br/>หรือ ครุภัณฑ์ที่จัดหามาเพื่อดำเนินการส่งมอบให้กับหน่วยงานอื่นๆตามเอกสารหรือสัญญาที่สำนักงานได้ลงนามไว้</font>',
					anchor:'-10',
					margin:'5 0 0 10'
				}]
			},{
				xtype:'container',
				layout:'hbox',
				anchor:'-10',
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
					fieldLabel:'ต้นทุนงาน',
					width:340,
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
			},{
				xtype:'hidden',
				name:'pcmOu',
				value:replaceIfNull(me.rec.pcm_ou, null)
//			}{
//				xtype:'combo',
//				name:'pcmOu',
//				fieldLabel:'หน่วยงานที่จัดซื้อ/จัดจ้าง',
//		    	displayField:'name',
//		    	valueField:'id',
//		        emptyText : "โปรดเลือก",
//		        store: purUnitStore,
//		        queryMode: 'local',
//		        typeAhead:true,
//		        multiSelect:false,
//		        forceSelection:true,
//				width:554,
//				margin:"5 0 0 10",
//				labelWidth:lbw,
//				value:replaceIfNull(me.rec.pcm_ou, null)
			},{
				xtype:'textarea',
				name:'location',
				fieldLabel:'สถานที่ส่งสินค้า / เบอร์โทรศัพท์ผู้ขอ',
				labelWidth:lbw,
				anchor:'-10',
				height:60,
				margin:"5 0 0 10",
				value:replaceIfNull(me.rec.location, null),
				maxLength:1000
			},{
				xtype:'container',
				layout:'hbox',
				items:[{
					xtype:'checkbox',
					name:'isAcrossBudget',
					boxLabel:'ใช้เงินงบประมาณมากกว่า 1 ปี',
					inputValue:'1',
					margin:'5 0 0 10',
					width:200,
					checked:replaceIfNull(me.rec.is_across_budget, "0") == "1",
					listeners:{
						change:function(chk, newV) {
							me.fireEvent("isAcrossBudget",chk, newV);
						}
					}
				},{
					xtype:'textfield',
					fieldLabel:mandatoryLabel('มูลค่าเงินรวม'),
					labelWidth:90,
					margin:"5 0 0 0",
					width:335,
					name:'acrossBudget',
					value:replaceIfNull(me.rec.across_budget, null),
					allowBlank:false,
					disabled:replaceIfNull(me.rec.is_across_budget, "0") != "1"
				}]
			},{
				xtype:'container',
				layout:'hbox',
				items:[{
					xtype:'checkbox',
					name:'isRefId',
					boxLabel:'PR เพิ่มเติม',
					inputValue:'1',
					margin:"5 0 0 10",
					width:194,
					checked:replaceIfNull(me.rec.is_ref_id, "0") == "1",
					listeners:{
						change:function(chk, newV) {
							me.fireEvent("isRefId",chk, newV);
						}
					}
				},{
					xtype:'trigger',
					name:'refId',
					fieldLabel:mandatoryLabel('อ้างอิง #'),
					labelWidth:66,
					margin:"5 0 0 30",
					width:312,
					triggerCls:'x-form-search-trigger',
					onTriggerClick:function(evt) {
						me.fireEvent("selectPR");
					},
					value:replaceIfNull(me.rec.ref_id, null),
					allowBlank:false,
					editable:false,
					disabled:replaceIfNull(me.rec.is_ref_id, "0") != "1"
				}]
			}]
		});		
		
	    this.callParent(arguments);
	}
    
});
