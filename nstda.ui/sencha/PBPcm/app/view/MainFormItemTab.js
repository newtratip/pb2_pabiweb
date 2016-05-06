Ext.define('PBPcm.view.MainFormItemTab', {
    extend: 'Ext.panel.Panel',
    alias:'widget.pcmReqItemTab',

    layout:'fit',
    autoScroll:true,

	initComponent: function(config) {
		var me = this;
		
		var columns = []
		columns.push(
	        	{
	        		xtype: 'actioncolumn',
		        	dataIndex: 'action',
		        	text: '', 
		            width: 80,
		            items: [{
		                tooltip: 'Edit', 
		                action : 'edit',
		        	    getClass: function(v) {
		        	    	return getActionIcon(v, "E", 'edit');
			            }
		            }, {
		                tooltip: 'Delete', 
		                action : 'del',
		        	    getClass: function(v) {
		        	    	return getActionIcon(v, "D", 'delete');
		        	    }
		            }]
	        	},			
				{ text: 'ค่าใช้จ่าย/ครุภัณฑ์',  dataIndex: 'isEquipment', width:130, renderer:me.equipmentRenderer, align:'center'},
				{ text: 'รายการวัสดุ',  dataIndex: 'description', flex:1},
				{ text: 'จำนวน',  dataIndex: 'quantity', width:80, align:'right', xtype: 'numbercolumn', format:'0,000'},
				{ text: 'หน่วยนับ',  dataIndex: 'unit', width:80, align:'center'},
				{ text: 'ราคาต่อหน่วย',  dataIndex: 'price', width:100, align:'right', xtype: 'numbercolumn', format:'0,000.00'},
				{ text: 'ราคาต่อหน่วย(สกุลเงินบาท)',  dataIndex: 'priceCnv', width:185, align:'right', xtype: 'numbercolumn', format:'0,000.00'},
				{ text: 'ราคารวม',  dataIndex: 'total', width:180, align:'right', xtype: 'numbercolumn', format:'0,000.00'}
		);
		
		var vatStore = Ext.create('PB.store.common.ComboBoxStore');
		vatStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/tax/list';
		vatStore.getProxy().extraParams = {
		}
		vatStore.load();
		
		var store = Ext.create('PBPcm.store.ItemGridStore');
		
		Ext.applyIf(me, {
			items:[{
				xtype:'container',
				layout:'border',
				border:0,
				items:[{
					xtype:'grid',
					region:'center',
					columns : columns,
					store: store,
				    tbar:[{
				    	xtype:'tbfill'
				    },{
		        		xtype: 'button',
		                text: "Add",
		                iconCls: "icon_add",
		                action:'addItem'
		        	}]

				},{
					xtype:'container',
					region:'south',
					layout:'border',
					height:90,
					style:'background-color:white',
					items:[{
						xtype:'container',
						region:'center',
						border:0
					},{
						xtype:'container',
						region:'east',
						layout:'vbox',
						border:0,
						items:[{
							xtype:'container',
							layout:'hbox',
							border:0,
							margin:'5 5 0 5',
							items:[{
								xtype:'label',
								text:'จำนวนเงินก่อนภาษี',
								width:114
							},{
								xtype:'label',
								name:'grossAmt',
								style:'text-align:right;',
								width:211
							}]
						},{
							xtype:'container',
							layout:'hbox',
							border:0,
							margin:'4 5 0 5',
							items:[{
								xtype:'hidden',
								name:'vat',
								value:replaceIfNull(me.rec.vat, 0)
							},{
								xtype:'combo',
								name:'vatId',
								fieldLabel:mandatoryLabel('ภาษีมูลค่าเพิ่ม'),
								labelWidth:100,
						    	displayField:'name',
						    	valueField:'id',
						        emptyText : "โปรดเลือก",
						        store: vatStore,
						        queryMode: 'local',
						        multiSelect:false,
						        forceSelection:true,
								width:220,
								margin:"3 0 0 0",
								editable:false,
								allowBlank:false,
								listeners:{
									select : function(combo, rec){
				    	       		   me.fireEvent("selectVat",combo, rec);
				    	       	    }
								},
								value:replaceIfNull(me.rec.vat_id, null)
							},{
								xtype:'label',
								name:'vatAmt',
								style:'text-align:right;',
								width:100,
								margin:'3 0 0 10'
							}]
						},{
							xtype:'container',
							layout:'hbox',
							border:0,
							margin:'5 5 0 5',
							items:[{
								xtype:'label',
								text:'จำนวนเงินรวม',
								width:110
							},{
								xtype:'label',
								name:'netAmt',
								style:'text-align:right;',
								width:215
							},{
								xtype:'hiddenfield',
								name:'total',
								value:replaceIfNull(me.rec.total, 0)
							}]
						}]
					}]
					
				}]	
			}]
		});		
		
	    this.callParent(arguments);
	    
		Ext.apply(store, {pageSize:PAGE_SIZE});
		store.load({
			params:{id:me.rec.id},
			callback:function() {
				me.fireEvent("itemStoreLoad");
			}
		});
	},
	
	equipmentRenderer:function(v) {
		return ((v == "1") ? "ครุภัณฑ์" : "ค่าใช้จ่าย");
	}
    
});
