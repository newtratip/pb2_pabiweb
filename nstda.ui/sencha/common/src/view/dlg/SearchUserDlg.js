Ext.define('PB.view.common.SearchUserDlg', {
	extend : 'Ext.window.Window',
    alias:'widget.searchUserDlg',
    
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
		        itemId : 'searchPR',
		        border : 0,
		        items:[{
		        	xtype:'textfield',
		        	fieldLabel:'PR NO.',
		        	margin:'5 0 0 10'
		        },{
		        	xtype:'textfield',
		        	fieldLabel:'เรื่อง',
		        	margin:'5 0 0 10'
		        },{
		        	xtype:'button',
		        	text:'ค้นหา',
		        	margin:'5 0 0 10'
		        }],
		        buttons : [{
		          text: 'เลือก', 
		          action : 'ok',
		          iconCls:'icon_ok',
		          listeners: {
		               click: function(){
		                	this.fireEvent("confirmUser", this);
		               }
		          }
		        },{
		          text: 'ยกเลิก',
		          iconCls:'icon_no',
		          handler:this.destroy,
		          scope:this
		        }]
	        }]
		});
		
        this.callParent(arguments);
		
	}
});