Ext.define('PBPcm.view.item.DtlDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.pcmItemDtlDlg',
	
	initComponent: function(config) {
		var me = this;
		
		var store = Ext.create('PB.store.common.ComboBoxStore');
		store.getProxy().api.read = ALF_CONTEXT+'/admin/main/product/uom/list';
		store.getProxy().extraParams = {
		}
		store.load();
		
		var lbw = 100;
		
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
						 xtype:'hidden',
						 name:'isEqmt',
						 value:'0'
//					 },{
//					    xtype: 'radiogroup',
//					    itemId:'isEqmt',
//					    fieldLabel : 'ครุภัณฑ์',
//					    columns:2,
//					    labelWidth: lbw,
//					    msgTarget: 'side',
//					    margin: '10 0 0 10',
//					    anchor:"-10",
//					    items:[
//					           {name:'isEqmt',boxLabel:'ไม่ใช่',inputValue:'0',checked:true},
//					           {name:'isEqmt',boxLabel:'ใช่',inputValue:'1'}
//					    ]
					 },{
					    xtype: 'textfield',
					    fieldLabel : mandatoryLabel('รายการ'), 
					    labelWidth: lbw,
					    anchor:"-10",
					    hideTrigger:true,
					    name : 'desc',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    allowBlank:false
					},{
					    xtype: 'numericfield',
					    fieldLabel : mandatoryLabel('จำนวน'), 
					    labelWidth: lbw,
					    anchor:"-10",
					    hideTrigger:true,
					    name : 'qty',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    allowBlank:false
					},{
						xtype:'combo',
						name:'unit',
						fieldLabel:mandatoryLabel('หน่วยนับ'),
				    	displayField:'name',
				    	valueField:'name',
				        emptyText : "โปรดเลือก",
				        store: store,
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
				        anchor:"-10",
						labelWidth:lbw,
						margin: '10 0 0 10',
						allowBlank:false,
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
					    xtype: 'numericfield',
					    fieldLabel : mandatoryLabel('ราคาต่อหน่วย'), 
					    labelWidth: lbw,
					    anchor:"-10",
					    hideTrigger:true,
					    name : 'prc',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    allowBlank:false
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