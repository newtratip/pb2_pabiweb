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
        ref: 'hidBudgetCcType',
        selector: 'pcmReqMainForm field[name=budgetCcType]'
    },{
        ref: 'hidBudgetCc',
        selector: 'pcmReqMainForm field[name=budgetCc]'
    },{
        ref: 'raStock',
        selector: 'pcmReqMainForm field[name=isStock]'
    },{
        ref: 'cmbStockOu',
        selector: 'pcmReqMainForm field[name=stockOu]'
    },{
        ref: 'raPrototype',
        selector: 'pcmReqMainForm field[name=isPrototype]'
    },{
        ref: 'cmbPrototype',
        selector: 'pcmReqMainForm field[name=prototype]'
    },{
        ref: 'txtPttContractNo',
        selector: 'pcmReqMainForm field[name=pttContractNo]'
    },{
        ref: 'hidCostControlTypeId',
        selector: 'pcmReqMainForm field[name=costControlTypeId]'
    },{
        ref: 'hidCostControlId',
        selector: 'pcmReqMainForm field[name=costControlId]'
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
        ref: 'hidTotal',     
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
        ref: 'cmbVatId',
        selector: 'pcmReqMainForm field[name=vatId]'
    },{
        ref: 'hidVat',
        selector: 'pcmReqMainForm field[name=vat]'
	},{
    	ref:'fileTab',
		selector:'pcmReqFileTab'
	},{
    	ref:'uploadGrid',
		selector:'pcmReqFileTab uploadGrid'
    },{
        ref: 'btnApprovalMatrix',     
        selector: 'pcmReqMainForm button[action=approvalMatrix]'
	},{
    	ref:'itemGrid',
    	selector:'pcmReqItemTab grid'
	},{
    	ref:'cmtTab',
    	selector:'pcmReqCmtTab tabpanel'
	},{
    	ref:'infoTab',
    	selector:'pcmReqInfoTab'
	},{
    	ref:'userTab',
    	selector:'pcmReqUserTab'
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
			'pcmReqUserTab':{
				selectReqBy:me.selectReqBy
			},
			'pcmReqInfoTab':{
				selectObjective:me.selectObjective,
				selectCurrency:me.selectCurrency,
				selectBudgetCc:me.selectBudgetCc,
				selectCostControl:me.selectCostControl,
				clearCostControl:me.clearCostControl,
				selectPrototype:me.selectPrototype,
				notStock:me.notStock,
				notPrototype:me.notPrototype,
				isAcrossBudget:me.isAcrossBudget,
				isRefId:me.isRefId
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
			params = me.prepareParams();
  		} catch (err) {
  			console.error(err);
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
	
	prepareParams : function() {
		var me = this;
		var params = {
  			id:me.getHidId().getValue(),
  			status:me.getHidStatus().getValue(),
  			
  			reqBy:me.getTxtReqBy().getValue(),
			reqOu:me.getTxtReqOu().getValue(),
  			
			objectiveType:me.getCmbObjectiveType().getValue(),
			objective:me.getTxtObjective().getValue(),
			reason:me.getTxtReason().getValue()
  		};
		
		params.currency = me.getCmbCurrency().getValue();
		params.currencyRate = me.getTxtCurrencyRate().getValue();
		
		params.budgetCcType = me.getHidBudgetCcType().getValue();
		params.budgetCc = me.getHidBudgetCc().getValue();
		
//		params.isStock = (me.getRaStock().getValue() ? "1" : "0");
//		params.stockOu = me.getCmbStockOu().getValue();
		
		params.isPrototype = (me.getRaPrototype().getValue() ? "1" : "0");
		params.prototype = me.getCmbPrototype().getValue();
		params.pttContractNo = me.getTxtPttContractNo().getValue();
		
		params.costControlTypeId = me.getHidCostControlTypeId().getValue();
		params.costControlId = me.getHidCostControlId().getValue();
		
		params.pcmOu = me.getCmbPcmOu().getValue();
		params.location = me.getTxtLocation().getValue();
		
		params.isAcrossBudget = (me.getChkAcrossBudget().getValue() ? "1" : "0");
		params.acrossBudget = me.getTxtAcrossBudget().getValue();
		
		params.isRefId = (me.getChkRefId().getValue() ? "1" : "0");
		params.refId = me.getTxtRefId().getValue();

		params.method = me.getCmbMethod().getValue();
		params.methodCond2Rule = me.getHidMethodCond2Rule() ? me.getHidMethodCond2Rule().getValue() : "";
		params.methodCond2 = me.getCmbMethodCond2() ? me.getCmbMethodCond2().getValue() : "";
		params.methodCond2Dtl = me.getTxtMethodCond2Dtl() ? me.getTxtMethodCond2Dtl().getValue() : "";
		
		params.vatId = me.getCmbVatId().getValue();
		params.vat = me.getHidVat().getValue();
		
		params.total = me.getHidTotal().getValue();
		
		params.items = me.getItems();
		params.files = me.listFiles();
		params.cmts = me.getCmts();
		
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
			params = me.prepareParams();
  		} catch (err) {
  			console.error(err);
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
	
	getItems:function() {
	
		var me = this;
	
		var itemStore = me.getItemGrid().getStore();
		
		var data = [];
		itemStore.each(function(rec){
		   data.push(rec.data);
		});
		
		return Ext.JSON.encode(data);
	},
	
	getCmts:function() {
	
		var me = this;
		
		var panel = me.getCmtTab();
		
		var data = [];
		panel.items.each(function(grid){
			console.log(grid.xtype);
			if (grid.xtype == 'grid') {
			
				var cmtStore = grid.getStore();
				
				var cmt = [];
				cmtStore.each(function(rec){
				   cmt.push(rec.data);
				});
				
				data.push({
					cmt:grid.title,
					cmts:cmt
				});
				
			}
		});
	
		return Ext.JSON.encode(data);
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
			params = me.prepareParams();
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
		PB.Dlg.confirm('CONFIRM_CANCEL_PCM_REQ',this,'backToWfForm', MODULE_PCM);
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
	
	selectPrototype:function(cmb, newV, oldV) {
		var me = this;
		
		me.getTxtPttContractNo().setDisabled(newV != "PTT2");
	},	
	
	preview:function() {
		
		var me = this;
		
		var form = me.getMainForm();
		
		if(!validForm(form)){
			PB.Dlg.error('INVALID_INPUT_'+this.MSG_KEY, MODULE_PCM);
			return;
		}		
		
		var grid = me.getMainGrid();
		
		var myMask = new Ext.LoadMask({
			target:me.getMain(), 
			msg:"Please wait..."
		});
		
		myMask.show();
		
		var params;
		
		try {
			params = me.prepareParams();
  		} catch (err) {
  			console.log(err);
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
					window.open(me.URL+"/preview?id="+json.data[0].id,"_blank");
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
	
	notStock:function() {
		var me = this;
		
		me.getCmbStockOu().clearValue();
	},
	
	notPrototype:function() {
		var me = this;
		
		me.getCmbPrototype().clearValue();
	},
	
	selectBudgetCcCallBack:function(ids, type, typeName) {
		var tab = this.targetPanel;
		setValue(tab, 'budgetCc', ids[0]);
		setValue(tab, 'budgetCcName', ids[1]);
		setValue(tab, 'budgetCcType', type);
		setValue(tab, 'budgetCcTypeName', typeName);
	},
	
	selectBudgetCc:function() {
		var me = this;

		var dlg = Ext.create("PB.view.common.SearchSectionProjectDlg",{
			title:'ค้นหา',
			targetPanel:this.getInfoTab(),
			callback:this.selectBudgetCcCallBack
		});
		dlg.show();
	},
	
	selectReqByCallBack:function(ids, type, typeName) {
		var tab = this.targetPanel;
		setValue(tab, 'reqBy', ids[0]);
	},	

	selectReqBy:function() {
		var me = this;

		var dlg = Ext.create("PB.view.common.SearchUserDlg",{
			title:'ค้นหา',
			targetPanel:this.getUserTab(),
			callback:this.selectReqByCallBack
		});
		dlg.show();
	},
	
	selectCostControlCallBack:function(ids, type, typeName) {
		var tab = this.targetPanel;
		setValue(tab, 'costControlId', ids[0]);
		setValue(tab, 'costControlName', ids[1]);
		setValue(tab, 'costControlTypeId', type);
		setValue(tab, 'costControlTypeName', typeName);
	},
	
	selectCostControl:function() {
		Ext.create("PB.view.common.SearchCostControlDlg",{
			title:'ค้นหา',
			targetPanel:this.getInfoTab(),
			callback:this.selectCostControlCallBack
		}).show();
	},
	
	clearCostControl:function() {
		var tab = this.getInfoTab();
		setValue(tab, 'costControlId', null);
		setValue(tab, 'costControlName', '');
		setValue(tab, 'costControlTypeId', null);
		setValue(tab, 'costControlTypeName', '');
	},
	
	isAcrossBudget:function(chk, v) {
		var txt = this.getInfoTab().down("[name=acrossBudget]");
		if (v) {
			txt.enable();
		} else {
			txt.disable();
		}
	},
	
	isRefId:function(chk, v) {
		var txt = this.getInfoTab().down("[name=refId]");
		if (v) {
			txt.enable();
		} else {
			txt.disable();
		}
	}

});
