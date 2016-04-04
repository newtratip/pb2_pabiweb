Ext.define('PB.controller.common.UserGroup', {
    extend: 'Ext.app.Controller',

    init:function() {
		var me = this;
		
		me.control({
			'usergroupGrid [action=openSearchUserGroupDlg]': {
				click : me.openSearchDlg
			},
			'usergroupGrid': {
   				delUserGroup : me.del
			},
			'usergroupSearchGrid': {
				searchUserGroup : me.search,
				addUserGroup : me.add
			}
		});
	},
	
	getGrid:function(btn) {
		return btn.up("usergroupGrid");
	},
	
	del : function(grid, rowIndex, colIndex){
		var store = grid.getStore();
		var rec = store.getAt(rowIndex);	
		store.remove(rec);
	},

	openSearchDlg:function(btn) {
		var grid = this.getGrid(btn);
		
		var dlg = Ext.create('PB.view.common.usergroup.SearchDlg',{
    		parentGrid:grid,
    		title:grid.tbTitle
    	});
    	dlg.show();
		
	},
	
    search : function(searchGrid) {
		var grid = searchGrid.parentGrid;
		
		var searchText = searchGrid.down("field[name=search]").getValue();
    	var type = searchGrid.down("field[name=type]").getGroupValue();
		
		var parentStore = grid.getStore();

		var data = [];
		parentStore.each(function(rec){
			data.push(rec.data);
		});	
		var notIn = Ext.JSON.encode(data);
		
    	var searchStore = searchGrid.getStore();
    	
    	searchStore.getProxy().extraParams = {
    			s:searchText,
    			t:type,
    			notIn:notIn
    	}
    	
    	searchStore.load();
	},

	add : function(searchGrid, rec) {
		var grid = searchGrid.parentGrid;
		rec.action = "D";
		grid.getStore().add(rec);
		
		this.search(searchGrid);
	}

});