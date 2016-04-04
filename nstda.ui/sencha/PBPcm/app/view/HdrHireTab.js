Ext.define('PBPcm.view.HdrHireTab', {
    extend: 'Ext.form.Panel',
    alias:'widget.pcmReqHdrHireTab',
    
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
		
		var trainStore = Ext.create('PB.store.common.ComboBoxStore');
		trainStore.getProxy().api.read = ALF_CONTEXT+'/srcUrl/main/master?all=false';
		trainStore.getProxy().extraParams = {
			p1 : "type='TR'",
			orderBy : 'code',
			all : true
		}
		trainStore.load();
		
		var purUnitStore = Ext.create('PB.store.common.ComboBoxStore');
		purUnitStore.getProxy().api.read = ALF_CONTEXT+'/srcUrl/main/master?all=false';
		purUnitStore.getProxy().extraParams = {
			p1 : "type='PU'",
			orderBy : 'code',
			all : true
		}
		purUnitStore.load();
		
		
		var lbw = 170;
		
		Ext.applyIf(me, {
			items:[{
				xtype:'container',
				layout:'hbox',
				margin:"5 0 0 10",
				anchor:"-10",
				items:[{
					xtype:'combo',
					name:'objectiveType',
					fieldLabel:'มีความประสงค์ที่จะขออนุมัติ',
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
					}
				},{
					xtype:'textfield',
					name:'objective',
					fieldLabel:'วัตถุประสงค์',
					labelWidth:100,
					margin:"0 0 0 15",
					flex:1
				}]
			},{
				xtype:'textfield',
				name:'reason',
				fieldLabel:'เหตุผล / ความจำเป็น',
				labelWidth:lbw,
				margin:"5 0 0 10",
				anchor:"-10"
			},{
				xtype:'container',
				border:0,
				layout:'hbox',
				items:[{
					xtype:'combo',
					name:'currency',
					fieldLabel:'สกุลเงิน',
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
					value:'THB',
	     	        listeners: {
	    	       	   change : function(combo, newValue, oldValue, e){
	    	       		   me.fireEvent("selectCurrency",combo, newValue, oldValue);
	    	       	   }
	                }
				},{
					xtype:'textfield',
					name:'currencyRate',
					fieldLabel:'อัตราแลกเปลี่ยน',
					labelWidth:100,
					width:180,
					margin:"5 0 0 15"
				}]
			},{
				xtype:'container',
				layout:'hbox',
				anchor:'-10',
				items:[{
					xtype:'trigger',
					name:'budgetCc',
					fieldLabel:'งบประมาณที่ใช้',
					width:280,
					labelWidth:lbw,
					margin:"5 0 0 10",
					triggerCls:'x-form-search-trigger',
					onTriggerClick:function(evt) {
						Ext.Msg.alert("Status","Search");
					}
				},{
					xtype:'textfield',
					name:'budgetCcName',
					hideLabel:true,
					flex:1,
					margin:'5 0 0 10'
				}]
			},{
				xtype:'container',
				margin:'5 0 0 10',
				layout:{
					type:'hbox',
					align:'middle'
				},
				items:[{
					xtype:'label',
					text:'ประเภทการซื้อ:',
					width:lbw
				},{
					xtype:'radio',
					name:'toStock',
					boxLabel:'เข้า  Stock',
					inputValue:'1',
					margin:'0 0 0 5'
				},{
					xtype:'combo',
					name:'stockOu',
					hideLabel:true,
			    	displayField:'name',
			    	valueField:'id',
			        emptyText : "โปรดเลือก",
			        store: stockStore,
			        queryMode: 'local',
			        typeAhead:true,
			        multiSelect:false,
			        forceSelection:true,
					width:280,
					margin:"5 0 0 10"
				},{
					xtype:'radio',
					name:'toStock',
					boxLabel:'ไม่เข้า Stock',
					inputValue:'0',
					margin:'0 0 0 20',
					checked:true
				}]
			},{
				xtype:'radiogroup',
				fieldLabel:'การจัดซื้อ/จัดจ้างครั้งนี้เป็นรายการวัสดุต้นแบบ',
				labelWidth:lbw,
				width:300,
				columns:2,
				margin:"5 0 0 10",
				items:[
				       {boxLabel: 'ใช่', name: 'prototype', inputValue: '1', checked: true},
				       {boxLabel: 'ไม่ใช่', name: 'prototype', inputValue: '0'}
				]
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
				xtype:'combo',
				name:'event',
				fieldLabel:'จัดฝึกอบรม',
		    	displayField:'name',
		    	valueField:'id',
		        emptyText : "โปรดเลือก",
		        store: trainStore,
		        queryMode: 'local',
		        typeAhead:true,
		        multiSelect:false,
		        forceSelection:true,
				width:554,
				margin:"5 0 0 10",
				labelWidth:lbw
			},{
				xtype:'combo',
				name:'pcmOu',
				fieldLabel:'หน่วยงานที่จัดซื้อ/จัดจ้าง',
		    	displayField:'name',
		    	valueField:'id',
		        emptyText : "โปรดเลือก",
		        store: purUnitStore,
		        queryMode: 'local',
		        typeAhead:true,
		        multiSelect:false,
		        forceSelection:true,
				width:554,
				margin:"5 0 0 10",
				labelWidth:lbw
			},{
				xtype:'textarea',
				name:'location',
				fieldLabel:'สถานที่ส่งสินค้า / เบอร์โทรศัพท์ผู้ขอ',
				labelWidth:lbw,
				anchor:'-10',
				height:60,
				margin:"5 0 0 10"
			},{
				xtype:'container',
				layout:'hbox',
				items:[{
					xtype:'checkbox',
					name:'isAcrossBudget',
					boxLabel:'',
					inputValue:'1',
					margin:'5 0 0 10',
					width:20
				},{
					xtype:'textfield',
					fieldLabel:'ใช้เงินงบประมาณมากกว่า 1 ปี มูลค่าเงินรวม',
					labelWidth:lbw-20,
					margin:"5 0 0 0",
					width:150+lbw,
					name:'acrossBudget'
				}]
			},{
				xtype:'container',
				layout:'hbox',
				items:[{
					xtype:'checkbox',
					name:'isRefId',
					boxLabel:'PR เพิ่มเติม',
					inputValue:'1',
					margin:"5 0 0 10"
				},{
					xtype:'trigger',
					name:'refId',
					fieldLabel:'อ้างอิง #',
					labelWidth:60,
					margin:"5 0 0 20",
					width:445,
					triggerCls:'x-form-search-trigger',
					onTriggerClick:function(evt) {
						Ext.Msg.alert("Status","Search");
					}
				}]
			}]
		});		
		
	    this.callParent(arguments);
	}
    
});
