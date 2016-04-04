Ext.define('PBPcm.controller.Form', {
    extend: 'Ext.app.Controller',

    refs:[{
        ref: 'main',
        selector: 'pcmReqMain'
    },{
        ref: 'mainForm',
        selector: 'pcmReqMainForm'
    },{
        ref: 'mainGrid',
        selector: 'pcmReqMainGrid'
    },{
        ref: 'hidId',     
        selector: 'pcmReqMainForm field[name=id]'
    },{
        ref: 'hidStatus',     
        selector: 'pcmReqMainForm field[name=status]'
    },{
        ref: 'txtReqBy',     
        selector: 'pcmReqMainForm field[name=reqBy]'
    },{
        ref: 'txtReqBu',     
        selector: 'pcmReqMainForm field[name=reqBu]'
    },{
        ref: 'txtReqOu',     
        selector: 'pcmReqMainForm field[name=reqOu]'
    },{
        ref: 'cmbObjectiveType',     
        selector: 'pcmReqMainForm field[name=objectiveType]'
    },{
        ref: 'txtObjective',
        selector: 'pcmReqMainForm field[name=objective]'
    },{
        ref: 'txtReason',
        selector: 'pcmReqMainForm field[name=reason]'
    },{
        ref: 'cmbCurrency',
        selector: 'pcmReqMainForm field[name=currency]'
    },{
        ref: 'txtCurrencyRate',
        selector: 'pcmReqMainForm field[name=currencyRate]'
    },{
        ref: 'txtBudgetCc',
        selector: 'pcmReqMainForm field[name=budgetCc]'
    },{
        ref: 'raStock',
        selector: 'pcmReqMainForm field[name=toStock]'
    },{
        ref: 'cmbStockOu',
        selector: 'pcmReqMainForm field[name=stockOu]'
    },{
        ref: 'raPrototype',
        selector: 'pcmReqMainForm field[name=prototype]'
    },{
        ref: 'cmbEvent',
        selector: 'pcmReqMainForm field[name=event]'
    },{
        ref: 'cmbPcmOu',
        selector: 'pcmReqMainForm field[name=pcmOu]'
    },{
        ref: 'txtLocation',
        selector: 'pcmReqMainForm field[name=location]'
    },{
        ref: 'chkAcrossBudget',
        selector: 'pcmReqMainForm field[name=isAcrossBudget]'
    },{
        ref: 'txtAcrossBudget',
        selector: 'pcmReqMainForm field[name=acrossBudget]'
    },{
        ref: 'chkRefId',
        selector: 'pcmReqMainForm field[name=isRefId]'
    },{
        ref: 'txtRefId',
        selector: 'pcmReqMainForm field[name=refId]'
    },{
        ref: 'txtTotal',     
        selector: 'pcmReqMainForm field[name=total]'
    },{
        ref: 'cmbMethod',     
        selector: 'pcmReqMainForm field[name=method]'
    },{
        ref: 'hidMethodCond2Rule',     
        selector: 'pcmReqMainForm field[name=methodCond2Rule]'
    },{
        ref: 'cmbMethodCond2',     
        selector: 'pcmReqMainForm field[name=methodCond2]'
    },{
        ref: 'txtMethodCond2Dtl',     
        selector: 'pcmReqMainForm field[name=methodCond2Dtl]'
	},{
    	ref:'fileTab',
		selector:'pcmReqFileTab'
	},{
    	ref:'uploadGrid',
		selector:'pcmReqFileTab uploadGrid'
    },{
        ref: 'btnApprovalMatrix',     
        selector: 'pcmReqMainForm button[action=approvalMatrix]'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'pcmReqMainForm [action=finish]': {
				click : me.finish
			},
			'pcmReqMainForm [action=cancelEdit]': {
				click : me.cancelEdit
			},
			'pcmReqMainForm [action=send]': {
				click : me.send
			},
			'pcmReqMainForm [action=cancel]': {
				click : me.cancel
			},
			'pcmReqMainForm [action=saveDraft]': {
				click : me.saveDraft
			},
			'pcmReqMainForm [action=preview]': {
				click : me.preview
			},
			'pcmReqMainForm [action=approvalMatrix]': {
				click : me.showApprovalMatrix
			},
			'pcmReqMainForm [action=searchPR]': {
				click : me.searchPR
			},
			'pcmReqHdrHireTab':{
				selectObjective:me.selectObjective,
				selectCurrency:me.selectCurrency
			}
		});

	},
	
	PREVIEW_MSG_KEY : 'PREVIEW',
	SEND_MSG_KEY : 'SEND_PCM_REQ',
	MSG_KEY : 'SAVE_PCM_REQ',
    URL : ALF_CONTEXT+'/pcm',
    MSG_URL : ALF_CONTEXT+'/pcm/message',
    
    validForm:function(saveDraft) {
		var me = this;
		var result = true;
		
		return result;
	},
	
	send:function() {
		var form = this.getMainForm();
		
		if (this.validForm(false)) {
			if(validForm(form) ){
				PB.Dlg.confirm('CONFIRM_'+this.SEND_MSG_KEY,this,'doSend', MODULE_PCM);
			} else {
				PB.Dlg.error('INVALID_INPUT_'+this.MSG_KEY, MODULE_PCM);
			}
		}
	},
	
	doSend: function(){
	
		var me = this;
		
		var grid = me.getMainGrid();
		
		var myMask = new Ext.LoadMask({
			target:me.getMain(), 
			msg:"Please wait..."
		});
		
		myMask.show();
		
		var params;
		
		try {
			params = me.prepareParams(true);
  		} catch (err) {
  			myMask.hide();
  			return;
  		}
		
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
			   			else {
			   				msg = "";
			   				
					   		if (json.data.users) {
				   				msg += "Invalid Users ==> " + json.data.users;
					   		}
					   		
					   		if (json.data.groups) {
					   			if (msg) {
					   				msg += "<br/><br/>";
					   			}
					   			
				   				msg += "Invalid Groups ==> " + json.data.groups;
					   		}
					   		
					   		if (msg) {
				   				msg += "<br/><br/>Please check whether group exists and has member.";
					   		}
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
	
	prepareParams : function(includeContent1) {
		var me = this;
		var params = {
  			id:me.getHidId().getValue(),
  			status:me.getHidStatus().getValue()
  		};
		
		params.reqBy = me.getTxtReqBy().getValue();
		params.reqBu = me.getTxtReqBu().getValue();
		params.reqOu = me.getTxtReqOu().getValue();
		
		params.objectiveType = me.getCmbObjectiveType().getValue();
		params.objective = me.getTxtObjective().getValue();
		params.reason = me.getTxtReason().getValue();
		
		params.currency = me.getCmbCurrency().getValue();
		params.currencyRate = me.getTxtCurrencyRate().getValue();
		
		params.budgetCc = me.getTxtBudgetCc().getValue();
		params.stockOu = me.getCmbStockOu().getValue();
		
		params.prototype = (me.getRaPrototype().getValue() ? "1" : "0");
		params.event = me.getCmbEvent().getValue();
		
		params.pcmOu = me.getCmbPcmOu().getValue();
		params.location = me.getTxtLocation().getValue();
		
		params.acrossBudget = me.getTxtAcrossBudget().getValue();
		params.refId = me.getTxtRefId().getValue();

		params.method = me.getCmbMethod().getValue();
		params.methodCond2Rule = me.getHidMethodCond2Rule().getValue();
		params.methodCond2 = me.getCmbMethodCond2().getValue();
		params.methodCond2Dtl = me.getTxtMethodCond2Dtl().getValue();
		
		params.total = me.getTxtTotal().getValue();
		
//		params.dtls = me.getFieldValues();
		
		params.files = me.listFiles();
		
		return params;
	},
	
	cancel:function() {
		PB.Dlg.confirm('CONFIRM_CANCEL_PCM_REQ',this,'closeForm', MODULE_PCM);
	},
	
	closeForm:function() {
		this.getMainForm().destroy();
		
		this.getMainGrid().getStore().load();
	},
	
	saveDraft:function() {
		if (!this.validForm(true)) {
			return;
		}
	
		var form = this.getMainForm();
	
		if(!validForm(form)){
			PB.Dlg.error('INVALID_INPUT_'+this.MSG_KEY, MODULE_PCM);
			return;
		}

		var me = this;
	
		var grid = me.getMainGrid();
		
		var myMask = new Ext.LoadMask({
			target:me.getMain(), 
			msg:"Please wait..."
		});
		
		myMask.show();
		
		var params;
		try {
			params = me.prepareParams(true);
  		} catch (err) {
  			myMask.hide();
  			return;
  		}
		
		Ext.Ajax.request({
		    url:me.URL+"/save",
		    method: "POST",
		    params: params,
		    success: function(response){
			
			  	var json = Ext.decode(response.responseText);
				  
			   	if (json.success) {
			   		var id = json.data.id;
			   		PB.Dlg.info('SUCC_'+me.MSG_KEY, MODULE_PCM, {msg:'ID:'+id, fn:me.closeForm, scope:me});
			   	} else {
			   		PB.Dlg.error('ERR_'+me.MSG_KEY, MODULE_PCM);
			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.MSG_KEY+" ("+json.message+")", MODULE_PCM);
		    	}
		    	catch (err) {
			    	alert(response.responseText);
		    	}
			    myMask.hide();
		    },
		    headers: getAlfHeader()
		});
	},
	
	getHSaveFieldValue:function(saveField, req) {
		var field = this.getMainForm().down("field[hSaveField="+saveField+"]");
		var v;
		if(field) {
			v = field.getValue();
		}
		else {
			if (req) {
				alert("Field Not Found : "+saveField);
				throw "fnf";
			}
		}
		return v;
	},
	
	getFieldValues:function() {
		var fields = this.getMainForm().query("field[name^=F_]");
		
		var values = [];

		for(var i=0; i<fields.length; i++) {
			var f = fields[i];
			
			if (f.inputType == "radio") {
				if (f.checked) {
					values.push({
						n:f.name.substring(2),
						v:f.inputValue
					});
				}
			} else {
				if (f.bgData.dtype!="B") {
					values.push({
						n:f.name.substring(2),
						v:(f.getValue()==null || f.getValue() == "null") ? "" : f.getValue()
					});
				}
			}
		}
		
		return Ext.JSON.encode(values);
	},
	
	listSelectedUserGroup:function(itemId) {
		var values = [];
	
		var store = this.getFileTab().down("usergroupGrid[itemId="+itemId+"]").getStore();
		
		store.each(function(rec){
			values.push({
				id:rec.get("id"),
				type:rec.get("type")
			});
		});

		return Ext.JSON.encode(values);
	},
	
	listFiles:function() {
		var values = [];
	
		var store = this.getFileTab().down("uploadGrid").getStore();
		
		store.each(function(rec){
			values.push({
				name:rec.get("name"),
				path:rec.get("path"),
				nodeRef:rec.get("nodeRef")
			});
		});

		return Ext.JSON.encode(values);
	},
	
	preview:function() {
	
		var me = this;
		
		var grid = me.getMainGrid();
		
		var myMask = new Ext.LoadMask({
			target:me.getMain(), 
			msg:"Please wait..."
		});
		
		myMask.show();
		
		var params = {};
		
		try {
			params.content1 = me.getEditor().getValue();
  		} catch (err) {
  			myMask.hide();
  			return;
  		}
  		
		Ext.Ajax.request({
		    url:me.URL+"/preview",
		    method: "POST",
		    params: params,
		    success: function(response){
		  	  
			  	var json = Ext.decode(response.responseText);
				  
			   	if (json.success) {
			   		window.open(me.URL+"/preview?id="+json.data[0].id, "_new");
			   	} else {
			   		PB.Dlg.error('ERR_'+me.PREVIEW_MSG_KEY, MODULE_PCM);
			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.PREVIEW_MSG_KEY+" ("+json.message+")", MODULE_PCM);
		    	}
		    	catch (err) {
			    	alert(response.responseText);
		    	}
			    myMask.hide();
		    },
		    headers: getAlfHeader()
		});

	},
	
	activateRptTab:function() {
		this.getMainForm().down("tabpanel").getLayout().setActiveItem(this.getRptTab());
	},

	finish:function() {
		var form = this.getMainForm();
		
		if(validForm(form)){
			PB.Dlg.confirm('CONFIRM_'+this.MSG_KEY,this,'doFinish', MODULE_PCM);
		} else {
			PB.Dlg.error('INVALID_INPUT_'+this.MSG_KEY, MODULE_PCM);
		}
		
	},
	
	doFinish: function(){
		var me = this;
		
		var myMask = new Ext.LoadMask({
			target:me.getMain(), 
			msg:"Please wait..."
		});
		
		myMask.show();
		
		var params;
		
		try {
			params = me.prepareParams(true);
  		} catch (err) {
  			myMask.hide();
  			return;
  		}
		
		Ext.Ajax.request({
		    url:me.URL+"/finish",
		    method: "POST",
		    params: params,
		    success: function(response){
		  	  
			  	var json = Ext.decode(response.responseText);
				  
			   	if (json.success) {
			   		 
			   		PB.Dlg.info('SUCC_'+me.MSG_KEY, MODULE_PCM, {msg:'ID:'+json.data.id, fn:me.backToWfForm, scope:me});
			   		
			   	} else {
			   		if (json.data.valid != undefined && !json.data.valid) {
			   			
						var msg;
						
						if (json.data.msg) {
							msg = json.data.msg;
						}
						else {
							msg = "";
							
							if (json.data.users) {
								msg += "Invalid Users ==> " + json.data.users;
							}
							
							if (json.data.groups) {
								if (msg) {
									msg += "<br/><br/>";
								}
								
								msg += "Invalid Groups ==> " + json.data.groups;
							}
							
							if (msg) {
								msg += "<br/><br/>Please check whether group exists and has member.";
							}
						}
						
						PB.Dlg.error(null, MODULE_PCM, {msg:msg});
					}
					else {
						PB.Dlg.error('ERR_'+me.MSG_KEY, MODULE_PCM);
					}

			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.MSG_KEY+" ("+json.message+")", MODULE_PCM);
		    	}
		    	catch (err) {
			    	alert(response.responseText);
		    	}
			    myMask.hide();
		    },
		    headers: getAlfHeader()
		});
	},
	
	cancelEdit:function() {
		PB.Dlg.confirm('CONFIRM_CANCEL_MEMO',this,'backToWfForm', MODULE_PCM);
	},

	backToWfForm:function() {
		window.location = appContext+"/page/task-edit?taskId="+TASK_ID+"&referrer=tasks";
	},
	
	showApprovalMatrix:function() {
		var me = this;
	
		var dlg = Ext.create("PBPcm.view.approvalMatrix.DtlDlg");
		var id = me.getBtnApprovalMatrix().fid;
	
		var store = dlg.items.items[0].getStore(); 
		store.getProxy().extraParams = {
			id : id
		}
		store.load();
		
		dlg.show();
	},
	
	searchPR:function() {
		var dlg = Ext.create("PBPcm.view.pr.SearchDlg");
		dlg.show();
	},
	
	selectObjective:function(cmb, newV, oldV) {
		var me = this;
		
		var methodStore = me.getCmbMethod().getStore();
		methodStore.getProxy().extraParams = {
			objType : newV
		}
		methodStore.load();			
	},
	
	selectCurrency:function(cmb, newV, oldV) {
		var me = this;
		
		Ext.Ajax.request({
		     url:ALF_CONTEXT+'/admin/main/currency/rate/get',
		     method: "GET",
		     params: {
				id: newV
		     },
		     success: function(res){
		    	 
		    	 var json = Ext.decode(res.responseText);
		      	  
		    	 if(json.success){
		    	  
		    		me.getTxtCurrencyRate().setValue(json.data);
		    		
		    	 }else{
		    		 PB.Dlg.error('ERR_'+me.MSG_KEY, MODULE_PCM);
		    	 }
		    	 
		     },
		     failure: function(response, opts){
		    	 PB.Dlg.error('ERR_'+me.MSG_KEY, MODULE_PCM);
		     },
		     headers: getAlfHeader()
		});  
	},
	
	preview:function() {
		
		var me = this;
		
		var grid = me.getMainGrid();
		
		var myMask = new Ext.LoadMask({
			target:me.getMain(), 
			msg:"Please wait..."
		});
		
		myMask.show();
		
		var params = {};
			
		Ext.Ajax.request({
		    url:me.URL+"/preview",
		    method: "POST",
		    params: params,
		    success: function(response){
		  	  
			  	var json = Ext.decode(response.responseText);
				  
			   	if (json.success) {
			   		window.open(me.URL+"/preview?id="+json.data[0].id, "_blank");
			   	} else {
			   		PB.Dlg.error('ERR_'+me.PREVIEW_MSG_KEY, MODULE_PCM);
			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.PREVIEW_MSG_KEY+" ("+json.message+")", MODULE_PCM);
		    	}
		    	catch (err) {
			    	alert(response.responseText);
		    	}
			    myMask.hide();
		    },
		    headers: getAlfHeader()
		});
	
	}

});
