Ext.define('PBExp.view.item.DtlDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.expBrwItemDtlDlg',
	
	initComponent: function(config) {
		var me = this;
		
		var lbw = 140;
		
		Ext.applyIf(me, {
		        renderTo : Ext.getBody(),
	            modal: true,
	            width: 500,
	            height: 150,
	            layout: 'fit',
	            resizable: false,
	            items : [{
		        	xtype : 'form',
			        itemId : 'formDetail',
			        border : 0,
			        height:110,
			        items:[{
						xtype: 'hidden',
						name: 'id',
						value : me.rec ? me.rec.get("id") : null
					},{
						xtype:'textfield',
						name:'activity',
						fieldLabel:mandatoryLabel('รายการยืมเงิน'),
				        anchor:"-10",
						labelWidth:lbw,
						margin: '10 0 0 10',
						allowBlank:false,
						value:me.rec ? me.rec.get("activity") : null,
					    listeners:{
							afterrender:function(txt) {
								Ext.defer(function(){
									txt.focus();
								},100);
							}
						}
					},{
					    xtype: 'numericfield',
					    fieldLabel : mandatoryLabel('จำนวนเงิน'), 
					    labelWidth: lbw,
//					    anchor:"-10",
						width:300,
					    hideTrigger:true,
					    name : 'amount',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    allowBlank:false,
						value : me.rec ? me.rec.get("amount") : null
					}]
	            }],
		        buttons : [{
		          text: 'บันทึก', 
	//	          disabled : true,
		          action : 'ok',
		          itemId: 'okButton',
		          iconCls:'icon_ok'
		        },{
		          text: 'ยกเลิก',
		          handler:this.destroy,
		          scope:this,
		          iconCls:'icon_no'
		        }]
		});
		
        this.callParent(arguments);
	}
});		