Ext.define('PBPcm.view.DtlTab', {
    extend: 'Ext.panel.Panel',
    alias:'widget.pcmReqDtlTab',

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
		            width: 130,
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
				{ text: 'ค่าใช้จ่าย/ครุภัณฑ์',  dataIndex: 'type', width:130},
				{ text: 'รายการวัสดุ',  dataIndex: 'desc', flex:1},
				{ text: 'จำนวน',  dataIndex: 'amt', width:80},
				{ text: 'หน่วยนับ',  dataIndex: 'unit', width:80},
				{ text: 'ราคาต่อหน่วย',  dataIndex: 'price', width:100, align:'right'},
				{ text: 'ราคาต่อหน่วย(สกุลเงินบาท)',  dataIndex: 'priceBaht', width:185, align:'right'},
				{ text: 'ราคารวม',  dataIndex: 'total', width:180, align:'right'}
		);
		
		var store = Ext.create('PBPcm.store.DtlGridStore');
		
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
		                action:'addDtl'
//		                listeners: {
//		                    click: function(){
//				    			alert("Add");
//		                    }
//		                }
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
								style:'text-align:right;',
								width:100
							}]
						},{
							xtype:'container',
							layout:'hbox',
							border:0,
							margin:'4 5 0 5',
							items:[{
								xtype:'checkbox',
								boxLabel:'ภาษีมูลค่าเพิ่ม',
								inputValue:'t',
								width:114
							},{
								xtype:'label',
								style:'text-align:right;',
								width:100,
								margin:'3 0 0 0'
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
								style:'text-align:right;',
								width:100
							},{
								xtype:'hiddenfield',
								name:'total',
								value:'0'
							}]
						}]
					}]
					
				}]	
			}]
		});		
		
	    this.callParent(arguments);
	    
		Ext.apply(store, {pageSize:PAGE_SIZE});
		store.load({params:{id:'1'}});	    
	}
    
});
