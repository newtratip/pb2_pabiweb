Ext.define('PBPcmOrd.controller.Main', {
    extend: 'Ext.app.Controller',
    
	refs:[{
	    ref:'main',
	    selector:'pcmOrdMain'
	},{
	    ref:'mainGrid',
	    selector:'pcmOrdMainGrid'
	},{
		ref:'txtSearch',
		selector:'pcmOrdMainGrid [itemId=txtSearch]'
	}],
	
	init:function() {
		var me = this;

		me.control({
			'pcmOrdMain [action=add]': {
				click : me.add
			},
			'pcmOrdMain [action=search]': {
				click : me.search
			},
			'pcmOrdMainGrid':{
				search : me.search,
   				gotoFolder : me.gotoFolder,
   				viewDetail : me.viewDetail,
   				viewHistory : me.viewHistory
			}
		});
	
	},
	
	SEND_MSG_KEY : 'SEND_PCM_ORD',
	MSG_KEY : 'DELETE_ORD',
    URL : ALF_CONTEXT+'/pcm/ord',
    ADMIN_URL : ALF_CONTEXT+'/admin/pcm/ord',
    MSG_URL : ALF_CONTEXT+'/pcm/message',
	
	onLaunch:function(app) {
		var me = this;
	},
	
	search:function() {
		var me = this;
		var store = me.getMainGrid().getStore();
		
		var params = {
			s : me.getTxtSearch().getValue()
		}
		
		var fields = {};
		
		var tbar = me.getMainGrid().getDockedComponent(1);
		for(var i=0; i<10; i++) {
			var c = tbar.down("[itemId=cri"+i+"]");
			if (c) {
				var d = c.bgData;
				fields[d.field] = c.getValue();
			}
		}
		
		params.fields = Ext.JSON.encode(fields);
		
		store.getProxy().extraParams = params;
		store.currentPage = 1;
		store.load();
	},
	
	add:function() {
		var me = this;
		
		var grid = me.getMainGrid();
		
		var myMask = new Ext.LoadMask({
			target:me.getMain(), 
			msg:"Please wait..."
		});
		
		myMask.show();
		
		var params = {
				total:224700,
				requested_time:'2016-03-20T12:00:00',
				files:Ext.JSON.encode([]),
				reqOu:'105015'
		};
		
		Ext.Ajax.request({
		    url:me.URL+"/send",
		    method: "POST",
		    params: params,
		    success: function(response){
		  	  
			  	var json = Ext.decode(response.responseText);
				  
			   	if (json.success) {
			   		PB.Dlg.info('SUCC_'+me.SEND_MSG_KEY, MODULE_PCM, {msg:'ID:'+json.data.id, fn:me.closeForm, scope:me});
			   	} else {
			   		if (json.data.valid != undefined && !json.data.valid) {
			   			
			   			var msg;
			   			
			   			if (json.data.msg) {
			   				msg = json.data.msg;
				   		}
				   		
			   			PB.Dlg.error(null, MODULE_PCM, {msg:msg});
			   		}
			   		else {
				   		PB.Dlg.error('ERR_'+me.SEND_MSG_KEY, MODULE_PCM);
			   		}
			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.SEND_MSG_KEY+" ("+json.message+")", MODULE_PCM);
		    	}
		    	catch (err) {
			    	alert(response.responseText);
		    	}
			    myMask.hide();
		    },
		    headers: getAlfHeader()
		});
	},

	closeForm:function() {
		this.getMainGrid().getStore().load();
	},
	
	gotoFolder:function(r) {
		Ext.Ajax.request({
	        url:ALF_CONTEXT+"/util/getFolderName",
	        async : false,
	        method: "POST",
	        params: {
	        	n : r.get('folder_ref')
	        },
	        success: function(response){
	        	
	        	var json = Ext.decode(response.responseText);  
	        	
	        	if(json.success){
	        		
	//            	window.open(MAIN_CONTEXT+"/page/repository#filter=path%7C%2FSites%2F"+CURRENT_SITE+"%2FdocumentLibrary%2F"+json.folderName+"%7C", "_new");
	//            	window.open(MAIN_CONTEXT+"/page/site/"+CURRENT_SITE+"/documentlibrary#filter=path%7C"+ json.data.name + "%7C", "_new");
	            	window.open(MAIN_CONTEXT+"/page/repository#filter=path%7C"+json.data.name+"%7C", "_new");
	        		
	        	}
	        	
	        },
	        failure: function(response, opts){
	        	
	        	//console.log("fail");
	        },
	        headers: getAlfHeader()
	    });
	},
	
	viewDetail:function() {
	},
	
	viewHistory:function(r) {
		var dlg = Ext.create("PBPcmOrd.view.workflow.DtlDlg");
		var id = r.get("id");
	
		var store = dlg.items.items[0].getStore(); 
		store.getProxy().extraParams = {
			id : id
		}
		store.load();
		
		store = dlg.items.items[1].items.items[0].getStore();
		store.getProxy().extraParams = {
		   	id : id
		};
		store.load();
		
		store = dlg.items.items[1].items.items[1].getStore();
		store.getProxy().extraParams = {
		   	id : id
		};
		store.load();
		
		dlg.show();
	}

});
