Ext.define('PBExp.view.MainFormItemTab', {
    extend: 'Ext.panel.Panel',
    alias:'widget.expBrwItemTab',

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
		            align:'center',
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
				{ text: PBExp.Label.i.actGrp,  dataIndex: 'actGrpName', flex:1},
				{ text: PBExp.Label.i.act,  dataIndex: 'actName', flex:1},
				{ text: PBExp.Label.i.desc,  dataIndex: 'activity', flex:1},
				{ text: PBExp.Label.i.cond,  dataIndex: 'condition1', flex:1, renderer:function(v){return (v ? v :"")}},
				{ text: PBExp.Label.i.amt,  dataIndex: 'amount', width:180, align:'right', xtype: 'numbercolumn', format:'0,000.00'}
		);
		
//		var vatStore = Ext.create('PB.store.common.ComboBoxStore');
//		vatStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/tax/list';
//		vatStore.getProxy().extraParams = {
//		}
//		vatStore.load();
		
//		var store = Ext.create('PBExpUse.store.ItemGridStore');
		var store = Ext.create('PBExp.store.ItemGridStore',{storeId:'expBrwItemGridStore',autoLoad:false});
		store.getProxy().extraParams = {
			id : me.rec.id
		}
		
		var lbw = parseInt(PBExp.Label.i.lbw);
		
		Ext.applyIf(me, {
			items:[{
				xtype:'container',
				layout:'border',
				border:0,
				items:[{
					region:'north',
					xtype:'form',
					height:110,
					items:[{
						xtype:'textfield',
						name:'reason',
						fieldLabel:PBExp.Label.i.reason,
						labelWidth:lbw,
						margin:"5 0 0 10",
						width:WIDTH-40,
						allowBlank:true,
						value:replaceIfNull(me.rec.reason, null)
					},{
						xtype:'textareafield',
						name:'note',
						fieldLabel:PBExp.Label.i.note,
						labelWidth:lbw,
						margin:"5 0 0 10",
						width:WIDTH-40,
						height:70,
						allowBlank:true,
						maxLength:255,
						value:replaceIfNull(me.rec.note, null)
					}]
				},{
					region:'center',
					margin:'0 0 0 0',
					xtype:'grid',
					itemId:'itemGrid',
					columns:columns,
					store:store,
				    header:{
						titlePosition:0,
						items:[{
					    	xtype:'tbfill'
					    },{
			        		xtype: 'button',
			                text: PB.Label.m.add,
			                iconCls: "icon_add",
			                action:'addItem'
			        	}]
				    }
				},{
					xtype:'container',
					region:'south',
					layout:'border',
					height:30,
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
//							xtype:'container',
//							layout:'hbox',
//							border:0,
//							margin:'5 5 0 5',
//							items:[{
//								xtype:'label',
//								text:PBExpUse.Label.i.gross,
//								width:114
//							},{
//								xtype:'label',
//								name:'grossAmt',
//								style:'text-align:right;',
//								width:211
//							}]
//						},{
//							xtype:'container',
//							layout:'hbox',
//							border:0,
//							margin:'4 5 0 5',
//							items:[{
//								xtype:'hidden',
//								name:'vat',
//								value:replaceIfNull(me.rec.vat, 0)
//							},{
//								xtype:'combo',
//								name:'vatId',
//								fieldLabel:mandatoryLabel(PBExp.Label.i.vat),
//								labelWidth:100,
//						    	displayField:'name',
//						    	valueField:'id',
//						        emptyText : PB.Label.m.select,
//						        store: vatStore,
//						        queryMode: 'local',
//						        multiSelect:false,
//						        forceSelection:true,
//								width:220,
//								margin:"3 0 0 0",
//								editable:false,
//								allowBlank:false,
//								listeners:{
//									select : function(combo, rec){
//				    	       		   me.fireEvent("selectVat",combo, rec);
//				    	       	    }
//								},
//								value:me.rec.vat_id!=null ? me.rec.vat_id : null
//							},{
//								xtype:'label',
//								name:'vatAmt',
//								style:'text-align:right;',
//								width:100,
//								margin:'3 0 0 10'
//							}]
//						},{
							xtype:'container',
							layout:'hbox',
							border:0,
							margin:'5 5 0 5',
							items:[{
								xtype:'label',
								text:PBExp.Label.i.total,
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
	}
	
});
