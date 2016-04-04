Ext.define('PBAdmin.Application', {
    name: 'PBAdmin',

    extend: 'Ext.app.Application',

    requires: [
        'Ext.util.Cookies',
        'Ext.MessageBox',
        'Ext.tip.QuickTipManager',
        'Ext.picker.Color',
        'Ext.Toolbar.Item',
        'Ext.toolbar.Toolbar',
        'Ext.Toolbar.Separator',
        'Ext.Toolbar.Spacer',
        'Ext.util.Format',
        'Ext.layout.container.Card',
        'Ext.layout.container.Form',
        'Ext.form.field.Hidden',
        'Ext.form.field.Text',
        'Ext.tab.Panel',
        'Ext.grid.column.CheckColumn',
        'Ext.form.field.Checkbox',
        'Ext.form.field.Date',
        'Ext.overrides.grid.column.Action',
        
        'PB.Util',
        'PB.Dlg',
        'PB.button.LinkButton',
        
        'PBAdmin.view.Main',
        'PBAdmin.view.main.settings.Main',
        'PBAdmin.view.main.testSystem.Main',
        
        'PBAdmin.controller.Main',
        'PBAdmin.controller.main.Settings',
        'PBAdmin.controller.main.SettingsForm',
        'PBAdmin.controller.main.TestSystem',
        
        'PBAdmin.store.main.SettingsGridStore',
        'PBAdmin.store.main.AuthMasterTypeCmbStore',
        'PBAdmin.store.main.ModuleCmbStore'
	],

    views: [
        'Main'
    ],

    controllers: [
        'Main',
        'main.Settings',
        'main.SettingsForm',
        'main.Group',
        'main.TestSystem'
    ],

    stores: [
        'main.SettingsGridStore',
        'main.AuthMasterTypeCmbStore'
    ],
    
	launch: function () {
		if (typeof TASKS !== 'undefined') {
			HEIGHT = Ext.get(HTML_ID).getHeight();
			WIDTH = Ext.get(HTML_ID).getWidth();
			
		 	view = Ext.create('PBAdmin.view.Main',{
			  renderTo: Ext.Element.get(MAIN_ID),
		 	  height:(HEIGHT-H_OFFSET)+'px',
		 	  width:(WIDTH-W_OFFSET)+'px',
		 	  tasks:TASKS
		 	});
		 	
		 	Ext.Ajax.request({
			      url:ALF_CONTEXT+"/util/getPageSize",
			      method: "GET",
			      success: function(response){
			    	  
			    	var data = Ext.decode(response.responseText).data;
			    	
			    	ADMIN_PAGE_SIZE = data.pageSize;
			    	
			      },
			      failure: function(response, opts){
			          // do nothing
			      },
			      headers: getAlfHeader(),
			      async:false
			});	 	
		 	
		 	Ext.Ajax.request({
			      url:ALF_CONTEXT+"/admin/module/list",
			      method: "GET",
			      success: function(response){
			    	  
			    	var system = Ext.decode(response.responseText);
			    	
				 	modules = system.modules;
					
					for(var i=0; i<modules.length; i++) {
						view.addMenu(modules[i].name, modules[i].iconCls, i==0);
					}
					view.setMenuItems(modules[0].items);
	
			      },
			      failure: function(response, opts){
			          alert("failed");
			      },
			      headers: getAlfHeader()
			});
		}

	}
//test
});
