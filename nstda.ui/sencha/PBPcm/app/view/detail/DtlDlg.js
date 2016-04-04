Ext.define('PBPcm.view.detail.DtlDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.pcmDetailDtlDlg',
	
	initComponent: function(config) {
		var me = this;
		
		var store = Ext.create('PB.store.common.ComboBoxStore');
		store.getProxy().api.read = ALF_CONTEXT+'/admin/main/product/uom/list';
		store.getProxy().extraParams = {
		}
		store.load();
		
		var lbw = 90;
		
		Ext.applyIf(me, {
		        renderTo : Ext.getBody(),
	            modal: true,
	            width: 420,
	            height: 370,
	            layout: 'fit',
	            resizable: false,
	            items : [{
		        	xtype : 'form',
			        itemId : 'formDetail',
			        border : 0,
			        items:[{
						xtype: 'hidden',
						name: 'id'
					 },{
					    xtype: 'radiogroup',
					    fieldLabel : 'ครุภัณฑ์',
					    columns:2,
					    labelWidth: lbw,
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    anchor:"-10",
					    items:[
					           {name:'type',boxLabel:'ไม่ใช่',value:'0',checked:true},
					           {name:'type',boxLabel:'ใช่',value:'1'}
					    ]
					 },{
					    xtype: 'textfield',
					    fieldLabel : 'รายการ', 
					    labelWidth: lbw,
					    anchor:"-10",
					    hideTrigger:true,
					    name : 'name',
					    msgTarget: 'side',
					    margin: '10 0 0 10'
					},{
					    xtype: 'textfield',
					    fieldLabel : 'จำนวน', 
					    labelWidth: lbw,
					    anchor:"-10",
					    hideTrigger:true,
					    name : 'amount',
					    msgTarget: 'side',
					    margin: '10 0 0 10'
					},{
						xtype:'combo',
						fieldLabel:'หน่วยนับ',
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : "โปรดเลือก",
				        store: store,
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
				        anchor:"-10",
						labelWidth:lbw,
						margin: '10 0 0 10',
				        listConfig : {
					    	resizable:true,
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
						}			
					},{
					    xtype: 'textfield',
					    fieldLabel : 'ราคาต่อหน่วย', 
					    labelWidth: lbw,
					    anchor:"-10",
					    hideTrigger:true,
					    name : 'price',
					    msgTarget: 'side',
					    margin: '10 0 0 10'
					}],
			        buttons : [{
			          text: 'บันทึก', 
//			          disabled : true,
			          action : 'ok',
			          itemId: 'okButton'
			        },{
			          text: 'ยกเลิก',
			          handler:this.destroy,
			          scope:this
			        }]
	            }]
		});
		
        this.callParent(arguments);
	}
});		