Ext.define('PBExpUse.controller.Form', {
    extend: 'Ext.app.Controller',

	refs:[{
	    ref: 'main',
	    selector: 'expUseMain'
	},{
	    ref: 'mainForm',
	    selector: 'expUseMainForm'
	},{
	    ref: 'mainGrid',
	    selector: 'expUseMainGrid'
	},{
	    ref: 'hidId',     
	    selector: 'expUseMainForm field[name=id]'
	},{
	    ref: 'hidStatus',     
	    selector: 'expUseMainForm field[name=status]'
	},{
	    ref: 'txtReqBy',     
	    selector: 'expUseMainForm field[name=reqBy]'
	},{
	    ref: 'txtReqOu',     
	    selector: 'expUseMainForm field[name=reqOu]'
	},{
	    ref: 'txtObjective',
	    selector: 'expUseMainForm field[name=objective]'
	},{
	    ref: 'hidBudgetCcType',
	    selector: 'expUseMainForm field[name=budgetCcType]'
	},{
	    ref: 'hidBudgetCc',
	    selector: 'expUseMainForm field[name=budgetCc]'
	},{
	    ref: 'cmbBank',     
	    selector: 'expUseMainForm field[name=bank]'
	},{
	    ref: 'radBankType',     
	    selector: 'expUseMainForm field[name=bankType]'
	},{
	    ref: 'txtTotal',     
	    selector: 'expUseMainForm field[name=total]'
	},{
	    ref: 'radCostControlTypeId',
	    selector: 'expUseMainForm field[name=costControlTypeId]'
	},{
	    ref: 'cmbCostControlId',
	    selector: 'expUseMainForm field[name=costControlId]'
	},{
	    ref: 'txtCostControl',
	    selector: 'expUseMainForm field[name=costControl]'
	},{
	    ref: 'txtCc1From',
	    selector: 'expUseMainForm field[name=cc1From]'
	},{
	    ref: 'txtCc1To',
	    selector: 'expUseMainForm field[name=cc1To]'
	},{
	    ref: 'txtCc2From',
	    selector: 'expUseMainForm field[name=cc2From]'
	},{
	    ref: 'txtCc2To',
	    selector: 'expUseMainForm field[name=cc2To]'
	},{
	    ref: 'radPayType',
	    selector: 'expUseMainForm field[name=payType]'
	},{
	    ref: 'txtSupName',
	    selector: 'expUseMainForm field[name=supName]'
	},{
	    ref: 'txtSupFeeName',
	    selector: 'expUseMainForm field[name=supFeeName]'
	},{
	    ref: 'cmbPoNo',
	    selector: 'expUseMainForm field[name=poNo]'
	},{
	    ref: 'cmbAssetNo',
	    selector: 'expUseMainForm field[name=assetNo]'
	},{
	    ref: 'cmbAvCode',
	    selector: 'expUseMainForm field[name=avCode]'
	},{
	    ref: 'hidIchargeCode',
	    selector: 'expUseMainForm field[name=ichargeCode]'
	},{
	    ref: 'hidIchargeType',
	    selector: 'expUseMainForm field[name=ichargeType]'
	},{
	    ref: 'txtIchargeTypeName',
	    selector: 'expUseMainForm field[name=ichargeTypeName]'
    },{
        ref: 'cmbVatId',
        selector: 'expUseMainForm field[name=vatId]'
    },{
        ref: 'hidVat',
        selector: 'expUseMainForm field[name=vat]'
	},{
		ref:'fileTab',
		selector:'expUseFileTab'
	},{
		ref:'uploadGrid',
		selector:'expUseFileTab uploadGrid'
	},{
	    ref:'btnApprovalMatrix',     
	    selector: 'expUseMainForm button[action=approvalMatrix]'
	},{
		ref:'voyagerEmpGrid',
		selector:'expUseVoyagerTab #empGrid'
	},{
		ref:'voyagerOthGrid',
		selector:'expUseVoyagerTab #othGrid'
	},{
    	ref:'itemGrid',
    	selector:'expUseItemTab grid'
	},{
		ref:'infoTab',
		selector:'expUseInfoTab'
	},{
		ref:'userTab',
		selector:'expUseUserTab'
	}],
	
	init:function() {
		var me = this;
		
		me.control({
			'expUseMainForm [action=finish]': {
				click : me.finish
			},
			'expUseMainForm [action=cancelEdit]': {
				click : me.cancelEdit
			},
			'expUseMainForm [action=send]': {
				click : me.send
			},
			'expUseMainForm [action=cancel]': {
				click : me.cancel
			},
			'expUseMainForm [action=saveDraft]': {
				click : me.saveDraft
			},
			'expUseMainForm [action=preview]': {
				click : me.preview
			},
			'expUseMainForm [action=approvalMatrix]': {
				click : me.showApprovalMatrix
			},
			'expUseUserTab':{
				selectReqBy:me.selectReqBy
			},
			'expUseInfoTab':{
				selectBudgetCc:me.selectBudgetCc,
				selectMainBank:me.selectMainBank,
				selectPayType:me.selectPayType,
				selectIcharge:me.selectIcharge
			},
			'expUseVoyagerTab':{
				selectCostControl:me.selectCostControl
			}
		});
	
	},
	
	PREVIEW_MSG_KEY : 'PREVIEW',
	SEND_MSG_KEY : 'SEND_EXP_USE',
	MSG_KEY : 'SAVE_EXP_USE',
	URL : ALF_CONTEXT+'/exp/use',
	MSG_URL : ALF_CONTEXT+'/exp/message',
	
	validForm:function(saveDraft,fn) {
		var me = this;
		var result = true;
		
		return result;
	},
	
	listInvalidField:function(form) {
		var ifield = form.query("field{isValid()==false}");
		var msg = "";
		var i = 1;
		for(var a in ifield) {
			if (msg) {
				msg +="<br/>";
			}
			var lbl = ifield[a].getFieldLabel();
			var pos = lbl.indexOf("<font");
			if(pos<0) {
				pos = lbl.length;
			}
			msg += i+"."+lbl.substring(0,pos);
			i++;
		}
		
		return msg;
	},
	
	send:function() {
		var form = this.getMainForm();
		
		if (validForm(form)) {
			if(this.validForm(false,'doSend')){
				PB.Dlg.confirm('CONFIRM_'+this.SEND_MSG_KEY,this,'doSend', MODULE_EXP);
			}
		} else {
			var msg = this.listInvalidField(form);
			PB.Dlg.error('INVALID_INPUT_'+this.MSG_KEY, MODULE_EXP, {msg:msg});
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
			   		PB.Dlg.info('SUCC_'+me.SEND_MSG_KEY, MODULE_EXP, {msg:'ID:'+json.data.id, fn:me.closeForm, scope:me});
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
				   		
			   			PB.Dlg.error(null, MODULE_EXP, {msg:msg});
			   		}
			   		else {
				   		PB.Dlg.error('ERR_'+me.SEND_MSG_KEY, MODULE_EXP);
			   		}
			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.SEND_MSG_KEY+" ("+json.message+")", MODULE_EXP);
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
				
			objective:me.getTxtObjective().getValue()
		};
		params.budgetCcType = me.getHidBudgetCcType().getValue();
		params.budgetCc = me.getHidBudgetCc().getValue();
		
		var ccType = me.getRadCostControlTypeId().getGroupValue();
	
		params.costControlTypeId = ccType;
		if (ccType=="0") {
			params.costControlId = me.getCmbCostControlId().getValue();
			
			params.costControlFrom = me.getTxtCc1From().getValue();
			params.costControlTo = me.getTxtCc1To().getValue();
		} 
		else if (ccType=="1") {
			params.costControl = me.getTxtCostControl().getValue();
			
			params.costControlFrom = me.getTxtCc2From().getValue();
			params.costControlTo = me.getTxtCc2To().getValue();
		}
		
		params.bankType = me.getRadBankType().getGroupValue();
		params.bank = me.getCmbBank().getValue();
		
		params.vatId = me.getCmbVatId().getValue();
		params.vat = me.getHidVat().getValue();
		
		var payType = me.getRadPayType().getGroupValue();
		params.payType = payType;
		if (payType==1) {
			params.payDtl1 = me.getTxtSupName().getValue();
		} 
		else
		if (payType==2) {
			params.payDtl1 = me.getTxtSupFeeName().getValue();
			params.payDtl2 = me.getCmbPoNo().getValue();
			params.payDtl3 = me.getCmbAssetNo().getValue();
		} 
		else
		if (payType==3) {
			params.payDtl1 = me.getCmbAvCode().getValue();
		}
		else
		if (payType==4) {
			params.payDtl1 = me.getHidIchargeCode().getValue();
			params.payDtl2 = me.getHidIchargeType().getValue();
		}
		
		params.total = me.getTxtTotal().getValue();
		
		params.voyagers = me.getVoyagers();
		params.items = me.getItems();
		params.files = me.listFiles();
		
		return params;
	},
	
	cancel:function() {
		PB.Dlg.confirm('CONFIRM_CANCEL_EXP_USE',this,'closeForm', MODULE_EXP);
	},
	
	closeForm:function() {
		this.getMainForm().destroy();
		this.refreshGrid();
	},
	
	refreshGrid:function() {
		this.getMainGrid().getStore().load();
	},
	
	saveDraft:function() {
		var form = this.getMainForm();
	
		if(!validForm(form)){
			var msg = this.listInvalidField(form);			
			
			PB.Dlg.error('INVALID_INPUT_'+this.MSG_KEY, MODULE_EXP, {msg:msg});
			return;
		}
	
		if (!this.validForm(true)) {
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
			   		me.getHidId().setValue(id);
			   		me.getMainForm().setTitle('Edit : <font color="red">'+id+"</font>");
			   		me.refreshGrid();
			   		PB.Dlg.info('SUCC_'+me.MSG_KEY, MODULE_EXP, {msg:'ID:'+id, scope:me});
			   	} else {
			   		PB.Dlg.error('ERR_'+me.MSG_KEY, MODULE_EXP);
			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.MSG_KEY+" ("+json.message+")", MODULE_EXP);
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
	
	getVoyagers:function() {
	
		var me = this;
	
		var data = [];
		me.getVoyagerEmpGrid().getStore().each(function(rec){
		   rec.data.type = "E";
		   data.push(rec.data);
		});
		
		me.getVoyagerOthGrid().getStore().each(function(rec){
		   rec.data.type = "O";
		   data.push(rec.data);
		});
		
		return Ext.JSON.encode(data);
	},
	
	getItems:function() {
	
		var me = this;
	
		var data = [];
		me.getItemGrid().getStore().each(function(rec){
		   if (!rec.data.condition1) {
			   rec.data.condition1 = "";
		   }
		   
		   data.push(rec.data);
		});
		
		return Ext.JSON.encode(data);
	},
	
	listFiles:function() {
		var values = [];
	
		var store = this.getFileTab().down("uploadGrid").getStore();
		
		store.each(function(rec){
			values.push({
				name:rec.get("name"),
				desc:rec.get("desc"),
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
		
		if (validForm(form)) {
			if(this.validForm(false,'doFinish')){
				PB.Dlg.confirm('CONFIRM_'+this.SEND_MSG_KEY,this,'doFinish', MODULE_EXP);
			}
		} else {
			var msg = this.listInvalidField(form);
			PB.Dlg.error('INVALID_INPUT_'+this.MSG_KEY, MODULE_EXP, {msg:msg});
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
			   		 
			   		PB.Dlg.info('SUCC_'+me.MSG_KEY, MODULE_EXP, {msg:'ID:'+json.data.id, fn:me.backToWfForm, scope:me});
			   		
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
						
						PB.Dlg.error(null, MODULE_EXP, {msg:msg});
					}
					else {
						PB.Dlg.error('ERR_'+me.MSG_KEY, MODULE_EXP);
					}
	
			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.MSG_KEY+" ("+json.message+")", MODULE_EXP);
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
		PB.Dlg.confirm('CONFIRM_CANCEL_EXP_USE',this,'backToWfForm', MODULE_EXP);
	},
	
	backToWfForm:function() {
		window.location = appContext+"/page/task-edit?taskId="+TASK_ID+"&referrer=tasks";
	},
	
	showApprovalMatrix:function() {
		var me = this;
	
		var dlg = Ext.create("PBExpUse.view.approvalMatrix.DtlDlg");
		var id = me.getBtnApprovalMatrix().fid;
	
		var store = dlg.items.items[0].getStore(); 
		store.getProxy().extraParams = {
			id : id
		}
		store.load();
		
		dlg.show();
	},
	
	preview:function() {
		
		var me = this;
		
		var form = me.getMainForm();
		
		if(!validForm(form)){
			var msg = this.listInvalidField(form);
			
			PB.Dlg.error('INVALID_INPUT_'+this.MSG_KEY, MODULE_EXP, {msg:msg});
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
			   		PB.Dlg.error('ERR_'+me.PREVIEW_MSG_KEY, MODULE_EXP);
			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.PREVIEW_MSG_KEY+" ("+json.message+")", MODULE_EXP);
		    	}
		    	catch (err) {
			    	alert(response.responseText);
		    	}
			    myMask.hide();
		    },
		    headers: getAlfHeader()
		});
	
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
	
	selectIchargeCallBack:function(ids, type, typeName) {
		var tab = this.targetPanel;
		setValue(tab, 'ichargeCode', ids[0]);
		setValue(tab, 'ichargeName', ids[1]);
		setValue(tab, 'ichargeType', type);
		setValue(tab, 'ichargeTypeName', typeName);
	},
	
	selectIcharge:function() {
		var me = this;
	
		var dlg = Ext.create("PB.view.common.SearchSectionProjectDlg",{
			title:'ค้นหา',
			targetPanel:this.getInfoTab(),
			callback:this.selectIchargeCallBack
		});
		dlg.show();
	},
	
	selectReqByCallBack:function(id, rec) {
		var tab = this.targetPanel;
		setValue(tab, 'reqBy', rec.get('emp_id'));
		setValue(tab, 'reqByName', rec.get('first_name') + ' ' + rec.get('last_name'));
		setValue(tab, 'reqByDept', rec.get('pos_name'));
		setValue(tab, 'reqBu', rec.get('org_desc'));
		setValue(tab, 'reqOuName', rec.get('section_desc'));
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
	
	selectMainBank:function(rad,v) {
		var me = this;
		
		var vi = parseInt(v);
		
		var i=0;
		var b = vi!=1; 
		me.getCmbBank().setDisabled(b);
		if (b) {
			me.getCmbBank().setValue(null);
		}
	},
	
	selectCostControl:function(rad,v) {
		var me = this;
		
		var vi = parseInt(v);
		
		var i=0;
		var b = vi!=i;
		me.getCmbCostControlId().setDisabled(b);
		me.getTxtCc1From().setDisabled(b);
		me.getTxtCc1To().setDisabled(b);
		if (b) {
			me.getCmbCostControlId().setValue(null);
			me.getTxtCc1From().setValue(null);
			me.getTxtCc1To().setValue(null);
		}
		
		i++;
		b = vi!=i;
		me.getTxtCostControl().setDisabled(b);
		me.getTxtCc2From().setDisabled(b);
		me.getTxtCc2To().setDisabled(b);
		if (b) {
			me.getTxtCostControl().setValue(null);
			me.getTxtCc2From().setValue(null);
			me.getTxtCc2To().setValue(null);
		}
	},
	
	selectPayType:function(rad,v) {
		var me = this;
		
		var vi = parseInt(v);
		
		var i=1;
		b = vi!=i;
		me.getTxtSupName().setDisabled(b);
		if (b) {
			me.getTxtSupName().setValue(null);
		}
		
		i++;
		b = vi!=i;
		me.getTxtSupFeeName().setDisabled(b);
		me.getCmbPoNo().setDisabled(b);
		me.getCmbAssetNo().setDisabled(b);
		if (b) {
			me.getTxtSupFeeName().setValue(null);
			me.getCmbPoNo().setValue(null);
			me.getCmbAssetNo().setValue(null);
		}
		
		i++;
		b = vi!=i;
		me.getCmbAvCode().setDisabled(b);
		if (b) {
			me.getCmbAvCode().setValue(null);
		}
		
		i++;
		b = vi!=i;
		me.getTxtIchargeTypeName().setDisabled(b);
		if (b) {
			me.getTxtIchargeTypeName().setValue(null);
		}
	}
	
});
