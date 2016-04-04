Ext.define('PBAdmin.controller.Main', {
    extend: 'Ext.app.Controller',
    
    refs:[{
    	ref:'adminView',
		selector:'adminView'
	}],
	
	init:function() {
		var me = this;
		
   		me.control({
   			'adminView [action=menu]': {
   				'click' : me.onClickMenu
   			}
   		});

	},
	
	onClickMenu: function (btn, evt) {
	
		var me = this;
	
		 Ext.Ajax.request({
		      url:ALF_CONTEXT+"/admin/module/list",
		      method: "GET",
		      success: function(response){
		    	  
		    	var system = Ext.decode(response.responseText);
		    	
			 	modules = system.modules;
						
				for(var i=0; i<modules.length; i++) {
					var module = modules[i];
			    	if (module.name == btn.text) {
			            me.getAdminView().setMenuItems(module.items);
			            break;
			    	}
			    }

		      },
		      failure: function(response, opts){
		          alert("failed");
		      },
		      headers: getAlfHeader()
		});
	    
	}

});
