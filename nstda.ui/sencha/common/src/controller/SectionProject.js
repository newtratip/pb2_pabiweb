Ext.define('PB.controller.common.SectionProject', {
    extend: 'Ext.app.Controller',

    refs:[{
    	ref:'dlg',
    	selector:'searchSectionProjectDlg'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'button': {
				searchSectionProject : me.search
			},
			'textfield': {
				searchSectionProject : me.search
			},
			'searchSectionProjectDlg button[action=ok]':{
				confirmSectionProject:me.ok
			}
			
		});
	},
	
	getGrid:function(sender) {
		return sender.up("window").down("grid");
	},
	
	getSearchTerm:function(sender) {
		return sender.up("container").down("field[itemId=searchTerm]");
	},
	
	getType:function(sender) {
		return sender.up("window").down("field[name=type]");
	},
	
    search : function(sender) {
		var me = this;
	
		var store = me.getGrid(sender).getStore();
		
		store.getProxy().extraParams = {
			s:me.getSearchTerm(sender).getValue(),
			t:me.getType(sender).getGroupValue()
		}
		
		store.load();
	},
	
	ok:function(btn) {
		var me = this;
		
		var ids = getRadioValue("id").split("|");
		var type = me.getType(btn).getGroupValue();
		var typeName = (type == "P") ? "โครงการ" : "หน่วยงาน" ;
		
		me.getDlg().callback(ids, type, typeName);
		me.getDlg().close();
	}

});