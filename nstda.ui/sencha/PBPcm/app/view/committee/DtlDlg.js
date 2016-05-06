Ext.define('PBPcm.view.committee.DtlDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.pcmCommitteeDtlDlg',
	
	initComponent: function(config) {
		var me = this;
		
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
					    xtype: 'textfield',
					    fieldLabel : 'ชื่อ', 
					    labelWidth: 70,
					    anchor:"-10",
					    name : 'first_name',
					    msgTarget: 'side',
					    margin: '10 0 0 10'
					},{
					    xtype: 'textfield',
					    fieldLabel : 'นามสกุล', 
					    labelWidth: 70,
					    anchor:"-10",
					    name : 'last_name',
					    msgTarget: 'side',
					    margin: '10 0 0 10'
					},{
					    xtype: 'textfield',
					    fieldLabel: 'ตำแหน่ง',
					    labelWidth: 70,
					    anchor:"-10",
					    name : 'position',
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