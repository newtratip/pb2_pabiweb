Ext.define('PBExpUse.view.MainFormVoyagerTab', {
    extend: 'Ext.panel.Panel',
    alias:'widget.expUseVoyagerTab',

    layout:'fit',
    autoScroll:true,

	initComponent: function(config) {
		var me = this;
		
		var courseStore = Ext.create('PB.store.common.ComboBoxStore',{autoLoad:false});
		courseStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/costControl/list';
		courseStore.getProxy().extraParams = {
			type:1
		}
		courseStore.load();
		
		var estore = Ext.create('PBExpUse.store.VoyagerGridStore');
		estore.getProxy().extraParams = {
			id:me.rec.id,
			type:'E'
		}
		estore.load();

		var ostore = Ext.create('PBExpUse.store.VoyagerGridStore');
		ostore.getProxy().extraParams = {
		    id:me.rec.id,
		    type:'O'
		}
		ostore.load();
		
		var ecolumns = []
		ecolumns.push(
	        	{
	        		xtype: 'actioncolumn',
		        	dataIndex: 'action',
		        	text: '', 
		            width: 40,
		            align:'center',
		            items: [{
//		                tooltip: 'Edit', 
//		                action : 'editEmp',
//		        	    getClass: function(v) {
//		        	    	return getActionIcon(v, "E", 'edit');
//			            }
//		            }, {
		                tooltip: 'Delete', 
		                action : 'delEmp',
		        	    getClass: function(v) {
		        	    	return getActionIcon(v, "D", 'delete');
		        	    }
		            }]
	        	},
	        	{ text: 'ลำดับ',  xtype: 'rownumberer', width:60},
				{ text: 'รหัสพนักงาน',  dataIndex: 'code', width:120},
				{ text: 'ชื่อ - นามสกุล',  dataIndex: 'name', flex:1},
				{ text: 'หน่วยงาน / โครงการ',  dataIndex: 'unit_type', flex:1},
				{ text: 'ตำแหน่ง',  dataIndex: 'position', flex:1}
		);
		
		var ocolumns = []
		ocolumns.push(
	        	{
	        		xtype: 'actioncolumn',
		        	dataIndex: 'action',
		        	text: '', 
		            width: 80,
		            align:'center',
		            items: [{
		                tooltip: 'Edit', 
		                action : 'editOth',
		        	    getClass: function(v) {
		        	    	return getActionIcon(v, "E", 'edit');
			            }
		            }, {
		                tooltip: 'Delete', 
		                action : 'delOth',
		        	    getClass: function(v) {
		        	    	return getActionIcon(v, "D", 'delete');
		        	    }
		            }]
	        	},
	        	{ text: 'ลำดับ',  xtype: 'rownumberer', width:60},
				{ text: 'ชื่อ - นามสกุล',  dataIndex: 'name', flex:1},
				{ text: 'ตำแหน่ง',  dataIndex: 'position', flex:1}
		);
		
		var c1 = {};
		var c2 = {};
		if (me.rec.cost_control_type_id=='0') {
			c1.from = cvtDateValue(me.rec.cost_control_from);
			c1.to = cvtDateValue(me.rec.cost_control_to);
		} else 
		if (me.rec.cost_control_type_id=='1') {
			c2.from = cvtDateValue(me.rec.cost_control_from);
			c2.to = cvtDateValue(me.rec.cost_control_to);
		}
		
		Ext.applyIf(me, {
			items:[{
				xtype:'container',
				layout:'border',
				items:[{
					region:'north',
					xtype:'panel',
					height:125,
					split:true,
					title:'ประเภทค่าใช้จ่าย',
					items:[{
						xtype:'container',
						layout:'hbox',
						margin:'5 0 0 0',
						items:[{
							xtype:'radio',
							name:'costControlTypeId',
							boxLabel:'จัดฝึกอบรม',
							inputValue:'0',
							margin:'5 0 0 10',
							width:210,
							checked:replaceIfNull(me.rec.cost_control_type_id, "0") == "0",
							listeners:{
								change:function(rad, newV, oldV) {
									if (newV) {
										me.fireEvent("selectCostControl",rad, "0");
									}
								}
							}
						},{
							xtype:'combo',
							name:'costControlId',
							hideLabel:true,
							fieldLabel:'จัดฝึกอบรม',
					    	displayField:'name',
					    	valueField:'id',
					        emptyText : "โปรดเลือก",
					        store: courseStore,
					        queryMode: 'local',
					        multiSelect:false,
					        forceSelection:true,
							flex:1,
							margin:'5 0 0 10',
							typeAhead:true,
							editable:true,
							allowBlank:false,
							value:me.rec.cost_control_id!=null ? me.rec.cost_control_id : null,
							listeners:{
								beforequery : function(qe) {
									qe.query = new RegExp(qe.query, 'i');
					//				qe.forceAll = true;
								}
							}
						},{
							xtype:'datefield',
							name:'cc1From',
							itemId:'cc1From',
							fieldLabel:mandatoryLabel('ระหว่างวันที่'),
							labelWidth:80,
							width:205,
							margin:'5 5 0 20',
							format:'d/m/Y',
							value:replaceIfNull(c1.from, null),
							vtype: 'daterange',
							endDateField: 'cc1To',
							allowBlank:false,
							listeners : {
								change : function(d, newV, oldV) {
					                var end = d.up('form').down('#' + d.endDateField);
					                end.setMinValue(newV);
					                end.validate();
					                d.dateRangeMin = newV;
								}
							}
						},{
							xtype:'datefield',
							name:'cc1To',
							itemId:'cc1To',
							fieldLabel:mandatoryLabel('ถึงวันที่'),
							labelWidth:55,
							width:180,
							margin:'5 10 0 10',
							format:'d/m/Y',
							value:replaceIfNull(c1.to, null),
							vtype: 'daterange',
							startDateField: 'cc1From',
							allowBlank:false,
							listeners : {
								change : function(d, newV, oldV) {
					                var start = d.up('form').down('#' + d.startDateField);
					                start.setMaxValue(newV);
					                start.validate();
					                d.dateRangeMax = newV;
								}
							}
						}]
					},{
						xtype:'container',
						layout:'hbox',
						items:[{
							xtype:'radio',
							name:'costControlTypeId',
							boxLabel:'เข้ารับการอบรม/เดินทางสัมมนา',
							inputValue:'1',
							margin:'5 0 0 10',
							width:210,
							checked:replaceIfNull(me.rec.cost_control_type_id, "0") == "1", 
							listeners:{
								change:function(rad, newV, oldV) {
									if (newV) {
										me.fireEvent("selectCostControl",rad, "1");
									}
								}
							}
						},{
							xtype:'textfield',
							name:'costControl',
							hideLabel:true,
							fieldLabel:'เข้ารับการอบรม/เดินทางสัมมนา',
							flex:1,
							margin:'5 0 0 10',
							value:replaceIfNull(me.rec.cost_control, null),
							allowBlank:false,
							disabled:true
						},{
							xtype:'datefield',
							name:'cc2From',
							itemId:'cc2From',
							fieldLabel:mandatoryLabel('ระหว่างวันที่'),
							labelWidth:80,
							width:205,
							margin:'5 5 0 20',
							format:'d/m/Y',
							value:replaceIfNull(c2.from, null),
							vtype: 'daterange',
							endDateField: 'cc2To',
							allowBlank:false,
							disabled:true,
							listeners : {
								change : function(d, newV, oldV) {
					                var end = d.up('form').down('#' + d.endDateField);
					                end.setMinValue(newV);
					                end.validate();
					                d.dateRangeMin = newV;
								}
							}
						},{
							xtype:'datefield',
							name:'cc2To',
							itemId:'cc2To',
							fieldLabel:mandatoryLabel('ถึงวันที่'),
							labelWidth:55,
							width:180,
							margin:'5 10 0 10',
							format:'d/m/Y',
							value:replaceIfNull(c2.to, null),
							vtype: 'daterange',
							startDateField: 'cc2From',
							allowBlank:false,
							disabled:true,
							listeners : {
								change : function(d, newV, oldV) {
					                var start = d.up('form').down('#' + d.startDateField);
					                start.setMaxValue(newV);
					                start.validate();
					                d.dateRangeMax = newV;
								}
							}
						}]
					},{
						xtype:'radio',
						name:'costControlTypeId',
						boxLabel:'ค่าใช้จ่ายอื่นๆ',
						inputValue:'2',
						margin:'5 0 0 10',
						checked:replaceIfNull(me.rec.cost_control_type_id, "0") == "2", 
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectCostControl",rad, "2");
								}
							}
						}
					}]
				},{
					title:'รายชื่อผู้ร่วมเดินทาง เข้ารับการอบรม / เดินทางสัมมนา (พนักงาน)',
					xtype:'grid',
					itemId:'empGrid',
					region:'center',
					columns : ecolumns,
					store : estore,
				    header:{
						titlePosition:0,
						items:[{
					    	xtype:'tbfill'
					    },{
			        		xtype: 'button',
			                text: "Add",
			                iconCls: "icon_add",
			                action:'addEmp'
			        	}]
				    }
				},{
					split:true,
					height:200,
					title:'รายชื่อผู้ร่วมเดินทาง เข้ารับการอบรม / เดินทางสัมมนา (บุคคลภายนอก)',
					xtype:'grid',
					itemId:'othGrid',
					region:'south',
					columns : ocolumns,
					store : ostore,
				    header:{
						titlePosition:0,
						items:[{
					    	xtype:'tbfill'
					    },{
			        		xtype: 'button',
			                text: "Add",
			                iconCls: "icon_add",
			                action:'addOth'
			        	}]
				    }
				}]
			}]
		});
		
	    this.callParent(arguments);
	},

	listeners:{
		added:function() {
			var me = this;
			
			me.fireEvent("selectCostControl", me.down("radio[name=costControlTypeId]"), replaceIfNull(me.rec.cost_control_type_id, "0"));
		}
	}	
    
});
