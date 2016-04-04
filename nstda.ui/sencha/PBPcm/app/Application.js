Ext.define('PBPcm.Application', {
    name: 'PBPcm',

    extend: 'Ext.app.Application',

    requires: [
        'Ext.util.Cookies',
        'Ext.grid.Panel',
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
        'Ext.form.field.TextArea',
        'Ext.form.field.Date',
        'Ext.tab.Panel',
        'Ext.overrides.grid.column.Action',
        'Ext.ux.form.NumericField',
        'Ext.ux.form.TimePickerField',
        'Ext.ux.DateTimePicker',
        'Ext.ux.form.DateTimeField',
        'Ext.ux.DateTimeMenu',
        
        'PB.Util',
        'PB.Dlg',
        'PB.button.LinkButton',
        'PB.store.common.ComboBoxStore',
        'PB.model.common.ComboBoxModel',
        'PB.vtype.Validation',

        'PBPcm.view.Main',
        
        'PBPcm.controller.Main',
        'PBPcm.controller.Form',
        'PBPcm.controller.common.UserGroup',
        'PBPcm.controller.common.Upload',
        'PBPcm.controller.detail.Main',
        'PBPcm.controller.detail.Form',
        'PBPcm.controller.committee.Main',
        'PBPcm.controller.committee.Form',
        'PBPcm.controller.file.Main',
        'PBPcm.controller.Report',
        
        'PBPcm.Label'
	],

    views: [
        'Main'
    ],

    controllers: [
        'Main',
        'Form',
        'common.UserGroup',
        'common.Upload',
        'Report',
        'detail.Main',
        'detail.Form',
        'committee.Main',
        'committee.Form',
        'file.Main'
    ],

    stores: [
    ],
    
	launch: function () {
	 	Ext.Ajax.request({
		      url:ALF_CONTEXT+"/util/getPageSize",
		      method: "GET",
		      success: function(response){
		    	  
		    	var data = Ext.decode(response.responseText).data;
		    	
		    	PAGE_SIZE = data.pageSize;
		    	
		      },
		      failure: function(response, opts){
		          // do nothing
		      },
		      headers: getAlfHeader(),
		      async:false
		});
	 	
	 	Ext.Ajax.request({
		      url:ALF_CONTEXT+"/pcm/userTask",
		      method: "GET",
		      success: function(response){
		    	  
		    	var data = Ext.decode(response.responseText).data;
		    	
		    	TASKS = data.tasks;
		    	
		      },
		      failure: function(response, opts){
		          // do nothing
		      },
		      headers: getAlfHeader(),
		      async:false
		});
	 	
	 	Ext.Ajax.request({
		      url:ALF_CONTEXT+"/pcm/message/req",
		      method: "GET",
		      success: function(response){
		    	  
		    	var data = Ext.decode(response.responseText);
			 	Ext.apply(PBPcm.Label, data);
			 	//alert(PBPcm.Label.a);
		      },
		      failure: function(response, opts){
		          // do nothing
		      },
		      headers: getAlfHeader(),
		      async:false
		});	 	
	
		HEIGHT = Ext.get(HTML_ID).getHeight();
		WIDTH = Ext.get(HTML_ID).getWidth();
		
	 	view = Ext.create('PBPcm.view.Main',{
		  renderTo: Ext.Element.get(MAIN_ID),
	 	  height:(HEIGHT-H_OFFSET)+'px',
	 	  width:(WIDTH-W_OFFSET)+'px',
	 	  tasks:TASKS
	 	});
	
	}

});
