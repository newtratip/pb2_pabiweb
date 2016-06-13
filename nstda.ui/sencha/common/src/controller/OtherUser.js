Ext.define('PB.controller.common.OtherUser', {
    extend: 'Ext.app.Controller',

    refs:[{
    	ref:'dlg',
    	selector:'searchOtherUserDlg'
    },{
    	ref:'form',
		selector:'#formDetail'
	},{
    	ref:'txtName',
    	selector:'searchOtherUserDlg field[name=name]'
    },{
    	ref:'txtPosition',
    	selector:'searchOtherUserDlg field[name=position]'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'searchOtherUserDlg button[action=ok]':{
				confirm:me.ok
			}
			
		});
	},
	
	ok:function(btn) {
		var me = this;
		
		if (validForm(me.getForm())) {
			var name = me.getTxtName().getValue();
			var position = me.getTxtPosition().getValue();
			
			me.getDlg().callback(me.getDlg().rec ? me.getDlg().rec.id : null, name, position);
			me.getDlg().close();
		}
	}

});