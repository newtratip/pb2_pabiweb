Ext.define('PB.view.common.usergroup.SearchDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.usergroupSearchDlg',
	
	initComponent: function(config) {
		
		var me = this;
		
		Ext.applyIf(me, {
            modal: true,
            width: 500,
            height: 400,
            layout: 'fit',
            resizable: false,
            items : [{
            	xtype:'usergroupSearchGrid',
            	parentGrid:me.parentGrid
            }]
		});
		
        this.callParent(arguments);
	}
});		