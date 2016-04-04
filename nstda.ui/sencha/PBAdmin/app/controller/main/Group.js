Ext.define('PBAdmin.controller.main.Group', {
    extend: 'Ext.app.Controller',

    refs:[{
        ref:'grid',
        selector: 'adminGroupGrid'
    }],
      
    init:function() {
	
		var me = this;
		me.control({
			'adminGroupGrid' :{
				addGroup : me.addGroup
			}
		
		});
	},
	
	addGroup : function(grid, rec){
	
		grid.parentGrid.getStore().add(rec);
		grid.getStore().remove(rec);
		
		
	}
	
	
		


});
