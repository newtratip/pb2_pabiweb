Ext.define('PB.controller.common.SectionProjectUser', {
    extend: 'Ext.app.Controller',

    refs:[{
    	ref:'dlg',
    	selector:'searchSectionProjectUserDlg'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'searchSectionProjectUserDlg': {
				selectRadio : me.selectRadio
			},
			'searchSectionProjectUserDlg button': {
				searchUser : me.search
			},
			'searchSectionProjectUserDlg textfield': {
				searchUser : me.search
			},
			'searchSectionProjectUserDlg button[action=ok]':{
				confirm:me.ok
			}
			
		});
	},
	
	getGrid:function(sender) {
		return sender.up("window").down("grid");
	},
	
	getType:function(sender) {
		return sender.up("window").down("field[name=type]");
	},	
	
	getCode:function(sender) {
		return sender.up("window").down("field[itemId=code]");
	},
	
	getSearchTerm:function(sender) {
		return sender.up("container").down("field[itemId=searchTerm]");
	},
	
    search : function(sender) {
		var me = this;
	
		var store = me.getGrid(sender).getStore();
		
		var c = me.getCode(sender).getValue();
		var pos = c.indexOf("|");
		
		store.getProxy().extraParams = {
            t:me.getType(sender).getGroupValue(),
            c:c.substring(0,pos),
			s:me.getSearchTerm(sender).getValue()
		}
		
		store.load();
	},	
	
	ok:function(btn) {
		var me = this;
		
		var grid = me.getGrid(btn);
		var selModel = grid.getSelectionModel();
		var selected = selModel.selected;

		me.getDlg().callback(selected.items);
		me.getDlg().close();
	},
	
	selectRadio:function(rad, v) {
		var me = this;
		var type = v ? "U" : "P";

		var store = me.getCode(rad).getStore();
		store.getProxy().extraParams = {
			t:type
		}
		store.load();
	}

});