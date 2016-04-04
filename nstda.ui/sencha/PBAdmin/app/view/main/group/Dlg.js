/*
 * - Grid
 */
Ext.define('PBAdmin.view.main.group.Dlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.adminGroupDlg',
	
	initComponent: function(config) {
	
		var me = this;
		
		Ext.applyIf(me, {
		        renderTo : Ext.getBody(),
	            modal: true,
	            width: 500,
	            height: 330,
	            layout: 'fit',
	            resizable: false,
	            items : [{
	            	items:[{
			        	xtype : 'form',
				        itemId : 'formSettings',
				        border : 0
			        }],
			
			        buttons : [{
			          text: 'Close',
			          text_TH: 'เสร็จสิ้น',
			          handler:this.destroy,
			          scope:this
			        }]
	            }]
		});
		
        this.callParent(arguments);
	}
});		