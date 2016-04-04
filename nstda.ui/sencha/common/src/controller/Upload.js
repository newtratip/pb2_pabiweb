Ext.define('PB.controller.common.Upload', {
    extend: 'Ext.app.Controller',
    alias:'controller.commonUpload',

    init:function() {
		var me = this;
		
		me.control({
   			'uploadGrid': {
				uploadFile :  me.uploadFile,
				deleteFile :  me.deleteFile
			}
		});

	},
	
	uploadFile : function(form,grid){
		   
		if(form.isValid()){
		    form.submit({
		    	method : 'POST',
		        url: grid.uploadUrl+'?'+ALF_TOKEN+'='+encodeURIComponent(Ext.util.Cookies.get(ALF_TOKEN)),
		        waitMsg: 'Uploading file...',
		        success: function(fp, o) {
		        	
		    		if (o.result.success) {
			        	var fileModel = new Ext.create('PB.model.common.FileModel',{
			        		name : o.result.data[0].name,
			        		path : o.result.data[0].path,
			        		action   : 'D'
			        	});
			        	
			        	grid.getStore().add(fileModel);
			        	Ext.Msg.alert('Success', 'Success');
		        	}
		        }
		    });
		}
	},
	
	deleteFile : function(grid, rowIndex, colIndex){
	
		var rec = grid.getStore().getAt(rowIndex);
	
		Ext.Ajax.request({
		    url : grid.deleteUrl,
		    method: "POST",
		    params: {
		    	name : rec.get('name'),
		    	path: rec.get('path')
		    },
		    success: function(response){
		    	
		    	var json = Ext.decode(response.responseText);  
		    	
		    	if(json.success){
		    		
		    		grid.getStore().remove(rec);
		    		
		    	}
		    	
		    },
		    failure: function(response, opts){
		    	
		    	
		    },
		    headers: getAlfHeader()
		});
	
	}

});