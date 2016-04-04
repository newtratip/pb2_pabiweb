Ext.define('PB.Dlg', {
	statics: {
		confirm:function(key,f,fn,module,n_fn){
			
			Ext.Ajax.request({
		        url:ALF_CONTEXT+"/"+module+"/message/get",
		        method: "GET",
		        params: {
		            key: key
		        },
		        success: function(response){
		        	
		        	var json = Ext.decode(response.responseText);
		        	Ext.MessageBox.confirm(json.data[0].code, json.data[0].message , function(btn){
		        	
		        		if(btn=="yes"){
		        			f[fn](null);
		        		}else{
		        			
		        			if(n_fn!=null && n_fn!=undefined){
		        				f[n_fn](null);
		        			}
		        			
		        		}
		        	});
		        	
		        },
		        failure: function(response, opts){
		            //console.log("failed");
		        },
		        headers: getAlfHeader()
		    });        	
		
			
		},
		
		/*
		 * opts :
		 * 	- msg : extra message
		 *  - icon : Ext.MessageBox.INFO, WARNING, ERROR, QUESTION
		 *  - modal : true/false
		 *  - fn : callback function
		 */
		show:function(key,module,opts) {
	
			if (!opts.icon) {
				icon = Ext.MessageBox.INFO;
			}
		
			Ext.Ajax.request({
		        url:ALF_CONTEXT+"/"+module+"/message/get",
		        method: "GET",
		        params: {
		            key: key
		        },
		        success: function(response){
		        	
		        	var json = Ext.decode(response.responseText);
		        	
		        	var msg = json.data[0].message.trim();
		        	if (opts.msg) {
		        		if (msg && msg!="") {
		        			msg += "<br/><br/>" ;
		        		}
		        		
		        		msg += opts.msg;
		        	}

		        	Ext.MessageBox.show({
		        		title:json.data[0].code, 
		        		msg:msg,
		        		icon: opts.icon,
		        		buttons:Ext.MessageBox.OK,
		        		modal:opts.modal,
		        		fn:opts.fn,
		        		scope:opts.scope
		        	});
		        	
		        },
		        failure: function(response, opts){
		            //console.log("failed");
		        },
		        headers: getAlfHeader()
		    });  
		},
		
		info:function(key,module, opts) {
			if (!key) {
				key = "INFO";
			}
			
			if (!opts) {
				opts = {};
			}
			
			opts.icon = Ext.MessageBox.INFO;
			this.show(key,module,opts);
		},

		warn:function(key,module, opts) {
			if (!key) {
				key = "WARNING";
			}
		
			if (!opts) {
				opts = {};
			}
			
			opts.icon = Ext.MessageBox.WARNING;
			this.show(key,module,opts);
		},

		error:function(key,module, opts) {
			if (!key) {
				key = "ERROR";
			}
			
			if (!opts) {
				opts = {};
			}
			
			opts.icon = Ext.MessageBox.ERROR;
			this.show(key,module,opts);
		},

		alert:function(key,module){
			
			Ext.Ajax.request({
		        url:ALF_CONTEXT+"/"+module+"/message/get",
		        method: "GET",
		        params: {
		            key: key
		        },
		        success: function(response){
		        	
		        	var json = Ext.decode(response.responseText);
		        	Ext.MessageBox.alert(json.data[0].code, json.data[0].message);
		        	
		        },
		        failure: function(response, opts){
		            //console.log("failed");
		        },
		        headers: getAlfHeader(),
		        async:false
		    });        	
		
			
		},
		
		prompt:function (key,f,fn,module){
		
			Ext.Ajax.request({
		        url:ALF_CONTEXT+"/"+module+"/message/get",
		        method: "GET",
		        params: {
		            key: key
		        },
		        success: function(response){
		        	
		        	var json = Ext.decode(response.responseText);
		        	
		        	var dlg = Ext.create('PB.view.common.prompt.PromptDlg',{
		        		title : json.data[0].code
		        	});
		        	
		        	dlg.down('[itemId=msg]').setText(json.data[0].message);
		        	dlg.show();
		        	
		        	var btn = dlg.down('[itemId=btnPrompt]');
		        	var txt = dlg.down('[itemId=promptTxt]');
		        	btn.handler = function(){
		        		
		        		if(txt.isValid()){
		        			
		        			f[fn](txt.getValue());
		        			dlg.destroy();
		        			
		        		}else{
		        			
		        			return false;
		        			
		        		}
		        		
		        	}
		        
		        },
		        failure: function(response, opts){
		            //console.log("failed");
		        },
		        headers: getAlfHeader()
		    });   
			
		}

	}
});