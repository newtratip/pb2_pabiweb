Ext.define('PBExp.controller.Form', {
    extend: 'Ext.app.Controller',

    refs:[{
        ref: 'main',
        selector: 'expBrwMain'
    },{
        ref: 'mainForm',
        selector: 'expBrwMainForm'
    },{
        ref: 'mainGrid',
        selector: 'expBrwMainGrid'
    },{
        ref: 'hidId',     
        selector: 'expBrwMainForm field[name=id]'
    },{
        ref: 'hidStatus',     
        selector: 'expBrwMainForm field[name=status]'
    },{
        ref: 'txtReqBy',     
        selector: 'expBrwMainForm field[name=reqBy]'
    },{
        ref: 'txtReqOu',     
        selector: 'expBrwMainForm field[name=reqOu]'
    },{
        ref: 'cmbObjectiveType',     
        selector: 'expBrwMainForm field[name=objectiveType]'
    },{
        ref: 'txtObjective',
        selector: 'expBrwMainForm field[name=objective]'
    },{
        ref: 'pnlOldAV',
        selector: 'expBrwInfoTab panel[itemId=oldAV]'
    },{
        ref: 'txtReason',
        selector: 'expBrwMainForm field[name=reason]'
    },{
        ref: 'hidBudgetCcType',
        selector: 'expBrwMainForm field[name=budgetCcType]'
    },{
        ref: 'hidBudgetCc',
        selector: 'expBrwMainForm field[name=budgetCc]'
    },{
        ref: 'cmbBank',     
        selector: 'expBrwMainForm field[name=bank]'
    },{
        ref: 'radBankType',     
        selector: 'expBrwMainForm field[name=bankType]'
    },{
        ref: 'hidTotal',     
        selector: 'expBrwMainForm field[name=total]'
    },{
        ref: 'txtActivity',
        selector: 'expBrwMainForm field[name=activity]'
    },{
        ref: 'radCostControlTypeId',
        selector: 'expBrwMainForm field[name=costControlTypeId]'
    },{
        ref: 'cmbCostControlId',
        selector: 'expBrwMainForm field[name=costControlId]'
    },{
        ref: 'txtCostControl',
        selector: 'expBrwMainForm field[name=costControl]'
    },{
        ref: 'txtCc1From',
        selector: 'expBrwMainForm field[name=cc1From]'
    },{
        ref: 'txtCc1To',
        selector: 'expBrwMainForm field[name=cc1To]'
    },{
        ref: 'txtCc2From',
        selector: 'expBrwMainForm field[name=cc2From]'
    },{
        ref: 'txtCc2To',
        selector: 'expBrwMainForm field[name=cc2To]'
    },{
    	ref:'fileTab',
		selector:'expBrwFileTab'
	},{
    	ref:'uploadGrid',
		selector:'expBrwFileTab uploadGrid'
    },{
        ref: 'btnApprovalMatrix',     
        selector: 'expBrwMainForm button[action=approvalMatrix]'
	},{
    	ref:'itemGrid',
    	selector:'expBrwInfoTab grid[itemId=itemGrid]'
	},{
    	ref:'attendeeEmpGrid',
    	selector:'expBrwAttendeeTab #empGrid'
	},{
    	ref:'attendeeOthGrid',
    	selector:'expBrwAttendeeTab #othGrid'
	},{
    	ref:'infoTab',
    	selector:'expBrwInfoTab'
	},{
    	ref:'userTab',
    	selector:'expBrwUserTab'
    }],

    init:function() {
		var me = this;
		
		me.control({
			'expBrwMainForm [action=finish]': {
				click : me.finish
			},
			'expBrwMainForm [action=cancelEdit]': {
				click : me.cancelEdit
			},
			'expBrwMainForm [action=send]': {
				click : me.send
			},
			'expBrwMainForm [action=cancel]': {
				click : me.cancel
			},
			'expBrwMainForm [action=saveDraft]': {
				click : me.saveDraft
			},
			'expBrwMainForm [action=preview]': {
				click : me.preview
			},
			'expBrwMainForm [action=approvalMatrix]': {
				click : me.showApprovalMatrix
			},
			'expBrwUserTab':{
				selectReqBy:me.selectReqBy
			},
			'expBrwInfoTab':{
				selectBudgetCc:me.selectBudgetCc,
				selectMainBank:me.selectMainBank,
				oldStoreLoad:me.oldStoreLoad
			},
			'expBrwAttendeeTab':{
				selectCostControl:me.selectCostControl
			}
		});

	},
	
	PREVIEW_MSG_KEY : 'PREVIEW',
	SEND_MSG_KEY : 'SEND_EXP_BRW',
	MSG_KEY : 'SAVE_EXP_BRW',
    URL : ALF_CONTEXT+'/exp',
    MSG_URL : ALF_CONTEXT+'/exp/message',
    
    validForm:function(saveDraft,fn) {
		var me = this;
		var result = true;
		
		if (!saveDraft) {
			var msg = "";
			
			// check file
			if (result) {
//				if (me.getHidTotal().getValue()>=100000) {
//					if (me.getFileTab().down("uploadGrid").getStore().getCount()<=0) {
//						PB.Dlg.show('ERR_NO_FILE', MODULE_EXP, 
//							{
//								icon:Ext.MessageBox.WARNING,
//								modal:true,
//								fn:function(btn) {
//									if (btn=='yes') {
//										me[fn](null);
//									}
//								},
//								scope:me,
////								animateTarget:me.getFileTab().down("uploadGrid"),
//								buttonText:{yes:'ยืนยันการส่งขออนุมัติ',no:'แนบเอกสาร'},
//								buttons:Ext.MessageBox.YESNO
//							}
//						);
//						result = false;
//					}
//				}
			}
		}
		
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
  			
			objectiveType:me.getCmbObjectiveType().getValue(),
			objective:me.getTxtObjective().getValue(),
			reason:me.getTxtReason().getValue()
  		};
		params.budgetCcType = me.getHidBudgetCcType().getValue();
		params.budgetCc = me.getHidBudgetCc().getValue();
		
		var ccType = me.getRadCostControlTypeId().getGroupValue();
//		console.log("brwType:"+brwType);

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
		
		params.total = me.getHidTotal().getValue();
		
		params.items = me.getItems();
		params.attendees = me.getAttendees();
		params.files = me.listFiles();
		
		return params;
	},
	
	cancel:function() {
		PB.Dlg.confirm('CONFIRM_CANCEL_EXP_BRW',this,'closeForm', MODULE_EXP);
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
	
	getItems:function() {
		var me = this;
	
		var itemStore = me.getItemGrid().getStore();
		
		var data = [];
		itemStore.each(function(rec){
		   data.push(rec.data);
		});
		
		return Ext.JSON.encode(data);
	},
	
	getAttendees:function() {
	
		var me = this;
	
		var eStore = me.getAttendeeEmpGrid().getStore();
		
		var data = [];
		eStore.each(function(rec){
		   rec.data.type = "E";
		   data.push(rec.data);
		});
		
		var oStore = me.getAttendeeOthGrid().getStore();
		oStore.each(function(rec){
		   rec.data.type = "O";
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
		PB.Dlg.confirm('CONFIRM_CANCEL_EXP_BRW',this,'backToWfForm', MODULE_EXP);
	},

	backToWfForm:function() {
		window.location = appContext+"/page/task-edit?taskId="+TASK_ID+"&referrer=tasks";
	},
	
	showApprovalMatrix:function() {
		var me = this;
	
		var dlg = Ext.create("PBExp.view.approvalMatrix.DtlDlg");
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

		var dlg = Ext.create("PB.view.common.SearchBudgetSrcDlg",{
			title:'ค้นหา',
			targetPanel:this.getInfoTab(),
			callback:this.selectBudgetCcCallBack
		});
		dlg.show();
	},
	
	selectReqByCallBack:function(id, rec) {
		var tab = this.targetPanel;
		setValue(tab, 'reqBy', rec.get('emp_id'));
		setValue(tab, 'reqByName', rec.get('first_name') + ' ' + rec.get('last_name'));
		
		var mphone = rec.get("mobile_phone")!=null ? rec.get("mobile_phone") : "";
		var wphone = rec.get("work_phone")!=null ? rec.get("work_phone") : "";
		var comma = (mphone!="" && wphone!="") ? "," : "";
		
		setValue(tab, 'reqTelNo', wphone+comma+mphone);
		
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
	
	oldStoreLoad:function(r) {
//		for(var a in r) {
//			console.log("- "+a+":"+r[a]);
//		}
		if (!r || r.length<=0) {
			this.getTxtReason().setFieldStyle('background-color: #ddd; background-image:none;');
			this.getTxtReason().setDisabled(true);
			this.getPnlOldAV().setDisabled(true);
		}
	}
	
});
